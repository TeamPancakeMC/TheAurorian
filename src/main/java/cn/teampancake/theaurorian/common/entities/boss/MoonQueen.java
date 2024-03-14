package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.common.entities.ai.goal.MeleeNoAttackGoal;
import cn.teampancake.theaurorian.common.entities.phase.*;
import cn.teampancake.theaurorian.common.registry.TAAttributes;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;

public class MoonQueen extends AbstractAurorianBoss implements GeoEntity {

    private static final RawAnimation SHRUG = RawAnimation.begin().thenPlay("misc.shrug");
    private static final RawAnimation LUNA_BEFALL = RawAnimation.begin().thenPlay("skill.luna_befall");
    private static final RawAnimation LUNA_BEFALL_END = RawAnimation.begin().thenPlay("skill.luna_befall_end");
    private static final EntityDataAccessor<Float> ATTACK_Y_ROT =
            SynchedEntityData.defineId(MoonQueen.class, EntityDataSerializers.FLOAT);
    private static final List<MobEffectInstance> BUFF_LIST = List.of(
            new MobEffectInstance(TAMobEffects.CRESCENT.get(), 200),
            new MobEffectInstance(TAMobEffects.BLESS_OF_MOON.get(), 200),
            new MobEffectInstance(TAMobEffects.MOON_OF_VENGEANCE.get(), 200));
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final HashSet<String> killedDuelistUUID = new HashSet<>();
    private final HashSet<String> currentSavedUUID = new HashSet<>();
    private final HashSet<String> alreadyHealForUUID = new HashSet<>();
    private long ticksCanOneHitMustKill = 24000L;
    private int ticksDueling = 2400;
    private int safeTime;
    private boolean duelingMoment;
    private String currentDuelistUUID = "";

