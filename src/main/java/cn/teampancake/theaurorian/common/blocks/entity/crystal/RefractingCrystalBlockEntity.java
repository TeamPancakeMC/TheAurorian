package cn.teampancake.theaurorian.common.blocks.entity.crystal;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RefractingCrystalBlockEntity extends AbstractLumenCrystalBlockEntity {

    public RefractingCrystalBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.REFRACTING_CRYSTAL.get(), pos, blockState);
    }

}