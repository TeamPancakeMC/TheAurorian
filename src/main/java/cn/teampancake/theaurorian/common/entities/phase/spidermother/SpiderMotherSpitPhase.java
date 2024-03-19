package cn.teampancake.theaurorian.common.entities.phase.spidermother;

import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.entities.projectile.WebbingEntity;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SpiderMotherSpitPhase extends AttackPhase<SpiderMother> {

    public SpiderMotherSpitPhase() {
        super(3, 3, 14, 300);
    }

    @Override
    public boolean canStart(SpiderMother entity, boolean coolDownOver) {
        return coolDownOver && entity.getTarget() != null;
    }

    @Override
    public void onStart(SpiderMother entity) {
        entity.triggerAnim("spit_controller", "spit_animation");
    }

    @Override
    public void tick(SpiderMother entity) {
        Level level = entity.level();
        LivingEntity target = entity.getTarget();
        if (target != null) {
            entity.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
            if (entity.getAttackTicks() == 5) {
                WebbingEntity webbing = new WebbingEntity(entity, level);
                webbing.setItem(TAItems.WEBBING.get().getDefaultInstance());
                webbing.setNoGravity(true);
                double d0 = target.getX() - entity.getX();
                double d1 = target.getBoundingBox().minY +
                        target.getBbHeight() / 3.0F - webbing.getY();
                double d2 = target.getZ() - entity.getZ();
                double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
                float pitch = 0.8F / (entity.getRandom().nextFloat() * 0.4F + 0.8F);
                webbing.shoot(d0, d1 + d3 * 0.1D, d2, 1F, 0F);
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                        SoundEvents.CAT_HISS, SoundSource.HOSTILE, 0.8F, pitch);
                level.addFreshEntity(webbing);
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