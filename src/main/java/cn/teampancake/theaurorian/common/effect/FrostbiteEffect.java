package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.common.items.armor.MysteriumWoolArmor;
import cn.teampancake.theaurorian.common.registry.TACapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class FrostbiteEffect extends IncurableEffect {

    public static final String UUID = "9e74f30c-c40a-4206-aa6c-5ee1f3cbe333";

    public FrostbiteEffect() {
        super(MobEffectCategory.HARMFUL, 0xa7c6ff);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID,
                (-1), AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.level();
        if (!level.isClientSide) {
            livingEntity.setSharedFlagOnFire(false);
            livingEntity.getCapability(TACapability.MISC_CAP)
                    .ifPresent(miscNBT -> miscNBT.setTicksFrostbite(140));
            if (!MysteriumWoolArmor.isWearFullArmor(livingEntity)) {
                livingEntity.hurt(livingEntity.damageSources().freeze(), (amplifier + 1.0F));
            }
        } else {
            RandomSource random = level.getRandom();
            if ((livingEntity.xOld != livingEntity.getX() || livingEntity.zOld != livingEntity.getZ())) {
                double y = livingEntity.getY() + 1.0D;
                double xSpeed = Mth.randomBetween(random, (-1.0F), (1.0F)) * 0.083333336F;
                double zSpeed = Mth.randomBetween(random, (-1.0F), (1.0F)) * 0.083333336F;
                level.addParticle(ParticleTypes.SNOWFLAKE, livingEntity.getX(), y, livingEntity.getZ(), xSpeed, 0.05F, zSpeed);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}