package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.common.level.damagesource.CorruptionDamage;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TADamageTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

public class CorruptionEffect extends IncurableEffect {

    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public CorruptionEffect() {
        super(MobEffectCategory.HARMFUL, 0x570e20);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity cameraEntity = minecraft.getCameraEntity();
        if (cameraEntity instanceof LocalPlayer player) {
            player.hurtTime = 0;
        }

        return true;
    }

    public static void doHurtTarget(LivingEntity entity) {
        RegistryAccess registry = entity.level().registryAccess();
        Holder<DamageType> holder = registry.holderOrThrow(TADamageTypes.CORRUPTION);
        CorruptionDamage corruptionDamage = new CorruptionDamage(holder);
        AttachmentType<Float> type1 = TAAttachmentTypes.DAMAGE_ACCUMULATION.get();
        AttachmentType<Float> type2 = TAAttachmentTypes.ARMOR_HURT_ACCUMULATION.get();
        AttachmentType<Float> type3 = TAAttachmentTypes.EXHAUSTION_ACCUMULATION.get();
        entity.hurt(corruptionDamage, entity.getData(type1));
        entity.setData(type1, 0.0F);
        if (entity instanceof Player player) {
            float armorHurt = entity.getData(type2);
            player.doHurtEquipment(corruptionDamage, armorHurt, ARMOR_SLOTS);
            player.causeFoodExhaustion(player.getData(type3));
            player.setData(type2, 0.0F);
            player.setData(type3, 0.0F);
        }
    }

}