package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.blocks.HidesNeighborFaceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "shouldRenderFace", at = @At(value = "HEAD"), cancellable = true)
    private static void shouldRenderFace(BlockState state, BlockGetter level, BlockPos offset, Direction face, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState sideState = level.getBlockState(pos);
        if (state.getBlock() instanceof HidesNeighborFaceBlock block && sideState.canOcclude()) {
            cir.setReturnValue(block.shouldRenderFace(level, pos, state, sideState, face));
        }
    }

}