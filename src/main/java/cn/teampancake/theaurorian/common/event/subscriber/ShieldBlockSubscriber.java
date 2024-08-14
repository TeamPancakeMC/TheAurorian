package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.shield.CrystallineShield;
import cn.teampancake.theaurorian.common.items.shield.MoonShield;
import cn.teampancake.theaurorian.common.items.shield.MoonStoneShield;
import cn.teampancake.theaurorian.common.items.shield.UmbraShield;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;

@EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class ShieldBlockSubscriber {

    @SubscribeEvent
    public static void onCrystallineShieldBlock(LivingShieldBlockEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack offhandItem = entity.getOffhandItem();
        if (offhandItem.getItem() instanceof CrystallineShield) {
            if (entity instanceof ServerPlayer player) {
                ItemStack itemInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (itemInHand.getDamageValue() < itemInHand.getMaxDamage() && itemInHand.isDamageableItem()) {
                    itemInHand.setDamageValue(itemInHand.getDamageValue() - 1);
                }

                player.getCooldowns().addCooldown(offhandItem.getItem(), 20);
            }
        }
    }

    @SubscribeEvent
    public static void onUmbraShieldBlock(LivingShieldBlockEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource damageSource = event.getDamageSource();
        Entity entity = damageSource.getEntity();
        Item item = livingEntity.getUseItem().getItem();
        if (item instanceof UmbraShield) {
            if (entity != null) {
                entity.setRemainingFireTicks(60);
            }
        }
    }


    @SubscribeEvent
    public static void onMoonShieldBlock(LivingShieldBlockEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Item item = livingEntity.getUseItem().getItem();
        Entity entity = event.getDamageSource().getEntity();
        if (item instanceof MoonShield) {
            if (entity != null && livingEntity instanceof LivingEntity) {
                entity.setPos(entity.getX(), entity.getY() + 5, entity.getZ());
            }
        }
    }

    @SubscribeEvent
    public static void onMoonStoneShieldBlock(LivingShieldBlockEvent event) {
        Level level = event.getEntity().level();
        LivingEntity entity = event.getEntity();
        ItemStack offhandItem = entity.getOffhandItem();
        if (offhandItem.getItem() instanceof MoonStoneShield) {
            if (level.isNight() && level.random.nextBoolean()) {
                return;
            }

            event.setBlockedDamage(event.getBlockedDamage() * 0.5F);
        }
    }

}