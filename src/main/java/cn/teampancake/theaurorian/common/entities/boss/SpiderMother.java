package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.entities.phase.spidermother.*;
import cn.teampancake.theaurorian.common.entities.projectile.WebbingEntity;
import cn.teampancake.theaurorian.common.registry.TAAttributes;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

//TODO: Method getPassengersRidingOffset was delete, need to find other method
/** @noinspection deprecation*/
public class SpiderMother extends AbstractAurorianBoss implements GeoEntity {

    private static final RawAnimation HATCH_BEGIN = RawAnimation.begin().thenPlay("misc.hatch_begin");
    private static final RawAnimation HATCH_HOLD = RawAnimation.begin().thenLoop("misc.hatch_hold");
    private static final RawAnimation HATCH_END = RawAnimation.begin().thenPlay("misc.hatch_end");
    private static final RawAnimation EIDOLON = RawAnimation.begin().thenPlay("attack.eidolon");
    private static final RawAnimation SLASH = RawAnimation.begin().thenPlay("attack.slash");
    private static final RawAnimation SMASH = RawAnimation.begin().thenPlay("attack.smash");
    private static final RawAnimation SPIT = RawAnimation.begin().thenPlay("attack.spit");
    private static final RawAnimation DEATH = RawAnimation.begin().thenPlay("misc.death");
    private static final EntityDataAccessor<Boolean> HANGING = SynchedEntityData.defineId(SpiderMother.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(SpiderMother.class, EntityDataSerializers.BYTE);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final HashSet<String> currentSavedUUID = new HashSet<>();
    private final HashSet<String> alreadyHealForUUID = new HashSet<>();
    private boolean canBeInvisible = true;
    private int safeTime;

    public SpiderMother(EntityType<? extends SpiderMother> type, Level level) {
        super(type, level);
        this.xpReward = 500;
        this.attackManager = new AttackManager<>(this, List.of(
                new SpiderMotherSlashPhase(), new SpiderMotherSmashPhase(),
                new SpiderMotherSpitPhase(), new SpiderMotherEidolonPhase(),
                new SpiderMotherHatchBeginPhase(),
                new SpiderMotherHatchHoldPhase(),
                new SpiderMotherHatchEndPhase()));
    }

    @Nullable @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        for (int i = 0; i < 4; i++) {
            if (this.getRandomSpiderlings() instanceof Mob mob) {
                mob.setPos(this.position());
                mob.setTarget(this.getTarget());
                level.addFreshEntity(mob);
            }
        }

        return spawnData;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new HangGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.FALSE));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, Boolean.FALSE,
                entity -> !(entity instanceof SpiderMother) && !entity.getType().is(TAEntityTags.SPIDERLING)));
    }

    @NotNull
    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(TAAttributes.MAX_BOSS_HEALTH, 400.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder.add(Attributes.FOLLOW_RANGE, 50.0F);
        builder.add(Attributes.ARMOR, 8.0F);
        return builder;
    }

    @Nullable
    public Entity getRandomSpiderlings() {
        List<Holder<EntityType<?>>> list = new ArrayList<>();
        BuiltInRegistries.ENTITY_TYPE.getTagOrEmpty(TAEntityTags.SPIDERLING).forEach(list::add);
        return list.get(this.level().random.nextInt(list.size())).value().create(this.level());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HANGING, Boolean.FALSE);
        builder.define(DATA_FLAGS_ID, (byte)0);
    }

    private AnimationController<?> createControllers(String name, RawAnimation animation) {
        return new AnimationController<>(this, name + "_controller", state -> PlayState.STOP)
                .triggerableAnim(name + "_animation", animation).transitionLength(5);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));
        controllers.add(this.createControllers("hatch_begin", HATCH_BEGIN));
        controllers.add(this.createControllers("hatch_hold", HATCH_HOLD));
        controllers.add(this.createControllers("hatch_end", HATCH_END));
        controllers.add(this.createControllers("eidolon", EIDOLON));
        controllers.add(this.createControllers("slash", SLASH));
        controllers.add(this.createControllers("smash", SMASH));
        controllers.add(this.createControllers("spit", SPIT));
        controllers.add(this.createControllers("death", DEATH));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private ListTag saveListTag(HashSet<String> list) {
        ListTag listTag = new ListTag();
        list.forEach(s -> {
            CompoundTag compound = new CompoundTag();
            compound.putString("UUID", s);
            listTag.add(compound);
        });

        return listTag;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.level() instanceof ServerLevel serverLevel) {
            float maxHealth = this.getMaxHealth();
            List<ServerPlayer> serverPlayerList = serverLevel.players();
            AttributeInstance health = this.getAttribute(TAAttributes.MAX_BOSS_HEALTH);
            serverPlayerList.forEach(player -> this.currentSavedUUID.add(player.getStringUUID()));
            int size = this.currentSavedUUID.size() - this.alreadyHealForUUID.size() - 1;
            if (this.getHealth() < maxHealth * 0.25F && this.canBeInvisible) {
                MobEffectInstance instance = new MobEffectInstance(MobEffects.INVISIBILITY, 400);
                this.addEffect(instance);
                this.canBeInvisible = false;
            }

            if (health != null && size > 0) {
                float extraValue = size * 100.0F;
                health.setBaseValue(maxHealth + extraValue);
                if (this.lastHurtByPlayer == null) {
                    this.setBossHealth((float) health.getBaseValue());
                } else {
                    this.heal(extraValue);
                }
            }

            if (++this.safeTime > 160 && this.tickCount % 20 == 0) {
                this.heal(maxHealth * 0.05F);
            }

            this.alreadyHealForUUID.addAll(this.currentSavedUUID);
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        if (damageSource.getEntity() instanceof ServerPlayer player) {
            AttributeInstance instance = player.getAttribute(Attributes.MAX_HEALTH);
            if (instance != null && instance.getModifier(WebbingEntity.WEBBING_MODIFIER) != null) {
                instance.removeModifier(WebbingEntity.WEBBING_MODIFIER);
            }
        }

        super.die(damageSource);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level);
    }

    public boolean isHanging() {
        return this.getEntityData().get(HANGING);
    }

    public void setClimbing(boolean climbing) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (climbing) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (!this.level().isClientSide) {
            if (this.deathTime == 1) {
                this.triggerAnim("death_controller", "death_animation");
            }

            if (this.deathTime > 20 && !this.isRemoved()) {
                this.level().broadcastEntityEvent(this, (byte) 60);
                this.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SafeTime", this.safeTime);
        compound.putBoolean("CanBeInvisible", this.canBeInvisible);
        compound.put("CurrentSavedUUID", this.saveListTag(this.currentSavedUUID));
        compound.put("AlreadyHealForUUID", this.saveListTag(this.alreadyHealForUUID));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.safeTime = compound.getInt("SafeTime");
        this.canBeInvisible = compound.getBoolean("CanBeInvisible");
        ListTag listTagC = compound.getList("CurrentSavedUUID", 10);
        for (int i = 0; i < listTagC.size(); i++) {
            this.currentSavedUUID.add(listTagC.getCompound(i).getString("UUID"));
        }

        ListTag listTagT = compound.getList("AlreadyHealForUUID", 10);
        for (int i = 0; i < listTagT.size(); i++) {
            this.alreadyHealForUUID.add(listTagT.getCompound(i).getString("UUID"));
        }
    }

    @Override
    public boolean onClimbable() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    @Override
    public void makeStuckInBlock(BlockState state, Vec3 motionMultiplier) {
        if (!state.is(Blocks.COBWEB)) {
            super.makeStuckInBlock(state, motionMultiplier);
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance potionEffect) {
        MobEffect effect = potionEffect.getEffect().value();
        boolean flag1 = effect == MobEffects.POISON;
        boolean flag2 = effect == MobEffects.WITHER;
        boolean flag3 = effect == TAMobEffects.CRYSTALLIZATION.get();
        return flag1 || flag2 || flag3 || super.canBeAffected(potionEffect);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (super.hurt(source, amount)) {
            this.safeTime = 0;
            return true;
        } else {
            return false;
        }
    }

    private static class HangGoal extends Goal {

        private final Mob mob;
        private int hangCooldown = 0;
        private int hangTime = 0;
        private int hangingX;
        private int hangingY;
        private int hangingZ;

        public HangGoal(Mob mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            if (target != null) {
                if (this.hangCooldown == 0) {
                    this.hangingX = this.mob.getBlockX();
                    this.hangingY = this.mob.getBlockY();
                    this.hangingZ = this.mob.getBlockZ();
                    for (int y = 8; y <= 50; y++) {
                        BlockPos pos = new BlockPos(this.hangingX, this.hangingY, this.hangingZ);
                        if (this.mob.level().loadedAndEntityCanStandOn(pos.above(y), this.mob)) {
                            this.hangingY = this.hangingY + y - 3;
                            return true;
                        }
                    }
                } else if (this.hangCooldown > 0) {
                    this.hangCooldown--;
                }
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            if (this.hangTime == 0) {
                this.mob.getEntityData().set(HANGING, false);
                this.mob.setNoGravity(false);
                this.hangCooldown = 200;
                return false;
            } else {
                return true;
            }
        }

        @Override
        public boolean isInterruptable() {
            return false;
        }

        @Override
        public void start() {
            LivingEntity target = this.mob.getTarget();
            this.mob.getNavigation().stop();
            this.mob.getEntityData().set(HANGING, true);
            this.mob.setNoGravity(true);
            this.hangTime = 200;
            if (target != null) {
                this.mob.lookAt(target, this.mob.getHeadRotSpeed() * 2, this.mob.getMaxHeadXRot() * 2);
            }
        }

        @Override
        public void tick() {
            if (this.mob.getY() <= this.hangingY) {
                this.mob.setPos(hangingX, this.mob.position().y + 0.5D, hangingZ);
            }

            this.hangTime--;
        }

    }

}