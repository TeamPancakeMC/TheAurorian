package cn.teampancake.theaurorian.common.level.alchemy;

import cn.teampancake.theaurorian.common.components.AlchemyProduct;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PotionDecaySystem {

    public static void applyDecay(AlchemyProduct alchemyProduct, List<MobEffectInstance> effectInstances, @Nullable Player player, Level level) {
        alchemyProduct.age++;
        long currentTick = level.getGameTime();
        double ageFactor = calculateAgeFactor(alchemyProduct.age);
        if (alchemyProduct.lastDecay == currentTick - 1) return;
        RandomSource random = RandomSource.create();
        effectInstances.forEach(effect -> {
            double baseChance = calculateBaseDecayChance(effect);
            double currentChance = baseChance * ageFactor;
            if (player != null && !level.isClientSide()) {
                if (player.attributes.hasAttribute(Attributes.LUCK)) {
                    double luck = player.attributes.getValue(Attributes.LUCK);
                    currentChance *= (1.0D - luck * 0.01D);
                }
            }

            if (random.nextFloat() < currentChance) {
                int amplifier = effect.getAmplifier();
                alchemyProduct.lastDecay = currentTick;
                if (amplifier == 0 || amplifier > 0 && random.nextFloat() < 0.3F) {
                    int ampDecay = calculateAmplifierDecay(effect, random);
                    effect.amplifier = Math.max(0, amplifier - ampDecay);
                } else {
                    int durDecay = calculateDurationDecay(effect, random);
                    effect.duration = Math.max(0, effect.getDuration() - durDecay);
                }
            }
        });
    }

    public static void onPotionMixed(MobEffectInstance baseEffect, MobEffectInstance addedEffect) {
        int newDuration = safeAddDurations(baseEffect.getDuration(), addedEffect.getDuration());
        float complexity = calculateMixComplexity(baseEffect, addedEffect);
        newDuration = (int)(newDuration * (1 - 0.1f * complexity));
        baseEffect.duration = Math.min(newDuration, 1000000);
    }

    private static double calculateBaseDecayChance(MobEffectInstance effect) {
        double baseChance = 0.01f;
        if (effect.getAmplifier() > 0) {
            baseChance *= 1.0f / (1.0f + effect.getAmplifier() * 0.5f);
        }

        baseChance *= 1.0f + (effect.getDuration() / 6000.0f);
        switch (effect.getEffect().value().getCategory()) {
            case BENEFICIAL: baseChance *= 1.2f; break;
            case HARMFUL: baseChance *= 0.8f; break;
        }

        return Math.min(baseChance, 0.3D);
    }

    private static double calculateAgeFactor(int ageInTicks) {
        float ageInDays = ageInTicks / 24000.0F;
        float k = 0.8f;
        float x0 = 3.0f;
        return 1.0f / (1.0f + Math.exp(-k * (ageInDays - x0)));
    }

    private static int calculateAmplifierDecay(MobEffectInstance effect, RandomSource random) {
        MobEffectCategory category = effect.getEffect().value().getCategory();
        int currentAmp = effect.getAmplifier();
        float normalizedAmp = (float)currentAmp / MobEffectInstance.MAX_AMPLIFIER;
        float decayRatio = 0.15f * (float)Math.pow(normalizedAmp, 0.6f);
        decayRatio *= 0.75f + random.nextFloat() * 0.5f;
        int decay = Math.max(1, (int)(currentAmp * decayRatio));
        if (category == MobEffectCategory.HARMFUL) {
            decay = (int)(decay * 0.7f);
        }

        return Math.min(decay, currentAmp);
    }

    private static int calculateDurationDecay(MobEffectInstance effect, RandomSource random) {
        if (effect.getDuration() > 72000) {
            return calculateLongDurationDecay(effect, random);
        }

        return calculateNormalDurationDecay(effect, random);
    }

    private static int calculateLongDurationDecay(MobEffectInstance effect, RandomSource random) {
        MobEffectCategory category = effect.getEffect().value().getCategory();
        int currentDuration = effect.getDuration();
        double logDuration = Math.log1p(currentDuration);
        double normalized = logDuration / Math.log1p(1000000);
        double decayRatio = 0.001 + 0.049 * Math.pow(normalized, 0.7);
        decayRatio *= 0.75 + random.nextDouble() * 0.5;
        double decayTicks = Math.expm1(logDuration * decayRatio);
        int minDecay = Math.min(1200, currentDuration / 100);
        int decay = Math.max(minDecay, (int)decayTicks);
        if (category == MobEffectCategory.HARMFUL) {
            decay = (int)(decay * 0.6);
        }

        return Math.min(decay, currentDuration - 1);
    }

    private static int calculateNormalDurationDecay(MobEffectInstance effect, RandomSource random) {
        MobEffectCategory category = effect.getEffect().value().getCategory();
        int currentDuration = effect.getDuration();
        float decayRatio;
        if (currentDuration <= 1200) {
            decayRatio = 0.05f + random.nextFloat() * 0.05f;
        } else if (currentDuration <= 6000) {
            float t = (currentDuration - 1200) / 4800f;
            decayRatio = 0.1f + t * 0.15f;
        } else if (currentDuration <= 24000) {
            float t = (currentDuration - 6000) / 18000f;
            decayRatio = 0.25f + t * 0.15f;
        } else {
            float normalized = (float)Math.log10(currentDuration / 24000.0F);
            decayRatio = 0.4f * (1f + normalized * 0.5f);
        }

        int decay = Math.max(20, (int)(currentDuration * decayRatio));
        if (category == MobEffectCategory.HARMFUL) {
            decay = (int)(decay * 0.6f);
        }

        return Math.min(decay, currentDuration - 1);
    }

    private static float calculateMixComplexity(MobEffectInstance eff1, MobEffectInstance eff2) {
        float complexity = 0f;
        if (!eff1.getEffect().equals(eff2.getEffect())) {
            complexity += 0.4f;
        }

        complexity += 0.3f * (eff1.getAmplifier() + eff2.getAmplifier()) / MobEffectInstance.MAX_AMPLIFIER;
        complexity += (float) (0.3f * Math.min(1, (eff1.getDuration() + eff2.getDuration()) / Math.log1p(1000000)));
        return Math.min(complexity, 1.0f);
    }

    private static int safeAddDurations(int a, int b) {
        long sum = (long)a + (long)b;
        return (int)Math.min(sum, 1000000);
    }

}