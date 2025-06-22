package cn.teampancake.theaurorian.common.level.alchemy;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class PotionConflictResolver {

    public static ConflictResolution resolveConflict(MobEffectInstance effect1, MobEffectInstance effect2) {
        EffectType type1 = getEffectType(effect1.getEffect());
        EffectType type2 = getEffectType(effect2.getEffect());
        float strength1 = calculateEffectStrength(effect1, type1);
        float strength2 = calculateEffectStrength(effect2, type2);
        boolean effect1Dominant = strength1 >= strength2;
        MobEffectInstance dominant = effect1Dominant ? effect1 : effect2;
        MobEffectInstance recessive = effect1Dominant ? effect2 : effect1;
        return performPreciseCancellation(dominant, recessive, type1, type2);
    }

    private static ConflictResolution performPreciseCancellation(
            MobEffectInstance dominant, MobEffectInstance recessive,
            EffectType typeD, EffectType typeR) {
        MobEffectInstance result = new MobEffectInstance(dominant);
        int cancelAmount = 0;
        if (typeD == EffectType.FULL && typeR == EffectType.FULL) {
            if (dominant.getAmplifier() == recessive.getAmplifier()) {
                cancelAmount = Math.min(dominant.getDuration(), recessive.getDuration());
            } else {
                float ratio = (recessive.getAmplifier() + 1f) / (dominant.getAmplifier() + 1f);
                cancelAmount = (int)(recessive.getDuration() * ratio);
            }

            result.duration = dominant.getDuration() - cancelAmount;
        } else if (typeD == EffectType.INSTANTANEOUS) {
            if (typeR == EffectType.INSTANTANEOUS) {
                return new ConflictResolution(null, recessive.getDuration());
            } else {
                cancelAmount = recessive.getDuration() / 2;
            }
        } else if (typeD == EffectType.DURATION_ONLY) {
            cancelAmount = Math.min(dominant.getDuration(), recessive.getDuration());
            result.duration = dominant.getDuration() - cancelAmount;
        }

        if (result.duration <= 0) {
            return new ConflictResolution(null, cancelAmount);
        }

        return new ConflictResolution(result, cancelAmount);
    }

    private static float calculateEffectStrength(MobEffectInstance effect, EffectType type) {
        return switch (type) {
            case INSTANTANEOUS -> (effect.getAmplifier() + 1) * 1000f;
            case DURATION_ONLY -> effect.getDuration() / 20f;
            case FULL -> (effect.getAmplifier() + 1) * (effect.getDuration() / 20f);
        };
    }

    private static EffectType getEffectType(Holder<MobEffect> effect) {
        MobEffect mobEffect = effect.value();
        boolean flag1 = mobEffect.getClass() == MobEffect.class;
        boolean flag2 = mobEffect.attributeModifiers.isEmpty();
        if (mobEffect.isInstantenous()) {
            return EffectType.INSTANTANEOUS;
        } else if (flag1 && flag2) {
            return EffectType.DURATION_ONLY;
        } else {
            return EffectType.FULL;
        }
    }

    public record ConflictResolution(MobEffectInstance remainingEffect, int cancelledAmount) { }

    private enum EffectType {
        INSTANTANEOUS,
        DURATION_ONLY,
        FULL
    }

}