package cn.teampancake.theaurorian.common.enchantments;

import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;

public record MoltenCoreEffect(int placeholder) implements EnchantmentEntityEffect {

    public static final MapCodec<MoltenCoreEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("placeholder").forGetter(MoltenCoreEffect::placeholder)).apply(instance, MoltenCoreEffect::new));

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (entity instanceof LivingEntity) {
            AttachmentType<Integer> type = TAAttachmentTypes.TICKS_THERMAL_ENHANCEMENT.get();
            boolean isInNether = level.dimension() == Level.NETHER;
            boolean flag = entity.isOnFire() || entity.isInLava() || isInNether;
            entity.setData(type, flag ? 80 : Math.max(entity.getData(type) - 1, 0));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}