package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.config.TACommonConfig;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;

public class AbsorptionOrb extends Item {

    public AbsorptionOrb(Item.Properties properties) {
        super(properties.rarity(Rarity.EPIC).durability(250)
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(Enchantments.UNBREAKING) || enchantment.is(Enchantments.MENDING) || super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof ServerPlayer player) {
            ItemStack offHandItem = player.getItemInHand(InteractionHand.OFF_HAND);
            ItemStack mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (offHandItem.getItem() == this && mainHandItem.getItem() != this && mainHandItem.isDamageableItem()) {
                List<? extends String> list = TACommonConfig.Config_OrbOfAbsorptionList.get();
                if (!list.contains(BuiltInRegistries.ITEM.getKey(mainHandItem.getItem()).toString())) {
                    int damage = mainHandItem.getDamageValue();
                    if (damage < mainHandItem.getMaxDamage()) {
                        mainHandItem.setDamageValue(damage - 1);
                        if (!player.getAbilities().instabuild) {
                            offHandItem.hurtAndBreak(1, player, EquipmentSlot.OFFHAND);
                        }
                    }
                }
            }
        }
    }

}