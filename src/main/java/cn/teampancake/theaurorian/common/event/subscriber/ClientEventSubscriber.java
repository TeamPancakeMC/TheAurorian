package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import cn.teampancake.theaurorian.client.renderer.level.TASpecialEffects;
import cn.teampancake.theaurorian.common.effect.ConfusionEffect;
import cn.teampancake.theaurorian.common.effect.TremorEffect;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, value = Dist.CLIENT)
public class ClientEventSubscriber {

    private static final ResourceLocation MOONLIGHT_KNIGHT_BARS = AurorianMod.prefix("textures/gui/moonlight_knight_bars.png");
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
    public static void onViewpoint(ViewportEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity cameraEntity = minecraft.getCameraEntity();
        if (cameraEntity instanceof LocalPlayer player) {
            ClientLevel level = minecraft.level;
            int i = EnchantmentHelper.getEnchantmentLevel(TAEnchantments.MOONLIGHT.get(), player);
            if (level != null) {
                level.entitiesForRendering().forEach(entity -> {
                    if (entity instanceof LivingEntity livingEntity) {
                        double distance = player.distanceToSqr(livingEntity);
                        livingEntity.setSharedFlag(6, i > 0 && distance <= Math.pow(i * 20.0D, 2.0D));
                    }
                });
            }

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
                    float rotation = Mth.sin(player.tickCount / 10.0F) * 45.0F;
                    event.setRoll(rotation);
                }

                if (mobEffectInstance.getAmplifier() == 2) {
                    event.setRoll(180.0F);
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
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        Camera camera = event.getCamera();
        ClientLevel level = Minecraft.getInstance().level;
        if (camera.getEntity() instanceof LocalPlayer localPlayer) {
            boolean flag = localPlayer.hasEffect(TAMobEffects.EIDOLON_POISON.get());
            if (level != null && level.dimension() == TADimensions.AURORIAN_DIMENSION) {
                Vec3 vec3 = TASkyRenderer.getSkyColor(level, camera.getPosition());
                if (camera.getFluidInCamera() == FogType.NONE && !flag) {
                    event.setRed((float) vec3.x);
                    event.setGreen((float) vec3.y);
                    event.setBlue((float) vec3.z);
                }
            }

            if (flag) {
                event.setRed(Color.WHITE.getRed());
                event.setGreen(Color.WHITE.getGreen());
                event.setBlue(Color.WHITE.getBlue());
            }
        }
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        if (event.getCamera().getEntity() instanceof LocalPlayer localPlayer) {
            float renderDistance = Minecraft.getInstance().gameRenderer.getRenderDistance();
            if (localPlayer.hasEffect(TAMobEffects.EIDOLON_POISON.get())) {
                event.setNearPlaneDistance(0.0F);
                event.setFarPlaneDistance(renderDistance);
                event.setFogShape(FogShape.CYLINDER);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderBossBars(CustomizeGuiOverlayEvent.BossEventProgress event) {
        if (event.getBossEvent().getName().getContents() instanceof TranslatableContents contents) {
            if (contents.getKey().equals(TAEntityTypes.MOONLIGHT_KNIGHT.get().getDescriptionId())) {
                Component description = TAEntityTypes.MOONLIGHT_KNIGHT.get().getDescription();
                renderBossBar(event, MOONLIGHT_KNIGHT_BARS, description, 20, -2, 6, 8, -9, 0x3d3f99);
            } else if (contents.getKey().equals(TAEntityTypes.RUNESTONE_KEEPER.get().getDescriptionId())) {
                Component description = TAEntityTypes.RUNESTONE_KEEPER.get().getDescription();
                renderBossBar(event, RUNESTONE_KEEPER_BARS, description, 22, -2, 5, 8, -9, 0x6c7f82);
            } else if (contents.getKey().equals(TAEntityTypes.SPIDER_MOTHER.get().getDescriptionId())) {
                Component description = TAEntityTypes.SPIDER_MOTHER.get().getDescription();
                renderBossBar(event, SPIDER_MOTHER_BARS, description, 22, -2, 5, 8, -9, 0x4397f0);
            } else if (contents.getKey().equals(TAEntityTypes.MOON_QUEEN.get().getDescriptionId())) {
                Component description = TAEntityTypes.MOON_QUEEN.get().getDescription();
                renderBossBar(event, MOON_QUEEN_BARS, description, 22, 4, 5, 10, -7, 0xe276e8);
            }
        }
    }

    private static void renderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event, ResourceLocation atlasLocation, Component description,
                                      int frameHeight, int frameYOffset, int barHeight, int barYOffset, int textYOffset, int textColor) {
        event.setCanceled(true);
        Font font = Minecraft.getInstance().font;
        GuiGraphics graphics = event.getGuiGraphics();
        int guiWidth = graphics.guiWidth();
        int fontWidth = font.width(description);
        int strX = guiWidth / 2 - fontWidth / 2;
        int progress = (int) (180 * event.getBossEvent().getProgress());
        graphics.blit(atlasLocation, (guiWidth - 186) / 2, event.getY() + frameYOffset, 0, 5, 186, frameHeight);
        graphics.blit(atlasLocation, (guiWidth - 180) / 2, event.getY() + barYOffset, 0, 0, progress, barHeight);
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