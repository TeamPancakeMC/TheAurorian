package cn.teampancake.theaurorian.common.blocks;

import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HidesNeighborFaceBlock extends Block {

    public HidesNeighborFaceBlock(Properties properties) {
        super(properties);
    }

    public boolean shouldRenderFace(BlockGetter level, BlockPos pos, BlockState state, BlockState sideState, Direction face) {
        Block.BlockStatePairKey statePairKey = new Block.BlockStatePairKey(state, sideState, face);
        Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> hashMap = Block.OCCLUSION_CACHE.get();
        byte b = hashMap.getAndMoveToFirst(statePairKey);
        if (b != 127) return b != 0;
        Direction opposite = face.getOpposite();
        VoxelShape thisShape = state.getFaceOcclusionShape(level, pos, face);
        VoxelShape sideShape = sideState.getFaceOcclusionShape(level, pos, opposite);
        if (thisShape.isEmpty() && (!sideState.isFaceSturdy(level, pos, opposite) || sideShape.isEmpty())) return true;
        boolean flag = Shapes.joinIsNotEmpty(thisShape, sideShape, BooleanOp.ONLY_FIRST);
        if (hashMap.size() == 2048) hashMap.removeLastByte();
        hashMap.putAndMoveToFirst(statePairKey, (byte) (flag ? 1 : 0));
        return flag;
    }

}