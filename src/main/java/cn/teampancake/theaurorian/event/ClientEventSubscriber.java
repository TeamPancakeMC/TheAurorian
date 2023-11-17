package cn.teampancake.theaurorian.event;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.ModEntityTypes;
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

    @SubscribeEvent
    public static void onRenderBossBars(CustomizeGuiOverlayEvent.BossEventProgress event) {
        if (event.getBossEvent().getName().getContents() instanceof TranslatableContents contents) {
            if (contents.getKey().equals(ModEntityTypes.RUNESTONE_KEEPER.get().getDescriptionId())) {
                event.setCanceled(true);
                Font font = Minecraft.getInstance().font;
                GuiGraphics graphics = event.getGuiGraphics();
                Component description = ModEntityTypes.RUNESTONE_KEEPER.get().getDescription();
                int guiWidth = graphics.guiWidth();
                int fontWidth = font.width(description);
                int strX = guiWidth / 2 - fontWidth / 2;
                int progress = (int) (182 * event.getBossEvent().getProgress());
                graphics.blit(RUNESTONE_KEEPER_BARS, (guiWidth - 186) / 2, event.getY() - 2, 0, 5, 186, 22);
                graphics.blit(RUNESTONE_KEEPER_BARS, (guiWidth - 180) / 2, event.getY() + 8, 0, 0, progress, 5);
                graphics.drawString(font, description, strX, event.getY() - 9, 16777215);
                event.setIncrement(25);
            }
        }
    }

}