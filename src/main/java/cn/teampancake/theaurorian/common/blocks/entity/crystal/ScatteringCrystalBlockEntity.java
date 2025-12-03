package cn.teampancake.theaurorian.common.blocks.entity.crystal;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ScatteringCrystalBlockEntity extends AbstractLumenCrystalBlockEntity {

    public ScatteringCrystalBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.SCATTERING_CRYSTAL.get(), pos, blockState);
    }

}