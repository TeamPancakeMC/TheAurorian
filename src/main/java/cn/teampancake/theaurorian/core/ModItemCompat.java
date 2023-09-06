package cn.teampancake.theaurorian.core;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.CeruleanArrow;
import cn.teampancake.theaurorian.common.items.CrystalArrow;
import cn.teampancake.theaurorian.registry.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@SuppressWarnings("SpellCheckingInspection")
@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItemCompat {

    @SubscribeEvent
    public static void registerCompostables(FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerDispenser(FMLCommonSetupEvent event) {
        DispenserBlock.registerBehavior(ModItems.CERULEAN_ARROW.get(), new CeruleanArrow.Dispense());
        DispenserBlock.registerBehavior(ModItems.CRYSTAL_ARROW.get(), new CrystalArrow.Dispense());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerItemProperties(FMLClientSetupEvent event) {
        ItemProperties.register(ModItems.SILENT_WOOD_BOW.get(), AurorianMod.prefix("pull"), ((stack, level, entity, seed) ->
                entity == null || entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F));
        ItemProperties.register(ModItems.SILENT_WOOD_BOW.get(), AurorianMod.prefix("pulling"), ((stack, level, entity, seed) ->
                entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));
        ItemProperties.register(ModItems.KEEPERS_BOW.get(), AurorianMod.prefix("pull"), ((stack, level, entity, seed) ->
                entity == null || entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F));
        ItemProperties.register(ModItems.KEEPERS_BOW.get(), AurorianMod.prefix("pulling"), ((stack, level, entity, seed) ->
                entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));
    }

}