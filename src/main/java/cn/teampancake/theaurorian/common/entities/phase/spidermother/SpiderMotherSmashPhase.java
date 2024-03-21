package cn.teampancake.theaurorian.common.entities.phase.spidermother;

import cn.teampancake.theaurorian.common.blocks.entity.TrapHoleRestorerBlockEntity;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("deprecation")
public class SpiderMotherSmashPhase extends AttackPhase<SpiderMother> {

    public SpiderMotherSmashPhase() {
        super(2, 1, 20, 200);
    }

    @Override
    public boolean canStart(SpiderMother entity, boolean coolDownOver) {
        return coolDownOver && entity.getTarget() != null;
    }

    @Override
    public void onStart(SpiderMother entity) {
        entity.triggerAnim("smash_controller", "smash_animation");
    }

    @Override
    public void tick(SpiderMother entity) {
        LivingEntity target = entity.getTarget();
        if (entity.getAttackTicks() == 10 && target != null) {
            entity.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
            Level level = target.level();
            BlockPos pos = target.getOnPos();
            int px = pos.getX();
            int py = pos.getY();
            int pz = pos.getZ();
            int j = target.jumping ? 3 : 2;
            if (level.canSeeSky(pos.above())) {
                for (int x = -2; x <= 2; x++) {
                    for (int y = -j; y <= 2; y++) {
                        for (int z = -2; z <= 2; z++) {
                            Block block = TABlocks.TRAP_HOLE_RESTORER.get();
                            BlockPos tempPos = new BlockPos(px + x, py + y, pz + z);
                            BlockState originalState = level.getBlockState(tempPos);
                            if (originalState.isSolid() && !originalState.is(block)) {
                                level.destroyBlock(tempPos, Boolean.FALSE);
                                level.setBlockAndUpdate(tempPos, block.defaultBlockState());
                                if (level.getBlockEntity(tempPos) instanceof TrapHoleRestorerBlockEntity blockEntity) {
                                    blockEntity.restoreCountdown = level.random.nextInt(100) + 100;
                                    blockEntity.transferCountdown = j * 10;
                                    blockEntity.storedState = originalState;
                                }
                            }
                        }
                    }
                }
            } else {
                int ty = level.getMaxBuildHeight() - py;
                BlockPos targetPos = BlockPos.ZERO;
                BlockState state = Blocks.AIR.defaultBlockState();
                for (int i = 0; i < ty; i++) {
                    BlockPos tempPos = pos.above(i);
                    BlockState tempState = level.getBlockState(tempPos);
                    BlockState underState = level.getBlockState(tempPos.below());
                    if (tempState.isSolid() && underState.isAir()) {
                        targetPos = tempPos;
                        state = tempState;
                        break;
                    }
                }

                if (!state.isAir() && targetPos != BlockPos.ZERO) {
                    FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, targetPos, state);
                    Vec3 vec3 = fallingBlock.getDeltaMovement();
                    fallingBlock.setDeltaMovement(vec3.multiply(0.0D, -0.2D, 0.0D));
                    fallingBlock.setHurtsEntities((2.0F), (40));
                    fallingBlock.disableDrop();
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