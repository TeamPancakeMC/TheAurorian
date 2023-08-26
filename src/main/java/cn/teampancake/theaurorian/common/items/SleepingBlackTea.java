package cn.teampancake.theaurorian.common.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SleepingBlackTea extends TeaFood{
    public SleepingBlackTea() {
        super(new Item.Properties()
                .food(new FoodProperties.Builder().nutrition(0).saturationMod(0).build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        pEntityLiving.startSleeping(pEntityLiving.getOnPos());
        return super.finishUsingItem(pStack,pLevel,pEntityLiving);
    }
}
