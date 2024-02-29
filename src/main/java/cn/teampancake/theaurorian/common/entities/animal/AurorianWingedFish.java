package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AurorianWingedFish extends AbstractAurorianFish {

    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState crashAnimationState = new AnimationState();
    public final AnimationState crash2AnimationState = new AnimationState();

    public AurorianWingedFish(EntityType<? extends AurorianWingedFish> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.swimAnimationState.animateWhen(this.walkAnimation.isMoving(), this.tickCount);
        }
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return TAItems.AURORIAN_WINGED_FISH_BUCKET.get().getDefaultInstance();
    }

}