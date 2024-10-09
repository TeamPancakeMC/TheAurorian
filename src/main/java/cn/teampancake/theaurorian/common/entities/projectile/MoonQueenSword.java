package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAParticleTypes;
import cn.teampancake.theaurorian.common.registry.TASoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MoonQueenSword extends AbstractArrow {

    private static final EntityDataAccessor<Boolean> ROTATE = SynchedEntityData.defineId(MoonQueenSword.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SWORD_TYPE = SynchedEntityData.defineId(MoonQueenSword.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TIME_UNTIL_SHOOT = SynchedEntityData.defineId(MoonQueenSword.class, EntityDataSerializers.INT);
    public float fixedXRot;
    public float fixedYRot;
    public double type1Vec3X;
    public double type1Vec3Y;
    public double type1Vec3Z;

    public MoonQueenSword(EntityType<? extends MoonQueenSword> type, Level level) {
        super(type, level);
        this.pickup = Pickup.DISALLOWED;
    }

    public MoonQueenSword(Level level, LivingEntity shooter) {
        super(TAEntityTypes.MOON_QUEEN_SWORD.get(), shooter, level, new ItemStack(Items.STONE), null);
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ROTATE, false);
        builder.define(SWORD_TYPE, 0);
        builder.define(TIME_UNTIL_SHOOT, 128);
    }

    @Override
    public void tick() {
        this.baseTick();
        Vec3 vec3 = this.getDeltaMovement();
        this.setTimeUntilShoot(this.getTimeUntilShoot() - 1);
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / Mth.PI));
            this.setXRot((float)(Mth.atan2(vec3.y, d0) * 180.0F / Mth.PI));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        if (this.getTimeUntilShoot() == 0 && this.getOwner() instanceof LivingEntity entity) {
            this.playSound(TASoundEvents.CRYSTALLINE_SWORD_SHOOT.get());
            this.setRotate(!this.isRotate());
            switch (this.getSwordType()) {
                case 0: this.shootFromRotation(entity, this.fixedXRot, this.fixedYRot, 0.0F, 1.5F, 0.0F); break;
                case 1: this.addDeltaMovement(new Vec3(this.type1Vec3X, this.type1Vec3Y, this.type1Vec3Z)); break;
            }
        }

        BlockPos blockPos = this.blockPosition();
        BlockState blockState = this.level().getBlockState(blockPos);
        if (!blockState.isAir()) {
            VoxelShape voxelShape = blockState.getCollisionShape(this.level(), blockPos);
            if (!voxelShape.isEmpty()) {
                Vec3 vec31 = this.position();
                for (AABB aabb : voxelShape.toAabbs()) {
                    if (aabb.move(blockPos).contains(vec31)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.inGround) {
            this.explosion();
        } else {
            this.inGroundTime = 0;
            Vec3 vec32 = this.position();
            Vec3 vec33 = vec32.add(vec3);
            HitResult hitResult = this.level().clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (hitResult.getType() != HitResult.Type.MISS) {
                vec33 = hitResult.getLocation();
            }

            while (!this.isRemoved()) {
                EntityHitResult entityHitResult = this.findHitEntity(vec32, vec33);
                if (entityHitResult != null) {
                    hitResult = entityHitResult;
                }

                if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
                    this.hasImpulse = true;
                    if (this.hitTargetOrDeflectSelf(hitResult) != ProjectileDeflection.NONE) {
                        break;
                    }
                }

                if (entityHitResult == null || this.getPierceLevel() <= 0) {
                    break;
                }

                hitResult = null;
            }

            vec3 = this.getDeltaMovement();
            double d5 = vec3.x;
            double d6 = vec3.y;
            double d1 = vec3.z;
            if (this.getTimeUntilShoot() < 0) {
                for (int i = 0; i < 10; i++) {
                    this.level().addParticle(TAParticleTypes.MAGIC_PURPLE.get(),
                            this.getRandomX(0.5F), this.getRandomY(), this.getRandomZ(0.5F),
                            0.0D, 0.0D, 0.0D);
                }
            }

            double d7 = this.getX() + d5;
            double d2 = this.getY() + d6;
            double d3 = this.getZ() + d1;
            double d4 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(d5, d1) * 180.0F / Mth.PI));
            this.setXRot((float)(Mth.atan2(d6, d4) * 180.0F / Mth.PI));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
            this.setPos(d7, d2, d3);
            this.checkInsideBlocks();
        }
    }

    public void makeParticlesTo(Entity entity) {
        if (this.level().isClientSide) {
            Vec3 vec3 = this.position();
            double sx = vec3.x + 0.5D;
            double sy = vec3.y + 2.0D;
            double sz = vec3.z + 0.5D;
            double dx = sx - entity.getX();
            double dy = sy - entity.getY() - entity.getEyeHeight();
            double dz = sz - entity.getZ();
            for (int i = 0; i < 5; i++) {
                this.level().addParticle(TAParticleTypes.MAGIC_PURPLE.get(), sx, sy, sz, -dx, -dy, -dz);
            }
        }
    }

    public boolean isRotate() {
        return this.entityData.get(ROTATE);
    }

    public void setRotate(boolean value) {
        this.entityData.set(ROTATE, value);
    }

    public int getSwordType() {
        return this.entityData.get(SWORD_TYPE);
    }

    public void setSwordType(int type) {
        this.entityData.set(SWORD_TYPE, type);
    }

    public int getTimeUntilShoot() {
        return this.entityData.get(TIME_UNTIL_SHOOT);
    }

    public void setTimeUntilShoot(int time) {
        this.entityData.set(TIME_UNTIL_SHOOT, time);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("FixedXRot", this.fixedXRot);
        compound.putFloat("FixedYRot", this.fixedYRot);
        compound.putDouble("Type1Vec3X", this.type1Vec3X);
        compound.putDouble("Type1Vec3Y", this.type1Vec3Y);
        compound.putDouble("Type1Vec3Z", this.type1Vec3Z);
        compound.putBoolean("Rotate", this.isRotate());
        compound.putInt("SwordType", this.getSwordType());
        compound.putInt("TimeUntilShoot", this.getTimeUntilShoot());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.fixedXRot = compound.getFloat("FixedXRot");
        this.fixedYRot = compound.getFloat("FixedYRot");
        this.type1Vec3X = compound.getDouble("Type1Vec3X");
        this.type1Vec3Y = compound.getDouble("Type1Vec3Y");
        this.type1Vec3Z = compound.getDouble("Type1Vec3Z");
        this.setRotate(compound.getBoolean("Rotate"));
        this.setSwordType(compound.getInt("SwordType"));
        this.setTimeUntilShoot(compound.getInt("TimeUntilShoot"));
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.STONE);
    }

    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        this.explosion();
    }

    private void explosion() {
        Level level = this.level();
        if (!level.isClientSide()) {
            Entity source = this.getOwner() != null ? this.getOwner() : this;
            level.explode(source, this.getX(), this.getY(), this.getZ(), 4.0F, Level.ExplosionInteraction.NONE);
            this.discard();
        }
    }

}