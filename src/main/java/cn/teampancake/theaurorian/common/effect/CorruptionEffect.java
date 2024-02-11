package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.common.registry.TAAttributes;
import cn.teampancake.theaurorian.common.registry.TADamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;

public class CorruptionEffect extends IncurableEffect {

    public CorruptionEffect() {
        super(MobEffectCategory.HARMFUL, 0x570e20);
    }

    public void entityCorruptionEffect(LivingEntity entity) {
        DamageSource source = entity.damageSources().source(TADamageTypes.CORRUPTION);
        Attribute da = TAAttributes.DAMAGE_ACCUMULATION.get();
        Attribute ea = TAAttributes.EXHAUSTION_ACCUMULATION.get();
        Attribute aa = TAAttributes.ARMOR_HURT_ACCUMULATION.get();
        AttributeInstance daInstance = entity.getAttribute(da);
        AttributeInstance eaInstance = entity.getAttribute(ea);
        AttributeInstance aaInstance = entity.getAttribute(aa);
        float i = (float) entity.getAttributeValue(da);
        float j = (float) entity.getAttributeValue(aa);
        float k = entity.getAbsorptionAmount();
        if (i > 0.0F && daInstance != null) {
            entity.getCombatTracker().recordDamage(source, i);
            entity.setHealth(entity.getHealth() - i);
            entity.setAbsorptionAmount(k - i);
            entity.gameEvent(GameEvent.ENTITY_DAMAGE);
            daInstance.setBaseValue(0.0D);
            if (entity instanceof Player player && eaInstance != null && aaInstance != null) {
                player.causeFoodExhaustion((float) player.getAttributeValue(ea));
                player.getInventory().hurtArmor(source, j, Inventory.ALL_ARMOR_SLOTS);
                eaInstance.setBaseValue(0.0D);
                aaInstance.setBaseValue(0.0D);
            }
        }
    }

}