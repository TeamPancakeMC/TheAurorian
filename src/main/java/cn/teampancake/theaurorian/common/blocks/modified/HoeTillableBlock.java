package cn.teampancake.theaurorian.common.blocks.modified;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class HoeTillableBlock extends Block {

    private final Supplier<Block> block;

    public HoeTillableBlock(Supplier<Block> tilledBlock, Properties properties) {
        super(properties);
        this.block = tilledBlock;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility toolAction, boolean simulate) {
        return toolAction == ItemAbilities.HOE_TILL ? this.block.get().defaultBlockState() : null;
    }

}