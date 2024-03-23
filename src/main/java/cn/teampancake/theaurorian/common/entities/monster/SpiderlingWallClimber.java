package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.entities.projectile.WebbingEntity;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SpiderlingWallClimber extends TASpider implements RangedAttackMob {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SpiderlingWallClimber(EntityType<? extends SpiderlingWallClimber> type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, (1.25D), (20), (10.0F)));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.FALSE));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, Boolean.FALSE));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MOVEMENT_SPEED, 0.1D);
        builder.add(Attributes.MAX_HEALTH, 30.0D);
        return builder;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        WebbingEntity webbing = new WebbingEntity(this, this.level());
        webbing.setItem(TAItems.WEBBING.get().getDefaultInstance());
        webbing.setNoGravity(true);
        double d0 = target.getX() - this.getX();
        double d1 = target.getBoundingBox().minY +
                target.getBbHeight() / 3.0F - webbing.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
        float pitch = 0.8F / (this.getRandom().nextFloat() * 0.4F + 0.8F);
        webbing.shoot(d0, d1 + d3 * 0.1D, d2, 1F, 0F);
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.CAT_HISS, SoundSource.HOSTILE, 0.8F, pitch);
        this.level().addFreshEntity(webbing);
    }
    
}