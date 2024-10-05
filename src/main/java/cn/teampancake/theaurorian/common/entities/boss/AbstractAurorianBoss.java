package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.common.entities.monster.MultiPhaseAttacker;
import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.registry.TAAttributes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HoneyBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

abstract class AbstractAurorianBoss extends Monster implements MultiPhaseAttacker {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(AbstractAurorianBoss.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(AbstractAurorianBoss.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> BOSS_HEALTH = SynchedEntityData.defineId(AbstractAurorianBoss.class, EntityDataSerializers.FLOAT);
    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(Objects.requireNonNull(this.getDisplayName()),
            BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false);
    protected AttackManager<?> attackManager = new AttackManager<>(this, List.of());

    protected AbstractAurorianBoss(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setBossHealth(this.getMaxHealth());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACK_STATE, 0);
        builder.define(ATTACK_TICKS, 0);
        builder.define(BOSS_HEALTH, 1.0F);
    }

    @Override
    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    @Override
    public void setAttackState(int attackState) {
        this.entityData.set(ATTACK_STATE, attackState);
    }

    @Override
    public int getAttackTicks() {
        return this.entityData.get(ATTACK_TICKS);
    }

    @Override
    public void setAttackTicks(int attackTicks) {
        this.entityData.set(ATTACK_TICKS, attackTicks);
    }

    @Override
    protected void customServerAiStep() {
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        this.attackManager.tick();
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        this.bossEvent.removePlayer(player);
    }

    @Override
    public void heal(float healAmount) {
        float f = this.getHealth();
        if (f > 0.0F) {
            this.setBossHealth(f + healAmount);
        }
    }

    @Override
    public float getHealth() {
        return this.entityData.get(BOSS_HEALTH);
    }

    @Override
    public float getMaxHealth() {
        return (float) this.getAttributeValue(TAAttributes.MAX_BOSS_HEALTH);
    }

    @Override
    public void setHealth(float health) {}

    public void setBossHealth(float health) {
        this.entityData.set(BOSS_HEALTH, Mth.clamp(health, 0.0F, this.getMaxHealth()));
    }

    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case 3 -> {
                if (this.getDeathSound() != null) {
                    float randomPitch = (this.random.nextFloat() - this.random.nextFloat());
                    this.playSound(this.getDeathSound(), this.getSoundVolume(), randomPitch * 0.2F + 1.0F);
                }
                this.setBossHealth(0.0F);
                this.die(this.damageSources().generic());
            }
            case 29 -> this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + this.level().random.nextFloat() * 0.4F);
            case 30 -> this.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + this.level().random.nextFloat() * 0.4F);
            case 46 -> {
                for (int j = 0; j < 128; ++j) {
                    double d0 = (double) j / 127.0D;
                    float f = (this.random.nextFloat() - 0.5F) * 0.2F;
                    float f1 = (this.random.nextFloat() - 0.5F) * 0.2F;
                    float f2 = (this.random.nextFloat() - 0.5F) * 0.2F;
                    double d1 = Mth.lerp(d0, this.xo, this.getX()) + (this.random.nextDouble() - 0.5D) * (double) this.getBbWidth() * 2.0D;
                    double d2 = Mth.lerp(d0, this.yo, this.getY()) + this.random.nextDouble() * (double) this.getBbHeight();
                    double d3 = Mth.lerp(d0, this.zo, this.getZ()) + (this.random.nextDouble() - 0.5D) * (double) this.getBbWidth() * 2.0D;
                    this.level().addParticle(ParticleTypes.PORTAL, d1, d2, d3, f, f1, f2);
                }
            }
            case 53 -> HoneyBlock.showSlideParticles(this);
            case 54 -> HoneyBlock.showJumpParticles(this);
            case 60 -> this.makePoofParticles();
            default -> {}
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("TABossHealth", this.getHealth());
        if (compound.contains("Health", 99)) {
            compound.remove("Health");
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("TABossHealth", 99)) {
            this.setBossHealth(compound.getFloat("TABossHealth"));
        }

        if (compound.contains("Health", 99)) {
            compound.remove("Health");
        }
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float amount) {
        boolean flag = damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
        if (!this.isInvulnerableTo(damageSource)) {
            amount = this.getDamageAfterArmorAbsorb(damageSource, amount);
            amount = this.getDamageAfterMagicAbsorb(damageSource, amount);
            float f = Math.max(amount - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (amount - f));
            float g = amount - f;
            if (g > 0.0F && g < 3.4028235E37F) {
                if (damageSource.getEntity() instanceof ServerPlayer serverPlayer) {
                    serverPlayer.awardStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(g * 10.0F));
                }
            }

            if (f != 0.0F && (f < this.getMaxHealth() || flag)) {
                this.getCombatTracker().recordDamage(damageSource, f);
                this.setBossHealth(this.getHealth() - f);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - f);
                this.gameEvent(GameEvent.ENTITY_DAMAGE);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!source.isCreativePlayer() && this.getTarget() == null) {
            return false;
        }

        return super.hurt(source, amount);
    }

    @Override
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected boolean canRide(Entity entity) {
        return false;
    }

    @Override
    public boolean startRiding(Entity entity, boolean force) {
        return false;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

}