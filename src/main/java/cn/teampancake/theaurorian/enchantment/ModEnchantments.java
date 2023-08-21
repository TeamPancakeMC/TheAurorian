package cn.teampancake.theaurorian.enchantment;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, AurorianMod.MOD_ID);

    public static final RegistryObject<Enchantment> LIGHTNING_DAMAGE = ENCHANTMENTS.register(LightningDamage.NAME, LightningDamage::new);
    public static final RegistryObject<Enchantment> LIGHTNING_RESISTANCE = ENCHANTMENTS.register(LightningResistance.NAME, LightningResistance::new);

}
