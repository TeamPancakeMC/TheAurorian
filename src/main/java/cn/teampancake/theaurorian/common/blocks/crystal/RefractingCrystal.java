package cn.teampancake.theaurorian.common.blocks.crystal;

import cn.teampancake.theaurorian.common.blocks.entity.crystal.AbstractLumenCrystalBlockEntity;
import cn.teampancake.theaurorian.common.blocks.entity.crystal.RefractingCrystalBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;

public class RefractingCrystal extends AbstractLumenCrystal {

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(p -> new RefractingCrystal());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RefractingCrystalBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (level.isClientSide || state.getValue(HALF) == DoubleBlockHalf.UPPER) ? null : createTickerHelper(blockEntityType, TABlockEntityTypes.REFRACTING_CRYSTAL.get(), AbstractLumenCrystalBlockEntity::serverTick);
    }

}