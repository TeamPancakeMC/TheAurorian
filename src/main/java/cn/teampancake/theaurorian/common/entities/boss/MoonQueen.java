package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.entities.ai.goal.MeleeNoAttackGoal;
import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.entities.phase.moonqueen.*;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TAAttributes;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.registry.TAParticleTypes;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.doubles.DoubleDoubleImmutablePair;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/** @noinspection deprecation*/
public class MoonQueen extends AbstractAurorianBoss implements GeoEntity {

    private static final RawAnimation DEFEAT = RawAnimation.begin().thenPlay("misc.defeat");
    private static final RawAnimation DEFEAT_LOOP = RawAnimation.begin().thenPlay("misc.defeat_loop");
    private static final RawAnimation ATTACK_SWING_2 = RawAnimation.begin().thenPlay("attack.swing_2");
    private static final RawAnimation ATTACK_BURST = RawAnimation.begin().thenPlay("attack.burst");
    private static final RawAnimation ATTACK_BUFF = RawAnimation.begin().thenPlay("attack.buff");
    private static final RawAnimation ATTACK_DUEL = RawAnimation.begin().thenPlay("attack.duel");
    private static final RawAnimation ATTACK_BLADE_WAVE = RawAnimation.begin().thenPlay("attack.blade_wave");
    private static final RawAnimation ATTACK_MOON_BEFALL = RawAnimation.begin().thenPlay("attack.moon_befall");
    private static final ResourceLocation SPEED_MODIFIER_FOUND_TARGET = TheAurorian.prefix("found_target");
    private static final EntityDataAccessor<Float> ATTACK_Y_ROT = SynchedEntityData.defineId(MoonQueen.class, EntityDataSerializers.FLOAT);
    public static final ImmutableList<MobEffectInstance> BUFF_LIST = ImmutableList.of(
            new MobEffectInstance(TAMobEffects.CRESCENT, 200),
            new MobEffectInstance(TAMobEffects.BLESS_OF_MOON, 200),
            new MobEffectInstance(TAMobEffects.MOON_OF_VENGEANCE, 200));
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public final HashSet<String> killedDuelistName = new HashSet<>();
    private final HashSet<String> currentSavedName = new HashSet<>();
    private final HashSet<String> playerAlreadyHealFor = new HashSet<>();
    private long ticksCanOneHitMustKill = 24000L;
    private int ticksDueling = 2400;
    private int triggerDuelingCount;
    public int preparationTime;
    public int safeTime;
    public int fqmPySwordNum;
    public boolean isNeutral;
    public boolean duelingMoment;
    private String currentDuelistName = "";

