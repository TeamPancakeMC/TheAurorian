package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.entities.ai.CatFollowCatBellGoal;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.monster.CrystallineSprite;
import cn.teampancake.theaurorian.common.items.TAArmorMaterials;
import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.utils.AurorianSteelHelper;
import cn.teampancake.theaurorian.common.utils.AurorianUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
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
            if (monster.getType().is(TAEntityTags.AFFECTED_BY_NIGHTMARE_MODE)) {
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
        } else if (entity instanceof Cat cat) {
            cat.goalSelector.addGoal(5, new CatFollowCatBellGoal(cat));
        }
    }

    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event) {
        event.setCanceled(event.getEntity() instanceof Player player && player.hasEffect(TAMobEffects.MOON_CURSE.get()));
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        if (target != null) {
            for (ItemStack piece : target.getArmorSlots()) {
                if (piece.getItem() instanceof ArmorItem armorItem) {
                    if (armorItem.getMaterial() == TAArmorMaterials.AURORIAN_STEEL) {
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
                    if (armorItem.getMaterial() == TAArmorMaterials.SPECTRAL) {
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
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        HitResult hitResult = event.getRayTraceResult();
        if (hitResult instanceof EntityHitResult entityHitResult) {
            Entity resultEntity = entityHitResult.getEntity();
            Projectile projectile = event.getProjectile();
            if (resultEntity instanceof LivingEntity livingEntity && projectile instanceof AbstractArrow arrow && arrow.getOwner() instanceof Player player) {
                if (player.getItemInHand(player.getUsedItemHand()).is(TAItems.STAR_OCEAN_CROSSBOW.get())) {
                    MobEffectInstance mobEffect = new MobEffectInstance(MobEffects.GLOWING, 200, 0);
                    livingEntity.addEffect(mobEffect, arrow.getEffectSource());
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
        }
    }

}