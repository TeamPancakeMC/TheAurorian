package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.common.registry.TABiomes;
import cn.teampancake.theaurorian.common.registry.TAFluidTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

public abstract class AbstractAurorianFish extends AbstractFish {

    public AbstractAurorianFish(EntityType<? extends AbstractAurorianFish> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return type != TAFluidTypes.MOON_WATER.get() || type != ForgeMod.WATER_TYPE.get();
    }

    @Override
    public boolean canSwimInFluidType(FluidType type) {
        return type == TAFluidTypes.MOON_WATER.get() || super.canSwimInFluidType(type);
    }

    public static boolean checkAbstractAurorianFishSpawnRules(EntityType<? extends AbstractAurorianFish> aurorianFish, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        boolean isRiverOrLake = level.getBiome(pos).is(TABiomes.AURORIAN_RIVER) || level.getBiome(pos).is(TABiomes.AURORIAN_LAKE);
        boolean flag = WaterAnimal.checkSurfaceWaterAnimalSpawnRules(aurorianFish, level, spawnType, pos, random);
        return level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER) && (isRiverOrLake || flag);
    }

}