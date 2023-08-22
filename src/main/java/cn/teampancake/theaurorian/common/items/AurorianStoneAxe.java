package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class AurorianStoneAxe extends AxeItem {
    public AurorianStoneAxe(Tier pTier, Properties pProperties) {
        //TODO ItemRegistry.Materials.AURORIANSTONE
        super(pTier, 8.0F, -3.2F, pProperties);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        super.mineBlock(stack, level, state, pos, entityLiving);
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0D) {
            int y = 1;
            while (level.getBlockState(new BlockPos(pos.getX(), pos.getY() + y, pos.getZ())) == ModBlocks.SILENT_TREE_LOG.get().defaultBlockState() && stack.getDamageValue() <= (stack.getMaxDamage() - 3)) {
                level.destroyBlock(new BlockPos(pos.getX(), pos.getY() + y, pos.getZ()), true);
                stack.hurt(3,level.random,(ServerPlayer)entityLiving);
                y++;
            }
        }
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn) {
        ModItems.appendTooltip(stack, tooltip);
    }
}
