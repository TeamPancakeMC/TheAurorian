package cn.teampancake.theaurorian.utils;

import cn.teampancake.theaurorian.common.blocks.SlabBlockWithBase;
import cn.teampancake.theaurorian.common.blocks.WallBlockWithBase;
import cn.teampancake.theaurorian.registry.TABlocks;
import cn.teampancake.theaurorian.registry.TAItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class TABlockRegUtils {

    public static RegistryObject<Block> register(String name, Supplier<Block> block) {
        RegistryObject<Block> register = TABlocks.BLOCKS.register(name, block);
        TAItems.ITEMS.register(name, () -> new BlockItem(register.get(), new Item.Properties()));
        return register;
    }

    public static RegistryObject<Block> normal(String name, BlockBehaviour.Properties properties) {
        return register(name, () -> new Block(properties));
    }

    public static RegistryObject<Block> ore(String name, BlockBehaviour.Properties properties) {
        return register(name, () -> new DropExperienceBlock(properties.requiresCorrectToolForDrops()));
    }

    public static RegistryObject<Block> wood(String name, MapColor mapColor, float strength) {
        return register(name, () -> new RotatedPillarBlock(BlockBehaviour.Properties
                .of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS)
                .strength(strength).sound(SoundType.WOOD).ignitedByLava()));
    }

    public static RegistryObject<Block> flowerPot(String name, Supplier<Block> block) {
        return TABlocks.BLOCKS.register("potted_" + name, () -> Blocks.flowerPot(block.get()));
    }

    public static RegistryObject<Block> pressurePlate(String name, BlockBehaviour.Properties properties, BlockSetType blockSetType) {
        return register(name, () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, properties, blockSetType));
    }

    public static RegistryObject<Block> fenceGate(String name, BlockBehaviour.Properties properties, WoodType woodType) {
        return register(name, () -> new FenceGateBlock(properties, woodType));
    }

    public static RegistryObject<Block> trapdoor(String name, BlockBehaviour.Properties properties, BlockSetType blockSetType) {
        return register(name, () -> new TrapDoorBlock(properties, blockSetType));
    }

    public static RegistryObject<Block> button(String name, boolean sensitive, BlockSetType blockSetType) {
        return register(name, () -> sensitive ? Blocks.woodenButton(blockSetType) : Blocks.stoneButton());
    }

    public static <T extends Block> RegistryObject<Block> stair(String name, Supplier<T> block, BlockBehaviour.Properties properties) {
        return register(name, () -> new StairBlock(block.get().defaultBlockState(), properties));
    }

    public static RegistryObject<Block> fence(String name, BlockBehaviour.Properties properties) {
        return register(name, () -> new FenceBlock(properties));
    }

    public static RegistryObject<Block> door(String name, BlockBehaviour.Properties properties, BlockSetType blockSetType) {
        return register(name, () -> new DoorBlock(properties, BlockSetType.register(blockSetType)));
    }

    public static <T extends Block> RegistryObject<Block> slab(String name, Supplier<T> base, BlockBehaviour.Properties properties) {
        return register(name, () -> new SlabBlockWithBase(base.get(), properties));
    }

    public static <T extends Block> RegistryObject<Block> wall(String name, Supplier<T> base, BlockBehaviour.Properties properties) {
        return register(name, () -> new WallBlockWithBase(base.get(), properties));
    }

}