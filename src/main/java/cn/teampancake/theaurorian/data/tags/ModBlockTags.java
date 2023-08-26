package cn.teampancake.theaurorian.data.tags;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    public static final TagKey<Block> DUNGEON_BRICKS = create("dungeon_bricks");

    private static TagKey<Block> create(String name) {
        return BlockTags.create(AurorianMod.prefix(name));
    }

}