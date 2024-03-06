package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import cn.teampancake.theaurorian.client.renderer.level.TASpecialEffects;
import cn.teampancake.theaurorian.common.effect.ConfusionEffect;
import cn.teampancake.theaurorian.common.effect.TremorEffect;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, value = Dist.CLIENT)
public class ClientEventSubscriber {

    private static final ResourceLocation RUNESTONE_KEEPER_BARS = AurorianMod.prefix("textures/gui/runestone_keeper_bars.png");
    private static final ResourceLocation SPIDER_MOTHER_BARS = AurorianMod.prefix("textures/gui/spider_mother_bars.png");
    private static final ResourceLocation MOON_QUEEN_BARS = AurorianMod.prefix("textures/gui/moon_queen_bars.png");

    @SubscribeEvent
    public static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer localPlayer) {
            localPlayer.getActiveEffectsMap().values().forEach(effect -> {
                if (effect.getEffect() instanceof ConfusionEffect confusionEffect) {
                    confusionEffect.onMovementInputUpdate(effect.getAmplifier(), event.getInput(), localPlayer);
                }
            });
        }
    }

    @SubscribeEvent
    public static <T extends LivingEntity, M extends EntityModel<T>> void onRenderLiving(RenderLivingEvent.Pre<T, M> event) {
        Entity cameraEntity = Minecraft.getInstance().getCameraEntity();
        if (cameraEntity instanceof LocalPlayer player && player.hasEffect(TAMobEffects.CONCEALMENT.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onViewpoint(ViewportEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity cameraEntity = minecraft.getCameraEntity();
        ClientLevel level = minecraft.level;
        if (cameraEntity instanceof LocalPlayer player && level != null) {
            boolean flag = player.hasEffect(TAMobEffects.CONCEALMENT.get());
            level.entitiesForRendering().forEach(entity -> entity.setInvisible(flag));
            if (player.hasEffect(TAMobEffects.CORRUPTION.get())) {
                player.hurtTime = 0;
            }
        }
    }

    @SubscribeEvent
    public static void onViewportComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) return;
        player.getActiveEffectsMap().forEach((effect, mobEffectInstance) -> {
            if (effect instanceof ConfusionEffect) {
                if (mobEffectInstance.getAmplifier() == 1) {
                    double rotation = Math.sin(player.tickCount / 10.0) * 45;
                    event.setRoll((float)rotation);
                }

                if (mobEffectInstance.getAmplifier() == 2) {
                    event.setRoll(180);
                }
            }

            if (effect instanceof TremorEffect) {
                RandomSource rng = player.level().random;
                Entity cameraEntity = minecraft.getCameraEntity();
                float partialTick = minecraft.getPartialTick();
                float tremorAmount = 0.5F;
                if (cameraEntity != null) {
                    double d =  Math.sin((cameraEntity.tickCount + partialTick) * 0.5F) * tremorAmount;
                    event.getCamera().move((d * rng.nextFloat()), (d * rng.nextFloat()), (d * rng.nextFloat()));
                }
            }
        });
    }

    @SubscribeEvent
    public static void onRenderBossBars(CustomizeGuiOverlayEvent.BossEventProgress event) {
        if (event.getBossEvent().getName().getContents() instanceof TranslatableContents contents) {
            if (contents.getKey().equals(TAEntityTypes.RUNESTONE_KEEPER.get().getDescriptionId())) {
                Component description = TAEntityTypes.RUNESTONE_KEEPER.get().getDescription();
                renderBossBar(event, RUNESTONE_KEEPER_BARS, description, 186, 22, -2, 180, 5, 8, -9, 0x6c7f82);
            } else if (contents.getKey().equals(TAEntityTypes.SPIDER_MOTHER.get().getDescriptionId())) {
                Component description = TAEntityTypes.SPIDER_MOTHER.get().getDescription();
                renderBossBar(event, SPIDER_MOTHER_BARS, description, 186, 22, -2, 180, 5, 8, -9, 0x4397f0);
            } else if (contents.getKey().equals(TAEntityTypes.MOON_QUEEN.get().getDescriptionId())) {
                Component description = TAEntityTypes.MOON_QUEEN.get().getDescription();
                renderBossBar(event, MOON_QUEEN_BARS, description, 186, 22, 4, 180, 5, 10, -7, 0xe276e8);
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void renderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event, ResourceLocation atlasLocation, Component description,
            int frameWidth, int frameHeight, int frameYOffset, int barWidth, int barHeight, int barYOffset, int textYOffset, int textColor) {
        event.setCanceled(true);
        Font font = Minecraft.getInstance().font;
        GuiGraphics graphics = event.getGuiGraphics();
        int guiWidth = graphics.guiWidth();
        int fontWidth = font.width(description);
        int strX = guiWidth / 2 - fontWidth / 2;
        int progress = (int) (barWidth * event.getBossEvent().getProgress());
        graphics.blit(atlasLocation, (guiWidth - frameWidth) / 2, event.getY() + frameYOffset, 0, 5, frameWidth, frameHeight);
        graphics.blit(atlasLocation, (guiWidth - barWidth) / 2, event.getY() + barYOffset, 0, 0, progress, barHeight);
        graphics.drawString(font, description, strX, event.getY() + textYOffset, textColor);
        event.setIncrement(frameHeight + 3);
    }

    @Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
            new TASkyRenderer();
            event.register(AurorianMod.prefix("aurorian"), new TASpecialEffects());
        }

    }

}