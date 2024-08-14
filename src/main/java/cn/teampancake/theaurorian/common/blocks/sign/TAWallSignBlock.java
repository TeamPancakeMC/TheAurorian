package cn.teampancake.theaurorian.common.blocks.sign;

import cn.teampancake.theaurorian.common.blocks.entity.TASignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TAWallSignBlock extends WallSignBlock {

    public TAWallSignBlock(Properties properties, WoodType type) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TASignBlockEntity(pos, state);
    }

}