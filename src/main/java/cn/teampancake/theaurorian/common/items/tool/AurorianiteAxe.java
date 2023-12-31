package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AurorianiteAxe extends AxeItem implements ITooltipsItem {

    public AurorianiteAxe() {
        super(TAToolTiers.AURORIANITE, 12.0F, -3.5F,new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level,pos) != 0.0F) {
            if (state.is(BlockTags.LOGS)) {
                chopTree(level, pos, stack, (ServerPlayer) entityLiving);
            } else {
                super.mineBlock(stack, level, state, pos, entityLiving);
            }
        }
        return true;
    }

    private void chopTree(Level level, BlockPos pos, ItemStack stack, ServerPlayer serverPlayer) {

        boolean isDone = false;
        int maxLogs = AurorianConfig.Config_AurorianiteAxeMaxChopSize.get();
        int logCount = 0;
        List<BlockPos> notSearchedWood = new ArrayList<>();
        List<BlockPos> searchedWood = new ArrayList<>();

        //Set our first block to be the log we mined
        BlockPos currentPos = pos;

        while (!isDone) {

            //for loops for getting a 3x3 on the same level of the block and 3x3 a level above
            for (int x = -1; x <= 1; x++) {
                for (int y = 0; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos p = currentPos.offset(x, y, z);
                        //If any block we find is wood and is not already searched or in our list, we add it.
                        if (level.getBlockState(p).is(BlockTags.LOGS)){
                            if(stack.is(TAItems.AURORIANITE_AXE.get()) && !level.getBlockState(p).is(TABlocks.SILENT_TREE_LOG.get())) {
                                continue;
                            }
                                if (!notSearchedWood.contains(p) && !searchedWood.contains(p)) {
                                    notSearchedWood.add(p);
                                    logCount++;
                                }
                        }
                    }
                }
            }

            //Move the block we just searched from our unsearched list to a list for later breaking
            searchedWood.add(currentPos);
            notSearchedWood.remove(currentPos);

            //If no more blocks are found we are done, else we set our next block from the first in the list unless we reach the max log count
            if (notSearchedWood.isEmpty() || logCount >= maxLogs) {
                isDone = true;
            } else {
                currentPos = notSearchedWood.get(0);
            }
        }

        for (BlockPos p : searchedWood) {
            level.destroyBlock(p, true);
            stack.hurt(1, level.random ,serverPlayer);
        }

    }

}