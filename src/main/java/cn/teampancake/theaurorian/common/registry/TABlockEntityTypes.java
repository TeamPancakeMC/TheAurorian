package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.renderer.block.MoonlightForgeRenderer;
import cn.teampancake.theaurorian.client.renderer.block.MysteriumWoolBedRenderer;
import cn.teampancake.theaurorian.client.renderer.block.SilentCampfireRender;
import cn.teampancake.theaurorian.client.renderer.block.SilentWoodChestRenderer;
import cn.teampancake.theaurorian.common.blocks.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings({"ConstantConditions", "SpellCheckingInspection"})
@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TABlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AurorianMod.MOD_ID);
    public static final RegistryObject<BlockEntityType<AurorianFurnaceBlockEntity>> AURORIAN_FURNACE = BLOCK_ENTITY_TYPES.register("aurorian_furnace",
            () -> BlockEntityType.Builder.of(AurorianFurnaceBlockEntity::new, TABlocks.AURORIAN_FURNACE.get()).build(null));
    public static final RegistryObject<BlockEntityType<MoonlightForgeBlockEntity>> MOONLIGHT_FORGE = BLOCK_ENTITY_TYPES.register("moonlight_forge",
            () -> BlockEntityType.Builder.of(MoonlightForgeBlockEntity::new, TABlocks.MOONLIGHT_FORGE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ScrapperBlockEntity>> SCRAPPER = BLOCK_ENTITY_TYPES.register("scrapper",
            () -> BlockEntityType.Builder.of(ScrapperBlockEntity::new, TABlocks.SCRAPPER.get()).build(null));
    public static final RegistryObject<BlockEntityType<SilentWoodChestBlockEntity>> SILENT_WOOD_CHEST = BLOCK_ENTITY_TYPES.register("silent_wood_chest",
            () -> BlockEntityType.Builder.of(SilentWoodChestBlockEntity::new, TABlocks.SILENT_WOOD_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<SilentWoodCraftingBlockEntity>> SILENT_WOOD_CRAFTING_TABLE = BLOCK_ENTITY_TYPES.register("silent_wood_crafting_table",
            () -> BlockEntityType.Builder.of(SilentWoodCraftingBlockEntity::new, TABlocks.AURORIAN_CRAFTING_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<MysteriumWoolBedBlockEntity>> MYSTERIUM_WOOL_BED = BLOCK_ENTITY_TYPES.register("mysterium_wool_bed",
            () -> BlockEntityType.Builder.of(MysteriumWoolBedBlockEntity::new, TABlocks.MYSTERIUM_WOOL_BED.get()).build(null));
    public static final RegistryObject<BlockEntityType<SilentCampfireBlockEntity>> SILENT_CAMPFIRE =  BLOCK_ENTITY_TYPES.register("silent_campfire",
            ()-> BlockEntityType.Builder.of(SilentCampfireBlockEntity::new,TABlocks.SILENT_CAMPFIRE.get()).build(null));

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(MOONLIGHT_FORGE.get(), MoonlightForgeRenderer::new);
        event.registerBlockEntityRenderer(SILENT_WOOD_CHEST.get(), SilentWoodChestRenderer::new);
        event.registerBlockEntityRenderer(MYSTERIUM_WOOL_BED.get(), MysteriumWoolBedRenderer::new);
        event.registerBlockEntityRenderer(SILENT_CAMPFIRE.get(), SilentCampfireRender::new);
    }

}