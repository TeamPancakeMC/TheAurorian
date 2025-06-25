package cn.teampancake.theaurorian.client.gui;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;

import java.awt.Color;

public class StunEffectScreen extends Screen {

    private static final ResourceLocation STUN_ICON = TheAurorian.prefix("textures/mob_effect/stun.png");
    private static final String PREFIX = TheAurorian.MOD_ID + ".stun_effect_screen.";
    private static final Component RECOVERING = Component.translatable(PREFIX + "recovering");
    private static final Component STUNNING = Component.translatable(PREFIX + "stunning");

    private final int stunDuration;
    private float iconRotation = 0.0f;
    private float progress = 1.0f;
    private String keyHint = "";
    private long keyHintTime = 0;
    private boolean isRecovering = false;
    private boolean hasClosed = false;
    private long recoverStartTime = 0;
    private float recoverAlpha = 1.0f;

    public StunEffectScreen(int stunDuration) {
        super(Component.empty());
        this.stunDuration = stunDuration;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        int width = this.width;
        int height = this.height;
        long now = System.currentTimeMillis();
        float vignetteAlpha = (float) (0.35 + 0.15 * Math.sin(now / 250.0));
        vignetteAlpha *= this.recoverAlpha;
        this.drawVignette(guiGraphics, width, height, vignetteAlpha);
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        int baseCenterX = width / 2;
        int baseCenterY = height / 2 - 40;
        poseStack.translate(baseCenterX, baseCenterY, 0);
        poseStack.scale(6.0F, 6.0F, 6.0F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(this.iconRotation));
        int iconWidth = 18;
        int iconHeight = 18;
        guiGraphics.blit(STUN_ICON, -iconWidth / 2, -iconHeight / 2, 0, 0, iconWidth, iconHeight, iconWidth, iconHeight);
        poseStack.popPose();
        int barWidth = 240, barHeight = 12;
        int barX = width / 2 - barWidth / 2;
        int barY = baseCenterY + 80;
        this.drawLoadingBar(guiGraphics, barX, barY, barWidth, barHeight, this.progress, this.recoverAlpha);
        Component tip = this.isRecovering ? RECOVERING : STUNNING;
        int tipColor = this.isRecovering ? 0x77FF77 : 0xFFFFFF;
        guiGraphics.drawCenteredString(this.font, tip, width / 2, barY + 30, tipColor | (((int)(255 * this.recoverAlpha)) << 24));
        if (!this.keyHint.isEmpty()) {
            MutableComponent text = Component.translatable(PREFIX + "forbidden", this.keyHint);
            guiGraphics.drawCenteredString(this.font, text, width / 2, height - 40, 0xFF5555 | (((int)(255 * this.recoverAlpha)) << 24));
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void tick() {
        long now = System.currentTimeMillis();
        if (this.minecraft != null) {
            LocalPlayer player = this.minecraft.player;
            if (player != null && player.hasEffect(TAMobEffects.STUN)) {
                MobEffectInstance instance = player.getEffect(TAMobEffects.STUN);
                if (instance != null) {
                    this.progress = Math.max(0.0f, 1.0f - (float) instance.duration / this.stunDuration);
                }
            } else {
                this.minecraft.setScreen(null);
            }
        }

        float iconRotationSpeed = 180.0f + 180.0f * this.progress;
        this.iconRotation += iconRotationSpeed / 20.0f;
        if (this.iconRotation > 360.0f) {
            this.iconRotation -= 360.0f;
        }

        if (this.progress > 0.95F && !this.isRecovering) {
            this.isRecovering = true;
            this.recoverStartTime = System.currentTimeMillis();
        }

        if (this.isRecovering && !this.hasClosed) {
            long recoverElapsed = now - this.recoverStartTime;
            this.recoverAlpha = Math.max(0f, 1.0f - recoverElapsed / 800.0f);
            this.hasClosed = true;
        }

        if (!this.keyHint.isEmpty() && now - this.keyHintTime > 1000) {
            this.keyHint = "";
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.keyHint = InputConstants.getKey(keyCode, scanCode).getDisplayName().getString();
        this.keyHintTime = System.currentTimeMillis();
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return true;
    }

    private void drawVignette(GuiGraphics graphics, int w, int h, float alpha) {
        int steps = 32;
        int cx = w / 2, cy = h / 2;
        int rx = w / 4;
        int ry = h / 6;
        int maxR = (int)(Math.sqrt(w * w + h * h) / 2);
        for (int i = 0; i < steps; i++) {
            float t1 = (float)(i + 1) / steps;
            int r1x = (int)(rx + (maxR - rx) * t1);
            int r1y = (int)(ry + (maxR - ry) * t1);
            float opacity = alpha * t1;
            int color = ((int)(255 * opacity)) << 24;
            graphics.fill(0, 0, w, cy - r1y, color);
            graphics.fill(0, cy + r1y, w, h, color);
            graphics.fill(0, cy - r1y, cx - r1x, cy + r1y, color);
            graphics.fill(cx + r1x, cy - r1y, w, cy + r1y, color);
        }
    }

    public void drawProgressRing(GuiGraphics graphics, int cx, int cy, int radius, float progress, float alpha) {
        int segs = 60;
        float angleStep = 360.0f / segs;
        int colorStart = 0xFFFFDD44;
        int colorEnd = 0xFF44FF88;
        for (int i = 0; i < segs * progress; i++) {
            float a0 = (float)i * angleStep - 90;
            float a1 = (float)(i + 1) * angleStep - 90;
            float t = (float)i / segs;
            int color = lerpColorInt(colorStart, colorEnd, t, alpha);
            int x0 = cx + (int)(Math.cos(Math.toRadians(a0)) * radius);
            int y0 = cy + (int)(Math.sin(Math.toRadians(a0)) * radius);
            int x1 = cx + (int)(Math.cos(Math.toRadians(a1)) * radius);
            int y1 = cy + (int)(Math.sin(Math.toRadians(a1)) * radius);
            graphics.fill(x0, y0, x1, y1, color);
        }
    }

    private void drawLoadingBar(GuiGraphics graphics, int x, int y, int w, int h, float progress, float alpha) {
        int bg = ((int)(alpha * 255) << 24) | 0x333333;
        int fg = ((int)(alpha * 255) << 24) | 0xDDFF88;
        int border = ((int)(alpha * 255) << 24) | 0xFFFFFF;
        graphics.fill(x, y, x + w, y + h, bg);
        graphics.fill(x, y, x + (int)(w * progress), y + h, fg);
        graphics.fill(x, y, x + w, y + 1, border);
        graphics.fill(x, y + h - 1, x + w, y + h, border);
        graphics.fill(x, y, x + 1, y + h, border);
        graphics.fill(x + w - 1, y, x + w, y + h, border);
    }

    private Color lerpColor(Color c1, Color c2, float t) {
        int r = (int)(c1.getRed() + (c2.getRed() - c1.getRed()) * t);
        int g = (int)(c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
        int b = (int)(c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
        return new Color(r, g, b);
    }

    private int lerpColorInt(int c1, int c2, float t, float alpha) {
        int a1 = (c1 >> 24) & 0xFF, a2 = (c2 >> 24) & 0xFF;
        int r1 = (c1 >> 16) & 0xFF, r2 = (c2 >> 16) & 0xFF;
        int g1 = (c1 >> 8) & 0xFF, g2 = (c2 >> 8) & 0xFF;
        int b1 = c1 & 0xFF, b2 = c2 & 0xFF;
        int a = (int)((a1 + (a2 - a1) * t) * alpha);
        int r = (int)(r1 + (r2 - r1) * t);
        int g = (int)(g1 + (g2 - g1) * t);
        int b = (int)(b1 + (b2 - b1) * t);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

}