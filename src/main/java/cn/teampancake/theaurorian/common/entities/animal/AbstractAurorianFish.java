package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAFluidTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.fluids.FluidType;

@SuppressWarnings("unused")
public abstract class AbstractAurorianFish extends AbstractFish {

    public AbstractAurorianFish(EntityType<? extends AbstractAurorianFish> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        if (type == TAFluidTypes.MOON_WATER.get()) {
            return false;
        }

        return super.canDrownInFluidType(type);
    }

    @Override
    public boolean canSwimInFluidType(FluidType type) {
        return type == TAFluidTypes.MOON_WATER.get() || super.canDrownInFluidType(type);
    }

    public static boolean checkAbstractAurorianFishSpawnRules(EntityType<? extends AbstractFish> abstractFish, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(TABlocks.MOON_WATER.get());
    }

}