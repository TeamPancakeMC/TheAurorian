package cn.teampancake.theaurorian.event;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.monster.CrystallineSprite;
import cn.teampancake.theaurorian.common.items.CrystallineShield;
import cn.teampancake.theaurorian.common.items.ModArmorMaterials;
import cn.teampancake.theaurorian.common.items.UmbraShield;
import cn.teampancake.theaurorian.config.AurorianConfig;
import cn.teampancake.theaurorian.data.tags.ModBlockTags;
import cn.teampancake.theaurorian.data.tags.ModEntityTags;
import cn.teampancake.theaurorian.registry.ModItems;
import cn.teampancake.theaurorian.utils.AurorianSteelHelper;
import cn.teampancake.theaurorian.utils.AurorianUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class EntityEventSubscriber {

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Monster monster) {
            double baseHealth = monster.getAttributeBaseValue(Attributes.MAX_HEALTH);
            double baseAttackDamage = monster.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
            double baseMovementSpeed = monster.getAttributeBaseValue(Attributes.MOVEMENT_SPEED);
            if (monster.getType().is(ModEntityTags.AFFECTED_BY_NIGHTMARE_MODE)) {
                boolean nightmareMode = AurorianConfig.CONFIG_NIGHTMARE_MODE.get();
                double multiplier = AurorianConfig.CONFIG_NIGHTMARE_MODE_MULTIPLIER.get();
                double newHealth = nightmareMode ? baseHealth * multiplier * 2.0D : baseHealth;
                double newAttackDamage = nightmareMode ? baseAttackDamage * multiplier * 2.0D : baseAttackDamage;
                double newMovementSpeed = nightmareMode ? baseMovementSpeed * multiplier * 2.0D : baseMovementSpeed;
                monster.getAttribute(Attributes.MAX_HEALTH).setBaseValue(newHealth);
                monster.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(newAttackDamage);
                if (!(monster instanceof CrystallineSprite)) {
                    monster.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(newMovementSpeed);
                }
            } else if (monster instanceof RunestoneKeeper runestoneKeeper) {
                double healthMultiplier = AurorianConfig.CONFIG_RUNESTONE_KEEPER_HEALTH_MULIPLIER.get();
                double damageMultiplier = AurorianConfig.CONFIG_RUNESTONE_KEEPER_DAMAGE_MULIPLIER.get();
                runestoneKeeper.getAttribute(Attributes.MAX_HEALTH).setBaseValue(baseHealth * healthMultiplier);
                runestoneKeeper.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(baseAttackDamage * damageMultiplier);
            } else if (monster instanceof SpiderMother spiderMother) {
                double healthMultiplier = AurorianConfig.CONFIG_SPIDER_MOTHER_HEALTH_MULIPLIER.get();
                double damageMultiplier = AurorianConfig.CONFIG_SPIDER_MOTHER_DAMAGE_MULIPLIER.get();
                spiderMother.getAttribute(Attributes.MAX_HEALTH).setBaseValue(baseHealth * healthMultiplier);
                spiderMother.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(baseAttackDamage * damageMultiplier);
            } else if (monster instanceof MoonQueen moonQueen) {
                double healthMultiplier = AurorianConfig.CONFIG_MOON_QUEEN_HEALTH_MULIPLIER.get();
                double damageMultiplier = AurorianConfig.CONFIG_MOON_QUEEN_DAMAGE_MULIPLIER.get();
                moonQueen.getAttribute(Attributes.MAX_HEALTH).setBaseValue(baseHealth * healthMultiplier);
                moonQueen.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(baseAttackDamage * damageMultiplier);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        if (target != null) {
            for (ItemStack piece : target.getArmorSlots()) {
                if (piece.getItem() instanceof ArmorItem armorItem) {
                    if (armorItem.getMaterial() == ModArmorMaterials.AURORIAN_STEEL) {
                        AurorianSteelHelper.handleAurorianSteelDurability(piece);
                    }
                }
            }
        }
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof LivingEntity livingEntity) {
            float chance = 0.00F;
            for (ItemStack piece : livingEntity.getArmorSlots()) {
                if (piece.getItem() instanceof ArmorItem armorItem) {
                    if (armorItem.getMaterial() == ModArmorMaterials.SPECTRAL) {
                        chance += AurorianConfig.CONFIG_SPECTRAL_ARMOR_CLEANSE_CHANCE.get();
                    }
                }
            }
            if (chance != 0.00F && AurorianUtil.randomChanceOf(chance)) {
                for (MobEffectInstance effectInstance : livingEntity.getActiveEffects()) {
                    if (effectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
                        livingEntity.removeEffect(effectInstance.getEffect());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource damageSource = event.getDamageSource();
        Entity entity = damageSource.getEntity();
        Item item = livingEntity.getUseItem().getItem();
        if (item instanceof UmbraShield) {
            if (entity != null) {
                entity.setSecondsOnFire(3);
            }
        }
        if (item instanceof CrystallineShield) {
            if (livingEntity instanceof ServerPlayer player) {
                ItemStack mainhand = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (mainhand.getDamageValue() < mainhand.getMaxDamage() && mainhand.isDamageableItem()) {
                    mainhand.setDamageValue(mainhand.getDamageValue() - 1);
                }
                player.getCooldowns().addCooldown(item, 20);
            }
        }
        if (item == ModItems.MOON_SHIELD.get()) {
            if (entity != null) {
                entity.setPos(entity.getX(), entity.getY() + 5, entity.getZ());
            }
        }
    }

    @SubscribeEvent
    public static void playerBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        BlockState state = event.getState();
        ItemStack blockStack = new ItemStack(state.getBlock());
        ItemStack handStack = player.getItemInHand(player.getUsedItemHand());
        if (blockStack.is(Tags.Items.ORES) && handStack.is(ModItems.AURORIANITE_PICKAXE.get())) {
            event.setNewSpeed(event.getOriginalSpeed() * 1.4F);
        } else if (state.is(ModBlockTags.DUNGEON_BRICKS)) {
            boolean flag = handStack.is(ModItems.QUEENS_CHIPPER.get());
            event.setNewSpeed(flag ? event.getOriginalSpeed() * 16.0F : 0.0F);
        }
    }

}