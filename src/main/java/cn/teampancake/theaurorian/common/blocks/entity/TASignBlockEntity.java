package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TASignBlockEntity extends SignBlockEntity {

    public TASignBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.TA_SIGN.get(), pos, blockState);
    }

}