package cn.teampancake.theaurorian.common.level.portal;

import cn.teampancake.theaurorian.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
public class AurorianPortalShape extends PortalShape {

    public AurorianPortalShape(LevelAccessor level, BlockPos bottomLeft, Direction.Axis axis) {
        super(level, bottomLeft, axis);
    }

    public static Optional<AurorianPortalShape> findAurorianEmptyPortalShape(LevelAccessor level, BlockPos bottomLeft, Direction.Axis axis) {
        return findAurorianPortalShape(level, bottomLeft, (portalShape) -> portalShape.isValid() && portalShape.numPortalBlocks == 0, axis);
    }

    public static Optional<AurorianPortalShape> findAurorianPortalShape(LevelAccessor level, BlockPos bottomLeft, Predicate<AurorianPortalShape> predicate, Direction.Axis axis) {
        Optional<AurorianPortalShape> optional = Optional.of(new AurorianPortalShape(level, bottomLeft, axis)).filter(predicate);
        if (optional.isPresent()) {
            return optional;
        } else {
            Direction.Axis direction$axis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
            return Optional.of(new AurorianPortalShape(level, bottomLeft, direction$axis)).filter(predicate);
        }
    }

    @Nullable
    @SuppressWarnings("StatementWithEmptyBody")
    public BlockPos calculateBottomLeft(BlockPos pos) {
        boolean flag = isEmpty(this.level.getBlockState(pos.below()));
        for (int i = Math.max(this.level.getMinBuildHeight(), pos.getY() - 21); pos.getY() > i && flag; pos = pos.below()) {
        }

        Direction direction = this.rightDir.getOpposite();
        int j = this.getDistanceUntilEdgeAboveFrame(pos, direction) - 1;
        return j < 0 ? null : pos.relative(direction, j);
    }

    @Override
    public int getDistanceUntilEdgeAboveFrame(BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for(int i = 0; i <= 21; ++i) {
            mutableBlockPos.set(pos).move(direction, i);
            BlockState blockstate = this.level.getBlockState(mutableBlockPos);
            if (!isEmpty(blockstate)) {
                if (FRAME.test(blockstate, this.level, mutableBlockPos)) {
                    return i;
                }
                break;
            }

            BlockState blockState = this.level.getBlockState(mutableBlockPos.move(Direction.DOWN));
            if (!FRAME.test(blockState, this.level, mutableBlockPos)) {
                break;
            }
        }

        return 0;
    }

    @Override
    public int getDistanceUntilTop(BlockPos.MutableBlockPos pos) {
        for(int i = 0; i < 21; ++i) {
            if (this.bottomLeft != null) {
                pos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, -1);
                if (!FRAME.test(this.level.getBlockState(pos), this.level, pos)) {
                    return i;
                }

                pos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, this.width);
                if (!FRAME.test(this.level.getBlockState(pos), this.level, pos)) {
                    return i;
                }

                for(int j = 0; j < this.width; ++j) {
                    pos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, j);
                    BlockState blockState = this.level.getBlockState(pos);
                    if (!isEmpty(blockState)) {
                        return i;
                    }

                    if (blockState.is(TABlocks.AURORIAN_PORTAL.get())) {
                        ++this.numPortalBlocks;
                    }
                }
            }
        }

        return 21;
    }

    private static boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.is(TABlocks.AURORIAN_PORTAL.get());
    }

    @Override
    public void createPortalBlocks() {
        if (this.bottomLeft != null) {
            BlockState blockState = TABlocks.AURORIAN_PORTAL.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, this.axis);
            BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1)
                    .relative(this.rightDir, this.width - 1)).forEach((pos) -> this.level.setBlock(pos, blockState, 18));
        }
    }

}