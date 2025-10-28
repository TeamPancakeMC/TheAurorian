package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.components.RunestoneWater;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAMobEffectTags;
import cn.teampancake.theaurorian.common.effect.TAMobEffect;
import cn.teampancake.theaurorian.common.entities.ai.goal.SpiderIgnoreSpectralArmorGoal;
import cn.teampancake.theaurorian.common.entities.boss.AbstractAurorianBoss;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrab;
import cn.teampancake.theaurorian.common.entities.monster.SpiderlingCrystalShell;
import cn.teampancake.theaurorian.common.entities.technical.SitEntity;
import cn.teampancake.theaurorian.common.items.armor.SpectralArmor;
import cn.teampancake.theaurorian.common.level.TAServerPlayer;
import cn.teampancake.theaurorian.common.level.data.world_event.*;
import cn.teampancake.theaurorian.common.network.*;
import cn.teampancake.theaurorian.common.registry.*;
import cn.teampancake.theaurorian.common.components.RunestoneIce;
import cn.teampancake.theaurorian.common.shields.BaseShield;
import cn.teampancake.theaurorian.common.shields.ShieldStack;
import cn.teampancake.theaurorian.common.utils.EnchantmentUtils;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import cn.teampancake.theaurorian.common.utils.TAInventoryUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.*;
import java.util.List;
import java.util.function.Predicate;

