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

    public AurorianPixie(EntityType<? extends AurorianPixie> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RESTING, Boolean.FALSE);
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
            this.setPosRaw(this.getX(), (double) Mth.floor(this.getY()) + 1.0D - (double)this.getBbHeight(), this.getZ());
        } else {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        BlockPos originPos = this.blockPosition();
        if (this.isResting()) {

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