package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.screens.SelenaInitialScreen;
import cn.teampancake.theaurorian.common.entities.npc.Selena;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record InteractWithSelenaS2CPacket(String name, int id) implements CustomPacketPayload {

    public static final Type<InteractWithSelenaS2CPacket> TYPE = new Type<>(TheAurorian.prefix("network.interact_with_selena"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InteractWithSelenaS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(InteractWithSelenaS2CPacket::write, InteractWithSelenaS2CPacket::new);

    public InteractWithSelenaS2CPacket(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readInt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.name);
        buf.writeInt(this.id);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(InteractWithSelenaS2CPacket packet, IPayloadContext context) {
        MutableComponent component = Component.literal(packet.name);
        Entity entity = context.player().level().getEntity(packet.id);
        if (!packet.name.isEmpty() && context.flow().isClientbound()) {
            if (entity instanceof Selena selena) {
                context.enqueueWork(() -> openScreen(component, selena));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void openScreen(Component name, Selena selena) {
        Minecraft.getInstance().setScreen(new SelenaInitialScreen(name, selena));
    }

}