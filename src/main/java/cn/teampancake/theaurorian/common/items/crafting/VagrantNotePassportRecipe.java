package cn.teampancake.theaurorian.common.items.crafting;

import cn.teampancake.theaurorian.common.items.VagrantNote;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class VagrantNotePassportRecipe extends CustomRecipe {

    public VagrantNotePassportRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Nullable
    private Pair<ItemStack, ItemStack> getInputItems(CraftingInput input) {
        ItemStack noteStack = null;
        ItemStack crystalStack = null;
        for (int i = 0; i < input.size(); i++) {
            ItemStack inputStack = input.getItem(i);
            if (inputStack.isEmpty()) continue;
            Item inputItem = inputStack.getItem();
            if (inputItem instanceof VagrantNote) {
                if (noteStack != null) return null;
                noteStack = inputStack;
            } else if (inputStack.is(TAItems.AURORIAN_CRYSTAL)) {
                if (crystalStack != null) return null;
                crystalStack = inputStack;
            } else return null;
        }

        if (noteStack != null && crystalStack != null) {
            return Pair.of(noteStack, crystalStack);
        } else {
            return null;
        }
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return this.getInputItems(input) != null;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        Pair<ItemStack, ItemStack> inputItems = this.getInputItems(input);
        DataComponentType<Unit> component = TADataComponents.NOTE_PASSPORT.get();
        if (inputItems == null) return ItemStack.EMPTY;
        ItemStack firstStack = inputItems.getFirst();
        ItemStack outputStack = firstStack.copy();
        if (!outputStack.has(component)) {
            outputStack.set(component, Unit.INSTANCE);
            return outputStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TARecipes.VAGRANT_NOTE_PASSPORT_SERIALIZER.get();
    }

}