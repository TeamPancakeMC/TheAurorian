package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class CrystallineSprite extends Monster implements RangedAttackMob {

    private float allowedHeightOffset = 0.5F;
    private int nextHeightOffsetChangeTick;

    public CrystallineSprite(EntityType<? extends CrystallineSprite> type, Level level) {
        super(type, level);
        this.xpReward = 25;
        this.setSilent(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (1.0D), Boolean.FALSE));
        this.goalSelector.addGoal(2, new RangedAttackGoal((this), (0.85F), (40), (40.0F)));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.TRUE));
    }

    public static boolean checkCrystallineSpriteRules(EntityType<CrystallineSprite> crystallineSprite, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(TABlocks.MOON_TEMPLE_BRICKS.get()) && checkAnyLightMonsterSpawnRules(crystallineSprite, level, spawnType, pos, random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, 20.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 2.0D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.25D);
        builder.add(Attributes.FOLLOW_RANGE, 20.0D);
        return builder;
    }

    @Override
    public double getEyeY() {
        return this.getBbHeight() * 0.5F;
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        Level.ExplosionInteraction type = Level.ExplosionInteraction.MOB;
        if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0F, type);
            this.level().broadcastEntityEvent(this, (byte)60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }
    }

    @Override
    protected void customServerAiStep() {
        --this.nextHeightOffsetChangeTick;
        if (this.nextHeightOffsetChangeTick <= 0) {
            this.nextHeightOffsetChangeTick = 100;
            this.allowedHeightOffset = (float) this.random.triangle(0.5D, 6.891D);
        }

        LivingEntity target = this.getTarget();
        if (target != null && target.getEyeY() > this.getEyeY() + (double)this.allowedHeightOffset && this.canAttack(target)) {
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.add(0.0D, ((double)0.3F - vec3.y) * (double)0.3F, 0.0D));
            this.hasImpulse = true;
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        Arrow arrow = TAEntityTypes.CRYSTALLINE_BEAM.get().create(this.level());
        if (arrow != null) {
            double d0 = target.getX() - this.getX();
            double d2 = target.getZ() - this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            double d1 = target.getBoundingBox().minY + target.getBbHeight() / 3.0F - arrow.getY();
            float pitch = 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F);
            arrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, 9F);
            this.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0F, pitch);
            this.level().addFreshEntity(arrow);
        }
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 5 * AurorianConfig.CONFIG_MOON_TEMPLE_MOB_DENSITY.get();
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        return false;
    }

}