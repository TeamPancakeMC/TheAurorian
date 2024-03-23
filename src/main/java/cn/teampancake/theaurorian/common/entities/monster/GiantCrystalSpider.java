package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.entities.phase.GiantCrystalSpiderFallPhase;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class GiantCrystalSpider extends TASpider {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public GiantCrystalSpider(EntityType<? extends GiantCrystalSpider> type, Level level) {
        super(type, level);
        this.xpReward = 20;
        this.attackManager = new AttackManager<>(this,
                List.of(new GiantCrystalSpiderFallPhase()));
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.FALSE));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, Boolean.FALSE));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder.add(Attributes.MAX_HEALTH, 60.0D);
        return builder;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));
        controllers.add(new AnimationController<>(this, "fall_controller", state -> PlayState.STOP)
                .triggerableAnim("fall_animation", RawAnimation.begin().thenPlay("attack.fall")).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}