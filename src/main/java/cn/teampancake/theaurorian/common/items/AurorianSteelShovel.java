package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.util.AurorianSteelHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class AurorianSteelShovel extends ShovelItem {
    public AurorianSteelShovel(float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(ItemRegistry.Materials.AURORIANSTEEL, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0D) {
            stack.hurt(1,level.random, (ServerPlayer) entityLiving);
            AurorianSteelHelper.handleAurorianSteelDurability(stack);
        }
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn) {
        AurorianSteelHelper.getAurorianSteelInfo(stack, levelIn, tooltip);
    }
}
