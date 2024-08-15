package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.entity.MoonlightForgeBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MoonlightForge extends BaseEntityBlockWithState {

    private static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    private static final VoxelShape TOP_AABB = Block.box(2.0D, 10.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public MoonlightForge(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(MoonlightForge::new);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(BOTTOM_AABB, TOP_AABB);
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.getBlockEntity(pos) instanceof MoonlightForgeBlockEntity blockEntity && blockEntity.isCrafting()) {
            double d0 = (double) pos.getX() + random.nextDouble();
            double d1 = (double) pos.getY() + 4.2D;
            double d2 = (double) pos.getZ() + random.nextDouble();
            level.addParticle(ParticleTypes.FIREWORK, d0, d1, d2, 0.0D, -0.1D, 0.0D);
        }
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof MoonlightForgeBlockEntity blockEntity) {
            blockEntity.updateClient();
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu(state.getMenuProvider(level, pos), extraData -> extraData.writeBlockPos(pos));
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MoonlightForgeBlockEntity moonlightForge) {
                Containers.dropContents(level, pos, moonlightForge);
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MoonlightForgeBlockEntity(pos, state);
    }

    @Nullable @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, TABlockEntityTypes.MOONLIGHT_FORGE.get(), MoonlightForgeBlockEntity::serverTick);
    }

}