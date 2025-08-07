package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.items.armor.SpectralArmor;
import cn.teampancake.theaurorian.common.utils.TAInventoryUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class SpiderIgnoreSpectralArmorGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    public SpiderIgnoreSpectralArmorGoal(Mob mob, Class<T> targetType) {
        super(mob, targetType, true);
    }

    @Override
    public boolean canUse() {
        boolean flag = false;
        if (this.target != null && this.target instanceof Player) {
            flag = TAInventoryUtils.isWearFullArmor(this.target, SpectralArmor.class);
        }

        return !flag && super.canUse();
    }

}