package cn.teampancake.theaurorian.common.network;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.entity.AlchemyTableBlockEntity;
import cn.teampancake.theaurorian.common.utils.TAPotionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public record UpdateAlchemyTableIntDataC2SPacket(BlockPos blockPos, ItemStack carried, String data, int value) implements CustomPacketPayload {

    public static final Type<UpdateAlchemyTableIntDataC2SPacket> TYPE = new Type<>(TheAurorian.prefix("network.update_alchemy_table_int_data"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateAlchemyTableIntDataC2SPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, UpdateAlchemyTableIntDataC2SPacket::blockPos,
            ItemStack.OPTIONAL_STREAM_CODEC, UpdateAlchemyTableIntDataC2SPacket::carried,
            ByteBufCodecs.STRING_UTF8, UpdateAlchemyTableIntDataC2SPacket::data,
            ByteBufCodecs.INT, UpdateAlchemyTableIntDataC2SPacket::value,
            UpdateAlchemyTableIntDataC2SPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateAlchemyTableIntDataC2SPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                Level level = player.level();
                BlockEntity blockEntity = level.getBlockEntity(packet.blockPos);
                if (blockEntity instanceof AlchemyTableBlockEntity alchemyTable) {
                    Class<?> clazz = alchemyTable.getClass();
                    try {
                        Field field = clazz.getDeclaredField(packet.data());
                        field.setAccessible(true);
                        field.setInt(alchemyTable, packet.value);
                        if (packet.data.equals("liquidLevel") && packet.value == 0) {
                            alchemyTable.setPotionContents(PotionContents.EMPTY);
                            alchemyTable.setLiquidData(0);
                            alchemyTable.getMaterials().clear();
                            return;
                        }

                        if (packet.data.equals("liquidData")) {
                            List<ItemStack> materials = alchemyTable.getMaterials();
                            if (packet.value == 0) {
                                alchemyTable.setPotionContents(PotionContents.EMPTY);
                                materials.clear();
                            } else {
                                int potionColor = TAPotionUtils.getPotionColor(packet.value);
                                PotionContents potionContents = new PotionContents(
                                        Optional.of(Potions.WATER), Optional.of(potionColor),
                                        TAPotionUtils.getPotionEffects(packet.value));
                                alchemyTable.setPotionContents(potionContents);
                                materials.add(packet.carried);
                            }
                        }
                    } catch (Exception ignored) {}
                    alchemyTable.setChanged();
                    BlockState state = alchemyTable.getBlockState();
                    level.sendBlockUpdated(packet.blockPos, state, state, 3);
                }
            }
        });
    }

}