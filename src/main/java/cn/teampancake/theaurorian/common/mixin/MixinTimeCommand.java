package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.TimeCommand;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TimeCommand.class)
public class MixinTimeCommand {

    @Inject(method = "setTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V", shift = At.Shift.AFTER))
    private static void setTime(CommandSourceStack source, int time, CallbackInfoReturnable<Integer> cir, @Local(ordinal = 0) ServerLevel serverLevel) {
        long amount = time - serverLevel.dayTime();
        if (amount > 0) {
            TAWorldEvents.REGISTRY.forEach(worldEvent -> worldEvent.handleTimeSkip(serverLevel, amount));
        } else if (amount < 0) {
            TAWorldEvents.REGISTRY.forEach(worldEvent -> worldEvent.handleTimeRewind(serverLevel, Math.abs(amount)));
        }
    }

    @Inject(method = "addTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V", shift = At.Shift.AFTER))
    private static void addTime(CommandSourceStack source, int amount, CallbackInfoReturnable<Integer> cir, @Local(ordinal = 0) ServerLevel serverLevel) {
        TAWorldEvents.REGISTRY.forEach(worldEvent -> worldEvent.handleTimeSkip(serverLevel, amount));
    }

}