package cn.teampancake.theaurorian.common.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BepsiItem extends Item implements ITooltipsItem{

    public BepsiItem() {
        super(new Properties().stacksTo(1)
                .food(new FoodProperties.Builder()
                        .nutrition(0)
                        .saturationMod(0)
                        .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600), 1.0F)
                        .alwaysEat()
                        .build()));

    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        if(entityLiving instanceof ServerPlayer serverPlayer)
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        return super.finishUsingItem(stack, level, entityLiving);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }
}
