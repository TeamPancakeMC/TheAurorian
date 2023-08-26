package cn.teampancake.theaurorian.event;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.CrystallineShield;
import cn.teampancake.theaurorian.common.items.ModArmorMaterials;
import cn.teampancake.theaurorian.common.items.UmbraShield;
import cn.teampancake.theaurorian.config.AurorianConfig;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class EntityEventSubscriber {

    @SubscribeEvent
    public static void handleDamageEvent(LivingDamageEvent event) {
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
        ItemStack blockStack = new ItemStack(event.getState().getBlock());
        ItemStack handStack = player.getItemInHand(player.getUsedItemHand());
        if (blockStack.is(Tags.Items.ORES) && handStack.is(ModItems.AURORIANITE_PICKAXE.get())) {
            float originalSpeed = event.getOriginalSpeed();
            event.setNewSpeed(originalSpeed * 1.4F);
        }
    }

}