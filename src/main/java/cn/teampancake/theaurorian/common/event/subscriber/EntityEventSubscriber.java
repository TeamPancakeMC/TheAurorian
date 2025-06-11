package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.MysteriumWoolBed;
import cn.teampancake.theaurorian.common.components.SourceOfTerra;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAMobEffectTags;
import cn.teampancake.theaurorian.common.effect.CorruptionEffect;
import cn.teampancake.theaurorian.common.effect.ForbiddenCurseEffect;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrab;
import cn.teampancake.theaurorian.common.entities.projectile.ThrownAxe;
import cn.teampancake.theaurorian.common.entities.technical.SitEntity;
import cn.teampancake.theaurorian.common.items.armor.MysteriumWoolArmor;
import cn.teampancake.theaurorian.common.items.armor.SpectralArmor;
import cn.teampancake.theaurorian.common.items.tool.AurorianSteelSword;
import cn.teampancake.theaurorian.common.level.TAServerPlayer;
import cn.teampancake.theaurorian.common.level.effect.CorruptionEffectInstance;
import cn.teampancake.theaurorian.common.network.FrostbiteS2CPacket;
import cn.teampancake.theaurorian.common.registry.*;
import cn.teampancake.theaurorian.common.utils.EnchantmentUtils;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

/** @noinspection deprecation*/
@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class EntityEventSubscriber {

    @SubscribeEvent
    public static void onPlayerTicking(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && player.level() instanceof ServerLevel level) {
            boolean noImmuneEffect = !player.hasEffect(TAMobEffects.WARM) && !player.hasEffect(TAMobEffects.FROSTBITE);
            boolean isSnowField = level.getBiome(player.blockPosition()).is(TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD);
            if (!player.isCreative() && !player.isSpectator() && !MysteriumWoolArmor.isWearFullArmor(player)) {
                if (noImmuneEffect && isSnowField && player.tickCount % 60 == 0) {
                    player.setData(TAAttachmentTypes.TICKS_FROSTBITE, 140);
                    player.hurt(player.damageSources().freeze(), 1.0F);
                    player.setSharedFlagOnFire(false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerPickupXp(PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        ExperienceOrb orb = event.getOrb();
        Holder<Enchantment> enchantment = TAEnchantments.get(player.level(), TAEnchantments.EXPERIENCE_ORE);
        int i = EnchantmentUtils.getEnchantmentLevel(enchantment, player);
        if (orb.value > 0 && i > 0 && player.getRandom().nextFloat() < i * 0.08F) {
            orb.value *= 2;
        }
    }

    @SubscribeEvent
    public static void onPlayerXpChange(PlayerXpEvent.XpChange event) {
        Player player = event.getEntity();
        int amount = event.getAmount();
        Holder<Enchantment> enchantment = TAEnchantments.get(player.level(), TAEnchantments.CLEAR_MIND);
        int i = EnchantmentUtils.getEnchantmentLevel(enchantment, player);
        if (amount > 0 && i > 0 && player.experienceLevel < 30) {
            event.setAmount(amount + Mth.ceil(amount * i * 0.1F));
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        Player player = event.getEntity();
        ItemStack itemInHand = player.getItemInHand(event.getHand());
        DataComponentType<SourceOfTerra> componentType = TADataComponents.SOURCE_OF_TERRA.get();
        if (itemInHand.getEnchantmentLevel(TAEnchantments.get(level, TAEnchantments.SOURCE_OF_TERRA)) > 0) {
            if (HopperBlockEntity.getContainerAt(level, pos) != null && player.isShiftKeyDown()) {
                SourceOfTerra sourceOfTerra = itemInHand.get(componentType);
                String dimension = level.dimension().location().toString();
                if (sourceOfTerra == null) {
                    itemInHand.set(componentType, new SourceOfTerra(dimension, pos.getX(), pos.getY(), pos.getZ()));
                    checkIfServerPlayerAndSendMessage(player, "message.source_of_terra.bind");
                } else {
                    int selectedX = sourceOfTerra.selectedX();
                    int selectedY = sourceOfTerra.selectedY();
                    int selectedZ = sourceOfTerra.selectedZ();
                    if (selectedX == pos.getX() && selectedY == pos.getY() && selectedZ == pos.getZ()) {
                        checkIfServerPlayerAndSendMessage(player, "message.source_of_terra.unbind");
                        itemInHand.remove(componentType);
                    } else {
                        itemInHand.set(componentType, new SourceOfTerra(dimension, pos.getX(), pos.getY(), pos.getZ()));
                        checkIfServerPlayerAndSendMessage(player, "message.source_of_terra.changed");
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        Level level = player.level();
        Holder<Enchantment> enchantment = TAEnchantments.get(level, TAEnchantments.ROUNDABOUT_THROW);
        int enchantmentLevel = EnchantmentUtils.getEnchantmentLevel(enchantment, player);
        if (stack.getItem() instanceof AxeItem && enchantmentLevel > 0) {
            if (!level.isClientSide) {
                Inventory inventory = player.getInventory();
                player.setItemInHand(event.getHand(), ItemStack.EMPTY);
                double baseDamage = player.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
                double damage = 1.0F + baseDamage * 1.2F;
                int containerSize = inventory.getContainerSize();
                int slot = event.getHand() == InteractionHand.OFF_HAND ? containerSize - 1 : inventory.selected;
                ThrownAxe entity = new ThrownAxe(level, player);
                double y = player.position().y + player.getBbHeight() / 2.0F;
                entity.setPos(player.position().x, y, player.position().z);
                entity.setData((float) damage, player.getUUID(), slot);
                entity.setNoGravity(true);
                entity.setItem(stack);
                entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0.0F);
                level.addFreshEntity(entity);
            }

            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
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
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Cat cat) {
            Predicate<ItemStack> items = stack -> stack.is(TAItems.CAT_BELL);
            TemptGoal temptGoal = new TemptGoal(cat, (1.25F), items, Boolean.FALSE);
            cat.goalSelector.addGoal(0, temptGoal);
        }
    }

    @SubscribeEvent
    public static void onMobFinalizeSpawn(FinalizeSpawnEvent event) {
        Mob mob = event.getEntity();
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
    }

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            Level level = entity.level();
            if (!level.isClientSide()) {
                int i = entity.getData(TAAttachmentTypes.TICKS_FROSTBITE);
                boolean flag = entity.hasEffect(TAMobEffects.PARALYSIS) || entity.hasEffect(TAMobEffects.STUN);
                if (flag && entity.getVehicle() == null) {
                    SitEntity sitEntity = new SitEntity(level);
                    sitEntity.setPos(entity.position());
                    level.addFreshEntity(sitEntity);
                    entity.startRiding(sitEntity);
                }

                if (i > -1) {
                    entity.setData(TAAttachmentTypes.TICKS_FROSTBITE, Math.max(0, i - 10));
                    if (entity instanceof ServerPlayer serverPlayer) {
                        PacketDistributor.sendToPlayer(serverPlayer, new FrostbiteS2CPacket(i));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onShieldBlock(LivingShieldBlockEvent event) {
        DamageSource source = event.getDamageSource();
        if (source.getEntity() instanceof SnowTundraGiantCrab) {
            event.setShieldDamage(event.shieldDamage() * 3);
        }
    }

    @SubscribeEvent
    public static void onMobEffectRemoved(MobEffectEvent.Remove event) {
        MobEffectInstance instance = event.getEffectInstance();
        boolean flag = instance != null && !instance.isInfiniteDuration()
                && instance.getEffect().is(TAMobEffects.CORRUPTION);
        if (flag || event.getEffect().is(TAMobEffects.CORRUPTION)) {
            CorruptionEffect.doHurtTarget(event.getEntity());
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
        if (flag1 || flag2 || flag3 || flag4) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    @SubscribeEvent
    public static void onMobEffectAdded(MobEffectEvent.Added event) {
        try {
            Class<MobEffectEvent> clazz = MobEffectEvent.class;
            Field field = clazz.getDeclaredField("effectInstance");
            field.setAccessible(true);
            if (field.get(event) instanceof MobEffectInstance instance) {
                if (instance.is(TAMobEffects.CORRUPTION)) {
                    field.set(event, new CorruptionEffectInstance(instance));
                }
            }

        } catch (Exception ignored) {}
    }

    @SubscribeEvent
    public static void onMobEffectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance instance = event.getEffectInstance();
        LivingEntity entity = event.getEntity();
        if (instance != null) {
            if (instance.is(TAMobEffects.PARALYSIS) || instance.is(TAMobEffects.STUN)) {
                BlockPos pos = entity.getOnPos();
                if (entity.getVehicle() instanceof SitEntity sitEntity) {
                    entity.moveTo(pos.getX(), pos.above().getY(), pos.getZ());
                    sitEntity.ejectPassengers();
                    sitEntity.discard();
                }
            }

            if (instance.is(TAMobEffects.CRYSTALLIZATION)) {
                List<ResourceLocation> list = entity.getData(TAAttachmentTypes.MAX_HEALTH_SUBTRACT_IDS);
                AttributeInstance attribute = entity.getAttribute(Attributes.MAX_HEALTH);
                if (!list.isEmpty() && attribute != null) {
                    list.forEach(attribute::removeModifier);
                    list.clear();
                }
            }

            if (instance.is(TAMobEffects.CORRUPTION)) {
                CorruptionEffect.doHurtTarget(entity);
            }

            if (instance.is(TAMobEffects.FORBIDDEN_CURSE) && entity instanceof Player player) {
                ForbiddenCurseEffect.restorePlayerInventoryItemEnchantments(player);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent.Pre event) {
        DamageSource source = event.getSource();
        LivingEntity entity = event.getEntity();
        boolean isHarmfulEffect = source.is(DamageTypes.INDIRECT_MAGIC) || source.is(DamageTypes.MAGIC);
        boolean enchantmentFlag = TAEntityUtils.canArmorTriggerEnchantmentEffect(entity, TAEnchantments.VIRTUALIZATION);
        if (isHarmfulEffect && entity.hasEffect(TAMobEffects.HOLINESS) || enchantmentFlag) {
            event.setNewDamage(0.0F);
        }

        if (entity instanceof Player player) {
            AttachmentType<Float> type = TAAttachmentTypes.EXHAUSTION_ACCUMULATION.get();
            player.setData(type, player.getData(type) + source.getFoodExhaustion());
            if (SpectralArmor.isWearSpectralArmor(player)) {
                player.getActiveEffects().stream().map(MobEffectInstance::getEffect)
                        .filter(effect -> effect.value().getCategory() == MobEffectCategory.HARMFUL)
                        .forEach(player::removeEffect);
            }
        }

        if (source.getEntity() instanceof Player player) {
            ItemStack itemInHand = player.getItemInHand(player.getUsedItemHand());
            Set<Holder<Enchantment>> holderSet = itemInHand.getTagEnchantments().keySet();
            if (holderSet.contains(TAEnchantments.get(player.level(), TAEnchantments.LEGENDARY_HERO))) {
                List<LivingEntity> entities = player.level().getEntitiesOfClass(
                        LivingEntity.class, player.getBoundingBox().inflate(20.0D),
                        e -> e instanceof Player && e != player || e instanceof Villager);
                event.setNewDamage(event.getNewDamage() + Math.min(entities.size(), 10));
            }

            if (entity.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0D) {
                Holder<Enchantment> holder = TAEnchantments.get(player.level(), TAEnchantments.SUNDER_ARMOR_SLASH);
                event.setNewDamage(event.getNewDamage() + event.getNewDamage() * itemInHand.getEnchantmentLevel(holder) * 0.1F);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        Entity sourceEntity = source.getEntity();
        Holder<MobEffect> effect = TAMobEffects.CORRUPTION;
        if (target.hasEffect(effect)) {
            AttachmentType<Float> type = TAAttachmentTypes.DAMAGE_ACCUMULATION.get();
            target.setData(type, target.getData(type) + event.getNewDamage());
            //Prevent the death message doesn't show.
            if (Objects.requireNonNull(target.getEffect(effect)).getDuration() > 10) {
                event.setNewDamage(0.0F);
            }
        }

        if (target.hasEffect(TAMobEffects.CRYSTALLIZATION)) {
            event.setNewDamage(event.getOriginalDamage() * 1.5F);
            AttributeInstance attribute = target.getAttribute(Attributes.MAX_HEALTH);
            AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL;
            if (attribute != null && target.getMaxHealth() > 2.0D && Math.random() <= 0.25F) {
                UUID uuid = Mth.createInsecureUUID(RandomSource.createNewThreadLocalInstance());
                ResourceLocation id = TheAurorian.prefix("crystallization-" + uuid);
                AttributeModifier modifier = new AttributeModifier(id, -0.1D, operation);
                target.getData(TAAttachmentTypes.MAX_HEALTH_SUBTRACT_IDS).add(modifier.id());
                attribute.addTransientModifier(modifier);
            }
        }

        if (sourceEntity instanceof LivingEntity entity) {
            if (TAEntityUtils.canArmorTriggerEnchantmentEffect(target, TAEnchantments.REFLECT_AURA)) {
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

            if (entity instanceof MoonQueen) {
                event.setNewDamage(Math.max(1.0F, event.getNewDamage()));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity sourceEntity = event.getSource().getEntity();
        LivingEntity entity = event.getEntity();
        if (sourceEntity instanceof MoonQueen moonQueen) {
            moonQueen.safeTime = 0;
            Holder<MobEffect> effect = TAMobEffects.MOON_BEFALL;
            if (entity instanceof ServerPlayer player) {
                if (moonQueen.hasEffect(effect)) {
                    moonQueen.removeEffect(effect);
                }

                if (moonQueen.duelingMoment) {
                    moonQueen.killedDuelistName.add(player.getName().getString());
                    moonQueen.selectDuelistFromNearestTarget();
                    moonQueen.heal((moonQueen.getMaxHealth() * 0.1F));
                }
            }

            if (!(entity instanceof Player) && !entity.isRemoved()) {
                Level level = entity.level();
                if (entity.isSleeping()) {
                    entity.stopSleeping();
                }

                entity.getCombatTracker().recheckStatus();
                if (level instanceof ServerLevel) {
                    entity.gameEvent(GameEvent.ENTITY_DIE);
                    level.broadcastEntityEvent(entity, (byte)3);
                }

                entity.setPose(Pose.DYING);
                event.setCanceled(true);
            }
        }

        if (entity instanceof ServerPlayer player) {
            Level level = player.level();
            ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
            Holder<Enchantment> enchantment = TAEnchantments.get(entity.level(), TAEnchantments.GUARDIAN);
            int enchantmentLevel = chestItem.getEnchantmentLevel(enchantment);
            if (enchantmentLevel > 0 && !player.getAbilities().instabuild) {
                player.setHealth(player.getMaxHealth());
                chestItem.setCount(0);
                event.setCanceled(true);
            }

            if (level.dimension() == TADimensions.AURORIAN_DIMENSION) {
                TAServerPlayer.die(player, event.getSource());
                event.setCanceled(true);
            }
        }

        if (sourceEntity instanceof SpiderMother spiderMother) {
            spiderMother.heal(entity.getMaxHealth());
        }

        if (sourceEntity instanceof ServerPlayer serverPlayer) {
            ItemStack stack = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(TAItems.TSLAT_SWORD.get())) {
                DataComponentType<Integer> type = TADataComponents.KILL_COUNT.get();
                stack.set(type, stack.getOrDefault(type, 0) + 1);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        Entity sourceEntity = event.getSource().getEntity();
        if (sourceEntity instanceof MoonQueen) {
            event.setCanceled(true);
        }

        if ((entity instanceof AgeableMob || entity instanceof NeutralMob) && sourceEntity instanceof ServerPlayer player) {
            Holder<Enchantment> enchantment = TAEnchantments.get(entity.level(), TAEnchantments.SAVAGE);
            int level = EnchantmentUtils.getEnchantmentLevel(enchantment, player);
            if (level > 0 && player.getRandom().nextFloat() <= level * 0.1F) {
                event.getDrops().forEach(itemEntity -> entity.level().addFreshEntity(itemEntity));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        if (source.is(DamageTypes.FREEZE) && target.hasEffect(TAMobEffects.WARM)) {
            event.setCanceled(true);
        }

        if (target.isAlive() && source.getEntity() instanceof ServerPlayer player) {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(TAItems.TSLAT_SWORD.get()) && !target.isDamageSourceBlocked(source)) {
                int count = Mth.clamp(stack.getOrDefault(TADataComponents.KILL_COUNT, 0), 0, 20);
                target.setHealth(target.getHealth() - count * 0.05F);
            }
        }

        if (source.getEntity() instanceof Player player && player.hasEffect(TAMobEffects.HOLINESS)
                && player.getMainHandItem().getItem() instanceof AurorianSteelSword) {
            MobEffectInstance holinessEffect = player.getEffect(TAMobEffects.HOLINESS);
            if (holinessEffect != null) {
                holinessEffect.duration += 30;
                player.addEffect(holinessEffect);
            }
        }
    }

    @SubscribeEvent
    public static void onArmorHurt(ArmorHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(TAMobEffects.CORRUPTION) || entity.hasEffect(TAMobEffects.TOUGH)) {
            if (entity.hasEffect(TAMobEffects.CORRUPTION)) {
                float damage = 0.0F;
                for (EquipmentSlot slot : event.getArmorMap().keySet()) {
                    damage += event.getOriginalDamage(slot);
                }

                AttachmentType<Float> type = TAAttachmentTypes.ARMOR_HURT_ACCUMULATION.get();
                entity.setData(type, entity.getData(type) + damage);
            }

            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getRayTraceResult() instanceof EntityHitResult result) {
            Projectile projectile = event.getProjectile();
            if (result.getEntity() instanceof LivingEntity livingEntity) {
                boolean flag = projectile instanceof ThrownEgg || projectile instanceof Snowball;
                if (flag && projectile.getOwner() instanceof Player player && player.hasEffect(TAMobEffects.PARALYSIS)) {
                    livingEntity.hurt(livingEntity.damageSources().thrown(projectile, player), 1.0F);
                }

                if (projectile instanceof AbstractArrow arrow && arrow.getData(TAAttachmentTypes.SHOOT_FROM_KEEPERS_BOW.get())) {
                    livingEntity.invulnerableTime = 0;
                }
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
    public static void playerBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        BlockState state = event.getState();
        ItemStack blockStack = new ItemStack(state.getBlock());
        ItemStack handStack = player.getItemInHand(player.getUsedItemHand());
        if (blockStack.is(Tags.Items.ORES) && handStack.is(TAItems.AURORIANITE_PICKAXE.get())) {
            event.setNewSpeed(event.getOriginalSpeed() * 1.4F);
        } else if (state.is(TABlockTags.DUNGEON_BLOCKS)) {
            boolean flag = handStack.is(TAItems.QUEENS_CHIPPER.get());
            event.setNewSpeed(flag ? event.getOriginalSpeed() * 16.0F : 0.0F);
        } else if (handStack.is(TAItems.UMBRA_PICKAXE.get())) {
            CustomData customData = handStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag compoundTag = customData.copyTag().getCompound("selected_block");
            HolderLookup<Block> blockGetter = player.level().holderLookup(Registries.BLOCK);
            BlockState selected = NbtUtils.readBlockState(blockGetter, compoundTag);
            if (state.is(selected.getBlock()) && !state.isAir()) {
                event.setNewSpeed(event.getOriginalSpeed() * 2.0F);
            }
        }
    }

    private static void checkIfServerPlayerAndSendMessage(Player player, String key) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.sendSystemMessage(Component.translatable(key));
        }
    }

}