package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AddItemToInventoryC2SPacket(ItemStack carried) implements CustomPacketPayload {

    public static final Type<AddItemToInventoryC2SPacket> TYPE = new Type<>(TheAurorian.prefix("network.add_item_to_inventory"));
    public static final StreamCodec<RegistryFriendlyByteBuf, AddItemToInventoryC2SPacket> STREAM_CODEC =
            StreamCodec.composite(ItemStack.OPTIONAL_STREAM_CODEC, AddItemToInventoryC2SPacket::carried, AddItemToInventoryC2SPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(AddItemToInventoryC2SPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player instanceof ServerPlayer) {
                packet.carried.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
                player.addItem(packet.carried);
            }
        });
    }

}