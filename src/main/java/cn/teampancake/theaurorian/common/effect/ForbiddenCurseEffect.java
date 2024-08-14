package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.List;
import java.util.function.Consumer;

public class ForbiddenCurseEffect extends TAMobEffect {

    public ForbiddenCurseEffect() {
        super(MobEffectCategory.HARMFUL, 0x4b6584);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            removePlayerInventoryItemEnchantments(player);
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    public void processPlayerInventoryItems(Player player, Consumer<NonNullList<ItemStack>> processItems) {
        Inventory inventory = player.getInventory();
        processItems.accept(inventory.armor);
        processItems.accept(inventory.offhand);
        processItems.accept(inventory.items);
    }

    public void removePlayerInventoryItemEnchantments(Player player) {
        processPlayerInventoryItems(player, this::removeItemEnchantments);
    }

    public void restorePlayerInventoryItemEnchantments(Player player) {
        processPlayerInventoryItems(player, this::restoreItemEnchantments);
    }

    public void removeItemEnchantments(List<ItemStack> itemStackList) {
        itemStackList.stream().filter(ItemStack::isEnchanted).forEach(itemStack -> {
            DataComponentMap components = itemStack.getComponents();
            ItemEnchantments enchantments = components.get(DataComponents.ENCHANTMENTS);
            if (components.has(TADataComponents.FORBIDDEN_CURSE.get())) {

            }
        });
    }

    public void restoreItemEnchantments(List<ItemStack> itemStackList) {

    }

}