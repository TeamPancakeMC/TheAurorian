package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.AbstractCropBlock;
import cn.teampancake.theaurorian.common.blocks.AurorianFarmTile;
import cn.teampancake.theaurorian.common.blocks.AurorianFurnace;
import cn.teampancake.theaurorian.common.blocks.AurorianPortal;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.utils.ModCommonUtils;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("SpellCheckingInspection")
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
        this.registerCraftingTableModel();
        this.registerAurorianPortalModel();
        this.registerAurorianFurnaceModel();
        this.registerAurorianFarmlandModel();
        this.registerSilentWoodLadderModel();
        this.simpleBlock(ModBlocks.GEODE_ORE.get());
        this.simpleBlock(ModBlocks.CERULEAN_ORE.get());
        this.simpleBlock(ModBlocks.MOONSTONE_ORE.get());
        this.simpleBlock(ModBlocks.AURORIAN_DIRT.get());
        this.simpleBlock(ModBlocks.AURORIAN_STONE.get());
        this.simpleBlock(ModBlocks.AURORIAN_COAL_ORE.get());
        this.simpleBlock(ModBlocks.AURORIAN_STONE_BRICKS.get());
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
        this.simpleBlock(ModBlocks.SILENT_TREE_PLANKS.get());
        this.simpleBlock(ModBlocks.WEEPING_WILLOW_PLANKS.get());
        this.simpleBlock(ModBlocks.SILENT_WOOD_TORCH.get(),
                this.models().torch(this.name(ModBlocks.SILENT_WOOD_TORCH.get()),
                        this.blockTexture(ModBlocks.SILENT_WOOD_TORCH.get())));
        this.simpleBlock(ModBlocks.MOON_TORCH.get(),
                this.models().torch(this.name(ModBlocks.MOON_TORCH.get()),
                        this.blockTexture(ModBlocks.MOON_TORCH.get())));
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
        this.registerBarModels(ModBlocks.RUNE_STONE_BARS.get());
        this.registerBarModels(ModBlocks.MOON_TEMPLE_BARS.get());
        this.registerGlassPaneModels(ModBlocks.MOON_GLASS_PANE.get());
        this.registerGlassPaneModels(ModBlocks.AURORIAN_GLASS_PANE.get());
        this.registerWallTorchModel(ModBlocks.MOON_WALL_TORCH.get());
        this.registerWallTorchModel(ModBlocks.SILENT_WOOD_WALL_TORCH.get());
        this.registerCropModels(ModBlocks.LAVENDER_CROP.get());
        this.registerCropModels(ModBlocks.SILK_BERRY_CROP.get());
        this.registerPlantModels(ModBlocks.PETUNIA_PLANT.get());
        this.registerPlantModels(ModBlocks.INDIGO_MUSHROOM.get());
        this.registerPlantModels(ModBlocks.AURORIAN_GRASS.get());
        this.registerPlantModels(ModBlocks.AURORIAN_GRASS_LIGHT.get());
        this.registerMushroomModels(ModBlocks.INDIGO_MUSHROOM_BLOCK.get());
        this.registerMushroomModels(ModBlocks.INDIGO_MUSHROOM_STEM.get());
        for (Block block : ModCommonUtils.getKnownBlocks()) {
            if (block instanceof StairBlock stairBlock) {
                this.stairsBlock(stairBlock, this.blockTexture(stairBlock.base));
            }
        }
    }

    private void registerBarModels(Block block) {
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

    private void registerWallTorchModel(Block block) {
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

    private void registerCropModels(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (int stage : AbstractCropBlock.AGE.getPossibleValues()) {
            String name = this.name(block) + "_stage" + stage;
            ResourceLocation texture = this.modLoc("block/" + name);
            ModelFile modelFile = this.models().crop(name, texture);
            builder.partialState().with(AbstractCropBlock.AGE, stage)
                    .modelForState().modelFile(modelFile).addModel();
        }
    }

    private void registerPlantModels(Block block) {
        this.simpleBlock(block, this.models().cross(this.name(block), this.blockTexture(block)).renderType(CUTOUT));
    }

    private void registerMushroomModels(Block block) {
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
        ResourceLocation parent = this.mcLoc("block/template_single_face");
        ResourceLocation outside = this.modLoc("block/" + this.name(block) + "_side");
        ResourceLocation inside = this.modLoc("block/" + this.name(block) + "_inside");
        ModelFile outsideModel = this.models().singleTexture(this.name(block), parent, outside).renderType(TRANSLUCENT);
        ModelFile insideModel = this.models().singleTexture(this.name(block), parent, inside).renderType(TRANSLUCENT);
        Map<Integer, Integer> map = Map.of((0), (90), (1), (270), (2), (0), (3), (180), (4), (270), (5), (90));
        Map<Direction, BooleanProperty> properties = PipeBlock.PROPERTY_BY_DIRECTION;
        for (Direction direction : Direction.values()) {
            boolean flag = direction == Direction.NORTH;
            int yRot = map.get(direction.get3DDataValue());
            BooleanProperty property = properties.get(direction);
            builder.part().modelFile(outsideModel).uvLock(!flag).rotationY(yRot)
                    .addModel().condition(property, Boolean.TRUE).end();
            builder.part().modelFile(insideModel).uvLock(Boolean.FALSE).rotationY(yRot)
                    .addModel().condition(property, Boolean.FALSE).end();
        }
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

    private void registerCraftingTableModel() {
        String name = this.name(ModBlocks.SILENT_WOOD_CRAFTING_TABLE.get());
        ResourceLocation down = this.modLoc("block/" +
                this.name(ModBlocks.SILENT_TREE_PLANKS.get()));
        ResourceLocation front = this.modLoc("block/" + name + "_front");
        ResourceLocation side = this.modLoc("block/" + name + "_side");
        ResourceLocation top = this.modLoc("block/" + name + "_top");
        ModelFile modelFile = this.models().cube(name, down, top, front, side, side, front);
        this.simpleBlock(ModBlocks.SILENT_WOOD_CRAFTING_TABLE.get(), modelFile);
    }

    private void registerAurorianPortalModel() {
        Block block = ModBlocks.AURORIAN_PORTAL.get();
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
        VariantBlockStateBuilder builder = this.getVariantBuilder(ModBlocks.AURORIAN_FARM_TILE.get());
        for (int i = 0; i <= AurorianFarmTile.MAX_MOISTURE; i++) {
            String name = "aurorian_farm_tile" + (i == AurorianFarmTile.MAX_MOISTURE ? "_moist" : "");
            ConfiguredModel configuredModel = new ConfiguredModel(this.models()
                    .withExistingParent(name, this.mcLoc("block/template_farmland"))
                    .texture("dirt", this.modLoc("block/aurorian_dirt"))
                    .texture("top", this.modLoc("block/" + name)));
            builder.partialState().with(AurorianFarmTile.MOISTURE, i).addModels(configuredModel);
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

    private void simpleBlockWithRenderType(Block block, ResourceLocation type) {
        simpleBlock(block, models().cubeAll(this.name(block), this.blockTexture(block)).renderType(type));
    }

}