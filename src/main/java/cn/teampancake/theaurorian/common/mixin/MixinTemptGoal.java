package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TemptGoal.class)
public class MixinTemptGoal {

    @Shadow @Final protected PathfinderMob mob;

    @Inject(method = "shouldFollow", at = @At(value = "HEAD"), cancellable = true)
    public void shouldFollow(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (this.mob instanceof Cat && entity instanceof Player player) {
            boolean flag1 = player.getMainHandItem().is(TAItems.CAT_BELL.get());
            boolean flag2 = player.getOffhandItem().is(TAItems.CAT_BELL.get());
            cir.setReturnValue(flag1 || flag2);
        }
    }

}