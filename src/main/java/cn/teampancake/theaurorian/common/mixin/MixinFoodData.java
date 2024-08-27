package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoodData.class)
public class MixinFoodData {

    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V"))
    public boolean tick(Player player, float healAmount) {
        return !TAEntityUtils.shouldForbiddenNaturalRegeneration(player);
    }

}