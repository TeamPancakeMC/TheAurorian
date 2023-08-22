package cn.teampancake.theaurorian.common.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IndigoMushroom extends HalfTransparentBlock {

    public IndigoMushroom(Properties properties) {
        super(properties);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.fallOn(level, state, pos, entity, fallDistance * 0.25F);
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter level, Entity entity) {
        if (entity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(level, entity);
        } else {
            Vec3 vec3 = entity.getDeltaMovement();
            if (vec3.y < 0.0D) {
                double d0 = entity instanceof LivingEntity ? 0.75D : 0.8D;
                entity.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("string.theaurorian.tooltip.shiftinfo").withStyle(ChatFormatting.ITALIC));
        } else {
            tooltip.add(Component.translatable("string.theaurorian.tooltip.mushroom"));
        }
    }

}