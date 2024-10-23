package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MoonstoneSickle extends ShearsItem {
    
    public MoonstoneSickle() {
        super(TAItemProperties.get().durability(250).addItemTag(ItemTags.DURABILITY_ENCHANTABLE, ItemTags.MINING_ENCHANTABLE, TAItemTags.IS_EPIC).hasTooltips().isSimpleModelItem());
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
        if (!level.isClientSide && !state.is(BlockTags.FIRE)) {
            if (level.isNight() && level.random.nextDouble() <= 0.5D) {
                stack.setDamageValue(stack.getDamageValue() - 1);
            } else {
                stack.hurtAndBreak(2, livingEntity, EquipmentSlot.MAINHAND);
            }
        }

        return state.is(TABlockTags.CAN_HURT_SICKLE) || super.mineBlock(stack, level, state, pos, livingEntity);
    }
    
}