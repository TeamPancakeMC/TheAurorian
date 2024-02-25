package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.common.registry.TACapability;
import cn.teampancake.theaurorian.common.registry.TADamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;

public class CorruptionEffect extends IncurableEffect {

    public CorruptionEffect() {
        super(MobEffectCategory.HARMFUL, 0x570e20);
    }

    public void doHurtTarget(LivingEntity entity) {
        DamageSource source = entity.damageSources().source(TADamageTypes.CORRUPTION);
        entity.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
            float damage = miscNBT.getDamageAccumulation();
            float l = entity.getAbsorptionAmount();
            if (damage > 0.0F) {
                entity.getCombatTracker().recordDamage(source, damage);
                entity.setHealth(entity.getHealth() - damage);
                entity.setAbsorptionAmount(l - damage);
                entity.gameEvent(GameEvent.ENTITY_DAMAGE);
                miscNBT.setDamageAccumulation(0.0F);
            }
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