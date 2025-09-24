package cn.teampancake.theaurorian.client.gui;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public final class AurorianEventGui {

    public static final ResourceLocation TEXTURE = TheAurorian.prefix("textures/gui/aurorian_event.png");

    public static final int TEX_W = 512;
    public static final int TEX_H = 512;

    public static final int PAGE_W = 142;
    public static final int PAGE_H = 188;

    public enum Card {
        COMBAT_NIGHT("战斗夜", 0,   0,   PAGE_W, PAGE_H),
        PROTECTION_NIGHT("守护夜", 143, 0,   PAGE_W, PAGE_H),
        EXPLORATION_NIGHT("探索夜", 285, 0,   PAGE_W, PAGE_H),
        MINING_NIGHT("挖掘夜", 0,   189, PAGE_W, PAGE_H),
        GROWTH_NIGHT("生长夜", 143, 189, PAGE_W, PAGE_H);

        public final String displayName;
        public final int u;
        public final int v;
        public final int width;
        public final int height;

        Card(String displayName, int u, int v, int width, int height) {
            this.displayName = displayName;
            this.u = u;
            this.v = v;
            this.width = width;
            this.height = height;
        }

        public void blit(GuiGraphics gg, int dstX, int dstY) {
            gg.blit(TEXTURE, dstX, dstY, this.u, this.v, this.width, this.height, TEX_W, TEX_H);
        }
    }

    private AurorianEventGui() {}
} 