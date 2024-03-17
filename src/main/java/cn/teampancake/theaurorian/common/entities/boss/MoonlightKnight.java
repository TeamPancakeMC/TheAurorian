package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.common.entities.ai.goal.MeleeNoAttackGoal;
import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.entities.phase.MoonlightKnightBeastMeleePhase;
import cn.teampancake.theaurorian.common.entities.phase.MoonlightKnightNormalMeleePhase;
import cn.teampancake.theaurorian.common.registry.TAAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class MoonlightKnight extends AbstractAurorianBoss implements GeoEntity {

    private static final RawAnimation SWING_STING = RawAnimation.begin().thenPlay("attack.swing_sting");
    private static final RawAnimation SWING_SMASH = RawAnimation.begin().thenPlay("attack.swing_stomp");
    private static final RawAnimation FORM_CHANGE = RawAnimation.begin().thenPlay("misc.form_change");
    private static final RawAnimation CRYSTAL_SPELL = RawAnimation.begin().thenPlay("attack.crystal_spell");
    private static final RawAnimation IDLE_BEAST = RawAnimation.begin().thenLoop("misc.idle_beast");
    private static final RawAnimation WALK_BEAST = RawAnimation.begin().thenLoop("misc.walk_beast");
    private static final RawAnimation BURST_BEAST = RawAnimation.begin().thenPlay("attack.burst_beast");
    private static final RawAnimation SWING_BEAST = RawAnimation.begin().thenPlay("attack.swing_beast");
    private static final RawAnimation CLAW = RawAnimation.begin().thenPlay("attack.claw");
    private static final RawAnimation DEATH = RawAnimation.begin().thenPlay("misc.death");
    private static final EntityDataAccessor<Boolean> CRYSTAL = SynchedEntityData.defineId(MoonlightKnight.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MoonlightKnight(EntityType<? extends MoonlightKnight> type, Level level) {
        super(type, level);
        this.xpReward = 400;
        this.attackManager = new AttackManager<>(this, List.of(
                new MoonlightKnightNormalMeleePhase(),
                new MoonlightKnightBeastMeleePhase()));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new MeleeNoAttackGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, (1.0D)));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, (1.0D)));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, (8.0F)));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.FALSE));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(TAAttributes.MAX_BOSS_HEALTH.get(), 600.0D);
        builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.75D);
        builder.add(Attributes.ARMOR_TOUGHNESS, 12.0D);
        builder.add(Attributes.ATTACK_KNOCKBACK, 0.5D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder.add(Attributes.FOLLOW_RANGE, 40.0F);
        builder.add(Attributes.ATTACK_DAMAGE, 5.0D);
        builder.add(Attributes.ARMOR, 10.0F);
        return builder;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CRYSTAL, Boolean.FALSE);
    }

    private AnimationController<?> createControllers(String name, RawAnimation animation) {
        return new AnimationController<>(this, name + "_controller", state -> PlayState.STOP)
                .triggerableAnim(name + "_animation", animation).transitionLength(5);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(this.createControllers("swing", DefaultAnimations.ATTACK_SWING));
        controllers.add(this.createControllers("stomp", DefaultAnimations.ATTACK_STOMP));
        controllers.add(this.createControllers("swing_sting", SWING_STING));
        controllers.add(this.createControllers("swing_stomp", SWING_SMASH));
        controllers.add(this.createControllers("form_change", FORM_CHANGE));
        controllers.add(this.createControllers("crystal_spell", CRYSTAL_SPELL));
        controllers.add(this.createControllers("burst_beast", BURST_BEAST));
        controllers.add(this.createControllers("swing_beast", SWING_BEAST));
        controllers.add(this.createControllers("claw", CLAW));
        controllers.add(this.createControllers("death", DEATH));
        controllers.add(new AnimationController<>(this, "Walk/Idle", 0, state -> {
            RawAnimation normal = state.isMoving() ? DefaultAnimations.WALK : DefaultAnimations.IDLE;
            RawAnimation beast = state.isMoving() ? WALK_BEAST : IDLE_BEAST;
            return state.setAndContinue(!this.isCrystal() ? normal : beast);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public boolean isCrystal() {
        return this.entityData.get(CRYSTAL);
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (!this.level().isClientSide) {
            if (this.isCrystal() && this.deathTime == 1) {
                this.triggerAnim(("death_controller"), ("death_animation"));
            }

            if (this.deathTime > 80 && !this.isRemoved()) {
                this.level().broadcastEntityEvent(this, (byte) 60);
                this.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Crystal", this.isCrystal());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(CRYSTAL, compound.getBoolean("Crystal"));
    }

}