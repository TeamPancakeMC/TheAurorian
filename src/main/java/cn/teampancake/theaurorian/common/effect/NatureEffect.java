package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABiomeTags;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;

public class NatureEffect extends TAMobEffect {

    public NatureEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x003472);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Holder<Biome> biomes = livingEntity.level().getBiome(livingEntity.blockPosition());
        if (biomes.is(BiomeTags.IS_FOREST) || biomes.is(TABiomeTags.IS_AUARORIAN_FOREST)) {
            livingEntity.removeEffect(TAMobEffects.PRESSURE);
            if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
                livingEntity.heal(1.0F);
            }
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int k = 100 >> amplifier;
        if (k > 0) {
            return duration % k == 0;
        } else {
            return true;
        }
    }

}