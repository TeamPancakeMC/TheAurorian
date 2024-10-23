package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AurorianStonePickaxe extends PickaxeItem {

    public AurorianStonePickaxe() {
        super(TAToolTiers.AURORIAN_STONE, TAItemProperties.get().attributes(createAttributes(TAToolTiers.AURORIAN_STONE, (1), (-2.8F))).addItemTag(ItemTags.PICKAXES, TAItemTags.IS_EPIC));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            if (state.getBlock() == TABlocks.AURORIAN_STONE.get()) {
                if (level.random.nextDouble() <= 0.75D) {
                    stack.hurtAndBreak(1, livingEntity, EquipmentSlot.MAINHAND);
                }
            } else {
                stack.hurtAndBreak(1, livingEntity, EquipmentSlot.MAINHAND);
            }
        }

        return true;
    }

}