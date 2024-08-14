package cn.teampancake.theaurorian.common.blocks.modified;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AxeStrippableBlock extends RotatedPillarBlock {

    private final Supplier<Block> block;

    public AxeStrippableBlock(Supplier<Block> strippedBlock, Properties properties) {
        super(properties);
        this.block = strippedBlock;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility toolAction, boolean simulate) {
        return toolAction == ItemAbilities.AXE_STRIP ? transferAllBlockStates(state, this.block.get().defaultBlockState()) : null;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static BlockState transferAllBlockStates(BlockState initial, BlockState after) {
        BlockState block = after;
        for (Property property : initial.getBlock().getStateDefinition().getProperties()) {
            if (after.hasProperty(property)) {
                block = block.setValue(property, initial.getValue(property));
            }
        }

        return block;
    }

}