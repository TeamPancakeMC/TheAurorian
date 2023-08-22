package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.util.AurorianSteelHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class AurorianSteelSword extends SwordItem {
    public AurorianSteelSword(Tier tier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        //TODO ItemRegistry.Materials.AURORIANSTEEL
        super(tier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity pTarget, LivingEntity pAttacker) {
        super.hurtEnemy(stack, pTarget, pAttacker);
        AurorianSteelHelper.handleAurorianSteelDurability(stack);
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn) {
        AurorianSteelHelper.getAurorianSteelInfo(stack, levelIn, tooltip);
    }
}
