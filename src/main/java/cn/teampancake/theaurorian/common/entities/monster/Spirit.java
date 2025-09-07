package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.entity.SacrificeTableBlockEntity;
import cn.teampancake.theaurorian.common.entities.ai.control.SpiritMoveControl;
import cn.teampancake.theaurorian.common.entities.ai.goal.SpiritChargeAttackGoal;
import cn.teampancake.theaurorian.common.entities.ai.goal.SpiritRandomMoveGoal;
import cn.teampancake.theaurorian.common.entities.npc.AurorianVillager;
import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.entities.phase.SpiritMeleePhase;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/** @noinspection deprecation*/
public class Spirit extends TAMonster {

    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Spirit.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(Spirit.class, EntityDataSerializers.BOOLEAN);
    private static final ResourceLocation ANGRY_MOVEMENT_SPEED_ID = TheAurorian.prefix("angry_movement_speed");
    private static final ResourceLocation ANGRY_ATTACK_DAMAGE_ID = TheAurorian.prefix("angry_attack_damage");
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private boolean canInvisible = true;

    public Spirit(EntityType<? extends Spirit> type, Level level) {
        super(type, level);
        this.moveControl = new SpiritMoveControl(this);
        this.attackManager = new AttackManager<>(this, List.of(new SpiritMeleePhase()));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new SpiritChargeAttackGoal(this));
        this.goalSelector.addGoal(8, new SpiritRandomMoveGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AurorianVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public static boolean checkSpawnRules(EntityType<Spirit> spirit, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (hasActiveSacrificeTableNearby(level, pos, 128)) return false;

        if (random.nextInt(20) != 0 || !level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(24.0D)).isEmpty()) return false;
        return level.getBlockState(pos.below()).is(TABlocks.AURORIAN_GRASS_BLOCK.get()) && checkAnyLightMonsterSpawnRules(spirit, level, spawnType, pos, random);
    }

    private static boolean hasActiveSacrificeTableNearby(ServerLevelAccessor accessor, BlockPos pos, int range) {
        ServerLevel serverLevel = accessor.getLevel();
        int minChunkX = (pos.getX() - range) >> 4;
        int maxChunkX = (pos.getX() + range) >> 4;
        int minChunkZ = (pos.getZ() - range) >> 4;
        int maxChunkZ = (pos.getZ() + range) >> 4;

        for (int cx = minChunkX; cx <= maxChunkX; cx++) {
            for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                LevelChunk chunk = serverLevel.getChunkSource().getChunkNow(cx, cz);
                if (chunk == null) continue; // only consider loaded chunks
                for (BlockEntity be : chunk.getBlockEntities().values()) {
                    if (be instanceof SacrificeTableBlockEntity table && table.guardTime > 0) {
                        if (be.getBlockPos().closerThan(pos, range + 0.5)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, 40.0F);
        builder.add(Attributes.MOVEMENT_SPEED, 0.2F);
        builder.add(Attributes.ATTACK_DAMAGE, 5.0F);
        builder.add(Attributes.FOLLOW_RANGE, 35.0D);
        builder.add(Attributes.ARMOR, 0.0F);
        return builder;
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (this.isAngry()) {
            AttributeInstance instance1 = this.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeInstance instance2 = this.getAttribute(Attributes.ATTACK_DAMAGE);
            if (instance1 != null && instance2 != null) {
                instance1.addPermanentModifier(new AttributeModifier(ANGRY_MOVEMENT_SPEED_ID, 0.2D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                instance2.addPermanentModifier(new AttributeModifier(ANGRY_ATTACK_DAMAGE_ID, 9.0D, AttributeModifier.Operation.ADD_VALUE));
            }
        }

        return spawnGroupData;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLAGS_ID, (byte)0);
        builder.define(ANGRY, Boolean.FALSE);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (key.equals(ATTACK_STATE) && this.getAttackState() != 0) {
            if (this.getAttackState() == 1) {
                this.attackAnimationState.start(this.tickCount);
            }
        }

        super.onSyncedDataUpdated(key);
    }

    public boolean getFlag(int mask) {
        return (this.entityData.get(DATA_FLAGS_ID) & mask) != 0;
    }

    public void setFlag(int mask, boolean value) {
        int i = this.entityData.get(DATA_FLAGS_ID);
        if (value) {
            i |= mask;
        } else {
            i &= ~mask;
        }

        this.entityData.set(DATA_FLAGS_ID, (byte)(i & 255));
    }

    public boolean isAngry() {
        return this.entityData.get(ANGRY);
    }

    public void setAngry(boolean isAngry) {
        this.entityData.set(ANGRY, isAngry);
    }

    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            boolean flag = !this.isInWaterOrBubble() && !this.walkAnimation.isMoving();
            this.idleAnimationState.animateWhen(flag, this.tickCount);
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (!this.level().isClientSide && this.canInvisible && this.getHealth() <= 5.0D) {
            this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 200));
            this.canInvisible = false;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsAngry", this.isAngry());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setAngry(compound.getBoolean("IsAngry"));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextBoolean() ? SoundEvents.AMBIENT_CAVE.value() : SoundEvents.VEX_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.WITHER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

}