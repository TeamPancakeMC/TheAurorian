package cn.teampancake.theaurorian.common.blocks.entity.crystal;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ReceivingCrystalBlockEntity extends AbstractLumenCrystalBlockEntity {

    public ReceivingCrystalBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.RECEIVING_CRYSTAL.get(), pos, blockState);
    }

}