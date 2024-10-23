package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.*;
import cn.teampancake.theaurorian.common.blocks.base.*;
import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import cn.teampancake.theaurorian.common.blocks.state.properties.VerticalSlabShape;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SpellCheckingInspection")
public class TABlockStateProvider extends BlockStateProvider {

    private static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace("cutout");
    private static final ResourceLocation CUTOUT_MIPPED = ResourceLocation.withDefaultNamespace("cutout_mipped");
    private static final ResourceLocation TRANSLUCENT = ResourceLocation.withDefaultNamespace("translucent");
    private static final Map<Direction, Integer> DIRECTION_WITH_ROTATION =
            Map.of(Direction.NORTH, 0, Direction.EAST, 90,
            Direction.SOUTH, 180, Direction.WEST, 270);

    public TABlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TheAurorian.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.registerLiquidStates();
        this.registerMysticalBarrierState();
        this.registerLargeFilthyIceSpike();
        this.registerSilentWoodLadderState();
        TACommonUtils.getKnownBlockStream().filter(block -> block.properties() instanceof TABlockProperties properties && properties.isSimpleModelBlock).forEach(this::simpleBlock);
        this.simpleBlock(TABlocks.SILENT_WOOD_CHEST.get(), this.models().getBuilder(this.name(TABlocks.SILENT_WOOD_CHEST.get()))
                .texture("particle", this.modLoc("block/" + this.name(TABlocks.SILENT_TREE_PLANKS.get()))));
        this.simpleBlock(TABlocks.SILENT_WOOD_TORCH.get(), this.models().torch(this.name(TABlocks.SILENT_WOOD_TORCH.get()),
                this.blockTexture(TABlocks.SILENT_WOOD_TORCH.get())).renderType(CUTOUT));
        this.simpleBlock(TABlocks.MOON_TORCH.get(), this.models().torch(this.name(TABlocks.MOON_TORCH.get()),
                this.blockTexture(TABlocks.MOON_TORCH.get())).renderType(CUTOUT));
        this.signBlock((StandingSignBlock) TABlocks.SILENT_WOOD_SIGN.get(),
                (WallSignBlock) TABlocks.SILENT_WOOD_WALL_SIGN.get(),
                this.blockTexture(TABlocks.SILENT_TREE_PLANKS.get()));
        this.signBlock((StandingSignBlock) TABlocks.WEEPING_WILLOW_WOOD_SIGN.get(),
                (WallSignBlock) TABlocks.WEEPING_WILLOW_WOOD_WALL_SIGN.get(),
                this.blockTexture(TABlocks.WEEPING_WILLOW_PLANKS.get()));
        this.signBlock((StandingSignBlock) TABlocks.CURTAIN_WOOD_SIGN.get(),
                (WallSignBlock) TABlocks.CURTAIN_WOOD_WALL_SIGN.get(),
                this.blockTexture(TABlocks.CURTAIN_TREE_PLANKS.get()));
        this.signBlock((StandingSignBlock) TABlocks.CURSED_FROST_WOOD_SIGN.get(),
                (WallSignBlock) TABlocks.CURSED_FROST_WOOD_WALL_SIGN.get(),
                this.blockTexture(TABlocks.CURSED_FROST_TREE_PLANKS.get()));
        this.logBlock((RotatedPillarBlock) TABlocks.RUNE_STONE_PILLAR.get());
        this.logBlock((RotatedPillarBlock) TABlocks.DARK_STONE_PILLAR.get());
        this.logBlock((RotatedPillarBlock) TABlocks.MOON_TEMPLE_PILLAR.get());
        this.logBlock((RotatedPillarBlock) TABlocks.STRIPPED_SILENT_TREE_LOG.get());
        this.logBlock((RotatedPillarBlock) TABlocks.STRIPPED_WEEPING_WILLOW_LOG.get());
        this.logBlock((RotatedPillarBlock) TABlocks.STRIPPED_CURTAIN_TREE_LOG.get());
        this.logBlock((RotatedPillarBlock) TABlocks.STRIPPED_CURSED_FROST_TREE_LOG.get());
        this.logBlock((RotatedPillarBlock) TABlocks.SILENT_TREE_LOG.get());
        this.logBlock((RotatedPillarBlock) TABlocks.WEEPING_WILLOW_LOG.get());
        this.logBlock((RotatedPillarBlock) TABlocks.CURTAIN_TREE_LOG.get());
        this.logBlock((RotatedPillarBlock) TABlocks.CURSED_FROST_TREE_LOG.get());
        this.simpleBlock(TABlocks.SILENT_TREE_SAPLING.get(),
                this.models().cross(this.name(TABlocks.SILENT_TREE_SAPLING.get()),
                        this.blockTexture(TABlocks.SILENT_TREE_SAPLING.get())).renderType(CUTOUT));
        this.simpleBlock(TABlocks.CURTAIN_TREE_SAPLING.get(),
                this.models().cross(this.name(TABlocks.CURTAIN_TREE_SAPLING.get()),
                        this.blockTexture(TABlocks.CURTAIN_TREE_SAPLING.get())).renderType(CUTOUT));
        this.simpleBlock(TABlocks.CURSED_FROST_TREE_SAPLING.get(),
                this.models().cross(this.name(TABlocks.CURSED_FROST_TREE_SAPLING.get()),
                        this.blockTexture(TABlocks.CURSED_FROST_TREE_SAPLING.get())).renderType(CUTOUT));
        this.axisBlock((RotatedPillarBlock) TABlocks.STRIPPED_SILENT_TREE_WOOD.get(),
                this.modLoc("block/stripped_silent_tree_log"),
                this.modLoc("block/stripped_silent_tree_log"));
        this.axisBlock((RotatedPillarBlock) TABlocks.STRIPPED_WEEPING_WILLOW_WOOD.get(),
                this.modLoc("block/stripped_weeping_willow_log"),
                this.modLoc("block/stripped_weeping_willow_log"));
        this.axisBlock((RotatedPillarBlock) TABlocks.STRIPPED_CURTAIN_TREE_WOOD.get(),
                this.modLoc("block/stripped_curtain_tree_log"),
                this.modLoc("block/stripped_curtain_tree_log"));
        this.axisBlock((RotatedPillarBlock) TABlocks.STRIPPED_CURSED_FROST_TREE_WOOD.get(),
                this.modLoc("block/stripped_cursed_frost_tree_log"),
                this.modLoc("block/stripped_cursed_frost_tree_log"));
        this.axisBlock((RotatedPillarBlock) TABlocks.SILENT_TREE_WOOD.get(),
                this.modLoc("block/silent_tree_log"),
                this.modLoc("block/silent_tree_log"));
        this.axisBlock((RotatedPillarBlock) TABlocks.WEEPING_WILLOW_WOOD.get(),
                this.modLoc("block/weeping_willow_log"),
                this.modLoc("block/weeping_willow_log"));
        this.axisBlock((RotatedPillarBlock) TABlocks.CURTAIN_TREE_WOOD.get(),
                this.modLoc("block/curtain_tree_log"),
                this.modLoc("block/curtain_tree_log"));
        this.axisBlock((RotatedPillarBlock) TABlocks.CURSED_FROST_TREE_WOOD.get(),
                this.modLoc("block/cursed_frost_tree_log"),
                this.modLoc("block/cursed_frost_tree_log"));
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
        this.simpleBlock(TABlocks.SNOW_AURORIAN_GRASS_BLOCK.get(),
                this.models().cubeBottomTop("snow_aurorian_grass_block",
                        this.modLoc("block/snow_aurorian_grass_block"),
                        this.modLoc("block/aurorian_dirt"),
                        this.modLoc("block/snow_aurorian_grass_block_top")));
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
        this.registerFilthyIceSpike(TABlocks.SMALL_FILTHY_ICE_SPIKE.get());
        this.registerFilthyIceSpike(TABlocks.MEDIUM_FILTHY_ICE_SPIKE.get());
        this.simpleBlockWithRenderType(TABlocks.FILTHY_ICE.get(), TRANSLUCENT);
        this.simpleBlockWithRenderType(TABlocks.MOON_GLASS.get(), TRANSLUCENT);
        this.simpleBlockWithRenderType(TABlocks.AURORIAN_GLASS.get(), TRANSLUCENT);
        this.simpleBlockWithRenderType(TABlocks.DARK_STONE_GLASS.get(), TRANSLUCENT);
        this.simpleBlockWithRenderType(TABlocks.SILENT_TREE_LEAVES.get(), CUTOUT_MIPPED);
        this.simpleBlockWithRenderType(TABlocks.WEEPING_WILLOW_LEAVES.get(), CUTOUT_MIPPED);
        this.simpleBlockWithRenderType(TABlocks.CURTAIN_TREE_LEAVES.get(), CUTOUT_MIPPED);
        this.simpleBlockWithRenderType(TABlocks.CURSED_FROST_TREE_LEAVES.get(), CUTOUT_MIPPED);
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
        this.registerBarStates(TABlocks.DARK_STONE_BARS.get());
        this.registerBarStates(TABlocks.MOON_TEMPLE_BARS.get());
        this.registerWallTorchStates(TABlocks.MOON_WALL_TORCH.get());
        this.registerWallTorchStates(TABlocks.SILENT_WOOD_WALL_TORCH.get());
        this.registerCropStates(TABlocks.LAVENDER_CROP.get());
        this.registerCropStates(TABlocks.SILK_BERRY_CROP.get());
        this.registerCrossStates(TABlocks.BLUEBERRY_BUSH.get());
        this.registerPlantStates(TABlocks.NEBULA_BLOSSOM_CLUSTER.get());
        this.registerPlantStates(TABlocks.MOON_FROST_FLOWER.get());
        this.registerPlantStates(TABlocks.VOID_CANDLE_FLOWER.get());
        this.registerPlantStates(TABlocks.EQUINOX_FLOWER.get());
        this.registerPlantStates(TABlocks.LAVENDER_PLANT.get());
        this.registerPlantStates(TABlocks.PETUNIA_PLANT.get());
        this.registerPlantStates(TABlocks.INDIGO_MUSHROOM.get());
        this.registerPlantStates(TABlocks.CRISPED_MALLOW.get());
        this.registerPlantStates(TABlocks.FROST_SNOW_GRASS.get());
        this.registerPlantStates(TABlocks.ICE_CALENDULA.get());
        this.registerPlantStates(TABlocks.AURORIAN_GRASS.get());
        this.registerPlantStates(TABlocks.AURORIAN_GRASS_LIGHT.get());
        this.registerPlantStates(TABlocks.WINTER_ROOT.get());
        this.registerLuminousStates(TABlocks.LUMINOUS_AURORIAN_CASTLE_RUNE_STONE.get(), TABlocks.AURORIAN_CASTLE_RUNE_STONE.get());
        this.registerLuminousStates(TABlocks.LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE.get(), TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE.get());
        this.registerLuminousStates(TABlocks.LUMINOUS_CERULEAN_CASTLE_RUNE_STONE.get(), TABlocks.CERULEAN_CASTLE_RUNE_STONE.get());
        this.registerLuminousStates(TABlocks.LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE.get(), TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE.get());
        this.registerLuminousStates(TABlocks.LUMINOUS_MOON_CASTLE_RUNE_STONE.get(), TABlocks.MOON_CASTLE_RUNE_STONE.get());
        this.registerHangingSignStates(TABlocks.SILENT_WOOD_HANGING_SIGN.get(),
                TABlocks.SILENT_WOOD_WALL_HANGING_SIGN.get(),
                this.blockTexture(TABlocks.STRIPPED_SILENT_TREE_LOG.get()));
        this.registerHangingSignStates(TABlocks.WEEPING_WILLOW_WOOD_HANGING_SIGN.get(),
                TABlocks.WEEPING_WILLOW_WOOD_WALL_HANGING_SIGN.get(),
                this.blockTexture(TABlocks.STRIPPED_WEEPING_WILLOW_LOG.get()));
        this.registerHangingSignStates(TABlocks.CURTAIN_WOOD_HANGING_SIGN.get(),
                TABlocks.CURTAIN_WOOD_WALL_HANGING_SIGN.get(),
                this.blockTexture(TABlocks.STRIPPED_CURTAIN_TREE_LOG.get()));
        this.registerHangingSignStates(TABlocks.CURSED_FROST_WOOD_HANGING_SIGN.get(),
                TABlocks.CURSED_FROST_WOOD_WALL_HANGING_SIGN.get(),
                this.blockTexture(TABlocks.STRIPPED_CURSED_FROST_TREE_LOG.get()));
        for (Block block : TACommonUtils.getKnownBlocks()) {
            if (block instanceof PressurePlateBlockWithBase pressurePlateBlock) {
                this.pressurePlateBlock(pressurePlateBlock, this.blockTexture(pressurePlateBlock.getBase()));
            } else if (block instanceof FenceGateBlockWithBase fenceGateBlock) {
                this.fenceGateBlockWithRenderType(fenceGateBlock, this.blockTexture(fenceGateBlock.getBase()), CUTOUT);
            } else if (block instanceof TrapDoorBlockWithBase trapDoorBlock) {
                ResourceLocation texture = this.blockTexture(trapDoorBlock);
                this.trapdoorBlockWithRenderType(trapDoorBlock, texture, Boolean.TRUE, CUTOUT);
                this.simpleBlockItem(trapDoorBlock, this.models().trapdoorBottom(this.name(trapDoorBlock), texture));
            } else if (block instanceof ButtonBlockWithBase buttonBlock) {
                String name = this.name(buttonBlock) + "_inventory";
                ResourceLocation texture = this.blockTexture(buttonBlock.getBase());
                ModelFile buttonInventory = this.models().buttonInventory(name, texture);
                this.buttonBlock(buttonBlock, texture);
                this.simpleBlockItem(buttonBlock, buttonInventory);
            } else if (block instanceof StairBlock stairBlock) {
                this.stairsBlock(stairBlock, this.blockTexture(stairBlock.base));
            } else if (block instanceof FenceBlockWithBase fenceBlock) {
                ResourceLocation texture = this.blockTexture(fenceBlock.getBase());
                this.fenceBlockWithRenderType(fenceBlock, texture, CUTOUT);
                this.simpleBlockItem(fenceBlock, this.models().fenceInventory(this.name(fenceBlock), texture));
            } else if (block instanceof DoorBlockWithBase doorBlock) {
                String name = "block/" + this.name(doorBlock) + "_";
                this.doorBlockWithRenderType(doorBlock,
                        this.modLoc(name + "bottom"),
                        this.modLoc(name + "top"), CUTOUT);
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
                this.registerPottedPlantStates(flowerPotBlock, flowerPotBlock.getPotted());
            } else if (block instanceof TAClusterBlock clusterBlock) {
                this.registerClusterStates(clusterBlock);
            } else if (block instanceof DungeonStoneGateKeyhole keyhole) {
                this.registerKeyholeStates(keyhole);
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
        if (block.asItem() instanceof StandingAndWallBlockItem wallBlockItem) {
            VariantBlockStateBuilder stateBuilder = this.getVariantBuilder(block);
            BlockModelBuilder modelBuilder = this.models().torchWall(this.name(block), this.blockTexture(wallBlockItem.getBlock())).renderType(CUTOUT);
            Map<Direction, Integer> map = Map.of(Direction.NORTH, 270, Direction.EAST, 0, Direction.SOUTH, 90, Direction.WEST, 180);
            for (Direction direction : WallTorchBlock.FACING.getPossibleValues()) {
                stateBuilder.partialState().with(WallTorchBlock.FACING, direction).modelForState().modelFile(modelBuilder).rotationY(map.get(direction)).addModel();
            }
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

    private void registerFilthyIceSpike(Block block) {
        DirectionProperty property = BlockStateProperties.VERTICAL_DIRECTION;
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        ResourceLocation texture = this.modLoc("block/" + this.name(block));
        ModelFile modelFile = this.models().cross(this.name(block), texture).renderType(CUTOUT);
        for (Direction direction : property.getPossibleValues()) {
            builder.partialState().with(property, direction).modelForState().modelFile(modelFile).addModel();
        }
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

    private void registerKeyholeStates(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        BooleanProperty property = DungeonStoneGate.UNLOCKED;
        property.getPossibleValues().forEach(unlocked -> {
            String name = "block/" + this.name(block) + (unlocked ? "_unlocked" : "");
            ModelFile modelFile = this.models().cubeAll(name, this.modLoc(name));
            builder.partialState().with(property, unlocked).modelForState().modelFile(modelFile).addModel();
        });
    }

    private void registerHangingSignStates(Block standing, Block wall, ResourceLocation texture) {
        ModelFile sign = this.models().sign(this.name(standing), texture);
        this.simpleBlock(standing, sign);
        this.simpleBlock(wall, sign);
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

    private void registerLargeFilthyIceSpike() {
        Block block = TABlocks.LARGE_FILTHY_ICE_SPIKE.get();
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        DirectionProperty property1 = BlockStateProperties.VERTICAL_DIRECTION;
        EnumProperty<DoubleBlockHalf> property2 = BlockStateProperties.DOUBLE_BLOCK_HALF;
        ResourceLocation lowerTexture = this.modLoc("block/large_filthy_ice_spike");
        ResourceLocation upperTexture = this.modLoc("block/medium_filthy_ice_spike");
        ResourceLocation parent = this.mcLoc("block/tinted_cross");
        Map<DoubleBlockHalf, ResourceLocation> map = Map.of(
                DoubleBlockHalf.LOWER, lowerTexture,
                DoubleBlockHalf.UPPER, upperTexture);
        for (Direction direction : property1.getPossibleValues()) {
            for (DoubleBlockHalf half : property2.getPossibleValues()) {
                String name = this.name(block) + "_" + half.toString();
                ModelFile modelFile = this.models().withExistingParent(name, parent)
                        .texture("cross", map.get(half)).renderType(CUTOUT);
                builder.partialState().with(property1, direction).with(property2, half)
                        .modelForState().modelFile(modelFile).addModel();
            }
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
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

}