package cn.teampancake.theaurorian.compat;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VanillaBlockCompat {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerColors(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerFlammable(FMLCommonSetupEvent event) {
        FireBlock fireBlock = (FireBlock)Blocks.FIRE;
        fireBlock.setFlammable(ModBlocks.SILENT_TREE_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(ModBlocks.SILENT_WOOD_STAIRS.get(), 5, 20);
        fireBlock.setFlammable(ModBlocks.SILENT_TREE_LOG.get(), 5, 5);
        fireBlock.setFlammable(ModBlocks.SILENT_TREE_WOOD.get(), 5, 5);
        fireBlock.setFlammable(ModBlocks.SILENT_TREE_LEAVES.get(), 30, 60);
        fireBlock.setFlammable(ModBlocks.WEEPING_WILLOW_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(ModBlocks.WEEPING_WILLOW_STAIRS.get(), 5, 20);
        fireBlock.setFlammable(ModBlocks.WEEPING_WILLOW_LOG.get(), 5, 5);
        fireBlock.setFlammable(ModBlocks.WEEPING_WILLOW_WOOD.get(), 5, 5);
        fireBlock.setFlammable(ModBlocks.WEEPING_WILLOW_LEAVES.get(), 30, 60);
        fireBlock.setFlammable(ModBlocks.AURORIAN_GRASS.get(), 60, 100);
        fireBlock.setFlammable(ModBlocks.AURORIAN_GRASS_LIGHT.get(), 60, 100);
        fireBlock.setFlammable(ModBlocks.PETUNIA_PLANT.get(), 60, 100);
    }

}