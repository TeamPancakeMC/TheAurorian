package cn.teampancake.theaurorian.compat;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.CeruleanArrow;
import cn.teampancake.theaurorian.common.items.CrystalArrow;
import cn.teampancake.theaurorian.registry.TABlocks;
import cn.teampancake.theaurorian.registry.TAItems;
import cn.teampancake.theaurorian.utils.TACommonUtils;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Map;

@SuppressWarnings("SpellCheckingInspection")
@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class VanillaItemCompat {

    @SubscribeEvent
    public static void registerFurnaceFuels(FurnaceFuelBurnTimeEvent event) {
        Map<Item, Integer> map = Maps.newLinkedHashMap();
        map.put(TABlocks.SILENT_WOOD_CRAFTING_TABLE.get().asItem(), 300);
        map.put(TABlocks.SILENT_WOOD_LADDER.get().asItem(), 300);
        map.put(TAItems.SILENT_WOOD_STICK.get(), 100);
        map.put(TAItems.SILENT_WOOD_BOW.get(), 300);
        map.put(TAItems.AURORIAN_COAL.get(), 1500);
        for (Item item : map.keySet()) {
            if (event.getItemStack().getItem() == item) {
                event.setBurnTime(map.get(item));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class BusModEvents {

        @SubscribeEvent
        public static void registerCompostables(FMLCommonSetupEvent event) {

        }

        @SubscribeEvent
        public static void registerDispenser(FMLCommonSetupEvent event) {
            DispenserBlock.registerBehavior(TAItems.CERULEAN_ARROW.get(), new CeruleanArrow.Dispense());
            DispenserBlock.registerBehavior(TAItems.CRYSTAL_ARROW.get(), new CrystalArrow.Dispense());
        }

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerItemProperties(FMLClientSetupEvent event) {
            ItemProperties.register(TAItems.SILENT_WOOD_BOW.get(), AurorianMod.prefix("pull"), ((stack, level, entity, seed) ->
                    entity == null || entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F));
            ItemProperties.register(TAItems.SILENT_WOOD_BOW.get(), AurorianMod.prefix("pulling"), ((stack, level, entity, seed) ->
                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));
            ItemProperties.register(TAItems.KEEPERS_BOW.get(), AurorianMod.prefix("pull"), ((stack, level, entity, seed) ->
                    entity == null || entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F));
            ItemProperties.register(TAItems.KEEPERS_BOW.get(), AurorianMod.prefix("pulling"), ((stack, level, entity, seed) ->
                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));
            for (Item item : TACommonUtils.getKnownItems()) {
                if (item instanceof ShieldItem) {
                    ItemProperties.register(item, AurorianMod.prefix("blocking"), ((stack, level, entity, seed) ->
                            entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));
                }
            }
        }

    }

}