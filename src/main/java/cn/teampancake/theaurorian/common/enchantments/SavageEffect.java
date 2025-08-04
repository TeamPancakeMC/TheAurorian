package cn.teampancake.theaurorian.common.enchantments;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;

public record SavageEffect(Unit unit) implements EnchantmentEntityEffect {

    public static final MapCodec<SavageEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Unit.CODEC.fieldOf("unit").forGetter(SavageEffect::unit)).apply(instance, SavageEffect::new));

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        Collection<ItemEntity> drops = entity.captureDrops();
        if (drops != null && !drops.isEmpty() && level.random.nextFloat() <= enchantmentLevel * 0.1F) {
            drops.forEach(itemEntity -> entity.level().addFreshEntity(itemEntity));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}