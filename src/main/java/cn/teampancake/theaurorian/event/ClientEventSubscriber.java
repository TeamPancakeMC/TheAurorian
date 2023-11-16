package cn.teampancake.theaurorian.event;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.ModEntityTypes;
import net.minecraft.client.gui.GuiGraphics;
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
                GuiGraphics graphics = event.getGuiGraphics();
                graphics.blit(RUNESTONE_KEEPER_BARS, (graphics.guiWidth() - 186) / 2, event.getY() - 9, 0, 5, 186, 22);
                graphics.blit(RUNESTONE_KEEPER_BARS, (graphics.guiWidth() - 182) / 2, event.getY() - 9 + 10, 0, 0, (int) (182 * event.getBossEvent().getProgress()), 5);
                event.setIncrement(22);
            }
        }
    }

}