package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.entities.technical.SitEntity;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener extends ClientCommonPacketListenerImpl {

    protected MixinClientPacketListener(Minecraft minecraft, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraft, connection, commonListenerCookie);
    }

    @Inject(method = "handleSetEntityPassengersPacket", at = @At(target = "Lnet/minecraft/world/entity/Entity;startRiding(Lnet/minecraft/world/entity/Entity;Z)Z", value = "INVOKE", shift = At.Shift.AFTER), cancellable = true)
    public void handleSetEntityPassengersPacket(ClientboundSetPassengersPacket packet, CallbackInfo ci, @Local(ordinal = 0) Entity entity) {
        if (entity instanceof SitEntity) {
            ci.cancel();
        }
    }

    @Inject(method = "handleHurtAnimation", at = @At(target = "Lnet/minecraft/world/entity/Entity;animateHurt(F)V", value = "INVOKE"), cancellable = true)
    public void handleHurtAnimation(ClientboundHurtAnimationPacket packet, CallbackInfo ci, @Local Entity entity) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(TAMobEffects.CORRUPTION)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleDamageEvent", at = @At(target = "Lnet/minecraft/world/entity/Entity;handleDamageEvent(Lnet/minecraft/world/damagesource/DamageSource;)V", value = "INVOKE"), cancellable = true)
    public void handleDamageEvent(ClientboundDamageEventPacket packet, CallbackInfo ci, @Local Entity entity) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(TAMobEffects.CORRUPTION)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleRespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;startWaitingForNewLevel(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/gui/screens/ReceivingLevelScreen$Reason;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/resources/ResourceKey;)V"))
    public void handleRespawn(ClientboundRespawnPacket packet, CallbackInfo ci, @Local(ordinal = 0) LocalPlayer oldPlayer, @Local(ordinal = 1) LocalPlayer newPlayer) {
        newPlayer.setData(TAAttachmentTypes.LOST_IN_FOREST, oldPlayer.getData(TAAttachmentTypes.LOST_IN_FOREST));
    }

    @Inject(method = "startWaitingForNewLevel(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/gui/screens/ReceivingLevelScreen$Reason;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/resources/ResourceKey;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"), cancellable = true)
    private void startWaitingForNewLevel(LocalPlayer player, ClientLevel level, ReceivingLevelScreen.Reason reason, ResourceKey<Level> toDimension, ResourceKey<Level> fromDimension, CallbackInfo ci) {
        if (player.getData(TAAttachmentTypes.LOST_IN_FOREST)) {
            this.minecraft.setScreen(null);
            ci.cancel();
        }
    }

}