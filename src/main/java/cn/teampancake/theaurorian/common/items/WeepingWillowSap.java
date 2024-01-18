package cn.teampancake.theaurorian.common.items;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WeepingWillowSap extends Item implements ITooltipsItem {

    public WeepingWillowSap() {
        super(new Properties().food(new FoodProperties.Builder().build()));
    }
    protected void onFoodEaten(Level level, Player player) {
        if (level.isClientSide) return;

        if (player.hasEffect(MobEffects.POISON)) {
            player.removeEffect(MobEffects.POISON);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player && !level.isClientSide) {
            onFoodEaten(level, player);
        }
        return super.finishUsingItem(stack, level, entity);
    }
}