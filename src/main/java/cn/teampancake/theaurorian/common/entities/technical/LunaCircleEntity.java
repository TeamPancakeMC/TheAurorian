package cn.teampancake.theaurorian.common.entities.technical;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LunaCircleEntity extends Entity implements GeoEntity {

    private static final EntityDataAccessor<Integer> SPAWN_TIME = SynchedEntityData.defineId(LunaCircleEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> SCALE = SynchedEntityData.defineId(LunaCircleEntity.class, EntityDataSerializers.FLOAT);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public LunaCircleEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(SPAWN_TIME, 0);
        this.entityData.define(SCALE, 0.0F);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "spawn_controller", state -> PlayState.STOP)
                .triggerableAnim("spawn_animation", DefaultAnimations.SPAWN).transitionLength(5));
        controllers.add(new AnimationController<>(this, "rotate_controller", state -> PlayState.STOP)
                .triggerableAnim("rotate_animation", RawAnimation.begin().thenPlay(("misc.rotate"))).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public int getSpawnTime() {
        return this.entityData.get(SPAWN_TIME);
    }

    public float getScale() {
        return this.entityData.get(SCALE);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isRemoved() && !this.level().isClientSide()) {
            this.entityData.set(SPAWN_TIME, this.getSpawnTime() + 1);
            if (this.getSpawnTime() == 1) {
                this.triggerAnim("spawn_controller", "spawn_animation");
            } else if (this.getSpawnTime() == 31) {
                this.triggerAnim("rotate_controller", "rotate_animation");
            }
            if (this.getScale() <= 8.0F) {
                this.entityData.set(SCALE, this.getScale() + 0.05F);
            }
            if (this.getSpawnTime() > 180) {
                this.discard();
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        compound.putInt("SpawnTime", this.entityData.get(SPAWN_TIME));
        compound.putFloat("Scale", this.entityData.get(SCALE));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        this.entityData.set(SPAWN_TIME, compound.getInt("SpawnTime"));
        this.entityData.set(SCALE, compound.getFloat("Scale"));
    }

}