package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AurorianStoneAxe extends AxeItem implements ITooltipsItem {

    public AurorianStoneAxe() {
        super(TAToolTiers.AURORIAN_STONE, 8.0F, -3.2F, new Properties());
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0D) {
            int y = 1;
            while (level.getBlockState(pos.above(y)) == TABlocks.SILENT_TREE_LOG.get().defaultBlockState()
                    && stack.getDamageValue() <= (stack.getMaxDamage() - 3)) {
                level.destroyBlock(new BlockPos(pos.getX(), pos.getY() + y, pos.getZ()), true);
                stack.hurtAndBreak(3, entityLiving, p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                y++;
            }
        }
        return true;
    }

}
