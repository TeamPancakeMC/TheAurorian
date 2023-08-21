package cn.teampancake.theaurorian.event;

import cn.teampancake.theaurorian.config.AurorianConfig;
import cn.teampancake.theaurorian.enchantment.ModEnchantments;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EnchantmentEventSubscriber {
    public static void LightningDamage(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        Entity entity = source.getEntity();
        LivingEntity target = event.getEntity();

        if (entity == null || target == null || !(entity instanceof LivingEntity attacker)) {
            return;
        }
        ItemStack mainHandItem = attacker.getMainHandItem();
        if (mainHandItem.isEmpty()) {
            return;
        }

        int damageLevel = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.LIGHTNING_DAMAGE.get(), attacker);
        float extradamage = 0;

        for (ItemStack stack : target.getArmorSlots()) {
            boolean hasresist = false;
            int resistanceLevel = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.LIGHTNING_RESISTANCE.get(), attacker);
            if (resistanceLevel > 0) {
                hasresist = true;
            }
            Item item = stack.getItem();
            if (item instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial() != ArmorMaterials.LEATHER && !hasresist) {
                    if (extradamage <= damageLevel) {
                        extradamage++;
                    }
                }
            }
        }
        event.setAmount((float) (event.getAmount() + (extradamage * AurorianConfig.Config_LightningEnchantmentMulitplier.get())));
    }

    public static void LightningResistance(LivingDamageEvent event) {

        if (event.getSource().is(DamageTypes.LIGHTNING_BOLT)) {
            LivingEntity target = event.getEntity();

            if (target == null) {
                return;
            }
            boolean hasresist = false;
            int resistanceLevel = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.LIGHTNING_RESISTANCE.get(), target);
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
