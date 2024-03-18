package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.data.nbt.MiscNBT;
import cn.teampancake.theaurorian.common.entities.ai.goal.MeleeNoAttackGoal;
import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.entities.phase.MoonQueenBlockPhase;
import cn.teampancake.theaurorian.common.entities.phase.MoonQueenMeleePhase;
import cn.teampancake.theaurorian.common.entities.phase.MoonQueenMoonBefallPhase;
import cn.teampancake.theaurorian.common.registry.TAAttributes;
import cn.teampancake.theaurorian.common.registry.TACapability;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.registry.TAParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;
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
import java.util.function.Predicate;

public class MoonQueen extends AbstractAurorianBoss implements GeoEntity {

    private static final RawAnimation DEFEAT = RawAnimation.begin().thenPlay("misc.defeat");
    private static final RawAnimation DEFEAT_LOOP = RawAnimation.begin().thenPlay("misc.defeat_loop");
    private static final RawAnimation ATTACK_SWING_2 = RawAnimation.begin().thenPlay("attack.swing_2");
    private static final RawAnimation ATTACK_BURST = RawAnimation.begin().thenPlay("attack.burst");
    private static final RawAnimation ATTACK_BUFF = RawAnimation.begin().thenPlay("attack.buff");
    private static final RawAnimation ATTACK_DUEL = RawAnimation.begin().thenPlay("attack.duel");
    private static final RawAnimation ATTACK_MOON_BEFALL = RawAnimation.begin().thenPlay("attack.moon_befall");
    private static final UUID SPEED_MODIFIER_FOUND_TARGET = UUID.fromString("3c64caed-3cb0-487e-a1b2-ee4c8364e352");
    private static final EntityDataAccessor<Float> ATTACK_Y_ROT = SynchedEntityData.defineId(MoonQueen.class, EntityDataSerializers.FLOAT);
    private static final List<MobEffectInstance> BUFF_LIST = List.of(
            new MobEffectInstance(TAMobEffects.CRESCENT.get(), 200),
            new MobEffectInstance(TAMobEffects.BLESS_OF_MOON.get(), 200),
            new MobEffectInstance(TAMobEffects.MOON_OF_VENGEANCE.get(), 200));
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public final HashSet<String> killedDuelistUUID = new HashSet<>();
    private final HashSet<String> currentSavedUUID = new HashSet<>();
    private final HashSet<String> alreadyHealForUUID = new HashSet<>();
    private long ticksCanOneHitMustKill = 24000L;
    private int ticksDueling = 2400;
    public int preparationTime;
    public int safeTime;
    public boolean isNeutral;
    public boolean duelingMoment;
    private String currentDuelistUUID = "";

