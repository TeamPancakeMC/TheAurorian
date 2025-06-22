package cn.teampancake.theaurorian.common.level.alchemy;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class PotionConflictResolver {

    public static ConflictResult resolveConflict(MobEffectInstance effect1, MobEffectInstance effect2) {
        EffectType type1 = getEffectType(effect1.getEffect());
        EffectType type2 = getEffectType(effect2.getEffect());
        int effectiveLevel1 = (type1 == EffectType.DURATION_ONLY) ? 1 : effect1.getAmplifier() + 1;
        int effectiveLevel2 = (type2 == EffectType.DURATION_ONLY) ? 1 : effect2.getAmplifier() + 1;
        int duration1 = (type1 == EffectType.INSTANTANEOUS) ? Integer.MAX_VALUE : effect1.getDuration();
        int duration2 = (type2 == EffectType.INSTANTANEOUS) ? Integer.MAX_VALUE : effect2.getDuration();
        float strength1 = calculateEffectStrength(effectiveLevel1, duration1, type1);
        float strength2 = calculateEffectStrength(effectiveLevel2, duration2, type2);
        MobEffectInstance primaryEffect = strength1 >= strength2 ? effect1 : effect2;
        MobEffectInstance secondaryEffect = strength1 >= strength2 ? effect2 : effect1;
        boolean bothAreFullEffects = type1 == EffectType.FULL && type2 == EffectType.FULL;
        return calculateResult(primaryEffect, secondaryEffect, bothAreFullEffects);
    }

    private static ConflictResult calculateResult(MobEffectInstance primary, MobEffectInstance secondary, boolean bothAreFullEffects) {
        MobEffectInstance result = new MobEffectInstance(primary);
        if (bothAreFullEffects) {
            int levelDiff = primary.getAmplifier() - secondary.getAmplifier();
            if (levelDiff == 0) {
                result.duration = Math.min(primary.getDuration(), secondary.getDuration());
            } else if (levelDiff > 0) {
                float reductionRatio = 1f - (1f / (levelDiff + 1));
                result.duration = (int)(primary.getDuration() * reductionRatio);
            } else {
                result.duration = primary.getDuration() / (Math.abs(levelDiff) + 1);
            }
        } else {
            EffectType primaryType = getEffectType(primary.getEffect());
            EffectType secondaryType = getEffectType(secondary.getEffect());
            if (primaryType == EffectType.INSTANTANEOUS || secondaryType == EffectType.INSTANTANEOUS) {
                if (primaryType == EffectType.INSTANTANEOUS && secondaryType == EffectType.INSTANTANEOUS) {
                    return new ConflictResult(null, Boolean.TRUE);
                } else if (primaryType == EffectType.INSTANTANEOUS) {
                    result.duration = secondary.getDuration() / 2;
                } else {
                    result.duration = primary.getDuration() / 2;
                }
            } else {
                result.duration = Math.min(primary.getDuration(), secondary.getDuration());
            }
        }

        result.duration = Math.max(0, result.duration);
        return new ConflictResult(result.duration > 0 ? result : null, result.duration == 0);
    }

    private static float calculateEffectStrength(int level, int duration, EffectType type) {
        return switch (type) {
            case INSTANTANEOUS -> level * 1000f;
            case DURATION_ONLY -> duration / 20f;
            case FULL -> (level * level) * (duration / 20f);
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

    public record ConflictResult(MobEffectInstance remainingEffect, boolean fullyCancelled) { }

    private enum EffectType {
        INSTANTANEOUS,
        DURATION_ONLY,
        FULL
    }

}