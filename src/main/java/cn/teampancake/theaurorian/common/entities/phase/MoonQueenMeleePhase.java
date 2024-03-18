package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class MoonQueenMeleePhase extends AttackPhase<MoonQueen> {

    private final String[] parts = new String[] {"swing", "swing_2", "burst"};
    private final int[] hurtTargetTime = new int[] {10, 7, 8};
    private int randomIndex = -1;

    public MoonQueenMeleePhase() {
        super(1, 1, 20, 0);
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return entity.preparationTime <= 0 && entity.isAlive() && TAEntityUtils.canReachTarget(entity, 2.0D);
    }

    @Override
    public void onStart(MoonQueen entity) {
        this.randomIndex = RandomSource.create().nextInt(3);
        String part = this.parts[this.randomIndex];
        String controller = part + "_controller";
        String animation = part + "_animation";
        entity.triggerAnim(controller, animation);
        LivingEntity target = entity.getTarget();
        entity.setSprinting(false);
        if (target != null) {
            entity.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
            entity.setAttackYRot(entity.getYRot());
        }
    }

    @Override
    public void tick(MoonQueen entity) {
        int i = this.randomIndex;
        Level level = entity.level();
        LivingEntity target = entity.getTarget();
        if (i >= 0 && target != null && entity.getAttackTicks() == this.hurtTargetTime[i]) {
            AABB selfInflate = entity.getBoundingBox().inflate(2.0D);
            AABB targetInflate = target.getBoundingBox().inflate(1.5D);
            float value = entity.getYRot() * ((float) Math.PI / 180F);
            for (LivingEntity livingEntity : level.getNearbyEntities(LivingEntity.class,
                    TargetingConditions.DEFAULT, entity, selfInflate)) {
                if (livingEntity.getUUID().equals(target.getUUID())) {
                    livingEntity.invulnerableTime = 0;
                    entity.doHurtTarget(livingEntity);
                    if (i == 2) {
                        livingEntity.hurt(entity.damageSources().mobAttack(entity), 6.0F);
                        livingEntity.knockback(1.0F, Mth.sin(value), -Mth.cos(value));
                    }
                }
            }

            if (i == 0 || i == 1) {
                for (LivingEntity livingEntity : level.getEntitiesOfClass(LivingEntity.class, targetInflate)) {
                    double distance = entity.distanceToSqr(livingEntity);
                    if (livingEntity != entity && livingEntity != target && distance < 8.0D) {
                        livingEntity.knockback(0.4F, Mth.sin(value), -Mth.cos(value));
                        entity.doHurtTarget(livingEntity);
                    }
                }
            }
        }
    }

    @Override
    public boolean canContinue(MoonQueen entity) {
        return true;
    }

    @Override
    public void onStop(MoonQueen entity) {

    }

}