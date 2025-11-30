package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.components.RunestoneMountain;
import cn.teampancake.theaurorian.common.components.SourceOfTerra;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import cn.teampancake.theaurorian.common.level.structure.structures.RuinsAltarStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.UUID;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class BlockEventSubscriber {

    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        List<ItemEntity> drops = event.getDrops();
        if (event.getBreaker() instanceof Player player) {
            ItemStack itemInHand = player.getItemInHand(player.getUsedItemHand());
            DataComponentType<SourceOfTerra> componentType = TADataComponents.SOURCE_OF_TERRA.get();
            AttachmentType<List<UUID>> attachmentType = TAAttachmentTypes.BINDING_PLAYER_UUIDS.get();
            BlockEntity currentBlockEntity = event.getBlockEntity();
            if (currentBlockEntity != null) {
                List<UUID> uuidList = currentBlockEntity.getData(attachmentType);
                if (!uuidList.isEmpty()) {
                    uuidList.forEach(uuid -> {
                        if (event.getLevel().getPlayerByUUID(uuid) instanceof ServerPlayer serverPlayer) {
                            serverPlayer.sendSystemMessage(Component.translatable("message.source_of_terra.destroy"));
                        }
                    });

                    return;
                }
            }

            CuriosApi.getCuriosInventory(player).ifPresent(itemHandler -> {
                DataComponentType<RunestoneMountain> component = TADataComponents.RUNESTONE_MOUNTAIN.get();
                itemHandler.findFirstCurio(stack -> stack.has(component)).ifPresent(slotResult -> {
                    RunestoneMountain runestoneMountain = slotResult.stack().get(component);
                    int experience = event.getDroppedExperience();
                    if (runestoneMountain != null) {
                        float minXpBoost = runestoneMountain.minXpBoost();
                        float maxXpBoost = runestoneMountain.maxXpBoost();
                        float boost = Mth.randomBetween(player.getRandom(), minXpBoost, maxXpBoost);
                        event.setDroppedExperience(experience + Mth.ceil(experience * boost));
                    }
                });
            });

            if (itemInHand.getEnchantmentLevel(TAEnchantments.get(player.level(), TAEnchantments.SOURCE_OF_TERRA)) > 0) {
                SourceOfTerra sourceOfTerra = itemInHand.get(componentType);
                if (!drops.isEmpty() && sourceOfTerra != null) {
                    ResourceLocation levelResource = ResourceLocation.parse(sourceOfTerra.dimension());
                    ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, levelResource);
                    MinecraftServer server = player.level().getServer();
                    if (server == null) return;
                    ServerLevel targetLevel = server.getLevel(levelKey);
                    if (targetLevel == null) return;
                    BlockPos selectedPos = sourceOfTerra.selectedPos();
                    int selectedX = selectedPos.getX();
                    int selectedY = selectedPos.getY();
                    int selectedZ = selectedPos.getZ();
                    BlockPos targetPos = new BlockPos(selectedX, selectedY, selectedZ);
                    BlockState state = targetLevel.getBlockState(targetPos);
                    BlockEntity blockEntity = targetLevel.getBlockEntity(targetPos);
                    if (state.hasBlockEntity() && blockEntity instanceof Container container) {
                        drops.forEach(itemEntity -> HopperBlockEntity.addItem(container, itemEntity));
                    } else {
                        if (player instanceof ServerPlayer serverPlayer) {
                            serverPlayer.sendSystemMessage(Component.translatable("message.source_of_terra.invalid"));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (RuinsAltarStructure.isPositionInStructure(event.getLevel(), event.getPos())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (RuinsAltarStructure.isPositionInStructure(event.getLevel(), event.getPos())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (RuinsAltarStructure.isPositionInStructure(event.getLevel(), event.getPos())) {
            if (event.getItemStack().getItem() instanceof BlockItem) {
                event.setCanceled(true);
            }
        }
    }

}