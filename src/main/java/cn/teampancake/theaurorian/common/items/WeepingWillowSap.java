package cn.teampancake.theaurorian.common.items;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WeepingWillowSap extends Item {

    public WeepingWillowSap() {
        super(TAItemProperties.get().food(new FoodProperties.Builder().build()).hasTooltips().isSimpleModelItem());
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player && !level.isClientSide) {
            player.removeEffect(MobEffects.POISON);
        }

        return super.finishUsingItem(stack, level, entity);
    }

}