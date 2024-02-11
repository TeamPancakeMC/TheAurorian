package cn.teampancake.theaurorian.common.effect;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class ForbiddenCurseEffect extends TAMobEffect{
    public ForbiddenCurseEffect() {
        super(MobEffectCategory.HARMFUL, 0x4b6584);
    }


    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof Player player) {
            removePlayerInventoryItemEnchantments(player);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
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
        itemStackList.stream()
                .filter(ItemStack::isEnchanted)
                .forEach(itemStack -> {
                    CompoundTag tag = itemStack.getOrCreateTag();
                    Tag tag1 = tag.get("Enchantments");

                    if (!tag.contains("forbidden_curse", 9)) {
                        tag.put("forbidden_curse", new ListTag());
                    }

                    ListTag listtag = tag.getList("forbidden_curse", 10);
                    listtag.addAll((ListTag) tag1);

                    tag.remove("Enchantments");
                });
    }

    public void restoreItemEnchantments(List<ItemStack> itemStackList) {
        itemStackList.stream()
                .filter(itemStack -> itemStack.getOrCreateTag().contains("forbidden_curse", 9))
                .forEach(itemStack -> {
                    CompoundTag tag = itemStack.getOrCreateTag();
                    ListTag listTag = tag.getList("forbidden_curse", 10);
                    tag.put("Enchantments", listTag);
                    tag.remove("forbidden_curse");
                });
    }


}
