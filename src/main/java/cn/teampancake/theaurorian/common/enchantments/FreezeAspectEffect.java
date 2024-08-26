package cn.teampancake.theaurorian.common.enchantments;

import cn.teampancake.theaurorian.common.items.armor.MysteriumWoolArmor;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record FreezeAspectEffect(int placeholder) implements EnchantmentEntityEffect {

    public static final MapCodec<FreezeAspectEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("placeholder").forGetter(FreezeAspectEffect::placeholder)).apply(instance, FreezeAspectEffect::new));

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (entity instanceof LivingEntity livingEntity && MysteriumWoolArmor.isWearFullArmor(livingEntity)) {
            livingEntity.addEffect(new MobEffectInstance(TAMobEffects.FROSTBITE, (100 + enchantmentLevel * 20), enchantmentLevel));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}