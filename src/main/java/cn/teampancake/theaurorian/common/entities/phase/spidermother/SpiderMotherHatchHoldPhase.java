package cn.teampancake.theaurorian.common.entities.phase.spidermother;

import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import net.minecraft.world.entity.Mob;
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
        if (entity.getAttackTicks() % 20 == 0) {
            if (entity.getRandomSpiderlings() instanceof Mob mob) {
                mob.setPos(entity.position());
                mob.setTarget(entity.getTarget());
                level.addFreshEntity(mob);
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