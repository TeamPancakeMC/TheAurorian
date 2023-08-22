package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static cn.teampancake.theaurorian.utils.ModItemRegUtils.food;
import static cn.teampancake.theaurorian.utils.ModItemRegUtils.normal;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AurorianMod.MOD_ID);

    public static final RegistryObject<Item> CUP = normal("cup", true);
    public static final RegistryObject<Item> AURORIAN_COAL = normal("aurorian_coal", true);
    public static final RegistryObject<Item> AURORIANITE_INGOT = normal("aurorianite_ingot", true);
    public static final RegistryObject<Item> AURORIAN_STEEL = normal("aurorian_steel", true);
    public static final RegistryObject<Item> CERULEAN_INGOT = normal("cerulean_ingot", true);
    public static final RegistryObject<Item> CRYSTALLINE_INGOT = normal("crystalline_ingot", true);
    public static final RegistryObject<Item> MOONSTONE_INGOT = normal("moonstone_ingot", true);
    public static final RegistryObject<Item> UMBRA_INGOT = normal("umbra_ingot", true);
    public static final RegistryObject<Item> LAVENDER = normal("lavender", true);
    public static final RegistryObject<Item> MOON_TEMPLE_CELL_KEY_FRAGMENT = normal("moon_temple_cell_key_fragment", true);
    public static final RegistryObject<Item> AURORIAN_STEEL_NUGGET = normal("aurorian_steel_nugget", true);
    public static final RegistryObject<Item> CERULEAN_NUGGET = normal("cerulean_nugget", true);
    public static final RegistryObject<Item> AURORIAN_COAL_NUGGET = normal("aurorian_coal_nugget", true);
    public static final RegistryObject<Item> MOONSTONE_NUGGET = normal("moonstone_nugget", true);
    public static final RegistryObject<Item> PLANTFIBER = normal("plant_fiber", true);
    public static final RegistryObject<Item> SCRAP_AURORIANITE = normal("scrap_aurorianite", true);
    public static final RegistryObject<Item> SCRAP_CRYSTALLINE = normal("scrap_crystalline", true);
    public static final RegistryObject<Item> SCRAP_UMBRA = normal("scrap_umbra", true);
    public static final RegistryObject<Item> SPECTRAL_SILK = normal("spectral_silk", true);
    public static final RegistryObject<Item> TROPHY_KEEPER = normal("trophy_keeper", true);
    public static final RegistryObject<Item> TROPHY_MOON_QUEEN = normal("trophy_moon_queen", true);
    public static final RegistryObject<Item> TROPHY_SPIDER = normal("trophy_spider", true);
    public static final RegistryObject<Item> SILK_BERRY_JAM = food("silk_berry_jam", 2, 0.5F, false);
    public static final RegistryObject<Item> SILK_BERRY_JAM_SANDWICH = food("silk_berry_jam_sandwich", 6, 0.9F, true);
    public static final RegistryObject<Item> AURORIAN_PORK = food("aurorian_pork", 3, 0.3F, false);
    public static final RegistryObject<Item> AURORIAN_BACON = food("aurorian_bacon", 2, 0.8F, true);
    public static final RegistryObject<Item> COOKED_AURORIAN_PORK = food("cooked_aurorian_pork", 8, 0.8F, true);
    public static final RegistryObject<Item> AURORIAN_SLIMEBALL = food("aurorian_slimeball", 1, 0.2F, false);
    public static final RegistryObject<Item> SILK_SHROOM_STEW = food("silk_shroom_stew", 6, 1F, false);
    public static final RegistryObject<Item> LAVENDER_BREAD = food("lavender_bread", 4, 0.4F, false);
    public static final RegistryObject<Item> SOULLESS_FLESH = food("soulless_flesh", 2, 0.1F, false);
    public static final RegistryObject<Item> ABSORPTION_ORB = ITEMS.register("absorption_orb", AbsorptionOrbItem::new);

    public static void appendTooltip(ItemStack stack, List<Component> tooltip){
        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("string.theaurorian.tooltip.shiftinfo").withStyle(ChatFormatting.ITALIC));
        } else {
            tooltip.add(Component.translatable("string.theaurorian.tooltip." + stack.getItem()));
        }
    }

}