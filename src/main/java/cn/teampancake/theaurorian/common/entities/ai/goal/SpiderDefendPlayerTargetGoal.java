package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.entities.monster.TASpider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public class SpiderDefendPlayerTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {

    private static final Predicate<Entity> TRUSTED_TARGET_SELECTOR = entity -> entity instanceof LivingEntity livingEntity
            && livingEntity.getLastHurtMob() != null && livingEntity.getLastHurtMobTimestamp() < livingEntity.tickCount + 600;

    @Nullable
    private LivingEntity trustedLastHurtBy;
    @Nullable
    private LivingEntity trustedLastHurt;
    private int timestamp;
    private final TASpider spider;

    public SpiderDefendPlayerTargetGoal(TASpider mob, Class<LivingEntity> targetType, boolean mustSee) {
        super(mob, targetType, mustSee, TRUSTED_TARGET_SELECTOR::test);
        this.spider = mob;
    }

    @Override
    public boolean canUse() {
        if (this.randomInterval <= 0 || this.mob.getRandom().nextInt(this.randomInterval) == 0) {
            UUID uuid = this.spider.getOwnerUUID();
            if (uuid != null && this.spider.level() instanceof ServerLevel serverLevel) {
                Entity entity = serverLevel.getEntity(uuid);
                if (entity instanceof LivingEntity livingEntity) {
                    this.trustedLastHurt = livingEntity;
                    this.trustedLastHurtBy = livingEntity.getLastHurtByMob();
                    int i = livingEntity.getLastHurtByMobTimestamp();
                    return i != this.timestamp && this.canAttack(this.trustedLastHurtBy, this.targetConditions);
                }
            }
        }

        return false;
    }

    @Override
    public void start() {
        this.setTarget(this.trustedLastHurtBy);
        this.target = this.trustedLastHurtBy;
        if (this.trustedLastHurt != null) {
            this.timestamp = this.trustedLastHurt.getLastHurtByMobTimestamp();
        }

        super.start();
    }

}