package cn.teampancake.theaurorian.event;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.ModBlocks;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, value = Dist.CLIENT)
public class ClientEventSubscriber {

    @SubscribeEvent
    @SuppressWarnings("deprecation")
    public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerBelowAll("aurorian_portal", (gui, guiGraphics, partialTick, width, height) -> {
            LocalPlayer player = gui.getMinecraft().player;
            if (player != null) {
                float f1 = Mth.lerp(partialTick, player.oSpinningEffectIntensity, player.spinningEffectIntensity);
                if (f1 > 0.0F && !player.hasEffect(MobEffects.CONFUSION)) {
                    gui.setupOverlayRenderState(true, false);
                    f1 = f1 < 1.0F ? (float) (Math.pow(f1, 4.0D) * 0.8F + 0.2F) : f1;
                    RenderSystem.disableDepthTest();
                    RenderSystem.depthMask(false);
                    guiGraphics.setColor(1.0F, 1.0F, 1.0F, f1);
                    BlockModelShaper shaper = gui.getMinecraft().getBlockRenderer().getBlockModelShaper();
                    TextureAtlasSprite particleIcon = shaper.getParticleIcon(ModBlocks.AURORIAN_PORTAL.get().defaultBlockState());
                    guiGraphics.blit(0, 0, -90, width, height, particleIcon);
                    RenderSystem.depthMask(true);
                    RenderSystem.enableDepthTest();
                    guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        });
    }

}