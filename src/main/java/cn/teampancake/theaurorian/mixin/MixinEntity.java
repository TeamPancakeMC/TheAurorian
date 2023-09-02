package cn.teampancake.theaurorian.mixin;

import cn.teampancake.theaurorian.registry.ModDimensions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow protected int portalTime;
    @Shadow protected boolean isInsidePortal;
    @Shadow public abstract Level level();
    @Shadow public abstract boolean isPassenger();
    @Shadow public abstract int getPortalWaitTime();
    @Shadow public abstract void setPortalCooldown();
    @Shadow protected abstract void processPortalCooldown();
    @Shadow @Nullable public abstract Entity changeDimension(ServerLevel destination);

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;handleNetherPortal()V", shift = At.Shift.AFTER))
    public void baseTick(CallbackInfo ci) {
        this.handleAurorianPortal();
    }

    @SuppressWarnings("resource")
    protected void handleAurorianPortal() {
        if (this.level() instanceof ServerLevel serverLevel) {
            int i = this.getPortalWaitTime();
            if (this.isInsidePortal) {
                MinecraftServer minecraftServer = serverLevel.getServer();
                boolean flag = this.level().dimension() == ModDimensions.AURORIAN_DIMENSION;
                ResourceKey<Level> resourceKey = flag ? Level.OVERWORLD : ModDimensions.AURORIAN_DIMENSION;
                ServerLevel serverLevel1 = minecraftServer.getLevel(resourceKey);
                if (serverLevel1 != null && minecraftServer.isNetherEnabled() && !this.isPassenger() && this.portalTime++ >= i) {
                    this.level().getProfiler().push("portal");
                    this.portalTime = i;
                    this.setPortalCooldown();
                    this.changeDimension(serverLevel1);
                    this.level().getProfiler().pop();
                }

                this.isInsidePortal = false;
            } else {
                if (this.portalTime > 0) {
                    this.portalTime -= 4;
                }

                if (this.portalTime < 0) {
                    this.portalTime = 0;
                }
            }

            this.processPortalCooldown();
        }
    }

}