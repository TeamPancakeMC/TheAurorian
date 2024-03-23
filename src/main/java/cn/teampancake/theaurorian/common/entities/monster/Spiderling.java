package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class Spiderling extends TASpiderling implements NeutralMob, IAffectedByNightmareMode {

    private static final UniformInt ALERT_INTERVAL = TimeUtil.rangeOfSeconds(4, 6);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int ticksUntilNextAlert;
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;

    public Spiderling(EntityType<? extends Spiderling> type, Level level) {
        super(type, level);
        this.xpReward = 10;
    }

    @Nullable @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        return this.finalizeSpawn(this, level, spawnData);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Spider.createAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));
        controllers.add(new AnimationController<>(this, "bite_controller", state -> PlayState.STOP)
                .triggerableAnim("bite_animation", DefaultAnimations.ATTACK_BITE).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public static boolean checkSpawnRules(EntityType<Spiderling> spiderling, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(TABlocks.DARK_STONE_BRICKS.get()) && checkAnyLightMonsterSpawnRules(spiderling, level, spawnType, pos, random);
    }

    @Override
    protected void customServerAiStep() {
        this.updatePersistentAnger((ServerLevel)this.level(), Boolean.TRUE);
        if (this.getTarget() != null) {
            if (this.ticksUntilNextAlert > 0) {
                --this.ticksUntilNextAlert;
            } else {
                if (this.getSensing().hasLineOfSight(this.getTarget())) {
                    AABB aabb = AABB.unitCubeFromLowerCorner(this.position()).inflate(20.0D, 10.0D, 20.0D);
                    this.level().getEntitiesOfClass(Spiderling.class, aabb, EntitySelector.NO_SPECTATORS).stream()
                            .filter(entity -> entity != this && entity.getTarget() == null)
                            .filter(entity -> !entity.isAlliedTo(this.getTarget()))
                            .forEach(entity -> entity.setTarget(this.getTarget()));
                }

                this.ticksUntilNextAlert = ALERT_INTERVAL.sample(this.random);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.level().broadcastEntityEvent(this, (byte) 4);
        if (super.doHurtTarget(entity)) {
            Level level = this.level();
            if (entity instanceof LivingEntity livingEntity && this.random.nextInt(4) == 0) {
                BlockPos pos = livingEntity.blockPosition();
                if (level.getBlockState(pos).canBeReplaced()) {
                    level.setBlockAndUpdate(pos, Blocks.COBWEB.defaultBlockState());
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (this.getTarget() == null && target == null) {
            this.ticksUntilNextAlert = ALERT_INTERVAL.sample(this.random);
        }

        if (target instanceof Player player) {
            this.setLastHurtByPlayer(player);
        }

        super.setTarget(target);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return 0.45F;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.remainingPersistentAngerTime = time;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    @Nullable @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

}