    public MoonQueen(EntityType<? extends MoonQueen> type, Level level) {
        super(type, level);
        this.xpReward = 500;
        this.attackManager = new AttackManager<>(this, List.of(
                new MoonQueenSwingPhase(), new MoonQueenBlockPhase(), new MoonQueenShrugPhase(),
                new MoonQueenLunaBefallPhase(), new MoonQueenLunaBefallEndPhase()));
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
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.TRUE));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(TAAttributes.MAX_BOSS_HEALTH.get(), 500.0D);
        builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.85D);
        builder.add(Attributes.ARMOR_TOUGHNESS, 10.0D);
        builder.add(Attributes.ATTACK_KNOCKBACK, 0.5D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.25D);
        builder.add(Attributes.FOLLOW_RANGE, 40.0F);
        builder.add(Attributes.ATTACK_DAMAGE, 4.0D);
        builder.add(Attributes.ARMOR, 8.0F);
        return builder;
    }

    public static List<MobEffect> getExclusiveEffects() {
        List<MobEffect> list = new ArrayList<>();
        list.add(TAMobEffects.FALL_OF_MOON.get());
        BUFF_LIST.forEach(instance -> list.add(instance.getEffect()));
        return list;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_Y_ROT, 0.0F);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new MoonQueenBodyRotationControl(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));
        controllers.add(new AnimationController<>(this, "swing_controller", state -> PlayState.STOP)
                .triggerableAnim("swing_animation", DefaultAnimations.ATTACK_SWING).transitionLength(5));
        controllers.add(new AnimationController<>(this, "block_controller", state -> PlayState.STOP)
                .triggerableAnim("block_animation", DefaultAnimations.ATTACK_BLOCK).transitionLength(5));
        controllers.add(new AnimationController<>(this, "shrug_controller", state -> PlayState.STOP)
                .triggerableAnim("shrug_animation", SHRUG).transitionLength(5));
        controllers.add(new AnimationController<>(this, "luna_befall_controller", state -> PlayState.STOP)
                .triggerableAnim("luna_befall_animation", LUNA_BEFALL).transitionLength(5));
        controllers.add(new AnimationController<>(this, "luna_befall_end_controller", state -> PlayState.STOP)
                .triggerableAnim("luna_befall_end_animation", LUNA_BEFALL_END).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public float getAttackYRot() {
        return this.entityData.get(ATTACK_Y_ROT);
    }

    public void setAttackYRot(float attackYRot) {
        this.entityData.set(ATTACK_Y_ROT, attackYRot);
    }

    public void setSafeTime(int safeTime) {
        this.safeTime = safeTime;
    }

    public boolean isDuelingMoment() {
        return this.duelingMoment;
    }

    public HashSet<String> getKilledDuelistUUID() {
        return this.killedDuelistUUID;
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

    private void teleportToLowestHealthOrOutermostTarget() {
        boolean flag = this.random.nextBoolean();
        List<String> uuidList = Lists.newArrayList();
        List<Integer> integerList = Lists.newArrayList();
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        AABB aabb = this.getBoundingBox().inflate(40.0F);
        List<Player> playerList = this.level().getEntitiesOfClass(Player.class, aabb);
        if (!playerList.isEmpty()) {
            playerList.stream().filter(this::hasLineOfSight).forEach(player -> {
                float d = (float) this.distanceToSqr(player);
                float f = flag ? player.getHealth() : d;
                treeMap.put(player.getStringUUID(), (int) f);
            });

            treeMap.forEach((key, value) -> integerList.add(value));
            Collections.sort(integerList);
            treeMap.forEach((key, value) -> {
                int index = flag ? 0 : integerList.size();
                if (Objects.equals(treeMap.get(key), integerList.get(index))) {
                    uuidList.add(key);
                }
            });

            if (!uuidList.isEmpty()) {
                String uuid = uuidList.get(this.random.nextInt(uuidList.size()));
                for (Player player : playerList) {
                    if (player.getStringUUID().equals(uuid)) {
                        this.teleportToTheBackOfTheTarget(player);
                        break;
                    }
                }
            }
        }
    }

    private void teleportToTheBackOfTheTarget(LivingEntity target) {
        Vec3 eyePos = target.getEyePosition();
        Vec3 lookAngle = target.getLookAngle();
        double tx = eyePos.x - lookAngle.x * 2.0D;
        double tz = eyePos.z - lookAngle.z * 2.0D;
        this.teleportTo(tx, eyePos.y, tz);
        if (target.isAlive()) {
            double dx = target.getX();
            double dy = target.getEyeY();
            double dz = target.getZ();
            this.getLookControl().setLookAt(dx, dy, dz);
        }
    }

    public void selectDuelistFromNearestTarget() {
        AABB aabb = this.getBoundingBox().inflate(24.0D);
        List<String> uuidList = Lists.newArrayList();
        List<Player> playerList = this.level().getEntitiesOfClass(Player.class, aabb);
        playerList.forEach(player -> uuidList.add(player.getStringUUID()));
        boolean flag = this.currentDuelistUUID.isEmpty() || !uuidList.contains(this.currentDuelistUUID);
        if (!playerList.isEmpty() && flag && this.ticksDueling > 0) {
            int index = this.random.nextInt(playerList.size());
            this.currentDuelistUUID = playerList.get(index).getStringUUID();
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.level() instanceof ServerLevel serverLevel) {
            String uuid = this.currentDuelistUUID;
            AABB aabb = this.getBoundingBox().inflate(24.0D);
            List<ServerPlayer> serverPlayerList = serverLevel.players();
            List<Player> playerList = this.level().getEntitiesOfClass(Player.class, aabb);
            AttributeInstance health = this.getAttribute(TAAttributes.MAX_BOSS_HEALTH.get());
            serverPlayerList.forEach(player -> this.currentSavedUUID.add(player.getStringUUID()));
            int size = this.currentSavedUUID.size() - this.alreadyHealForUUID.size() - 1;
            boolean isHalfHealth = this.getHealth() < this.getMaxHealth() * 0.5F;
            if (isHalfHealth && !this.duelingMoment && this.ticksDueling == 2400) {
                this.addEffect(BUFF_LIST.get(this.random.nextInt(BUFF_LIST.size())));
                this.selectDuelistFromNearestTarget();
                this.duelingMoment = true;
            }

            if (!uuid.isEmpty() && playerList.isEmpty() && this.duelingMoment) {
                this.selectDuelistFromNearestTarget();
                this.heal(50.0F);
            }

            if (health != null && size > 0) {
                health.setBaseValue(this.getMaxHealth() + size * 200.0F);
            }

            if (playerList.isEmpty()) {
                ++this.safeTime;
            } else {
                this.safeTime = 0;
            }

            if (this.safeTime > 60) {
                this.heal(2.5F);
            }

            this.alreadyHealForUUID.addAll(this.currentSavedUUID);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.isAlive()) {
            long l = this.ticksCanOneHitMustKill + 1L;
            this.ticksCanOneHitMustKill = Math.min(l, 24000L);
            if (this.duelingMoment && --this.ticksDueling == 0) {
                this.duelingMoment = false;
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
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SafeTime", this.safeTime);
        compound.putInt("TicksDueling", this.ticksDueling);
        compound.putBoolean("DuelingMoment", this.duelingMoment);
        compound.putString("CurrentDuelistUUID", this.currentDuelistUUID);
        compound.putLong("TicksCanOneHitMustKill", this.ticksCanOneHitMustKill);
        compound.put("KilledDuelistUUID", this.saveListTag(this.killedDuelistUUID));
        compound.put("CurrentSavedUUID", this.saveListTag(this.currentSavedUUID));
        compound.put("AlreadyHealForUUID", this.saveListTag(this.alreadyHealForUUID));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.safeTime = compound.getInt("SafeTime");
        this.ticksDueling = compound.getInt("TicksDueling");
        this.duelingMoment = compound.getBoolean("DuelingMoment");
        this.currentDuelistUUID = compound.getString("CurrentDuelistUUID");
        this.ticksCanOneHitMustKill = compound.getLong("TicksCanOneHitMustKill");
        ListTag listTagK = compound.getList("KilledDuelistUUID", 10);
        for (int i = 0; i < listTagK.size(); i++) {
            this.killedDuelistUUID.add(listTagK.getCompound(i).getString("UUID"));
        }

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
    protected void tickEffects() {
        Iterator<MobEffect> iterator = this.getActiveEffectsMap().keySet().iterator();
        try {
            while (iterator.hasNext()) {
                MobEffectInstance instance = this.getActiveEffectsMap().get(iterator.next());
                if (!instance.tick(this, () -> this.onEffectUpdated(instance, true, null))) {
                    if (!this.level().isClientSide) {
                        iterator.remove();
                        this.onEffectRemoved(instance);
                        if (this.duelingMoment && BUFF_LIST.contains(instance)) {
                            this.addEffect(BUFF_LIST.get(this.random.nextInt(BUFF_LIST.size())));
                        }
                    }
                } else if (instance.getDuration() % 600 == 0) {
                    this.onEffectUpdated(instance, false, null);
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        if (this.duelingMoment && !effectInstance.getEffect().isBeneficial()) {
            return false;
        }

        return super.canBeAffected(effectInstance);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (entity instanceof LivingEntity livingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), livingEntity.getMobType());
            f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
            if (this.hasEffect(TAMobEffects.FALL_OF_MOON.get())) {
                this.heal(this.getMaxHealth() * 0.2F);
                livingEntity.kill();
                return true;
            }
        }

        if (this.hasEffect(TAMobEffects.MOON_OF_VENGEANCE.get())) {
            f *= 2.0F;
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            entity.setSecondsOnFire(i * 4);
        }

        boolean flag = entity.hurt(this.damageSources().mobAttack(this), f);
        if (flag) {
            this.level().broadcastEntityEvent(this, (byte) 4);
            if (f1 > 0.0F && entity instanceof LivingEntity livingEntity) {
                float value = this.getYRot() * ((float)Math.PI / 180.0F);
                livingEntity.knockback(f1 * 0.5F, Mth.sin(value), -Mth.cos(value));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            if (entity instanceof Player player) {
                this.maybeDisableShield(player, this.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }

            this.doEnchantDamageEffects(this, entity);
            this.setLastHurtMob(entity);
            if (this.hasEffect(TAMobEffects.CRESCENT.get())) {
                this.heal(f / 2.0F);
            }
        }

        return flag;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean flag = this.hasEffect(TAMobEffects.BLESS_OF_MOON.get());
        if (!this.isInvulnerableTo(source)) {
            if (this.duelingMoment && source.getDirectEntity() instanceof Projectile) {
                return false;
            }

            if (source.getEntity() instanceof Player player) {
                this.safeTime = 0;
                String uuid = player.getStringUUID();
                if (this.duelingMoment && !this.currentDuelistUUID.equals(uuid)) {
                    return false;
                }
            }

            return super.hurt(source, flag ? amount / 2.0F : amount);
        } else {
            return false;
        }
    }

    @Override
    public boolean checkTotemDeathProtection(DamageSource damageSource) {
        this.setBossHealth(1.0F);
        this.ticksCanOneHitMustKill = this.level().getDayTime() % 24000L;
        Map<AttributeInstance, Double> adMap = new HashMap<>();
        adMap.put(this.getAttribute(Attributes.ARMOR), 30.0D);
        adMap.put(this.getAttribute(Attributes.MOVEMENT_SPEED), 0.35D);
        adMap.put(this.getAttribute(Attributes.ARMOR_TOUGHNESS), 20.0D);
        adMap.put(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE), 1.0D);
        adMap.forEach(AttributeInstance::setBaseValue);
        this.addEffect(new MobEffectInstance(TAMobEffects.FALL_OF_MOON.get(), 200));
        return this.ticksCanOneHitMustKill == 24000L;
    }

    private class MoonQueenBodyRotationControl extends BodyRotationControl {

        public MoonQueenBodyRotationControl(Mob mob) {
            super(mob);
        }

        @Override
        public void clientTick() {
            if (getAttackState() == 1) {
                yHeadRot = getAttackYRot();
                yBodyRot = getAttackYRot();
            } else {
                super.clientTick();
            }
        }

    }

}