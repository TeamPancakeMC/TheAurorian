package cn.teampancake.theaurorian.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

/** @noinspection deprecation*/
public class AurorianConfig {

    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    //Whether born in Aurorian Dimension
    public static ModConfigSpec.BooleanValue CONFIG_DEFAULT_SPAWN_IN_AURORIAN_DIMENSION;

    //Compat
    public static ModConfigSpec.BooleanValue CONFIG_ENABLE_TINKERS_CONSTRUCT_COMPATIBILITY;
    public static ModConfigSpec.BooleanValue CONFIG_ENABLE_CONSTRUCTS_ARMORY_COMPATIBILITY;

    //Blocks
    public static ModConfigSpec.IntValue CONFIG_MAXIMUM_CHIMNEYS;
    public static ModConfigSpec.DoubleValue CONFIG_CHIMNEY_SPEED_MULTIPLIER;
    public static ModConfigSpec.BooleanValue CONFIG_CRYSTALS_SPEED_UP_MACHINES;
    public static ModConfigSpec.DoubleValue CONFIG_CRYSTALS_CHANCE_OF_BREAKING;
    public static ModConfigSpec.DoubleValue CONFIG_CRYSTALS_SPEED_REDUCTION;
    public static ModConfigSpec.IntValue CONFIG_SCRAPPER_TICK_INTERVAL;

    //Entities
    public static ModConfigSpec.DoubleValue CONFIG_RUNESTONE_KEEPER_HEALTH_MULIPLIER;
    public static ModConfigSpec.DoubleValue CONFIG_MOON_QUEEN_HEALTH_MULIPLIER;
    public static ModConfigSpec.DoubleValue CONFIG_SPIDER_MOTHER_HEALTH_MULIPLIER;
    public static ModConfigSpec.DoubleValue CONFIG_RUNESTONE_KEEPER_DAMAGE_MULIPLIER;
    public static ModConfigSpec.DoubleValue CONFIG_MOON_QUEEN_DAMAGE_MULIPLIER;
    public static ModConfigSpec.DoubleValue CONFIG_SPIDER_MOTHER_DAMAGE_MULIPLIER;

    //Misc
    public static ModConfigSpec.ConfigValue<List<? extends String>> Config_OrbOfAbsorptionList;
    public static ModConfigSpec.DoubleValue Config_UmbraPickaxeMiningSpeedMultiplier;
    public static ModConfigSpec.IntValue Config_AurorianSteel_BaseMaxLevel;
    public static ModConfigSpec.DoubleValue Config_AurorianSteel_BaseMaxLevelMultiplier;
    public static ModConfigSpec.ConfigValue<List<? extends String>> Config_AurorianSteel_Enchants;
    public static ModConfigSpec.IntValue Config_AurorianSteel_Enchants_WhitelistBlacklist;

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
        Config_OrbOfAbsorptionList = BUILDER
                .comment("List of items, use is decided by OrbOfAbsorptionWhitelistBlacklist, you can also specify mod ids to whitelist or blacklist whole mods, ex: (tconstruct, minecraft:elytra)")
                .defineList("OrbOfAbsorptionList", List.of("theaurorian:absorption_orb", "theaurorian:strange_meat"), o -> o instanceof String);
        Config_UmbraPickaxeMiningSpeedMultiplier = BUILDER
                .comment("Speed multiplier for Umbra Pickaxe, 2.0F is twice as fast as it would mine regular blocks")
                .defineInRange("UmbraPickaxeMiningSpeedMultiplier", 2.0F, 1.0F, 100.0F);
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
        BUILDER.pop();
    }

    static {
        SPEC = BUILDER.build();
    }

}