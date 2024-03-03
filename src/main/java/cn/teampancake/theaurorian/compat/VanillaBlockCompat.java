package cn.teampancake.theaurorian.compat;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.state.TAWoodType;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VanillaBlockCompat {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {

    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerWoodType(FMLClientSetupEvent event) {
        Sheets.addWoodType(TAWoodType.SILENT_WOOD);
        Sheets.addWoodType(TAWoodType.WEEPING_WILLOW);
        Sheets.addWoodType(TAWoodType.CURTAIN_TREE);
        Sheets.addWoodType(TAWoodType.CURSED_FROST_TREE);
    }

    @SubscribeEvent
    public static void registerFlammable(FMLCommonSetupEvent event) {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        fireBlock.setFlammable(TABlocks.SILENT_TREE_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.SILENT_WOOD_STAIRS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.SILENT_TREE_LOG.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.SILENT_TREE_WOOD.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.SILENT_TREE_LEAVES.get(), 30, 60);
        fireBlock.setFlammable(TABlocks.WEEPING_WILLOW_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.WEEPING_WILLOW_STAIRS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.WEEPING_WILLOW_LOG.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.WEEPING_WILLOW_WOOD.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.WEEPING_WILLOW_LEAVES.get(), 30, 60);
        fireBlock.setFlammable(TABlocks.CURTAIN_TREE_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.CURTAIN_WOOD_STAIRS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.CURTAIN_TREE_LOG.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.CURTAIN_TREE_WOOD.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.CURTAIN_TREE_LEAVES.get(), 30, 30);
        fireBlock.setFlammable(TABlocks.CURSED_FROST_TREE_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.CURSED_FROST_TREE_LOG.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.CURSED_FROST_TREE_WOOD.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.CURSED_FROST_TREE_LEAVES.get(), 30, 30);
        fireBlock.setFlammable(TABlocks.AURORIAN_GRASS.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.AURORIAN_GRASS_LIGHT.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.LAVENDER_PLANT.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.PETUNIA_PLANT.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.AURORIAN_FLOWER_1.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.AURORIAN_FLOWER_2.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.AURORIAN_FLOWER_3.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.EQUINOX_FLOWER.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.WICK_GRASS.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.TALL_WICK_GRASS.get(), 60, 100);
    }

}