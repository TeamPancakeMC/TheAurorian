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

    private final long stunStartTime = System.currentTimeMillis();
    private final int stunDuration;
    private float iconScale = 5.0f;
    private float iconRotation = 0.0f;
    private float progress = 1.0f;
    private String keyHint = "";
    private long keyHintTime = 0;
    private boolean isRecovering = false;
    private boolean hasClosed = false;
    private long recoverStartTime = 0;
    private float iconColorLerp = 0f;
    private float recoverAlpha = 1.0f;

    public StunEffectScreen(int stunDuration) {
        super(Component.empty());
        this.stunDuration = stunDuration;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int width = this.width;
        int height = this.height;
        long now = System.currentTimeMillis();

        // 2. 脉动暗角
        float vignetteAlpha = (float) (0.35 + 0.15 * Math.sin(now / 250.0));
        vignetteAlpha *= this.recoverAlpha;
        this.drawVignette(guiGraphics, width, height, vignetteAlpha);

        // 3. 屏幕抖动
        int baseCenterX = width / 2;
        int baseCenterY = height / 2 - 40;

        // 4. 中心眩晕图标（缩放、旋转、颜色渐变）
        float iconScaleNow = this.iconScale * this.recoverAlpha;
        Color iconColor = lerpColor(new Color(120, 200, 255), new Color(120, 255, 180), this.iconColorLerp);
        if (this.isRecovering) {
            iconColor = new Color(120, 255, 120);
        }

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(baseCenterX, baseCenterY, 0);
        poseStack.scale(iconScaleNow, iconScaleNow, iconScaleNow);
        poseStack.mulPose(Axis.ZP.rotationDegrees(this.iconRotation));
        float red = iconColor.getRed() / 255f;
        float green = iconColor.getGreen() / 255f;
        float blue = iconColor.getBlue() / 255f;
        guiGraphics.setColor(red, green, blue, this.recoverAlpha);
        int iconWidth = 18;
        int iconHeight = 18;
        guiGraphics.blit(STUN_ICON, -iconWidth / 2, -iconHeight / 2, 0, 0, iconWidth, iconHeight, iconWidth, iconHeight);
        poseStack.popPose();
        guiGraphics.setColor(1f, 1f, 1f, 1f);

        // 5. 进度环
        this.drawProgressRing(guiGraphics, baseCenterX, baseCenterY, 64, this.progress, this.recoverAlpha);

        // 6. 进度条
        int barWidth = 200, barHeight = 12;
        int barX = width / 2 - barWidth / 2;
        int barY = baseCenterY + 80;
        this.drawLoadingBar(guiGraphics, barX, barY, barWidth, barHeight, this.progress, this.recoverAlpha);

        // 7. 状态文字
        Component tip = this.isRecovering ? RECOVERING : STUNNING;
        int tipColor = this.isRecovering ? 0x77FF77 : 0xFFFFFF;
        guiGraphics.drawCenteredString(this.font, tip, width / 2, barY + 30, tipColor | (((int)(255 * this.recoverAlpha)) << 24));

        // 8. 按键锁定提示
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
        long elapsed = now - this.stunStartTime;
        if (this.minecraft != null) {
            LocalPlayer player = this.minecraft.player;
            if (player != null && player.hasEffect(TAMobEffects.STUN)) {
                MobEffectInstance instance = player.getEffect(TAMobEffects.STUN);
                if (instance != null) {
                    this.progress = Math.max(1.0f, 1.0f - (float) instance.duration / this.stunDuration);
                }
            } else {
                this.minecraft.setScreen(null);
            }
        }

        // 图标缩放动画
        if (elapsed < 600) {
            this.iconScale = 5.0f - 2.0f * (elapsed / 600.0f);
        } else {
            this.iconScale = 1.0f;
        }

        // 图标旋转速度随眩晕强度变化
        float iconRotationSpeed = 180.0f + 180.0f * this.progress;
        this.iconRotation += iconRotationSpeed / 20.0f;
        if (this.iconRotation > 360.0f) {
            this.iconRotation -= 360.0f;
        }

        // 图标颜色渐变
        this.iconColorLerp = (float)(0.5f + 0.5f * Math.sin(now / 1200.0));
        if (this.isRecovering) {
            this.iconColorLerp = 1.0f;
        }

        // 进入恢复阶段
        if (this.progress <= 0.0f && !this.isRecovering) {
            this.isRecovering = true;
            this.recoverStartTime = System.currentTimeMillis();
        }

        // 恢复阶段淡出
        if (this.isRecovering && !this.hasClosed) {
            long recoverElapsed = now - this.recoverStartTime;
            this.recoverAlpha = Math.max(0f, 1.0f - recoverElapsed / 800.0f);
            if (this.minecraft != null) {
                this.minecraft.setScreen(null);
                this.hasClosed = true;
            }
        }

        // 按键提示消失
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

    // 脉动暗角
    private void drawVignette(GuiGraphics graphics, int w, int h, float alpha) {
        int steps = 32;
        int cx = w / 2, cy = h / 2;
        int maxR = Math.max(w, h) / 2;
        for (int i = steps - 1; i >= 0; i--) {
            float r0 = (float)i / steps;
            float r1 = (float)(i + 1) / steps;
            graphics.fill(
                cx - (int)(maxR * r0), cy - (int)(maxR * r0),
                cx + (int)(maxR * r0), cy + (int)(maxR * r0),
                ((int)(255 * alpha * r1)) << 24);
        }
    }

    // 进度环
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

    // 进度条
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

    // 颜色插值
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