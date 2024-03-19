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
            entity.hurt(source, miscNBT.damageAccumulation);
            miscNBT.damageAccumulation = 0.0F;
            miscNBT.corruptionTime = 0;
            miscNBT.validCorruptionTime = 0;
        });

        if (entity instanceof Player player) {
            player.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
                float armorHurt = miscNBT.armorHurtAccumulation;
                player.getInventory().hurtArmor(source, armorHurt, Inventory.ALL_ARMOR_SLOTS);
                player.causeFoodExhaustion(miscNBT.exhaustionAccumulation);
                miscNBT.exhaustionAccumulation = 0.0F;
                miscNBT.armorHurtAccumulation = 0.0F;
            });
        }
    }

}