package cn.teampancake.theaurorian.common.enchantments;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

public record ExperienceOreEffect(Unit unit) implements EnchantmentValueEffect {

    public static final MapCodec<ExperienceOreEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Unit.CODEC.fieldOf("unit").forGetter(ExperienceOreEffect::unit)).apply(instance, ExperienceOreEffect::new));

    @Override
    public float process(int enchantmentLevel, RandomSource random, float value) {
        return (Math.max(0, random.nextInt(enchantmentLevel + 2) - 1) + 1) * value;
    }

    @Override
    public MapCodec<? extends EnchantmentValueEffect> codec() {
        return CODEC;
    }

}