package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.*;
import cn.teampancake.theaurorian.common.blocks.state.properties.VerticalSlabShape;
import cn.teampancake.theaurorian.common.registry.TABlocks;
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
import java.util.Set;

public class TABlockStateProvider extends BlockStateProvider {

    public static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace("cutout");
    public static final ResourceLocation CUTOUT_MIPPED = ResourceLocation.withDefaultNamespace("cutout_mipped");
    public static final ResourceLocation TRANSLUCENT = ResourceLocation.withDefaultNamespace("translucent");
    public static final Map<Direction, Integer> DIRECTION_WITH_ROTATION =
            Map.of(Direction.NORTH, 0, Direction.EAST, 90,
            Direction.SOUTH, 180, Direction.WEST, 270);

    public TABlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TheAurorian.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.registerLiquidStates();
        this.registerMysticalBarrierState();
        this.signBlock(TABlocks.SILENT_WOOD_SIGN.get(), TABlocks.SILENT_WOOD_WALL_SIGN.get(), this.blockTexture(TABlocks.SILENT_TREE_PLANKS.get()));
        this.signBlock(TABlocks.WEEPING_WILLOW_WOOD_SIGN.get(), TABlocks.WEEPING_WILLOW_WOOD_WALL_SIGN.get(), this.blockTexture(TABlocks.WEEPING_WILLOW_PLANKS.get()));
        this.signBlock(TABlocks.CURTAIN_WOOD_SIGN.get(), TABlocks.CURTAIN_WOOD_WALL_SIGN.get(), this.blockTexture(TABlocks.CURTAIN_TREE_PLANKS.get()));
        this.signBlock(TABlocks.CURSED_FROST_WOOD_SIGN.get(), TABlocks.CURSED_FROST_WOOD_WALL_SIGN.get(), this.blockTexture(TABlocks.CURSED_FROST_TREE_PLANKS.get()));
        this.registerWallTorchStates(TABlocks.MOON_WALL_TORCH.get());
        this.registerWallTorchStates(TABlocks.SILENT_WOOD_WALL_TORCH.get());
    }

    public static void registerGrassBlockState(Block block, BlockStateProvider provider) {
        provider.simpleBlock(block, provider.models().cubeBottomTop(name(block),
                provider.modLoc("block/" + name(block)),
                provider.modLoc("block/aurorian_dirt"),
                provider.modLoc(String.format("block/%s_top", name(block)))));
    }

    public static void registerTorchStates(Block block, BlockStateProvider provider) {
        provider.simpleBlock(block, provider.models().torch(name(block), provider.blockTexture(block)).renderType(CUTOUT));
    }

    public static void registerSaplingStates(Block block, BlockStateProvider provider) {
        provider.simpleBlock(block, provider.models().cross(name(block), provider.blockTexture(block)).renderType(CUTOUT));
    }

    public static void registerPillarStates(RotatedPillarBlock block, BlockStateProvider provider) {
        if (name(block).endsWith("_wood")) {
            String path = "block/" + name(block).replaceFirst("wood", "log");
            provider.axisBlock(block, provider.modLoc(path), provider.modLoc(path));
        } else {
            provider.logBlock(block);
        }
    }

    public static void registerStairStates(StairBlock stairBlock, ResourceLocation texture, BlockStateProvider provider, boolean emissivity) {
        if (emissivity) {
            ModelFile stairs = provider.models().stairs(name(stairBlock), texture, texture, texture)
                    .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 8.0F, 16.0F)
                    .face(Direction.DOWN).uvs(0.0F, 0.0F, 16.0F, 16.0F)
                    .texture("#bottom").cullface(Direction.DOWN).emissivity((15), (15)).end()
                    .face(Direction.UP).uvs(0.0F, 0.0F, 16.0F, 16.0F)
                    .texture("#top").emissivity((15), (15)).end()
                    .allFacesExcept((direction, faceBuilder) -> faceBuilder.texture("#side")
                            .uvs(0.0F, 8.0F, 16.0F, 16.0F).cullface(direction)
                            .emissivity((15), (15)), Set.of(Direction.DOWN, Direction.UP)).end()
                    .element().from(8.0F, 8.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.UP).uvs(8.0F, 0.0F, 16.0F, 16.0F).texture("#top").cullface(Direction.UP).emissivity((15), (15)).end()
                    .face(Direction.NORTH).uvs(0.0F, 0.0F, 8.0F, 8.0F).texture("#side").cullface(Direction.NORTH).emissivity((15), (15)).end()
                    .face(Direction.SOUTH).uvs(8.0F, 0.0F, 16.0F, 8.0F).texture("#side").cullface(Direction.SOUTH).emissivity((15), (15)).end()
                    .face(Direction.WEST).uvs(0.0F, 0.0F, 16.0F, 8.0F).texture("#side").emissivity((15), (15)).end()
                    .face(Direction.EAST).uvs(0.0F, 0.0F, 16.0F, 8.0F).texture("#side").cullface(Direction.EAST).emissivity((15), (15)).end().end();
            ModelFile stairsInner = provider.models().stairsInner(name(stairBlock) + "_inner", texture, texture, texture)
                    .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 8.0F, 16.0F)
                    .face(Direction.DOWN).uvs(0.0F, 0.0F, 16.0F, 16.0F)
                    .texture("#bottom").cullface(Direction.DOWN).emissivity((15), (15)).end()
                    .face(Direction.UP).uvs(0.0F, 0.0F, 16.0F, 16.0F)
                    .texture("#top").emissivity((15), (15)).end()
                    .allFacesExcept((direction, faceBuilder) -> faceBuilder.texture("#side")
                            .uvs(0.0F, 8.0F, 16.0F, 16.0F).cullface(direction)
                            .emissivity((15), (15)), Set.of(Direction.DOWN, Direction.UP)).end()
                    .element().from(8.0F, 8.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.UP).uvs(8.0F, 0.0F, 16.0F, 16.0F).texture("#top").cullface(Direction.UP).emissivity((15), (15)).end()
                    .face(Direction.NORTH).uvs(0.0F, 0.0F, 8.0F, 8.0F).texture("#side").cullface(Direction.NORTH).emissivity((15), (15)).end()
                    .face(Direction.SOUTH).uvs(8.0F, 0.0F, 16.0F, 8.0F).texture("#side").cullface(Direction.SOUTH).emissivity((15), (15)).end()
                    .face(Direction.WEST).uvs(0.0F, 0.0F, 16.0F, 8.0F).texture("#side").emissivity((15), (15)).end()
                    .face(Direction.EAST).uvs(0.0F, 0.0F, 16.0F, 8.0F).texture("#side").cullface(Direction.EAST).emissivity((15), (15)).end().end()
                    .element().from(0.0F, 8.0F, 8.0F).to(8.0F, 16.0F, 16.0F)
                    .face(Direction.UP).uvs(0.0F, 8.0F, 8.0F, 16.0F).texture("#top").cullface(Direction.UP).emissivity((15), (15)).end()
                    .face(Direction.NORTH).uvs(8.0F, 0.0F, 16.0F, 8.0F).texture("#side").emissivity((15), (15)).end()
                    .face(Direction.SOUTH).uvs(0.0F, 0.0F, 8.0F, 8.0F).texture("#side").cullface(Direction.SOUTH).emissivity((15), (15)).end()
                    .face(Direction.WEST).uvs(8.0F, 0.0F, 16.0F, 8.0F).texture("#side").cullface(Direction.WEST).emissivity((15), (15)).end().end();
            ModelFile stairsOuter = provider.models().stairsOuter(name(stairBlock) + "_outer", texture, texture, texture)
                    .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 8.0F, 16.0F)
                    .face(Direction.DOWN).uvs(0.0F, 0.0F, 16.0F, 16.0F)
                    .texture("#bottom").cullface(Direction.DOWN).emissivity((15), (15)).end()
                    .face(Direction.UP).uvs(0.0F, 0.0F, 16.0F, 16.0F)
                    .texture("#top").emissivity((15), (15)).end()
                    .allFacesExcept((direction, faceBuilder) -> faceBuilder.texture("#side")
                            .uvs(0.0F, 8.0F, 16.0F, 16.0F).cullface(direction)
                            .emissivity((15), (15)), Set.of(Direction.DOWN, Direction.UP)).end()
                    .element().from(8.0F, 8.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.UP).uvs(8.0F, 0.0F, 16.0F, 16.0F).texture("#top").cullface(Direction.UP).emissivity((15), (15)).end()
                    .face(Direction.NORTH).uvs(0.0F, 0.0F, 8.0F, 8.0F).texture("#side").emissivity((15), (15)).end()
                    .face(Direction.SOUTH).uvs(8.0F, 0.0F, 16.0F, 8.0F).texture("#side").cullface(Direction.SOUTH).emissivity((15), (15)).end()
                    .face(Direction.WEST).uvs(0.0F, 0.0F, 16.0F, 8.0F).texture("#side").emissivity((15), (15)).end()
                    .face(Direction.EAST).uvs(0.0F, 0.0F, 8.0F, 8.0F).texture("#side").cullface(Direction.EAST).emissivity((15), (15)).end().end();
            provider.stairsBlock(stairBlock, stairs, stairsInner, stairsOuter);
        } else {
            provider.stairsBlock(stairBlock, provider.blockTexture(stairBlock.base));
        }
    }

    public static void registerSlabStates(SlabBlock slabBlock, ResourceLocation texture, BlockStateProvider provider, boolean emissivity) {
        if (emissivity) {
            ModelFile bottom = provider.models().slab(name(slabBlock), texture, texture, texture)
                    .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 8.0F, 16.0F)
                    .face(Direction.DOWN).uvs(0.0F, 0.0F, 16.0F, 16.0F)
                    .texture("#bottom").cullface(Direction.DOWN).emissivity((15), (15)).end()
                    .face(Direction.UP).uvs(0.0F, 0.0F, 16.0F, 16.0F)
                    .texture("#top").emissivity((15), (15)).end()
                    .allFacesExcept((direction, faceBuilder) -> faceBuilder.texture("#side")
                            .uvs(0.0F, 8.0F, 16.0F, 16.0F).cullface(direction)
                            .emissivity((15), (15)), Set.of(Direction.DOWN, Direction.UP)).end();
            ModelFile top = provider.models().slabTop(name(slabBlock) + "_top", texture, texture, texture)
                    .element().from(0.0F, 8.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.DOWN).uvs(0.0F, 0.0F, 16.0F, 16.0F)
                    .texture("#bottom").emissivity((15), (15)).end()
                    .face(Direction.UP).uvs(0.0F, 0.0F, 16.0F, 16.0F)
                    .texture("#top").cullface(Direction.UP).emissivity((15), (15)).end()
                    .allFacesExcept((direction, faceBuilder) -> faceBuilder.texture("#side")
                            .uvs(0.0F, 0.0F, 16.0F, 8.0F).cullface(direction)
                            .emissivity((15), (15)), Set.of(Direction.DOWN, Direction.UP)).end();
            provider.slabBlock(slabBlock, bottom, top, provider.models().getExistingFile(texture));
        } else {
            provider.slabBlock(slabBlock, texture, texture);
        }
    }

    public static void registerWallStates(WallBlock wallBlock, ResourceLocation texture, BlockStateProvider provider, boolean emissivity) {
        if (emissivity) {
            String baseName = BuiltInRegistries.BLOCK.getKey(wallBlock).toString();
            ModelFile post = provider.models().wallPost(baseName + "_post", texture)
                    .element().from(4.0F, 0.0F, 4.0F).to(12.0F, 16.0F, 12.0F)
                    .face(Direction.DOWN).texture("#wall").cullface(Direction.DOWN).emissivity((15), (15)).end()
                    .face(Direction.UP).texture("#wall").cullface(Direction.UP).emissivity((15), (15)).end()
                    .allFacesExcept((direction, faceBuilder) -> faceBuilder.texture("#wall").cullface(direction)
                            .emissivity((15), (15)).end(), Set.of(Direction.DOWN, Direction.UP)).end();
            ModelFile side = provider.models().wallSide(baseName + "_side", texture)
                    .element().from(4.0F, 0.0F, 4.0F).to(12.0F, 16.0F, 12.0F)
                    .face(Direction.DOWN).texture("#wall").cullface(Direction.DOWN).emissivity((15), (15)).end()
                    .face(Direction.NORTH).texture("#wall").cullface(Direction.NORTH).emissivity((15), (15)).end()
                    .allFacesExcept((direction, faceBuilder) -> faceBuilder.texture("#wall").cullface(direction)
                            .emissivity((15), (15)).end(), Set.of(Direction.DOWN, Direction.NORTH, Direction.SOUTH)).end();
            ModelFile sideTall = provider.models().wallSideTall(baseName + "_side_tall", texture)
                    .element().from(4.0F, 0.0F, 4.0F).to(12.0F, 16.0F, 12.0F)
                    .face(Direction.DOWN).texture("#wall").cullface(Direction.DOWN).emissivity((15), (15)).end()
                    .face(Direction.UP).texture("#wall").cullface(Direction.UP).emissivity((15), (15)).end()
                    .face(Direction.NORTH).texture("#wall").cullface(Direction.NORTH).emissivity((15), (15)).end()
                    .face(Direction.WEST).texture("#wall").emissivity((15), (15)).end()
                    .face(Direction.EAST).texture("#wall").emissivity((15), (15)).end().end();
            provider.wallBlock(wallBlock, post, side, sideTall);
        } else {
            provider.wallBlock(wallBlock, texture);
        }
    }

    public static void registerBarStates(Block block, BlockStateProvider provider) {
        String name = name(block);
        ResourceLocation texture = provider.blockTexture(block);
        ModelFile post = provider.models().getBuilder(name + "_post").renderType(CUTOUT_MIPPED)
                .texture("particle", texture).texture("bars", texture).ao(false)
                .element().from(8.0F, 0.0F, 7.0F).to(8.0F, 16.0F, 9.0F)
                .face(Direction.WEST).uvs(7.0F, 0.0F, 9.0F, 16.0F).texture("#bars").end()
                .face(Direction.EAST).uvs(9.0F, 0.0F, 7.0F, 16.0F).texture("#bars").end().end()
                .element().from(7.0F, 0.0F, 8.0F).to(9.0F, 16.0F, 8.0F)
                .face(Direction.NORTH).uvs(7.0F, 0.0F, 9.0F, 16.0F).texture("#bars").end()
                .face(Direction.SOUTH).uvs(9.0F, 0.0F, 7.0F, 16.0F).texture("#bars").end().end();
        ModelFile postEnds = provider.models().getBuilder(name + "_post_ends").renderType(CUTOUT_MIPPED)
                .texture("particle", texture).texture("edge", texture).ao(false)
                .element().from(7.0F, 0.001F, 7.0F).to(9.0F, 0.001F, 9.0F)
                .face(Direction.DOWN).uvs(7.0F, 7.0F, 9.0F, 9.0F).texture("#edge").end()
                .face(Direction.UP).uvs(7.0F, 7.0F, 9.0F, 9.0F).texture("#edge").end().end()
                .element().from(7.0F, 15.999F, 7.0F).to(9.0F, 15.999F, 9.0F)
                .face(Direction.DOWN).uvs(7.0F, 7.0F, 9.0F, 9.0F).texture("#edge").end()
                .face(Direction.UP).uvs(7.0F, 7.0F, 9.0F, 9.0F).texture("#edge").end().end();
        ModelFile cap = provider.models().getBuilder(name + "_cap")
                .renderType(CUTOUT_MIPPED).texture("particle", texture)
                .texture("edge", texture).texture("bars", texture).ao(false)
                .element().from(8.0F, 0.0F, 8.0F).to(8.0F, 16.0F, 9.0F)
                .face(Direction.WEST).uvs(8.0F, 0.0F, 7.0F, 16.0F).texture("#bars").end()
                .face(Direction.EAST).uvs(7.0F, 0.0F, 8.0F, 16.0F).texture("#bars").end().end()
                .element().from(7.0F, 0.0F, 9.0F).to(9.0F, 16.0F, 9.0F)
                .face(Direction.NORTH).uvs(9.0F, 0.0F, 7.0F, 16.0F).texture("#bars").end()
                .face(Direction.SOUTH).uvs(7.0F, 0.0F, 9.0F, 16.0F).texture("#bars").end().end();
        ModelFile capAlt = provider.models().getBuilder(name + "_cap_alt")
                .renderType(CUTOUT_MIPPED).texture("particle", texture)
                .texture("edge", texture).texture("bars", texture).ao(false)
                .element().from(8.0F, 0.0F, 7.0F).to(8.0F, 16.0F, 7.0F)
                .face(Direction.WEST).uvs(8.0F, 0.0F, 9.0F, 16.0F).texture("#bars").end()
                .face(Direction.EAST).uvs(9.0F, 0.0F, 8.0F, 16.0F).texture("#bars").end().end()
                .element().from(7.0F, 0.0F, 7.0F).to(9.0F, 16.0F, 7.0F)
                .face(Direction.NORTH).uvs(7.0F, 0.0F, 9.0F, 16.0F).texture("#bars").end()
                .face(Direction.SOUTH).uvs(9.0F, 0.0F, 7.0F, 16.0F).texture("#bars").end().end();
        ModelFile side = provider.models().getBuilder(name + "_side").renderType(CUTOUT_MIPPED)
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
        ModelFile sideAlt = provider.models().getBuilder(name + "_side_alt").renderType(CUTOUT_MIPPED)
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
        MultiPartBlockStateBuilder builder = provider.getMultipartBuilder(block).part().modelFile(postEnds).addModel().end();
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
            BlockModelBuilder modelBuilder = this.models().torchWall(name(block), this.blockTexture(wallBlockItem.getBlock())).renderType(CUTOUT);
            Map<Direction, Integer> map = Map.of(Direction.NORTH, 270, Direction.EAST, 0, Direction.SOUTH, 90, Direction.WEST, 180);
            for (Direction direction : WallTorchBlock.FACING.getPossibleValues()) {
                stateBuilder.partialState().with(WallTorchBlock.FACING, direction).modelForState().modelFile(modelBuilder).rotationY(map.get(direction)).addModel();
            }
        }
    }

    public static void registerCropStates(Block block, BlockStateProvider provider) {
        VariantBlockStateBuilder builder = provider.getVariantBuilder(block);
        for (int stage : TACropBlock.AGE.getPossibleValues()) {
            String name = name(block) + "_stage" + stage;
            ResourceLocation texture = provider.modLoc("block/" + name);
            ModelFile modelFile = provider.models().crop(name, texture).renderType(CUTOUT);
            builder.partialState().with(TACropBlock.AGE, stage).modelForState().modelFile(modelFile).addModel();
        }
    }

    public static void registerCrossStates(Block block, BlockStateProvider provider) {
        VariantBlockStateBuilder builder = provider.getVariantBuilder(block);
        for (int stage : TACropBlock.AGE.getPossibleValues()) {
            String name = name(block) + "_stage" + stage;
            ResourceLocation texture = provider.modLoc("block/" + name);
            ModelFile modelFile = provider.models().cross(name, texture).renderType(CUTOUT);
            builder.partialState().with(TACropBlock.AGE, stage).modelForState().modelFile(modelFile).addModel();
        }
    }

    public static void registerVerticalStairStates(Block block, Block base, BlockStateProvider provider, boolean emissivity) {
        String suffix = emissivity ? "luminous_vertical_stair" : "vertical_stair";
        DirectionProperty facing = VerticalStairBlock.FACING;
        ModelFile modelFile = provider.models()
                .withExistingParent(name(block), provider.modLoc("block/" + suffix))
                .texture("all", provider.blockTexture(base));
        facing.getPossibleValues().forEach(direction -> {
            int y = (int) (direction.toYRot() - 180.0F);
            provider.getVariantBuilder(block).partialState().with(facing, direction).modelForState()
                    .rotationY(y).uvLock(true).modelFile(modelFile).addModel();
        });
    }

    public static void registerVerticalSlabStates(Block block, Block base, BlockStateProvider provider, boolean emissivity) {
        BlockModelBuilder normal = provider.models().withExistingParent(name(block),
                        provider.modLoc("block/" + (emissivity ? "luminous_vertical_slab" : "vertical_slab")))
                .texture("all", provider.blockTexture(base));
        BlockModelBuilder full = provider.models().withExistingParent(name(block) + "_full",
                provider.mcLoc("block/cube_all")).texture("all", provider.blockTexture(base));
        BlockModelBuilder post = provider.models().withExistingParent(name(block) + "_post",
                        provider.modLoc("block/" + (emissivity ? "luminous_vertical_slab_post" : "vertical_slab_post")))
                .texture("all", provider.blockTexture(base));
        provider.getVariantBuilder(block).forAllStatesExcept(state -> {
            VerticalSlabShape slabType = state.getValue(VerticalSlabBlock.SHAPE);
            VerticalSlabBlock.Connection connection = state.getValue(VerticalSlabBlock.CONNECTION);
            ConfiguredModel model = slabType == VerticalSlabShape.FULL ? new ConfiguredModel(full)
                    : connection == VerticalSlabBlock.Connection.NONE ? new ConfiguredModel(normal, 0,
                    slabType.getModelRotation(), true) : new ConfiguredModel(post, 0,
                    (int)(connection == VerticalSlabBlock.Connection.LEFT ? slabType.getDirection() :
                            slabType.getDirection().getClockWise()).toYRot() - 180, true);
            return new ConfiguredModel[] {model};
        }, VerticalSlabBlock.WATERLOGGED);
    }

    public static void registerFilthyIceSpike(Block block, BlockStateProvider provider) {
        DirectionProperty property = BlockStateProperties.VERTICAL_DIRECTION;
        VariantBlockStateBuilder builder = provider.getVariantBuilder(block);
        ResourceLocation texture = provider.modLoc("block/" + name(block));
        ModelFile modelFile = provider.models().cross(name(block), texture).renderType(CUTOUT);
        for (Direction direction : property.getPossibleValues()) {
            builder.partialState().with(property, direction).modelForState().modelFile(modelFile).addModel();
        }
    }

    public static void registerClusterStates(Block block, BlockStateProvider provider) {
        VariantBlockStateBuilder builder = provider.getVariantBuilder(block);
        ResourceLocation texture = provider.modLoc("block/" + name(block));
        ModelFile modelFile = provider.models().cross(name(block), texture).renderType(CUTOUT);
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

    public static void registerKeyholeStates(Block block, BlockStateProvider provider) {
        VariantBlockStateBuilder builder = provider.getVariantBuilder(block);
        BooleanProperty property = DungeonStoneGate.UNLOCKED;
        property.getPossibleValues().forEach(unlocked -> {
            String name = "block/" + name(block) + (unlocked ? "_unlocked" : "");
            ModelFile modelFile = provider.models().cubeAll(name, provider.modLoc(name));
            builder.partialState().with(property, unlocked).modelForState().modelFile(modelFile).addModel();
        });
    }

    public static void registerCeilingHangingSignStates(Block standing, Block original, BlockStateProvider provider) {
        provider.simpleBlock(standing, provider.models().sign(name(standing), provider.blockTexture(original)));
    }

    public static void registerWallHangingSignStates(Block standing, Block wall, Block original, BlockStateProvider provider) {
        provider.simpleBlock(wall, provider.models().sign(name(standing), provider.blockTexture(original)));
    }

    public static void registerPottedPlantStates(Block block, Block content, BlockStateProvider provider) {
        provider.simpleBlock(block, provider.models().withExistingParent(name(block), "block/flower_pot_cross")
                .renderType(CUTOUT).texture("plant", provider.blockTexture(content)));
    }

    public static void registerPlantStates(Block block, BlockStateProvider provider) {
        provider.simpleBlock(block, provider.models().cross(name(block), provider.blockTexture(block)).renderType(CUTOUT));
    }

    public static void registerLightPlantStates(Block block, BlockStateProvider provider) {
        VariantBlockStateBuilder builder = provider.getVariantBuilder(block);
        for (int level : BlockStateProperties.LEVEL.getPossibleValues()) {
            ResourceLocation parent = provider.mcLoc("block/cross");
            ResourceLocation texture = provider.modLoc("block/" + name(block));
            ModelFile modelFile = provider.models().withExistingParent(name(block), parent).texture("cross", texture).renderType(CUTOUT);
            builder.partialState().with(BlockStateProperties.LEVEL, level).modelForState().modelFile(modelFile).addModel();
        }
    }

    public static void registerDoublePlantStates(Block block, BlockStateProvider provider) {
        VariantBlockStateBuilder builder = provider.getVariantBuilder(block);
        for (DoubleBlockHalf half : DoublePlantBlock.HALF.getPossibleValues()) {
            String name = name(block) + "_" + half.toString();
            ResourceLocation parent = provider.mcLoc("block/tinted_cross");
            ResourceLocation texture = provider.modLoc("block/" + name);
            ModelFile modelFile = provider.models().withExistingParent(name, parent)
                    .texture("cross", texture).renderType(CUTOUT);
            builder.partialState().with(DoublePlantBlock.HALF, half)
                    .modelForState().modelFile(modelFile).addModel();
        }
    }

    public static void registerDoubleLightPlantStates(Block block, BlockStateProvider provider) {
        VariantBlockStateBuilder builder = provider.getVariantBuilder(block);
        for (DoubleBlockHalf half : DoublePlantBlock.HALF.getPossibleValues()) {
            for (int level : BlockStateProperties.LEVEL.getPossibleValues()) {
                String name = name(block) + "_" + half.toString();
                ResourceLocation texture = provider.modLoc("block/" + name);
                ModelFile modelFile = provider.models().withExistingParent(name,
                                provider.mcLoc("block/tinted_cross"))
                        .texture("cross", texture).renderType(CUTOUT);
                builder.partialState().with(DoublePlantBlock.HALF, half)
                        .with(BlockStateProperties.LEVEL, level)
                        .modelForState().modelFile(modelFile).addModel();
            }
        }
    }

    public static void registerWaterSurfacePlantStates(Block block, BlockStateProvider provider) {
        IntegerProperty property = AurorianWaterSurfacePlant.LEVEL;
        VariantBlockStateBuilder builder = provider.getVariantBuilder(block);
        for (int level : property.getPossibleValues()) {
            ModelFile modelFile = new ModelFile.UncheckedModelFile(provider.modLoc("block/" + name(block)));
            builder.partialState().with(property, level).modelForState().modelFile(modelFile).addModel();
        }
    }

    public static void registerLuminousStates(Block newBlock, Block originalBlock, BlockStateProvider provider) {
        VariantBlockStateBuilder builder = provider.getVariantBuilder(newBlock);
        ModelFile modelFile = provider.models().getBuilder(name(newBlock))
                .parent(new ModelFile.UncheckedModelFile(provider.mcLoc("block/block")))
                .texture("rune", provider.blockTexture(originalBlock))
                .texture("particle", provider.blockTexture(originalBlock))
                .ao(false).element().allFaces((direction, faceBuilder) -> faceBuilder.texture("#rune")
                        .emissivity((15), (15)).cullface(direction)).end();
        builder.partialState().modelForState().modelFile(modelFile).addModel();
    }

    private void registerLiquidStates() {
        this.simpleBlock(TABlocks.MOLTEN_MOONSILVER.get(), this.models()
                .getBuilder(TABlocks.MOLTEN_MOONSILVER.getId().getPath())
                .texture("particle", this.modLoc("block/molten_moonsilver")));
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
        ModelFile modelFile = this.models().withExistingParent(name(block), this.mcLoc("block/orientable"))
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

    public static void registerLargeFilthyIceSpike(Block block, BlockStateProvider provider) {
        VariantBlockStateBuilder builder = provider.getVariantBuilder(block);
        DirectionProperty property1 = BlockStateProperties.VERTICAL_DIRECTION;
        EnumProperty<DoubleBlockHalf> property2 = BlockStateProperties.DOUBLE_BLOCK_HALF;
        ResourceLocation lowerTexture = provider.modLoc("block/large_filthy_ice_spike");
        ResourceLocation upperTexture = provider.modLoc("block/medium_filthy_ice_spike");
        ResourceLocation parent = provider.mcLoc("block/tinted_cross");
        Map<DoubleBlockHalf, ResourceLocation> map = Map.of(
                DoubleBlockHalf.LOWER, lowerTexture,
                DoubleBlockHalf.UPPER, upperTexture);
        for (Direction direction : property1.getPossibleValues()) {
            for (DoubleBlockHalf half : property2.getPossibleValues()) {
                String name = name(block) + "_" + half.toString();
                ModelFile modelFile = provider.models().withExistingParent(name, parent)
                        .texture("cross", map.get(half)).renderType(CUTOUT);
                builder.partialState().with(property1, direction).with(property2, half)
                        .modelForState().modelFile(modelFile).addModel();
            }
        }
    }

    public static void registerSilentWoodLadderState(Block block, BlockStateProvider provider) {
        VariantBlockStateBuilder variantBuilder = provider.getVariantBuilder(block);
        BlockModelBuilder builder = provider.models().getBuilder(name(block));
        ConfiguredModel configuredModel = new ConfiguredModel(builder.ao(false)
                .texture("particle", provider.modLoc("block/" + name(block)))
                .texture("texture", provider.modLoc("block/" + name(block)))
                .element().from(0.0F, 0.0F, 15.2F).to(16.0F, 16.0F, 15.2F).shade(false)
                .face(Direction.NORTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#texture").end()
                .face(Direction.SOUTH).uvs(16.0F, 0.0F, 0.0F, 16.0F).texture("#texture").end().end().renderType(CUTOUT));
        for (Direction direction : LadderBlock.FACING.getPossibleValues()) {
            variantBuilder.partialState().with(LadderBlock.FACING, direction)
                    .modelForState().modelFile(configuredModel.model)
                    .rotationY(DIRECTION_WITH_ROTATION.get(direction)).addModel();
        }
    }

    public static void simpleBlockWithRenderType(Block block, ResourceLocation type, BlockStateProvider provider) {
        provider.simpleBlock(block, provider.models().cubeAll(name(block), provider.blockTexture(block)).renderType(type));
    }

    public static String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

}