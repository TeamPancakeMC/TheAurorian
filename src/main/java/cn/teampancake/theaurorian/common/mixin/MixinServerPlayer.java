package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.level.SylvanisHandler;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer extends Player {

    public MixinServerPlayer(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @Inject(method = "checkMovementStatistics", at = @At(value = "HEAD"))
    public void checkNotMoving(double dx, double dy, double dz, CallbackInfo ci) {
        SylvanisHandler.checkNotMoving(this, dx, dy, dz);
    }

    @Inject(method = "checkMovementStatistics", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/lang/Math;round(F)I", ordinal = 3))
    public void checkWalkOrRunOnGround(double dx, double dy, double dz, CallbackInfo ci, @Local(ordinal = 0) int l) {
        SylvanisHandler.checkSylvanisToTeleport(this, this.level(), l > 0);
    }

    @Inject(method = "checkMovementStatistics", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/lang/Math;round(F)I", ordinal = 5))
    public void checkJumpAndRunOnGround(double dx, double dy, double dz, CallbackInfo ci, @Local(ordinal = 0) int j1) {
        SylvanisHandler.checkSylvanisToTeleport(this, this.level(), j1 > 25);
    }

}