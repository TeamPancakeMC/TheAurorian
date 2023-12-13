package cn.teampancake.theaurorian.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MoonstoneSickle extends ShearsItem {
    
    public MoonstoneSickle() {
        super(new Properties().durability(250));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
        if (!level.isClientSide && !state.is(BlockTags.FIRE)) {
            if (level.isNight() && level.random.nextDouble() <= 0.5D) {
                stack.setDamageValue(stack.getDamageValue() - 1);
            } else {
                stack.hurtAndBreak(2, livingEntity, (player) -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        return state.is(BlockTags.LEAVES) || state.is(Blocks.COBWEB) || state.is(Blocks.GRASS) ||
                state.is(Blocks.FERN) || state.is(Blocks.DEAD_BUSH) || state.is(Blocks.HANGING_ROOTS) ||
                state.is(Blocks.VINE) || state.is(Blocks.TRIPWIRE) || state.is(BlockTags.WOOL) ||
                super.mineBlock(stack, level, state, pos, livingEntity);
    }
    
}