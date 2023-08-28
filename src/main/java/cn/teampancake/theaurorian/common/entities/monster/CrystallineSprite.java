package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.config.AurorianConfig;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
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
        this.setSilent(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 0.85F, 40, 40.0F));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static boolean checkCrystallineSpriteRules(EntityType<CrystallineSprite> crystallineSprite, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(ModBlocks.MOON_TEMPLE_BRICK.get()) && checkAnyLightMonsterSpawnRules(crystallineSprite, level, spawnType, pos, random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        boolean nightmareMode = AurorianConfig.CONFIG_NIGHTMARE_MODE.get();
        double multiplier = AurorianConfig.CONFIG_NIGHTMARE_MODE_MULTIPLIER.get();
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, nightmareMode ? 40.0F * multiplier : 20.0F);
        builder.add(Attributes.ATTACK_DAMAGE, nightmareMode ? 12.0F * multiplier : 6.0F);
        builder.add(Attributes.MOVEMENT_SPEED, 0.23000000417232513D);
        builder.add(Attributes.FOLLOW_RANGE, 20.0F);
        return builder;
    }

    @Override
    public double getEyeY() {
        return this.getBbHeight() * 0.5F;
    }

    @Override
    public void aiStep() {
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }
        super.aiStep();
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
        super.customServerAiStep();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        Arrow arrow = ModEntityTypes.CRYSTALLINE_BEAM.get().create(this.level());
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

}