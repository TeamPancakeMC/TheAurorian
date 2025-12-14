package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.entity.SilentCampfireBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

public class SilentCampfire extends CampfireBlock {

    public SilentCampfire(Properties properties) {
        super(Boolean.TRUE, 1, properties.mapColor(MapColor.PODZOL).instrument(NoteBlockInstrument.BASS)
                .strength(2.0F).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
                .lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SilentCampfireBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        BlockEntityTicker<T> particleTick = createTickerHelper(blockEntityType, TABlockEntityTypes.SILENT_CAMPFIRE.get(), CampfireBlockEntity::particleTick);
        BlockEntityTicker<T> cooldownTick = createTickerHelper(blockEntityType, TABlockEntityTypes.SILENT_CAMPFIRE.get(), CampfireBlockEntity::cooldownTick);
        BlockEntityTicker<T> cookTick = createTickerHelper(blockEntityType, TABlockEntityTypes.SILENT_CAMPFIRE.get(), SilentCampfireBlockEntity::cookTick);
        return level.isClientSide ? (state.getValue(LIT) ? particleTick : null) : (state.getValue(LIT) ? cookTick : cooldownTick);
    }

}