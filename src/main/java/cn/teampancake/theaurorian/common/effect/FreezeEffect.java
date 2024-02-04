package cn.teampancake.theaurorian.common.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class FreezeEffect extends TAMobEffect {

    public static final String UUID = "9e74f30c-c40a-4206-aa6c-5ee1f3cbe333";

    public FreezeEffect() {
        super(MobEffectCategory.HARMFUL, 0xa7c6ff);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID, (-1), AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.level();
        if (!level.isClientSide) {
            livingEntity.setTicksFrozen(140);
            livingEntity.setSharedFlagOnFire(false);
            livingEntity.hurt(livingEntity.damageSources().freeze(), amplifier);
        } else {
            RandomSource random = level.getRandom();
            if ((livingEntity.xOld != livingEntity.getX() || livingEntity.zOld != livingEntity.getZ()) && random.nextBoolean()) {
                double y = livingEntity.getY() + 1.0D;
                double xSpeed = Mth.randomBetween(random, (-1.0F), (1.0F)) * 0.083333336F;
                double zSpeed = Mth.randomBetween(random, (-1.0F), (1.0F)) * 0.083333336F;
                level.addParticle(ParticleTypes.SNOWFLAKE, livingEntity.getX(), y, livingEntity.getZ(), xSpeed, 0.05F, zSpeed);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int i = 40 >> amplifier;
        return i <= 0 || duration % i == 0;
    }

}