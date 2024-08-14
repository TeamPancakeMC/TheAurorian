package cn.teampancake.theaurorian.common.entities.phase.spidermother;

import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SpiderMotherEidolonPhase extends AttackPhase<SpiderMother> {

    public SpiderMotherEidolonPhase() {
        super(4, 1, 30, 600);
    }

    @Override
    public boolean canStart(SpiderMother entity, boolean coolDownOver) {
        return coolDownOver && entity.getTarget() != null;
    }

    @Override
    public void onStart(SpiderMother entity) {
        entity.triggerAnim("eidolon_controller", "eidolon_animation");
    }

    @Override
    public void tick(SpiderMother entity) {
        if (entity.getAttackTicks() == 10) {
            Level level = entity.level();
            AABB aabb = entity.getBoundingBox();
            for (LivingEntity livingEntity : level.getNearbyEntities(LivingEntity.class,
                    TargetingConditions.DEFAULT, entity, aabb.inflate(8.0D))) {
                if (livingEntity instanceof Player player) {
                    player.addEffect(new MobEffectInstance(TAMobEffects.EIDOLON_POISON, 100));
                }
            }

            for (LivingEntity livingEntity : level.getNearbyEntities(LivingEntity.class,
                    TargetingConditions.DEFAULT, entity, aabb.inflate(4.0D))) {
                if (livingEntity instanceof Player player) {
                    player.addEffect(new MobEffectInstance(TAMobEffects.CRYSTALLIZATION, 200));
                }
            }
        }
    }

    @Override
    public boolean canContinue(SpiderMother entity) {
        return true;
    }

    @Override
    public void onStop(SpiderMother entity) {

    }

}