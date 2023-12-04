package cn.teampancake.theaurorian.common.entities.technical;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class LunaCircleEntity extends Entity {

    private int time;
    public float scale;
    public final AnimationState rotateAnimationState = new AnimationState();
    public final AnimationState spawnAnimationState = new AnimationState();

    public LunaCircleEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        ++this.time;
        if (this.scale < 8.0F) {
            this.scale += 0.05F;
        }
        if (!this.isRemoved()) {
            if (this.level().isClientSide) {
                if (this.time <= 30) {
                    this.spawnAnimationState.startIfStopped(this.tickCount);
                } else {
                    this.spawnAnimationState.stop();
                    this.rotateAnimationState.startIfStopped(this.tickCount);
                }
            } else {
                if (this.time > 180) {
                    this.discard();
                }
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }

}