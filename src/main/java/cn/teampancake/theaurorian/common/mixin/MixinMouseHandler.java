package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("DiscouragedShift")
@Mixin(MouseHandler.class)
public class MixinMouseHandler {

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V", shift = At.Shift.BEFORE), cancellable = true)
    private void turnPlayer(double movementTime, CallbackInfo ci, @Local(ordinal = 4) double d0, @Local(ordinal = 5) double d1) {
        LocalPlayer player = this.minecraft.player;
        if (player != null && player.hasEffect(TAMobEffects.CONFUSION)) {
            player.turn(-d0, -d1);
            ci.cancel();
        }
    }

}