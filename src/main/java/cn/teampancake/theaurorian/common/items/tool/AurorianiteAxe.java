package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AurorianiteAxe extends AxeItem implements ITooltipsItem {

    public AurorianiteAxe() {
        super(TAToolTiers.AURORIANITE, new Properties().rarity(Rarity.EPIC).attributes(createAttributes(TAToolTiers.AURORIANITE, (12.0F), (-3.5F))));
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && state.is(BlockTags.LOGS)) {
            String toolUUID = UUID.randomUUID().toString();
        }

        return true;
    }

}