package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.effect.CorruptionEffect;
import cn.teampancake.theaurorian.common.effect.ForbiddenCurseEffect;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.monster.MoonAcolyte;
import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrab;
import cn.teampancake.theaurorian.common.entities.monster.Spirit;
import cn.teampancake.theaurorian.common.entities.projectile.ThrownAxe;
import cn.teampancake.theaurorian.common.entities.projectile.WebbingEntity;
import cn.teampancake.theaurorian.common.entities.technical.SitEntity;
import cn.teampancake.theaurorian.common.items.TAArmorMaterials;
import cn.teampancake.theaurorian.common.network.FrostbiteS2CPacket;
import cn.teampancake.theaurorian.common.registry.*;
import cn.teampancake.theaurorian.common.utils.AurorianSteelHelper;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
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
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
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

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class EntityEventSubscriber {

    private static final DataComponentType<CustomData> CUSTOM_DATA = DataComponents.CUSTOM_DATA;

    @SubscribeEvent
    public static void onPlayerTicking(PlayerTickEvent.Pre event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer && serverPlayer.isAlive()) {
            ResourceKey<Enchantment> enchantment = TAEnchantments.SPRING_OF_LIFE;
            Holder<Enchantment> holder = TAEnchantments.get(serverPlayer.level(), enchantment);
            ItemStack chestItem = serverPlayer.getItemBySlot(EquipmentSlot.CHEST);
            CompoundTag compoundTag = chestItem.getOrDefault(CUSTOM_DATA, CustomData.EMPTY).copyTag();
            Inventory inventory = serverPlayer.getInventory();
            float maxHealth = serverPlayer.getMaxHealth();
            int count = compoundTag.getInt("enchant_armor_heal_amount");
            int level = chestItem.getEnchantmentLevel(holder);
            boolean flag = serverPlayer.getHealth() < maxHealth * 0.1F;
            boolean shouldHealPlayer = compoundTag.getBoolean("should_heal_player");
            List<NonNullList<ItemStack>> compartments = ImmutableList.of(
                    inventory.items, inventory.armor, inventory.offhand);
            if (flag && level > 0 && !shouldHealPlayer) {
                compoundTag.putBoolean("should_heal_player", true);
                chestItem.set(CUSTOM_DATA, CustomData.of(compoundTag));
            }

            if (shouldHealPlayer && level > 0) {
                compoundTag.putInt("enchant_armor_heal_amount", count + 1);
                chestItem.set(CUSTOM_DATA, CustomData.of(compoundTag));
                serverPlayer.heal((maxHealth * 0.01F));
            }

            for (NonNullList<ItemStack> nonNullList : compartments) {
                for (ItemStack stack : nonNullList) {
                    if (stack.getEnchantmentLevel(holder) > 0) {
                        CompoundTag tag = stack.getOrDefault(CUSTOM_DATA, CustomData.EMPTY).copyTag();
                        if (tag.getInt("enchant_armor_heal_amount") > 100 && tag.getBoolean("should_heal_player")) {
                            stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).keySet().remove(holder);
                            tag.putBoolean("should_heal_player", false);
                            tag.putInt("enchant_armor_heal_amount", 0);
                            stack.set(CUSTOM_DATA, CustomData.of(tag));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        Player originalPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();
        Collection<MobEffectInstance> activeEffects = originalPlayer.getActiveEffects();
        boolean flag = originalPlayer.hasEffect(TAMobEffects.POTION_REMAIN);
        if (event.isWasDeath() && !activeEffects.isEmpty() && flag) {
            originalPlayer.getActiveEffects().forEach(newPlayer::addEffect);
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
        Level level = event.getLevel();
        if (!level.isClientSide && level.isNight()) {
            boolean isFullMoon = level.getMoonBrightness() == 1.0F;
            if (level.dimension() == Level.OVERWORLD && isFullMoon) {
                Entity entity = event.getEntity();
                if (entity instanceof Pig pig) {
                    TAEntityUtils.convertWithExtraData(TAEntityTypes.AURORIAN_PIG.get(), pig);
                } else if (entity instanceof Cow cow) {
                    TAEntityUtils.convertWithExtraData(TAEntityTypes.AURORIAN_COW.get(), cow);
                } else if (entity instanceof Sheep sheep) {
                    TAEntityUtils.convertWithExtraData(TAEntityTypes.AURORIAN_SHEEP.get(), sheep);
                } else if (entity instanceof Rabbit rabbit) {
                    TAEntityUtils.convertWithExtraData(TAEntityTypes.AURORIAN_RABBIT.get(), rabbit);
                } else if (entity instanceof Chicken) {
                    event.setCanceled(true);
                } else if (entity instanceof MoonAcolyte || entity instanceof Spirit) {
                    entity.setData(TAAttachmentTypes.SPAWN_IN_OVERWORLD, true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            Level level = entity.level();
            if (!level.isClientSide()) {
                if (entity.hasEffect(TAMobEffects.PARALYSIS) && entity.getVehicle() == null) {
                    SitEntity sitEntity = new SitEntity(level, entity.getOnPos(), 0.7D);
                    level.addFreshEntity(sitEntity);
                    entity.startRiding(sitEntity);
                }

                if (entity.getData(TAAttachmentTypes.SPAWN_IN_OVERWORLD) && level.isDay()) {
                    entity.kill();
                }
            }

            MobEffect effect = TAMobEffects.CORRUPTION.get();
            AttachmentType<Integer> type = TAAttachmentTypes.CORRUPTION_TIME.get();
            int validTime = entity.getData(TAAttachmentTypes.VALID_CORRUPTION_TIME);
            int i = entity.getData(TAAttachmentTypes.TICKS_FROSTBITE);
            int j = entity.getData(TAAttachmentTypes.CORRUPTION_TIME);
            if (!level.isClientSide) {
                if (effect instanceof CorruptionEffect corruption && entity.hasEffect(TAMobEffects.CORRUPTION)) {
                    boolean shouldRemove = level.random.nextFloat() < (j / (validTime + 40.0F));
                    if (entity.tickCount % 20 == 0) {
                        int data = entity.getData(type);
                        entity.setData(type, data + 1);
                    }

                    if (j >= validTime || shouldRemove) {
                        corruption.doHurtTarget(entity);
                        entity.removeEffect(TAMobEffects.CORRUPTION);
                    }
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
    public static void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        Holder<MobEffect> effect = Objects.requireNonNull(event.getEffectInstance()).getEffect();
        List<Holder<MobEffect>> effects = MoonQueen.getExclusiveEffects();
        final Holder<MobEffect> holiness = TAMobEffects.HOLINESS;
        final Holder<MobEffect> incantation = TAMobEffects.INCANTATION;
        boolean flag1 = effect == incantation && entity.hasEffect(holiness);
        boolean flag2 = effect == holiness && entity.hasEffect(incantation);
        boolean flag3 = effect == TAMobEffects.PARALYSIS.get() && !(entity instanceof Player);
        boolean flag4 = effects.contains(effect) && !(entity instanceof MoonQueen);
        if (flag1 || flag2 || flag3 || flag4) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    @SubscribeEvent
    public static void onMobEffectAdded(MobEffectEvent.Added event) {
        MobEffectInstance instance = event.getEffectInstance();
        LivingEntity entity = event.getEntity();
        AttachmentType<Integer> type = TAAttachmentTypes.VALID_CORRUPTION_TIME.get();
        if (instance != null && instance.getEffect() == TAMobEffects.CORRUPTION) {
            int duration = instance.getDuration();
            int oldValidTime = entity.getData(type);
            if (!entity.hasEffect(TAMobEffects.CORRUPTION) || duration > oldValidTime) {
                entity.setData(type, duration);
            }
        }
    }

    @SubscribeEvent
    public static void onMobEffectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance instance = event.getEffectInstance();
        LivingEntity entity = event.getEntity();
        if (instance != null) {
            Holder<MobEffect> effect = instance.getEffect();
            if (effect == TAMobEffects.PARALYSIS.get()) {
                BlockPos pos = entity.getOnPos();
                if (entity.getVehicle() instanceof SitEntity sitEntity) {
                    entity.moveTo(pos.getX(), pos.above().getY(), pos.getZ());
                    sitEntity.ejectPassengers();
                    sitEntity.discard();
                }
            }

            if (effect == TAMobEffects.CRYSTALLIZATION.get() && entity instanceof ServerPlayer player) {
                AttachmentType<List<ResourceLocation>> type = TAAttachmentTypes.MAX_HEALTH_SUBTRACT_IDS.get();
                AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
                List<ResourceLocation> list = player.getData(type);
                if (attribute != null && !list.isEmpty()) {
                    player.getData(type).clear();
                    list.forEach(id -> {
                        if (attribute.getModifier(id) != null) {
                            attribute.removeModifier(id);
                        }
                    });
                }
            }

            if (effect instanceof CorruptionEffect corruption) {
                corruption.doHurtTarget(entity);
            }

            if (effect instanceof ForbiddenCurseEffect forbiddenCurse && entity instanceof Player player) {
                forbiddenCurse.restorePlayerInventoryItemEnchantments(player);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event) {
        if (event.getEntity() instanceof Player player && player.level().getDifficulty() != Difficulty.PEACEFUL) {
            event.setCanceled(player.hasEffect(TAMobEffects.INCANTATION) || player.hasEffect(TAMobEffects.PRESSURE));
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent.Pre event) {
        DamageSource source = event.getSource();
        LivingEntity entity = event.getEntity();
        boolean isHarmfulEffect = source.is(DamageTypes.INDIRECT_MAGIC) || source.is(DamageTypes.MAGIC);
        if (isHarmfulEffect && entity.hasEffect(TAMobEffects.HOLINESS)) {
            event.setNewDamage(0.0F);
        }

        if (entity instanceof ServerPlayer player) {
            AttachmentType<Float> type = TAAttachmentTypes.EXHAUSTION_ACCUMULATION.get();
            player.setData(type, player.getData(type) + source.getFoodExhaustion());
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        Entity sourceEntity = source.getEntity();
        if (target instanceof Player player && sourceEntity instanceof MoonQueen) {
            event.setNewDamage(Math.max(1.0F, event.getNewDamage()));
            if (player instanceof ServerPlayer) {
                AttachmentType<Integer> type = TAAttachmentTypes.UNINTERRUPTED_HURT_BY_MOON_QUEEN_COUNT.get();
                player.setData(type, player.getData(type) + 1);
                if (player.getData(type) >= 10) {
                    player.addEffect(new MobEffectInstance(TAMobEffects.LACERATION, 100));
                }
            }
        }

        Holder<MobEffect> effect = TAMobEffects.CORRUPTION;
        for (ItemStack piece : target.getArmorSlots()) {
            if (piece.getItem() instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial() == TAArmorMaterials.AURORIAN_STEEL) {
                    AurorianSteelHelper.handleAurorianSteelDurability(piece);
                }
            }
        }

        if (target.hasEffect(TAMobEffects.CRYSTALLIZATION) && target instanceof ServerPlayer player) {
            event.setNewDamage(event.getNewDamage() * 1.5F);
            AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
            AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL;
            if (attribute != null && player.getRandom().nextFloat() <= 0.25F) {
                ResourceLocation id = TheAurorian.prefix("crystallization");
                AttributeModifier modifier = new AttributeModifier(id, -0.1D, operation);
                player.getData(TAAttachmentTypes.MAX_HEALTH_SUBTRACT_IDS).add(modifier.id());
                attribute.addTransientModifier(modifier);
            }
        }

        if (target.hasEffect(effect)) {
            AttachmentType<Float> type = TAAttachmentTypes.DAMAGE_ACCUMULATION.get();
            target.setData(type, target.getData(type) + event.getNewDamage());
            //Prevent the death message doesn't show.
            if (Objects.requireNonNull(target.getEffect(effect)).getDuration() > 10) {
                event.setNewDamage(0.0F);
            }
        }

        if (sourceEntity instanceof LivingEntity livingEntity) {
            Holder<Enchantment> enchantment = TAEnchantments.get(livingEntity.level(), TAEnchantments.REFLECT_AURA);
            int level = EnchantmentHelper.getEnchantmentLevel(enchantment, livingEntity);
            if (level > 0 && livingEntity.getRandom().nextFloat() <= level * 0.01F) {
                float amount = event.getNewDamage();
                livingEntity.getCombatTracker().recordDamage(source, amount);
                livingEntity.setHealth(livingEntity.getHealth() - amount);
                livingEntity.setAbsorptionAmount(livingEntity.getAbsorptionAmount() - amount);
                livingEntity.gameEvent(GameEvent.ENTITY_DAMAGE);
            }

            if (livingEntity instanceof SpiderMother spiderMother) {
                if (spiderMother.getHealth() < spiderMother.getMaxHealth() * 0.5F) {
                    spiderMother.heal(event.getNewDamage());
                }
            }

            float chance = 0.00F;
            for (ItemStack piece : livingEntity.getArmorSlots()) {
                if (piece.getItem() instanceof ArmorItem armorItem) {
                    if (armorItem.getMaterial() == TAArmorMaterials.SPECTRAL) {
                        chance += 0.06F;
                    }
                }
            }

            if (chance != 0.00F && livingEntity.getRandom().nextFloat() <= chance) {
                for (MobEffectInstance effectInstance : livingEntity.getActiveEffects()) {
                    if (effectInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                        livingEntity.removeEffect(effectInstance.getEffect());
                    }
                }
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
                    String uuid = player.getStringUUID();
                    moonQueen.killedDuelistUUID.add(uuid);
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
            AttributeInstance instance = serverPlayer.getAttribute(Attributes.MAX_HEALTH);
            if (entity instanceof SpiderMother && instance != null) {
                if (instance.getModifier(WebbingEntity.WEBBING_MODIFIER) != null) {
                    instance.removeModifier(WebbingEntity.WEBBING_MODIFIER);
                }
            }

            if (entity instanceof MoonQueen) {
                serverPlayer.setData(TAAttachmentTypes.IMMUNE_TO_PRESSURE, true);
            }

            ItemStack stack = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(TAItems.TSLAT_SWORD.get())) {
                DataComponentType<Integer> type = TADataComponents.KILL_COUNT.get();
                int count = stack.getOrDefault(type, 0);
                stack.set(type, count + 1);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        Entity sourceEntity = event.getSource().getEntity();
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
        if (source.getEntity() instanceof ServerPlayer serverPlayer) {
            ItemStack stack = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(TAItems.TSLAT_SWORD.get()) && !target.isDamageSourceBlocked(source)) {
                int count = stack.getOrDefault(TADataComponents.KILL_COUNT, 0);
                if (count > 20) {
                    count = 20;
                }

                if (target.isAlive()) {
                    target.setHealth(target.getHealth() - count * 0.05F);
                }
            }
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
        } else if (state.is(TABlockTags.DUNGEON_BRICKS)) {
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