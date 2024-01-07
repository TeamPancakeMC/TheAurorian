package cn.teampancake.theaurorian.common.data.datagen.tags;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TABlockTags {

    public static final TagKey<Block> VERTICAL_STAIRS = create("vertical_stairs");
    public static final TagKey<Block> VERTICAL_SLABS = create("vertical_slabs");
    public static final TagKey<Block> DUNGEON_BRICKS = create("dungeon_bricks");
    public static final TagKey<Block> SILENT_TREE_LOGS = create("silent_tree_logs");
    public static final TagKey<Block> WEEPING_WILLOW_LOGS = create("weeping_willow_logs");
    public static final TagKey<Block> AURORIAN_GRASS_BLOCK = create("aurorian_grass_block");
    public static final TagKey<Block> AUROTIAN_ANIMAL_UNSPAWNABLE_ON = create("aurotian_animal_unspawnable_on");

    private static TagKey<Block> create(String name) {
        return BlockTags.create(AurorianMod.prefix(name));
    }

}