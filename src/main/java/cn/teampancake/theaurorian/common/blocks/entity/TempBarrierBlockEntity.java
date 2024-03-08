package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class TempBarrierBlockEntity extends BlockEntity {

    private boolean stopChopping;
    private int destroyCountdown;
    private String minerUUID = "";
    private String toolUUID = "";
    private BlockState logState = Blocks.AIR.defaultBlockState();

    public TempBarrierBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.TEMP_BARRIER.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TempBarrierBlockEntity blockEntity) {
        if (!level.isClientSide() && level.getGameTime() % 2 == 0) {
            if (blockEntity.destroyCountdown < 0) {
                level.removeBlock(pos, Boolean.FALSE);
            } else {
                --blockEntity.destroyCountdown;
            }
            // Damage tool when destroy block.
            if (blockEntity.logState.is(BlockTags.LOGS) && !blockEntity.minerUUID.isEmpty()) {
                if (level instanceof ServerLevel serverLevel) {
                    List<String> uuidList = Lists.newArrayList();
                    List<ServerPlayer> playerList = serverLevel.players();
                    playerList.forEach(player -> uuidList.add(player.getStringUUID()));
                    blockEntity.stopChopping = !uuidList.contains(blockEntity.minerUUID);
                    if (blockEntity.destroyCountdown == 0) {
                        playerList.forEach(player -> {
                            InteractionHand hand = player.getUsedItemHand();
                            List<ItemStack> stackList = Lists.newArrayList();
                            if (player.getStringUUID().equals(blockEntity.minerUUID)) {
                                player.getInventory().items.stream().filter(ItemStack::isDamageableItem).forEach(stack -> {
                                    boolean flag = stack.getTag() != null && stack.getTag().getAllKeys().contains("ToolUUID");
                                    if (flag && stack.getTag().getString("ToolUUID").equals(blockEntity.toolUUID)) {
                                        stack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(hand));
                                        stackList.add(stack);
                                    }
                                });
                            }
                            // Stop destroy block if tool was break.
                            blockEntity.stopChopping = stackList.isEmpty();
                        });
                    }
                }

                if (!blockEntity.stopChopping) {
                    for (BlockPos blockPos : BlockPos.withinManhattan(pos, 1, 1, 1)) {
                        BlockState neighborState = level.getBlockState(blockPos);
                        if (neighborState.is(blockEntity.logState.getBlock())) {
                            level.destroyBlock(blockPos, Boolean.TRUE);
                            level.setBlockAndUpdate(blockPos, state);
                        }

                        if (neighborState.is(TABlocks.TEMP_BARRIER.get())) {
                            BlockEntity relativeBlockEntity = level.getBlockEntity(blockPos);
                            if (relativeBlockEntity instanceof TempBarrierBlockEntity tempBarrier) {
                                if (tempBarrier.logState.isAir() || tempBarrier.minerUUID.isEmpty()) {
                                    tempBarrier.destroyCountdown = blockEntity.destroyCountdown;
                                    tempBarrier.logState = blockEntity.logState;
                                    tempBarrier.minerUUID = blockEntity.minerUUID;
                                    tempBarrier.toolUUID = blockEntity.toolUUID;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void load(CompoundTag tag) {
        this.stopChopping = tag.getBoolean("StopChopping");
        this.destroyCountdown = tag.getInt("DestroyCountdown");
        this.minerUUID = tag.getString("MinerUUID");
        this.toolUUID = tag.getString("ToolUUID");
        if (this.level != null) {
            HolderLookup<Block> blockGetter = this.level.holderLookup(Registries.BLOCK);
            this.logState = NbtUtils.readBlockState(blockGetter, tag.getCompound("LogState"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putBoolean("StopChopping", this.stopChopping);
        tag.putInt("DestroyCountdown", this.destroyCountdown);
        tag.putString("MinerUUID", this.minerUUID);
        tag.putString("ToolUUID", this.toolUUID);
        tag.put("LogState", NbtUtils.writeBlockState(this.logState));
    }

    public void setDestroyCountdown(int value) {
        this.destroyCountdown = value;
    }

    public void setMinerUUID(String uuid) {
        this.minerUUID = uuid;
    }

    public void setToolUUID(String uuid) {
        this.toolUUID = uuid;
    }

    public void setLogState(BlockState state) {
        this.logState = state;
    }

}