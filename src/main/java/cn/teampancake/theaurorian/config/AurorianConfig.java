package cn.teampancake.theaurorian.config;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AurorianConfig {
    private static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Integer> Config_OrbOfAbsorptionDurability;

    //Compat
    public static final ForgeConfigSpec.BooleanValue Config_EnableTinkersConstructCompatibility;
    public static final ForgeConfigSpec.BooleanValue Config_EnableConstructsArmoryCompatibility;


    //Blocks
    public static final ForgeConfigSpec.IntValue Config_MaximumChimneys;
    public static final ForgeConfigSpec.DoubleValue Config_ChimneySpeedMultiplier;
    public static final ForgeConfigSpec.BooleanValue Config_CrystalsSpeedUpMachines;
    public static final ForgeConfigSpec.DoubleValue Config_CrystalsChanceOfBreaking;
    public static final ForgeConfigSpec.DoubleValue Config_CrystalsSpeedReduction;
    public static final ForgeConfigSpec.IntValue Config_ScrapperTickInterval;


    //Structures
    public static final ForgeConfigSpec.BooleanValue Config_GenerateRunestoneDungeon;
    public static final ForgeConfigSpec.IntValue Config_DungeonDensity;
    public static final ForgeConfigSpec.IntValue Config_RunestoneDungeonFloors;
    public static final ForgeConfigSpec.BooleanValue Config_GenerateRuins;
    public static final ForgeConfigSpec.BooleanValue Config_GenerateMoonTemple;
    public static final ForgeConfigSpec.BooleanValue Config_GenerateMoonTemplePath;
    public static final ForgeConfigSpec.BooleanValue Config_GenerateUmbraTower;
    public static final ForgeConfigSpec.BooleanValue Config_GenerateMushroomCaves;
    public static final ForgeConfigSpec.BooleanValue Config_GenerateGraveyards;
    public static final ForgeConfigSpec.BooleanValue Config_GenerateDarkstoneDungeon;


    //Entities
    public static final ForgeConfigSpec.BooleanValue Config_Nightmaremode;
    public static final ForgeConfigSpec.DoubleValue Config_Nightmaremode_Multiplier;
    public static final ForgeConfigSpec.IntValue Config_RunestoneDungeonMobDensity;
    public static final ForgeConfigSpec.IntValue Config_MoonTempleMobDensity;
    public static final ForgeConfigSpec.IntValue Config_DarkstoneDungeonMobDensity;
    public static final ForgeConfigSpec.DoubleValue Config_RunestoneKeeperHealthMuliplier;
    public static final ForgeConfigSpec.DoubleValue Config_MoonQueenHealthMuliplier;
    public static final ForgeConfigSpec.DoubleValue Config_SpiderMotherHealthMuliplier;
    public static final ForgeConfigSpec.DoubleValue Config_RunestoneKeeperDamageMuliplier;
    public static final ForgeConfigSpec.DoubleValue Config_MoonQueenDamageMuliplier;
    public static final ForgeConfigSpec.DoubleValue Config_SpiderMotherDamageMuliplier;

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
    public static final ForgeConfigSpec.IntValue Config_StrangeMeatUses;
    public static final ForgeConfigSpec.IntValue Config_AurorianiteAxeMaxChopSize;
    public static final ForgeConfigSpec.DoubleValue Config_LightningEnchantmentMulitplier;
    public static final ForgeConfigSpec.IntValue Config_UmbraShieldTimeUntilOverheat;
    public static final ForgeConfigSpec.IntValue Config_UmbraShieldOverheatCooldown;
    public static final ForgeConfigSpec.IntValue Config_UmbraSwordCooldown;
    public static final ForgeConfigSpec.IntValue Config_SlimeBootsCooldown;
    public static final ForgeConfigSpec.IntValue Config_OrbOfAbsorptionWhitelistBlacklist;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Config_OrbOfAbsorptionList;
    private static final ForgeConfigSpec.DoubleValue Config_UmbraPickaxeMiningSpeedMultiplier;

    private static final ForgeConfigSpec.BooleanValue Config_MoonlightForgeTransfersEnchants;

    private static final ForgeConfigSpec.IntValue Config_AurorianSteel_BaseMaxLevel;

    private static final ForgeConfigSpec.DoubleValue Config_AurorianSteel_BaseMaxLevelMultiplier;

    private static final ForgeConfigSpec.IntValue Config_CrystalStackSize;

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> Config_AurorianSteel_Enchants;

    private static final ForgeConfigSpec.IntValue Config_AurorianSteel_Enchants_WhitelistBlacklist;

    private static final ForgeConfigSpec.DoubleValue Config_Spectral_Armor_CleanseChance;


    private static final ForgeConfigSpec.DoubleValue Config_Silentwood_Multiplier_Speed;

    private static final ForgeConfigSpec.DoubleValue Config_Silentwood_Multiplier_Durability;

    private static final ForgeConfigSpec.IntValue Config_Silentwood_HarvestLevel;

    private static final ForgeConfigSpec.DoubleValue Config_AurorianStone_Multiplier_Damage;

    private static final ForgeConfigSpec.DoubleValue Config_AurorianStone_Multiplier_Speed;

    private static final ForgeConfigSpec.DoubleValue Config_AurorianStone_Multiplier_Durability;

    private static final ForgeConfigSpec.IntValue Config_AurorianStone_HarvestLevel;

    private static final ForgeConfigSpec.DoubleValue Config_Moonstone_Multiplier_Damage;

    private static final ForgeConfigSpec.DoubleValue Config_Moonstone_Multiplier_Speed;

    private static final ForgeConfigSpec.DoubleValue Config_Moonstone_Multiplier_Durability;

    private static final ForgeConfigSpec.IntValue Config_Moonstone_HarvestLevel;

    private static final ForgeConfigSpec.DoubleValue Config_Special_Multiplier_Damage;

    private static final ForgeConfigSpec.DoubleValue Config_Special_Multiplier_Speed;

    private static final ForgeConfigSpec.DoubleValue Config_Special_Multiplier_Durability;

    private static final ForgeConfigSpec.IntValue Config_Special_HarvestLevel;

    private static final ForgeConfigSpec.DoubleValue Config_AurorianSteel_Multiplier_Damage;

    private static final ForgeConfigSpec.DoubleValue Config_AurorianSteel_Multiplier_Speed;

    private static final ForgeConfigSpec.DoubleValue Config_AurorianSteel_Multiplier_Durability;

    private static final ForgeConfigSpec.DoubleValue Config_AurorianSteel_Multiplier_Armor;

    private static final ForgeConfigSpec.IntValue Config_AurorianSteel_HarvestLevel;

    private static final ForgeConfigSpec.DoubleValue Config_Cerulean_Multiplier_Durability;

    private static final ForgeConfigSpec.DoubleValue Config_Cerulean_Multiplier_Armor;

    private static final ForgeConfigSpec.DoubleValue Config_Tea_EffectDuration_Muliplier;

    private static final ForgeConfigSpec.DoubleValue Config_Spectral_Multiplier_Durability;

    private static final ForgeConfigSpec.DoubleValue Config_Spectral_Multiplier_Armor;

    //Multipliers
    static {
        BUILDER.push("Multipliers");
        Config_Silentwood_Multiplier_Speed = BUILDER
                .comment("Multiplier for mining speed")
                .defineInRange("Silentwood_Multiplier_Speed", 1.0f, 0.0f, 1000.0f);
        Config_Silentwood_Multiplier_Durability = BUILDER
                .comment("Multiplier for tool/armor durability")
                .defineInRange("Silentwood_Multiplier_Durability", 1.0f, 0.0f, 1000.0f);
        Config_Silentwood_HarvestLevel = BUILDER
                .comment("Harvest level for these tools")
                .defineInRange("Silentwood_HarvestLevel", 0, 0, 500);
        Config_AurorianStone_Multiplier_Damage = BUILDER
                .comment("Multiplier for damage")
                .defineInRange("AurorianStone_Multiplier_Damage", 1.0f, 0.0f, 1000.0f);
        Config_AurorianStone_Multiplier_Speed = BUILDER
                .comment("Multiplier for mining speed")
                .defineInRange("AurorianStone_Multiplier_Speed", 1.0f, 0.0f, 1000.0f);
        Config_AurorianStone_Multiplier_Durability = BUILDER
                .comment("Multiplier for tool/armor durability")
                .defineInRange("AurorianStone_Multiplier_Durability", 1.0f, 0.0f, 1000.0f);
        Config_AurorianStone_HarvestLevel = BUILDER
                .comment("Harvest level for these tools")
                .defineInRange("AurorianStone_HarvestLevel", 0, 0, 500);
        Config_Moonstone_Multiplier_Damage = BUILDER
                .comment("Multiplier for damage")
                .defineInRange("Moonstone_Multiplier_Damage", 1.0f, 0.0f, 1000.0f);
        Config_Moonstone_Multiplier_Speed = BUILDER
                .comment("Multiplier for mining speed")
                .defineInRange("Moonstone_Multiplier_Speed", 1.0f, 0.0f, 1000.0f);
        Config_Moonstone_Multiplier_Durability = BUILDER
                .comment("Multiplier for tool/armor durability")
                .defineInRange("Moonstone_Multiplier_Durability", 1.0f, 0.0f, 1000.0f);
        Config_Moonstone_HarvestLevel = BUILDER
                .comment("Harvest level for these tools")
                .defineInRange("Moonstone_HarvestLevel", 2, 0, 500);
        Config_Special_Multiplier_Damage = BUILDER
                .comment("Multiplier for damage")
                .defineInRange("Special_Multiplier_Damage", 1.0f, 0.0f, 1000.0f);
        Config_Special_Multiplier_Speed = BUILDER
                .comment("Multiplier for mining speed")
                .defineInRange("Special_Multiplier_Speed", 1.0f, 0.0f, 1000.0f);
        Config_Special_Multiplier_Durability = BUILDER
                .comment("Multiplier for tool/armor durability")
                .defineInRange("Special_Multiplier_Durability", 1.0f, 0.0f, 1000.0f);
        Config_Special_HarvestLevel = BUILDER
                .comment("Harvest level for these tools")
                .defineInRange("Special_HarvestLevel", 3, 0, 500);
        Config_AurorianSteel_Multiplier_Damage = BUILDER
                .comment("Multiplier for damage")
                .defineInRange("AurorianSteel_Multiplier_Damage", 1.0f, 0.0f, 1000.0f);
        Config_AurorianSteel_Multiplier_Speed = BUILDER
                .comment("Multiplier for mining speed")
                .defineInRange("AurorianSteel_Multiplier_Speed", 1.0f, 0.0f, 1000.0f);
        Config_AurorianSteel_Multiplier_Durability = BUILDER
                .comment("Multiplier for tool/armor durability")
                .defineInRange("AurorianSteel_Multiplier_Durability", 1.0f, 0.0f, 1000.0f);
        Config_AurorianSteel_Multiplier_Armor = BUILDER
                .comment("Multiplier for armor strength")
                .defineInRange("AurorianSteel_Multiplier_Armor", 1.0f, 0.0f, 1000.0f);
        Config_AurorianSteel_HarvestLevel = BUILDER
                .comment("Harvest level for these tools")
                .defineInRange("AurorianSteel_HarvestLevel", 3, 0, 500);
        Config_Cerulean_Multiplier_Durability = BUILDER
                .comment("Multiplier for tool/armor durability")
                .defineInRange("Cerulean_Multiplier_Durability", 1.0f, 0.0f, 1000.0f);
        Config_Cerulean_Multiplier_Armor = BUILDER
                .comment("Multiplier for armor strength")
                .defineInRange("Cerulean_Multiplier_Armor", 1.0f, 0.0f, 1000.0f);
        Config_Tea_EffectDuration_Muliplier = BUILDER
                .comment("Multiplier for tea potion effect duration")
                .defineInRange("Tea_EffectDuration_Muliplier", 1.0f, 0.0f, 1000.0f);
        Config_Spectral_Multiplier_Durability = BUILDER
                .comment("Multiplier for tool/armor durability")
                .defineInRange("Spectral_Multiplier_Durability", 1.0f, 0.0f, 1000.0f);
        Config_Spectral_Multiplier_Armor = BUILDER
                .comment("Multiplier for armor strength")
                .defineInRange("Spectral_Multiplier_Armor", 1.0f, 0.0f, 1000.0f);
        BUILDER.pop();
    }





    //Compat
    static {
        BUILDER.push("Compatibility");
        Config_EnableTinkersConstructCompatibility = BUILDER.comment("Enable Tinkers' Construct compatibility.")
                .define("EnableTinkersConstructCompatibility", true);

        Config_EnableConstructsArmoryCompatibility = BUILDER.comment("Enable Constructs' Armory compatibility.")
                .define("EnableConstructsArmoryCompatibility", true);
        BUILDER.pop();
    }
    //blocks
    static {
        BUILDER.push("Blocks");
        Config_MaximumChimneys = BUILDER
                .comment("Maximum number of chimneys able to be stacked on Aurorian Furnace")
                .defineInRange("MaximumChimneys", 10, 0, 255);

        Config_ChimneySpeedMultiplier = BUILDER
                .comment("Maximum number of chimneys able to be stacked on Aurorian Furnace")
                .defineInRange("ChimneySpeedMultiplier", 0.5D, 0.0D, 0.99D);

        Config_CrystalsSpeedUpMachines = BUILDER
                .comment("Set to false to disable Crystals speeding up machines when placed on top")
                .define("CrystalsSpeedUpMachines", true);

        Config_CrystalsChanceOfBreaking = BUILDER
                .comment("The chance for a Crystal to break when the machine finishes a recipe. (0.5 is 50%, 0 is no breaking)")
                .defineInRange("CrystalsChanceOfBreaking", 0.25f, 0.0f, 1.0f);
        Config_CrystalsSpeedReduction = BUILDER
                .comment("How much a Crystal will speed up the machine below it (LOWER percentage = FASTER machine, yes I know its backwards)")
                .defineInRange("CrystalsSpeedReduction", 0.5f, 0.01f, 1.0f);
        Config_ScrapperTickInterval = BUILDER
                .comment("How many ticks until the scrapper will perform 1 update, Scrapper requires 100 updates to do 1 craft. (Meaning default is 400 ticks(20 seconds) for 1 craft)")
                .defineInRange("ScrapperTickInterval", 4, 0, 72000);
        BUILDER.pop();
    }

    //Structures
    static {
        BUILDER.push("Structures");
        Config_GenerateRunestoneDungeon = BUILDER
                .comment("Set to false to disable Runestone Dungeons (Why would anyone do this? :c )")
                .define("GenerateRunestoneDungeon", true);
        Config_DungeonDensity = BUILDER.comment("How many chunks away until another Runestone Dungeons can generate, also affects Moon Temple generation")
                .defineInRange("DungeonDensity",32,16,256);
        Config_RunestoneDungeonFloors = BUILDER
                .comment("How many floors each Runestone Dungeon has, including double sized floors, code only accepts odd numbers! Evens will have +1 added")
                .defineInRange("RunestoneDungeonFloors",4,1,17);
        Config_GenerateRuins = BUILDER
                .comment("Set to false to disable ruin structures (like destroyed houses or small underground structures)")
                .define("GenerateRuins",true);
        Config_GenerateMoonTemple = BUILDER
                .comment("Set to false to disable Moon Temples")
                .define("GenerateMoonTemple",true);
        Config_GenerateMoonTemplePath = BUILDER
                .comment("Set to false to disable Moon Temple's spiral path up")
                .define("GenerateMoonTemplePath",true);
        Config_GenerateUmbraTower = BUILDER
                .comment("Set to false to disable Umbra Towers")
                .define("GenerateUmbraTower",true);
        Config_GenerateMushroomCaves = BUILDER
                .comment("Set to false to disable Mushroom Caves")
                .define("GenerateMushroomCaves",true);
        Config_GenerateGraveyards = BUILDER
                .comment("Set to false to disable Graveyards")
                .define("GenerateGraveyards",false);
        Config_GenerateDarkstoneDungeon = BUILDER
                .comment("Set to false to disable Darkstone Dungeons")
                .define("GenerateDarkstoneDungeon",true);
        BUILDER.pop();
    }


    //Entities
    static {
        BUILDER.push("Entity");
        Config_Nightmaremode = BUILDER
                .comment("Enable to make Aurorian mobs(not bosses) fast and strong, change multiplier to adjust the strength of these effects")
                .define("Nightmaremode", false);
        Config_Nightmaremode_Multiplier = BUILDER
                .comment("Use at your own risk!")
                .defineInRange("NightmaremodeMultiplier", 1.0f, 0.0f, 50.0f);
        Config_RunestoneDungeonMobDensity = BUILDER
                .comment("Density of mobs spawning in the Runestone Dungeon, 2 for twice as many mobs, etc")
                .defineInRange("RunestoneDungeonMobDensity", 1, 0, 10);
        Config_MoonTempleMobDensity = BUILDER
                .comment("Density of mobs spawning in the Moon Temple, 2 for twice as many mobs, etc")
                .defineInRange("MoonTempleMobDensity", 1, 0, 10);
        Config_DarkstoneDungeonMobDensity = BUILDER
                .comment("Density of mobs spawning in the Darkstone Dungeon, 2 for twice as many mobs, etc")
                .defineInRange("DarkstoneDungeonMobDensity", 1, 0, 10);
        Config_RunestoneKeeperHealthMuliplier = BUILDER
                .defineInRange("RunestoneKeeperHealthMuliplier", 1.0f, 0.0f, 100.0f);
        Config_MoonQueenHealthMuliplier = BUILDER
                .defineInRange("MoonQueenHealthMuliplier", 1.0f, 0.0f, 100.0f);
        Config_SpiderMotherHealthMuliplier = BUILDER
                .defineInRange("SpiderMotherHealthMuliplier", 1.0f, 0.0f, 100.0f);
        Config_RunestoneKeeperDamageMuliplier = BUILDER
                .defineInRange("RunestoneKeeperDamageMuliplier", 1.0f, 0.0f, 100.0f);
        Config_MoonQueenDamageMuliplier = BUILDER
                .defineInRange("MoonQueenDamageMuliplier", 1.0f, 0.0f, 100.0f);
        Config_SpiderMotherDamageMuliplier = BUILDER
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
        Config_StrangeMeatUses = BUILDER
                .comment("How many uses Strange Meat has")
                .defineInRange("StrangeMeatUses", 10, 1, 72000);
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
        Config_OrbOfAbsorptionDurability = BUILDER
                .comment("Max durability of the Orb of Absorption")
                .defineInRange("OrbOfAbsorptionDurability", 250, 1, 72000);
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
        Config_Spectral_Armor_CleanseChance = BUILDER
                .comment("Percent for one armor piece to cleanse the player’s negative effects, stacks with other armor parts (full spectral armor with 6% is a 24% chance with full set)")
                .defineInRange("Spectral_Armor_CleanseChance", 0.06F, 0.01F, 1.0F);
        BUILDER.pop();
    }


    static {
        SPEC = BUILDER.build();
    }
    public static void setup()
    {
        Path configPath = FMLPaths.CONFIGDIR.get();
        Path configFolder = Paths.get(configPath.toAbsolutePath().toString(), AurorianMod.MOD_ID);

        try
        {
            Files.createDirectory(configFolder);
        }
        catch (Exception ignored) {}

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "theaurorian/TheAurorian.toml");
    }
}
