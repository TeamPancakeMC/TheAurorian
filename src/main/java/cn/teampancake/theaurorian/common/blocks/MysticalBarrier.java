package cn.teampancake.theaurorian.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class MysticalBarrier extends HalfTransparentBlock {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final VoxelShape AABB_NS = box(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
    private static final VoxelShape AABB_EW = box(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);

    public MysticalBarrier(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.isPassenger() && !entity.isVehicle()) {
            Vec3 vec3 = entity.getDeltaMovement();
            double x = vec3.x, z = vec3.z;
            switch (state.getValue(FACING)) {
                case NORTH -> z += 0.2D;
                case SOUTH -> z -= 0.2D;
                case EAST -> x += 0.2D;
                case WEST -> x -= 0.2D;
            }
            entity.setDeltaMovement(x, vec3.y, z);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case EAST, WEST -> AABB_EW;
            default -> AABB_NS;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}