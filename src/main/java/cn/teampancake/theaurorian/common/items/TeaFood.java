package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TeaFood extends BowlFoodItem implements ITooltipsItem{
    public TeaFood(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public int getUseDuration(ItemStack pStack){
        return 16;
    }
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        ItemStack itemstack = super.finishUsingItem(pStack, pLevel, pEntityLiving);
        return pEntityLiving instanceof Player && ((Player)pEntityLiving).getAbilities().instabuild ? itemstack : new ItemStack(TAItems.TEA_CUP.get());
    }
    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }
}
