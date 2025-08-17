package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {

    @Shadow @Final public Minecraft minecraft;
    @Shadow @Nullable public ClientLevel level;

    @Inject(method = "renderEntity", at = @At(value = "HEAD"), cancellable = true)
    private void renderEntity(Entity entity, double camX, double camY, double camZ, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci) {
        if (Minecraft.getInstance().getCameraEntity() instanceof LocalPlayer player && player.hasEffect(TAMobEffects.SHADOWED_SIGHT) && entity != player) ci.cancel();
    }

    @Inject(method = "addParticle(Lnet/minecraft/core/particles/ParticleOptions;ZDDDDDD)V", at = @At(value = "HEAD"), cancellable = true)
    public void addParticle(ParticleOptions options, boolean force, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, CallbackInfo ci) {
        if (Minecraft.getInstance().getCameraEntity() instanceof LocalPlayer player && player.hasEffect(TAMobEffects.SHADOWED_SIGHT)) {
            if (options.getType() == ParticleTypes.ENTITY_EFFECT) ci.cancel();
        }
    }

}