    public MoonQueen(EntityType<? extends MoonQueen> type, Level level) {
        super(type, level);
        this.xpReward = 500;
        this.attackManager = new AttackManager<>(this, List.of(
                new MoonQueenMeleePhase(), new MoonQueenBlockPhase(), new MoonQueenRangedPhase(),
                new MoonQueenBackAttackPhase(), new MoonQueenMoonBefallPhase(),
                new MoonQueenFirstQuarterMoonWithRainOfSwords()));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new MeleeNoAttackGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, (0.6D)));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, (1.0D)));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, (8.0F)));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, (0.8D)));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new MoonQueenNearestAttackableTargetGoal<>(this, Player.class, Boolean.FALSE));
        this.targetSelector.addGoal(3, new MoonQueenNearestAttackableTargetGoal<>(this, LivingEntity.class, Boolean.FALSE, entity -> {
            boolean flag = !BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getNamespace().equals(TheAurorian.MOD_ID);
            return !(entity instanceof MoonQueen) && !(entity instanceof MoonlightKnight) && flag && entity.attackable();
        }));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(TAAttributes.MAX_BOSS_HEALTH, 500.0D);
        builder.add(Attributes.WATER_MOVEMENT_EFFICIENCY, 1.0D);
        builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.85D);
        builder.add(Attributes.ARMOR_TOUGHNESS, 10.0D);
        builder.add(Attributes.ATTACK_KNOCKBACK, 0.5D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.25D);
        builder.add(Attributes.FOLLOW_RANGE, 60.0F);
        builder.add(Attributes.ATTACK_DAMAGE, 8.0D);
        builder.add(Attributes.ARMOR, 8.0F);
        return builder;
    }

    public static List<Holder<MobEffect>> getExclusiveEffects() {
        List<Holder<MobEffect>> list = new ArrayList<>();
        BUFF_LIST.forEach(e -> list.add(e.getEffect()));
        list.add(TAMobEffects.MOON_BEFALL);
        return list;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACK_Y_ROT, 0.0F);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new MoonQueenBodyRotationControl(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkRunIdleController(this));
        controllers.add(new AnimationController<>(this, "block_controller", state -> PlayState.STOP)
                .triggerableAnim("block_animation", DefaultAnimations.ATTACK_BLOCK).transitionLength(5));
        controllers.add(new AnimationController<>(this, "swing_controller", state -> PlayState.STOP)
                .triggerableAnim("swing_animation", DefaultAnimations.ATTACK_SWING).transitionLength(1));
        controllers.add(new AnimationController<>(this, "swing_2_controller", state -> PlayState.STOP)
                .triggerableAnim("swing_2_animation", ATTACK_SWING_2).transitionLength(1));
        controllers.add(new AnimationController<>(this, "burst_controller", state -> PlayState.STOP)
                .triggerableAnim("burst_animation", ATTACK_BURST).transitionLength(1));
        controllers.add(new AnimationController<>(this, "buff_controller", state -> PlayState.STOP)
                .triggerableAnim("buff_animation", ATTACK_BUFF).transitionLength(5));
        controllers.add(new AnimationController<>(this, "duel_controller", state -> PlayState.STOP)
                .triggerableAnim("duel_animation", ATTACK_DUEL).transitionLength(5));
        controllers.add(new AnimationController<>(this, "blade_wave_controller", state -> PlayState.STOP)
                .triggerableAnim("blade_wave_animation", ATTACK_BLADE_WAVE).transitionLength(1));
        controllers.add(new AnimationController<>(this, "moon_befall_controller", state -> PlayState.STOP)
                .triggerableAnim("moon_befall_animation", ATTACK_MOON_BEFALL).transitionLength(5));
        controllers.add(new AnimationController<>(this, "defeat_controller", state -> PlayState.STOP)
                .triggerableAnim("defeat_animation", DEFEAT).transitionLength(5));
        controllers.add(new AnimationController<>(this, "defeat_loop_controller", state -> PlayState.STOP)
                .triggerableAnim("defeat_loop_animation", DEFEAT_LOOP).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        switch (level.getDifficulty()) {
            case NORMAL -> this.fqmPySwordNum = 16;
            case HARD -> this.fqmPySwordNum = 24;
            default -> this.fqmPySwordNum = 10;
        }

        return spawnGroupData;
    }

    public float getAttackYRot() {
        return this.entityData.get(ATTACK_Y_ROT);
    }

    public void setAttackYRot(float attackYRot) {
        this.entityData.set(ATTACK_Y_ROT, attackYRot);
    }

    private ListTag saveListTag(HashSet<String> list) {
        ListTag listTag = new ListTag();
        list.forEach(s -> {
            CompoundTag compound = new CompoundTag();
            compound.putString("Name", s);
            listTag.add(compound);
        });

        return listTag;
    }

    public void teleportToTheBackOfTheTarget(LivingEntity target) {
        if (target instanceof Player) {
            Vec3 eyePos = target.getEyePosition();
            Vec3 lookAngle = target.getLookAngle();
            double tx = eyePos.x - lookAngle.x * 2.0D;
            double tz = eyePos.z - lookAngle.z * 2.0D;
            this.teleportTo(tx, target.getY(), tz);
            if (target.isAlive()) {
                double dx = target.getX();
                double dy = target.getEyeY();
                double dz = target.getZ();
                this.getLookControl().setLookAt(dx, dy, dz);
            }
        }
    }

    private boolean isTruePlayer(Player player) {
        return !player.isCreative() && !player.isSpectator();
    }

    public List<Player> getPlayerInBoundingBoxWithInflate(double value) {
        List<Player> list = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(value));
        Stream<Player> stream = list.stream().filter(this::hasLineOfSight).filter(this::isTruePlayer);
        return new ArrayList<>(stream.toList());
    }

    public void selectDuelistFromNearestTarget() {
        List<String> list = new ArrayList<>();
        this.getPlayerInBoundingBoxWithInflate(24.0D).stream()
                .map(Player::getName).forEach(name -> list.add(name.getString()));
        boolean flag = this.currentDuelistName.isEmpty();
        if (!list.isEmpty() && flag && this.ticksDueling > 0) {
            int index = this.random.nextInt(list.size());
            this.triggerAnim(("duel_controller"), ("duel_animation"));
            this.currentDuelistName = list.get(index);
            this.triggerDuelingCount++;
            if (this.triggerDuelingCount == 1) {
                this.addEffect(BUFF_LIST.get(this.random.nextInt(BUFF_LIST.size())));
                this.preparationTime = 20;
                this.duelingMoment = true;
            } else {
                this.heal((this.getMaxHealth() * 0.1F));
            }

            LivingEntity attacker = this.getLastAttacker();
            if (attacker != null && attacker.isAlive()) {
                double dx = attacker.getX();
                double dy = attacker.getEyeY();
                double dz = attacker.getZ();
                this.getLookControl().setLookAt(dx, dy, dz);
            }
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.level() instanceof ServerLevel serverLevel) {
            float maxHealth = this.getMaxHealth();
            LivingEntity target = this.getTarget();
            AABB aabb = this.getBoundingBox().inflate(24.0D);
            List<Player> playerList = this.level().getEntitiesOfClass(Player.class, aabb);
            AttributeInstance health = this.getAttribute(TAAttributes.MAX_BOSS_HEALTH);
            serverLevel.players().forEach(player -> this.currentSavedName.add(player.getName().getString()));
            int size = this.currentSavedName.size() - this.playerAlreadyHealFor.size() - 1;
            boolean isHalfHealth = this.getHealth() < this.getMaxHealth() * 0.5F;
            if (isHalfHealth && !this.duelingMoment && this.ticksDueling == 2400) {
                this.selectDuelistFromNearestTarget();
            }

            if (target != null) {
                double distance = this.distanceToSqr(target);
                this.setSprinting(distance > 8.0D);
                this.addSpeedWhenFoundTarget();
//                this.destroyHorizontalBlock();
            } else {
                this.removeSpeedWhenNoTarget();
            }

            if (health != null && size > 0) {
                float extraValue = size * 200.0F;
                health.setBaseValue(maxHealth + extraValue);
                if (this.lastHurtByPlayer == null) {
                    this.setBossHealth((float) health.getBaseValue());
                } else {
                    this.heal(extraValue);
                }
            }

            this.safeTime = playerList.isEmpty() ? this.safeTime + 1 : 0;
            if (this.safeTime > 100 && this.tickCount % 20 == 0) {
                this.heal((maxHealth * 0.05F));
            }

            if (this.getAttackState() == 0) {
                this.setNoGravity(false);
            }

            this.playerAlreadyHealFor.addAll(this.currentSavedName);
        }
    }

    private void removeSpeedWhenNoTarget() {
        AttributeInstance instance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (instance != null && instance.getModifier(SPEED_MODIFIER_FOUND_TARGET) != null) {
            instance.removeModifier(SPEED_MODIFIER_FOUND_TARGET);
        }
    }

    private void addSpeedWhenFoundTarget() {
        AttributeInstance instance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL;
        if (instance != null && instance.getModifier(SPEED_MODIFIER_FOUND_TARGET) == null) {
            instance.addTransientModifier(new AttributeModifier(SPEED_MODIFIER_FOUND_TARGET, 0.4F, operation));
        }
    }

    public void destroyHorizontalBlock() {
        BlockPos pos = this.blockPosition();
        BlockPos[] blockPos = new BlockPos[] {pos, pos.above(), pos.above().above()};
        for (Direction direction : Direction.BY_2D_DATA) {
            for (BlockPos tempPos : blockPos) {
                BlockPos relativePos = tempPos.relative(direction);
                BlockState state = this.level().getBlockState(relativePos);
                if (!state.is(TABlockTags.MOON_TEMPLE_BLOCKS) && !state.isAir()) {
                    this.level().destroyBlock(relativePos, Boolean.FALSE);
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.isAlive()) {
            long l = this.ticksCanOneHitMustKill + 1L;
            this.ticksCanOneHitMustKill = Math.min(l, 24000L);
            if (this.duelingMoment && --this.ticksDueling == 0) {
                this.triggerDuelingCount = 0;
                this.currentDuelistName = "";
                this.duelingMoment = false;
            }

            if (this.preparationTime > 0) {
                --this.preparationTime;
            }
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        if (damageSource.getEntity() instanceof ServerPlayer player) {
            player.setData(TAAttachmentTypes.IMMUNE_TO_PRESSURE, true);
        }

        super.die(damageSource);
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (!this.level().isClientSide) {
            if (this.deathTime == 1) {
                this.triggerAnim("defeat_controller", "defeat_animation");
            } else if (this.deathTime == 11) {
                this.triggerAnim("defeat_loop_controller", "defeat_loop_animation");
            }

            if (this.deathTime > 80 && !this.isRemoved()) {
                this.level().broadcastEntityEvent(this, (byte) 60);
                this.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.GHAST_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isDamageSourceBlocked(damageSource) ? SoundEvents.ANVIL_PLACE : SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.GHAST_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15F, 1.0F);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 77) {
            for (int i = 0; i < 150; i++) {
                this.createParticleBall(0.4D, 1);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SafeTime", this.safeTime);
        compound.putInt("TicksDueling", this.ticksDueling);
        compound.putInt("PreparationTime", this.preparationTime);
        compound.putInt("TriggerDuelingCount", this.triggerDuelingCount);
        compound.putInt("FqmPySwordNum", this.fqmPySwordNum);
        compound.putBoolean("IsNeutral", this.isNeutral);
        compound.putBoolean("DuelingMoment", this.duelingMoment);
        compound.putString("CurrentDuelistName", this.currentDuelistName);
        compound.putLong("TicksCanOneHitMustKill", this.ticksCanOneHitMustKill);
        compound.put("KilledDuelistName", this.saveListTag(this.killedDuelistName));
        compound.put("CurrentSavedName", this.saveListTag(this.currentSavedName));
        compound.put("PlayerAlreadyHealFor", this.saveListTag(this.playerAlreadyHealFor));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.safeTime = compound.getInt("SafeTime");
        this.ticksDueling = compound.getInt("TicksDueling");
        this.preparationTime = compound.getInt("PreparationTime");
        this.triggerDuelingCount = compound.getInt("TriggerDuelingCount");
        this.fqmPySwordNum = compound.getInt("FqmPySwordNum");
        this.isNeutral = compound.getBoolean("IsNeutral");
        this.duelingMoment = compound.getBoolean("DuelingMoment");
        this.currentDuelistName = compound.getString("CurrentDuelistName");
        this.ticksCanOneHitMustKill = compound.getLong("TicksCanOneHitMustKill");
        ListTag listTagK = compound.getList("KilledDuelistName", 10);
        for (int i = 0; i < listTagK.size(); i++) {
            this.killedDuelistName.add(listTagK.getCompound(i).getString("Name"));
        }

        ListTag listTagC = compound.getList("CurrentSavedName", 10);
        for (int i = 0; i < listTagC.size(); i++) {
            this.currentSavedName.add(listTagC.getCompound(i).getString("Name"));
        }

        ListTag listTagT = compound.getList("PlayerAlreadyHealFor", 10);
        for (int i = 0; i < listTagT.size(); i++) {
            this.playerAlreadyHealFor.add(listTagT.getCompound(i).getString("Name"));
        }
    }

    @Override
    public boolean isInWall() {
        return false;
    }

    @Override
    protected float getWaterSlowDown() {
        return 1.0F;
    }

    @Override
    public boolean canSwimInFluidType(FluidType type) {
        return true;
    }

    @Override
    public boolean removeEffect(Holder<MobEffect> effect) {
        List<Holder<MobEffect>> list = new ArrayList<>();
        BUFF_LIST.forEach(instance -> list.add(instance.getEffect()));
        return !list.contains(effect) && super.removeEffect(effect);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        if (this.duelingMoment && !effectInstance.getEffect().value().isBeneficial()) {
            return false;
        }

        return super.canBeAffected(effectInstance);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        boolean noLastHurt = this.lastHurtByPlayer == null && this.getLastHurtByMob() == null;
        if (itemInHand.is(Tags.Items.NETHER_STARS) && noLastHurt && !this.isNeutral) {
            this.isNeutral = true;
            itemInHand.consume(1, player);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        DamageSource source = this.damageSources().mobAttack(this);
        if (this.level() instanceof ServerLevel serverLevel) {
            f += EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, source, f);
            if (this.hasEffect(TAMobEffects.MOON_BEFALL) || !(entity instanceof Player)) {
                this.heal(this.getMaxHealth() * 0.2F);
                entity.kill();
                return true;
            }
        }

        if (this.hasEffect(TAMobEffects.MOON_OF_VENGEANCE)) {
            f *= 2.0F;
        }

        boolean flag = entity.hurt(source, f);
        if (flag) {
            float f1 = this.getKnockback(entity, source);
            if (f1 > 0.0F && entity instanceof LivingEntity livingEntity) {
                float value = this.getYRot() * ((float)Math.PI / 180.0F);
                livingEntity.knockback(f1 * 0.5F, Mth.sin(value), -Mth.cos(value));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                EnchantmentHelper.doPostAttackEffects(serverLevel, entity, source);
            }

            if (entity instanceof ServerPlayer player) {
                AttachmentType<Integer> type = TAAttachmentTypes.UNINTERRUPTED_HURT_BY_MOON_QUEEN_COUNT.get();
                player.setData(type, player.getData(type) + 1);
                if (player.getData(type) >= 10) {
                    player.addEffect(new MobEffectInstance(TAMobEffects.LACERATION, 100));
                }
            }

            this.setLastHurtMob(entity);
            this.playAttackSound();
            if (this.hasEffect(TAMobEffects.CRESCENT)) {
                this.heal(f / 2.0F);
            }
        }

        return flag;
    }

    @Override
    public boolean isDamageSourceBlocked(DamageSource source) {
        Entity entity = source.getDirectEntity();
        boolean flag = entity instanceof AbstractArrow arrow && arrow.getPierceLevel() > 0;
        if (!source.is(DamageTypeTags.BYPASSES_SHIELD) && !flag) {
            Vec3 vec32 = source.getSourcePosition();
            if (vec32 != null) {
                Vec3 vec3 = this.getViewVector(1.0F);
                Vec3 vec31 = vec32.vectorTo(this.position()).normalize();
                vec31 = new Vec3(vec31.x, 0.0D, vec31.z);
                return vec31.dot(vec3) < 0.0D && this.random.nextFloat() <= 0.3F;
            }
        }

        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getEntity();
        boolean shouldImmuneRangedAttack = this.duelingMoment && source.getDirectEntity() instanceof Projectile;
        boolean isPreparingAnimation = this.preparationTime > 0 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
        if (this.isInvulnerableTo(source) || this.level().isClientSide || this.isDeadOrDying()) {
            return false;
        } else if (source.is(DamageTypeTags.IS_FIRE) && this.hasEffect(MobEffects.FIRE_RESISTANCE)) {
            return false;
        } else if (shouldImmuneRangedAttack || isPreparingAnimation) {
            return false;
        } else if (entity instanceof Player player && this.duelingMoment && this.triggerDuelingCount > 0
                && !this.currentDuelistName.equals(player.getName().getString())) {
            return false;
        } else {
            this.isNeutral = false;
            this.safeTime = 0;
            this.noActionTime = 0;
            float f = amount;
            boolean flag = false;
            if (amount > 0.0F && this.isDamageSourceBlocked(source)) {
                if (!source.is(DamageTypeTags.IS_PROJECTILE) && source.getDirectEntity() instanceof LivingEntity livingEntity) {
                    this.blockUsingShield(livingEntity);
                }

                amount = 0.0F;
                flag = true;
            }

            if (this.hasEffect(TAMobEffects.BLESS_OF_MOON)) {
                amount *= 0.5F;
            }

            boolean flag1 = true;
            if (this.invulnerableTime > 10.0F && !source.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
                if (amount <= this.lastHurt) {
                    return false;
                }

                this.actuallyHurt(source, amount - this.lastHurt);
                this.lastHurt = amount;
                flag1 = false;
            } else {
                this.lastHurt = amount;
                this.invulnerableTime = 20;
                this.actuallyHurt(source, amount);
                this.hurtDuration = 10;
                this.hurtTime = this.hurtDuration;
            }

            if (entity != null) {
                if (entity instanceof LivingEntity livingEntity
                        && !source.is(DamageTypeTags.NO_ANGER) && (!source.is(DamageTypes.WIND_CHARGE)
                        || !this.getType().is(EntityTypeTags.NO_ANGER_FROM_WIND_CHARGE))) {
                    this.setLastHurtByMob(livingEntity);
                }

                if (entity instanceof Player player1) {
                    this.lastHurtByPlayerTime = 100;
                    this.lastHurtByPlayer = player1;
                }
            }

            if (flag1) {
                if (flag) {
                    this.triggerAnim(("block_controller"), ("block_animation"));
                    this.level().broadcastEntityEvent(this, (byte)29);
                } else {
                    this.level().broadcastDamageEvent(this, source);
                }

                if (!source.is(DamageTypeTags.NO_IMPACT) && !flag) {
                    this.markHurt();
                }

                if (!source.is(DamageTypeTags.NO_KNOCKBACK)) {
                    double d0 = 0.0F;
                    double d1 = 0.0F;
                    if (source.getDirectEntity() instanceof Projectile projectile) {
                        DoubleDoubleImmutablePair pair = projectile.calculateHorizontalHurtKnockbackDirection(this, source);
                        d0 = -pair.leftDouble();
                        d1 = -pair.rightDouble();
                    } else if (source.getSourcePosition() != null) {
                        d0 = source.getSourcePosition().x() - this.getX();
                        d1 = source.getSourcePosition().z() - this.getZ();
                    }

                    this.knockback(0.4F, d0, d1);
                }
            }

            if (this.isDeadOrDying()) {
                if (!this.checkTotemDeathProtection(source)) {
                    if (flag1) {
                        this.makeSound(this.getDeathSound());
                    }

                    this.die(source);
                }
            } else if (flag1) {
                this.playHurtSound(source);
            }

            if (!flag) {
                this.lastDamageSource = source;
                this.lastDamageStamp = this.level().getGameTime();
            }

            if (entity instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.PLAYER_HURT_ENTITY.trigger(serverPlayer, this, source, f, amount, flag);
            }

            return !flag;
        }
    }

    public void createParticleBall(double speed, int size) {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        for (int i = -size; i <= size; ++i) {
            for (int j = -size; j <= size; ++j) {
                for (int k = -size; k <= size; ++k) {
                    double d3 = (double)j + (this.random.nextDouble() - this.random.nextDouble()) * 0.5D;
                    double d4 = (double)i + (this.random.nextDouble() - this.random.nextDouble()) * 0.5D;
                    double d5 = (double)k + (this.random.nextDouble() - this.random.nextDouble()) * 0.5D;
                    double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / speed + this.random.nextGaussian() * 0.05D;
                    this.level().addParticle(TAParticleTypes.MAGIC_PURPLE.get(), d0, d1, d2, d3 / d6, 0.1D, d5 / d6);
                    if (i != -size && i != size && j != -size && j != size) {
                        k += size * 2 - 1;
                    }
                }
            }
        }
    }

    @Override
    public boolean checkTotemDeathProtection(DamageSource damageSource) {
        if (this.ticksCanOneHitMustKill == 24000L) {
            this.triggerAnim(("moon_befall_controller"), ("moon_befall_animation"));
            this.addEffect(new MobEffectInstance(TAMobEffects.MOON_BEFALL, 200));
            this.ticksCanOneHitMustKill = this.level().getDayTime() % 24000L;
            Map<AttributeInstance, Double> map = new HashMap<>();
            map.put(this.getAttribute(Attributes.ARMOR), 30.0D);
            map.put(this.getAttribute(Attributes.ARMOR_TOUGHNESS), 20.0D);
            map.put(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE), 1.0D);
            map.put(this.getAttribute(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE), 1.0D);
            map.forEach(AttributeInstance::setBaseValue);
            this.duelingMoment = false;
            this.preparationTime = 26;
            this.setBossHealth(1.0F);
            this.setAttackState(4);
            return true;
        } else {
            return false;
        }
    }

    private class MoonQueenNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

        public MoonQueenNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee) {
            super(mob, targetType, mustSee);
        }

        public MoonQueenNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee, Predicate<LivingEntity> targetPredicate) {
            super(mob, targetType, mustSee, targetPredicate);
        }

        @Override
        public boolean canUse() {
            AABB aabb = getBoundingBox().inflate(24.0D);
            List<Player> playerList = level().getEntitiesOfClass(Player.class, aabb);
            if (!currentDuelistName.isEmpty() && ticksDueling > 0) {
                for (Player player : playerList) {
                    String name = player.getName().getString();
                    if (name.equals(currentDuelistName)) {
                        if (!isTruePlayer(player)) {
                            currentDuelistName = "";
                            selectDuelistFromNearestTarget();
                            return false;
                        }

                        this.target = player;
                        return true;
                    }
                }
            }

            int r = this.mob.getRandom().nextInt(this.randomInterval);
            if (this.randomInterval > 0 && r != 0 || isNeutral) {
                return false;
            } else {
                this.findTarget();
                return this.target != null;
            }
        }

    }

    private class MoonQueenBodyRotationControl extends BodyRotationControl {

        public MoonQueenBodyRotationControl(Mob mob) {
            super(mob);
        }

        @Override
        public void clientTick() {
            if (getAttackState() != 0) {
                yHeadRot = getAttackYRot();
                yBodyRot = getAttackYRot();
            } else {
                super.clientTick();
            }
        }

    }

}