package cn.teampancake.theaurorian.client.inventory;

import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TAMenus;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class AccessoriesMenu extends RecipeBookMenu<RecipeInput, Recipe<RecipeInput>> {

    public final Player player;
    private final CraftingContainer craftMatrix = new TransientCraftingContainer(this, 2, 2);
    private final ResultContainer craftResult = new ResultContainer();

    @SuppressWarnings("unused")
    public AccessoriesMenu(int containerId, Inventory inventory, FriendlyByteBuf packetBuffer) {
        this(containerId, inventory);
    }

    public AccessoriesMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new ItemStackHandler(24));
    }

    public AccessoriesMenu(int containerId, Inventory inventory, IItemHandler itemHandler) {
        super(TAMenus.ACCESSORIES_MENU.get(), containerId);
        checkContainerSize(inventory, 24);
        this.player = inventory.player;
        this.addPlayerInventorySlot();
        this.addPlayerHotBarSlot();
        this.addPlayerCraftingSlot();
        this.addSlot(new ResultSlot(this.player, this.craftMatrix, this.craftResult, 0, 154, 28));
        this.addSlot(new Slot(this.player.getInventory(), 40, 77, 62) {
            @OnlyIn(Dist.CLIENT)
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 6; ++j) {
                int index = j + i * 6;
                int x = -(24 + j * 18);
                int y = 10 + i * 18;
                this.addSlot(new AccessoriesSlot(inventory.player, itemHandler, index, x, y));
            }
        }
    }

    private void addPlayerCraftingSlot() {
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                this.addSlot(new Slot(this.craftMatrix, j + i * 2, 98 + j * 18, 18 + i * 18));
            }
        }
    }

    private void addPlayerInventorySlot() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(this.player.getInventory(), j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotBarSlot() {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(this.player.getInventory(), i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void fillCraftSlotsStackedContents(@Nonnull StackedContents itemHelperIn) {
        this.craftMatrix.fillStackedContents(itemHelperIn);
    }

    @Override
    public void clearCraftingContent() {
        this.craftMatrix.clearContent();
        this.craftResult.clearContent();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean recipeMatches(RecipeHolder recipeHolder) {
        return recipeHolder.value().matches(this.craftMatrix.asCraftInput(), this.player.level());
    }

    @Override
    public int getResultSlotIndex() {
        return 0;
    }

    @Override
    public int getGridWidth() {
        return this.craftMatrix.getWidth();
    }

    @Override
    public int getGridHeight() {
        return this.craftMatrix.getHeight();
    }

    @Override
    public int getSize() {
        return 5;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    @Override
    public boolean shouldMoveToInventory(int slotIndex) {
        return slotIndex != this.getResultSlotIndex();
    }

    public static class AccessoriesSlot extends SlotItemHandler {

        private static final AttachmentType<NonNullList<ItemStack>> ATTACHMENT_TYPE = TAAttachmentTypes.ACCESSORIES_INVENTORY.get();
        private final Player player;

        public AccessoriesSlot(Player player, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
            this.player = player;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return !this.container.hasAnyMatching(s -> ItemStack.isSameItem(s, stack));
        }

        @Override
        public void onTake(Player player, ItemStack stack) {
            List<ItemStack> itemStacks = player.getData(ATTACHMENT_TYPE);
            itemStacks.remove(this.index);
            this.setChanged();
        }

        @Override
        public void set(ItemStack stack) {
            super.set(stack);
            if (!stack.isEmpty()) {
                List<ItemStack> itemStacks = this.player.getData(ATTACHMENT_TYPE);
                itemStacks.add(this.index, stack);
            }
        }

        @Override
        public boolean allowModification(@Nonnull Player player) {
            return true;
        }

    }

}