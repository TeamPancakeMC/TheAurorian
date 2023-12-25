package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Level.class)
public abstract class MixinLevel {

    @Redirect(method = "updateSkyBrightness", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;skyDarken:I", opcode = Opcodes.PUTFIELD))
    public void updateSkyBrightness(Level level, int value) {
        if (level.dimension == TADimensions.AURORIAN_DIMENSION) {
            level.skyDarken = Math.max(5, value);
        }
    }

}