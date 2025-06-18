package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemColors.class)
public class MixinItemColors {

    @Inject(method = "getColor", at = @At(value = "HEAD"), cancellable = true)
    public void getColor(ItemStack stack, int tintIndex, CallbackInfoReturnable<Integer> cir) {
        FoodProperties foodProperties = stack.get(DataComponents.FOOD);
        Boolean infusePotion = stack.get(TADataComponents.INFUSED_POTION);
        if (foodProperties != null && infusePotion != null) {
            List<MobEffectInstance> foodEffects = new ArrayList<>();
            foodProperties.effects().forEach(effect -> foodEffects.add(effect.effect()));
            cir.setReturnValue(PotionContents.getColor(foodEffects));
        }
    }

}