package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.entity.AstrologyTableBlockEntity;
import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import cn.teampancake.theaurorian.common.blocks.state.TALootType;
import cn.teampancake.theaurorian.common.event.subscriber.LevelEventSubscriber;
import cn.teampancake.theaurorian.common.network.FutureNightS2CPacket;
import cn.teampancake.theaurorian.common.network.ShowStarSignScreenS2CPacket;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class AstrologyTable extends BaseEntityBlock {

    public AstrologyTable() {
        super(TABlockProperties.get().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                .requiresCorrectToolForDrops().strength(6.0F).sound(SoundType.METAL).lootType(TALootType.SELF).noOcclusion());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(p -> new AstrologyTable());
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            long dayTime = (level.getDayTime() + 6000L) % 24000L;
            if (dayTime > 6000 && dayTime <= 18000) {
                String key = "message.theaurorian.astrology_table.only_at_night";
                serverPlayer.sendSystemMessage(Component.translatable(key));
                return InteractionResult.CONSUME;
            }

            int[] arr = LevelEventSubscriber.getFuturePhases();
            PacketDistributor.sendToPlayer(serverPlayer,
                    new FutureNightS2CPacket(arr[0], arr[1], arr[2]),
                    new ShowStarSignScreenS2CPacket());
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AstrologyTableBlockEntity(pos, state);
    }

}