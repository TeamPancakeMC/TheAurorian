package cn.teampancake.theaurorian.common.entities.animal;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AurorianWingedFish extends AbstractAurorianFish {

    public final AnimationState crashAnimationState = new AnimationState();

    public AurorianWingedFish(EntityType<? extends AurorianWingedFish> type, Level level) {
        super(type, level);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }

}