package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TADamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageSource.class)
public class MixinDamageSource {

    @Shadow @Final private Holder<DamageType> type;

    @Inject(method = "getLocalizedDeathMessage", at = @At(value = "HEAD"), cancellable = true)
    public void getLocalizedDeathMessage(LivingEntity livingEntity, CallbackInfoReturnable<Component> cir) {
        if (this.type.is(TADamageTypes.CORRUPTION) && (livingEntity instanceof ServerPlayer || livingEntity.hasCustomName())) {
            RandomSource random = livingEntity.getRandom();
            int easterEgg = random.nextBoolean() ? 2 : 3;
            int index = random.nextInt((10)) < 9 ? 1 : easterEgg;
            String s = "death.attack.corruption_" + index;
            cir.setReturnValue(Component.translatable(s, livingEntity.getDisplayName()));
        }
    }

}