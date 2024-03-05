package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.renderer.block.MoonlightForgeRenderer;
import cn.teampancake.theaurorian.client.renderer.block.SilentCampfireRender;
import cn.teampancake.theaurorian.client.renderer.block.SilentWoodChestRenderer;
import cn.teampancake.theaurorian.common.blocks.entity.*;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
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
    public static final RegistryObject<BlockEntityType<SilentCampfireBlockEntity>> SILENT_CAMPFIRE =  BLOCK_ENTITY_TYPES.register("silent_campfire",
            () -> BlockEntityType.Builder.of(SilentCampfireBlockEntity::new, TABlocks.SILENT_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<DungeonStoneGateBlockEntity>> DUNGEON_STONE_GATE = BLOCK_ENTITY_TYPES.register("dungeon_stone_gate",
            () -> BlockEntityType.Builder.of(DungeonStoneGateBlockEntity::new, TABlocks.RUNE_STONE_GATE.get(), TABlocks.MOON_TEMPLE_GATE.get(),
                    TABlocks.DARK_STONE_GATE.get(), TABlocks.RUNE_STONE_LOOT_GATE.get(), TABlocks.MOON_TEMPLE_CELL_GATE.get(),
                    TABlocks.DARK_STONE_GATE_KEYHOLE.get(), TABlocks.MOON_TEMPLE_GATE_KEYHOLE.get(), TABlocks.RUNE_STONE_GATE_KEYHOLE.get(),
                    TABlocks.RUNE_STONE_LOOT_GATE_KEYHOLE.get(), TABlocks.MOON_TEMPLE_CELL_GATE_KEYHOLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<TASignBlockEntity>> TA_SIGN = BLOCK_ENTITY_TYPES.register("ta_sign",
            () -> BlockEntityType.Builder.of(TASignBlockEntity::new, TABlocks.SILENT_WOOD_SIGN.get(), TABlocks.WEEPING_WILLOW_WOOD_SIGN.get(),
                    TABlocks.CURTAIN_WOOD_SIGN.get(), TABlocks.CURSED_FROST_WOOD_SIGN.get(), TABlocks.SILENT_WOOD_WALL_SIGN.get(),
                    TABlocks.WEEPING_WILLOW_WOOD_WALL_SIGN.get(), TABlocks.CURTAIN_WOOD_WALL_SIGN.get(),
                    TABlocks.CURSED_FROST_WOOD_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<TAHangingSignBlockEntity>> TA_HANGING_SIGN = BLOCK_ENTITY_TYPES.register("ta_hanging_sign",
            () -> BlockEntityType.Builder.of(TAHangingSignBlockEntity::new, TABlocks.SILENT_WOOD_HANGING_SIGN.get(), TABlocks.WEEPING_WILLOW_WOOD_HANGING_SIGN.get(),
                    TABlocks.CURTAIN_WOOD_HANGING_SIGN.get(), TABlocks.CURSED_FROST_WOOD_HANGING_SIGN.get(), TABlocks.SILENT_WOOD_WALL_HANGING_SIGN.get(),
                    TABlocks.WEEPING_WILLOW_WOOD_WALL_HANGING_SIGN.get(), TABlocks.CURTAIN_WOOD_WALL_HANGING_SIGN.get(),
                    TABlocks.CURSED_FROST_WOOD_WALL_HANGING_SIGN.get()).build(null));

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(SILENT_WOOD_CHEST.get(), SilentWoodChestRenderer::new);
        event.registerBlockEntityRenderer(MOONLIGHT_FORGE.get(), MoonlightForgeRenderer::new);
        event.registerBlockEntityRenderer(SILENT_CAMPFIRE.get(), SilentCampfireRender::new);
        event.registerBlockEntityRenderer(TA_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerBlockEntityRenderer(TA_SIGN.get(), SignRenderer::new);
    }

}