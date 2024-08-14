package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoEntity;

import javax.annotation.Nullable;
import java.util.List;

public abstract class TASpider extends Spider implements GeoEntity, MultiPhaseAttacker {

    protected static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(TASpider.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(TASpider.class, EntityDataSerializers.INT);
    protected AttackManager<?> attackManager = new AttackManager<>(this, List.of());

    public TASpider(EntityType<? extends TASpider> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACK_STATE, 0);
        builder.define(ATTACK_TICKS, 0);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance potionEffect) {
        MobEffect effect = potionEffect.getEffect().value();
        boolean flag1 = effect == MobEffects.POISON;
        boolean flag2 = effect == MobEffects.WITHER;
        boolean flag3 = effect == TAMobEffects.CRYSTALLIZATION.get();
        return flag1 || flag2 || flag3 || super.canBeAffected(potionEffect);
    }

    @Override
    protected void customServerAiStep() {
        this.attackManager.tick();
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float damageAmount) {
        if (!this.isInvulnerableTo(damageSource)) {
            SpiderlingCrystalShell crystalShell = this.getNearestCrystalShell();
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

            if (!this.isCrystalShell() && crystalShell != null) {
                crystalShell.getCombatTracker().recordDamage(damageSource, f1);
                crystalShell.setHealth(crystalShell.getHealth() - f1);
                crystalShell.setAbsorptionAmount(crystalShell.getAbsorptionAmount() - f1);
                crystalShell.gameEvent(GameEvent.ENTITY_DAMAGE);
                return;
            }

            if (f1 != 0.0F) {
                this.getCombatTracker().recordDamage(damageSource, f1);
                this.setHealth(this.getHealth() - f1);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - f1);
                this.gameEvent(GameEvent.ENTITY_DAMAGE);
            }
        }
    }

    protected boolean isCrystalShell() {
        return false;
    }

    @Nullable
    private SpiderlingCrystalShell getNearestCrystalShell() {
        double d0 = Double.MAX_VALUE;
        SpiderlingCrystalShell crystalShell = null;
        AABB aabb = this.getBoundingBox().inflate(32.0D);
        List<SpiderlingCrystalShell> list = this.level().getEntitiesOfClass(SpiderlingCrystalShell.class, aabb);
        for (SpiderlingCrystalShell entity : list) {
            double d1 = entity.distanceToSqr(this);
            if (d1 < d0) {
                d0 = d1;
                crystalShell = entity;
            }
        }

        return crystalShell;
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