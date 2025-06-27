package cn.teampancake.theaurorian.common.enchantments;

import cn.teampancake.theaurorian.common.components.SourceOfTerra;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.List;
import java.util.UUID;

public record SourceOfTerraEffect(Unit unit) implements EnchantmentEntityEffect {

    public static final MapCodec<SourceOfTerraEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Unit.CODEC.fieldOf("unit").forGetter(SourceOfTerraEffect::unit)).apply(instance, SourceOfTerraEffect::new));

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        BlockPos blockPos = BlockPos.containing(origin);
        Container container = HopperBlockEntity.getContainerAt(level, blockPos);
        if (container instanceof BlockEntity blockEntity && entity instanceof Player player && player.isShiftKeyDown()) {
            DataComponentType<SourceOfTerra> componentType = TADataComponents.SOURCE_OF_TERRA.get();
            ItemStack itemInHand = item.itemStack();
            SourceOfTerra sourceOfTerra = itemInHand.get(componentType);
            String dimension = level.dimension().location().toString();
            if (sourceOfTerra == null) {
                itemInHand.set(componentType, new SourceOfTerra(dimension, blockPos));
                this.checkIfServerPlayerAndSendMessage(player, "message.source_of_terra.bind");
                this.addUUIDToBlockEntity(blockEntity, player);
            } else {
                BlockPos selectedPos = sourceOfTerra.selectedPos();
                int selectedX = selectedPos.getX();
                int selectedY = selectedPos.getY();
                int selectedZ = selectedPos.getZ();
                if (selectedX == blockPos.getX() && selectedY == blockPos.getY() && selectedZ == blockPos.getZ()) {
                    this.checkIfServerPlayerAndSendMessage(player, "message.source_of_terra.unbind");
                    this.removeUUIDFromBlockEntity(blockEntity, player);
                    itemInHand.remove(componentType);
                } else {
                    Container selectedContainer = HopperBlockEntity.getContainerAt(level, selectedPos);
                    if (selectedContainer instanceof BlockEntity selectedBlockEntity) {
                        this.removeUUIDFromBlockEntity(selectedBlockEntity, player);
                    }

                    itemInHand.set(componentType, new SourceOfTerra(dimension, blockPos));
                    this.checkIfServerPlayerAndSendMessage(player, "message.source_of_terra.changed");
                    this.addUUIDToBlockEntity(blockEntity, player);
                }
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

    private void addUUIDToBlockEntity(BlockEntity blockEntity, Player player) {
        AttachmentType<List<UUID>> attachmentType = TAAttachmentTypes.BINDING_PLAYER_UUIDS.get();
        List<UUID> uuidList = blockEntity.getData(attachmentType);
        if (!uuidList.contains(player.getUUID())) {
            uuidList.add(player.getUUID());
            blockEntity.setData(attachmentType, uuidList);
        }
    }

    private void removeUUIDFromBlockEntity(BlockEntity blockEntity, Player player) {
        AttachmentType<List<UUID>> attachmentType = TAAttachmentTypes.BINDING_PLAYER_UUIDS.get();
        List<UUID> uuidList = blockEntity.getData(attachmentType);
        if (uuidList.contains(player.getUUID())) {
            uuidList.remove(player.getUUID());
            blockEntity.setData(attachmentType, uuidList);
        }
    }

    private void checkIfServerPlayerAndSendMessage(Player player, String key) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.sendSystemMessage(Component.translatable(key));
        }
    }

}