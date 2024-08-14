package cn.teampancake.theaurorian.common.entities;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.registry.TABiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.LevelAccessor;

public class TASpawnRules {

    public static boolean checkAnimalSpawnRules(EntityType<? extends PathfinderMob> mob, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && level.getRawBrightness(pos, 0) > 7;
    }

    public static boolean checkWaterAnimalSpawnRules(EntityType<? extends WaterAnimal> aurorianFish, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        boolean isRiverOrLake = level.getBiome(pos).is(TABiomes.AURORIAN_RIVER) || level.getBiome(pos).is(TABiomes.AURORIAN_LAKE);
        boolean flag = WaterAnimal.checkSurfaceWaterAnimalSpawnRules(aurorianFish, level, spawnType, pos, random);
        return (isRiverOrLake || flag);
    }

}