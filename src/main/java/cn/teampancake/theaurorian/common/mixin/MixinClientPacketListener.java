package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.entities.technical.SitEntity;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {

    @Inject(method = "handleSetEntityPassengersPacket", at = @At(target = "Lnet/minecraft/world/entity/Entity;startRiding(Lnet/minecraft/world/entity/Entity;Z)Z",
            value = "INVOKE", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void handleSetEntityPassengersPacket(ClientboundSetPassengersPacket packet, CallbackInfo ci, Entity entity, boolean flag, int[] var4, int var5, int var6, int i, Entity entity1) {
        if (entity instanceof SitEntity) {
            ci.cancel();
        }
    }

    @Inject(method = "handleHurtAnimation", at = @At(target = "Lnet/minecraft/world/entity/Entity;animateHurt(F)V",
            value = "INVOKE", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void handleHurtAnimation(ClientboundHurtAnimationPacket packet, CallbackInfo ci, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(TAMobEffects.CORRUPTION)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleDamageEvent", at = @At(target = "Lnet/minecraft/world/entity/Entity;handleDamageEvent(Lnet/minecraft/world/damagesource/DamageSource;)V",
            value = "INVOKE", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void handleDamageEvent(ClientboundDamageEventPacket packet, CallbackInfo ci, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(TAMobEffects.CORRUPTION)) {
            ci.cancel();
        }
    }

}