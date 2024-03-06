package cn.teampancake.theaurorian.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class AurorianConfig {

    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    //Whether born in Aurorian Dimension
    public static final ForgeConfigSpec.BooleanValue CONFIG_DEFAULT_SPAWN_IN_AURORIAN_DIMENSION;

    //Compat
    public static final ForgeConfigSpec.BooleanValue CONFIG_ENABLE_TINKERS_CONSTRUCT_COMPATIBILITY;
    public static final ForgeConfigSpec.BooleanValue CONFIG_ENABLE_CONSTRUCTS_ARMORY_COMPATIBILITY;

    //Blocks
    public static final ForgeConfigSpec.IntValue CONFIG_MAXIMUM_CHIMNEYS;
    public static final ForgeConfigSpec.DoubleValue CONFIG_CHIMNEY_SPEED_MULTIPLIER;
    public static final ForgeConfigSpec.BooleanValue CONFIG_CRYSTALS_SPEED_UP_MACHINES;
    public static final ForgeConfigSpec.DoubleValue CONFIG_CRYSTALS_CHANCE_OF_BREAKING;
    public static final ForgeConfigSpec.DoubleValue CONFIG_CRYSTALS_SPEED_REDUCTION;
    public static final ForgeConfigSpec.IntValue CONFIG_SCRAPPER_TICK_INTERVAL;

    //Entities
    public static final ForgeConfigSpec.IntValue CONFIG_RUNESTONE_DUNGEON_MOB_DENSITY;
    public static final ForgeConfigSpec.IntValue CONFIG_MOON_TEMPLE_MOB_DENSITY;
    public static final ForgeConfigSpec.IntValue CONFIG_DARKSTONE_DUNGEON_MOB_DENSITY;
    public static final ForgeConfigSpec.DoubleValue CONFIG_RUNESTONE_KEEPER_HEALTH_MULIPLIER;
    public static final ForgeConfigSpec.DoubleValue CONFIG_MOON_QUEEN_HEALTH_MULIPLIER;
    public static final ForgeConfigSpec.DoubleValue CONFIG_SPIDER_MOTHER_HEALTH_MULIPLIER;
    public static final ForgeConfigSpec.DoubleValue CONFIG_RUNESTONE_KEEPER_DAMAGE_MULIPLIER;
    public static final ForgeConfigSpec.DoubleValue CONFIG_MOON_QUEEN_DAMAGE_MULIPLIER;
    public static final ForgeConfigSpec.DoubleValue CONFIG_SPIDER_MOTHER_DAMAGE_MULIPLIER;

    //Misc
    public static final ForgeConfigSpec.IntValue Config_AurorianiteSwordCooldown;
    public static final ForgeConfigSpec.IntValue Config_CrystallineSwordCooldown;
    public static final ForgeConfigSpec.IntValue Config_AurorianiteAxeMaxChopSize;
    public static final ForgeConfigSpec.DoubleValue Config_LightningEnchantmentMulitplier;
    public static final ForgeConfigSpec.IntValue Config_UmbraShieldTimeUntilOverheat;
    public static final ForgeConfigSpec.IntValue Config_UmbraShieldOverheatCooldown;
    public static final ForgeConfigSpec.IntValue Config_UmbraSwordCooldown;
    public static final ForgeConfigSpec.IntValue Config_SlimeBootsCooldown;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Config_OrbOfAbsorptionList;
    public static final ForgeConfigSpec.DoubleValue Config_UmbraPickaxeMiningSpeedMultiplier;
    public static final ForgeConfigSpec.BooleanValue Config_MoonlightForgeTransfersEnchants;
    public static final ForgeConfigSpec.IntValue Config_AurorianSteel_BaseMaxLevel;
    public static final ForgeConfigSpec.DoubleValue Config_AurorianSteel_BaseMaxLevelMultiplier;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Config_AurorianSteel_Enchants;
    public static final ForgeConfigSpec.IntValue Config_AurorianSteel_Enchants_WhitelistBlacklist;
    public static final ForgeConfigSpec.DoubleValue CONFIG_SPECTRAL_ARMOR_CLEANSE_CHANCE;

    static {
        BUILDER.push("Spawn");
        CONFIG_DEFAULT_SPAWN_IN_AURORIAN_DIMENSION = BUILDER
                .comment("Set to true to make players spawn in the Aurorian Dimension by default")
                .define("DefaultSpawnInAurorianDimension", false);
    }

    //Compat
    static {
        BUILDER.push("Compatibility");
        CONFIG_ENABLE_TINKERS_CONSTRUCT_COMPATIBILITY = BUILDER.comment("Enable Tinkers' Construct compatibility.")
                .define("EnableTinkersConstructCompatibility", true);

        CONFIG_ENABLE_CONSTRUCTS_ARMORY_COMPATIBILITY = BUILDER.comment("Enable Constructs' Armory compatibility.")
                .define("EnableConstructsArmoryCompatibility", true);
        BUILDER.pop();
    }

    //blocks
    static {
        BUILDER.push("Blocks");
        CONFIG_MAXIMUM_CHIMNEYS = BUILDER
                .comment("Maximum number of chimneys able to be stacked on Aurorian Furnace")
                .defineInRange("MaximumChimneys", 10, 0, 255);

        CONFIG_CHIMNEY_SPEED_MULTIPLIER = BUILDER
                .comment("Maximum number of chimneys able to boost on Aurorian Furnace")
                .defineInRange("ChimneySpeedMultiplier", 0.5D, 0.0D, 0.99D);

        CONFIG_CRYSTALS_SPEED_UP_MACHINES = BUILDER
                .comment("Set to false to disable Crystals speeding up machines when placed on top")
                .define("CrystalsSpeedUpMachines", true);

        CONFIG_CRYSTALS_CHANCE_OF_BREAKING = BUILDER
                .comment("The chance for a Crystal to break when the machine finishes a recipe. (0.5 is 50%, 0 is no breaking)")
                .defineInRange("CrystalsChanceOfBreaking", 0.25f, 0.0f, 1.0f);
        CONFIG_CRYSTALS_SPEED_REDUCTION = BUILDER
                .comment("How much a Crystal will speed up the machine below it (LOWER percentage = FASTER machine, yes I know its backwards)")
                .defineInRange("CrystalsSpeedReduction", 0.5f, 0.01f, 1.0f);
        CONFIG_SCRAPPER_TICK_INTERVAL = BUILDER
                .comment("How many ticks until the scrapper will perform 1 update, Scrapper requires 100 updates to do 1 craft. (Meaning default is 400 ticks(20 seconds) for 1 craft)")
                .defineInRange("ScrapperTickInterval", 4, 0, 72000);
        BUILDER.pop();
    }

    //Entities
    static {
        BUILDER.push("Entity");
        CONFIG_RUNESTONE_DUNGEON_MOB_DENSITY = BUILDER
                .comment("Density of mobs spawning in the Runestone Dungeon, 2 for twice as many mobs, etc")
                .defineInRange("RunestoneDungeonMobDensity", 1, 0, 10);
        CONFIG_MOON_TEMPLE_MOB_DENSITY = BUILDER
                .comment("Density of mobs spawning in the Moon Temple, 2 for twice as many mobs, etc")
                .defineInRange("MoonTempleMobDensity", 1, 0, 10);
        CONFIG_DARKSTONE_DUNGEON_MOB_DENSITY = BUILDER
                .comment("Density of mobs spawning in the Darkstone Dungeon, 2 for twice as many mobs, etc")
                .defineInRange("DarkstoneDungeonMobDensity", 1, 0, 10);
        CONFIG_RUNESTONE_KEEPER_HEALTH_MULIPLIER = BUILDER
                .defineInRange("RunestoneKeeperHealthMuliplier", 1.0f, 0.0f, 100.0f);
        CONFIG_MOON_QUEEN_HEALTH_MULIPLIER = BUILDER
                .defineInRange("MoonQueenHealthMuliplier", 1.0f, 0.0f, 100.0f);
        CONFIG_SPIDER_MOTHER_HEALTH_MULIPLIER = BUILDER
                .defineInRange("SpiderMotherHealthMuliplier", 1.0f, 0.0f, 100.0f);
        CONFIG_RUNESTONE_KEEPER_DAMAGE_MULIPLIER = BUILDER
                .defineInRange("RunestoneKeeperDamageMuliplier", 1.0f, 0.0f, 100.0f);
        CONFIG_MOON_QUEEN_DAMAGE_MULIPLIER = BUILDER
                .defineInRange("MoonQueenDamageMuliplier", 1.0f, 0.0f, 100.0f);
        CONFIG_SPIDER_MOTHER_DAMAGE_MULIPLIER = BUILDER
                .defineInRange("SpiderMotherDamageMuliplier", 1.0f, 0.0f, 100.0f);
        BUILDER.pop();
    }

    static {
        BUILDER.push("Misc");
        Config_AurorianiteSwordCooldown = BUILDER
                .comment("Cooldown in ticks for the Aurorianite Sword’s levitate ability. 600 ticks = 30 seconds")
                .defineInRange("AurorianiteSwordCooldown", 600, 0, 72000);
        Config_CrystallineSwordCooldown = BUILDER
                .comment("Cooldown in ticks for the Crystalline Sword’s levitate ability. 600 ticks = 30 seconds")
                .defineInRange("AurorianiteSwordCooldown", 600, 0, 72000);
        Config_AurorianiteAxeMaxChopSize = BUILDER
                .comment("How many total connected log blocks can the Aurorianite Axe chop at once")
                .defineInRange("AurorianiteAxeMaxChopSize", 256, 0, 72000);
        Config_LightningEnchantmentMulitplier = BUILDER
                .comment("How much damage per armor piece the lightning enchantment should add (this multiplied by # of worn armor)")
                .defineInRange("LightningEnchantmentMulitplier", 0.20F, 0.0F, 10.0F);
        Config_UmbraShieldTimeUntilOverheat = BUILDER
                .comment("How long can the player use the shield’s fire ability until it overheats")
                .defineInRange("UmbraShieldTimeUntilOverheat", 60, 0, 72000);
        Config_UmbraShieldOverheatCooldown = BUILDER
                .comment("Cooldown in ticks for the player to be able to use the shield again after it overheats")
                .defineInRange("UmbraShieldOverheatCooldown", 60, 0, 72000);
        Config_OrbOfAbsorptionList = BUILDER
                .comment("List of items, use is decided by OrbOfAbsorptionWhitelistBlacklist, you can also specify mod ids to whitelist or blacklist whole mods, ex: (tconstruct, minecraft:elytra)")
                .defineList("OrbOfAbsorptionList", List.of("theaurorian:absorption_orb", "theaurorian:strange_meat"), o -> o instanceof String);
        Config_UmbraSwordCooldown = BUILDER
                .comment("Cooldown in ticks for the Umbra Sword’s ability")
                .defineInRange("UmbraSwordCooldown", 900, 0, 72000);
        Config_SlimeBootsCooldown = BUILDER
                .comment("Cooldown in ticks for the Aurorian Slime Boots jump ability")
                .defineInRange("SlimeBootsCooldown", 100, 0, 72000);
        Config_UmbraPickaxeMiningSpeedMultiplier = BUILDER
                .comment("Speed multiplier for Umbra Pickaxe, 2.0F is twice as fast as it would mine regular blocks")
                .defineInRange("UmbraPickaxeMiningSpeedMultiplier", 2.0F, 1.0F, 100.0F);
        Config_MoonlightForgeTransfersEnchants = BUILDER
                .comment("Set to false to disable enchantments from carrying over when crafting tools with the Moonlight Forge")
                .define("MoonlightForgeTransfersEnchants", true);
        Config_AurorianSteel_BaseMaxLevel = BUILDER
                .comment("Base Max level for Aurorian Steel items. After used this many times an enchantment will level up, next level cost depends on multiplier")
                .defineInRange("AurorianSteel_BaseMaxLevel", 100, 0, 72000);
        Config_AurorianSteel_BaseMaxLevelMultiplier = BUILDER
                .comment("Max Level multiplier for Aurorian Steel items, every time they level up the max level is multiplied by this")
                .defineInRange("AurorianSteel_BaseMaxLevelMultiplier", 1.75F, 1.0F, 100.0F);
        Config_AurorianSteel_Enchants = BUILDER
                .comment("List of enchantments, use is decided by AurorianSteel_Enchants_WhitelistBlacklist, you can also specify mod ids to whitelist or blacklist whole mods, ex: (draconicevolution, minecraft:sharpness)")
                .defineList("AurorianSteel_Enchants", List.of(), o -> o instanceof String);
        Config_AurorianSteel_Enchants_WhitelistBlacklist = BUILDER
                .comment("Decides how to treat AurorianSteel_Enchants: 0 - ignored (Aurorian Steel can upgrade any enchantment), 1 - whitelist (can only upgrade enchantments specified), 2 - blacklist (upgrades all but the enchantments specified)")
                .defineInRange("AurorianSteel_Enchants_WhitelistBlacklist", 0, 0, 2);
        CONFIG_SPECTRAL_ARMOR_CLEANSE_CHANCE = BUILDER
                .comment("Percent for one armor piece to cleanse the player’s negative effects, stacks with other armor parts (full spectral armor with 6% is a 24% chance with full set)")
                .defineInRange("Spectral_Armor_CleanseChance", 0.06F, 0.01F, 1.0F);
        BUILDER.pop();
    }

    static {
        SPEC = BUILDER.build();
    }

}