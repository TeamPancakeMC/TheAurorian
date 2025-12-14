package cn.teampancake.theaurorian.common.blocks.crystal;

import cn.teampancake.theaurorian.common.blocks.entity.crystal.LunarSplitterBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LunarSplitter extends AbstractLunarCrystal {

    public LunarSplitter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(LunarSplitter::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LunarSplitterBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return this.shouldEmptyTicker(level, state) ? null : createTickerHelper(blockEntityType, TABlockEntityTypes.LUNAR_SPLITTER.get(), LunarSplitterBlockEntity::serverTick);
    }

}