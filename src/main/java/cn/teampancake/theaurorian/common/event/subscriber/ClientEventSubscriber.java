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
import net.minecraft.client.gui.components.LerpingBossEvent;
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
import java.util.Map;

@EventBusSubscriber(modid = TheAurorian.MOD_ID, value = Dist.CLIENT)
public class ClientEventSubscriber {

    private static final BossBarStyle MOONLIGHT_KNIGHT_BAR = new BossBarStyle(TheAurorian.prefix("textures/misc/bar/moonlight_knight_bars.png"))
            .frameWidth(186).frameHeight(20).frameYOffset(-2).barWidth(180).barHeight(6).barYOffset(8).textYOffset(-9).textColor(0x3d3f99);
    private static final BossBarStyle RUNESTONE_KEEPER_BAR = new BossBarStyle(TheAurorian.prefix("textures/misc/bar/runestone_keeper_bars.png"))
            .frameWidth(186).frameHeight(22).frameYOffset(-2).barWidth(180).barHeight(5).barYOffset(8).textYOffset(-9).textColor(0x6c7f82);
    private static final BossBarStyle SPIDER_MOTHER_BAR = new BossBarStyle(TheAurorian.prefix("textures/misc/bar/spider_mother_bars.png"))
            .frameWidth(186).frameHeight(22).frameYOffset(-2).barWidth(180).barHeight(5).barYOffset(8).textYOffset(-9).textColor(0x4397f0);
    private static final BossBarStyle MOON_QUEEN_BAR = new BossBarStyle(TheAurorian.prefix("textures/misc/bar/moon_queen_bars.png"))
            .frameWidth(186).frameHeight(22).frameYOffset(4).barWidth(180).barHeight(5).barYOffset(10).textYOffset(-7).textColor(0xe276e8);
    private static final BossBarStyle BLOOD_MOON_BAR = new BossBarStyle(TheAurorian.prefix("textures/misc/bar/blood_moon_bars.png"))
            .frameWidth(186).frameHeight(20).frameYOffset(4).barWidth(182).barHeight(5).barYOffset(16).textYOffset(-7).textColor(16777215);
    private static final Map<String, BossBarStyle> BOSS_BAR_STYLE_MAP = Map.of(
            "entity.theaurorian.moonlight_knight", MOONLIGHT_KNIGHT_BAR,
            "entity.theaurorian.runestone_keeper", RUNESTONE_KEEPER_BAR,
            "entity.theaurorian.spider_mother", SPIDER_MOTHER_BAR,
            "entity.theaurorian.moon_queen", MOON_QUEEN_BAR);
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
        Component name = event.getBossEvent().getName();
        if (name.getContents() instanceof TranslatableContents contents) {
            String key = contents.getKey();
            if (BOSS_BAR_STYLE_MAP.containsKey(key)) {
                BOSS_BAR_STYLE_MAP.get(key).render(event);
            } else if (key.startsWith("event.theaurorian.blood_moon")) {
                BLOOD_MOON_BAR.render(event);
            }
        }
    }

    private static class BossBarStyle {

        private final ResourceLocation atlasLocation;
        private int frameWidth;
        private int frameHeight;
        private int frameYOffset;
        private int barWidth;
        private int barHeight;
        private int barYOffset;
        private int textYOffset;
        private int textColor;

        public BossBarStyle(ResourceLocation atlasLocation) {
            this.atlasLocation = atlasLocation;
        }

        public BossBarStyle frameWidth(int frameWidth) {
            this.frameWidth = frameWidth;
            return this;
        }

        public BossBarStyle frameHeight(int frameHeight) {
            this.frameHeight = frameHeight;
            return this;
        }

        public BossBarStyle frameYOffset(int frameYOffset) {
            this.frameYOffset = frameYOffset;
            return this;
        }

        public BossBarStyle barWidth(int barWidth) {
            this.barWidth = barWidth;
            return this;
        }

        public BossBarStyle barHeight(int barHeight) {
            this.barHeight = barHeight;
            return this;
        }

        public BossBarStyle barYOffset(int barYOffset) {
            this.barYOffset = barYOffset;
            return this;
        }

        public BossBarStyle textYOffset(int textYOffset) {
            this.textYOffset = textYOffset;
            return this;
        }

        public BossBarStyle textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public void render(CustomizeGuiOverlayEvent.BossEventProgress event) {
            event.setCanceled(true);
            Font font = Minecraft.getInstance().font;
            GuiGraphics graphics = event.getGuiGraphics();
            LerpingBossEvent bossEvent = event.getBossEvent();
            int guiWidth = graphics.guiWidth();
            int progress = Mth.floor(this.barWidth * bossEvent.getProgress());
            graphics.blit(this.atlasLocation, (guiWidth - this.frameWidth) / 2, event.getY() + this.frameYOffset, 0, 5, this.frameWidth, this.frameHeight);
            graphics.blit(this.atlasLocation, (guiWidth - this.barWidth) / 2, event.getY() + this.barYOffset, 0, 0, progress, this.barHeight);
            graphics.drawString(font, bossEvent.getName(), guiWidth / 2 - font.width(bossEvent.getName()) / 2, event.getY() + this.textYOffset, this.textColor);
            event.setIncrement(this.frameHeight + 3);
        }

    }

}