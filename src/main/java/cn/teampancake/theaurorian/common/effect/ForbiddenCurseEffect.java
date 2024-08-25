package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
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

    private static final DataComponentType<ItemEnchantments> FORBIDDEN_CURSE = TADataComponents.FORBIDDEN_CURSE.get();

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

    private static void processPlayerInventoryItems(Player player, Consumer<NonNullList<ItemStack>> processItems) {
        Inventory inventory = player.getInventory();
        processItems.accept(inventory.armor);
        processItems.accept(inventory.offhand);
        processItems.accept(inventory.items);
    }

    private static void removePlayerInventoryItemEnchantments(Player player) {
        processPlayerInventoryItems(player, ForbiddenCurseEffect::removeItemEnchantments);
    }

    public static void restorePlayerInventoryItemEnchantments(Player player) {
        processPlayerInventoryItems(player, ForbiddenCurseEffect::restoreItemEnchantments);
    }

    private static void removeItemEnchantments(List<ItemStack> itemStackList) {
        itemStackList.stream().filter(ItemStack::isEnchanted).forEach(itemStack -> {
            itemStack.set(FORBIDDEN_CURSE, itemStack.getTagEnchantments());
            itemStack.remove(DataComponents.ENCHANTMENTS);
        });
    }

    private static void restoreItemEnchantments(List<ItemStack> itemStackList) {
        itemStackList.stream().filter(stack -> stack.has(FORBIDDEN_CURSE)).forEach(stack -> {
            ItemEnchantments forbiddenCurses = stack.get(FORBIDDEN_CURSE);
            if (forbiddenCurses != null) {
                stack.set(DataComponents.ENCHANTMENTS, forbiddenCurses);
                stack.remove(FORBIDDEN_CURSE);
            }
        });
    }

}