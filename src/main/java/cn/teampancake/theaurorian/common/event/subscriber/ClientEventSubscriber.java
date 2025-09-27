package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.tooltips.ItemTooltip;
import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import cn.teampancake.theaurorian.common.effect.ConfusionEffect;
import cn.teampancake.theaurorian.common.level.SylvanisHandler;
import cn.teampancake.theaurorian.common.registry.*;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import cn.teampancake.theaurorian.compat.mui.ModernUICompatibility;
import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;

import java.awt.*;

@EventBusSubscriber(modid = TheAurorian.MOD_ID, value = Dist.CLIENT)
public class ClientEventSubscriber {

    private static final ResourceLocation MOONLIGHT_KNIGHT_BARS = TheAurorian.prefix("textures/gui/moonlight_knight_bars.png");
    private static final ResourceLocation RUNESTONE_KEEPER_BARS = TheAurorian.prefix("textures/gui/runestone_keeper_bars.png");
    private static final ResourceLocation SPIDER_MOTHER_BARS = TheAurorian.prefix("textures/gui/spider_mother_bars.png");
    private static final ResourceLocation MOON_QUEEN_BARS = TheAurorian.prefix("textures/gui/moon_queen_bars.png");

    private static final Color FOG_COLOR = new Color(0.85f, 0.9f, 1.0f);
    private static final float MIN_VISIBILITY = 15.0f;
    private static final float MAX_VISIBILITY = 5.0f;

    @SubscribeEvent
    public static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer localPlayer) {
            if (localPlayer.hasEffect(TAMobEffects.CONFUSION)) {
                ConfusionEffect.onMovementInputUpdate(event.getInput(), localPlayer);
            }
        }
    }

    @SubscribeEvent
    public static void onMouseButtonPre(InputEvent.MouseButton.Pre event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(TAMobEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMouseScrolling(InputEvent.MouseScrollingEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(TAMobEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onInteractionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(TAMobEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(TAMobEffects.DEAFNESS)) {
            event.setSound(null);
        }
    }

    @SubscribeEvent
    public static void onViewportComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            MobEffectInstance tremorInstance = player.getEffect(TAMobEffects.TREMOR);
            MobEffectInstance overheatingInstance = player.getEffect(TAMobEffects.OVERHEATING);
            if (player.hasEffect(TAMobEffects.CONFUSION)) {
                float rotation = Mth.sin(player.tickCount / 10.0F) * 45.0F;
                event.setRoll(rotation);
            }

            if (tremorInstance != null) {
                RandomSource random = player.level().getRandom();
                float amplifier = tremorInstance.getAmplifier();
                float partialTick = minecraft.getTimer().getGameTimeDeltaPartialTick(Boolean.TRUE);
                float f =  Mth.sin((player.tickCount + partialTick) * 2.0F) * ((amplifier + 1.0F) * 10.0F);
                player.turn(f * random.nextDouble(), f * random.nextDouble());
            }

            if (overheatingInstance != null) {
                float amplifier = overheatingInstance.getAmplifier();
                event.setRoll(Mth.sin(player.tickCount / 5.0F) * (amplifier + 1.0F));
            }
        }
    }

    @SubscribeEvent
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        Camera camera = event.getCamera();
        ClientLevel level = Minecraft.getInstance().level;
        if (camera.getEntity() instanceof LocalPlayer localPlayer) {
            boolean flag = localPlayer.hasEffect(TAMobEffects.EIDOLON_POISON);
            float sylvanis = localPlayer.getData(TAAttachmentTypes.SYLVANIS_PROGRESS);
            if (level != null && TACommonUtils.isAurorianDimension(level)) {
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
            } else if (sylvanis > SylvanisHandler.FOG_START) {
                event.setRed(FOG_COLOR.getRed());
                event.setGreen(FOG_COLOR.getGreen());
                event.setBlue(FOG_COLOR.getBlue());
            } else if (localPlayer.hasEffect(TAMobEffects.FROSTBITE)) {
                event.setRed(0.623F);
                event.setGreen(0.734F);
                event.setBlue(0.785F);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        if (event.getCamera().getEntity() instanceof LocalPlayer localPlayer) {
            float renderDistance = Minecraft.getInstance().gameRenderer.getRenderDistance();
            float sylvanis = localPlayer.getData(TAAttachmentTypes.SYLVANIS_PROGRESS);
            if (localPlayer.hasEffect(TAMobEffects.EIDOLON_POISON)) {
                event.setNearPlaneDistance(0.0F);
                event.setFarPlaneDistance(renderDistance);
                event.setFogShape(FogShape.CYLINDER);
                event.setCanceled(true);
            } else if (localPlayer.hasEffect(TAMobEffects.FROSTBITE)) {
                event.setNearPlaneDistance(0.0F);
                event.setFarPlaneDistance(4.0F);
                event.setFogShape(FogShape.CYLINDER);
                event.setCanceled(true);
            } else if (sylvanis > 0.0F) {
                SylvanisHandler.FogParameters fog = SylvanisHandler.calculateFog(sylvanis);
                float visibility = MIN_VISIBILITY - (MIN_VISIBILITY - MAX_VISIBILITY) * fog.density();
                event.setNearPlaneDistance(-8.0F);
                event.setFarPlaneDistance(visibility);
                event.setFogShape(FogShape.CYLINDER);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRenderTooltips(RenderTooltipEvent.Pre event) {
        ModernUICompatibility.toggleModernUITooltipRenderer(true);
        ItemStack itemStack = event.getItemStack();
        Holder<ItemTooltip> tooltip = itemStack.get(TADataComponents.ITEM_TOOLTIP);
        if (tooltip != null) {
            ModernUICompatibility.toggleModernUITooltipRenderer(false);
            tooltip.value().renderTooltips(event);
            event.setCanceled(true);
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

    private static void renderBossBar(
            CustomizeGuiOverlayEvent.BossEventProgress event, ResourceLocation atlasLocation, Component description,
            int frameHeight, int frameYOffset, int barHeight, int barYOffset, int textYOffset, int textColor) {
        event.setCanceled(true);
        Font font = Minecraft.getInstance().font;
        GuiGraphics graphics = event.getGuiGraphics();
        int guiWidth = graphics.guiWidth();
        int fontWidth = font.width(description);
        int strX = guiWidth >> 1 - fontWidth >> 1;
        int progress = (int) (180 * event.getBossEvent().getProgress());
        graphics.blit(atlasLocation, (guiWidth - 186) >> 1, event.getY() + frameYOffset, 0, 5, 186, frameHeight);
        graphics.blit(atlasLocation, (guiWidth - 180) >> 1, event.getY() + barYOffset, 0, 0, progress, barHeight);
        graphics.drawString(font, description, strX, event.getY() + textYOffset, textColor);
        event.setIncrement(frameHeight + 3);
    }

}