package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.config.AurorianConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public class AurorianAxe extends AxeItem {
    public AurorianAxe(Tier pTier, Properties properties) {

        //TODO ItemRegistry.Materials.AURORIANITE
        super(pTier, 12.0F, -3.5F, properties);
        properties.rarity(Rarity.EPIC);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
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

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn) {
        ModItems.appendTooltip(stack, tooltip);
    }
}
