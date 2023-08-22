package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.config.AurorianConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class AurorianFurnaceChimney extends Block {

    public AurorianFurnaceChimney(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return box(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isEmptyBlock(pos.above())) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + 0.5D + (random.nextDouble() * 6.0D / 16.0D);
            double d2 = (double) pos.getZ() + 0.5D;
            level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("string.theaurorian.tooltip.shiftinfo").withStyle(ChatFormatting.ITALIC));
        } else {
            int maximumChimneys = AurorianConfig.CONFIG_MAXIMUM_CHIMNEYS.get();
            tooltip.add(Component.translatable("string.theaurorian.tooltip.furnacechimney", maximumChimneys));
        }
    }

}
