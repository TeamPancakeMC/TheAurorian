package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AurorianStoneAxe extends AxeItem {

    public AurorianStoneAxe() {
        super(TAToolTiers.AURORIAN_STONE, TAItemProperties.get()
                .attributes(createAttributes(TAToolTiers.AURORIAN_STONE, (8.0F), (-3.2F)))
                .addItemTag(ItemTags.AXES, TAItemTags.IS_EPIC).hasTooltips());
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