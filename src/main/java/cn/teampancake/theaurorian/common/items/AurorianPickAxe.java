package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class AurorianPickAxe extends PickaxeItem {

    public AurorianPickAxe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties properties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, properties);
        properties.rarity(Rarity.EPIC);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level,pos) != 0.0F) {
            ItemStack s = new ItemStack(level.getBlockState(pos).getBlock());
            if (s.is(Tags.Items.ORES)) {
                entityLiving.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 100));
                stack.hurt(1, level.random , (ServerPlayer) entityLiving);
                return true;
            }
            stack.hurt(1, level.random , (ServerPlayer) entityLiving);
        }
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn) {
        ModItems.appendTooltip(stack, tooltip);
    }

}
