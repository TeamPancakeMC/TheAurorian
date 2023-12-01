package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

@SuppressWarnings("deprecation")
public class DungeonStoneGate extends Block implements ITooltipsItem {

    public static final BooleanProperty UNLOCKED = BooleanProperty.create("unlocked");
    public static final BooleanProperty DESTROYED = BooleanProperty.create("destroy");

    public DungeonStoneGate(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(UNLOCKED, false));
    }

    protected boolean isDestroyed(BlockState state) {
        return state.hasProperty(DESTROYED) && state.getValue(DESTROYED);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UNLOCKED);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide) {
            if (this.isDestroyed(state)) {
                if (state.getValue(UNLOCKED)) {
                    level.setBlockAndUpdate(pos, state.setValue(DESTROYED, false).setValue(UNLOCKED, false));
                } else {
                    level.setBlockAndUpdate(pos, state.setValue(UNLOCKED, true));
                    level.scheduleTick(pos, this, 15);
                }
            } else {
                if (state.getValue(UNLOCKED)) {
                    if (state.hasProperty(DESTROYED)) {
                        level.setBlockAndUpdate(pos, state.setValue(UNLOCKED, false).setValue(DESTROYED, true));
                        level.scheduleTick(pos, this, 20);
                    } else {
                        level.destroyBlock(pos, false);
                    }

                    for (Direction direction : Direction.values()) {
                        this.unlockRelativeSameBlock(state, level, pos.relative(direction));
                    }
                }
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide && !this.isDestroyed(state) && !state.getValue(UNLOCKED) && level.hasNeighborSignal(pos)) {
            this.unlockRelativeSameBlock(state, level, pos);
        }
    }

    protected void unlockRelativeSameBlock(BlockState state, Level level, BlockPos pos) {
        BlockState relativeState = level.getBlockState(pos);
        if (relativeState.getBlock() == state.getBlock() && !this.isDestroyed(relativeState) && !relativeState.getValue(UNLOCKED)) {
            level.setBlockAndUpdate(pos, relativeState.setValue(UNLOCKED, true));
            level.scheduleTick(pos, relativeState.getBlock(), 5);
        }
    }

}