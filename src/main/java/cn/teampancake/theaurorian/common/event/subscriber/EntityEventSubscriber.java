package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.inventory.AlchemyTableMenu;
import cn.teampancake.theaurorian.common.blocks.MysteriumWoolBed;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABiomeTags;
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
import cn.teampancake.theaurorian.common.items.armor.MysteriumWoolArmor;
import cn.teampancake.theaurorian.common.items.armor.SpectralArmor;
import cn.teampancake.theaurorian.common.level.TAServerPlayer;
import cn.teampancake.theaurorian.common.network.*;
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
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;
import java.util.List;
import java.util.function.Predicate;

/** @noinspection deprecation*/
@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class EntityEventSubscriber {

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
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Cat cat) {
            Predicate<ItemStack> items = stack -> stack.is(TAItems.CAT_BELL);
            TemptGoal temptGoal = new TemptGoal(cat, 1.25F, items, Boolean.FALSE);
            cat.goalSelector.addGoal(0, temptGoal);
        }

        if (event.getEntity() instanceof Spider spider) {
            spider.targetSelector.addGoal(0, new SpiderIgnoreSpectralArmorGoal<>(spider, Mob.class));
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

                    if (k > 0) {
                        player.setData(TAAttachmentTypes.TRIGGER_CORRUPTION_COOLDOWN, k - 1);
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
        if (flag1 || flag2 || flag3 || flag4) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
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
                BlockPos pos = entity.getOnPos();
                if (entity.getVehicle() instanceof SitEntity sitEntity) {
                    entity.moveTo(pos.getX(), pos.above().getY(), pos.getZ());
                    sitEntity.ejectPassengers();
                    sitEntity.discard();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent.Pre event) {
        DamageSource source = event.getSource();
        LivingEntity entity = event.getEntity();
        boolean isHarmfulEffect = source.is(DamageTypes.INDIRECT_MAGIC) || source.is(DamageTypes.MAGIC);
        boolean enchantmentFlag = EnchantmentUtils.canArmorTriggerEnchantmentEffect(entity, TAEnchantments.VIRTUALIZATION);
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
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        Entity sourceEntity = source.getEntity();
        Holder<MobEffect> effect = TAMobEffects.CORRUPTION;
        if (event.getNewDamage() <= 0.0F) return;
        if (target.hasEffect(effect)) {
            AttachmentType<Float> type = TAAttachmentTypes.DAMAGE_ACCUMULATION.get();
            target.setData(type, target.getData(type) + event.getNewDamage());
            //Prevent the death message doesn't show.
            if (Objects.requireNonNull(target.getEffect(effect)).getDuration() > 10) {
                event.setNewDamage(0.0F);
            }
        }

        if (target instanceof Player player) {
            double d0 = Double.MAX_VALUE;
            SpiderlingCrystalShell crystalShell = null;
            AABB aabb = player.getBoundingBox().inflate(32.0D);
            List<SpiderlingCrystalShell> list = player.level().getEntitiesOfClass(SpiderlingCrystalShell.class, aabb);
            for (SpiderlingCrystalShell entity : list) {
                UUID uuid = entity.getOwnerUUID();
                if (uuid != null && uuid.equals(player.getUUID())) {
                    double d1 = entity.distanceToSqr(player);
                    if (d1 < d0) {
                        d0 = d1;
                        crystalShell = entity;
                    }
                }
            }

            if (crystalShell != null) {
                float amount = event.getNewDamage();
                crystalShell.getCombatTracker().recordDamage(source, amount);
                crystalShell.setHealth(crystalShell.getHealth() - amount);
                crystalShell.setAbsorptionAmount(crystalShell.getAbsorptionAmount() - amount);
                crystalShell.gameEvent(GameEvent.ENTITY_DAMAGE);
                event.setNewDamage(0.0F);
            }
        }

        if (sourceEntity instanceof LivingEntity entity) {
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

            if (entity instanceof MoonQueen) {
                event.setNewDamage(Math.max(1.0F, event.getNewDamage()));
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
                if (TAInventoryUtils.isWearFullArmor(player, SpectralArmor.class) && player.getData(attachment) < 0) {
                    ResourceLocation texture = TheAurorian.prefix("textures/mob_effect/corruption.png");
                    PacketDistributor.sendToPlayer(player, new DisplayTextureActivationS2CPacket(texture, 40));
                    player.addEffect(new MobEffectInstance(TAMobEffects.CORRUPTION, 200));
                    player.setData(attachment, 6000);
                    player.setHealth(1.0F);
                    event.setCanceled(true);
                }
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
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        Entity sourceEntity = event.getSource().getEntity();
        if (sourceEntity instanceof MoonQueen) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        if (source.is(DamageTypes.FREEZE) && target.hasEffect(TAMobEffects.WARM)) {
            event.setCanceled(true);
        }

        if (TAInventoryUtils.canArmorTriggerEffect(target, SpectralArmor.class, 0.06D)) {
            target.getActiveEffects().forEach(effectInstance -> {
                Holder<MobEffect> holder = effectInstance.getEffect();
                if (holder.value().getCategory() == MobEffectCategory.HARMFUL) {
                    target.removeEffect(holder);
                }
            });
        }

        if (target.isAlive() && source.getEntity() instanceof Player player) {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(TAItems.TSLAT_SWORD.get()) && !target.isDamageSourceBlocked(source)) {
                int count = Mth.clamp(stack.getOrDefault(TADataComponents.KILL_COUNT, 0), 0, 20);
                target.setHealth(target.getHealth() - count * 0.05F);
            }

            if (stack.is(TAItems.AURORIAN_ALLOY_STEEL_SWORD)) {
                target.setHealth(target.getHealth() * 0.9F);
                event.setCanceled(true);
            }
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
    public static void onPlayerPickupItem(ItemEntityPickupEvent.Pre event) {
        if (event.getPlayer().hasEffect(TAMobEffects.REJECTUM)) {
            event.setCanPickup(TriState.FALSE);
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