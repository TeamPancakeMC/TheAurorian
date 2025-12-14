package cn.teampancake.theaurorian.common.blocks.crystal;

import cn.teampancake.theaurorian.common.blocks.entity.crystal.LunarSourcePrismBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class LunarSourcePrism extends AbstractLunarCrystal {

    public LunarSourcePrism(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(LunarSourcePrism::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LunarSourcePrismBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return this.shouldEmptyTicker(level, state) ? null : createTickerHelper(blockEntityType, TABlockEntityTypes.LUNAR_SOURCE_PRISM.get(), LunarSourcePrismBlockEntity::serverTick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos : pos.below());
        if (!level.isClientSide && blockEntity instanceof LunarSourcePrismBlockEntity entity) {
            if (entity.activated) {
                return InteractionResult.PASS;
            }

            if (entity.getMoonlight() < entity.getMaxMoonlight()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    String key = "message.theaurorian.lunar_source_prism.not_charged";
                    serverPlayer.sendSystemMessage(Component.translatable(key, entity.getMoonlight()));
                }

                return InteractionResult.SUCCESS;
            }

            if (player instanceof ServerPlayer serverPlayer) {
                String key = "message.theaurorian.lunar_source_prism.activate";
                serverPlayer.sendSystemMessage(Component.translatable(key));
            }

            entity.triggerAnim("active_controller", "active_animation");
            entity.activating = true;
            entity.activeTime = 40;
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

}