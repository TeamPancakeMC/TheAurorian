package cn.teampancake.theaurorian.event;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.ModArmorMaterials;
import cn.teampancake.theaurorian.registry.ModItems;
import cn.teampancake.theaurorian.utils.AurorianSteelHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class ItemEventSubscriber {

    @SubscribeEvent
    public static void onRenderTooltips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();
        if (stack.getItem() instanceof ITooltipsItem tooltipsItem && tooltipsItem.isHasTooltips()) {
            showTooltips(tooltip, ForgeRegistries.ITEMS.getKey(stack.getItem()));
        }
        if (stack.getItem() instanceof ArmorItem || stack.getItem() instanceof TieredItem) {
            ArmorItem armorItem = (ArmorItem) stack.getItem();
            TieredItem tieredItem = (TieredItem) stack.getItem();
            if (armorItem.getMaterial() == ModArmorMaterials.SPECTRAL) {
                showTooltips(tooltip, ForgeRegistries.ITEMS.getKey(armorItem));
            }
            boolean flag1 = armorItem.getMaterial().getRepairIngredient() == Ingredient.of(ModItems.AURORIAN_STEEL.get());
            boolean flag2 = tieredItem.getTier().getRepairIngredient() == Ingredient.of(ModItems.AURORIAN_STEEL.get());
            Level level = event.getEntity() == null ? null : event.getEntity().level();
            if ((flag1 || flag2)) {
                AurorianSteelHelper.getAurorianSteelInfo(stack, level, tooltip);
            }
        }
    }

    private static void showTooltips(List<Component> tooltip, ResourceLocation key) {
        String keyPart = "string.theaurorian.tooltip.";
        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable(keyPart + "shiftinfo").withStyle(ChatFormatting.ITALIC));
        } else if (key != null) {
            tooltip.add(Component.translatable(keyPart + key.getPath()));
        }
    }

}