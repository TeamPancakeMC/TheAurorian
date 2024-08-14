package cn.teampancake.theaurorian.common.entities.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class FlowerLeech extends Monster {

    public FlowerLeech(EntityType<? extends FlowerLeech> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, 20.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 3.0D);
        builder.add(Attributes.FOLLOW_RANGE, 40.0F);
        return builder;
    }

    public static boolean checkSpawnRules(EntityType<FlowerLeech> flowerLeech, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        boolean flag = checkAnyLightMonsterSpawnRules(flowerLeech, level, spawnType, pos, random);
        return level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER) && flag;
    }

}