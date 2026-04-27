package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.inventory.AlchemyTableMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateMenuCarriedC2SPacket(int containerId, ItemStack carried) implements CustomPacketPayload {

    public static final Type<UpdateMenuCarriedC2SPacket> TYPE = new Type<>(TheAurorian.prefix("network.update_menu_carried"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateMenuCarriedC2SPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, UpdateMenuCarriedC2SPacket::containerId,
            ItemStack.OPTIONAL_STREAM_CODEC, UpdateMenuCarriedC2SPacket::carried,
            UpdateMenuCarriedC2SPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateMenuCarriedC2SPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            AbstractContainerMenu container = player.containerMenu;
            if (container instanceof AlchemyTableMenu && container.containerId == packet.containerId) {
                if (player.isCreative()) {
                    player.addItem(packet.carried);
                } else {
                    container.setCarried(packet.carried);
                }
            }
        });
    }

}