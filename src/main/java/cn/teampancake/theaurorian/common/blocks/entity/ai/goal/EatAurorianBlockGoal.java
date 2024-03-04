package cn.teampancake.theaurorian.common.blocks.entity.ai.goal;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.EnumSet;
import java.util.function.Predicate;

public class EatAurorianBlockGoal extends Goal {
    private static final int EAT_ANIMATION_TICKS = 40;
    private static final Predicate<BlockState> IS_TALL_GRASS;
    private final Mob mob;
    private final Level level;
    private int eatAnimationTick;

    public EatAurorianBlockGoal(Mob mob) {
        this.mob=mob;
        this.level=mob.level();
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = this.mob.blockPosition();
            return IS_TALL_GRASS.test(this.level.getBlockState(blockpos)) || this.level.getBlockState(blockpos.below()).is(TABlocks.AURORIAN_GRASS_BLOCK.get());
        }
    }

    public void start() {
        this.eatAnimationTick = this.adjustedTickDelay(40);
        this.level.broadcastEntityEvent(this.mob, (byte)10);
        this.mob.getNavigation().stop();
    }

    public void stop() {
        this.eatAnimationTick = 0;
    }

    public boolean canContinueToUse() {
        return this.eatAnimationTick > 0;
    }

    public int getEatAnimationTick() {
        return this.eatAnimationTick;
    }

    public void tick() {
        this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        if (this.eatAnimationTick == this.adjustedTickDelay(4)) {
            BlockPos blockpos = this.mob.blockPosition();
            if (IS_TALL_GRASS.test(this.level.getBlockState(blockpos))) {
                if (ForgeEventFactory.getMobGriefingEvent(this.level, this.mob)) {
                    this.level.destroyBlock(blockpos, false);
                }

                this.mob.ate();
            } else {
                BlockPos blockpos1 = blockpos.below();
                if (this.level.getBlockState(blockpos1).is(Blocks.GRASS_BLOCK)) {
                    if (ForgeEventFactory.getMobGriefingEvent(this.level, this.mob)) {
                        this.level.levelEvent(2001, blockpos1, Block.getId(TABlocks.AURORIAN_GRASS_BLOCK.get().defaultBlockState()));
                        this.level.setBlock(blockpos1, TABlocks.AURORIAN_DIRT.get().defaultBlockState(), 2);
                    }

                    this.mob.ate();
                }
            }
        }

    }


    static {
        IS_TALL_GRASS = BlockStatePredicate.forBlock(TABlocks.AURORIAN_GRASS.get())
                .or(BlockStatePredicate.forBlock(TABlocks.WICK_GRASS.get()))
                .or(BlockStatePredicate.forBlock(TABlocks.AURORIAN_GRASS_LIGHT.get()));
    }
}
