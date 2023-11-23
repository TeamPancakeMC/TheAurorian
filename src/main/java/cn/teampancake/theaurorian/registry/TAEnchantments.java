package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.enchantments.LightningDamage;
import cn.teampancake.theaurorian.common.enchantments.LightningResistance;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TAEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, AurorianMod.MOD_ID);

    public static final RegistryObject<Enchantment> LIGHTNING_DAMAGE = ENCHANTMENTS.register(LightningDamage.NAME, LightningDamage::new);
    public static final RegistryObject<Enchantment> LIGHTNING_RESISTANCE = ENCHANTMENTS.register(LightningResistance.NAME, LightningResistance::new);

}
