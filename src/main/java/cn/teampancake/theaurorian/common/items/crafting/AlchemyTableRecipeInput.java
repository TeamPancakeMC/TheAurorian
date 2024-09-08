package cn.teampancake.theaurorian.common.items.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AlchemyTableRecipeInput(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack material) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> this.input1;
            case 1 -> this.input2;
            case 2 -> this.input3;
            case 3 -> this.material;
            default -> throw new IllegalArgumentException("No item for index " + index);
        };
    }

    @Override
    public int size() {
        return 4;
    }

}