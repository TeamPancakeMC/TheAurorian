package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.common.entities.ai.goal.MeleeNoAttackGoal;
import cn.teampancake.theaurorian.common.entities.phase.*;
import cn.teampancake.theaurorian.common.registry.TAAttributes;
import cn.teampancake.theaurorian.common.registry.TAItems;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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
    private static final UUID SPEED_ENHANCE_UUID = UUID.fromString("b215d775-85f4-49d8-96c3-30fec59f99a8");
    private static final UUID ARMOR_ENHANCE_UUID = UUID.fromString("b15bdb0b-0ae6-430a-b879-4d0db50e1268");
    private static final EntityDataAccessor<Float> ATTACK_Y_ROT = SynchedEntityData.defineId(MoonQueen.class, EntityDataSerializers.FLOAT);
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
    private boolean duelingMoment = false;
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
        builder.add(Attributes.ATTACK_DAMAGE, 4.0D);
        builder.add(Attributes.ATTACK_KNOCKBACK, 0.5D);
        builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.85D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.25D);
        builder.add(Attributes.FOLLOW_RANGE, 40.0F);
        builder.add(Attributes.ARMOR, 8.0F);
        return builder;
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

    //随机传送至半径24格内血量最少或者距离最远的目标
    private void randomTeleportBehindTarget() {
        boolean flag = this.random.nextBoolean();
        List<String> uuidList = Lists.newArrayList();
        List<Integer> integerList = Lists.newArrayList();
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        AABB aabb = this.getBoundingBox().inflate((24.0D));
        List<Player> playerList = this.level().getEntitiesOfClass(Player.class, aabb);
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

    //传送到玩家的背后
    private void teleportToTheBackOfTheTarget(LivingEntity target) {
        double deltaX = this.getX() - target.getX();
        double deltaZ = this.getZ() - target.getZ();
        double x = target.getX() - (deltaX * 2.0D);
        double z = this.getZ() - (deltaZ * 2.0D);
        this.randomTeleport(x, target.getY(), z, Boolean.TRUE);
        this.getLookControl().setLookAt(target);
        this.setTarget(target);
    }

    //从附近24格之内寻找一名玩家作为决斗者
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
            AABB aabb16 = this.getBoundingBox().inflate(16.0D);
            AABB aabb24 = this.getBoundingBox().inflate(24.0D);
            List<ServerPlayer> serverPlayerList = serverLevel.players();
            List<Player> playerList24 = this.level().getEntitiesOfClass(Player.class, aabb24);
            List<Player> playerList16 = this.level().getEntitiesOfClass(Player.class, aabb16);
            AttributeInstance health = this.getAttribute(TAAttributes.MAX_BOSS_HEALTH.get());
            serverPlayerList.forEach(player -> this.currentSavedUUID.add(player.getStringUUID()));
            boolean isHalfHealth = this.getHealth() < this.getMaxHealth() * 0.5F;
            int size = this.currentSavedUUID.size() - this.alreadyHealForUUID.size();
            //血量低于一半时，会进入决斗状态，并选中一名玩家，作为决斗者，同时给自身随机增加Buff
            if (isHalfHealth && !this.duelingMoment && this.ticksDueling == 2400) {
                this.addEffect(BUFF_LIST.get(this.random.nextInt(BUFF_LIST.size())));
                this.selectDuelistFromNearestTarget();
                this.duelingMoment = true;
            }
            //如果处于决斗期间，并且已经选定好了决斗者的情况下，决斗者脱离战场，则重新寻找决斗者并恢复50点血量。
            if (!uuid.isEmpty() && playerList24.isEmpty() && this.duelingMoment) {
                this.selectDuelistFromNearestTarget();
                this.heal(50.0F);
            }
            //如果处在单人模式，一旦离开皎月女王超出十格的距离，那么会有50%的概率进行一次空间斩。
            if (serverPlayerList.size() == 1) {
                ServerPlayer singlePlayer = serverPlayerList.get(0);
                if (this.distanceToSqr(singlePlayer) > 100.0D && this.random.nextBoolean()) {
                    this.teleportToTheBackOfTheTarget(singlePlayer);
                }
            } else {
                if (playerList16.isEmpty()) {
                    this.randomTeleportBehindTarget();
                }
            }
            //皎月女王生成时，会根据在线的玩家数量（取不重复uuid值），额外增加”200×玩家数量“的血量。
            if (health != null && size > 0) {
                float add = size * 200.0F;
                health.setBaseValue(this.getMaxHealth() + add);
                this.heal(add);
            }
            //如果在24格之内找不到目标，则自增。
            if (playerList24.isEmpty()) {
                ++this.safeTime;
            } else {
                this.safeTime = 0;
            }
            //超过3秒钟没有目标，则每刻恢复2.5生命值。
            if (this.safeTime > 60) {
                this.heal(2.5F);
            }

            this.alreadyHealForUUID.addAll(this.currentSavedUUID);
        }
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            long l = this.ticksCanOneHitMustKill;
            this.ticksCanOneHitMustKill = Math.min(l + 1L, 24000L);
        }
        //决斗的时长为期2分钟，故在此自减。
        if (!this.level().isClientSide && this.duelingMoment) {
            int i = this.ticksDueling;
            this.ticksDueling = Math.min(i - 1, 0);
            if (this.ticksDueling == 0) {
                this.duelingMoment = false;
            }
        }

        super.tick();
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
            //设置每个游戏天只能使用一次月临技能，此时皎月女王的护甲和速度都会有加成，并附带秒杀效果。
            if (this.isDeadOrDying() && this.ticksCanOneHitMustKill == 24000L) {
                AttributeInstance armor = this.getAttribute(Attributes.ARMOR);
                AttributeInstance speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
                this.ticksCanOneHitMustKill = this.level().getGameTime();
                this.addEffect(new MobEffectInstance(TAMobEffects.FALL_OF_MOON.get(), 200));
                this.setHealth(1.0F);
                if (armor != null && speed != null) {
                    AttributeModifier.Operation operation = AttributeModifier.Operation.MULTIPLY_TOTAL;
                    armor.addPermanentModifier(new AttributeModifier(ARMOR_ENHANCE_UUID, "Final Armor Enhance", 2.0D, operation));
                    speed.addPermanentModifier(new AttributeModifier(SPEED_ENHANCE_UUID, "Final Speed Enhance", 0.2D, operation));
                }

                return true;
            }

            //在决斗期间，免疫任何远程伤害。
            if (this.duelingMoment && source.getDirectEntity() instanceof Projectile) {
                return false;
            }

            //在决斗期间，免疫任何除决斗者以外的任何伤害来源。
            if (source.getEntity() instanceof Player player) {
                this.safeTime = 0;
                if (this.duelingMoment) {
                    if (!this.currentDuelistUUID.equals(player.getStringUUID())) {
                        return false;
                    }
                }
            }

            return super.hurt(source, flag ? amount / 2.0F : amount);
        } else {
            return false;
        }
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TAItems.MOONSTONE_SWORD.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(TAItems.KNIGHT_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(TAItems.KNIGHT_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(TAItems.KNIGHT_BOOTS.get()));
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