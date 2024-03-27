package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class EnchantmentEventSubscriber {

    @SubscribeEvent
    public static void onArrowCreated(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof Player player) {
            ItemStack itemStack = player.getItemInHand(player.getUsedItemHand());
            if (itemStack.getItem() instanceof BowItem) {
                Enchantment enchantment = TAEnchantments.IMPALE.get();
                int i = itemStack.getEnchantmentLevel(enchantment);
                if (i > 0) {
                    arrow.setPierceLevel((byte) i);
                }
            }
        }
    }

    @SubscribeEvent
    public static void LightningDamage(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        Entity entity = source.getEntity();
        LivingEntity target = event.getEntity();
        if (target == null || !(entity instanceof LivingEntity attacker)) {
            return;
        }

        ItemStack mainHandItem = attacker.getMainHandItem();
        if (mainHandItem.isEmpty()) {
            return;
        }

        int vLevel = EnchantmentHelper.getEnchantmentLevel(TAEnchantments.VIRTUALIZATION.get(), attacker);
        event.setCanceled(vLevel > 0 && attacker.getRandom().nextFloat() < vLevel / 100.0F);
        int damageLevel = EnchantmentHelper.getEnchantmentLevel(TAEnchantments.LIGHTNING_DAMAGE.get(), attacker);
        float extraDamage = 0;
        for (ItemStack stack : target.getArmorSlots()) {
            boolean hasResist = false;
            int resistanceLevel = EnchantmentHelper.getEnchantmentLevel(TAEnchantments.LIGHTNING_RESISTANCE.get(), attacker);
            if (resistanceLevel > 0) {
                hasResist = true;
            }

            Item item = stack.getItem();
            if (item instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial() != ArmorMaterials.LEATHER && !hasResist) {
                    if (extraDamage <= damageLevel) {
                        extraDamage++;
                    }
                }
            }
        }

        event.setAmount(event.getAmount() + extraDamage * 0.2F);
    }

    @SubscribeEvent
    public static void LightningResistance(LivingDamageEvent event) {
        if (event.getSource().is(DamageTypes.LIGHTNING_BOLT)) {
            LivingEntity target = event.getEntity();
            if (target == null) {
                return;
            }
            boolean hasresist = false;
            int resistanceLevel = EnchantmentHelper.getEnchantmentLevel(TAEnchantments.LIGHTNING_RESISTANCE.get(), target);
            if (resistanceLevel > 0) {
                hasresist = true;
            }
            if (hasresist) {
                event.setAmount(0);
                event.setCanceled(true);
            }
        }
    }

}