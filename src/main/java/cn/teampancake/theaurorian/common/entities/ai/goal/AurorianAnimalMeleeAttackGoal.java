package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.entities.animal.IOverworldAurorianAnimal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class AurorianAnimalMeleeAttackGoal extends MeleeAttackGoal {

    public AurorianAnimalMeleeAttackGoal(PathfinderMob mob) {
        super(mob, 1.5D, Boolean.TRUE);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.isSpawnInOverworld();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.isSpawnInOverworld();
    }

    private boolean isSpawnInOverworld() {
        return this.mob instanceof IOverworldAurorianAnimal aurorianAnimal && aurorianAnimal.isSpawnInOverworld();
    }

}