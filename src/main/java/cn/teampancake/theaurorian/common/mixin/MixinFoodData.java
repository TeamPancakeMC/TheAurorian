package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.level.TAFoodData;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class MixinFoodData {

    @Inject(method = "tick", at = @At(value = "HEAD"), cancellable = true)
    public void tick(Player player, CallbackInfo ci) {
        if (TACommonUtils.isAurorianDimension(player.level())) {
            FoodData foodData = (FoodData) (Object) this;
            TAFoodData.tick(foodData, player);
            ci.cancel();
        }
    }

}