    public MoonQueen(EntityType<? extends MoonQueen> type, Level level) {
        super(type, level);
        this.xpReward = 500;
        this.attackManager = new AttackManager<>(this, List.of(
                new MoonQueenMeleePhase(), new MoonQueenBlockPhase(),
                new MoonQueenMoonBefallPhase()));
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
        this.targetSelector.addGoal(2, new MoonQueenNearestAttackableTargetGoal<>(this, Player.class, Boolean.FALSE));
        this.targetSelector.addGoal(3, new MoonQueenNearestAttackableTargetGoal<>(this, LivingEntity.class, Boolean.FALSE, entity -> {
            ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
            boolean flag1 = key != null && !key.getNamespace().equals(AurorianMod.MOD_ID);
            boolean flag2 = entity.attackable() && !(entity instanceof Animal);
            return !(entity instanceof MoonQueen) && !(entity instanceof MoonlightKnight) && flag1 && flag2;
        }));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(TAAttributes.MAX_BOSS_HEALTH.get(), 500.0D);
        builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.85D);
        builder.add(Attributes.ARMOR_TOUGHNESS, 10.0D);
        builder.add(Attributes.ATTACK_KNOCKBACK, 0.5D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.25D);
        builder.add(Attributes.FOLLOW_RANGE, 40.0F);
        builder.add(Attributes.ATTACK_DAMAGE, 8.0D);
        builder.add(Attributes.ARMOR, 8.0F);
        return builder;
    }

    public static List<MobEffect> getExclusiveEffects() {
        List<MobEffect> list = new ArrayList<>();
        list.add(TAMobEffects.MOON_BEFALL.get());
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
            playerList.stream().filter(this::hasLineOfSight)
                    .filter(this::isTruePlayer).forEach(player -> {
                float d = (float) this.distanceToSqr(player);
                String uuid = player.getStringUUID();
                if (flag) {
                    treeMap.put(uuid, (int) player.getHealth());
                } else {
                    if (d > 144.0F) {
                        treeMap.put(uuid, (int) d);
                    }
                }
            });

            if (treeMap.isEmpty()) return;
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
        this.teleportTo(tx, target.getY(), tz);
        if (target.isAlive()) {
            double dx = target.getX();
            double dy = target.getEyeY();
            double dz = target.getZ();
            this.getLookControl().setLookAt(dx, dy, dz);
        }
    }

    private boolean isTruePlayer(Player player) {
        return !player.isCreative() && !player.isSpectator();
    }

    public void selectDuelistFromNearestTarget() {
        AABB aabb = this.getBoundingBox().inflate(24.0D);
        List<String> uuidList = new ArrayList<>();
        List<Player> playerList = this.level().getEntitiesOfClass(Player.class, aabb);
        playerList.stream().filter(this::isTruePlayer).forEach(
                player -> uuidList.add(player.getStringUUID()));
        boolean flag = this.currentDuelistUUID.isEmpty();
        if (!playerList.isEmpty() && flag && this.ticksDueling > 0) {
            int index = this.random.nextInt(uuidList.size());
            this.currentDuelistUUID = uuidList.get(index);
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.level() instanceof ServerLevel serverLevel) {
            String uuid = this.currentDuelistUUID;
            float maxHealth = this.getMaxHealth();
            LivingEntity target = this.getTarget();
            AABB aabb = this.getBoundingBox().inflate(24.0D);
            List<ServerPlayer> serverPlayerList = serverLevel.players();
            List<Player> playerList = this.level().getEntitiesOfClass(Player.class, aabb);
            AttributeInstance health = this.getAttribute(TAAttributes.MAX_BOSS_HEALTH.get());
            serverPlayerList.forEach(player -> this.currentSavedUUID.add(player.getStringUUID()));
            int size = this.currentSavedUUID.size() - this.alreadyHealForUUID.size() - 1;
            boolean isHalfHealth = this.getHealth() < this.getMaxHealth() * 0.5F;
            if (isHalfHealth && !this.duelingMoment && this.ticksDueling == 2400) {
                this.addEffect(BUFF_LIST.get(this.random.nextInt(BUFF_LIST.size())));
                this.triggerAnim(("duel_controller"), ("duel_animation"));
                this.selectDuelistFromNearestTarget();
                this.preparationTime = 20;
                this.duelingMoment = true;
                LivingEntity attacker = this.getLastAttacker();
                if (attacker != null && attacker.isAlive()) {
                    double dx = attacker.getX();
                    double dy = attacker.getEyeY();
                    double dz = attacker.getZ();
                    this.getLookControl().setLookAt(dx, dy, dz);
                }
            }

            if (this.duelingMoment) {
                for (MobEffectInstance instance : this.getActiveEffects()) {
                    if (BUFF_LIST.contains(instance) && instance.getDuration() == 1) {
                        this.removeEffect(instance.getEffect());
                        this.addEffect(BUFF_LIST.get(this.random.nextInt(BUFF_LIST.size())));
                        this.triggerAnim(("buff_controller"), ("buff_animation"));
                        break;
                    }
                }

                if (!uuid.isEmpty() && playerList.isEmpty()) {
                    float amount = this.getMaxHealth() * 0.1F / 20.0F;
                    this.currentDuelistUUID = "";
                    this.selectDuelistFromNearestTarget();
                    this.heal(amount);
                }
            }

            if (target != null) {
                double distance = this.distanceToSqr(target);
                this.setSprinting(distance > 8.0D);
                this.addSpeedWhenFoundTarget();
                this.destroyHorizontalBlock();
                this.safeTime = 0;
            } else {
                this.removeSpeedWhenNoTarget();
                ++this.safeTime;
            }

            if (health != null && size > 0) {
                health.setBaseValue(maxHealth + size * 200.0F);
                if (this.lastHurtByPlayer == null) {
                    this.setBossHealth((float) health.getBaseValue());
                } else {
                    this.heal(size * 200.0F);
                }
            }

            if (this.safeTime > 100 && this.tickCount % 20 == 0) {
                this.heal(maxHealth * 0.05F);
            }

            this.alreadyHealForUUID.addAll(this.currentSavedUUID);
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
        AttributeModifier.Operation operation = AttributeModifier.Operation.MULTIPLY_TOTAL;
        if (instance != null && instance.getModifier(SPEED_MODIFIER_FOUND_TARGET) == null) {
            instance.addTransientModifier(new AttributeModifier(SPEED_MODIFIER_FOUND_TARGET, "Found Target", 0.3F, operation));
        }
    }

    private void destroyHorizontalBlock() {
        BlockPos pos = this.blockPosition();
        BlockPos[] blockPos = new BlockPos[] {pos, pos.above()};
        for (Direction direction : Direction.BY_2D_DATA) {
            for (BlockPos tempPos : blockPos) {
                BlockPos relativePos = tempPos.relative(direction);
                BlockState state = this.level().getBlockState(relativePos);
                if (!state.is(TABlockTags.MOON_TEMPLE_BLOCK) && !state.isAir()) {
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
                this.currentDuelistUUID = "";
                this.duelingMoment = false;
            }

            if (this.preparationTime > 0) {
                --this.preparationTime;
            }
        }
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
    protected void tickEffects() {
        Iterator<MobEffect> iterator = this.getActiveEffectsMap().keySet().iterator();
        try {
            while(iterator.hasNext()) {
                MobEffect mobEffect = iterator.next();
                MobEffectInstance instance = this.getActiveEffectsMap().get(mobEffect);
                if (!instance.tick(this, () -> this.onEffectUpdated(instance, true, null))) {
                    if (!this.level().isClientSide) {
                        iterator.remove();
                        this.onEffectRemoved(instance);
                    }
                } else if (instance.getDuration() % 600 == 0) {
                    this.onEffectUpdated(instance, false, null);
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
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
        compound.putBoolean("IsNeutral", this.isNeutral);
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
        this.preparationTime = compound.getInt("PreparationTime");
        this.isNeutral = compound.getBoolean("IsNeutral");
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
    public boolean isInWall() {
        return false;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        if (this.duelingMoment && !effectInstance.getEffect().isBeneficial()) {
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
            if (!player.getAbilities().instabuild) {
                itemInHand.shrink(1);
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (entity instanceof LivingEntity livingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), livingEntity.getMobType());
            f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
            if (this.hasEffect(TAMobEffects.MOON_BEFALL.get()) || !(livingEntity instanceof Player)) {
                this.heal(this.getMaxHealth() * 0.2F);
                livingEntity.kill();
                return true;
            }
        }

        if (this.hasEffect(TAMobEffects.MOON_OF_VENGEANCE.get())) {
            f *= 2.0F;
        }

        boolean flag = entity.hurt(this.damageSources().mobAttack(this), f);
        if (flag) {
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
        boolean flag = this.hasEffect(TAMobEffects.BLESS_OF_MOON.get());
        boolean shouldImmuneRangedAttack = this.duelingMoment && source.getDirectEntity() instanceof Projectile;
        boolean isPreparingAnimation = this.preparationTime > 0 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
        if (!this.isInvulnerableTo(source)) {
            this.isNeutral = false;
            this.safeTime = 0;
            if (shouldImmuneRangedAttack || isPreparingAnimation) {
                return false;
            }

            if (this.isDamageSourceBlocked(source)) {
                this.triggerAnim(("block_controller"), ("block_animation"));
                return false;
            }

            if (source.getEntity() instanceof Player player) {
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
    public void die(DamageSource damageSource) {
        if (!this.isRemoved()) {
            Entity entity = damageSource.getEntity();
            this.getCombatTracker().recheckStatus();
            if (this.level() instanceof ServerLevel serverlevel) {
                if (entity == null || entity.killedEntity(serverlevel, this)) {
                    this.gameEvent(GameEvent.ENTITY_DIE);
                    this.dropAllDeathLoot(damageSource);
                }

                if (entity instanceof ServerPlayer serverPlayer) {
                    LazyOptional<MiscNBT> capability = serverPlayer.getCapability(TACapability.MISC_CAP);
                    capability.ifPresent(miscNBT -> miscNBT.setImmuneToPressure(true));
                }

                this.level().broadcastEntityEvent(this, (byte)3);
            }
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

    public void pushAwaySurroundingEntities(double radius, double strength) {
        AABB aabb = this.getBoundingBox().inflate(radius);
        this.level().getEntitiesOfClass(LivingEntity.class, aabb, entity -> entity != this).forEach(entity -> {
            Vec3 vec31 = entity.position().subtract(this.position());
            double d = (2.5F - vec31.length()) * (double)0.6F * strength;
            Vec3 vec32 = vec31.normalize().scale(Math.abs(d));
            entity.push(vec32.x, 0.6F, vec32.z);
        });
    }

    @Override
    public boolean checkTotemDeathProtection(DamageSource damageSource) {
        if (this.ticksCanOneHitMustKill == 24000L) {
            this.triggerAnim(("moon_befall_controller"), ("moon_befall_animation"));
            this.addEffect(new MobEffectInstance(TAMobEffects.MOON_BEFALL.get(), 200));
            this.ticksCanOneHitMustKill = this.level().getDayTime() % 24000L;
            Map<AttributeInstance, Double> map = new HashMap<>();
            map.put(this.getAttribute(Attributes.ARMOR), 30.0D);
            map.put(this.getAttribute(Attributes.ARMOR_TOUGHNESS), 20.0D);
            map.put(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE), 1.0D);
            map.forEach(AttributeInstance::setBaseValue);
            this.duelingMoment = false;
            this.preparationTime = 26;
            this.setBossHealth(1.0F);
            this.setAttackState(3);
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
            if (!currentDuelistUUID.isEmpty() && ticksDueling > 0) {
                for (Player player : playerList) {
                    String uuid = player.getStringUUID();
                    if (uuid.equals(currentDuelistUUID)) {
                        if (!isTruePlayer(player)) {
                            currentDuelistUUID = "";
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
            if (getAttackState() == 1) {
                yHeadRot = getAttackYRot();
                yBodyRot = getAttackYRot();
            } else {
                super.clientTick();
            }
        }

    }

}