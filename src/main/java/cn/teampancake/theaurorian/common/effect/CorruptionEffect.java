package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.common.registry.TACapability;
import cn.teampancake.theaurorian.common.registry.TADamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class CorruptionEffect extends IncurableEffect {

    public CorruptionEffect() {
        super(MobEffectCategory.HARMFUL, 0x570e20);
    }

    public void doHurtTarget(LivingEntity entity) {
        DamageSource source = entity.damageSources().source(TADamageTypes.CORRUPTION);
        entity.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
            entity.hurt(source, miscNBT.getDamageAccumulation());
            miscNBT.setDamageAccumulation(0.0F);
        });

        if (entity instanceof Player player) {
            player.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
                float armorHurt = miscNBT.getArmorHurtAccumulation();
                player.getInventory().hurtArmor(source, armorHurt, Inventory.ALL_ARMOR_SLOTS);
                player.causeFoodExhaustion(miscNBT.getExhaustionAccumulation());
                miscNBT.setExhaustionAccumulation(0.0F);
                miscNBT.setArmorHurtAccumulation(0.0F);
            });
        }
    }

}