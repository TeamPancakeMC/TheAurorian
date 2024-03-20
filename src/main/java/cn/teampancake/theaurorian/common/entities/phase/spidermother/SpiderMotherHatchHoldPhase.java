package cn.teampancake.theaurorian.common.entities.phase.spidermother;

import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.monster.Spiderling;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SpiderMotherHatchHoldPhase extends AttackPhase<SpiderMother> {

    public SpiderMotherHatchHoldPhase() {
        super(6, 1, 65, 0, 7);
    }

    @Override
    public boolean canStart(SpiderMother entity, boolean coolDownOver) {
        return false;
    }

    @Override
    public void onStart(SpiderMother entity) {
        entity.triggerAnim("hatch_hold_controller", "hatch_hold_animation");
    }

    @Override
    public void tick(SpiderMother entity) {
        Level level = entity.level();
        LivingEntity target = entity.getTarget();
        if (entity.getAttackTicks() % 20 == 0 && target != null) {
            Spiderling spiderling = new Spiderling(TAEntityTypes.SPIDERLING.get(), level);
            spiderling.setPos(entity.position());
            spiderling.setTarget(target);
            level.addFreshEntity(spiderling);
        }
    }

    @Override
    public boolean canContinue(SpiderMother entity) {
        return false;
    }

    @Override
    public void onStop(SpiderMother entity) {

    }

}