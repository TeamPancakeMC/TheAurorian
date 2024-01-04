package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MoonFish extends AbstractAurorianFish {

    public MoonFish(EntityType<? extends MoonFish> type, Level level) {
        super(type, level);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return TAItems.MOON_FISH_BUCKET.get().getDefaultInstance();
    }

}