package cn.teampancake.theaurorian.common.enchantments;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

public record SunderArmorSlashEffect(Unit unit) implements EnchantmentValueEffect {

    public static final MapCodec<SunderArmorSlashEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Unit.CODEC.fieldOf("unit").forGetter(SunderArmorSlashEffect::unit)).apply(instance, SunderArmorSlashEffect::new));

    @Override
    public float process(int enchantmentLevel, RandomSource random, float value) {
        return value + enchantmentLevel * value * 0.1F;
    }

    @Override
    public MapCodec<? extends EnchantmentValueEffect> codec() {
        return CODEC;
    }

}