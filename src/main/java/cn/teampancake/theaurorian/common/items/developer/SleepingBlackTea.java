package cn.teampancake.theaurorian.common.items.developer;

import cn.teampancake.theaurorian.common.items.TeaFood;
import cn.teampancake.theaurorian.registry.TAMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class SleepingBlackTea extends TeaFood implements IDeveloperItem {

    public SleepingBlackTea() {
        super(new Item.Properties().food(new FoodProperties.Builder().effect(
                () -> new MobEffectInstance(TAMobEffects.STUN.get(), 100), 1.0F).build()));
    }

}