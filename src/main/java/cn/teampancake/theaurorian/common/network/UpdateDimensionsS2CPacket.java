package cn.teampancake.theaurorian.common.network;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import cn.teampancake.theaurorian.TheAurorian;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateDimensionsS2CPacket(Set<ResourceKey<Level>> keys, boolean add) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UpdateDimensionsS2CPacket> TYPE = new CustomPacketPayload.Type<>(TheAurorian.prefix("network.update_dimensions"));
    public static final StreamCodec<ByteBuf, UpdateDimensionsS2CPacket> STREAM_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(Registries.DIMENSION).apply(ByteBufCodecs.list()).map(Set::copyOf, List::copyOf), UpdateDimensionsS2CPacket::keys,
            ByteBufCodecs.BOOL, UpdateDimensionsS2CPacket::add, UpdateDimensionsS2CPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateDimensionsS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            final LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                final Set<ResourceKey<Level>> dimensionList = player.connection.levels();
                Consumer<ResourceKey<Level>> keyConsumer = packet.add() ? dimensionList::add : dimensionList::remove;
                packet.keys().forEach(keyConsumer);
            }
        });
    }

}