package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.effect.CorruptionEffect;
import cn.teampancake.theaurorian.common.effect.ForbiddenCurseEffect;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrab;
import cn.teampancake.theaurorian.common.entities.projectile.ThrownAxe;
import cn.teampancake.theaurorian.common.entities.technical.SitEntity;
import cn.teampancake.theaurorian.common.items.armor.MysteriumWoolArmor;
import cn.teampancake.theaurorian.common.items.armor.SpectralArmor;
import cn.teampancake.theaurorian.common.network.FrostbiteS2CPacket;
import cn.teampancake.theaurorian.common.registry.*;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;
import java.util.List;

/** @noinspection deprecation*/
@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class EntityEventSubscriber {

    private static final DataComponentType<CustomData> CUSTOM_DATA = DataComponents.CUSTOM_DATA;

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
        int i = EnchantmentHelper.getEnchantmentLevel(enchantment, player);
        if (orb.value > 0 && i > 0 && player.getRandom().nextFloat() < i * 0.08F) {
            orb.value *= 2;
        }
    }

    @SubscribeEvent
    public static void onPlayerXpChange(PlayerXpEvent.XpChange event) {
        Player player = event.getEntity();
        int amount = event.getAmount();
        Holder<Enchantment> enchantment = TAEnchantments.get(player.level(), TAEnchantments.CLEAR_MIND);
        int i = EnchantmentHelper.getEnchantmentLevel(enchantment, player);
        if (amount > 0 && i > 0 && player.experienceLevel < 30) {
            event.setAmount(amount + Mth.ceil(amount * i * 0.1F));
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        Player player = event.getEntity();
        BlockState state = level.getBlockState(pos);
        ItemStack itemInHand = player.getItemInHand(event.getHand());
        if (itemInHand.getEnchantmentLevel(TAEnchantments.get(level, TAEnchantments.SOURCE_OF_TERRA)) > 0) {
            CompoundTag compoundTag = itemInHand.getOrDefault(CUSTOM_DATA, CustomData.EMPTY).copyTag();
            if (HopperBlockEntity.getContainerAt(level, pos) != null && player.isShiftKeyDown()) {
                compoundTag.put("selected_container", NbtUtils.writeBlockState(state));
                compoundTag.putInt("selected_x", pos.getX());
                compoundTag.putInt("selected_y", pos.getY());
                compoundTag.putInt("selected_z", pos.getZ());
                itemInHand.set(CUSTOM_DATA, CustomData.of(compoundTag));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        Level level = player.level();
        Holder<Enchantment> enchantment = TAEnchantments.get(level, TAEnchantments.ROUNDABOUT_THROW);
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(enchantment, player);
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
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Cat cat && cat.temptGoal != null) {

        }
    }

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            Level level = entity.level();
            if (!level.isClientSide()) {
                int i = entity.getData(TAAttachmentTypes.TICKS_FROSTBITE);
                if (entity.hasEffect(TAMobEffects.PARALYSIS) && entity.getVehicle() == null) {
                    SitEntity sitEntity = new SitEntity(level, entity.getOnPos(), 0.7D);
                    level.addFreshEntity(sitEntity);
                    entity.startRiding(sitEntity);
                }

                if (i > 0) {
                    entity.setData(TAAttachmentTypes.TICKS_FROSTBITE, Math.max(0, i - 2));
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
        if (event.getEntity() instanceof Player player && source.getEntity() instanceof SnowTundraGiantCrab) {
            float damage = event.getBlockedDamage();
            ItemStack useItem = player.getUseItem();
            if (useItem.canPerformAction(ItemAbilities.SHIELD_BLOCK)) {
                if (!player.level().isClientSide) {
                    player.awardStat(Stats.ITEM_USED.get(useItem.getItem()));
                }

                if (damage > 3.0F && player.level() instanceof ServerLevel serverLevel) {
                    int i = (1 + Mth.floor(damage)) * 3;
                    InteractionHand usedItemHand = player.getUsedItemHand();
                    player.level().broadcastEntityEvent(player, (byte) 29);
                    useItem.hurtAndBreak(i, serverLevel, player, item -> {
                        player.onEquippedItemBroken(item, Player.getSlotForHand(usedItemHand));
                        player.stopUsingItem();
                    });

                    if (useItem.isEmpty() || player.getRandom().nextInt(100) < 2) {
                        float pitch = 0.8F + player.level().random.nextFloat() * 0.4F;
                        EquipmentSlot slot = usedItemHand == InteractionHand.MAIN_HAND ?
                                EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                        player.playSound(SoundEvents.SHIELD_BREAK, 0.8F, pitch);
                        player.setItemSlot(slot, ItemStack.EMPTY);
                        player.stopUsingItem();
                    }
                }
            }

            event.setCanceled(true);
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
        List<Holder<MobEffect>> effects = MoonQueen.getExclusiveEffects();
        Holder<MobEffect> effect = Objects.requireNonNull(event.getEffectInstance()).getEffect();
        boolean flag1 = effect.is(TAMobEffects.INCANTATION) && entity.hasEffect(TAMobEffects.HOLINESS);
        boolean flag2 = effect.is(TAMobEffects.HOLINESS) && entity.hasEffect(TAMobEffects.INCANTATION);
        boolean flag3 = effect.is(TAMobEffects.PARALYSIS) && !(entity instanceof Player);
        boolean flag4 = effects.contains(effect) && !(entity instanceof MoonQueen);
        if (flag1 || flag2 || flag3 || flag4) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    @SubscribeEvent
    public static void onMobEffectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance instance = event.getEffectInstance();
        LivingEntity entity = event.getEntity();
        if (instance != null) {
            if (instance.is(TAMobEffects.PARALYSIS)) {
                BlockPos pos = entity.getOnPos();
                if (entity.getVehicle() instanceof SitEntity sitEntity) {
                    entity.moveTo(pos.getX(), pos.above().getY(), pos.getZ());
                    sitEntity.ejectPassengers();
                    sitEntity.discard();
                }
            }

            if (entity instanceof MoonQueen moonQueen && moonQueen.duelingMoment && MoonQueen.BUFF_LIST.contains(instance)) {
                moonQueen.addEffect(MoonQueen.BUFF_LIST.get(entity.getRandom().nextInt(MoonQueen.BUFF_LIST.size())));
                moonQueen.triggerAnim("buff_controller", "buff_animation");
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
        boolean enchantmentFlag = TAEntityUtils.canTriggerEnchantmentEffect(entity, TAEnchantments.VIRTUALIZATION);
        if (isHarmfulEffect && entity.hasEffect(TAMobEffects.HOLINESS) || enchantmentFlag) {
            event.setNewDamage(0.0F);
        }

        if (entity instanceof Player player) {
            AttachmentType<Float> type = TAAttachmentTypes.EXHAUSTION_ACCUMULATION.get();
            player.setData(type, player.getData(type) + source.getFoodExhaustion());
            if (SpectralArmor.isWearSpectralArmor(player)) {
                player.getActiveEffects().stream().filter(instance -> {
                    MobEffect effect = instance.getEffect().value();
                    return effect.getCategory() == MobEffectCategory.HARMFUL;
                }).forEach(instance -> player.removeEffect(instance.getEffect()));
            }
        }

        if (source.getEntity() instanceof Player player) {
            ItemStack itemInHand = entity.getItemInHand(entity.getUsedItemHand());
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
            if (TAEntityUtils.canTriggerEnchantmentEffect(target, TAEnchantments.REFLECT_AURA)) {
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

        if (entity instanceof ServerPlayer serverPlayer) {
            ItemStack chestItem = serverPlayer.getItemBySlot(EquipmentSlot.CHEST);
            Holder<Enchantment> enchantment = TAEnchantments.get(entity.level(), TAEnchantments.GUARDIAN);
            int enchantmentLevel = chestItem.getEnchantmentLevel(enchantment);
            if (enchantmentLevel > 0 && !serverPlayer.getAbilities().instabuild) {
                serverPlayer.setHealth(serverPlayer.getMaxHealth());
                chestItem.setCount(0);
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
        event.setCanceled(sourceEntity instanceof MoonQueen);
        if ((entity instanceof AgeableMob || entity instanceof NeutralMob) && sourceEntity instanceof ServerPlayer player) {
            Holder<Enchantment> enchantment = TAEnchantments.get(entity.level(), TAEnchantments.SAVAGE);
            int level = EnchantmentHelper.getEnchantmentLevel(enchantment, player);
            if (level > 0 && player.getRandom().nextFloat() <= level * 0.1F) {
                event.getDrops().forEach(itemEntity -> entity.level().addFreshEntity(itemEntity));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        event.setCanceled(source.is(DamageTypes.FREEZE) && target.hasEffect(TAMobEffects.WARM));
        if (target.isAlive() && source.getEntity() instanceof ServerPlayer player) {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(TAItems.TSLAT_SWORD.get()) && !target.isDamageSourceBlocked(source)) {
                int count = Mth.clamp(stack.getOrDefault(TADataComponents.KILL_COUNT, 0), 0, 20);
                target.setHealth(target.getHealth() - count * 0.05F);
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

}