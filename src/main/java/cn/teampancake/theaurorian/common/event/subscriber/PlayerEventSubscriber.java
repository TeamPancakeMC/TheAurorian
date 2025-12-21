package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.inventory.AlchemyTableMenu;
import cn.teampancake.theaurorian.common.blocks.MysteriumWoolBed;
import cn.teampancake.theaurorian.common.components.*;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABiomeTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAStructureTags;
import cn.teampancake.theaurorian.common.items.armor.MysteriumWoolArmor;
import cn.teampancake.theaurorian.common.level.data.sky_color.SkyColorData;
import cn.teampancake.theaurorian.common.level.data.sky_color.SkyColorManager;
import cn.teampancake.theaurorian.common.network.NightTypeS2CPacket;
import cn.teampancake.theaurorian.common.network.WorldNightColorS2CPacket;
import cn.teampancake.theaurorian.common.registry.*;
import cn.teampancake.theaurorian.common.utils.EnchantmentUtils;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import cn.teampancake.theaurorian.common.utils.TAInventoryUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
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
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class PlayerEventSubscriber {

    private static final Map<UUID, NoFlyZoneCache> noFlyZoneCache = new ConcurrentHashMap<>();
    private static final int CHECK_INTERVAL = 10;
    private static final double POSITION_THRESHOLD = 16.0;
    
    private static class NoFlyZoneCache {

        BlockPos lastCheckPos;
        boolean inNoFlyZone;
        int lastCheckTick;
        
        NoFlyZoneCache(BlockPos pos, boolean inZone, int tick) {
            this.lastCheckPos = pos;
            this.inNoFlyZone = inZone;
            this.lastCheckTick = tick;
        }

    }

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

                checkNoFlyZone(player);
            }
        }
    }

    private static void checkNoFlyZone(ServerPlayer player) {
        if (!player.isFallFlying()) return;
        BlockPos currentPos = player.blockPosition();
        UUID playerId = player.getUUID();
        int currentTick = player.tickCount;
        NoFlyZoneCache cache = noFlyZoneCache.get(playerId);
        boolean needCheck = false;
        boolean inNoFlyZone = false;
        if (cache == null) {
            needCheck = true;
        } else {
            int ticksSinceLastCheck = currentTick - cache.lastCheckTick;
            double distanceMoved = Math.sqrt(currentPos.distSqr(cache.lastCheckPos));
            if (ticksSinceLastCheck >= CHECK_INTERVAL || distanceMoved >= POSITION_THRESHOLD) {
                needCheck = true;
            } else {
                inNoFlyZone = cache.inNoFlyZone;
            }
        }

        if (needCheck) {
            inNoFlyZone = TAEntityUtils.isPlayerNearStructure(player, TAStructureTags.RUNESTONE_DUNGEON, 300.0);
            noFlyZoneCache.put(playerId, new NoFlyZoneCache(currentPos, inNoFlyZone, currentTick));
        }

        if (inNoFlyZone) {
            player.stopFallFlying();
        }
    }

    @SubscribeEvent
    public static void onPlayerItemFished(ItemFishedEvent event) {
        Player player = event.getEntity();
        RunestoneNature runestoneNature = RunestoneNature.findRunestoneNature(player);
        if (runestoneNature == null) return;
        NonNullList<ItemStack> drops = event.getDrops();
        FishingHook hook = event.getHookEntity();
        float minXpBoost = runestoneNature.minXpBoost();
        float maxXpBoost = runestoneNature.maxXpBoost();
        if (player instanceof ServerPlayer serverPlayer) {
            ItemStack stack = player.getMainHandItem();
            CriteriaTriggers.FISHING_ROD_HOOKED.trigger(serverPlayer, stack, hook, drops);
        }

        for (ItemStack itemStack : drops) {
            ItemEntity itemEntity = new ItemEntity(hook.level(), hook.getX(), hook.getY(), hook.getZ(), itemStack);
            double d0 = player.getX() - hook.getX();
            double d1 = player.getY() - hook.getY();
            double d2 = player.getZ() - hook.getZ();
            double y = d1 * 0.1F + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08F;
            itemEntity.setDeltaMovement(d0 * 0.1F, y, d2 * 0.1F);
            hook.level().addFreshEntity(itemEntity);
            int value = hook.getRandom().nextInt(6) + 1;
            value += Mth.ceil(value * Mth.randomBetween(player.getRandom(), minXpBoost, maxXpBoost));
            player.level().addFreshEntity(new ExperienceOrb(player.level(), player.getX(), player.getY() + 0.5F, player.getZ() + 0.5F, value));
            if (itemStack.is(ItemTags.FISHES)) {
                player.awardStat(Stats.FISH_CAUGHT, 1);
            }
        }

        event.setCanceled(true);
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
        if (event.getEntity() instanceof ServerPlayer player) {
            MinecraftServer server = player.getServer();
            if (server != null) {
                ServerLevel fromLevel = server.getLevel(event.getFrom());
                ServerLevel toLevel = server.getLevel(event.getTo());
                if (fromLevel != null && toLevel != null) {
                    AttachmentType<Integer> type = TAAttachmentTypes.TELEPORT_TO_AURORIAN_COUNT.get();
                    AttachmentType<Integer> nscType = TAAttachmentTypes.NIGHT_SKY_COLOR.get();
                    if (TACommonUtils.isAurorianDimension(toLevel)) {
                        SkyColorData skyData = SkyColorManager.getWorldSkyData(fromLevel);
                        SkyColorManager.syncSkyColorToPlayer(player, skyData);
                        player.setData(type, player.getData(type) + 1);
                        toLevel.setData(nscType, fromLevel.getData(nscType));
                        PacketDistributor.sendToPlayer(player, new NightTypeS2CPacket(skyData.currentDayColor));
                        PacketDistributor.sendToPlayer(player, new WorldNightColorS2CPacket(fromLevel.getData(nscType)));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();
            if (TACommonUtils.isAurorianDimension(level)) {
                SkyColorData skyData = SkyColorManager.getWorldSkyData(level);
                PacketDistributor.sendToPlayer(player, new NightTypeS2CPacket(skyData.currentDayColor));
                SkyColorManager.syncSkyColorToPlayer(player, skyData);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            noFlyZoneCache.remove(player.getUUID());
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
        if (TACommonUtils.isAurorianDimension(player.level())) {
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
                if (optional.isPresent() && TACommonUtils.isAurorianDimension(level)) {
                    ServerPlayer.RespawnPosAngle respawnPosAngle = optional.get();
                    DimensionTransition transition = new DimensionTransition(level, respawnPosAngle.position(),
                            Vec3.ZERO, respawnPosAngle.yaw(), 0.0F, DimensionTransition.DO_NOTHING);
                    event.setDimensionTransition(transition);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();
            if (TACommonUtils.isAurorianDimension(level)) {
                SkyColorData skyData = SkyColorManager.getWorldSkyData(level);
                PacketDistributor.sendToPlayer(player, new NightTypeS2CPacket(skyData.currentDayColor));
                SkyColorManager.syncSkyColorToPlayer(player, skyData);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        ItemStack mainHandItem = player.getMainHandItem();
        AtomicReference<Float> multiplier = new AtomicReference<>(event.getDamageMultiplier());
        CuriosApi.getCuriosInventory(player).ifPresent(itemHandler -> {
            DataComponentType<RunestoneBlaze> blazeComponent = TADataComponents.RUNESTONE_BLAZE.get();
            itemHandler.findFirstCurio(stack -> stack.has(blazeComponent)).ifPresent(slotResult -> {
                RunestoneBlaze runestoneBlaze = slotResult.stack().get(blazeComponent);
                float value = runestoneBlaze == null ? 0.0F : runestoneBlaze.getCriticalMultiplierBoost();
                multiplier.updateAndGet(v -> v + value);
                event.setDamageMultiplier(multiplier.get());
            });

            DataComponentType<RunestoneThunder> thunderComponent = TADataComponents.RUNESTONE_THUNDER.get();
            itemHandler.findFirstCurio(stack -> stack.has(thunderComponent)).ifPresent(slotResult -> {
                RunestoneThunder runestoneThunder = slotResult.stack().get(thunderComponent);
                AttachmentType<Boolean> attachmentType = TAAttachmentTypes.TRIGGER_CRITICAL_HIT.get();
                if (runestoneThunder != null && !event.isCriticalHit() && player.getData(attachmentType)) {
                    if (runestoneThunder.canTriggerCriticalHit()) event.setCriticalHit(true);
                    player.setData(attachmentType, false);
                }
            });

            DataComponentType<RunestoneWater> lightComponent = TADataComponents.RUNESTONE_LIGHT.get();
            itemHandler.findFirstCurio(stack -> stack.has(lightComponent)).ifPresent(slotResult -> {
                RunestoneWater runestoneWater = slotResult.stack().get(lightComponent);
                if (runestoneWater != null && event.isCriticalHit()) {
                    player.heal(runestoneWater.getHealValue());
                }
            });
        });

        if (mainHandItem.is(TAItems.STEEL_DAGGER)) {
            event.setDamageMultiplier(multiplier.get() + 0.75F);
            if (!player.hasInfiniteMaterials()) {
                int totalDamage = mainHandItem.getDamageValue() + 5;
                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(serverPlayer, mainHandItem, totalDamage);
                }

                mainHandItem.setDamageValue(totalDamage);
                if (totalDamage >= mainHandItem.getMaxDamage()) {
                    mainHandItem.shrink(1);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        BlockState state = event.getState();
        ItemStack handStack = player.getMainHandItem();
        if (state.is(BlockTags.MINEABLE_WITH_AXE) && handStack.isCorrectToolForDrops(state)) {
            CuriosApi.getCuriosInventory(player).ifPresent(itemHandler -> {
                DataComponentType<RunestoneNature> component = TADataComponents.RUNESTONE_NATURE.get();
                itemHandler.findFirstCurio(stack -> stack.has(component)).ifPresent(slotResult -> {
                    Float uncheckedChopBoost = slotResult.stack().get(TADataComponents.FIXED_CHOP_BOOST);
                    float chopBoost = uncheckedChopBoost != null ? uncheckedChopBoost : 0.0F;
                    float originalSpeed = event.getOriginalSpeed();
                    event.setNewSpeed(originalSpeed + originalSpeed * chopBoost);
                });
            });
        }

        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) && handStack.isCorrectToolForDrops(state)) {
            CuriosApi.getCuriosInventory(player).ifPresent(itemHandler -> {
                DataComponentType<RunestoneMountain> component = TADataComponents.RUNESTONE_MOUNTAIN.get();
                itemHandler.findFirstCurio(stack -> stack.has(component)).ifPresent(slotResult -> {
                    Float uncheckedMiningBoost = slotResult.stack().get(TADataComponents.FIXED_MINING_BOOST);
                    float miningBoost = uncheckedMiningBoost != null ? uncheckedMiningBoost : 0.0F;
                    float originalSpeed = event.getOriginalSpeed();
                    event.setNewSpeed(originalSpeed + originalSpeed * miningBoost);
                });
            });
        }

        if (handStack.is(TAItems.AURORIANITE_PICKAXE.get())) {
            Optional<BlockPos> position = event.getPosition();
            if (position.isPresent()) {
                BlockPos blockPos = position.get();
                Level level = player.level();
                if (state.getExpDrop(level, blockPos, null, player, handStack) > 0) {
                    event.setNewSpeed(event.getOriginalSpeed() * 1.4F);
                }
            }
        }
    }

}