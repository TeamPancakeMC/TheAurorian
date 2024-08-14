package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.blocks.technical.TrapHoleRestorer;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TrapHoleRestorerBlockEntity extends BlockEntity {

    public int restoreCountdown;
    public int transferCountdown;
    public BlockState storedState = Blocks.AIR.defaultBlockState();

    public TrapHoleRestorerBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.TRAP_HOLE_RESTORER.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TrapHoleRestorerBlockEntity blockEntity) {
        if (!level.isClientSide && !blockEntity.storedState.isAir()) {
            --blockEntity.transferCountdown;
            if (blockEntity.transferCountdown <= 0) {
                level.setBlockAndUpdate(pos, state.setValue(TrapHoleRestorer.EMPTY, Boolean.FALSE));
                --blockEntity.restoreCountdown;
            }

            if (blockEntity.restoreCountdown <= 0) {
                level.setBlockAndUpdate(pos, blockEntity.storedState);
            }
        }
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.restoreCountdown = tag.getInt("RestoreCountdown");
        this.transferCountdown = tag.getInt("TransferCountdown");
        if (this.level != null) {
            HolderLookup<Block> blockGetter = this.level.holderLookup(Registries.BLOCK);
            this.storedState = NbtUtils.readBlockState(blockGetter, tag.getCompound("StoredState"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("RestoreCountdown", this.restoreCountdown);
        tag.putInt("TransferCountdown", this.transferCountdown);
        tag.put("StoredState", NbtUtils.writeBlockState(this.storedState));
    }

}