package cn.teampancake.theaurorian.common.entities.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RuneSpider extends TASpider {

    private static final RawAnimation CRYSTAL_BEGIN = RawAnimation.begin().thenPlay("attack.crystal_begin");
    private static final RawAnimation CRYSTAL_HOLD = RawAnimation.begin().thenLoop("attack.crystal_hold");
    private static final RawAnimation CRYSTAL_END = RawAnimation.begin().thenPlay("attack.crystal_end");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public RuneSpider(EntityType<? extends RuneSpider> type, Level level) {
        super(type, level);
        this.xpReward = 50;
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MOVEMENT_SPEED, 0.25D);
        builder.add(Attributes.MAX_HEALTH, 50.0D);
        builder.add(Attributes.ARMOR, 10.0D);
        return builder;
    }

    private AnimationController<?> createControllers(String name, RawAnimation animation) {
        return new AnimationController<>(this, name + "_controller", state -> PlayState.STOP)
                .triggerableAnim(name + "_animation", animation).transitionLength(5);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));
        controllers.add(this.createControllers("crystal_begin", CRYSTAL_BEGIN));
        controllers.add(this.createControllers("crystal_hold", CRYSTAL_HOLD));
        controllers.add(this.createControllers("crystal_end", CRYSTAL_END));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}