package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Spiderling extends Spider {

    public Spiderling(EntityType<? extends Spiderling> type, Level level) {
        super(type, level);
        this.xpReward = 10;
    }

    public static boolean checkSpiderlingSpawnRules(EntityType<Spiderling> spiderling, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(TABlocks.DARK_STONE_BRICKS.get()) && checkAnyLightMonsterSpawnRules(spiderling, level, spawnType, pos, random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Spider.createAttributes().add(Attributes.MAX_HEALTH, 7.0D);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return 0.45F;
    }

}