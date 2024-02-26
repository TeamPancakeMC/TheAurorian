package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class ForgottenMagicBook extends Monster {

    public ForgottenMagicBook(EntityType<? extends ForgottenMagicBook> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, 28.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 4.0D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.2D);
        builder.add(Attributes.FOLLOW_RANGE, 40.0F);
        return builder;
    }

    public static boolean checkSpawnRules(EntityType<ForgottenMagicBook> forgottenMagicBook, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(TABlockTags.AURORIAN_GRASS_BLOCK) && checkAnyLightMonsterSpawnRules(forgottenMagicBook, level, spawnType, pos, random);
    }

}