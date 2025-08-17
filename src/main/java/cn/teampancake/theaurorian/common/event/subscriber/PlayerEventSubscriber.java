package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.inventory.AlchemyTableMenu;
import cn.teampancake.theaurorian.common.blocks.MysteriumWoolBed;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABiomeTags;
import cn.teampancake.theaurorian.common.items.armor.MysteriumWoolArmor;
import cn.teampancake.theaurorian.common.registry.*;
import cn.teampancake.theaurorian.common.utils.EnchantmentUtils;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import cn.teampancake.theaurorian.common.utils.TAInventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class PlayerEventSubscriber {

    @SubscribeEvent
    public static void onPlayerTicking(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && player.level() instanceof ServerLevel level) {
            if (player.isAlive() && !player.isSpectator() && !level.isClientSide()) {
                TAInventoryUtils.applyPotionDecay(player.getInventory().items, player, level);
                boolean noImmuneEffect = !player.hasEffect(TAMobEffects.WARM) && !player.hasEffect(TAMobEffects.FROSTBITE);
                boolean isInSnowField = level.getBiome(player.blockPosition()).is(TABiomeTags.IS_FILTHY_ICE);
                if (player.tickCount % 60 == 0 && noImmuneEffect && isInSnowField && !player.isCreative()
                        && !TAInventoryUtils.isWearFullArmor(player, MysteriumWoolArmor.class)) {
                    player.setData(TAAttachmentTypes.TICKS_FROSTBITE, player.getTicksRequiredToFreeze());
                    player.hurt(player.damageSources().freeze(), 1.0F);
                    player.setSharedFlagOnFire(false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerXpChange(PlayerXpEvent.XpChange event) {
        Player player = event.getEntity();
        int amount = event.getAmount();
        ItemStack offhandItem = player.getOffhandItem();
        if (offhandItem.is(TAItems.BOOK_OF_SIN)) {
            DataComponentType<Integer> component = TADataComponents.ABSORBED_EXPERIENCE.get();
            Integer i = offhandItem.get(component);
            if (amount > 0 && i != null) {
                offhandItem.set(component, i + amount);
                event.setCanceled(true);
            }
        }

        Holder<Enchantment> enchantment = TAEnchantments.get(player.level(), TAEnchantments.CLEAR_MIND);
        int i = EnchantmentUtils.getEnchantmentLevel(enchantment, player);
        if (amount > 0 && i > 0 && player.experienceLevel < 30) {
            event.setAmount(amount + Mth.ceil(amount * i * 0.1F));
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            TAEnchantmentEffectComponents.onBlockUse(serverLevel, event.getEntity(), event.getPos());
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            TAEnchantmentEffectComponents.onItemUse(serverLevel, event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        AttachmentType<Integer> type = TAAttachmentTypes.TELEPORT_TO_AURORIAN_COUNT.get();
        if (event.getTo() == TADimensions.AURORIAN_DIMENSION) {
            Player player = event.getEntity();
            int count = player.getData(type);
            player.setData(type, count + 1);
        }
    }

    @SubscribeEvent
    public static void onPlayerEarnedAdvancement(AdvancementEvent.AdvancementEarnEvent event) {
        ResourceLocation enteredAurorian = ResourceLocation.parse(TheAurorian.MOD_ID + ":enter_aurorian");
        if (event.getAdvancement().id().equals(enteredAurorian)) {
            event.getEntity().setData(TAAttachmentTypes.FIRST_ENTER_AURORIAN, true);
        }
    }

    @SubscribeEvent
    public static void onPlayerContainer(PlayerContainerEvent event) {
        AbstractContainerMenu container = event.getContainer();
        if (container instanceof AlchemyTableMenu menu) {
            List<ItemStack> stacks = menu.slots.stream().filter(Slot::hasItem).map(Slot::getItem).toList();
            DataComponentType<Boolean> component = TADataComponents.INGREDIENT_APPLIER.get();
            if (event instanceof PlayerContainerEvent.Open) {
                ContainerData containerData = menu.getContainerData();
                TAInventoryUtils.refreshIngredientApplier(containerData, stacks, Boolean.FALSE);
            }

            if (event instanceof PlayerContainerEvent.Close) {
                stacks.stream().filter(stack -> stack.has(component)).forEach(stack -> stack.remove(component));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerSetSpawn(PlayerSetSpawnEvent event) {
        Player player = event.getEntity();
        BlockPos newSpawn = event.getNewSpawn();
        if (player.level().dimension() == TADimensions.AURORIAN_DIMENSION) {
            if (player instanceof ServerPlayer serverPlayer && newSpawn != null) {
                BlockState state = player.level().getBlockState(newSpawn);
                if (state.getBlock() instanceof MysteriumWoolBed) {
                    player.setData(TAAttachmentTypes.SPAWN_POINT_OF_AURORIAN, newSpawn);
                    String key = "message.block." + TheAurorian.MOD_ID + ".set_spawn";
                    MutableComponent component = Component.translatable(key);
                    serverPlayer.sendSystemMessage(component);
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawnPosition(PlayerRespawnPositionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();
            BlockPos pos = TAEntityUtils.getLastPos(player, TAAttachmentTypes.SPAWN_POINT_OF_AURORIAN.get());
            if (pos != null && player.getData(TAAttachmentTypes.SHOULD_SPAWN_IN_AURORIAN.get())) {
                Optional<ServerPlayer.RespawnPosAngle> optional = ServerPlayer.findRespawnAndUseSpawnBlock(
                        level, pos, player.getRespawnAngle(), player.isRespawnForced(), Boolean.FALSE);
                if (optional.isPresent() && level.dimension() == TADimensions.AURORIAN_DIMENSION) {
                    ServerPlayer.RespawnPosAngle respawnPosAngle = optional.get();
                    DimensionTransition transition = new DimensionTransition(level, respawnPosAngle.position(),
                            Vec3.ZERO, respawnPosAngle.yaw(), 0.0F, DimensionTransition.DO_NOTHING);
                    event.setDimensionTransition(transition);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        ItemStack handStack = player.getUseItem();
        if (handStack.is(TAItems.AURORIANITE_PICKAXE.get())) {
            Optional<BlockPos> position = event.getPosition();
            if (position.isPresent()) {
                BlockPos blockPos = position.get();
                BlockState state = event.getState();
                Level level = player.level();
                if (state.getExpDrop(level, blockPos, null, player, handStack) > 0) {
                    event.setNewSpeed(event.getOriginalSpeed() * 1.4F);
                }
            }
        }
    }

}