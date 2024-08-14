package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.common.blocks.base.*;
import cn.teampancake.theaurorian.common.blocks.modified.AxeStrippableBlock;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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
        TAItems.ITEMS.register(name, () -> new BlockItem(register.get(), new Item.Properties()));
        return register;
    }

    public static DeferredHolder<Block, Block> normal(String name, BlockBehaviour.Properties properties) {
        return register(name, () -> new Block(properties));
    }

    public static DeferredHolder<Block, Block> ore(String name, IntProvider xpRange, BlockBehaviour.Properties properties) {
        return register(name, () -> new DropExperienceBlock(xpRange, properties.requiresCorrectToolForDrops()));
    }

    public static DeferredHolder<Block, Block> wood(String name, Supplier<Block> block, MapColor mapColor, float strength) {
        return register(name, () -> new AxeStrippableBlock(block, BlockBehaviour.Properties
                .of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS)
                .strength(strength).sound(SoundType.WOOD).ignitedByLava()));
    }

    public static DeferredHolder<Block, Block> wood(String name, MapColor mapColor, float strength) {
        return register(name, () -> new RotatedPillarBlock(BlockBehaviour.Properties
                .of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS)
                .strength(strength).sound(SoundType.WOOD).ignitedByLava()));
    }

    public static DeferredHolder<Block, Block> flowerPot(DeferredHolder<Block, Block> block) {
        return TABlocks.BLOCKS.register("potted_" + block.getId().getPath(), () -> Blocks.flowerPot(block.get()));
    }

    public static <T extends Block> DeferredHolder<Block, Block> verticalStair(String name, Supplier<T> base, BlockBehaviour.Properties properties) {
        return register(name, () -> new VerticalStairBlockWithBase(base.get(), properties));
    }

    public static <T extends Block> DeferredHolder<Block, Block> verticalSlab(String name, Supplier<T> base, BlockBehaviour.Properties properties) {
        return register(name, () -> new VerticalSlabBlockWithBase(base.get(), properties));
    }

    public static <T extends Block> DeferredHolder<Block, Block> pressurePlate(String name, Supplier<T> base, BlockBehaviour.Properties properties, BlockSetType blockSetType) {
        return register(name, () -> new PressurePlateBlockWithBase(base.get(), properties, blockSetType));
    }

    public static <T extends Block> DeferredHolder<Block, Block> fenceGate(String name, Supplier<T> base, BlockBehaviour.Properties properties, WoodType woodType) {
        return register(name, () -> new FenceGateBlockWithBase(base.get(), properties, woodType));
    }

    public static <T extends Block> DeferredHolder<Block, Block> trapdoor(String name, Supplier<T> base, BlockBehaviour.Properties properties, BlockSetType blockSetType) {
        return register(name, () -> new TrapDoorBlockWithBase(base.get(), properties, blockSetType));
    }

    public static <T extends Block> DeferredHolder<Block, Block> button(String name, Supplier<T> base, boolean sensitive, BlockSetType blockSetType) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        return register(name, () -> new ButtonBlockWithBase(base.get(), properties, blockSetType, sensitive ? 30 : 20));
    }

    public static <T extends Block> DeferredHolder<Block, Block> stair(String name, Supplier<T> block, BlockBehaviour.Properties properties) {
        return register(name, () -> new StairBlockWithBase(block.get().defaultBlockState(), properties));
    }

    public static <T extends Block> DeferredHolder<Block, Block> fence(String name, Supplier<T> base, BlockBehaviour.Properties properties) {
        return register(name, () -> new FenceBlockWithBase(base.get(), properties));
    }

    public static <T extends Block> DeferredHolder<Block, Block> door(String name, Supplier<T> base, BlockBehaviour.Properties properties, BlockSetType blockSetType) {
        return register(name, () -> new DoorBlockWithBase(base.get(), properties, blockSetType));
    }

    public static <T extends Block> DeferredHolder<Block, Block> slab(String name, Supplier<T> base, BlockBehaviour.Properties properties) {
        return register(name, () -> new SlabBlockWithBase(base.get(), properties));
    }

    public static <T extends Block> DeferredHolder<Block, Block> wall(String name, Supplier<T> base, BlockBehaviour.Properties properties) {
        return register(name, () -> new WallBlockWithBase(base.get(), properties));
    }

}