package cn.teampancake.theaurorian.mixin;

import cn.teampancake.theaurorian.registry.TADimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FogRenderer.class)
public class MixinFogRenderer {

    @ModifyVariable(method = "setupColor", at = @At(value = "STORE"), ordinal = 2)
    private static Vec3 setupColor(Vec3 value) {
        final Minecraft mc = Minecraft.getInstance();
        final ClientLevel level = mc.level;
        if (level != null && level.dimension() == TADimensions.AURORIAN_DIMENSION) {
            return level.getSkyColor(mc.gameRenderer.getMainCamera().getPosition(), mc.getFrameTime());
        } else {
            return value;
        }
    }

}