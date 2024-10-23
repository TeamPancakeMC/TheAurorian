package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.entity.SilentWoodChestBlockEntity;
import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import cn.teampancake.theaurorian.common.blocks.state.TALootType;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

public class SilentWoodChest extends ChestBlock {

    public SilentWoodChest() {
        super(TABlockProperties.ofFullCopy(Blocks.CHEST).addBlockTag(Tags.Blocks.CHESTS_WOODEN, BlockTags.MINEABLE_WITH_AXE).lootType(TALootType.SELF), TABlockEntityTypes.SILENT_WOOD_CHEST::get);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SilentWoodChestBlockEntity(pos, state);
    }

}