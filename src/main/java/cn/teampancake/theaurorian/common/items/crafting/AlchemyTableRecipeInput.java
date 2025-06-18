package cn.teampancake.theaurorian.common.items.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record AlchemyTableRecipeInput(List<ItemStack> items, ItemStack material) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        if (index < 3) {
            return this.items.get(index);
        } else if (index == 3) {
            return this.material;
        } else {
            throw new IllegalArgumentException("No item for index " + index);
        }
    }

    @Override
    public int size() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

}