package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.config.AurorianConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class AbsorptionOrbItem extends Item implements ITooltipsItem {

    public AbsorptionOrbItem(){
        super(new Properties().rarity(Rarity.EPIC).defaultDurability(250));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof ServerPlayer player) {
            ItemStack offHandItem = player.getItemInHand(InteractionHand.OFF_HAND);
            ItemStack mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (offHandItem.getItem() == this && mainHandItem.getItem() != this && mainHandItem.isDamageableItem()) {
                List<? extends String> list = AurorianConfig.Config_OrbOfAbsorptionList.get();
                Consumer<LivingEntity> onBroken = p -> p.broadcastBreakEvent(EquipmentSlot.OFFHAND);
                if (!list.contains(BuiltInRegistries.ITEM.getKey(mainHandItem.getItem()).toString())) {
                    int damage = mainHandItem.getDamageValue();
                    if (damage < mainHandItem.getMaxDamage()) {
                        mainHandItem.setDamageValue(damage - 1);
                        if (!player.getAbilities().instabuild) {
                            offHandItem.hurtAndBreak(1, player, onBroken);
                        }
                    }
                }
            }
        }
    }

}