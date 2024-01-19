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

    //Structures
    public static final ForgeConfigSpec.BooleanValue CONFIG_GENERATE_RUNESTONE_DUNGEON;
    public static final ForgeConfigSpec.IntValue CONFIG_DUNGEON_DENSITY;
    public static final ForgeConfigSpec.IntValue CONFIG_RUNESTONE_DUNGEON_FLOORS;
    public static final ForgeConfigSpec.BooleanValue CONFIG_GENERATE_RUINS;
    public static final ForgeConfigSpec.BooleanValue CONFIG_GENERATE_MOON_TEMPLE;
    public static final ForgeConfigSpec.BooleanValue CONFIG_GENERATE_MOON_TEMPLE_PATH;
    public static final ForgeConfigSpec.BooleanValue CONFIG_GENERATE_UMBRA_TOWER;
    public static final ForgeConfigSpec.BooleanValue CONFIG_GENERATE_MUSHROOM_CAVES;
    public static final ForgeConfigSpec.BooleanValue CONFIG_GENERATE_GRAVEYARDS;
    public static final ForgeConfigSpec.BooleanValue CONFIG_GENERATE_DARKSTONE_DUNGEON;

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

    //Generation
    public static final ForgeConfigSpec.IntValue Config_AurorianCoalOre_Size;
    public static final ForgeConfigSpec.IntValue Config_AurorianCoalOre_Count;
    public static final ForgeConfigSpec.IntValue Config_AurorianCoalOre_HeightMin;
    public static final ForgeConfigSpec.IntValue Config_AurorianCoalOre_HeightMax;
    public static final ForgeConfigSpec.IntValue Config_CeruleanOre_Size;
    public static final ForgeConfigSpec.IntValue Config_CeruleanOre_Count;
    public static final ForgeConfigSpec.IntValue Config_CeruleanOre_HeightMin;
    public static final ForgeConfigSpec.IntValue Config_CeruleanOre_HeightMax;
    public static final ForgeConfigSpec.IntValue Config_MoonstoneOre_Size;
    public static final ForgeConfigSpec.IntValue Config_MoonstoneOre_Count;
    public static final ForgeConfigSpec.IntValue Config_MoonstoneOre_HeightMin;
    public static final ForgeConfigSpec.IntValue Config_MoonstoneOre_HeightMax;
    public static final ForgeConfigSpec.IntValue Config_GeodeOre_Size;
    public static final ForgeConfigSpec.IntValue Config_GeodeOre_Count;
    public static final ForgeConfigSpec.IntValue Config_GeodeOre_HeightMin;
    public static final ForgeConfigSpec.IntValue Config_GeodeOre_HeightMax;
    public static final ForgeConfigSpec.IntValue Config_Peridotite_Size;
    public static final ForgeConfigSpec.IntValue Config_Peridotite_Count;
    public static final ForgeConfigSpec.IntValue Config_Peridotite_HeightMin;
    public static final ForgeConfigSpec.IntValue Config_Peridotite_HeightMax;
    public static final ForgeConfigSpec.IntValue Config_Dirt_Size;
    public static final ForgeConfigSpec.IntValue Config_Dirt_Count;
    public static final ForgeConfigSpec.IntValue Config_Dirt_HeightMin;
    public static final ForgeConfigSpec.IntValue Config_Dirt_HeightMax;
    public static final ForgeConfigSpec.BooleanValue Config_GenerateUrns;

    //Misc
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Config_PortalLighter;
    public static final ForgeConfigSpec.BooleanValue Config_SticksMakeFire;
    public static final ForgeConfigSpec.IntValue Config_AurorianiteSwordCooldown;
    public static final ForgeConfigSpec.IntValue Config_CrystallineSwordCooldown;
    public static final ForgeConfigSpec.IntValue Config_AurorianiteAxeMaxChopSize;
    public static final ForgeConfigSpec.DoubleValue Config_LightningEnchantmentMulitplier;
    public static final ForgeConfigSpec.IntValue Config_UmbraShieldTimeUntilOverheat;
    public static final ForgeConfigSpec.IntValue Config_UmbraShieldOverheatCooldown;
    public static final ForgeConfigSpec.IntValue Config_UmbraSwordCooldown;
    public static final ForgeConfigSpec.IntValue Config_SlimeBootsCooldown;
    public static final ForgeConfigSpec.IntValue Config_OrbOfAbsorptionWhitelistBlacklist;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Config_OrbOfAbsorptionList;
    public static final ForgeConfigSpec.DoubleValue Config_UmbraPickaxeMiningSpeedMultiplier;
    public static final ForgeConfigSpec.BooleanValue Config_MoonlightForgeTransfersEnchants;
    public static final ForgeConfigSpec.IntValue Config_AurorianSteel_BaseMaxLevel;
    public static final ForgeConfigSpec.DoubleValue Config_AurorianSteel_BaseMaxLevelMultiplier;
    public static final ForgeConfigSpec.IntValue Config_CrystalStackSize;
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
                .comment("Maximum number of chimneys able to be stacked on Aurorian Furnace")
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

    //Structures
    static {
        BUILDER.push("Structures");
        CONFIG_GENERATE_RUNESTONE_DUNGEON = BUILDER
                .comment("Set to false to disable Runestone Dungeons (Why would anyone do this? :c )")
                .define("GenerateRunestoneDungeon", true);
        CONFIG_DUNGEON_DENSITY = BUILDER.comment("How many chunks away until another Runestone Dungeons can generate, also affects Moon Temple generation")
                .defineInRange("DungeonDensity",32,16,256);
        CONFIG_RUNESTONE_DUNGEON_FLOORS = BUILDER
                .comment("How many floors each Runestone Dungeon has, including double sized floors, code only accepts odd numbers! Evens will have +1 added")
                .defineInRange("RunestoneDungeonFloors",4,1,17);
        CONFIG_GENERATE_RUINS = BUILDER
                .comment("Set to false to disable ruin structures (like destroyed houses or small underground structures)")
                .define("GenerateRuins",true);
        CONFIG_GENERATE_MOON_TEMPLE = BUILDER
                .comment("Set to false to disable Moon Temples")
                .define("GenerateMoonTemple",true);
        CONFIG_GENERATE_MOON_TEMPLE_PATH = BUILDER
                .comment("Set to false to disable Moon Temple's spiral path up")
                .define("GenerateMoonTemplePath",true);
        CONFIG_GENERATE_UMBRA_TOWER = BUILDER
                .comment("Set to false to disable Umbra Towers")
                .define("GenerateUmbraTower",true);
        CONFIG_GENERATE_MUSHROOM_CAVES = BUILDER
                .comment("Set to false to disable Mushroom Caves")
                .define("GenerateMushroomCaves",true);
        CONFIG_GENERATE_GRAVEYARDS = BUILDER
                .comment("Set to false to disable Graveyards")
                .define("GenerateGraveyards",false);
        CONFIG_GENERATE_DARKSTONE_DUNGEON = BUILDER
                .comment("Set to false to disable Darkstone Dungeons")
                .define("GenerateDarkstoneDungeon",true);
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

    //Generation
    static {
        BUILDER.push("Generation");
        Config_AurorianCoalOre_Size = BUILDER
                .comment("Changes the size of Aurorian Coal Ore vein")
                .defineInRange("AurorianCoalOreSize", 12, 0, 50);
        Config_AurorianCoalOre_Count = BUILDER
                .comment("Changes the count of Aurorian Coal Ore vein")
                .defineInRange("AurorianCoalOreCount", 13, 0, 50);
        Config_AurorianCoalOre_HeightMin = BUILDER
                .comment("Changes the min height of Aurorian Coal Ore vein")
                .defineInRange("AurorianCoalOreHeightMin", 40, 0, 255);
        Config_AurorianCoalOre_HeightMax = BUILDER
                .comment("Changes the max height of Aurorian Coal Ore vein")
                .defineInRange("AurorianCoalOreHeightMax", 128, 0, 255);
        Config_CeruleanOre_Size = BUILDER
                .comment("Changes the size of Cerulean Ore vein")
                .defineInRange("CeruleanOreSize", 7, 0, 50);
        Config_CeruleanOre_Count = BUILDER
                .comment("Changes the count of Cerulean Ore vein")
                .defineInRange("CeruleanOreCount", 13, 0, 50);
        Config_CeruleanOre_HeightMin = BUILDER
                .comment("Changes the min height of Cerulean Ore vein")
                .defineInRange("CeruleanOreHeightMin", 5, 0, 255);
        Config_CeruleanOre_HeightMax = BUILDER
                .comment("Changes the max height of Cerulean Ore vein")
                .defineInRange("CeruleanOreHeightMax", 60, 0, 255);
        Config_MoonstoneOre_Size = BUILDER
                .comment("Changes the size of Moonstone Ore vein")
                .defineInRange("MoonstoneOreSize", 9, 0, 50);
        Config_MoonstoneOre_Count = BUILDER
                .comment("Changes the count of Moonstone Ore vein")
                .defineInRange("MoonstoneOreCount", 2, 0, 50);
        Config_MoonstoneOre_HeightMin = BUILDER
                .comment("Changes the min height of Moonstone Ore vein")
                .defineInRange("MoonstoneOreHeightMin", 5, 0, 255);
        Config_MoonstoneOre_HeightMax = BUILDER
                .comment("Changes the max height of Moonstone Ore vein")
                .defineInRange("MoonstoneOreHeightMax", 30, 0, 255);
        Config_GeodeOre_Size = BUILDER
                .comment("Changes the size of Geode Ore vein")
                .defineInRange("GeodeOreSize", 5, 0, 50);
        Config_GeodeOre_Count = BUILDER
                .comment("Changes the count of Geode Ore vein")
                .defineInRange("GeodeOreCount", 8, 0, 50);
        Config_GeodeOre_HeightMin = BUILDER
                .comment("Changes the min height of Geode Ore vein")
                .defineInRange("GeodeOreHeightMin", 5, 0, 255);
        Config_GeodeOre_HeightMax = BUILDER
                .comment("Changes the max height of Geode Ore vein")
                .defineInRange("GeodeOreHeightMax", 128, 0, 255);
        Config_Peridotite_Size = BUILDER
                .comment("Changes the size of Peridotite vein")
                .defineInRange("PeridotiteSize", 33, 0, 50);
        Config_Peridotite_Count = BUILDER
                .comment("Changes the count of Peridotite vein")
                .defineInRange("PeridotiteCount", 10, 0, 50);
        Config_Peridotite_HeightMin = BUILDER
                .comment("Changes the min height of Peridotite vein")
                .defineInRange("PeridotiteHeightMin", 5, 0, 255);
        Config_Peridotite_HeightMax = BUILDER
                .comment("Changes the max height of Peridotite vein")
                .defineInRange("PeridotiteHeightMax", 80, 0, 255);
        Config_Dirt_Size = BUILDER
                .comment("Changes the size of Dirt vein")
                .defineInRange("DirtSize", 33, 0, 50);
        Config_Dirt_Count = BUILDER
                .comment("Changes the count of Dirt vein")
                .defineInRange("DirtCount", 10, 0, 50);
        Config_Dirt_HeightMin = BUILDER
                .comment("Changes the min height of Dirt vein")
                .defineInRange("DirtHeightMin", 5, 0, 255);
        Config_Dirt_HeightMax = BUILDER
                .comment("Changes the max height of Dirt vein")
                .defineInRange("DirtHeightMax", 256, 0, 255);
        Config_GenerateUrns = BUILDER
                .comment("Set to false to disable Urns that spawn underground")
                .define("GenerateUrns", true);
        BUILDER.pop();
    }

    static {
        BUILDER.push("Misc");
        Config_PortalLighter = BUILDER
                .comment("Can change the item(s) used to light the portal here (Uses item’s unlocalized name)")
                .defineList("PortalLighter", List.of("minecraft:flint_and_steel"),  o -> o instanceof String);
        Config_SticksMakeFire = BUILDER
                .comment("Set to false to disable Silentwood Sticks making Fire or lighting Portal")
                .define("SticksMakeFire", true);
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
        Config_OrbOfAbsorptionWhitelistBlacklist = BUILDER
                .comment("Decides how to treat OrbOfAbsorptionList: 0 - ignored (Orb of Absorption can repair any damaged object), 1 - whitelist (can only repair items in the list), 2 - blacklist (repairs everything but items in the list)")
                .defineInRange("OrbOfAbsorptionWhitelistBlacklist", 2, 0, 2);
        Config_OrbOfAbsorptionList = BUILDER
                .comment("List of items, use is decided by OrbOfAbsorptionWhitelistBlacklist, you can also specify mod ids to whitelist or blacklist whole mods, ex: (tconstruct, minecraft:elytra)")
                .defineList("OrbOfAbsorptionList", List.of("theaurorian:absorptionorb", "theaurorian:strangemeat"), o -> o instanceof String);
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
        Config_CrystalStackSize = BUILDER
                .comment("Stack size of Crystals")
                .defineInRange("CrystalStackSize", 16, 1, 64);
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