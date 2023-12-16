package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.registry.TAEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.Fluids;

public class MoonFishBucket extends MobBucketItem {

    public MoonFishBucket() {
        super(TAEntityTypes.MOON_FISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1));
    }

    public static class Dispense extends DefaultDispenseItemBehavior {

        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        public ItemStack execute(BlockSource source, ItemStack stack) {
            DispensibleContainerItem containerItem = (DispensibleContainerItem)stack.getItem();
            BlockPos blockPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            Level level = source.getLevel();
            if (containerItem.emptyContents(null, level, blockPos, null, stack)) {
                containerItem.checkExtraContent(null, level, stack, blockPos);
                return new ItemStack(Items.BUCKET);
            } else {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }
        }

    }

}