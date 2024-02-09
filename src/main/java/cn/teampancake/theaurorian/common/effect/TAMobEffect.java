package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAMobEffectTags;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("deprecation")
public class TAMobEffect extends MobEffect {

    public TAMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public static Set<MobEffect> getMoonQueenOnlyEffects() {
        Set<MobEffect> effects = new HashSet<>();
        BuiltInRegistries.MOB_EFFECT
                .getTagOrEmpty(TAMobEffectTags.MOON_QUEEN_ONLY)
                .forEach(holder -> effects.add(holder.get()));
        return effects;
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