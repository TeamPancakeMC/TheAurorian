package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AurorianFurnaceChimney extends Block {

    public AurorianFurnaceChimney(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isEmptyBlock(pos.above())) {
            double dy = (random.nextDouble() * 6.0D / 16.0D);
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + 0.5D + dy;
            double d2 = (double) pos.getZ() + 0.5D;
            level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }

        if (!state.canSurvive(level,pos)){
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(this) || level.getBlockState(pos.below()).is(TABlocks.AURORIAN_FURNACE.get());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction pDirection, BlockState pNeighborState, LevelAccessor level, BlockPos pos, BlockPos pNeighborPos) {
        if (!state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }

        return state;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if(!player.getItemInHand(player.getUsedItemHand()).is(this.asItem())) {
            return InteractionResult.PASS;
        }

        int y = 1;
        while (level.getBlockState(pos.above(y)).is(this)){
            y++;
        }

        if(y >= AurorianConfig.CONFIG_MAXIMUM_CHIMNEYS.get()) {
            return InteractionResult.PASS;
        }

        BlockPos newPos = pos.above(y);

        int i = 1;
        while (level.getBlockState(pos.below(i)).is(this)) {
            i++;
        }

        if ((i + y) >= AurorianConfig.CONFIG_MAXIMUM_CHIMNEYS.get()) {
            return InteractionResult.PASS;
        }

        if (level.isEmptyBlock(newPos)){
            if (!player.getAbilities().instabuild) {
                player.getItemInHand(player.getUsedItemHand()).shrink(1);
            }

            level.setBlockAndUpdate(newPos,this.defaultBlockState());
            BlockState newState = level.getBlockState(newPos);
            SoundType soundtype = newState.getSoundType(level, newPos, player);
            float volume = (soundtype.getVolume() + 1.0F) / 2.0F;
            float pitch = soundtype.getPitch() * 0.8F;
            level.playSound(null, newPos, soundtype.getPlaceSound(), SoundSource.BLOCKS, volume, pitch);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

}