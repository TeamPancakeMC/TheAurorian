package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;

public class TASandBlock extends SandBlock {
    public TASandBlock(int pDustColor, Properties pProperties) {
        super(pDustColor, pProperties);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return state.is(TABlockTags.AURORIAN_SAND_BLOCK);
    }
}
