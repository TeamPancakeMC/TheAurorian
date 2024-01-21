package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.world.level.levelgen.WorldOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(WorldOptions.class)
public class MixinWorldOptions {

    @Inject(method = "<init>(JZZLjava/util/Optional;)V", at = @At(value = "TAIL"))
    private void init(long seed, boolean generateStructures, boolean generateBonusChest, Optional<String> legacyCustomOptions, CallbackInfo ci) {
        TADimensions.seed = seed;
    }

}