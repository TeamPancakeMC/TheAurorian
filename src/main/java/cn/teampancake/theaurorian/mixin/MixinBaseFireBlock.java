package cn.teampancake.theaurorian.mixin;

import cn.teampancake.theaurorian.common.level.portal.AurorianPortalShape;
import cn.teampancake.theaurorian.registry.TADimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(BaseFireBlock.class)
public class MixinBaseFireBlock {

    @Inject(method = "onPlace", at = @At(value = "HEAD"))
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving, CallbackInfo ci) {
        if (!oldState.is(state.getBlock())) {
            if (inAurorianPortalDimension(level)) {
                Optional<AurorianPortalShape> optional = AurorianPortalShape.findAurorianEmptyPortalShape(level, pos, Direction.Axis.X);
                optional.ifPresent(AurorianPortalShape::createPortalBlocks);
            }
            if (!state.canSurvive(level, pos)) {
                level.removeBlock(pos, false);
            }
        }
    }

    @Inject(method = "isPortal", at = @At(value = "HEAD"), cancellable = true)
    private static void isPortal(Level level, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (!inAurorianPortalDimension(level)) {
            cir.setReturnValue(false);
        } else {
            BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
            boolean flag = false;
            for (Direction direction1 : Direction.values()) {
                if (level.getBlockState(mutableBlockPos.set(pos).move(direction1)).isPortalFrame(level, mutableBlockPos)) {
                    flag = true; break;
                }
            }

            if (!flag) {
                cir.setReturnValue(false);
            } else {
                Direction.Axis axis = direction.getAxis().isHorizontal() ? direction.getCounterClockWise().getAxis() :
                        Direction.Plane.HORIZONTAL.getRandomAxis(level.random);
                cir.setReturnValue(AurorianPortalShape.findAurorianEmptyPortalShape(level, pos, axis).isPresent());
            }
        }
    }

    private static boolean inAurorianPortalDimension(Level level) {
        return level.dimension() == Level.OVERWORLD || level.dimension() == TADimensions.AURORIAN_DIMENSION;
    }

}