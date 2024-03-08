package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.blocks.entity.TempBarrierBlockEntity;
import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AurorianiteAxe extends AxeItem implements ITooltipsItem {

    public AurorianiteAxe() {
        super(TAToolTiers.AURORIANITE, (12.0F), (-3.5F), new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && state.is(BlockTags.LOGS)) {
            String toolUUID = UUID.randomUUID().toString();
            stack.getOrCreateTag().putString("ToolUUID", toolUUID);
            for (BlockPos blockPos : BlockPos.withinManhattan(pos, 1, 1, 1)) {
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.is(BlockTags.LOGS)) {
                    level.destroyBlock(blockPos, Boolean.TRUE);
                    level.setBlockAndUpdate(blockPos, TABlocks.TEMP_BARRIER.get().defaultBlockState());
                    if (level.getBlockEntity(blockPos) instanceof TempBarrierBlockEntity blockEntity) {
                        blockEntity.setMinerUUID(entityLiving.getStringUUID());
                        blockEntity.setDestroyCountdown(10);
                        blockEntity.setToolUUID(toolUUID);
                        blockEntity.setLogState(state);
                    }
                }
            }
        }

        return true;
    }

}