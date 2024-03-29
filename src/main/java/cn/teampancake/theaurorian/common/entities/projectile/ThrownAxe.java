package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkHooks;

import java.util.UUID;

@SuppressWarnings("NotNullFieldNotInitialized")
public class ThrownAxe extends ThrowableItemProjectile {

    private static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(ThrownAxe.class, EntityDataSerializers.INT);
    private UUID ownerUUID;
    private Player owner;
    private int slot;
    private float damage;
    private int returnAge = 8;
    private boolean returning;

    public ThrownAxe(EntityType<? extends ThrownAxe> type, Level level) {
        super(type, level);
        this.noPhysics = false;
    }

    public ThrownAxe(Level level, LivingEntity shooter) {
        super(TAEntityTypes.THROWN_AXE.get(), shooter, level);
    }

    public Player getAxeOwner() {
        if (this.owner == null) {
            Level level = this.level();
            if (level instanceof ServerLevel serverLevel && !serverLevel.isClientSide) {
                this.owner = (Player) serverLevel.getEntity(this.ownerUUID);
            }
        }
        
        return this.owner;
    }

    public void setData(float damage, UUID ownerUUID, int slot) {
        this.damage = damage;
        this.ownerUUID = ownerUUID;
        this.slot = slot;
    }

    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float innacuracy) {
        float f = -Mth.sin(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((rotationPitch + pitchOffset) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, innacuracy);
        Vec3 vec3 = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, 0, vec3.z));
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.returning = true;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        DamageSource source = this.damageSources().mobAttack(this.getAxeOwner());
        if (!this.level().isClientSide && !entity.equals(this.owner)) {
            if (entity instanceof LivingEntity livingEntity && entity.hurt(source, this.damage)) {
                this.getItem().hurtAndBreak(1, this.getAxeOwner(), (e) -> remove(RemovalReason.KILLED));
                EnchantmentHelper.EnchantmentVisitor visitor = (enchantment, level) -> enchantment.doPostAttack(this.owner, livingEntity, level);
                EnchantmentHelper.runIterationOnInventory(visitor, this.owner.getAllSlots());
                EnchantmentHelper.runIterationOnItem(visitor, this.getItem());
                int i = this.getItem().getEnchantmentLevel(Enchantments.FIRE_ASPECT);
                if (i > 0) {
                    livingEntity.setSecondsOnFire(i * 4);
                }
            }

            this.returnAge += 4;
        }
    }

    public int getAge() {
        return this.entityData.get(AGE);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AGE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.ownerUUID != null) {
            compound.putUUID("ownerUUID", this.ownerUUID);
        }
        compound.putInt("slot", this.slot);
        compound.putFloat("damage", this.damage);
        compound.putBoolean("returning", this.returning);
        compound.putInt("returnAge", this.returnAge);
        compound.putInt("age", this.getAge());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("ownerUUID")) {
            this.ownerUUID = compound.getUUID("ownerUUID");
            this.owner = this.getAxeOwner();
        }

        this.slot = compound.getInt("slot");
        this.damage = compound.getFloat("damage");
        this.returning = compound.getBoolean("returning");
        this.returnAge = compound.getInt("returnAge");
        this.entityData.set(AGE, compound.getInt("age"));
    }

    @Override
    public void tick() {
        super.tick();
        this.entityData.set(AGE, this.getAge() + 1);
        Level level = this.level();
        if (level.isClientSide && !this.isInWaterRainOrBubble()) {
            if (this.getItem().getEnchantmentLevel(Enchantments.FIRE_ASPECT) > 0) {
                Vec3 vector = new Vec3(getRandomX(0.7), this.getRandomY(), this.getRandomZ(0.7));
                if (this.getItem().getItem() instanceof AxeItem) {
                    float rotation = this.random.nextFloat();
                    double x = Math.cos(this.getAge() + rotation * 2 - 1) * 0.8F;
                    double z = Math.sin(this.getAge() + rotation * 2 - 1) * 0.8F;
                    vector = new Vec3(Math.cos(this.getAge()) * 0.8f + this.getX(), this.getY(0.1), Math.sin(this.getAge()) * 0.8f + this.getZ());
                    level.addParticle(ParticleTypes.FLAME, x + this.getX(), vector.y, z + this.getZ(), 0, 0, 0);
                    level.addParticle(ParticleTypes.FLAME, x + this.getX(), vector.y, z + this.getZ(), 0, 0, 0);
                }

                level.addParticle(ParticleTypes.FLAME, vector.x, vector.y, vector.z, 0, 0, 0);
            }
        }

        if (!level.isClientSide) {
            Player playerEntity = this.getAxeOwner();
            if (playerEntity == null || !playerEntity.isAlive()) {
                ItemEntity itemEntity = new ItemEntity(level, this.getX(), this.getY() + 0.5, this.getZ(), this.getItem());
                itemEntity.setPickUpDelay(40);
                itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().multiply(0, 1, 0));
                level.addFreshEntity(itemEntity);
                this.discard();
                return;
            }

            if (this.getAge() % 3 == 0) {
                level.playSound(null, blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1, 1.25f);
            }

            if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
                Vec3 vector3d = this.getDeltaMovement();
                setYRot((float) (Mth.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI)));
                yRotO = getYRot();
                xRotO = getXRot();
            }

            if (this.getAge() > this.returnAge) {
                this.returning = true;
            }

            if (this.returning) {
                this.noPhysics = true;
                Vec3 ownerPos = playerEntity.position().add(0, 1, 0);
                Vec3 motion = ownerPos.subtract(position());
                this.setDeltaMovement(motion.normalize().scale(0.75f));
            }

            if (this.getAge() > 8 && this.distanceTo(playerEntity) < 3.0F && this.isAlive()) {
                ItemHandlerHelper.giveItemToPlayer(playerEntity, this.getItem(), this.slot);
                if (!playerEntity.isCreative()) {
                    int cooldown = 100 - 25 * (this.getItem().getEnchantmentLevel(TAEnchantments.ROUNDABOUT_THROW.get()) - 1);
                    playerEntity.getCooldowns().addCooldown(this.getItem().getItem(), cooldown);
                }

                this.discard();
            }
        }
    }

    @Override
    public float getPickRadius() {
        return 4.0f;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.IRON_AXE;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}