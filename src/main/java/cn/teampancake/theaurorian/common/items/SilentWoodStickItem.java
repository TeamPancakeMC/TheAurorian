package cn.teampancake.theaurorian.common.items;

import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class SilentWoodStickItem extends FlintAndSteelItem implements ITooltipsItem{
    public SilentWoodStickItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 100;
    }
}
