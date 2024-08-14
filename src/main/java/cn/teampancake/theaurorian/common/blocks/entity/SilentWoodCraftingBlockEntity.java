package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SilentWoodCraftingBlockEntity extends BlockEntity {

    private boolean isActive;

    public SilentWoodCraftingBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.SILENT_WOOD_CRAFTING_TABLE.get(), pos, blockState);
        this.isActive = true;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SilentWoodCraftingBlockEntity blockEntity) {
        for (Direction direction : Direction.BY_2D_DATA) {
            if (!level.getBlockState(pos.relative(direction)).is(TABlockTags.RUNE_STONE_BLOCK)) {
                blockEntity.isActive = false;
                break;
            }
        }

        if (blockEntity.isActive) {
            //TODO: 这里应该是放置工作台的操作
        }
    }

}