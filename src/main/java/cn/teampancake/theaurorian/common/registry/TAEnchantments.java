package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.enchantments.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TAEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, AurorianMod.MOD_ID);
    public static final RegistryObject<Enchantment> IMPALE = ENCHANTMENTS.register("impale", ImpaleEnchantment::new);
    public static final RegistryObject<Enchantment> AURORA = ENCHANTMENTS.register("aurora", AuroraEnchantment::new);
    public static final RegistryObject<Enchantment> SAVAGE = ENCHANTMENTS.register("savage", SavageEnchantment::new);
    public static final RegistryObject<Enchantment> OVERLOAD = ENCHANTMENTS.register("overload", OverloadEnchantment::new);
    public static final RegistryObject<Enchantment> GUARDIAN = ENCHANTMENTS.register("guardian", GuardianEnchantment::new);
    public static final RegistryObject<Enchantment> MOONLIGHT = ENCHANTMENTS.register("moonlight", MoonlightEnchantment::new);
    public static final RegistryObject<Enchantment> SOUL_SLASH = ENCHANTMENTS.register("soul_slash", SoulSlashEnchantment::new);
    public static final RegistryObject<Enchantment> CLEAR_MIND = ENCHANTMENTS.register("clear_mind", ClearMindEnchantment::new);
    public static final RegistryObject<Enchantment> WIND_RUNNER = ENCHANTMENTS.register("wind_runner", WindRunnerEnchantment::new);
    public static final RegistryObject<Enchantment> MOLTEN_CORE = ENCHANTMENTS.register("molten_core", MoltenCoreEnchantment::new);
    public static final RegistryObject<Enchantment> NIGHT_WALKER = ENCHANTMENTS.register("night_walker", NightWalkerEnchantment::new);
    public static final RegistryObject<Enchantment> REFLECT_AURA = ENCHANTMENTS.register("reflect_aura", ReflectAuraEnchantment::new);
    public static final RegistryObject<Enchantment> AMNESIA_CURSE = ENCHANTMENTS.register("amnesia_curse", AmnesiaCurseEnchantment::new);
    public static final RegistryObject<Enchantment> FREEZE_ASPECT = ENCHANTMENTS.register("freeze_aspect", FreezeAspectEnchantment::new);
    public static final RegistryObject<Enchantment> SPRING_OF_LIFE = ENCHANTMENTS.register("spring_of_life", SpringOfLifeEnchantment::new);
    public static final RegistryObject<Enchantment> EXPERIENCE_ORE = ENCHANTMENTS.register("experience_ore", ExperienceOreEnchantment::new);
    public static final RegistryObject<Enchantment> LEGENDARY_HERO = ENCHANTMENTS.register("legendary_hero", LegendaryHeroEnchantment::new);
    public static final RegistryObject<Enchantment> VIRTUALIZATION = ENCHANTMENTS.register("virtualization", VirtualizationEnchantment::new);
    public static final RegistryObject<Enchantment> SOURCE_OF_TERRA = ENCHANTMENTS.register("source_of_terra", SourceOfTerraEnchantment::new);
    public static final RegistryObject<Enchantment> COBWEB_CROSSING = ENCHANTMENTS.register("cobweb_crossing", CobwebCrossingEnchantment::new);
    public static final RegistryObject<Enchantment> ROUNDABOUT_THROW = ENCHANTMENTS.register("roundabout_throw", RoundaboutThrowEnchantment::new);
    public static final RegistryObject<Enchantment> LIGHTNING_DAMAGE = ENCHANTMENTS.register("lightning_damage", LightningDamageEnchantment::new);
    public static final RegistryObject<Enchantment> SUNDER_ARMOR_SLASH = ENCHANTMENTS.register("sunder_armor_slash", SunderArmorSlashEnchantment::new);
    public static final RegistryObject<Enchantment> LIGHTNING_RESISTANCE = ENCHANTMENTS.register("lightning_resistance", LightningResistance::new);

}