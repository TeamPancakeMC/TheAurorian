package cn.teampancake.theaurorian.common.blocks.entity.crystal;

import cn.teampancake.theaurorian.common.blocks.crystal.AbstractLunarCrystal;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class LunarDeflectorBlockEntity extends AbstractLunarCrystalBlockEntity {

    public LunarDeflectorBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.LUNAR_DEFLECTOR.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, LunarDeflectorBlockEntity blockEntity) {
        if (blockEntity.activating && --blockEntity.activeTime == 0) {
            level.setBlockAndUpdate(pos, state.setValue(AbstractLunarCrystal.ACTIVATED, true));
            blockEntity.activating = false;
            blockEntity.activated = true;
            blockEntity.updateBlock();
        }
    }

}