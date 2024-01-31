package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.*;
import cn.teampancake.theaurorian.common.blocks.base.*;
import cn.teampancake.theaurorian.common.blocks.state.VerticalSlabShape;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("SpellCheckingInspection")
public class TABlockStateProvider extends BlockStateProvider {

    private static final ResourceLocation CUTOUT = new ResourceLocation("cutout");
    private static final ResourceLocation CUTOUT_MIPPED = new ResourceLocation("cutout_mipped");
    private static final ResourceLocation TRANSLUCENT = new ResourceLocation("translucent");
    private static final Map<Direction, Integer> DIRECTION_WITH_ROTATION =
            Map.of(Direction.NORTH, 0, Direction.EAST, 90,
            Direction.SOUTH, 180, Direction.WEST, 270);

    public TABlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AurorianMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.registerLiquidStates();
        this.registerScrapperState();
        this.registerCraftingTableState();
        this.registerMysticalBarrierState();
        this.registerAurorianPortalState();
        this.registerAurorianFurnaceState();
        this.registerAurorianFarmlandState();
        this.registerSilentWoodLadderState();
        this.simpleBlock(TABlocks.GEODE_ORE.get());
        this.simpleBlock(TABlocks.CERULEAN_ORE.get());
        this.simpleBlock(TABlocks.MOONSTONE_ORE.get());
        this.simpleBlock(TABlocks.AURORIAN_DIRT.get());
        this.simpleBlock(TABlocks.AURORIAN_STONE.get());
        this.simpleBlock(TABlocks.AURORIAN_COAL_ORE.get());
        this.simpleBlock(TABlocks.AURORIAN_STONE_BRICKS.get());
        this.simpleBlock(TABlocks.AURORIAN_COBBLESTONE.get());
        this.simpleBlock(TABlocks.AURORIAN_GRANITE.get());
        this.simpleBlock(TABlocks.AURORIAN_DIORITE.get());
        this.simpleBlock(TABlocks.AURORIAN_ANDESITE.get());
        this.simpleBlock(TABlocks.AURORIAN_BARRIER_STONE.get());
        this.simpleBlock(TABlocks.AURORIAN_PORTAL_FRAME_BRICKS.get());
        this.simpleBlock(TABlocks.AURORIAN_PERIDOTITE.get());
        this.simpleBlock(TABlocks.MOON_SAND.get());
        this.simpleBlock(TABlocks.MOON_SAND_RIVER.get());
        this.simpleBlock(TABlocks.MOON_SANDSTONE.get());
        this.simpleBlock(TABlocks.CUT_MOON_SANDSTONE.get());
        this.simpleBlock(TABlocks.SMOOTH_MOON_SANDSTONE.get());
        this.simpleBlock(TABlocks.BRIGHT_MOON_SAND.get());
        this.simpleBlock(TABlocks.BRIGHT_MOON_SANDSTONE.get());
        this.simpleBlock(TABlocks.RUNE_STONE.get());
        this.simpleBlock(TABlocks.SMOOTH_RUNE_STONE.get());
        this.simpleBlock(TABlocks.CHISELED_RUNE_STONE.get());
        this.simpleBlock(TABlocks.AURORIAN_CASTLE_RUNE_STONE.get());
        this.simpleBlock(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE.get());
        this.simpleBlock(TABlocks.CERULEAN_CASTLE_RUNE_STONE.get());
        this.simpleBlock(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE.get());
        this.simpleBlock(TABlocks.MOON_CASTLE_RUNE_STONE.get());
        this.simpleBlock(TABlocks.TRANSPARENT_RUNE_STONE.get());
        this.simpleBlock(TABlocks.UMBRA_CASTLE_RUNE_STONE.get());
        this.simpleBlock(TABlocks.MOON_TEMPLE_BRICKS.get());
        this.simpleBlock(TABlocks.DARK_STONE_BRICKS.get());
        this.simpleBlock(TABlocks.DARK_STONE_FANCY.get());
        this.simpleBlock(TABlocks.DARK_STONE_LAYERS.get());
        this.simpleBlock(TABlocks.SMOOTH_DARK_STONE_BRICKS.get());
        this.simpleBlock(TABlocks.CHISELED_DARK_STONE_BRICKS.get());
        this.simpleBlock(TABlocks.SMOOTH_MOON_TEMPLE_BRICKS.get());
        this.simpleBlock(TABlocks.CHISELED_MOON_TEMPLE_BRICKS.get());
        this.simpleBlock(TABlocks.SMOOTH_AURORIAN_PERIDOTITE.get());
        this.simpleBlock(TABlocks.RUNE_STONE_LAMP.get());
        this.simpleBlock(TABlocks.DARK_STONE_LAMP.get());
        this.simpleBlock(TABlocks.MOON_TEMPLE_LAMP.get());
        this.simpleBlock(TABlocks.VOID_STONE.get());
        this.simpleBlock(TABlocks.RUNE_CRYSTAL.get());
        this.simpleBlock(TABlocks.CERULEAN_BLOCK.get());
        this.simpleBlock(TABlocks.MOONSTONE_BLOCK.get());
        this.simpleBlock(TABlocks.AURORIAN_COAL_BLOCK.get());
        this.simpleBlock(TABlocks.AURORIAN_STEEL_BLOCK.get());
        this.simpleBlock(TABlocks.RUNE_STONE_GATE.get());
        this.simpleBlock(TABlocks.DARK_STONE_GATE.get());
        this.simpleBlock(TABlocks.MOON_TEMPLE_GATE.get());
        this.simpleBlock(TABlocks.RUNE_STONE_LOOT_GATE.get());
        this.simpleBlock(TABlocks.MOON_TEMPLE_CELL_GATE.get());
        this.simpleBlock(TABlocks.RUNE_STONE_GATE_KEYHOLE.get());
        this.simpleBlock(TABlocks.DARK_STONE_GATE_KEYHOLE.get());
        this.simpleBlock(TABlocks.MOON_TEMPLE_GATE_KEYHOLE.get());
        this.simpleBlock(TABlocks.RUNE_STONE_LOOT_GATE_KEYHOLE.get());
        this.simpleBlock(TABlocks.MOON_TEMPLE_CELL_GATE_KEYHOLE.get());
        this.simpleBlock(TABlocks.UMBRA_STONE.get());
        this.simpleBlock(TABlocks.UMBRA_STONE_CRACKED.get());
        this.simpleBlock(TABlocks.UMBRA_STONE_ROOF_TILES.get());
        this.simpleBlock(TABlocks.INDIGO_MUSHROOM_CRYSTAL.get());
        this.simpleBlock(TABlocks.SILENT_TREE_PLANKS.get());
        this.simpleBlock(TABlocks.WEEPING_WILLOW_PLANKS.get());
        this.simpleBlock(TABlocks.CURTAIN_TREE_PLANKS.get());
        this.simpleBlock(TABlocks.SILENT_WOOD_CHEST.get(), this.models().getBuilder(this.name(TABlocks.SILENT_WOOD_CHEST.get()))
                .texture("particle", this.modLoc("block/" + this.name(TABlocks.SILENT_TREE_PLANKS.get()))));
        this.simpleBlock(TABlocks.SILENT_WOOD_TORCH.get(), this.models().torch(this.name(TABlocks.SILENT_WOOD_TORCH.get()),
                this.blockTexture(TABlocks.SILENT_WOOD_TORCH.get())).renderType(CUTOUT));
        this.simpleBlock(TABlocks.MOON_TORCH.get(), this.models().torch(this.name(TABlocks.MOON_TORCH.get()),
                this.blockTexture(TABlocks.MOON_TORCH.get())).renderType(CUTOUT));
        this.logBlock((RotatedPillarBlock) TABlocks.RUNE_STONE_PILLAR.get());
        this.logBlock((RotatedPillarBlock) TABlocks.DARK_STONE_PILLAR.get());
        this.logBlock((RotatedPillarBlock) TABlocks.MOON_TEMPLE_PILLAR.get());
        this.logBlock((RotatedPillarBlock) TABlocks.STRIPPED_SILENT_TREE_LOG.get());
        this.logBlock((RotatedPillarBlock) TABlocks.STRIPPED_WEEPING_WILLOW_LOG.get());
        this.logBlock((RotatedPillarBlock) TABlocks.STRIPPED_CURTAIN_TREE_LOG.get());
        this.logBlock((RotatedPillarBlock) TABlocks.SILENT_TREE_LOG.get());
        this.logBlock((RotatedPillarBlock) TABlocks.WEEPING_WILLOW_LOG.get());
        this.logBlock((RotatedPillarBlock) TABlocks.CURTAIN_TREE_LOG.get());
        this.simpleBlock(TABlocks.SILENT_TREE_SAPLING.get(),
                this.models().cross(this.name(TABlocks.SILENT_TREE_SAPLING.get()),
                        this.blockTexture(TABlocks.SILENT_TREE_SAPLING.get())).renderType(CUTOUT));
        this.simpleBlock(TABlocks.CURTAIN_TREE_SAPLING.get(),
                this.models().cross(this.name(TABlocks.CURTAIN_TREE_SAPLING.get()),
                        this.blockTexture(TABlocks.CURTAIN_TREE_SAPLING.get())).renderType(CUTOUT));
        this.axisBlock((RotatedPillarBlock) TABlocks.STRIPPED_SILENT_TREE_WOOD.get(),
                this.modLoc("block/stripped_silent_tree_log"),
                this.modLoc("block/stripped_silent_tree_log"));
        this.axisBlock((RotatedPillarBlock) TABlocks.STRIPPED_WEEPING_WILLOW_WOOD.get(),
                this.modLoc("block/stripped_weeping_willow_log"),
                this.modLoc("block/stripped_weeping_willow_log"));
        this.axisBlock((RotatedPillarBlock) TABlocks.STRIPPED_CURTAIN_TREE_WOOD.get(),
                this.modLoc("block/stripped_curtain_tree_log"),
                this.modLoc("block/stripped_curtain_tree_log"));
        this.axisBlock((RotatedPillarBlock) TABlocks.SILENT_TREE_WOOD.get(),
                this.modLoc("block/silent_tree_log"),
                this.modLoc("block/silent_tree_log"));
        this.axisBlock((RotatedPillarBlock) TABlocks.WEEPING_WILLOW_WOOD.get(),
                this.modLoc("block/weeping_willow_log"),
                this.modLoc("block/weeping_willow_log"));
        this.axisBlock((RotatedPillarBlock) TABlocks.CURTAIN_TREE_WOOD.get(),
                this.modLoc("block/curtain_tree_log"),
                this.modLoc("block/curtain_tree_log"));
        this.simpleBlock(TABlocks.AURORIAN_GRASS_BLOCK.get(),
                this.models().cubeBottomTop("aurorian_grass_block",
                        this.modLoc("block/aurorian_grass_block"),
                        this.modLoc("block/aurorian_dirt"),
                        this.modLoc("block/aurorian_grass_block_top")));
        this.simpleBlock(TABlocks.LIGHT_AURORIAN_GRASS_BLOCK.get(),
                this.models().cubeBottomTop("light_aurorian_grass_block",
                        this.modLoc("block/light_aurorian_grass_block"),
                        this.modLoc("block/aurorian_dirt"),
                        this.modLoc("block/light_aurorian_grass_block_top")));
        this.simpleBlock(TABlocks.RED_AURORIAN_GRASS_BLOCK.get(),
                this.models().cubeBottomTop("red_aurorian_grass_block",
                        this.modLoc("block/red_aurorian_grass_block"),
                        this.modLoc("block/aurorian_dirt"),
                        this.modLoc("block/red_aurorian_grass_block_top")));
        this.registerLightPlantStates(TABlocks.WICK_GRASS.get());
        this.registerLightPlantStates(TABlocks.AURORIAN_WATER_GRASS.get());
        this.registerDoublePlantStates(TABlocks.TALL_AURORIAN_GRASS.get());
        this.registerDoublePlantStates(TABlocks.TALL_LAVENDER_PLANT.get());
        this.registerDoubleLightPlantStates(TABlocks.TALL_WICK_GRASS.get());
        this.registerDoubleLightPlantStates(TABlocks.TALL_AURORIAN_WATER_GRASS.get());
        this.registerDoubleLightPlantStates(TABlocks.TALL_AURORIAN_GRASS_LIGHT.get());
        this.registerWaterSurfacePlantStates(TABlocks.AURORIAN_LILY_PAD.get());
        this.registerWaterSurfacePlantStates(TABlocks.AURORIAN_WATER_MUSHROOM.get());
        this.simpleBlockWithRenderType(TABlocks.MOON_GLASS.get(), TRANSLUCENT);
        this.simpleBlockWithRenderType(TABlocks.AURORIAN_GLASS.get(), TRANSLUCENT);
        this.simpleBlockWithRenderType(TABlocks.DARK_STONE_GLASS.get(), TRANSLUCENT);
        this.simpleBlockWithRenderType(TABlocks.SILENT_TREE_LEAVES.get(), CUTOUT_MIPPED);
        this.simpleBlockWithRenderType(TABlocks.WEEPING_WILLOW_LEAVES.get(), CUTOUT_MIPPED);
        this.simpleBlockWithRenderType(TABlocks.CURTAIN_TREE_LEAVES.get(), CUTOUT_MIPPED);
        this.paneBlockWithRenderType((IronBarsBlock) TABlocks.MOON_GLASS_PANE.get(),
                this.blockTexture(TABlocks.MOON_GLASS.get()),
                this.blockTexture(TABlocks.MOON_GLASS_PANE.get()), TRANSLUCENT);
        this.paneBlockWithRenderType((IronBarsBlock) TABlocks.AURORIAN_GLASS_PANE.get(),
                this.blockTexture(TABlocks.AURORIAN_GLASS.get()),
                this.blockTexture(TABlocks.AURORIAN_GLASS_PANE.get()), TRANSLUCENT);
        this.paneBlockWithRenderType((IronBarsBlock) TABlocks.DARK_STONE_GLASS_PANE.get(),
                this.blockTexture(TABlocks.DARK_STONE_GLASS.get()),
                this.blockTexture(TABlocks.DARK_STONE_GLASS_PANE.get()), TRANSLUCENT);
        this.registerBarStates(TABlocks.RUNE_STONE_BARS.get());
        this.registerBarStates(TABlocks.DARK_STOME_BARS.get());
        this.registerBarStates(TABlocks.MOON_TEMPLE_BARS.get());
        this.registerWallTorchStates(TABlocks.MOON_WALL_TORCH.get());
        this.registerWallTorchStates(TABlocks.SILENT_WOOD_WALL_TORCH.get());
        this.registerCropStates(TABlocks.LAVENDER_CROP.get());
        this.registerCropStates(TABlocks.SILK_BERRY_CROP.get());
        this.registerCrossStates(TABlocks.BLUEBERRY_BUSH.get());
        this.registerPlantStates(TABlocks.AURORIAN_FLOWER_1.get());
        this.registerPlantStates(TABlocks.AURORIAN_FLOWER_2.get());
        this.registerPlantStates(TABlocks.AURORIAN_FLOWER_3.get());
        this.registerPlantStates(TABlocks.EQUINOX_FLOWER.get());
        this.registerPlantStates(TABlocks.LAVENDER_PLANT.get());
        this.registerPlantStates(TABlocks.PETUNIA_PLANT.get());
        this.registerPlantStates(TABlocks.INDIGO_MUSHROOM.get());
        this.registerPlantStates(TABlocks.AURORIAN_GRASS.get());
        this.registerPlantStates(TABlocks.AURORIAN_GRASS_LIGHT.get());
        this.registerMushroomStates(TABlocks.INDIGO_MUSHROOM_BLOCK.get());
        this.registerMushroomStates(TABlocks.INDIGO_MUSHROOM_STEM.get());
        this.registerLuminousStates(TABlocks.LUMINOUS_AURORIAN_CASTLE_RUNE_STONE.get(), TABlocks.AURORIAN_CASTLE_RUNE_STONE.get());
        this.registerLuminousStates(TABlocks.LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE.get(), TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE.get());
        this.registerLuminousStates(TABlocks.LUMINOUS_CERULEAN_CASTLE_RUNE_STONE.get(), TABlocks.CERULEAN_CASTLE_RUNE_STONE.get());
        this.registerLuminousStates(TABlocks.LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE.get(), TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE.get());
        this.registerLuminousStates(TABlocks.LUMINOUS_MOON_CASTLE_RUNE_STONE.get(), TABlocks.MOON_CASTLE_RUNE_STONE.get());
        for (Block block : TACommonUtils.getKnownBlocks()) {
            if (block instanceof PressurePlateBlockWithBase pressurePlateBlock) {
                this.pressurePlateBlock(pressurePlateBlock, this.blockTexture(pressurePlateBlock.getBase()));
            } else if (block instanceof FenceGateBlockWithBase fenceGateBlock) {
                this.fenceGateBlockWithRenderType(fenceGateBlock, this.blockTexture(fenceGateBlock.getBase()), CUTOUT);
            } else if (block instanceof TrapDoorBlockWithBase trapDoorBlock) {
                ResourceLocation texture = this.blockTexture(trapDoorBlock.getBase());
                this.trapdoorBlockWithRenderType(trapDoorBlock, texture, Boolean.TRUE, CUTOUT);
                this.simpleBlockItem(trapDoorBlock, this.models().trapdoorBottom(this.name(trapDoorBlock), texture));
            } else if (block instanceof ButtonBlockWithBase buttonBlock) {
                ResourceLocation texture = this.blockTexture(buttonBlock.getBase());
                this.buttonBlock(buttonBlock, texture);
                this.simpleBlockItem(buttonBlock, this.models().buttonInventory(this.name(buttonBlock), texture));
            } else if (block instanceof StairBlock stairBlock) {
                this.stairsBlock(stairBlock, this.blockTexture(stairBlock.base));
            } else if (block instanceof FenceBlockWithBase fenceBlock) {
                ResourceLocation texture = this.blockTexture(fenceBlock.getBase());
                this.fenceBlockWithRenderType(fenceBlock, texture, CUTOUT);
                this.simpleBlockItem(fenceBlock, this.models().fenceInventory(this.name(fenceBlock), texture));
            } else if (block instanceof DoorBlockWithBase doorBlock) {
                String name = "block/" + this.name(doorBlock) + "_";
                this.doorBlockWithRenderType(doorBlock, this.modLoc(name + "bottom"), this.modLoc(name + "top"), CUTOUT);
            } else if (block instanceof SlabBlockWithBase slabBlock) {
                ResourceLocation texture = this.blockTexture(slabBlock.getBase());
                this.slabBlock(slabBlock, texture, texture);
            } else if (block instanceof WallBlockWithBase wallBlock) {
                ResourceLocation texture = this.blockTexture(wallBlock.getBase());
                this.wallBlock(wallBlock, texture);
                this.simpleBlockItem(wallBlock, this.models().wallInventory(this.name(wallBlock), texture));
            } else if (block instanceof VerticalStairBlockWithBase verticalStairBlock) {
                this.registerVerticalStairStates(verticalStairBlock);
            } else if (block instanceof VerticalSlabBlockWithBase verticalSlabBlock) {
                this.registerVerticalSlabStates(verticalSlabBlock);
            } else if (block instanceof FlowerPotBlock flowerPotBlock) {
                this.registerPottedPlantStates(flowerPotBlock, flowerPotBlock.getContent());
            } else if (block instanceof TAClusterBlock clusterBlock) {
                this.registerClusterStates(clusterBlock);
            }
        }
    }

    private void registerBarStates(Block block) {
        String name = this.name(block);
        ResourceLocation texture = this.blockTexture(block);
        ModelFile post = this.models().getBuilder(name + "_post").renderType(CUTOUT_MIPPED)
                .texture("particle", texture).texture("bars", texture).ao(false)
                .element().from(8.0F, 0.0F, 7.0F).to(8.0F, 16.0F, 9.0F)
                .face(Direction.WEST).uvs(7.0F, 0.0F, 9.0F, 16.0F).texture("#bars").end()
                .face(Direction.EAST).uvs(9.0F, 0.0F, 7.0F, 16.0F).texture("#bars").end().end()
                .element().from(7.0F, 0.0F, 8.0F).to(9.0F, 16.0F, 8.0F)
                .face(Direction.NORTH).uvs(7.0F, 0.0F, 9.0F, 16.0F).texture("#bars").end()
                .face(Direction.SOUTH).uvs(9.0F, 0.0F, 7.0F, 16.0F).texture("#bars").end().end();
        ModelFile postEnds = this.models().getBuilder(name + "_post_ends").renderType(CUTOUT_MIPPED)
                .texture("particle", texture).texture("edge", texture).ao(false)
                .element().from(7.0F, 0.001F, 7.0F).to(9.0F, 0.001F, 9.0F)
                .face(Direction.DOWN).uvs(7.0F, 7.0F, 9.0F, 9.0F).texture("#edge").end()
                .face(Direction.UP).uvs(7.0F, 7.0F, 9.0F, 9.0F).texture("#edge").end().end()
                .element().from(7.0F, 15.999F, 7.0F).to(9.0F, 15.999F, 9.0F)
                .face(Direction.DOWN).uvs(7.0F, 7.0F, 9.0F, 9.0F).texture("#edge").end()
                .face(Direction.UP).uvs(7.0F, 7.0F, 9.0F, 9.0F).texture("#edge").end().end();
        ModelFile cap = this.models().getBuilder(name + "_cap")
                .renderType(CUTOUT_MIPPED).texture("particle", texture)
                .texture("edge", texture).texture("bars", texture).ao(false)
                .element().from(8.0F, 0.0F, 8.0F).to(8.0F, 16.0F, 9.0F)
                .face(Direction.WEST).uvs(8.0F, 0.0F, 7.0F, 16.0F).texture("#bars").end()
                .face(Direction.EAST).uvs(7.0F, 0.0F, 8.0F, 16.0F).texture("#bars").end().end()
                .element().from(7.0F, 0.0F, 9.0F).to(9.0F, 16.0F, 9.0F)
                .face(Direction.NORTH).uvs(9.0F, 0.0F, 7.0F, 16.0F).texture("#bars").end()
                .face(Direction.SOUTH).uvs(7.0F, 0.0F, 9.0F, 16.0F).texture("#bars").end().end();
        ModelFile capAlt = this.models().getBuilder(name + "_cap_alt")
                .renderType(CUTOUT_MIPPED).texture("particle", texture)
                .texture("edge", texture).texture("bars", texture).ao(false)
                .element().from(8.0F, 0.0F, 7.0F).to(8.0F, 16.0F, 7.0F)
                .face(Direction.WEST).uvs(8.0F, 0.0F, 9.0F, 16.0F).texture("#bars").end()
                .face(Direction.EAST).uvs(9.0F, 0.0F, 8.0F, 16.0F).texture("#bars").end().end()
                .element().from(7.0F, 0.0F, 7.0F).to(9.0F, 16.0F, 7.0F)
                .face(Direction.NORTH).uvs(7.0F, 0.0F, 9.0F, 16.0F).texture("#bars").end()
                .face(Direction.SOUTH).uvs(9.0F, 0.0F, 7.0F, 16.0F).texture("#bars").end().end();
        ModelFile side = this.models().getBuilder(name + "_side").renderType(CUTOUT_MIPPED)
                .texture("particle", texture).texture("edge", texture).ao(false)
                .element().from(8.0F, 0.0F, 0.0F).to(8.0F, 16.0F, 8.0F)
                .face(Direction.WEST).uvs(16.0F, 0.0F,  8.0F, 16.0F).texture("#edge").end()
                .face(Direction.EAST).uvs(8.0F, 0.0F, 16.0F, 16.0F).texture("#edge").end().end()
                .element().from(7.0F, 0.0F, 0.0F).to(9.0F, 16.0F, 7.0F)
                .face(Direction.NORTH).uvs(7.0F, 0.0F, 9.0F, 16.0F).texture("#edge").cullface(Direction.NORTH).end().end()
                .element().from(7.0F, 0.001F, 0.0F).to(9.0F, 0.001F, 7.0F)
                .face(Direction.DOWN).uvs(9.0F, 0.0F, 7.0F, 7.0F).texture("#edge").end()
                .face(Direction.UP).uvs(7.0F, 0.0F, 9.0F, 7.0F).texture("#edge").end().end()
                .element().from(7.0F, 15.999F, 0.0F).to(9.0F, 15.999F, 7.0F)
                .face(Direction.DOWN).uvs(9.0F, 0.0F, 7.0F, 7.0F).texture("#edge").end()
                .face(Direction.UP).uvs(7.0F, 0.0F, 9.0F, 7.0F).texture("#edge").end().end();
        ModelFile sideAlt = this.models().getBuilder(name + "_side_alt").renderType(CUTOUT_MIPPED)
                .texture("particle", texture).texture("edge", texture).ao(false)
                .element().from(8.0F, 0.0F, 8.0F).to(8.0F, 16.0F, 16.0F)
                .face(Direction.WEST).uvs(8.0F, 0.0F,  0.0F, 16.0F).texture("#edge").end()
                .face(Direction.EAST).uvs(0.0F, 0.0F, 8.0F, 16.0F).texture("#edge").end().end()
                .element().from(7.0F, 0.0F, 9.0F).to(9.0F, 16.0F, 16.0F)
                .face(Direction.SOUTH).uvs(7.0F, 0.0F, 9.0F, 16.0F).texture("#edge").cullface(Direction.SOUTH).end()
                .face(Direction.DOWN).uvs(9.0F, 9.0F, 7.0F, 16.0F).texture("#edge").end()
                .face(Direction.UP).uvs(7.0F, 9.0F, 9.0F, 16.0F).texture("#edge").end().end()
                .element().from(7.0F, 0.001F, 9.0F).to(9.0F, 0.001F, 16.0F)
                .face(Direction.DOWN).uvs(9.0F, 9.0F, 7.0F, 16.0F).texture("#edge").end()
                .face(Direction.UP).uvs(7.0F, 9.0F, 9.0F, 16.0F).texture("#edge").end().end()
                .element().from(7.0F, 15.999F, 9.0F).to(9.0F, 15.999F, 16.0F)
                .face(Direction.DOWN).uvs(9.0F, 9.0F, 7.0F, 16.0F).texture("#edge").end()
                .face(Direction.UP).uvs(7.0F, 9.0F, 9.0F, 16.0F).texture("#edge").end().end();
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block).part().modelFile(postEnds).addModel().end();
        MultiPartBlockStateBuilder.PartBuilder partBuilder = builder.part().modelFile(post).addModel();
        Map<Direction, BooleanProperty> fourWayProperty = new HashMap<>(Map.copyOf(PipeBlock.PROPERTY_BY_DIRECTION));
        fourWayProperty.entrySet().removeIf(direction -> direction.getKey().getAxis().isVertical());
        fourWayProperty.forEach((key, value) -> partBuilder.condition(value, false));
        builder.part().modelFile(cap).rotationY(90).addModel().condition(PipeBlock.EAST, true).condition(PipeBlock.NORTH, false)
                .condition(PipeBlock.SOUTH, false).condition(PipeBlock.WEST, false).end();
        builder.part().modelFile(cap).addModel().condition(PipeBlock.EAST, false).condition(PipeBlock.NORTH, true)
                .condition(PipeBlock.SOUTH, false).condition(PipeBlock.WEST, false).end();
        builder.part().modelFile(capAlt).rotationY(90).addModel().condition(PipeBlock.EAST, false).condition(PipeBlock.NORTH, false)
                .condition(PipeBlock.SOUTH, true).condition(PipeBlock.WEST, false).end();
        builder.part().modelFile(capAlt).addModel().condition(PipeBlock.EAST, false).condition(PipeBlock.NORTH, false)
                .condition(PipeBlock.SOUTH, false).condition(PipeBlock.WEST, true).end();
        builder.part().modelFile(side).addModel().condition(PipeBlock.NORTH, true).end();
        builder.part().modelFile(side).rotationY(90).addModel().condition(PipeBlock.EAST, true).end();
        builder.part().modelFile(sideAlt).addModel().condition(PipeBlock.SOUTH, true).end();
        builder.part().modelFile(sideAlt).rotationY(90).addModel().condition(PipeBlock.WEST, true).end();
    }

    private void registerWallTorchStates(Block block) {
        Map<Direction, Integer> map = Map.of(Direction.NORTH, 270, Direction.EAST, 0,
                Direction.SOUTH, 90, Direction.WEST, 180);
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (Direction direction : WallTorchBlock.FACING.getPossibleValues()) {
            builder.partialState().with(WallTorchBlock.FACING, direction).modelForState()
                    .modelFile(this.models().torchWall(this.name(block),
                            this.blockTexture(TABlocks.SILENT_WOOD_TORCH.get())))
                    .rotationY(map.get(direction)).addModel();
        }
    }

    private void registerCropStates(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (int stage : TACropBlock.AGE.getPossibleValues()) {
            String name = this.name(block) + "_stage" + stage;
            ResourceLocation texture = this.modLoc("block/" + name);
            ModelFile modelFile = this.models().crop(name, texture).renderType(CUTOUT);
            builder.partialState().with(TACropBlock.AGE, stage).modelForState().modelFile(modelFile).addModel();
        }
    }

    private void registerCrossStates(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (int stage : TACropBlock.AGE.getPossibleValues()) {
            String name = this.name(block) + "_stage" + stage;
            ResourceLocation texture = this.modLoc("block/" + name);
            ModelFile modelFile = this.models().cross(name, texture).renderType(CUTOUT);
            builder.partialState().with(TACropBlock.AGE, stage).modelForState().modelFile(modelFile).addModel();
        }
    }

    private void registerVerticalStairStates(VerticalStairBlockWithBase block) {
        DirectionProperty facing = VerticalStairBlockWithBase.FACING;
        ModelFile modelFile = this.models().withExistingParent(this.name(block),
                this.modLoc("block/vertical_stair")).texture("all", this.blockTexture(block.getBase()));
        facing.getPossibleValues().forEach(direction -> {
            int y = (int) (direction.toYRot() - 180.0F);
            this.getVariantBuilder(block).partialState().with(facing, direction).modelForState()
                    .rotationY(y).uvLock(true).modelFile(modelFile).addModel();
        });
    }

    private void registerVerticalSlabStates(VerticalSlabBlockWithBase block) {
        BlockModelBuilder normal = this.models().withExistingParent(this.name(block),
                this.modLoc("block/vertical_slab")).texture("all", this.blockTexture(block.getBase()));
        BlockModelBuilder full = this.models().withExistingParent(this.name(block) + "_full",
                this.mcLoc("block/cube_all")).texture("all", this.blockTexture(block.getBase()));
        BlockModelBuilder post = this.models().withExistingParent(this.name(block) + "_post",
                this.modLoc("block/vertical_slab_post")).texture("all", this.blockTexture(block.getBase()));
        this.getVariantBuilder(block).forAllStatesExcept(state -> {
            VerticalSlabShape slabType = state.getValue(VerticalSlabBlockWithBase.SHAPE);
            VerticalSlabBlockWithBase.Connection connection = state.getValue(VerticalSlabBlockWithBase.CONNECTION);
            ConfiguredModel model = slabType == VerticalSlabShape.FULL ? new ConfiguredModel(full)
                    : connection == VerticalSlabBlockWithBase.Connection.NONE ? new ConfiguredModel(normal, 0,
                    slabType.getModelRotation(), true) : new ConfiguredModel(post, 0,
                    (int)(connection == VerticalSlabBlockWithBase.Connection.LEFT ? slabType.getDirection() :
                            slabType.getDirection().getClockWise()).toYRot() - 180, true);
            return new ConfiguredModel[] {model};
        }, VerticalSlabBlockWithBase.WATERLOGGED);
    }

    private void registerClusterStates(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        ResourceLocation texture = this.modLoc("block/" + this.name(block));
        ModelFile modelFile = this.models().cross(this.name(block), texture).renderType(CUTOUT);
        for (int level : TAClusterBlock.LEVEL.getPossibleValues()) {
            builder.partialState().with(TAClusterBlock.FACING, Direction.EAST).with(TAClusterBlock.LEVEL, level)
                    .modelForState().rotationX(90).rotationY(90).modelFile(modelFile).addModel()
                    .partialState().with(TAClusterBlock.FACING, Direction.WEST).with(TAClusterBlock.LEVEL, level)
                    .modelForState().rotationX(90).rotationY(270).modelFile(modelFile).addModel()
                    .partialState().with(TAClusterBlock.FACING, Direction.SOUTH).with(TAClusterBlock.LEVEL, level)
                    .modelForState().rotationX(90).rotationY(180).modelFile(modelFile).addModel()
                    .partialState().with(TAClusterBlock.FACING, Direction.NORTH).with(TAClusterBlock.LEVEL, level)
                    .modelForState().rotationX(90).modelFile(modelFile).addModel()
                    .partialState().with(TAClusterBlock.FACING, Direction.DOWN).with(TAClusterBlock.LEVEL, level)
                    .modelForState().rotationX(180).modelFile(modelFile).addModel()
                    .partialState().with(TAClusterBlock.FACING, Direction.UP).with(TAClusterBlock.LEVEL, level)
                    .modelForState().modelFile(modelFile).addModel();
        }
    }

    private void registerPottedPlantStates(Block block, Block content) {
        this.simpleBlock(block, this.models().withExistingParent(this.name(block), "block/flower_pot_cross")
                .renderType(CUTOUT).texture("plant", this.blockTexture(content)));
    }

    private void registerPlantStates(Block block) {
        this.simpleBlock(block, this.models().cross(this.name(block), this.blockTexture(block)).renderType(CUTOUT));
    }

    private void registerLightPlantStates(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (int level : BlockStateProperties.LEVEL.getPossibleValues()) {
            ResourceLocation parent = this.mcLoc("block/cross");
            ResourceLocation texture = this.modLoc("block/" + this.name(block));
            ModelFile modelFile = this.models().withExistingParent(this.name(block), parent).texture("cross", texture).renderType(CUTOUT);
            builder.partialState().with(BlockStateProperties.LEVEL, level).modelForState().modelFile(modelFile).addModel();
        }
    }

    private void registerDoublePlantStates(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (DoubleBlockHalf half : DoublePlantBlock.HALF.getPossibleValues()) {
            String name = this.name(block) + "_" + half.toString();
            ResourceLocation parent = this.mcLoc("block/tinted_cross");
            ResourceLocation texture = this.modLoc("block/" + name);
            ModelFile modelFile = this.models().withExistingParent(name, parent)
                    .texture("cross", texture).renderType(CUTOUT);
            builder.partialState().with(DoublePlantBlock.HALF, half)
                    .modelForState().modelFile(modelFile).addModel();
        }
    }

    private void registerDoubleLightPlantStates(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (DoubleBlockHalf half : DoublePlantBlock.HALF.getPossibleValues()) {
            for (int level : BlockStateProperties.LEVEL.getPossibleValues()) {
                String name = this.name(block) + "_" + half.toString();
                ResourceLocation texture = this.modLoc("block/" + name);
                ModelFile modelFile = this.models().withExistingParent(name,
                                this.mcLoc("block/tinted_cross"))
                        .texture("cross", texture).renderType(CUTOUT);
                builder.partialState().with(DoublePlantBlock.HALF, half)
                        .with(BlockStateProperties.LEVEL, level)
                        .modelForState().modelFile(modelFile).addModel();
            }
        }
    }

    private void registerWaterSurfacePlantStates(Block block) {
        IntegerProperty property = AurorianWaterSurfacePlant.LEVEL;
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (int level : property.getPossibleValues()) {
            ModelFile modelFile = new ModelFile.UncheckedModelFile(this.modLoc("block/" + this.name(block)));
            builder.partialState().with(property, level).modelForState().modelFile(modelFile).addModel();
        }
    }

    private void registerMushroomStates(Block block) {
        ResourceLocation parent = this.mcLoc("block/template_single_face");
        ResourceLocation outside = this.modLoc("block/" + this.name(block) + "_side");
        ResourceLocation inside = this.modLoc("block/" + this.name(block) + "_inside");
        ModelFile outsideModel = this.models().singleTexture(this.name(block), parent, outside).renderType(TRANSLUCENT);
        ModelFile insideModel = this.models().singleTexture(this.name(block) + "_inside", parent, inside).renderType(TRANSLUCENT);
        this.models().withExistingParent(this.name(block) + "_inventory", this.mcLoc("block/cube_all")).texture("all", outside);
        this.getMultipartBuilder(block).part().modelFile(outsideModel).addModel().condition(BlockStateProperties.NORTH, true).end()
                .part().modelFile(outsideModel).addModel().condition(BlockStateProperties.EAST, true).end()
                .part().modelFile(outsideModel).rotationY(90).uvLock(true).addModel().condition(BlockStateProperties.SOUTH, true).end()
                .part().modelFile(outsideModel).rotationY(180).uvLock(true).addModel().condition(BlockStateProperties.WEST, true).end()
                .part().modelFile(outsideModel).rotationY(270).uvLock(true).addModel().condition(BlockStateProperties.UP, true).end()
                .part().modelFile(outsideModel).rotationX(270).uvLock(true).addModel().condition(BlockStateProperties.DOWN, true).end()
                .part().modelFile(outsideModel).rotationX(90).uvLock(true).addModel().condition(BlockStateProperties.NORTH, false).end()
                .part().modelFile(insideModel).addModel().condition(BlockStateProperties.EAST, false).end()
                .part().modelFile(insideModel).rotationY(90).uvLock(false).addModel().condition(BlockStateProperties.SOUTH, false).end()
                .part().modelFile(insideModel).rotationY(180).uvLock(false).addModel().condition(BlockStateProperties.WEST, false).end()
                .part().modelFile(insideModel).rotationY(270).uvLock(false).addModel().condition(BlockStateProperties.UP, false).end()
                .part().modelFile(insideModel).rotationX(270).uvLock(false).addModel().condition(BlockStateProperties.DOWN, false).end()
                .part().modelFile(insideModel).rotationX(90).uvLock(false).addModel().end();
    }

    private void registerLuminousStates(Block newBlock, Block originalBlock) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(newBlock);
        ModelFile modelFile = this.models().getBuilder(this.name(newBlock))
                .parent(new ModelFile.UncheckedModelFile(this.mcLoc("block/block")))
                .texture("rune", this.blockTexture(originalBlock))
                .texture("particle", this.blockTexture(originalBlock))
                .ao(false).element().allFaces((direction, faceBuilder) -> faceBuilder.texture("#rune")
                        .emissivity((15), (15)).cullface(direction)).end();
        builder.partialState().modelForState().modelFile(modelFile).addModel();
    }

    private void registerLiquidStates() {
        this.simpleBlock(TABlocks.MOLTEN_AURORIAN_STEEL.get(), this.models()
                .getBuilder(TABlocks.MOLTEN_AURORIAN_STEEL.getId().getPath())
                .texture("particle", this.modLoc("block/molten_aurorian_steel")));
        this.simpleBlock(TABlocks.MOLTEN_CERULEAN.get(), this.models()
                .getBuilder(TABlocks.MOLTEN_CERULEAN.getId().getPath())
                .texture("particle", this.modLoc("block/molten_cerulean")));
        this.simpleBlock(TABlocks.MOLTEN_MOONSTONE.get(), this.models()
                .getBuilder(TABlocks.MOLTEN_MOONSTONE.getId().getPath())
                .texture("particle", this.modLoc("block/molten_moonstone")));
        this.simpleBlock(TABlocks.MOON_WATER.get(), this.models()
                .getBuilder(TABlocks.MOON_WATER.getId().getPath())
                .texture("particle", this.modLoc("block/moon_water")));
    }

    private void registerScrapperState() {
        Block block = TABlocks.SCRAPPER.get();
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        ModelFile modelFile = this.models().withExistingParent(this.name(block), this.mcLoc("block/cube"))
                .texture("particle", this.modLoc("block/scrapper_front"))
                .texture("down", this.modLoc("block/aurorian_furnace_on"))
                .texture("up", this.modLoc("block/scrapper_top"))
                .texture("north", this.modLoc("block/scrapper_front"))
                .texture("east", this.modLoc("block/scrapper_side"))
                .texture("south", this.modLoc("block/scrapper_side"))
                .texture("west", this.modLoc("block/scrapper_side"));
        for (Direction direction : HorizontalDirectionalBlock.FACING.getPossibleValues()) {
            builder.partialState().with(HorizontalDirectionalBlock.FACING, direction).modelForState()
                    .modelFile(modelFile).rotationY(DIRECTION_WITH_ROTATION.get(direction)).addModel();
        }
    }

    private void registerCraftingTableState() {
        Block block = TABlocks.SILENT_WOOD_CRAFTING_TABLE.get();
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        ResourceLocation front = this.modLoc("block/" + this.name(block) + "_front");
        ResourceLocation side = this.modLoc("block/" + this.name(block) + "_side");
        ResourceLocation top = this.modLoc("block/" + this.name(block) + "_top");
        ResourceLocation down = this.modLoc("block/" + this.name(TABlocks.SILENT_TREE_PLANKS.get()));
        ModelFile modelFile = this.models().cube(this.name(block), down, top, front, front, side, side).texture("particle", front);
        for (Direction direction : HorizontalDirectionalBlock.FACING.getPossibleValues()) {
            builder.partialState().with(HorizontalDirectionalBlock.FACING, direction).modelForState()
                    .modelFile(modelFile).rotationY(DIRECTION_WITH_ROTATION.get(direction)).addModel();
        }
    }

    private void registerMysticalBarrierState() {
        Block block = TABlocks.MYSTICAL_BARRIER.get();
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        ModelFile modelFile = this.models().withExistingParent(this.name(block), this.mcLoc("block/orientable"))
                .texture("particle", this.modLoc("block/mystical_barrier_out"))
                .texture("back", this.modLoc("block/mystical_barrier_out"))
                .texture("front", this.modLoc("block/mystical_barrier"))
                .texture("side", this.modLoc("block/mystical_barrier"))
                .element().from(0.0F, 0.0F, 6.0F).to(16.0F, 16.0F, 10.0F)
                .face(Direction.NORTH).uvs( 0.0F, 0.0F, 16.0F, 16.0F).texture("#front").end()
                .face(Direction.SOUTH).uvs( 0.0F, 0.0F, 16.0F, 16.0F).texture("back").end().end();
        for (Direction direction : MysticalBarrier.FACING.getPossibleValues()) {
            builder.partialState().with(MysticalBarrier.FACING, direction).modelForState()
                    .modelFile(modelFile).rotationY(direction.get2DDataValue() * 90).addModel();
        }
    }

    private void registerAurorianPortalState() {
        Block block = TABlocks.AURORIAN_PORTAL.get();
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (Direction.Axis axis : AurorianPortal.AXIS.getPossibleValues()) {
            boolean flag = axis == Direction.Axis.X;
            float x = flag ? 0.0F : 6.0F, z = flag ? 6.0F : 0.0F;
            String name = this.name(block) + (flag ? "_ns" : "_ew");
            ConfiguredModel.Builder<VariantBlockStateBuilder> builder1 = builder.partialState().with(AurorianPortal.AXIS, axis).modelForState();
            ModelBuilder<BlockModelBuilder>.ElementBuilder elementBuilder = this.models().getBuilder(name)
                    .texture("particle", this.blockTexture(block)).texture("portal", this.blockTexture(block))
                    .element().from(x, 0.0F, z).to(10.0F + z, 16.0F, 10.0F + x);
            if (axis == Direction.Axis.X) {
                builder1.modelFile(elementBuilder.face(Direction.NORTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#portal").end()
                        .face(Direction.SOUTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#portal").end().end()).addModel();
            } else if (axis == Direction.Axis.Z) {
                builder1.modelFile(elementBuilder.face(Direction.EAST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#portal").end()
                        .face(Direction.WEST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#portal").end().end()).addModel();
            }
        }
    }

    private void registerAurorianFurnaceState() {
        String name = this.name(TABlocks.AURORIAN_FURNACE.get());
        VariantBlockStateBuilder builder = this.getVariantBuilder(TABlocks.AURORIAN_FURNACE.get());
        for (Direction direction : AurorianFurnace.FACING.getPossibleValues()) {
            for (Boolean lit : AurorianFurnace.LIT.getPossibleValues()) {
                String front = name + (lit ? "_on" : "");
                ModelFile modelFile = new ConfiguredModel(this.models()
                        .orientable(front, this.modLoc("block/" + name + "_side"),
                                this.modLoc("block/" + front),
                                this.modLoc("block/" + name + "_top"))).model;
                builder.partialState().with(AurorianFurnace.FACING, direction)
                        .with(AurorianFurnace.LIT, lit).modelForState().modelFile(modelFile)
                        .rotationY(DIRECTION_WITH_ROTATION.get(direction)).addModel();
            }
        }
    }

    private void registerAurorianFarmlandState() {
        VariantBlockStateBuilder builder = this.getVariantBuilder(TABlocks.AURORIAN_FARM_TILE.get());
        for (int i = 0; i <= AurorianFarmTile.MAX_MOISTURE; i++) {
            String name = "aurorian_farm_tile" + (i == AurorianFarmTile.MAX_MOISTURE ? "_moist" : "");
            ConfiguredModel configuredModel = new ConfiguredModel(this.models()
                    .withExistingParent(name, this.mcLoc("block/template_farmland"))
                    .texture("dirt", this.modLoc("block/aurorian_dirt"))
                    .texture("top", this.modLoc("block/" + name)));
            builder.partialState().with(AurorianFarmTile.MOISTURE, i).addModels(configuredModel);
        }
    }

    private void registerSilentWoodLadderState() {
        String name = this.name(TABlocks.SILENT_WOOD_LADDER.get());
        VariantBlockStateBuilder builder = this.getVariantBuilder(TABlocks.SILENT_WOOD_LADDER.get());
        ConfiguredModel configuredModel = new ConfiguredModel(this.models().getBuilder(name).ao(false)
                .texture("particle", this.modLoc("block/" + name))
                .texture("texture", this.modLoc("block/" + name))
                .element().from(0.0F, 0.0F, 15.2F).to(16.0F, 16.0F, 15.2F).shade(false)
                .face(Direction.NORTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#texture").end()
                .face(Direction.SOUTH).uvs(16.0F, 0.0F, 0.0F, 16.0F).texture("#texture").end().end().renderType(CUTOUT));
        for (Direction direction : LadderBlock.FACING.getPossibleValues()) {
            builder.partialState().with(LadderBlock.FACING, direction)
                    .modelForState().modelFile(configuredModel.model)
                    .rotationY(DIRECTION_WITH_ROTATION.get(direction)).addModel();
        }
    }

    private void simpleBlockWithRenderType(Block block, ResourceLocation type) {
        simpleBlock(block, models().cubeAll(this.name(block), this.blockTexture(block)).renderType(type));
    }

    private String name(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

}