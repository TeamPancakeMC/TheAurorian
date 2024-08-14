package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.entities.monster.Spirit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SpiritRandomMoveGoal extends Goal {

    private final Spirit spirit;

    public SpiritRandomMoveGoal(Spirit spirit) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.spirit = spirit;
    }

    @Override
    public boolean canUse() {
        return !this.spirit.getMoveControl().hasWanted() && this.spirit.getRandom().nextInt(reducedTickDelay(7)) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void tick() {
        BlockPos blockPos = this.spirit.blockPosition();
        for (int i = 0; i < 3; ++i) {
            int dx = this.spirit.getRandom().nextInt(15) - 7;
            int dy = this.spirit.getRandom().nextInt(11) - 5;
            int dz = this.spirit.getRandom().nextInt(15) - 7;
            BlockPos offset = blockPos.offset(dx, dy, dz);
            if (this.spirit.level().isEmptyBlock(offset)) {
                double wx = (double)offset.getX() + 0.5D;
                double wy = (double)offset.getY() + 0.5D;
                double wz = (double)offset.getZ() + 0.5D;
                this.spirit.getMoveControl().setWantedPosition(wx, wy, wz, 0.25D);
                if (this.spirit.getTarget() == null) {
                    double lx = (double)offset.getX() + 0.5D;
                    double ly = (double)offset.getY() + 0.5D;
                    double lz = (double)offset.getZ() + 0.5D;
                    this.spirit.getLookControl().setLookAt(lx, ly, lz, 180.0F, 20.0F);
                }
                break;
            }
        }
    }
    
}