package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetContainerSlotItemC2SPacket(BlockPos blockPos, int index, ItemStack stack) implements CustomPacketPayload {

    public static final Type<SetContainerSlotItemC2SPacket> TYPE = new Type<>(TheAurorian.prefix("network.set_container_slot_item"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SetContainerSlotItemC2SPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, SetContainerSlotItemC2SPacket::blockPos,
            ByteBufCodecs.INT, SetContainerSlotItemC2SPacket::index,
            ItemStack.STREAM_CODEC, SetContainerSlotItemC2SPacket::stack,
            SetContainerSlotItemC2SPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SetContainerSlotItemC2SPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                Level level = player.level();
                BlockEntity blockEntity = level.getBlockEntity(packet.blockPos);
                if (blockEntity instanceof Container container) {
                    container.setItem(packet.index, packet.stack);
                    BlockState state = blockEntity.getBlockState();
                    blockEntity.setChanged();
                    level.sendBlockUpdated(packet.blockPos, state, state, 3);
                }
            }
        });
    }

}