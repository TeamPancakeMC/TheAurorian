package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.registry.TABlocks;
import cn.teampancake.theaurorian.utils.AurorianUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class AurorianStonePickaxe extends PickaxeItem {

    public AurorianStonePickaxe() {
        super(TAToolTiers.AURORIAN_STONE, 1, -2.8F, new Properties());
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            Consumer<LivingEntity> onBroken = p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            if (state.getBlock() == TABlocks.AURORIAN_STONE.get()) {
                if (AurorianUtil.randomChanceOf(0.75F)) {
                    stack.hurtAndBreak(1, livingEntity, onBroken);
                }
            } else {
                stack.hurtAndBreak(1, livingEntity, onBroken);
            }
        }
        return true;
    }

}