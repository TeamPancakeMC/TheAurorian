package cn.teampancake.theaurorian.common.items.tool.aurorianite;

import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import cn.teampancake.theaurorian.common.registry.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AurorianiteAxe extends AxeItem {

    public AurorianiteAxe(Item.Properties properties) {
        super(TAToolTiers.AURORIANITE, properties.rarity(Rarity.EPIC)
                .attributes(createAttributes(TAToolTiers.AURORIANITE, 12.0F, -3.5F))
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC));
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && state.is(BlockTags.LOGS)) {
            String toolUUID = UUID.randomUUID().toString();
        }

        return true;
    }

}