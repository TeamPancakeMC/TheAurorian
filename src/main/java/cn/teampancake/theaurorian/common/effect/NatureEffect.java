package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABiomeTags;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;

public class NatureEffect extends TAMobEffect {

    public NatureEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x003472);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Holder<Biome> biomes = livingEntity.level().getBiome(livingEntity.blockPosition());
        if (biomes.is(BiomeTags.IS_FOREST) || biomes.is(TABiomeTags.IS_AUARORIAN_FOREST)) {
            livingEntity.removeEffect(TAMobEffects.PRESSURE.get());
            if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
                livingEntity.heal(1.0F);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int k = 100 >> amplifier;
        if (k > 0) {
            return duration % k == 0;
        } else {
            return true;
        }
    }

    public static float getDamageAfterMagicAbsorb(LivingEntity livingEntity, DamageSource damageSource, float damageAmount) {
        if (damageSource.is(DamageTypeTags.BYPASSES_EFFECTS)) {
            return damageAmount;
        } else {
            if (livingEntity.hasEffect(TAMobEffects.NATURE.get()) && !damageSource.is(DamageTypeTags.BYPASSES_RESISTANCE)) {
                int i = (Objects.requireNonNull(livingEntity.getEffect(TAMobEffects.NATURE.get())).getAmplifier() + 1) * 5;
                int j = 50 - i;
                float f = damageAmount * (float)j;
                float f1 = damageAmount;
                damageAmount = Math.max(f / 50.0F, 0.0F);
                float f2 = f1 - damageAmount;
                if (f2 > 0.0F && f2 < 3.4028235E37F) {
                    if (livingEntity instanceof ServerPlayer serverPlayer) {
                        serverPlayer.awardStat(Stats.CUSTOM.get(Stats.DAMAGE_RESISTED), Math.round(f2 * 10.0F));
                    } else if (damageSource.getEntity() instanceof ServerPlayer serverPlayer) {
                        serverPlayer.awardStat(Stats.CUSTOM.get(Stats.DAMAGE_DEALT_RESISTED), Math.round(f2 * 10.0F));
                    }
                }
            }

            if (damageAmount <= 0.0F) {
                return 0.0F;
            } else if (damageSource.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
                return damageAmount;
            } else {
                int k = EnchantmentHelper.getDamageProtection(livingEntity.getArmorSlots(), damageSource);
                if (k > 0) {
                    damageAmount = CombatRules.getDamageAfterMagicAbsorb(damageAmount, (float)k);
                }

                return damageAmount;
            }
        }
    }

}