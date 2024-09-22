package cn.teampancake.theaurorian.common.data.datagen.tags;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TABlockTags {

    public static final TagKey<Block> VERTICAL_STAIRS = create("vertical_stairs");
    public static final TagKey<Block> VERTICAL_SLABS = create("vertical_slabs");
    public static final TagKey<Block> DUNGEON_BLOCKS = create("dungeon_blocks");
    public static final TagKey<Block> MOON_TEMPLE_BLOCKS = create("moon_temple_blocks");
    public static final TagKey<Block> RUNE_STONE_BLOCK = create("rune_stone_block");
    public static final TagKey<Block> CAN_HURT_SICKLE = create("can_hurt_sickle");
    public static final TagKey<Block> SILENT_TREE_LOGS = create("silent_tree_logs");
    public static final TagKey<Block> WEEPING_WILLOW_LOGS = create("weeping_willow_logs");
    public static final TagKey<Block> CURTAIN_TREE_LOGS = create("curtain_tree_logs");
    public static final TagKey<Block> CURSED_FROST_TREE_LOGS = create("cursed_frost_tree_logs");
    public static final TagKey<Block> AURORIAN_PLANKS = create("aurorian_planks");
    public static final TagKey<Block> AURORIAN_GRASS_BLOCK = create("aurorian_grass_block");
    public static final TagKey<Block> AURORIAN_SAND_BLOCK = create("aurorian_sand_block");
    public static final TagKey<Block> AURORIAN_CARVER_REPLACEABLES = create("aurorian_carver_replaceables");
    public static final TagKey<Block> AUROTIAN_ANIMAL_UNSPAWNABLE_ON = create("aurotian_animal_unspawnable_on");
    public static final TagKey<Block> AURORIAN_LIGHT_PLANT_MAY_PLACE_ON = create("aurorian_light_plant_may_place_on");

    private static TagKey<Block> create(String name) {
        return BlockTags.create(TheAurorian.prefix(name));
    }

}