package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TADamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

public class CorruptionEffect extends IncurableEffect {

    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public CorruptionEffect() {
        super(MobEffectCategory.HARMFUL, 0x570e20);
    }

    public void doHurtTarget(LivingEntity entity) {
        AttachmentType<Float> type1 = TAAttachmentTypes.DAMAGE_ACCUMULATION.get();
        AttachmentType<Float> type2 = TAAttachmentTypes.ARMOR_HURT_ACCUMULATION.get();
        AttachmentType<Float> type3 = TAAttachmentTypes.EXHAUSTION_ACCUMULATION.get();
        DamageSource source = entity.damageSources().source(TADamageTypes.CORRUPTION);
        entity.hurt(source, entity.getData(type1));
        entity.setData(type1, 0.0F);
        entity.setData(TAAttachmentTypes.CORRUPTION_TIME, 0);
        entity.setData(TAAttachmentTypes.VALID_CORRUPTION_TIME, 0);
        if (entity instanceof Player player) {
            float armorHurt = entity.getData(type2);
            player.doHurtEquipment(source, armorHurt, ARMOR_SLOTS);
            player.causeFoodExhaustion(player.getData(type3));
            player.setData(type2, 0.0F);
            player.setData(type3, 0.0F);
        }
    }

}