/** @noinspection deprecation*/
@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class LivingEventSubscriber {

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Cat cat) {
            Predicate<ItemStack> items = stack -> stack.is(TAItems.CAT_BELL);
            TemptGoal temptGoal = new TemptGoal(cat, 1.25F, items, Boolean.FALSE);
            cat.goalSelector.addGoal(0, temptGoal);
        }

        if (event.getEntity() instanceof Spider spider) {
            spider.targetSelector.addGoal(0, new SpiderIgnoreSpectralArmorGoal<>(spider, Player.class));
        }
    }

    @SubscribeEvent
    public static void onMobFinalizeSpawn(FinalizeSpawnEvent event) {
        Mob mob = event.getEntity();
        Level level = mob.level();
        if (mob.getType().is(TAEntityTags.AFFECTED_BY_NIGHTMARE_MODE)) {
            GameRules gameRules = event.getLevel().getLevel().getGameRules();
            if (gameRules.getRule(TAGameRules.RULE_ENABLE_NIGHTMARE_MODE).get()) {
                AttributeInstance health = mob.getAttribute(Attributes.MAX_HEALTH);
                AttributeInstance attack = mob.getAttribute(Attributes.ATTACK_DAMAGE);
                if (health != null && attack != null) {
                    GameRules.Key<GameRules.IntegerValue> key = TAGameRules.RULE_NIGHTMARE_MODE_MULTIPLIER;
                    ResourceLocation id1 = TheAurorian.prefix("nightmare_health_enhance");
                    ResourceLocation id2 = TheAurorian.prefix("nightmare_attack_enhance");
                    double multiplier = Math.max(1.0D, gameRules.getRule(key).get()) * 2.0D;
                    AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
                    health.addPermanentModifier(new AttributeModifier(id1, multiplier, operation));
                    attack.addPermanentModifier(new AttributeModifier(id2, multiplier, operation));
                }

                if (mob.getLastDamageSource() == null) {
                    mob.setHealth(mob.getMaxHealth());
                }
            }
        }

        if (level instanceof ServerLevel serverLevel && TAWorldEvents.BLOOD_MOON.get().isActive(serverLevel)) {
            BloodMoonEvent.enhanceEnemy(serverLevel, mob);
        }
    }

    @SubscribeEvent
    public static void onEntityPreTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            Level level = entity.level();
            if (!level.isClientSide()) {
                boolean flag = entity.hasEffect(TAMobEffects.PARALYSIS) || entity.hasEffect(TAMobEffects.STUN);
                if (flag && entity.getVehicle() == null) {
                    SitEntity sitEntity = new SitEntity(level);
                    sitEntity.setPos(entity.position());
                    level.addFreshEntity(sitEntity);
                    entity.startRiding(sitEntity);
                }

                if (entity instanceof ServerPlayer player) {
                    int i = entity.getData(TAAttachmentTypes.TICKS_FROSTBITE);
                    int j = entity.getData(TAAttachmentTypes.ACTIVATION_TICKS);
                    int k = player.getData(TAAttachmentTypes.TRIGGER_CORRUPTION_COOLDOWN);
                    if (i > -1) {
                        player.setData(TAAttachmentTypes.TICKS_FROSTBITE, Math.max(0, i - 10));
                        PacketDistributor.sendToPlayer(player, new FrostbiteS2CPacket(i));
                    }

                    if (j > -1) {
                        player.setData(TAAttachmentTypes.ACTIVATION_TICKS, j - 1);
                        PacketDistributor.sendToPlayer(player, new DisplayActivationTickS2CPacket(j));
                    }

                    if (k > 0) player.setData(TAAttachmentTypes.TRIGGER_CORRUPTION_COOLDOWN, k - 1);
                    if (level.getGameTime() % 20 == 0) {
                        ShieldStack shieldStack = player.getData(TAAttachmentTypes.CURRENT_SHIELD);
                        if (shieldStack != ShieldStack.EMPTY) {
                            BaseShield shield = shieldStack.getShield().value();
                            if (shield.isNaturalRecovery(entity)) {
                                shieldStack.increaseShield(shield.naturalRecovery(player));
                                PacketDistributor.sendToPlayer(player, new UpdateShieldValueS2CPacket(shieldStack.getShieldValue()));
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityPostTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Projectile projectile) {
            Level level = projectile.level();
            Entity owner = projectile.getOwner();
            if (!level.isClientSide && level instanceof ServerLevel serverLevel && owner instanceof LivingEntity entity) {
                TAEnchantmentEffectComponents.onProjectileTick(serverLevel, entity, projectile);
            }
        }
    }

    @SubscribeEvent
    public static void onShieldBlock(LivingShieldBlockEvent event) {
        DamageSource source = event.getDamageSource();
        Entity sourceEntity = source.getEntity();
        if (event.getEntity() instanceof Player player) {
            ItemStack useItem = player.getUseItem();
            if (useItem.is(TAItems.UMBRA_SHIELD) && sourceEntity != null) {
                sourceEntity.setRemainingFireTicks(60);
            } else if (useItem.is(TAItems.MOONSTONE_SHIELD)) {
                Level level = player.level();
                if (level.random.nextBoolean()) {
                    float shieldDamage = event.shieldDamage();
                    float multiplier = level.isDay() ? 2.0F : 0.5F;
                    event.setBlockedDamage(shieldDamage * multiplier);
                }
            } else if (useItem.is(TAItems.CRYSTALLINE_SHIELD)) {
                List<ItemStack> handSlots = new ArrayList<>();
                player.getHandSlots().forEach(handSlots::add);
                handSlots.remove(useItem);
                ItemStack otherStack = handSlots.getFirst();
                if (!otherStack.isEmpty() && otherStack.isDamageableItem()) {
                    int damageValue = otherStack.getDamageValue();
                    if (damageValue < otherStack.getMaxDamage()) {
                        otherStack.setDamageValue(damageValue + 1);
                        player.getCooldowns().addCooldown(useItem.getItem(), 20);
                    }
                }
            }
        }

        if (sourceEntity instanceof SnowTundraGiantCrab) {
            event.setShieldDamage(event.shieldDamage() * 3);
        }
    }

    @SubscribeEvent
    public static void onMobEffectRemoved(MobEffectEvent.Remove event) {
        MobEffectInstance instance = event.getEffectInstance();
        if (instance != null && instance.effect.value() instanceof TAMobEffect effect) {
            effect.onEffectRemoved(event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        Holder<MobEffect> effect = Objects.requireNonNull(event.getEffectInstance()).getEffect();
        boolean flag1 = !effect.value().isBeneficial() && entity.hasEffect(TAMobEffects.HOLINESS);
        boolean flag2 = effect.value().isBeneficial() && entity.hasEffect(TAMobEffects.INCANTATION);
        boolean flag3 = effect.is(TAMobEffects.PARALYSIS) && !(entity instanceof Player);
        boolean flag4 = effect.is(TAMobEffectTags.MOON_QUEEN_ONLY) && !(entity instanceof MoonQueen);
        if (flag1 || flag2 || flag3 || flag4) event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
    }

    @SubscribeEvent
    public static void onMobEffectAdded(MobEffectEvent.Added event) {
        MobEffectInstance instance = event.getEffectInstance();
        if (instance != null && instance.is(TAMobEffects.STUN) && event.getEntity() instanceof ServerPlayer player) {
            PacketDistributor.sendToPlayer(player, new ShowStunScreenS2CPacket(instance.duration));
            instance.showIcon = false;
            instance.visible = false;
        }
    }

    @SubscribeEvent
    public static void onMobEffectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance instance = event.getEffectInstance();
        if (instance != null && instance.effect.value() instanceof TAMobEffect effect) {
            LivingEntity entity = event.getEntity();
            effect.onEffectExpired(entity, instance.getAmplifier());
            if (instance.is(TAMobEffects.PARALYSIS) || instance.is(TAMobEffects.STUN)) {
                BlockPos pos = entity.getOnPos().above();
                if (entity.getVehicle() instanceof SitEntity sitEntity) {
                    entity.moveTo(pos.getX(), pos.getY(), pos.getZ());
                    sitEntity.ejectPassengers();
                    sitEntity.discard();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamagePre(LivingDamageEvent.Pre event) {
        DamageSource source = event.getSource();
        LivingEntity target = event.getEntity();
        boolean isHarmfulEffect = source.is(DamageTypes.INDIRECT_MAGIC) || source.is(DamageTypes.MAGIC);
        boolean enchantmentFlag = EnchantmentUtils.canArmorTriggerEnchantmentEffect(target, TAEnchantments.VIRTUALIZATION);
        if (isHarmfulEffect && target.hasEffect(TAMobEffects.HOLINESS) || enchantmentFlag) event.setNewDamage(0.0F);
        if (event.getNewDamage() <= 0.0F) return;
        if (target instanceof Player player) {
            Level level = player.level();
            AttachmentType<Float> type = TAAttachmentTypes.EXHAUSTION_ACCUMULATION.get();
            player.setData(type, player.getData(type) + source.getFoodExhaustion());
            if (SpectralArmor.isWearSpectralArmor(player)) {
                player.getActiveEffects().stream().map(MobEffectInstance::getEffect)
                        .filter(effect -> effect.value().getCategory() == MobEffectCategory.HARMFUL)
                        .forEach(player::removeEffect);
            }

            SpiderlingCrystalShell crystalShell = TAEntityUtils.getNearestEntity(
                    player, SpiderlingCrystalShell.class, 32.0D);
            if (crystalShell != null) {
                float amount = event.getNewDamage();
                crystalShell.getCombatTracker().recordDamage(source, amount);
                crystalShell.setHealth(crystalShell.getHealth() - amount);
                crystalShell.setAbsorptionAmount(crystalShell.getAbsorptionAmount() - amount);
                crystalShell.gameEvent(GameEvent.ENTITY_DAMAGE);
                event.setNewDamage(0.0F);
            }

            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.is(TAItems.KOPISH_DAGGER) && !player.hasInfiniteMaterials()) {
                int maxDamage = mainHandItem.getMaxDamage();
                int damage = mainHandItem.getDamageValue();
                float amount = event.getNewDamage();
                float health = player.getHealth();
                if (amount > 0.0F && maxDamage - damage > amount && amount >= health) {
                    int totalDamage = Mth.floor(damage + Math.min(Mth.floor(amount), health));
                    if (player instanceof ServerPlayer serverPlayer) {
                        CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(serverPlayer, mainHandItem, totalDamage);
                    }

                    mainHandItem.setDamageValue(totalDamage);
                    if (!mainHandItem.has(TADataComponents.NERF) && totalDamage <= maxDamage * 0.9F) {
                        mainHandItem.set(DataComponents.MAX_DAMAGE, Mth.floor(maxDamage * 0.9F));
                        mainHandItem.set(TADataComponents.NERF, Unit.INSTANCE);
                    }

                    player.setHealth(1.0F);
                    event.setNewDamage(0.0F);
                }
            }

            ShieldStack shieldStack = player.getData(TAAttachmentTypes.CURRENT_SHIELD);
            if (TACommonUtils.isAurorianDimension(level) && shieldStack.getShield().is(TAShields.COMMON) && level.dayTime() > 12000) {
                event.setNewDamage(shieldStack.applyShields(player, source, event.getNewDamage()));
                if (player instanceof ServerPlayer serverPlayer) {
                    PacketDistributor.sendToPlayer(serverPlayer, new UpdateShieldValueS2CPacket(shieldStack.getShieldValue()));
                }
            }
        }

        if (source.getEntity() instanceof LivingEntity entity) {
            if (EnchantmentUtils.canArmorTriggerEnchantmentEffect(target, TAEnchantments.REFLECT_AURA)) {
                float amount = event.getNewDamage();
                entity.getCombatTracker().recordDamage(source, amount);
                entity.setHealth(entity.getHealth() - amount);
                entity.setAbsorptionAmount(entity.getAbsorptionAmount() - amount);
                entity.gameEvent(GameEvent.ENTITY_DAMAGE);
            }

            if (entity instanceof SpiderMother spiderMother) {
                if (spiderMother.getHealth() < spiderMother.getMaxHealth() * 0.5F) {
                    spiderMother.heal(event.getNewDamage());
                }
            }
        }

        if (source.getEntity() instanceof Player player) {
            ItemStack itemInHand = player.getItemInHand(player.getUsedItemHand());
            Set<Holder<Enchantment>> holderSet = itemInHand.getTagEnchantments().keySet();
            if (holderSet.contains(TAEnchantments.get(player.level(), TAEnchantments.LEGENDARY_HERO))) {
                List<LivingEntity> entities = player.level().getEntitiesOfClass(
                        LivingEntity.class, player.getBoundingBox().inflate(20.0D),
                        e -> e instanceof Player && e != player || e instanceof Villager);
                event.setNewDamage(event.getOriginalDamage() + Math.min(entities.size(), 10));
            }

            Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
            curiosInventory.orElseThrow().findFirstCurio(TAItems.CRIMSON_PACT_PENDANT.get()).ifPresent(
                    slotResult -> player.setHealth(player.getHealth() + event.getNewDamage() * 0.25F));
            if (player.getMainHandItem().is(TAItems.AURORIAN_ALLOY_STEEL_SWORD)) {
                event.setNewDamage(0.0F);
            }
        }

        if (target.hasEffect(TAMobEffects.CORRUPTION)) {
            AttachmentType<Float> type = TAAttachmentTypes.DAMAGE_ACCUMULATION.get();
            target.setData(type, target.getData(type) + event.getNewDamage());
            if (Objects.requireNonNull(target.getEffect(TAMobEffects.CORRUPTION)).getDuration() > 10) {
                event.setNewDamage(0.0F);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamagePost(LivingDamageEvent.Post event) {
        if (event.getEntity() instanceof Player player && !player.isCreative()) {
            Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
            if (event.getNewDamage() > 0.0F && curiosInventory.isPresent()) {
                ICuriosItemHandler itemHandler = curiosInventory.get();
                player.setData(TAAttachmentTypes.TRIGGER_CRITICAL_HIT, true);
                itemHandler.findFirstCurio(stack -> stack.has(TADataComponents.RUNESTONE_WATER)).ifPresent(slotResult -> {
                    RunestoneWater runestoneWater = slotResult.stack().get(TADataComponents.RUNESTONE_WATER);
                    player.heal(runestoneWater == null ? 0.0F : runestoneWater.getHealValue());
                });
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity sourceEntity = event.getSource().getEntity();
        LivingEntity entity = event.getEntity();
        if (entity instanceof ServerPlayer player) {
            Level level = player.level();
            if (!player.getAbilities().instabuild) {
                ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
                if (chestItem.getEnchantmentLevel(TAEnchantments.get(level, TAEnchantments.GUARDIAN)) > 0) {
                    PacketDistributor.sendToPlayer(player, new DisplayItemActivationS2CPacket(chestItem));
                    player.setHealth(player.getMaxHealth());
                    chestItem.setCount(0);
                    event.setCanceled(true);
                }

                AttachmentType<Integer> attachment = TAAttachmentTypes.TRIGGER_CORRUPTION_COOLDOWN.get();
                if (TAInventoryUtils.isWearFullArmor(player, SpectralArmor.class) && player.getData(attachment) <= 0) {
                    ResourceLocation texture = TheAurorian.prefix("textures/mob_effect/corruption.png");
                    PacketDistributor.sendToPlayer(player, new DisplayTextureActivationS2CPacket(texture, 40));
                    player.addEffect(new MobEffectInstance(TAMobEffects.CORRUPTION, 200));
                    player.setData(attachment, 6000);
                    player.setHealth(1.0F);
                    event.setCanceled(true);
                }

                Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
                curiosInventory.orElseThrow().findFirstCurio(TAItems.CRIMSON_PACT_PENDANT.get()).map(SlotResult::stack).ifPresent(itemStack -> {
                    PacketDistributor.sendToPlayer(player, new DisplayItemActivationS2CPacket(itemStack));
                    player.addEffect(new MobEffectInstance(TAMobEffects.CORRUPTION, 200));
                    player.setHealth(1.0F);
                    itemStack.setCount(0);
                    event.setCanceled(true);
                });
            }

            if (level.dimension() == TADimensions.AURORIAN_DIMENSION) {
                TAServerPlayer.die(player, event.getSource());
                event.setCanceled(true);
            }
        }

        if (sourceEntity instanceof AbstractAurorianBoss boss) {
            boss.onKilledTarget(entity);
        }

        if (sourceEntity instanceof Player player) {
            Level level = player.level();
            ItemStack stack = player.getUseItem();
            if (stack.is(TAItems.TSLAT_SWORD.get())) {
                DataComponentType<Integer> type = TADataComponents.KILL_COUNT.get();
                stack.set(type, stack.getOrDefault(type, 0) + 1);
            }

            if (stack.is(TAItems.AURORIAN_STEEL_SWORD)) {
                MobEffectInstance holinessEffect = player.getEffect(TAMobEffects.HOLINESS);
                boolean flag = entity.getType().is(EntityTypeTags.UNDEAD);
                if (holinessEffect != null) {
                    holinessEffect.duration += flag ? 60 : 30;
                }
            }

            if (TAWorldEvents.BLOOD_MOON.get().isActive(level)) {
                AttachmentType<Integer> attachmentType = TAAttachmentTypes.KILL_COUNT_IN_BLOOD_MOON.get();
                ShieldStack shieldStack = player.getData(TAAttachmentTypes.CURRENT_SHIELD);
                player.setData(attachmentType, player.getData(attachmentType) + 1);
                WorldEventData eventData = level.getData(TAAttachmentTypes.WORLD_EVENT_DATA);
                Map<UUID, Integer> killCountInBloodMoons = eventData.killCountInBloodMoons;
                killCountInBloodMoons.put(player.getUUID(), player.getData(attachmentType));
                if (player instanceof ServerPlayer serverPlayer) {
                    shieldStack.increaseShield(2.0F);
                    PacketDistributor.sendToPlayer(serverPlayer, new UpdateShieldValueS2CPacket(shieldStack.getShieldValue()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        Entity sourceEntity = event.getSource().getEntity();
        if (sourceEntity instanceof MoonQueen) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        if (source.is(DamageTypes.FREEZE) && target.hasEffect(TAMobEffects.WARM)) event.setCanceled(true);
        if (TAInventoryUtils.canArmorTriggerEffect(target, SpectralArmor.class, 0.06D)) {
            target.getActiveEffects().stream().map(MobEffectInstance::getEffect)
                    .filter(effect -> effect.value().getCategory() == MobEffectCategory.HARMFUL)
                    .forEach(target::removeEffect);
        }

        if (target.isAlive() && source.getEntity() instanceof Player player) {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(TAItems.TSLAT_SWORD.get()) && !target.isDamageSourceBlocked(source)) {
                int count = Mth.clamp(stack.getOrDefault(TADataComponents.KILL_COUNT, 0), 0, 20);
                target.setHealth(target.getHealth() - count * 0.05F);
            }

            if (stack.is(TAItems.AURORIAN_ALLOY_STEEL_SWORD)) {
                float health = target.getHealth();
                float damage = health * 0.1F;
                target.getCombatTracker().recordDamage(source, damage);
                target.setHealth(health - damage);
                target.gameEvent(GameEvent.ENTITY_DAMAGE);
            }
        }

        if (target instanceof Player player) {
            CuriosApi.getCuriosInventory(player).ifPresent(itemHandler -> {
                DataComponentType<RunestoneIce> component = TADataComponents.RUNESTONE_ICE.get();
                itemHandler.findFirstCurio(stack -> stack.has(component)).ifPresent(slotResult -> {
                    RunestoneIce runestoneIce = slotResult.stack().get(component);
                    if (runestoneIce != null) {
                        float amount = event.getAmount();
                        float reduction = runestoneIce.calculateDamageReduction(amount);
                        event.setAmount(amount - reduction);
                    }
                });
            });
        }
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player && player.isShiftKeyDown() && player.onGround()) {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.FEET);
            if (stack.is(TAItems.AURORIAN_SLIME_BOOTS) && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                float jumpPower = player.getJumpPower(2.0F);
                Vec3 vec3 = player.getDeltaMovement();
                player.setDeltaMovement(vec3.x, jumpPower, vec3.z);
                player.getCooldowns().addCooldown(stack.getItem(), 100);
            }
        }
    }

    @SubscribeEvent
    public static void onArmorHurt(ArmorHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(TAMobEffects.CORRUPTION)) {
            float damage = 0.0F;
            for (EquipmentSlot slot : event.getArmorMap().keySet()) {
                damage += event.getOriginalDamage(slot);
            }

            AttachmentType<Float> attachment = TAAttachmentTypes.ARMOR_HURT_ACCUMULATION.get();
            entity.setData(attachment, entity.getData(attachment) + damage);
            event.setCanceled(true);
        }

        if (entity.hasEffect(TAMobEffects.TOUGH)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if (rayTraceResult instanceof EntityHitResult result) {
            Projectile projectile = event.getProjectile();
            if (result.getEntity() instanceof LivingEntity livingEntity) {
                boolean flag = projectile instanceof ThrownEgg || projectile instanceof Snowball;
                if (flag && projectile.getOwner() instanceof Player player && player.hasEffect(TAMobEffects.PARALYSIS)) {
                    livingEntity.hurt(livingEntity.damageSources().thrown(projectile, player), 1.0F);
                }

                if (projectile instanceof AbstractArrow arrow) {
                    ItemStack weaponItem = arrow.getWeaponItem();
                    if (weaponItem != null && weaponItem.is(TAItems.KEEPERS_BOW)) {
                        livingEntity.invulnerableTime = 0;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerPickupItem(ItemEntityPickupEvent.Pre event) {
        if (event.getPlayer().hasEffect(TAMobEffects.REJECTUM)) {
            event.setCanPickup(TriState.FALSE);
        }
    }

}