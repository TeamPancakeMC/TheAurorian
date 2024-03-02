package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.blocks.DungeonStoneGate;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DungeonStoneGateBlockEntity extends BlockEntity {

    private boolean unlock;
    private int unlockInterval;
    private int destroyCountdown;
    private BlockState gateState = Blocks.AIR.defaultBlockState();

    public DungeonStoneGateBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.DUNGEON_STONE_GATE.get(), pos, blockState);
    }

    @SuppressWarnings("unused")
    public static void serverTick(Level level, BlockPos pos, BlockState state, DungeonStoneGateBlockEntity blockEntity) {
        if (!level.isClientSide() && blockEntity.unlock && !blockEntity.gateState.is(Blocks.AIR)) {
            if (--blockEntity.unlockInterval <= 0) {
                for (Direction direction : Direction.values()) {
                    BlockPos relativePos = pos.relative(direction);
                    BlockState neighborState = level.getBlockState(relativePos);
                    if (neighborState.is(blockEntity.gateState.getBlock()) && neighborState.hasBlockEntity()) {
                        BlockEntity relativeBlockEntity = level.getBlockEntity(relativePos);
                        level.setBlockAndUpdate(relativePos, neighborState.setValue(DungeonStoneGate.UNLOCKED, true));
                        if (relativeBlockEntity instanceof DungeonStoneGateBlockEntity gateBlockEntity && !gateBlockEntity.unlock) {
                            gateBlockEntity.unlockInterval = level.random.nextInt(20);
                            int preset = level.random.nextInt(40);
                            int interval = gateBlockEntity.unlockInterval;
                            gateBlockEntity.destroyCountdown = preset < interval ? interval + preset : preset;
                            gateBlockEntity.gateState = blockEntity.gateState;
                            gateBlockEntity.unlock = true;
                        }
                    }
                }
            }

            if (--blockEntity.destroyCountdown <= 0) {
                level.destroyBlock(pos, Boolean.FALSE);
            }
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.unlock = tag.getBoolean("Unlock");
        this.unlockInterval = tag.getInt("UnlockInterval");
        this.destroyCountdown = tag.getInt("DestroyCountdown");
        if (this.level != null) {
            HolderLookup<Block> blockGetter = this.level.holderLookup(Registries.BLOCK);
            this.gateState = NbtUtils.readBlockState(blockGetter, tag.getCompound("GateState"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("Unlock", this.unlock);
        tag.putInt("UnlockInterval", this.unlockInterval);
        tag.putInt("DestroyCountdown", this.destroyCountdown);
        tag.put("GateState", NbtUtils.writeBlockState(this.gateState));
    }

    public boolean isUnlock() {
        return this.unlock;
    }

    public void setUnlock(boolean unlock) {
        this.unlock = unlock;
    }

    public int getUnlockInterval() {
        return this.unlockInterval;
    }

    public void setUnlockInterval(int unlockInterval) {
        this.unlockInterval = unlockInterval;
    }

    public void setDestroyCountdown(int destroyCountdown) {
        this.destroyCountdown = destroyCountdown;
    }

    public void setGateState(BlockState gateState) {
        this.gateState = gateState;
    }

}