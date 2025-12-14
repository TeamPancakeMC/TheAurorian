package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.VerticalSlabBlock;
import cn.teampancake.theaurorian.common.blocks.VerticalStairBlock;
import cn.teampancake.theaurorian.common.blocks.modified.AxeStrippableBlock;
import cn.teampancake.theaurorian.common.data.datagen.provider.TABlockStateProvider;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.*;
import cn.teampancake.theaurorian.compat.registrate.TARegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

import static cn.teampancake.theaurorian.common.data.datagen.provider.TABlockStateProvider.*;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.*;

@SuppressWarnings("unchecked")
public class TABlockRegUtils {

    public static <T extends Block> BlockBuilder<T, TARegistrate> registerNoItemBuilder(String name, NonNullFunction<BlockBehaviour.Properties, T> factory) {
        return TheAurorian.REGISTRATE.block(name, factory);
    }

    public static <T extends Block> BlockBuilder<T, TARegistrate> registerNoItemModelBuilder(String name, NonNullFunction<BlockBehaviour.Properties, T> factory) {
        BlockBuilder<T, TARegistrate> builder = registerNoItemBuilder(name, factory);
        return builder.getOwner().item(builder, name, p -> new BlockItem(builder.get().get(),
                p.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON))).build();
    }

    public static <T extends Block> BlockBuilder<T, TARegistrate> registerBuilder(String name, NonNullFunction<BlockBehaviour.Properties, T> factory) {
        BlockBuilder<T, TARegistrate> builder = registerNoItemBuilder(name, factory);
        return builder.getOwner().item(builder, name, p -> new BlockItem(builder.get().get(),
                        p.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON)))
                .model((ctx, prov) -> prov.blockItem(ctx::get)).build();
    }

    public static BlockBuilder<Block, TARegistrate> simpleBuilder(String name, BlockBehaviour.Properties properties) {
        return registerBuilder(name, p -> new Block(properties)).defaultBlockstate();
    }

    public static BlockBuilder<DropExperienceBlock, TARegistrate> oreBuilder(String name, IntProvider xpRange, BlockBehaviour.Properties properties) {
        return registerBuilder(name, p -> new DropExperienceBlock(xpRange, properties.requiresCorrectToolForDrops())).tag(BlockTags.MINEABLE_WITH_PICKAXE).defaultBlockstate();
    }

    @SafeVarargs
    public static BlockBuilder<Block, TARegistrate> runestoneBuilder(String name, BlockBehaviour.Properties properties, TagKey<Block>... values) {
        return simpleBuilder(name, TABlocks.runestoneProperties(properties)).tag(TABlockTags.DUNGEON_BLOCKS).tag(values).defaultLoot().item().tag(copyToItemTags(values)).build();
    }

    public static <T extends Block> BlockEntry<T> register(String name, NonNullFunction<BlockBehaviour.Properties, T> factory) {
        return registerBuilder(name, factory).register();
    }

    @SafeVarargs
    public static BlockEntry<AxeStrippableBlock> wood(String name, Supplier<RotatedPillarBlock> block, MapColor mapColor, float strength, TagKey<Block>... values) {
        return registerBuilder(name, properties -> new AxeStrippableBlock(block, properties.instrument(NoteBlockInstrument.BASS)
                .mapColor(mapColor).strength(strength).sound(SoundType.WOOD).ignitedByLava())).tag(values).defaultLoot()
                .blockstate((ctx, prov) -> TABlockStateProvider.registerPillarStates(ctx.get(), prov))
                .item().tag(copyToItemTags(values)).build().register();
    }

    @SafeVarargs
    public static BlockEntry<RotatedPillarBlock> strippedWood(String name, MapColor mapColor, float strength, TagKey<Block>... values) {
        return registerBuilder(name, properties -> new RotatedPillarBlock(properties.instrument(NoteBlockInstrument.BASS)
                .mapColor(mapColor).strength(strength).sound(SoundType.WOOD).ignitedByLava())).tag(values).defaultLoot()
                .blockstate((ctx, prov) -> TABlockStateProvider.registerPillarStates(ctx.get(), prov))
                .item().tag(copyToItemTags(values)).build().register();
    }

    public static<T extends Block> BlockEntry<Block> flowerPot(DeferredHolder<Block, T> block) {
        return registerNoItemBuilder("potted_" + block.getId().getPath(), p -> Blocks.flowerPot(block.get()))
                .tag(BlockTags.FLOWER_POTS).loot((ctx, prov) -> ctx.add(prov, ctx.createPotFlowerItemTable(block.get())))
                .blockstate((ctx, prov) -> registerPottedPlantStates(ctx.get(), block.get(), prov)).register();
    }

    @SafeVarargs
    public static <T extends Block> BlockEntry<VerticalStairBlock> verticalStair(String name, Supplier<T> base, BlockBehaviour.Properties properties, TagKey<Block>... values) {
        properties.requiredFeatures = properties.requiredFeatures.join(FeatureFlagSet.of(TAFeatureFlags.BUILDING));
        FeatureFlagSet requiredFeatures = properties.requiredFeatures;
        boolean isWooden = requiredFeatures.contains(TAFeatureFlags.WOOD_MATERIAL);
        boolean emissivity = requiredFeatures.contains(TAFeatureFlags.EMISSIVITY);
        NonNullFunction<BlockBehaviour.Properties, VerticalStairBlock> factory = p -> new VerticalStairBlock(properties.noOcclusion());
        BlockBuilder<VerticalStairBlock, TARegistrate> blockBuilder = registerBuilder(name, factory).tag(TABlockTags.VERTICAL_STAIRS).tag(values).defaultLoot()
                .blockstate((ctx, prov) -> registerVerticalStairStates(ctx.get(), base.get(), prov, emissivity)).item().recipe((ctx, prov) -> {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ctx.get(), 4)
                            .define('#', Ingredient.of(base.get())).pattern("#").pattern("#").pattern("#")
                            .unlockedBy(getHasName(base.get()), has(base.get())).save(prov);
                    if (!isWooden) stonecutterResultFromBase(prov, RecipeCategory.BUILDING_BLOCKS, ctx.get(), base.get());
                }).tag(TAItemTags.VERTICAL_STAIRS).build();
        if (requiredFeatures.contains(TAFeatureFlags.DUNGEON)) blockBuilder = blockBuilder.tag(TABlockTags.DUNGEON_BLOCKS);
        return isWooden ? blockBuilder.tag(BlockTags.MINEABLE_WITH_AXE).register() : blockBuilder.tag(BlockTags.MINEABLE_WITH_PICKAXE).register();
    }

    @SafeVarargs
    public static <T extends Block> BlockEntry<VerticalSlabBlock> verticalSlab(String name, Supplier<T> base, BlockBehaviour.Properties properties, TagKey<Block>... values) {
        properties.requiredFeatures = properties.requiredFeatures.join(FeatureFlagSet.of(TAFeatureFlags.BUILDING));
        FeatureFlagSet requiredFeatures = properties.requiredFeatures;
        boolean isWooden = requiredFeatures.contains(TAFeatureFlags.WOOD_MATERIAL);
        boolean emissivity = requiredFeatures.contains(TAFeatureFlags.EMISSIVITY);
        NonNullFunction<BlockBehaviour.Properties, VerticalSlabBlock> factory = p -> new VerticalSlabBlock(properties.noOcclusion());
        BlockBuilder<VerticalSlabBlock, TARegistrate> blockBuilder = registerBuilder(name, factory).tag(TABlockTags.VERTICAL_SLABS).tag(values).defaultLoot()
                .blockstate((ctx, prov) -> registerVerticalSlabStates(ctx.get(), base.get(), prov, emissivity)).item().recipe((ctx, prov) -> {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ctx.get(), 6)
                            .define('#', Ingredient.of(base.get())).pattern("###").pattern(" ##").pattern("  #")
                            .unlockedBy(getHasName(base.get()), has(base.get())).save(prov);
                    if (!isWooden) stonecutterResultFromBase(prov, RecipeCategory.BUILDING_BLOCKS, ctx.get(), base.get(), 2);
                }).tag(TAItemTags.VERTICAL_SLABS).build();
        if (requiredFeatures.contains(TAFeatureFlags.DUNGEON)) blockBuilder = blockBuilder.tag(TABlockTags.DUNGEON_BLOCKS);
        return isWooden ? blockBuilder.tag(BlockTags.MINEABLE_WITH_AXE).register() : blockBuilder.tag(BlockTags.MINEABLE_WITH_PICKAXE).register();
    }

    @SafeVarargs
    public static <T extends Block> BlockEntry<PressurePlateBlock> pressurePlate(String name, Supplier<T> base, BlockBehaviour.Properties properties, BlockSetType blockSetType, TagKey<Block>... values) {
        return registerBuilder(name, p -> new PressurePlateBlock(blockSetType, properties)).tag(values).defaultLoot()
                .blockstate((ctx, prov) -> prov.pressurePlateBlock(ctx.get(), prov.blockTexture(base.get())))
                .item().recipe((ctx, prov) -> RegistrateRecipeProvider.pressurePlate(prov, ctx.get(), base.get())).build().register();
    }

    public static <T extends Block> BlockEntry<FenceGateBlock> fenceGate(String name, Supplier<T> base, BlockBehaviour.Properties properties, WoodType woodType) {
        return registerBuilder(name, p -> new FenceGateBlock(woodType, properties)).tag(BlockTags.FENCE_GATES).defaultLoot()
                .blockstate((ctx, prov) -> prov.fenceGateBlockWithRenderType(ctx.get(), prov.blockTexture(base.get()), CUTOUT))
                .item().recipe((ctx, prov) -> fenceGateBuilder(ctx.get(), Ingredient.of(base.get()))
                        .unlockedBy(getHasName(base.get()), has(base.get())).save(prov)).build().register();
    }

    @SafeVarargs
    public static <T extends Block> BlockEntry<TrapDoorBlock> trapdoor(String name, Supplier<T> base, BlockBehaviour.Properties properties, BlockSetType blockSetType, TagKey<Block>... values) {
        return registerBuilder(name, p -> new TrapDoorBlock(blockSetType, properties)).tag(values).defaultLoot().blockstate((ctx, prov) -> {
            ResourceLocation texture = prov.blockTexture(ctx.get());
            prov.trapdoorBlockWithRenderType(ctx.get(), texture, Boolean.TRUE, CUTOUT);
            prov.simpleBlockItem(ctx.get(), prov.models().trapdoorBottom(name, texture));
        }).item().recipe((ctx, prov) -> {
            RecipeBuilder builder = trapdoorBuilder(ctx.get(), Ingredient.of(base.get()));
            builder.unlockedBy(getHasName(base.get()), has(base.get())).save(prov);
        }).build().register();
    }

    @SafeVarargs
    public static <T extends Block> BlockEntry<ButtonBlock> button(String name, Supplier<T> base, boolean sensitive, BlockSetType blockSetType, TagKey<Block>... values) {
        return registerBuilder(name, p -> new ButtonBlock(blockSetType, sensitive ? 30 : 20, p.noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)))
                .tag(values).defaultLoot().blockstate((ctx, prov) -> {
                    String path = name + "_inventory";
                    ResourceLocation texture = prov.blockTexture(base.get());
                    ModelFile buttonInventory = prov.models().buttonInventory(path, texture);
                    prov.buttonBlock(ctx.get(), texture);
                    prov.simpleBlockItem(ctx.get(), buttonInventory);
                }).item().recipe((ctx, prov) -> {
                    RecipeBuilder builder = buttonBuilder(ctx.get(), Ingredient.of(base.get()));
                    builder.unlockedBy(getHasName(base.get()), has(base.get())).save(prov);
                }).build().register();
    }

    @SafeVarargs
    public static <T extends Block> BlockEntry<StairBlock> stair(String name, Supplier<T> base, BlockBehaviour.Properties properties, TagKey<Block>... values) {
        properties.requiredFeatures = properties.requiredFeatures.join(FeatureFlagSet.of(TAFeatureFlags.BUILDING));
        FeatureFlagSet requiredFeatures = properties.requiredFeatures;
        boolean isWooden = requiredFeatures.contains(TAFeatureFlags.WOOD_MATERIAL);
        BlockBuilder<StairBlock, TARegistrate> blockBuilder = registerBuilder(name, p -> new StairBlock(
                base.get().defaultBlockState(), properties.noOcclusion())).tag(values).defaultLoot()
                .blockstate((ctx, prov) -> registerStairStates(ctx.get(), prov.blockTexture(base.get()),
                        prov, requiredFeatures.contains(TAFeatureFlags.EMISSIVITY))).item().recipe((ctx, prov) -> {
                            RecipeBuilder builder = stairBuilder(ctx.get(), Ingredient.of(base.get()));
                            builder.unlockedBy(getHasName(base.get()), has(base.get())).save(prov);
                            if (!isWooden) stonecutterResultFromBase(prov, RecipeCategory.BUILDING_BLOCKS, ctx.get(), base.get());
                        }).build();
        if (requiredFeatures.contains(TAFeatureFlags.DUNGEON)) blockBuilder = blockBuilder.tag(TABlockTags.DUNGEON_BLOCKS);
        return isWooden ? blockBuilder.tag(BlockTags.WOODEN_STAIRS).register() : blockBuilder.tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.STAIRS).register();
    }

    @SafeVarargs
    public static <T extends Block> BlockEntry<FenceBlock> fence(String name, Supplier<T> base, BlockBehaviour.Properties properties, TagKey<Block>... values) {
        return registerBuilder(name, p -> new FenceBlock(properties)).tag(values).defaultLoot().blockstate((ctx, prov) -> {
            ResourceLocation texture = prov.blockTexture(base.get());
            prov.fenceBlockWithRenderType(ctx.get(), texture, CUTOUT);
            prov.simpleBlockItem(ctx.get(), prov.models().fenceInventory(name, texture));
        }).item().recipe((ctx, prov) -> {
            RecipeBuilder builder = fenceBuilder(ctx.get(), Ingredient.of(base.get()));
            builder.unlockedBy(getHasName(base.get()), has(base.get())).save(prov);
        }).build().register();
    }

    @SafeVarargs
    public static <T extends Block> BlockEntry<DoorBlock> door(String name, Supplier<T> base, BlockBehaviour.Properties properties, BlockSetType blockSetType, TagKey<Block>... values) {
        return registerBuilder(name, p -> new DoorBlock(blockSetType, properties)).tag(values).loot((ctx, prov) -> ctx.add(prov, ctx.createDoorTable(prov))).blockstate((ctx, prov) ->
                prov.doorBlockWithRenderType(ctx.get(), prov.modLoc(String.format("block/%s_bottom", name)), prov.modLoc(String.format("block/%s_top", name)), CUTOUT))
                .item().defaultModel().recipe((ctx, prov) -> doorBuilder(ctx.get(), Ingredient.of(base.get())).unlockedBy(getHasName(base.get()), has(base.get())).save(prov)).build().register();
    }

    @SafeVarargs
    public static <T extends Block> BlockEntry<SlabBlock> slab(String name, Supplier<T> base, BlockBehaviour.Properties properties, TagKey<Block>... values) {
        properties.requiredFeatures = properties.requiredFeatures.join(FeatureFlagSet.of(TAFeatureFlags.BUILDING));
        FeatureFlagSet requiredFeatures = properties.requiredFeatures;
        boolean isWooden = requiredFeatures.contains(TAFeatureFlags.WOOD_MATERIAL);
        BlockBuilder<SlabBlock, TARegistrate> blockBuilder = registerBuilder(name, p -> new SlabBlock(properties.noOcclusion())).tag(values).defaultLoot()
                .blockstate((ctx, prov) -> registerSlabStates(ctx.get(), prov.blockTexture(base.get()), prov, requiredFeatures.contains(TAFeatureFlags.EMISSIVITY))).item().recipe((ctx, prov) -> {
                    RegistrateRecipeProvider.slab(prov, RecipeCategory.BUILDING_BLOCKS, ctx.get(), base.get());
                    if (!isWooden) stonecutterResultFromBase(prov, RecipeCategory.BUILDING_BLOCKS, ctx.get(), base.get(), 2);
                }).build();
        if (requiredFeatures.contains(TAFeatureFlags.DUNGEON)) blockBuilder = blockBuilder.tag(TABlockTags.DUNGEON_BLOCKS);
        return isWooden ? blockBuilder.tag(BlockTags.WOODEN_SLABS).register() : blockBuilder.tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.SLABS).register();
    }

    @SafeVarargs
    public static <T extends Block> BlockEntry<WallBlock> wall(String name, Supplier<T> base, BlockBehaviour.Properties properties, TagKey<Block>... values) {
        properties.requiredFeatures = properties.requiredFeatures.join(FeatureFlagSet.of(TAFeatureFlags.BUILDING));
        return registerBuilder(name, p -> new WallBlock(properties.noOcclusion().mapColor(MapColor.STONE)))
                .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.WALLS).tag(values).defaultLoot().blockstate((ctx, prov) -> {
                    boolean emissivity = properties.requiredFeatures.contains(TAFeatureFlags.EMISSIVITY);
                    ResourceLocation texture = prov.blockTexture(base.get());
                    TABlockStateProvider.registerWallStates(ctx.get(), texture, prov, emissivity);
                    prov.simpleBlockItem(ctx.get(), prov.models().wallInventory(name, texture));
                }).item().recipe((ctx, prov) -> {
                    RegistrateRecipeProvider.wall(prov, RecipeCategory.BUILDING_BLOCKS, ctx.get(), base.get());
                    stonecutterResultFromBase(prov, RecipeCategory.BUILDING_BLOCKS, ctx.get(), base.get());
                }).build().register();
    }

    private static TagKey<Item>[] copyToItemTags(TagKey<Block>... values) {
        TagKey<Item>[] itemKeys = new TagKey[values.length];
        for (int i = 0; i < values.length; i++) {
            itemKeys[i] = ItemTags.create(values[i].location());
        }

        return itemKeys;
    }

}