package cn.teampancake.theaurorian.common.entities.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

@SuppressWarnings({"deprecation", "OverrideOnly"})
public class AurorianPixie extends PathfinderMob {

    @Nullable
    private BlockPos targetPosition;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState tradeAnimationState = new AnimationState();
    private static final EntityDataAccessor<Boolean> RESTING = SynchedEntityData.defineId(AurorianPixie.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(AurorianPixie.class, EntityDataSerializers.INT);
    private static final TargetingConditions RESTING_TARGETING = TargetingConditions.forNonCombat().range(4.0D);

    public AurorianPixie(EntityType<? extends AurorianPixie> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RESTING, false);
        this.entityData.define(COLOR, 1);
    }

    public boolean isResting() {
        return this.entityData.get(RESTING);
    }

    public int getColorId() {
        return this.entityData.get(COLOR);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isResting()) {
            this.setDeltaMovement(Vec3.ZERO);
        } else {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            this.idleAnimationState.animateWhen(this.isResting(), this.tickCount);
            this.flyAnimationState.animateWhen(!this.isResting(), this.tickCount);
        }
    }

    @Override
    protected void customServerAiStep() {
        BlockPos originPos = this.blockPosition();
        BlockPos belowPos = originPos.below();
        if (this.isResting()) {
            if (this.level().getBlockState(belowPos).isCollisionShapeFullBlock(this.level(), originPos)) {
                if (this.level().getNearestPlayer(RESTING_TARGETING, this) != null || this.hurtMarked) {
                    this.entityData.set(RESTING, false);
                }
            } else {
                this.entityData.set(RESTING, false);
            }
        } else {
            if (this.targetPosition != null && (!this.level().isEmptyBlock(this.targetPosition) || this.targetPosition.getY() <= this.level().getMinBuildHeight())) {
                this.targetPosition = null;
            }
            if (this.targetPosition == null || this.random.nextInt(30) == 0 || this.targetPosition.closerToCenterThan(this.position(), 2.0D)) {
                double x = this.getX() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7);
                double y = this.getY() + (double)this.random.nextInt(6) - 2.0D;
                double z = this.getZ() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7);
                this.targetPosition = BlockPos.containing(x, y, z);
            }
            double d2 = (double)this.targetPosition.getX() + 0.5D - this.getX();
            double d0 = (double)this.targetPosition.getY() + 0.1D - this.getY();
            double d1 = (double)this.targetPosition.getZ() + 0.5D - this.getZ();
            Vec3 vec3 = this.getDeltaMovement();
            double x = (Math.signum(d2) * 0.5D - vec3.x) * (double)0.1F;
            double y = (Math.signum(d0) * (double)0.7F - vec3.y) * (double)0.1F;
            double z = (Math.signum(d1) * 0.5D - vec3.z) * (double)0.1F;
            Vec3 vec31 = vec3.add(x, y, z);
            this.setDeltaMovement(vec31);
            this.zza = 0.5F;
            float f = (float)(Mth.atan2(vec31.z, vec31.x) * (double)(180F / (float)Math.PI)) - 90.0F;
            this.setYRot(this.getYRot() + Mth.wrapDegrees(f - this.getYRot()));
            if (this.random.nextInt(100) == 0 && this.level().getBlockState(belowPos).isCollisionShapeFullBlock(this.level(), belowPos)) {
                this.entityData.set(RESTING, true);
            }
        }
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(RESTING, compound.getBoolean("Resting"));
        this.entityData.set(COLOR, compound.getInt("Color"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Resting", this.isResting());
        compound.putInt("Color", this.getColorId());
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        this.entityData.set(COLOR, level.getRandom().nextInt(4) + 1);
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

}