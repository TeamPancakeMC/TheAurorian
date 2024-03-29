package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.effect.CorruptionEffect;
import cn.teampancake.theaurorian.common.effect.ForbiddenCurseEffect;
import cn.teampancake.theaurorian.common.effect.TAMobEffect;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrab;
import cn.teampancake.theaurorian.common.entities.projectile.ThrownAxe;
import cn.teampancake.theaurorian.common.entities.projectile.WebbingEntity;
import cn.teampancake.theaurorian.common.entities.technical.SitEntity;
import cn.teampancake.theaurorian.common.items.TAArmorMaterials;
import cn.teampancake.theaurorian.common.network.TAMessages;
import cn.teampancake.theaurorian.common.network.message.FrostbiteSyncMessage;
import cn.teampancake.theaurorian.common.registry.TACapability;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.utils.AurorianSteelHelper;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
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
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class EntityEventSubscriber {

    @SubscribeEvent
    public static void onPlayerTicking(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer serverPlayer && serverPlayer.isAlive()) {
            Enchantment enchantment = TAEnchantments.SPRING_OF_LIFE.get();
            ResourceLocation enchantmentId = EnchantmentHelper.getEnchantmentId(enchantment);
            ItemStack chestItem = serverPlayer.getItemBySlot(EquipmentSlot.CHEST);
            CompoundTag compoundTag = chestItem.getOrCreateTag();
            Inventory inventory = serverPlayer.getInventory();
            float maxHealth = serverPlayer.getMaxHealth();
            int count = compoundTag.getInt("EnchantArmorHealCount");
            int level = chestItem.getEnchantmentLevel(enchantment);
            boolean flag = serverPlayer.getHealth() < maxHealth * 0.1F;
            boolean shouldHealPlayer = compoundTag.getBoolean("ShouldHealPlayer");
            List<NonNullList<ItemStack>> compartments = ImmutableList.of(
                    inventory.items, inventory.armor, inventory.offhand);
            if (flag && level > 0 && !shouldHealPlayer) {
                compoundTag.putBoolean("ShouldHealPlayer", true);
            }

            if (shouldHealPlayer && level > 0) {
                compoundTag.putInt("EnchantArmorHealCount", count + 1);
                serverPlayer.heal((maxHealth * 0.01F));
            }

            for (NonNullList<ItemStack> nonNullList : compartments) {
                for (ItemStack stack : nonNullList) {
                    if (stack.getEnchantmentLevel(enchantment) > 0) {
                        CompoundTag tag = stack.getOrCreateTag();
                        boolean flag1 = tag.getInt("EnchantArmorHealCount") > 100;
                        boolean flag2 = tag.getBoolean("ShouldHealPlayer");
                        if (flag1 && flag2) {
                            ListTag listTag = stack.getEnchantmentTags();
                            for (int i = 0; i < listTag.size(); i++) {
                                CompoundTag tempTag = listTag.getCompound(i);
                                String id = tempTag.getString("id");
                                if (id.equals(String.valueOf(enchantmentId))) {
                                    listTag.remove(tempTag);
                                }
                            }

                            tag.putBoolean("ShouldHealPlayer", false);
                            tag.putInt("EnchantArmorHealCount", 0);
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
        boolean flag = originalPlayer.hasEffect(TAMobEffects.POTION_REMAIN.get());
        if (event.isWasDeath() && !activeEffects.isEmpty() && flag) {
            originalPlayer.getActiveEffects().forEach(newPlayer::addEffect);
        }
    }

    @SubscribeEvent
    public static void onPlayerPickupXp(PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        ExperienceOrb orb = event.getOrb();
        int i = EnchantmentHelper.getEnchantmentLevel(TAEnchantments.EXPERIENCE_ORE.get(), player);
        if (orb.value > 0 && i > 0 && player.getRandom().nextFloat() < i * 0.08F) {
            orb.value *= 2;
        }
    }

    @SubscribeEvent
    public static void onPlayerXpChange(PlayerXpEvent.XpChange event) {
        Player player = event.getEntity();
        int amount = event.getAmount();
        int i = EnchantmentHelper.getEnchantmentLevel(TAEnchantments.CLEAR_MIND.get(), player);
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
        if (itemInHand.getEnchantmentLevel(TAEnchantments.SOURCE_OF_TERRA.get()) > 0) {
            Container container = HopperBlockEntity.getContainerAt(level, pos);
            CompoundTag compoundTag = itemInHand.getOrCreateTag();
            if (container != null && player.isShiftKeyDown()) {
                compoundTag.putInt("SOT_SelectedX", pos.getX());
                compoundTag.putInt("SOT_SelectedY", pos.getY());
                compoundTag.putInt("SOT_SelectedZ", pos.getZ());
                compoundTag.put("SOT_SelectedContainer", NbtUtils.writeBlockState(state));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(TAEnchantments.ROUNDABOUT_THROW.get(), player);
        if (stack.getItem() instanceof AxeItem && enchantmentLevel > 0) {
            Level level = player.level();
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
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (!level.isClientSide()) {
            MobEffect effect = TAMobEffects.PARALYSIS.get();
            if (entity.hasEffect(effect) && entity.getVehicle() == null) {
                SitEntity sitEntity = new SitEntity(level, entity.getOnPos(), 0.7D);
                level.addFreshEntity(sitEntity);
                entity.startRiding(sitEntity);
            }
        }

        entity.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
            MobEffect effect = TAMobEffects.CORRUPTION.get();
            int validTime = miscNBT.validCorruptionTime;
            int i = miscNBT.ticksFrostbite;
            int j = miscNBT.corruptionTime;
            if (!level.isClientSide) {
                ItemStack chestItem = entity.getItemBySlot(EquipmentSlot.CHEST);
                if (chestItem.getEnchantmentLevel(TAEnchantments.MOLTEN_CORE.get()) > 0) {
                    int tick = miscNBT.ticksThermalEnhancement;
                    boolean isInNether = level.dimension() == Level.NETHER;
                    boolean flag = entity.isOnFire() || entity.isInLava() || isInNether;
                    miscNBT.ticksThermalEnhancement = flag ? 80 : Math.max(tick - 1, 0);
                } else {
                    miscNBT.ticksThermalEnhancement = 0;
                }

                if (effect instanceof CorruptionEffect corruption && entity.hasEffect(corruption)) {
                    boolean shouldRemove = level.random.nextFloat() < (j / (validTime + 40.0F));
                    if (entity.tickCount % 20 == 0) {
                        ++miscNBT.corruptionTime;
                    }
                    if (j >= validTime || shouldRemove) {
                        corruption.doHurtTarget(entity);
                        entity.removeEffect(effect);
                    }
                }

                if (i > 0) {
                    miscNBT.ticksFrostbite = Math.max(0, i - 2);
                    if (entity instanceof ServerPlayer serverPlayer) {
                        TAMessages.sendToPlayer(new FrostbiteSyncMessage(i), serverPlayer);
                    }
                }
            }
        });
    }

    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        DamageSource source = event.getDamageSource();
        if (event.getEntity() instanceof Player player && source.getEntity() instanceof SnowTundraGiantCrab) {
            float damage = event.getBlockedDamage();
            ItemStack useItem = player.getUseItem();
            if (useItem.canPerformAction(ToolActions.SHIELD_BLOCK)) {
                if (!player.level().isClientSide) {
                    player.awardStat(Stats.ITEM_USED.get(useItem.getItem()));
                }

                if (damage > 3.0F) {
                    int i = (1 + Mth.floor(damage)) * 3;
                    InteractionHand usedItemHand = player.getUsedItemHand();
                    player.level().broadcastEntityEvent(player, (byte) 29);
                    useItem.hurtAndBreak(i, player, p -> {
                        p.broadcastBreakEvent(usedItemHand);
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
        MobEffect effect = event.getEffectInstance().getEffect();
        List<MobEffect> effects = MoonQueen.getExclusiveEffects();
        final MobEffect holiness = TAMobEffects.HOLINESS.get();
        final MobEffect incantation = TAMobEffects.INCANTATION.get();
        boolean flag1 = effect == incantation && entity.hasEffect(holiness);
        boolean flag2 = effect == holiness && entity.hasEffect(incantation);
        boolean flag3 = effect == TAMobEffects.PARALYSIS.get() && !(entity instanceof Player);
        boolean flag4 = effects.contains(effect) && !(entity instanceof MoonQueen);
        if (flag1 || flag2 || flag3 || flag4) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void onMobEffectAdded(MobEffectEvent.Added event) {
        MobEffect effect = TAMobEffects.CORRUPTION.get();
        MobEffectInstance instance = event.getEffectInstance();
        LivingEntity entity = event.getEntity();
        if (instance.getEffect() == effect) {
            int duration = instance.getDuration();
            entity.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
                int oldValidTime = miscNBT.validCorruptionTime;
                if (!entity.hasEffect(effect) || duration > oldValidTime) {
                    miscNBT.validCorruptionTime = duration;
                }
            });
        }
    }

    @SubscribeEvent
    public static void onMobEffectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance instance = event.getEffectInstance();
        LivingEntity entity = event.getEntity();
        if (instance != null) {
            MobEffect effect = instance.getEffect();
            if (effect == TAMobEffects.PARALYSIS.get()) {
                BlockPos pos = entity.getOnPos();
                if (entity.getVehicle() instanceof SitEntity sitEntity) {
                    entity.moveTo(pos.getX(), pos.above().getY(), pos.getZ());
                    sitEntity.ejectPassengers();
                    sitEntity.discard();
                }
            }

            if (effect == TAMobEffects.CRYSTALLIZATION.get() && entity instanceof ServerPlayer player) {
                player.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
                    AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
                    List<UUID> list = miscNBT.maxHealthSubtractUuids;
                    if (attribute != null && !list.isEmpty()) {
                        miscNBT.maxHealthSubtractUuids.clear();
                        list.forEach(uuid -> {
                            if (attribute.getModifier(uuid) != null) {
                                attribute.removeModifier(uuid);
                            }
                        });
                    }
                });
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
            event.setCanceled(player.hasEffect(TAMobEffects.INCANTATION.get()) || player.hasEffect(TAMobEffects.PRESSURE.get()));
        }
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(TAMobEffects.PARALYSIS.get()) ||
                entity.hasEffect(TAMobEffects.STUN.get())) {
            entity.setDeltaMovement(Vec3.ZERO);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        LivingEntity entity = event.getEntity();
        boolean isHarmfulEffect = source.is(DamageTypes.INDIRECT_MAGIC) || source.is(DamageTypes.MAGIC);
        event.setAmount(TAMobEffect.getDamageAfterMagicAbsorb(entity, source, event.getAmount()));
        event.setCanceled(isHarmfulEffect && entity.hasEffect(TAMobEffects.HOLINESS.get()));
        if (entity instanceof ServerPlayer player) {
            player.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT ->
                    miscNBT.exhaustionAccumulation += source.getFoodExhaustion());
        }

        if (source.getEntity() instanceof LivingEntity livingEntity) {
            ItemStack itemInHand = livingEntity.getItemInHand(livingEntity.getUsedItemHand());
            int heroLevel = itemInHand.getEnchantmentLevel(TAEnchantments.LEGENDARY_HERO.get());
            int savageLevel = itemInHand.getEnchantmentLevel(TAEnchantments.SAVAGE.get());
            int overloadLevel = itemInHand.getEnchantmentLevel(TAEnchantments.OVERLOAD.get());
            int soulSlashLevel = itemInHand.getEnchantmentLevel(TAEnchantments.SOUL_SLASH.get());
            int nightWalkerLevel = itemInHand.getEnchantmentLevel(TAEnchantments.NIGHT_WALKER.get());
            int sunderLevel = itemInHand.getEnchantmentLevel(TAEnchantments.SUNDER_ARMOR_SLASH.get());
            int moltenLevel = EnchantmentHelper.getEnchantmentLevel(TAEnchantments.MOLTEN_CORE.get(), livingEntity);
            float amount = event.getAmount();
            if (amount > 0.0F) {
                livingEntity.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
                    if (miscNBT.ticksThermalEnhancement > 0) {
                        event.setAmount(event.getAmount() + moltenLevel * 3);
                    }
                });

                if (entity.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0D && sunderLevel > 0) {
                    amount = event.getAmount();
                    event.setAmount(amount + amount * sunderLevel * 0.1F);
                }

                if (livingEntity instanceof ServerPlayer serverPlayer && heroLevel > 0) {
                    AABB aabb = serverPlayer.getBoundingBox().inflate(20.0D);
                    List<LivingEntity> canEnhances = serverPlayer.level().getEntitiesOfClass(LivingEntity.class, aabb, object ->
                            object instanceof ServerPlayer player && player != serverPlayer || object instanceof Villager);
                    event.setAmount(event.getAmount() + Math.min(canEnhances.size(), 10));
                }

                if (nightWalkerLevel > 0) {
                    Level level = livingEntity.level();
                    BlockPos pos = livingEntity.getOnPos();
                    if (level.getRawBrightness(pos, 0) < 5) {
                        event.setAmount(event.getAmount() + nightWalkerLevel);
                    }
                }

                if (savageLevel > 0 && (entity instanceof AgeableMob || entity instanceof NeutralMob)) {
                    amount = event.getAmount();
                    event.setAmount(amount + savageLevel * 2.0F);
                }

                if (overloadLevel > 0) {
                    amount = event.getAmount();
                    event.setAmount(amount + amount * overloadLevel);
                }

                if (soulSlashLevel > 0) {
                    amount = event.getAmount();
                    if (RandomSource.create().nextFloat() <= soulSlashLevel * 0.05F) {
                        entity.getCombatTracker().recordDamage(source, amount);
                        entity.setHealth(entity.getHealth() - amount);
                        entity.setAbsorptionAmount(entity.getAbsorptionAmount() - amount);
                        entity.gameEvent(GameEvent.ENTITY_DAMAGE);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        Entity sourceEntity = source.getEntity();
        if (target instanceof Player player && sourceEntity instanceof MoonQueen) {
            event.setAmount(Math.max(1.0F, event.getAmount()));
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
                    if (++miscNBT.uninterruptedHurtByMoonQueenCount >= 10) {
                        MobEffect effect = TAMobEffects.LACERATION.get();
                        serverPlayer.addEffect(new MobEffectInstance(effect, 100));
                    }
                });
            }
        }

        if (target != null) {
            MobEffect effect = TAMobEffects.CORRUPTION.get();
            for (ItemStack piece : target.getArmorSlots()) {
                if (piece.getItem() instanceof ArmorItem armorItem) {
                    if (armorItem.getMaterial() == TAArmorMaterials.AURORIAN_STEEL) {
                        AurorianSteelHelper.handleAurorianSteelDurability(piece);
                    }
                }
            }

            if (target.hasEffect(TAMobEffects.CRYSTALLIZATION.get()) && target instanceof ServerPlayer player) {
                event.setAmount(event.getAmount() * 1.5F);
                player.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
                    AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
                    AttributeModifier.Operation operation = AttributeModifier.Operation.MULTIPLY_TOTAL;
                    if (attribute != null && player.getRandom().nextFloat() <= 0.25F) {
                        AttributeModifier modifier = new AttributeModifier("Crystallization", -0.1D, operation);
                        miscNBT.maxHealthSubtractUuids.add(modifier.getId());
                        attribute.addTransientModifier(modifier);
                    }
                });
            }

            if (target.hasEffect(effect)) {
                target.getCapability(TACapability.MISC_CAP).ifPresent(
                        miscNBT -> miscNBT.damageAccumulation += event.getAmount());
                //Prevent the death message doesn't show.
                if (Objects.requireNonNull(target.getEffect(effect)).getDuration() > 10) {
                    event.setCanceled(true);
                }
            }
        }

        if (sourceEntity instanceof LivingEntity livingEntity) {
            int level = EnchantmentHelper.getEnchantmentLevel(TAEnchantments.REFLECT_AURA.get(), livingEntity);
            if (target != null && level > 0 && livingEntity.getRandom().nextFloat() <= level * 0.01F) {
                float amount = event.getAmount();
                livingEntity.getCombatTracker().recordDamage(source, amount);
                livingEntity.setHealth(livingEntity.getHealth() - amount);
                livingEntity.setAbsorptionAmount(livingEntity.getAbsorptionAmount() - amount);
                livingEntity.gameEvent(GameEvent.ENTITY_DAMAGE);
            }

            if (livingEntity instanceof SpiderMother spiderMother) {
                if (spiderMother.getHealth() < spiderMother.getMaxHealth() * 0.5F) {
                    spiderMother.heal(event.getAmount());
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
                    if (effectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
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
            MobEffect effect = TAMobEffects.MOON_BEFALL.get();
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
            int enchantmentLevel = chestItem.getEnchantmentLevel(TAEnchantments.GUARDIAN.get());
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
                if (instance.getModifier(WebbingEntity.MAX_HEALTH_SUBTRACT_WEBBING) != null) {
                    instance.removeModifier(WebbingEntity.MAX_HEALTH_SUBTRACT_WEBBING);
                }
            }

            if (entity instanceof MoonQueen) {
                serverPlayer.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> miscNBT.immuneToPressure = true);
            }

            ItemStack stack = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(TAItems.TSLAT_SWORD.get())) {
                int count = stack.getOrCreateTag().getInt("KillCount");
                stack.getOrCreateTag().putInt("KillCount", count + 1);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        Entity sourceEntity = event.getSource().getEntity();
        if ((entity instanceof AgeableMob || entity instanceof NeutralMob) && sourceEntity instanceof ServerPlayer player) {
            int level = EnchantmentHelper.getEnchantmentLevel(TAEnchantments.SAVAGE.get(), player);
            if (level > 0 && player.getRandom().nextFloat() <= level * 0.1F) {
                event.getDrops().forEach(itemEntity -> entity.level().addFreshEntity(itemEntity));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        event.setCanceled(source.is(DamageTypes.FREEZE) && target.hasEffect(TAMobEffects.WARM.get()));
        if (source.getEntity() instanceof ServerPlayer serverPlayer) {
            ItemStack stack = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(TAItems.TSLAT_SWORD.get()) && !target.isDamageSourceBlocked(source)) {
                int count = stack.getOrCreateTag().getInt("KillCount");
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
                if (flag && projectile.getOwner() instanceof Player player && player.hasEffect(TAMobEffects.PARALYSIS.get())) {
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
            CompoundTag compoundTag = handStack.getOrCreateTag().getCompound("SelectedBlock");
            HolderLookup<Block> blockGetter = player.level().holderLookup(Registries.BLOCK);
            BlockState selected = NbtUtils.readBlockState(blockGetter, compoundTag);
            if (state.is(selected.getBlock()) && !state.isAir()) {
                event.setNewSpeed(event.getOriginalSpeed() * 2.0F);
            }
        }
    }

}