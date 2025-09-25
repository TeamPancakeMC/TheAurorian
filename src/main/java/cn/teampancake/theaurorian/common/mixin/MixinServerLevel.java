package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.level.TAServerLevel;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel {

    @Shadow public abstract ServerLevel getLevel();

    @Inject(method = "tickChunk", at = @At(value = "HEAD"), cancellable = true)
    public void tickChunk(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        if (TACommonUtils.isAurorianDimension(this.getLevel())) {
            TAServerLevel.tickChunk(this.getLevel(), chunk, randomTickSpeed);
            ci.cancel();
        }
    }

}