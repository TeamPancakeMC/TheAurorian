package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class AurorianPortalFrame extends Block implements ITooltipsItem {

    public AurorianPortalFrame(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isPortalFrame(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

}