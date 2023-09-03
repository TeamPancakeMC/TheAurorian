package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.AurorianFarmland;
import cn.teampancake.theaurorian.common.blocks.AurorianFurnace;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.utils.ModCommonUtils;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public class ModBlockStateProvider extends BlockStateProvider {

    private static final ResourceLocation CUTOUT = new ResourceLocation("cutout");
    private static final ResourceLocation CUTOUT_MIPPED = new ResourceLocation("cutout_mipped");
    private static final ResourceLocation TRANSLUCENT = new ResourceLocation("translucent");

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AurorianMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.registerLiquidModels();
        this.registerWallTorchModel();
        this.registerAurorianFurnaceModel();
        this.registerAurorianFarmlandModel();
        this.registerSilentWoodLadderModel();
        this.simpleBlock(ModBlocks.GEODE_ORE.get());
        this.simpleBlock(ModBlocks.CERULEAN_ORE.get());
        this.simpleBlock(ModBlocks.MOONSTONE_ORE.get());
        this.simpleBlock(ModBlocks.AURORIAN_DIRT.get());
        this.simpleBlock(ModBlocks.AURORIAN_STONE.get());
        this.simpleBlock(ModBlocks.AURORIAN_COAL_ORE.get());
        this.simpleBlock(ModBlocks.AURORIAN_STONE_BRICK.get());
        this.simpleBlock(ModBlocks.AURORIAN_COBBLESTONE.get());
        this.simpleBlock(ModBlocks.AURORIAN_PORTAL_FRAME_BRICKS.get());
        this.simpleBlock(ModBlocks.AURORIAN_PERIDOTITE.get());
        this.simpleBlock(ModBlocks.MOON_SAND.get());
        this.simpleBlock(ModBlocks.RUNE_STONE.get());
        this.simpleBlock(ModBlocks.MOON_TEMPLE_BRICKS.get());
        this.simpleBlock(ModBlocks.DARK_STONE_BRICKS.get());
        this.simpleBlock(ModBlocks.DARK_STONE_FANCY.get());
        this.simpleBlock(ModBlocks.DARK_STONE_LAYERS.get());
        this.simpleBlock(ModBlocks.SMOOTH_RUNE_STONE.get());
        this.simpleBlock(ModBlocks.SMOOTH_MOON_TEMPLE_BRICKS.get());
        this.simpleBlock(ModBlocks.SMOOTH_AURORIAN_PERIDOTITE.get());
        this.simpleBlock(ModBlocks.RUNE_STONE_LAMP.get());
        this.simpleBlock(ModBlocks.DARK_STONE_LAMP.get());
        this.simpleBlock(ModBlocks.MOON_TEMPLE_LAMP.get());
        this.simpleBlock(ModBlocks.CERULEAN_BLOCK.get());
        this.simpleBlock(ModBlocks.MOONSTONE_BLOCK.get());
        this.simpleBlock(ModBlocks.AURORIAN_COAL_BLOCK.get());
        this.simpleBlock(ModBlocks.AURORIAN_STEEL_BLOCK.get());
        this.simpleBlock(ModBlocks.RUNE_STONE_GATE.get());
        this.simpleBlock(ModBlocks.DARK_STONE_GATE.get());
        this.simpleBlock(ModBlocks.MOON_TEMPLE_GATE.get());
        this.simpleBlock(ModBlocks.RUNE_STONE_LOOT_GATE.get());
        this.simpleBlock(ModBlocks.MOON_TEMPLE_CELL_GATE.get());
        this.simpleBlock(ModBlocks.RUNE_STONE_GATE_KEYHOLE.get());
        this.simpleBlock(ModBlocks.DARK_STONE_GATE_KEYHOLE.get());
        this.simpleBlock(ModBlocks.MOON_TEMPLE_GATE_KEYHOLE.get());
        this.simpleBlock(ModBlocks.RUNE_STONE_LOOT_GATE_KEYHOLE.get());
        this.simpleBlock(ModBlocks.MOON_TEMPLE_CELL_GATE_KEYHOLE.get());
        this.simpleBlock(ModBlocks.UMBRA_STONE.get());
        this.simpleBlock(ModBlocks.UMBRA_STONE_CRACKED.get());
        this.simpleBlock(ModBlocks.UMBRA_STONE_ROOF_TILES.get());
        this.simpleBlock(ModBlocks.INDIGO_MUSHROOM_CRYSTAL.get());
        this.simpleBlock(ModBlocks.SILENT_WOOD_TORCH.get(),
                this.models().torch(this.name(ModBlocks.SILENT_WOOD_TORCH.get()),
                        this.blockTexture(ModBlocks.SILENT_WOOD_TORCH.get())));
        this.logBlock((RotatedPillarBlock) ModBlocks.SILENT_TREE_LOG.get());
        this.logBlock((RotatedPillarBlock) ModBlocks.WEEPING_WILLOW_LOG.get());
        this.axisBlock((RotatedPillarBlock) ModBlocks.SILENT_TREE_WOOD.get(),
                this.modLoc("block/silent_tree_log"),
                this.modLoc("block/silent_tree_log"));
        this.axisBlock((RotatedPillarBlock) ModBlocks.WEEPING_WILLOW_WOOD.get(),
                this.modLoc("block/weeping_willow_log"),
                this.modLoc("block/weeping_willow_log"));
        this.simpleBlock(ModBlocks.AURORIAN_GRASS_BLOCK.get(),
                this.models().cubeBottomTop("aurorian_grass_block",
                        this.modLoc("block/aurorian_grass_block"),
                        this.modLoc("block/aurorian_dirt"),
                        this.modLoc("block/aurorian_grass_block_top")));
        this.simpleBlock(ModBlocks.AURORIAN_GRASS_LIGHT_BLOCK.get(),
                this.models().cubeBottomTop("aurorian_grass_light_block",
                        this.modLoc("block/aurorian_grass_light_block"),
                        this.modLoc("block/aurorian_dirt"),
                        this.modLoc("block/aurorian_grass_light_block_top")));
        this.simpleBlockWithRenderType(ModBlocks.MOON_GLASS.get(), CUTOUT);
        this.simpleBlockWithRenderType(ModBlocks.AURORIAN_GLASS.get(), CUTOUT);
        this.simpleBlockWithRenderType(ModBlocks.SILENT_TREE_LEAVES.get(), CUTOUT_MIPPED);
        this.simpleBlockWithRenderType(ModBlocks.WEEPING_WILLOW_LEAVES.get(), CUTOUT_MIPPED);
        this.registerGlassPaneModels(ModBlocks.MOON_GLASS_PANE.get());
        this.registerGlassPaneModels(ModBlocks.AURORIAN_GLASS_PANE.get());
        this.registerPlantModels(ModBlocks.AURORIAN_GRASS.get());
        this.registerPlantModels(ModBlocks.AURORIAN_GRASS_LIGHT.get());
        this.registerPlantModels(ModBlocks.PETUNIA_PLANT.get());
        this.registerPlantModels(ModBlocks.SILK_BERRY_PLANT.get());
        for (Block block : ModCommonUtils.getKnownBlocks()) {
            if (block instanceof StairBlock stairBlock) {
                this.stairsBlock(stairBlock, this.blockTexture(stairBlock.base));
            }
        }
    }

    private void registerGlassPaneModels(Block block) {
        String name = this.name(block);
        String side = name + "_side", sideAlt = name + "_side_alt";
        String noSide = name + "_noside", noSideAlt = name + "_noside_alt";
        ResourceLocation edge = this.modLoc("block/" + name);
        ResourceLocation pane =  this.modLoc("block/" + name.replaceAll("_pane", ""));
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block).part().modelFile(
                this.models().panePost(name + "_post", pane, edge).renderType(CUTOUT)).addModel().end();
        builder.part().modelFile(this.models().paneSide(side, pane, edge).renderType(CUTOUT))
                .addModel().condition(IronBarsBlock.NORTH, true).end();
        builder.part().modelFile(this.models().paneSide(side, pane, edge).renderType(CUTOUT))
                .rotationY(90).addModel().condition(IronBarsBlock.WEST, true).end();
        builder.part().modelFile(this.models().paneSideAlt(sideAlt, pane, edge).renderType(CUTOUT))
                .addModel().condition(IronBarsBlock.SOUTH, true).end();
        builder.part().modelFile(this.models().paneSideAlt(sideAlt, pane, edge).renderType(CUTOUT))
                .rotationY(90).addModel().condition(IronBarsBlock.WEST, true).end();
        builder.part().modelFile(this.models().paneNoSide(noSide, pane).renderType(CUTOUT))
                .addModel().condition(IronBarsBlock.NORTH, false).end();
        builder.part().modelFile(this.models().paneNoSide(noSide, pane).renderType(CUTOUT))
                .rotationY(270).addModel().condition(IronBarsBlock.WEST, false).end();
        builder.part().modelFile(this.models().paneNoSideAlt(noSideAlt, pane).renderType(CUTOUT))
                .addModel().condition(IronBarsBlock.EAST, false).end();
        builder.part().modelFile(this.models().paneNoSideAlt(noSideAlt, pane).renderType(CUTOUT))
                .rotationY(270).addModel().condition(IronBarsBlock.WEST, false).end();
    }

    private void registerPlantModels(Block block) {
        this.simpleBlock(block, this.models().cross(this.name(block), this.blockTexture(block)).renderType(CUTOUT));
    }

    private void registerLiquidModels() {
        this.simpleBlock(ModBlocks.MOLTEN_AURORIAN_STEEL.get(), this.models()
                .getBuilder(ModBlocks.MOLTEN_AURORIAN_STEEL.getId().getPath())
                .texture("particle", this.modLoc("block/molten_aurorian_steel")));
        this.simpleBlock(ModBlocks.MOLTEN_CERULEAN.get(), this.models()
                .getBuilder(ModBlocks.MOLTEN_CERULEAN.getId().getPath())
                .texture("particle", this.modLoc("block/molten_cerulean")));
        this.simpleBlock(ModBlocks.MOLTEN_MOONSTONE.get(), this.models()
                .getBuilder(ModBlocks.MOLTEN_MOONSTONE.getId().getPath())
                .texture("particle", this.modLoc("block/molten_moonstone")));
        this.simpleBlock(ModBlocks.MOON_WATER.get(), this.models()
                .getBuilder(ModBlocks.MOON_WATER.getId().getPath())
                .texture("particle", this.modLoc("block/moon_water")));
    }

    private void registerWallTorchModel() {
        Block block = ModBlocks.SILENT_WOOD_WALL_TORCH.get();
        Map<Direction, Integer> map = Map.of(Direction.NORTH, 270, Direction.EAST, 0,
                Direction.SOUTH, 90, Direction.WEST, 180);
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (Direction direction : WallTorchBlock.FACING.getPossibleValues()) {
            builder.partialState().with(WallTorchBlock.FACING, direction).modelForState()
                    .modelFile(this.models().torchWall(this.name(block),
                            this.blockTexture(ModBlocks.SILENT_WOOD_TORCH.get())))
                    .rotationY(map.get(direction)).addModel();
        }
    }

    private void registerAurorianFurnaceModel() {
        String name = this.name(ModBlocks.AURORIAN_FURNACE.get());
        Map<Direction, Integer> map = Map.of(Direction.NORTH, 0, Direction.EAST, 90,
                Direction.SOUTH, 180, Direction.WEST, 270);
        VariantBlockStateBuilder builder = this.getVariantBuilder(ModBlocks.AURORIAN_FURNACE.get());
        for (Direction direction : AurorianFurnace.FACING.getPossibleValues()) {
            for (Boolean lit : AurorianFurnace.LIT.getPossibleValues()) {
                String front = name + (lit ? "_on" : "");
                ConfiguredModel configuredModel = new ConfiguredModel(this.models()
                        .orientable(front, this.modLoc("block/" + name + "_side"),
                                this.modLoc("block/" + front),
                                this.modLoc("block/" + name + "_top")));
                builder.partialState().with(AurorianFurnace.FACING, direction)
                        .with(AurorianFurnace.LIT, lit).modelForState()
                        .modelFile(configuredModel.model)
                        .rotationY(map.get(direction)).addModel();
            }
        }
    }

    private void registerAurorianFarmlandModel() {
        VariantBlockStateBuilder builder = this.getVariantBuilder(ModBlocks.AURORIAN_FARMLAND.get());
        for (int i = 0; i <= AurorianFarmland.MAX_MOISTURE; i++) {
            String name = "aurorian_farmland" + (i == AurorianFarmland.MAX_MOISTURE ? "_moist" : "");
            ConfiguredModel configuredModel = new ConfiguredModel(this.models()
                    .withExistingParent(name, this.mcLoc("block/template_farmland"))
                    .texture("dirt", this.modLoc("block/aurorian_dirt"))
                    .texture("top", this.modLoc("block/" + name)));
            builder.partialState().with(AurorianFarmland.MOISTURE, i).addModels(configuredModel);
        }
    }

    private void registerSilentWoodLadderModel() {
        String name = this.name(ModBlocks.SILENT_WOOD_LADDER.get());
        Map<Direction, Integer> map = Map.of(Direction.NORTH, 0,
                Direction.EAST, 90, Direction.SOUTH, 180, Direction.WEST, 270);
        VariantBlockStateBuilder builder = this.getVariantBuilder(ModBlocks.SILENT_WOOD_LADDER.get());
        ConfiguredModel configuredModel = new ConfiguredModel(this.models()
                .withExistingParent(name, this.modLoc("block/" + name))
                .texture("particle", this.modLoc("block/" + name))
                .texture("texture", this.modLoc("block/" + name)));
        for (Direction direction : LadderBlock.FACING.getPossibleValues()) {
            builder.partialState().with(LadderBlock.FACING, direction).modelForState()
                    .modelFile(configuredModel.model).rotationY(map.get(direction)).addModel();
        }
    }

    private String name(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

    public void simpleBlockWithRenderType(Block block, ResourceLocation type) {
        simpleBlock(block, models().cubeAll(this.name(block), this.blockTexture(block)).renderType(type));
    }

}