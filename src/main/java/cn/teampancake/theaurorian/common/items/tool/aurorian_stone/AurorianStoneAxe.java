package cn.teampancake.theaurorian.common.items.tool.aurorian_stone;

import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import cn.teampancake.theaurorian.common.registry.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AurorianStoneAxe extends AxeItem {

    public AurorianStoneAxe(Item.Properties properties) {
        super(TAToolTiers.AURORIAN_STONE, properties
                .attributes(createAttributes(TAToolTiers.AURORIAN_STONE, 8.0F, -3.2F))
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC)
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0D) {
            int y = 1;
            while (level.getBlockState(pos.above(y)) == TABlocks.SILENT_TREE_LOG.get().defaultBlockState()
                    && stack.getDamageValue() <= (stack.getMaxDamage() - 3)) {
                level.destroyBlock(new BlockPos(pos.getX(), pos.getY() + y, pos.getZ()), true);
                stack.hurtAndBreak(3, entityLiving, EquipmentSlot.MAINHAND);
                y++;
            }
        }

        return true;
    }

}