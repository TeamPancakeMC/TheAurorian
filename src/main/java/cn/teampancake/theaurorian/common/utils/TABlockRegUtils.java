package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.common.blocks.base.*;
import cn.teampancake.theaurorian.common.blocks.modified.AxeStrippableBlock;
import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import cn.teampancake.theaurorian.common.blocks.state.TALootType;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class TABlockRegUtils {

    public static DeferredHolder<Block, Block> register(String name, Supplier<Block> block) {
        DeferredHolder<Block, Block> register = TABlocks.BLOCKS.register(name, block);
        TAItemProperties itemProperties = TAItemProperties.get().addItemTag(TAItemTags.BUILDING_BLOCK);
        TAItems.ITEMS.register(name, () -> new BlockItem(register.get(), itemProperties));
        return register;
    }

    public static DeferredHolder<Block, Block> normal(String name, BlockBehaviour.Properties properties) {
        return register(name, () -> new Block(properties));
    }

    public static DeferredHolder<Block, Block> ore(String name, IntProvider xpRange, TABlockProperties properties) {
        return register(name, () -> new DropExperienceBlock(xpRange, properties.requiresCorrectToolForDrops()
                .addBlockTag(BlockTags.MINEABLE_WITH_PICKAXE).isSimpleModelBlock().useSimpleBlockItem()));
    }

    @SafeVarargs
    public static DeferredHolder<Block, Block> wood(String name, Supplier<Block> block, MapColor mapColor, float strength, TagKey<Block>... values) {
        return register(name, () -> new AxeStrippableBlock(block, TABlockProperties.get().instrument(NoteBlockInstrument.BASS).mapColor(mapColor)
                .strength(strength).sound(SoundType.WOOD).addBlockTag(values).lootType(TALootType.SELF).useSimpleBlockItem().ignitedByLava()));
    }

    @SafeVarargs
    public static DeferredHolder<Block, Block> wood(String name, MapColor mapColor, float strength, TagKey<Block>... values) {
        return register(name, () -> new RotatedPillarBlock(TABlockProperties.get().instrument(NoteBlockInstrument.BASS).mapColor(mapColor)
                .strength(strength).sound(SoundType.WOOD).addBlockTag(values).lootType(TALootType.SELF).useSimpleBlockItem().ignitedByLava()));
    }

    public static DeferredHolder<Block, Block> flowerPot(DeferredHolder<Block, Block> block) {
        return TABlocks.BLOCKS.register("potted_" + block.getId().getPath(), () -> Blocks.flowerPot(block.get()));
    }

    public static <T extends Block> DeferredHolder<Block, Block> verticalStair(String name, Supplier<T> base, TABlockProperties properties, boolean... isWooden) {
        TABlockProperties p1 = properties.addBlockTag(TABlockTags.VERTICAL_STAIRS).lootType(TALootType.SELF).isBuildingBlock();
        TABlockProperties p2 = properties.addBlockTag(BlockTags.MINEABLE_WITH_PICKAXE, TABlockTags.VERTICAL_STAIRS).lootType(TALootType.SELF).isBuildingBlock();
        return register(name, () -> new VerticalStairBlockWithBase(base.get(), isWooden.length > 0 ? p1.useSimpleBlockItem() : p2.useSimpleBlockItem()));
    }

    public static <T extends Block> DeferredHolder<Block, Block> verticalSlab(String name, Supplier<T> base, TABlockProperties properties, boolean... isWooden) {
        TABlockProperties p1 = properties.addBlockTag(TABlockTags.VERTICAL_SLABS).lootType(TALootType.SELF).isBuildingBlock();
        TABlockProperties p2 = properties.addBlockTag(BlockTags.MINEABLE_WITH_PICKAXE, TABlockTags.VERTICAL_SLABS).lootType(TALootType.SELF).isBuildingBlock();
        return register(name, () -> new VerticalSlabBlockWithBase(base.get(), isWooden.length > 0 ? p1.useSimpleBlockItem() : p2.useSimpleBlockItem()));
    }

    public static <T extends Block> DeferredHolder<Block, Block> pressurePlate(String name, Supplier<T> base, TABlockProperties properties, BlockSetType blockSetType) {
        return register(name, () -> new PressurePlateBlockWithBase(base.get(), properties.lootType(TALootType.SELF).useSimpleBlockItem(), blockSetType));
    }

    public static <T extends Block> DeferredHolder<Block, Block> fenceGate(String name, Supplier<T> base, TABlockProperties properties, WoodType woodType) {
        return register(name, () -> new FenceGateBlockWithBase(base.get(), properties.addBlockTag(BlockTags.FENCE_GATES).lootType(TALootType.SELF).useSimpleBlockItem(), woodType));
    }

    public static <T extends Block> DeferredHolder<Block, Block> trapdoor(String name, Supplier<T> base, TABlockProperties properties, BlockSetType blockSetType) {
        return register(name, () -> new TrapDoorBlockWithBase(base.get(), properties.lootType(TALootType.SELF), blockSetType));
    }

    @SafeVarargs
    public static <T extends Block> DeferredHolder<Block, Block> button(String name, Supplier<T> base, boolean sensitive, BlockSetType blockSetType, TagKey<Block>... values) {
        TABlockProperties properties = TABlockProperties.get().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY).addBlockTag(values).lootType(TALootType.SELF);
        return register(name, () -> new ButtonBlockWithBase(base.get(), properties, blockSetType, sensitive ? 30 : 20));
    }

    public static <T extends Block> DeferredHolder<Block, Block> stair(String name, Supplier<T> block, TABlockProperties properties, boolean... isWooden) {
        TABlockProperties newProperties = properties.addBlockTag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.STAIRS).lootType(TALootType.SELF).isBuildingBlock().useSimpleBlockItem();
        return register(name, () -> new StairBlockWithBase(block.get().defaultBlockState(), isWooden.length > 0 ? newProperties : properties));
    }

    public static <T extends Block> DeferredHolder<Block, Block> fence(String name, Supplier<T> base, TABlockProperties properties) {
        return register(name, () -> new FenceBlockWithBase(base.get(), properties.lootType(TALootType.SELF)));
    }

    public static <T extends Block> DeferredHolder<Block, Block> door(String name, Supplier<T> base, BlockBehaviour.Properties properties, BlockSetType blockSetType) {
        return register(name, () -> new DoorBlockWithBase(base.get(), properties, blockSetType));
    }

    public static <T extends Block> DeferredHolder<Block, Block> slab(String name, Supplier<T> base, TABlockProperties properties, boolean... isWooden) {
        TABlockProperties newProperties = properties.addBlockTag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.SLABS).lootType(TALootType.SELF).isBuildingBlock().useSimpleBlockItem();
        return register(name, () -> new SlabBlockWithBase(base.get(), isWooden.length > 0 ? newProperties : properties));
    }

    public static <T extends Block> DeferredHolder<Block, Block> wall(String name, Supplier<T> base, TABlockProperties properties) {
        return register(name, () -> new WallBlockWithBase(base.get(), properties.addBlockTag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.WALLS).lootType(TALootType.SELF).isBuildingBlock()));
    }

}