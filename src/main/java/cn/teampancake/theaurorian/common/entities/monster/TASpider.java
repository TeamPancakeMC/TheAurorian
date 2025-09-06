package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import software.bernie.geckolib.animatable.GeoEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class TASpider extends Spider implements GeoEntity, MultiPhaseAttacker, OwnableEntity {

    protected static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(TASpider.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(TASpider.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(TASpider.class, EntityDataSerializers.OPTIONAL_UUID);
    protected AttackManager<?> attackManager = new AttackManager<>(this, List.of());
    public boolean summonByPlayer;
    public int aliveTime;

    public TASpider(EntityType<? extends TASpider> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACK_STATE, 0);
        builder.define(ATTACK_TICKS, 0);
        builder.define(OWNER_UUID, Optional.empty());
    }

    @Override
    public boolean canBeAffected(MobEffectInstance potionEffect) {
        boolean flag1 = potionEffect.is(MobEffects.POISON);
        boolean flag2 = potionEffect.is(MobEffects.WITHER);
        boolean flag3 = potionEffect.is(TAMobEffects.CRYSTALLIZATION);
        return !flag1 && !flag2 && !flag3 && super.canBeAffected(potionEffect);
    }

    @Override
    protected void customServerAiStep() {
        this.attackManager.tick();
        if (!this.level().isClientSide && this.summonByPlayer && --this.aliveTime < 0) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.discard();
        }
    }

    @Override
    public @Nullable UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("SummonByPlayer", this.summonByPlayer);
        compound.putInt("AliveTime", this.aliveTime);
        if (this.getOwnerUUID() != null) {
            compound.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.summonByPlayer = compound.getBoolean("SummonByPlayer");
        this.aliveTime = compound.getInt("AliveTime");
        UUID uuid = null;
        if (compound.hasUUID("Owner")) {
            uuid = compound.getUUID("Owner");
        } else if (this.getServer() != null) {
            String s = compound.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
            this.setOwnerUUID(uuid);
        }
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float damageAmount) {
        if (!this.isInvulnerableTo(damageSource)) {
            SpiderlingCrystalShell crystalShell = TAEntityUtils.getNearestEntity(
                    this, SpiderlingCrystalShell.class, 32.0D);
            damageAmount = this.getDamageAfterArmorAbsorb(damageSource, damageAmount);
            damageAmount = this.getDamageAfterMagicAbsorb(damageSource, damageAmount);
            float f1 = Math.max(damageAmount - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (damageAmount - f1));
            float f = damageAmount - f1;
            if (f > 0.0F && f < 3.4028235E37F) {
                if (damageSource.getEntity() instanceof ServerPlayer serverplayer) {
                    serverplayer.awardStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(f * 10.0F));
                }
            }

            if (f1 != 0.0F) {
                LivingEntity finalEntity = this;
                if (!this.isCrystalShell() && crystalShell != null) {
                    UUID ownerUUID = crystalShell.getOwnerUUID();
                    if (ownerUUID == null || !this.summonByPlayer) {
                        float health = crystalShell.getHealth();
                        if (health >= f1) {
                            finalEntity = crystalShell;
                        } else {
                            f1 -= health;
                            crystalShell.kill();
                        }
                    }
                }

                if (f1 != 0.0F) {
                    finalEntity.getCombatTracker().recordDamage(damageSource, f1);
                    finalEntity.setHealth(finalEntity.getHealth() - f1);
                    finalEntity.setAbsorptionAmount(finalEntity.getAbsorptionAmount() - f1);
                    finalEntity.gameEvent(GameEvent.ENTITY_DAMAGE);
                }
            }
        }
    }

    protected boolean isCrystalShell() {
        return false;
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

}