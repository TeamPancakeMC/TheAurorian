package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateItemPotionContentsC2SPacket(ItemStack carried, DataComponentType<?> componentType, PotionContents potionContents) implements CustomPacketPayload {

    public static final Type<UpdateItemPotionContentsC2SPacket> TYPE = new Type<>(TheAurorian.prefix("network.update_item_potion_component"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateItemPotionContentsC2SPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC, UpdateItemPotionContentsC2SPacket::carried,
            DataComponentType.STREAM_CODEC, UpdateItemPotionContentsC2SPacket::componentType,
            PotionContents.STREAM_CODEC, UpdateItemPotionContentsC2SPacket::potionContents,
            UpdateItemPotionContentsC2SPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateItemPotionContentsC2SPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            packet.carried.set(DataComponents.POTION_CONTENTS, packet.potionContents);
        });
    }

}