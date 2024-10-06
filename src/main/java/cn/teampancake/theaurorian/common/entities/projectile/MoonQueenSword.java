package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MoonQueenSword extends AbstractHurtingProjectile implements GeoEntity {

    private static final EntityDataAccessor<Float> WHITE_OVERLAY_RATIO = SynchedEntityData.defineId(MoonQueenSword.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> MODEL_X_ROT = SynchedEntityData.defineId(MoonQueenSword.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> MODEL_Y_ROT = SynchedEntityData.defineId(MoonQueenSword.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> MODEL_Z_ROT = SynchedEntityData.defineId(MoonQueenSword.class, EntityDataSerializers.FLOAT);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MoonQueenSword(EntityType<? extends MoonQueenSword> type, Level level) {
        super(type, level);
    }

    public MoonQueenSword(Level level, double x, double y, double z) {
        super(TAEntityTypes.MOON_QUEEN_SWORD.get(), x, y, z, level);
    }

    public MoonQueenSword(Level level, Vec3 movement, double x, double y, double z) {
        super(TAEntityTypes.MOON_QUEEN_SWORD.get(), x, y, z, movement, level);
    }

    public MoonQueenSword(LivingEntity owner, Vec3 movement, Level level) {
        super(TAEntityTypes.MOON_QUEEN_SWORD.get(), owner, movement, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(WHITE_OVERLAY_RATIO, 0.0F);
        builder.define(MODEL_X_ROT, 0.0F);
        builder.define(MODEL_Y_ROT, 0.0F);
        builder.define(MODEL_Z_ROT, 0.0F);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "spawn_controller", state -> PlayState.STOP)
                .triggerableAnim("spawn_animation", DefaultAnimations.SPAWN).transitionLength(5));
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return null;
    }

    public float getModelXRot() {
        return this.entityData.get(MODEL_X_ROT);
    }

    public void setModelXRot(float xRot) {
        this.entityData.set(MODEL_X_ROT, xRot);
    }

    public float getModelYRot() {
        return this.entityData.get(MODEL_Y_ROT);
    }

    public void setModelYRot(float yRot) {
        this.entityData.set(MODEL_Y_ROT, yRot);
    }

    public float getModelZRot() {
        return this.entityData.get(MODEL_Z_ROT);
    }

    public void setModelZRot(float zRot) {
        this.entityData.set(MODEL_Z_ROT, zRot);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("ModelXRot", this.getModelXRot());
        compound.putFloat("ModelYRot", this.getModelYRot());
        compound.putFloat("ModelZRot", this.getModelZRot());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setModelXRot(compound.getFloat("ModelXRot"));
        this.setModelYRot(compound.getFloat("ModelYRot"));
        this.setModelZRot(compound.getFloat("ModelZRot"));
    }

}