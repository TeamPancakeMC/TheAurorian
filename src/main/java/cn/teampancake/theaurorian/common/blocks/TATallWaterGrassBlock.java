package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;

public class TATallWaterGrassBlock extends TallSeagrassBlock {

    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;

    public TATallWaterGrassBlock(Properties properties) {
        super(properties.lightLevel(state -> state.getValue(LEVEL)));
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, LEVEL);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return TAFluids.MOON_WATER_STILL.get().getSource(false);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(TABlocks.AURORIAN_WATER_GRASS.get());
    }

}