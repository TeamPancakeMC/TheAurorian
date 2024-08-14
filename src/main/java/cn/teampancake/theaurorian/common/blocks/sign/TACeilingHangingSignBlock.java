package cn.teampancake.theaurorian.common.blocks.sign;

import cn.teampancake.theaurorian.common.blocks.entity.TAHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TACeilingHangingSignBlock extends CeilingHangingSignBlock {

    public TACeilingHangingSignBlock(Properties properties, WoodType type) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TAHangingSignBlockEntity(pos, state);
    }

}
