package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModItems;
import cn.teampancake.theaurorian.utils.AurorianUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class AurorianStonePickaxe extends PickaxeItem {

    public AurorianStonePickaxe() {
        super(ModToolTiers.AURORIAN_STONE, 1, -2.8F, new Properties());
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            Consumer<LivingEntity> onBroken = p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            if (state.getBlock() == ModBlocks.AURORIAN_STONE.get()) {
                if (AurorianUtil.randomChanceOf(0.75F)) {
                    stack.hurtAndBreak(1, livingEntity, onBroken);
                }
            } else {
                stack.hurtAndBreak(1, livingEntity, onBroken);
            }
        }
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flag) {
        ModItems.appendTooltip(stack, tooltip);
    }

}