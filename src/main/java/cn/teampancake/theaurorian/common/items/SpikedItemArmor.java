package cn.teampancake.theaurorian.common.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Map;

import static net.minecraft.world.item.ArmorItem.Type.CHESTPLATE;

public class SpikedItemArmor extends ArmorItem implements ITooltipsItem{

    public SpikedItemArmor() {
        super(ModArmorMaterials.SPECTRAL, CHESTPLATE, new Item.Properties());
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player){
            if (player.isShiftKeyDown()) {
                player.getArmorSlots().forEach(stack -> {
                    if (stack.getItem() instanceof SpikedItemArmor) {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10));
                        stack.enchant(Enchantments.THORNS, 3);
                    }
                });
            }else {
                player.getArmorSlots().forEach(stack -> {
                    if (stack.getItem() instanceof SpikedItemArmor) {
                        Map<Enchantment, Integer> ench = EnchantmentHelper.getEnchantments(stack);
                        ench.remove(Enchantments.THORNS);
                        EnchantmentHelper.setEnchantments(ench, stack);
                    }
                });
            }
        }
    }
}