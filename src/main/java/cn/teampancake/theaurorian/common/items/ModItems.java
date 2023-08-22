package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AurorianMod.MOD_ID);

    public static final RegistryObject<Item> ABSORPTION_ORB;

    public ModItems() {
    }

    static {
        ABSORPTION_ORB = ITEMS.register("absorptionorb", AbsorptionOrbItem::new);
    }

    public static void appendTooltip(ItemStack stack, List<Component> tooltip){
        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("string.theaurorian.tooltip.shiftinfo").withStyle(ChatFormatting.ITALIC));
        } else {
            tooltip.add(Component.translatable("string.theaurorian.tooltip."+stack.getItem()));
        }
    }
}