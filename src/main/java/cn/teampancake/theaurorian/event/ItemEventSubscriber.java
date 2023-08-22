package cn.teampancake.theaurorian.event;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.AbstractTooltipsItem;
import cn.teampancake.theaurorian.utils.ModCommonUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class ItemEventSubscriber {

    @SubscribeEvent
    public static void onRenderTooltips(ItemTooltipEvent event) {
        for (Item item : ModCommonUtils.getKnownItems()) {
            if (item instanceof AbstractTooltipsItem tooltipsItem) {
                ResourceLocation key = ForgeRegistries.ITEMS.getKey(tooltipsItem);
                if (tooltipsItem.isHasTooltips() && key != null) {
                    List<Component> tooltip = event.getToolTip();
                    String keyPart = "string.theaurorian.tooltip.";
                    if (!Screen.hasShiftDown()) {
                        tooltip.add(Component.translatable(keyPart + "shiftinfo").withStyle(ChatFormatting.ITALIC));
                    } else {
                        tooltip.add(Component.translatable(keyPart + key.getPath()));
                    }
                }
            }
        }
    }

}
