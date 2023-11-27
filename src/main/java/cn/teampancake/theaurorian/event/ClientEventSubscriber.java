package cn.teampancake.theaurorian.event;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.TAEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, value = Dist.CLIENT)
public class ClientEventSubscriber {

    private static final ResourceLocation RUNESTONE_KEEPER_BARS = AurorianMod.prefix("textures/gui/runestone_keeper_bars.png");
    private static final ResourceLocation SPIDER_MOTHER_BARS = AurorianMod.prefix("textures/gui/spider_mother_bars.png");
    private static final ResourceLocation MOON_QUEEN_BARS = AurorianMod.prefix("textures/gui/moon_queen_bars.png");

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
    private static void renderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event, ResourceLocation atlasLocation,
            Component description, int frameWidth, int frameHeight, int frameYOffset, int barWidth, int barHeight, int barYOffset, int textYOffset, int textColor) {
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

}