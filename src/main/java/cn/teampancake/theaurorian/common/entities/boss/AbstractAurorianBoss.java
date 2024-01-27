package cn.teampancake.theaurorian.common.entities.boss;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HoneyBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

abstract class AbstractAurorianBoss extends Monster {

    private static final EntityDataAccessor<Float> BOSS_HEALTH = SynchedEntityData.defineId(AbstractAurorianBoss.class, EntityDataSerializers.FLOAT);

    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(),
            BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);

    protected AbstractAurorianBoss(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setBossHealth(this.getMaxHealth());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BOSS_HEALTH, 1.0F);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
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
    public void setHealth(float health) {}

    private void setBossHealth(float health) {
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

    private void makePoofParticles() {
        for (int i = 0; i < 20; ++i) {
            double x = this.getRandomX(1.0D);
            double z = this.getRandomZ(1.0D);
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(ParticleTypes.POOF, x, this.getRandomY(), z, d0, d1, d2);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Health", 99)) {
            this.setBossHealth(compound.getFloat("Health"));
        }
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float amount) {
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

            if (f != 0.0F && f < this.getMaxHealth()) {
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
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    public boolean canBeLeashed(Player player) {
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

}