package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;

public class AurorianFarmTile extends FarmBlock {

    public AurorianFarmTile(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(MOISTURE) > 0;
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        PlantType type = plantable.getPlantType(world, pos.relative(facing));
        if (PlantType.CROP.equals(type) || PlantType.PLAINS.equals(type)) {
            return state.is(TABlocks.AURORIAN_FARM_TILE.get());
        } else {
            return false;
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            turnToAurorianDirt(null, state, level, pos);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int i = state.getValue(MOISTURE);
        if (!isNearWater(level, pos) && !level.isRainingAt(pos.above())) {
            if (i > 0) {
                level.setBlock(pos, state.setValue(MOISTURE, i - 1), 2);
            } else if (!shouldMaintainFarmland(level, pos)) {
                turnToAurorianDirt(null, state, level, pos);
            }
        } else if (i < 7) {
            level.setBlock(pos, state.setValue(MOISTURE, 7), 2);
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!level.isClientSide && ForgeHooks.onFarmlandTrample(level, pos, Blocks.DIRT.defaultBlockState(), fallDistance, entity)) {
            turnToAurorianDirt(entity, state, level, pos);
        }

        entity.causeFallDamage(fallDistance, 1.0F, entity.damageSources().fall());
    }

    private static void turnToAurorianDirt(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        BlockState blockState = pushEntitiesUp(state, TABlocks.AURORIAN_DIRT.get().defaultBlockState(), level, pos);
        level.setBlockAndUpdate(pos, blockState);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockState));
    }

}