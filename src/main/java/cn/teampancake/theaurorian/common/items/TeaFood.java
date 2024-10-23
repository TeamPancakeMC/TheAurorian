package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.jetbrains.annotations.NotNull;

public class TeaFood extends Item {

    public TeaFood(TAItemProperties properties) {
        super(properties.stacksTo((1)).food(new FoodProperties.Builder().usingConvertsTo(TAItems.TEA_CUP.get()).build())
                .addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem());
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 16;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

}