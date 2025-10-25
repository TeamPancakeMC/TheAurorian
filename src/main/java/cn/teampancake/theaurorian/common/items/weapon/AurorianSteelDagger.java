package cn.teampancake.theaurorian.common.items.weapon;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import cn.teampancake.theaurorian.common.registry.TAToolTiers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;

public class AurorianSteelDagger extends SwordItem {

    public AurorianSteelDagger() {
        super(TAToolTiers.AURORIAN_STEEL, new Properties()
                .durability(500).attributes(createAttributes())
                .component(TADataComponents.ITEM_TAGS, List.of(ItemTags.SWORDS))
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC));
    }

    private static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 2.0F,
                        AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -1.6F,
                        AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide() && attacker instanceof Player player && target.isAlive()) {
            CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            String targetId = target.getStringUUID();
            CompoundTag tag = customData.copyTag();
            CompoundTag damageData = tag.getCompound("dagger_damage_data");
            int currentCount = damageData.getInt(targetId);
            long lastAttackTime = damageData.getLong(targetId + "_time");
            long currentTime = attacker.level().getGameTime();
            if (lastAttackTime == 0 || (currentTime - lastAttackTime) > 40L) {
                currentCount = 1;
            } else {
                currentCount++;
            }

            damageData.putInt(targetId, currentCount);
            damageData.putLong(targetId + "_time", currentTime);
            tag.put("dagger_damage_data", damageData);
            if (currentCount > 1) {
                target.hurt(target.damageSources().playerAttack(player), currentCount - 1);
            }
        }
        
        return super.hurtEnemy(stack, target, attacker);
    }
} 