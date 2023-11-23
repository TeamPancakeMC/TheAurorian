package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.entity.AurorianFurnaceBlockEntity;
import cn.teampancake.theaurorian.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class AurorianFurnace extends FurnaceBlock {

    public AurorianFurnace(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock()) && !level.isClientSide) {
            Direction facing = state.getValue(FACING);
            for (Direction direction : Direction.values()) {
                BlockState state1 = level.getBlockState(pos.relative(direction));
                BlockState state2 = level.getBlockState(pos.relative(direction.getOpposite()));
                if (facing == direction && state1.isSolidRender(level, pos) && !state2.isSolidRender(level, pos)) {
                    facing = direction.getOpposite();
                }
            }
            level.setBlock(pos, state.setValue(FACING, facing), 2);
        }
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AurorianFurnaceBlockEntity(pos, state);
    }

    @Nullable @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, TABlockEntityTypes.AURORIAN_FURNACE.get(), AurorianFurnaceBlockEntity::serverTick);
    }

}