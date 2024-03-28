package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.*;
import cn.teampancake.theaurorian.common.items.armor.*;
import cn.teampancake.theaurorian.common.items.developer.*;
import cn.teampancake.theaurorian.common.items.shield.CeruleanShield;
import cn.teampancake.theaurorian.common.items.shield.CrystallineShield;
import cn.teampancake.theaurorian.common.items.shield.MoonShield;
import cn.teampancake.theaurorian.common.items.shield.UmbraShield;
import cn.teampancake.theaurorian.common.items.tool.*;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;

import static cn.teampancake.theaurorian.common.utils.TAItemRegUtils.*;
import static net.minecraft.world.item.ArmorItem.Type.*;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class TAItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AurorianMod.MOD_ID);

    /**
     * Materials
     */
    public static final RegistryObject<Item> RAW_CERULEAN = normal("raw_cerulean", false);
    public static final RegistryObject<Item> RAW_MOONSTONE = normal("raw_moonstone", false);
    public static final RegistryObject<Item> AURORIAN_COAL = normal("aurorian_coal", true);
    public static final RegistryObject<Item> AURORIANITE_INGOT = normal("aurorianite_ingot", true);
    public static final RegistryObject<Item> AURORIAN_STEEL = normal("aurorian_steel", true);
    public static final RegistryObject<Item> CERULEAN_INGOT = normal("cerulean_ingot", true);
    public static final RegistryObject<Item> CRYSTALLINE_INGOT = normal("crystalline_ingot", true);
    public static final RegistryObject<Item> MOONSTONE_INGOT = normal("moonstone_ingot", true);
    public static final RegistryObject<Item> UMBRA_INGOT = normal("umbra_ingot", true);
    public static final RegistryObject<Item> LAVENDER = normal("lavender", true);
    public static final RegistryObject<Item> PLANT_FIBER = normal("plant_fiber", true);
    public static final RegistryObject<Item> AURORIANITE_SCRAP = normal("aurorianite_scrap", true);
    public static final RegistryObject<Item> CRYSTALLINE_SCRAP = normal("crystalline_scrap", true);
    public static final RegistryObject<Item> UMBRA_SCRAP = normal("umbra_scrap", true);
    public static final RegistryObject<Item> SPECTRAL_SILK = normal("spectral_silk", true);
    public static final RegistryObject<Item> DARK_AMULET = normal("dark_amulet", true);
    public static final RegistryObject<Item> DUNGEON_KEEPER_AMULET = normal("dungeon_keeper_amulet", true);

    /**
     * Armor Item
     */
    public static final RegistryObject<Item> AURORIAN_STEEL_HELMET = ITEMS.register("aurorian_steel_helmet", () -> new AurorianSteelArmor(HELMET));
    public static final RegistryObject<Item> AURORIAN_STEEL_CHESTPLATE = ITEMS.register("aurorian_steel_chestplate", () -> new AurorianSteelArmor(CHESTPLATE));
    public static final RegistryObject<Item> AURORIAN_STEEL_LEGGINGS = ITEMS.register("aurorian_steel_leggings", () -> new AurorianSteelArmor(LEGGINGS));
    public static final RegistryObject<Item> AURORIAN_STEEL_BOOTS = ITEMS.register("aurorian_steel_boots", () -> new AurorianSteelArmor(BOOTS));
    public static final RegistryObject<Item> CERULEAN_HELMET = ITEMS.register("cerulean_helmet", () -> new CeruleanArmor(HELMET));
    public static final RegistryObject<Item> CERULEAN_CHESTPLATE = ITEMS.register("cerulean_chestplate", () -> new CeruleanArmor(CHESTPLATE));
    public static final RegistryObject<Item> CERULEAN_LEGGINGS = ITEMS.register("cerulean_leggings", () -> new CeruleanArmor(LEGGINGS));
    public static final RegistryObject<Item> CERULEAN_BOOTS = ITEMS.register("cerulean_boots", () -> new CeruleanArmor(BOOTS));
    public static final RegistryObject<Item> KNIGHT_HELMET = ITEMS.register("knight_helmet", () -> new KnightArmor(HELMET));
    public static final RegistryObject<Item> KNIGHT_CHESTPLATE = ITEMS.register("knight_chestplate", () -> new KnightArmor(CHESTPLATE));
    public static final RegistryObject<Item> KNIGHT_LEGGINGS = ITEMS.register("knight_leggings", () -> new KnightArmor(LEGGINGS));
    public static final RegistryObject<Item> KNIGHT_BOOTS = ITEMS.register("knight_boots", () -> new KnightArmor(BOOTS));
    public static final RegistryObject<Item> SPECTRAL_HELMET = ITEMS.register("spectral_helmet", () -> new SpectralArmor(HELMET));
    public static final RegistryObject<Item> SPECTRAL_CHESTPLATE = ITEMS.register("spectral_chestplate", () -> new SpectralArmor(CHESTPLATE));
    public static final RegistryObject<Item> SPECTRAL_LEGGINGS = ITEMS.register("spectral_leggings", () -> new SpectralArmor(LEGGINGS));
    public static final RegistryObject<Item> SPECTRAL_BOOTS = ITEMS.register("spectral_boots", () -> new SpectralArmor(BOOTS));
    public static final RegistryObject<Item> MYSTERIUM_WOOL_HELMET = ITEMS.register("mysterium_wool_helmet", () -> new MysteriumWoolArmor(HELMET));
    public static final RegistryObject<Item> MYSTERIUM_WOOL_CHESTPLATE = ITEMS.register("mysterium_wool_chestplate", () -> new MysteriumWoolArmor(CHESTPLATE));
    public static final RegistryObject<Item> MYSTERIUM_WOOL_LEGGINGS = ITEMS.register("mysterium_wool_leggings", () -> new MysteriumWoolArmor(LEGGINGS));
    public static final RegistryObject<Item> MYSTERIUM_WOOL_BOOTS = ITEMS.register("mysterium_wool_boots", () -> new MysteriumWoolArmor(BOOTS));
    public static final RegistryObject<Item> CRYSTAL_RUNE_HELMET = ITEMS.register("crystal_rune_helmet", () -> new CrystalRuneArmor(HELMET));
    public static final RegistryObject<Item> CRYSTAL_RUNE_CHESTPLATE = ITEMS.register("crystal_rune_chestplate", () -> new CrystalRuneArmor(CHESTPLATE));
    public static final RegistryObject<Item> CRYSTAL_RUNE_LEGGINGS = ITEMS.register("crystal_rune_leggings", () -> new CrystalRuneArmor(LEGGINGS));
    public static final RegistryObject<Item> CRYSTAL_RUNE_BOOTS = ITEMS.register("crystal_rune_boots", () -> new CrystalRuneArmor(BOOTS));
    public static final RegistryObject<Item> SPIKED_CHESTPLATE = ITEMS.register("spiked_chestplate", SpikedChestplate::new);
    public static final RegistryObject<Item> AURORIAN_SLIME_BOOTS = ITEMS.register("aurorian_slime_boots", AurorianSlimeBoots::new);

    /**
     * SwordItem
     */
    public static final RegistryObject<Item> AURORIAN_STEEL_SWORD = ITEMS.register("aurorian_steel_sword", AurorianSteelSword::new);
    public static final RegistryObject<Item> AURORIAN_STONE_SWORD = ITEMS.register("aurorian_stone_sword",
            () -> new SwordItem(TAToolTiers.AURORIAN_STONE, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> SILENT_WOOD_SWORD = ITEMS.register("silent_wood_sword",
            () -> new SwordItem(TAToolTiers.SILENT_WOOD, 4, -1.6F, new Item.Properties()));
    public static final RegistryObject<Item> UMBRA_SWORD = ITEMS.register("umbra_sword", UmbraSword::new);
    public static final RegistryObject<Item> AURORIANITE_SWORD = ITEMS.register("aurorianite_sword", AurorianiteSword::new);
    public static final RegistryObject<Item> CRYSTALLINE_SWORD = ITEMS.register("crystalline_sword", CrystallineSword::new);
    public static final RegistryObject<Item> MOONSTONE_SWORD = ITEMS.register("moonstone_sword",
            () -> new SwordItem(TAToolTiers.SILENT_WOOD, 4, -1.6F, new Item.Properties()));

    /**
     * ShovelItem
     */
    public static final RegistryObject<Item> AURORIAN_STEEL_SHOVEL = ITEMS.register("aurorian_steel_shovel",
            () -> new ShovelItem(TAToolTiers.AURORIAN_STEEL, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STONE_SHOVEL = ITEMS.register("aurorian_stone_shovel",
            () -> new ShovelItem(TAToolTiers.AURORIAN_STONE, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> SILENT_WOOD_SHOVEL = ITEMS.register("silent_wood_shovel",
            () -> new ShovelItem(TAToolTiers.SILENT_WOOD, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> MOONSTONE_SHOVEL = ITEMS.register("moonstone_shovel",
            () -> new ShovelItem(TAToolTiers.SILENT_WOOD, 1.5F, -3.0F, new Item.Properties()));

    /**
     * AxeItem
     */
    public static final RegistryObject<Item> AURORIAN_STEEL_AXE = ITEMS.register("aurorian_steel_axe",
            () -> new AxeItem(TAToolTiers.AURORIAN_STEEL, 6.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIANITE_AXE = ITEMS.register("aurorianite_axe", AurorianiteAxe::new);
    public static final RegistryObject<Item> AURORIAN_STONE_AXE = ITEMS.register("aurorian_stone_axe", AurorianStoneAxe::new);
    public static final RegistryObject<Item> SILENT_WOOD_AXE = ITEMS.register("silent_wood_axe",
            () -> new AxeItem(TAToolTiers.SILENT_WOOD, 6.0F, -3.2F, new Item.Properties()));
    public static final RegistryObject<Item> MOONSTONE_AXE = ITEMS.register("moonstone_axe",
            () -> new AxeItem(TAToolTiers.SILENT_WOOD, 6.0F, -3.0F, new Item.Properties()));

    /**
     * PickaxeItem
     */
    public static final RegistryObject<Item> AURORIAN_STEEL_PICKAXE = ITEMS.register("aurorian_steel_pickaxe",
            () -> new PickaxeItem(TAToolTiers.AURORIAN_STEEL, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIANITE_PICKAXE = ITEMS.register("aurorianite_pickaxe",
            () -> new PickaxeItem(TAToolTiers.AURORIANITE, 1, -3.0F, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> UMBRA_PICKAXE = ITEMS.register("umbra_pickaxe", UmbraPickaxe::new);
    public static final RegistryObject<Item> CRYSTALLINE_PICKAXE = ITEMS.register("crystalline_pickaxe", CrystallinePickaxe::new);
    public static final RegistryObject<Item> AURORIAN_STONE_PICKAXE = ITEMS.register("aurorian_stone_pickaxe", AurorianStonePickaxe::new);
    public static final RegistryObject<Item> SILENT_WOOD_PICKAXE = ITEMS.register("silent_wood_pickaxe", SilentWoodPickaxe::new);
    public static final RegistryObject<Item> MOONSTONE_PICKAXE = ITEMS.register("moonstone_pickaxe",
            () -> new PickaxeItem(TAToolTiers.SILENT_WOOD, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> QUEENS_CHIPPER = ITEMS.register("queens_chipper", QueensChipper::new);

    /**
     * HoeItem
     */
    public static final RegistryObject<Item> AURORIAN_STEEL_HOE = ITEMS.register("aurorian_steel_hoe",
            () -> new HoeItem(TAToolTiers.AURORIAN_STEEL, -2, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STONE_HOE = ITEMS.register("aurorian_stone_hoe",
            () -> new HoeItem(TAToolTiers.AURORIAN_STONE, -2, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> SILENT_WOOD_HOE = ITEMS.register("silent_wood_hoe",
            () -> new HoeItem(TAToolTiers.SILENT_WOOD, -2, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> MOONSTONE_HOE = ITEMS.register("moonstone_hoe",
            () -> new HoeItem(TAToolTiers.SILENT_WOOD, -2, -1.0F, new Item.Properties()));

    /**
     * ShearsItem
     */
    public static final RegistryObject<Item> SILENT_WOOD_SICKLE = ITEMS.register("silent_wood_sickle",
            () -> new ShearsItem(new Item.Properties().durability(50)));
    public static final RegistryObject<Item> AURORIAN_STONE_SICKLE = ITEMS.register("aurorian_stone_sickle",
            () -> new ShearsItem(new Item.Properties().durability(150)));
    public static final RegistryObject<Item> MOONSTONE_SICKLE = ITEMS.register("moonstone_sickle", MoonstoneSickle::new);

    /**
     * ShieldItem
     */
    public static final RegistryObject<Item> MOON_SHIELD = ITEMS.register("moon_shield", MoonShield::new);
    public static final RegistryObject<Item> UMBRA_SHIELD = ITEMS.register("umbra_shield", UmbraShield::new);
    public static final RegistryObject<Item> CERULEAN_SHIELD = ITEMS.register("cerulean_shield", CeruleanShield::new);
    public static final RegistryObject<Item> CRYSTALLINE_SHIELD = ITEMS.register("crystalline_shield", CrystallineShield::new);
    public static final RegistryObject<Item> MOONSTONE_SHIELD = ITEMS.register("moonstone_shield", () -> new ShieldItem(new Item.Properties()));

    /**
     * BowItem
     */
    public static final RegistryObject<Item> SILENT_WOOD_BOW = ITEMS.register("silent_wood_bow",
            () -> new BowItem(new Item.Properties().durability(384)));
    public static final RegistryObject<Item> KEEPERS_BOW = ITEMS.register("keepers_bow", KeepersBow::new);

    /**
     * Tea
     */
    public static final RegistryObject<Item> TEA_CUP = normal("tea_cup", false);
    public static final RegistryObject<Item> LAVENDER_TEA = ITEMS.register("lavender_tea", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300), 1.0F).build())));
    public static final RegistryObject<Item> SILK_BERRY_TEA = ITEMS.register("silk_berry_tea", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100), 1.0F).build())));
    public static final RegistryObject<Item> LAVENDER_SEEDY_TEA = ITEMS.register("lavender_seedy_tea", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 1.0F).build())));
    public static final RegistryObject<Item> PETUNIA_TEA = ITEMS.register("petunia_tea", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300), 1.0F).build())));
    public static final RegistryObject<Item> BEPSI = ITEMS.register("bepsi", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600), 1.0F).build())));

    /**
     * Food
     */
    public static final RegistryObject<Item> TALL_WICK_GRASS = ITEMS.register("tall_wick_grass",
            () -> new DoubleHighBlockItem(TABlocks.TALL_WICK_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_PORK = ITEMS.register("aurorian_pork",
            () -> new TASpecialItem(new Item.Properties().food(Foods.PORKCHOP), false));
    public static final RegistryObject<Item> COOKED_AURORIAN_PORK = ITEMS.register("cooked_aurorian_pork",
            () -> new TASpecialItem(new Item.Properties().food(Foods.COOKED_PORKCHOP), false));
    public static final RegistryObject<Item> AURORIAN_BEEF = ITEMS.register("aurorian_beef",
            () -> new TASpecialItem(new Item.Properties().food(Foods.BEEF), false));
    public static final RegistryObject<Item> COOKED_AURORIAN_BEEF = ITEMS.register("cooked_aurorian_beef",
            () -> new TASpecialItem(new Item.Properties().food(Foods.COOKED_BEEF), false));
    public static final RegistryObject<Item> AURORIAN_MUTTON = ITEMS.register("aurorian_mutton",
            () -> new TASpecialItem(new Item.Properties().food(Foods.MUTTON), false));
    public static final RegistryObject<Item> COOKED_AURORIAN_MUTTON = ITEMS.register("cooked_aurorian_mutton",
            () -> new TASpecialItem(new Item.Properties().food(Foods.COOKED_MUTTON), false));
    public static final RegistryObject<Item> AURORIAN_RABBIT = ITEMS.register("aurorian_rabbit",
            () -> new TASpecialItem(new Item.Properties().food(Foods.RABBIT), false));
    public static final RegistryObject<Item> COOKED_AURORIAN_RABBIT = ITEMS.register("cooked_aurorian_rabbit",
            () -> new TASpecialItem(new Item.Properties().food(Foods.COOKED_RABBIT), false));
    public static final RegistryObject<Item> WEEPING_WILLOW_SAP = ITEMS.register("weeping_willow_sap", WeepingWillowSap::new);
    public static final RegistryObject<Item> SILK_BERRY_JAM = food("silk_berry_jam", 2, 0.5F, false);
    public static final RegistryObject<Item> SILK_BERRY_JAM_SANDWICH = food("silk_berry_jam_sandwich", 6, 0.9F, false);
    public static final RegistryObject<Item> AURORIAN_SLIMEBALL = food("aurorian_slimeball", 1, 0.2F, false);
    public static final RegistryObject<Item> SILK_SHROOM_STEW = food("silk_shroom_stew", 6, 1F, false);
    public static final RegistryObject<Item> LAVENDER_BREAD = food("lavender_bread", 4, 0.4F, false);
    public static final RegistryObject<Item> SOULLESS_FLESH = food("soulless_flesh", 2, 0.1F, false);
    public static final RegistryObject<Item> MOON_FISH = food("moon_fish", 2, 0.4F, false);
    public static final RegistryObject<Item> AURORIAN_WINGED_FISH = food("aurorian_winged_fish",2,0.4F,false);
    public static final RegistryObject<Item> COOKED_MOON_FISH = food("cooked_moon_fish",5,6,false);
    public static final RegistryObject<Item> COOKED_AURORIAN_WINGED_FISH = food("cooked_aurorian_winged_fish",5,6,false);
    public static final RegistryObject<Item> LAVENDER_SEEDS = alias("lavender_seeds", TABlocks.LAVENDER_CROP, new Item.Properties());
    public static final RegistryObject<Item> SILK_BERRY = alias("silk_berry", TABlocks.SILK_BERRY_CROP,
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod((0.1F)).build()));
    public static final RegistryObject<Item> BLUEBERRY = alias("blueberry", TABlocks.BLUEBERRY_BUSH, new Item.Properties().food(Foods.SWEET_BERRIES));
    public static final RegistryObject<Item> CANDY = food("candy", 4, 0.2F, false);
    public static final RegistryObject<Item> CANDY_CANE = ITEMS.register("candy_cane", () -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(4).saturationMod((0.4F))
                    .effect(() -> new MobEffectInstance(MobEffects.LUCK, 300), 1.0F).build())));
    public static final RegistryObject<Item> GINGERBREAD_MAN = ITEMS.register("gingerbread_man", () -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(6).saturationMod((0.4F))
                    .effect(() -> new MobEffectInstance(TAMobEffects.WARM.get(), 1200), 1.0F).build())));
    public static final RegistryObject<Item> AURORIAN_SPECIALTY_DRINK = ITEMS.register("aurorian_specialty_drink", () -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600), 1.0F).build())));
    public static final RegistryObject<Item> MOONLIT_BLUEBERRY_SPECIALTY_DRINK = ITEMS.register("moonlit_blueberry_specialty_drink", () -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1), 1.0F).build())));
    public static final RegistryObject<Item> AURORIAN_BACON = ITEMS.register("aurorian_bacon", () -> new TASpecialItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod((0.8F))
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60), 1.0F).build()), true));
    public static final RegistryObject<Item> STRANGE_MEAT = ITEMS.register("strange_meat", StrangeMeat::new);
    public static final RegistryObject<Item> LAVENDER_SALAD = ITEMS.register("lavender_salad", () -> new TASpecialItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod((5.0F))
                    .effect(() -> new MobEffectInstance(TAMobEffects.NATURE.get(), 600), 1.0F).build()), true));
    public static final RegistryObject<Item> FAKE_ALGAL_PIT_FISH = ITEMS.register("fake_algal_pit_fish", () -> new TASpecialItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod((5.0F)).meat().build()), true));
    public static final RegistryObject<Item> SASHIMI = ITEMS.register("sashimi", () -> new TASpecialItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationMod((2.0F)).meat()
                    .effect(() -> new MobEffectInstance(MobEffects.LUCK, 400), 1.0F).build()), true));
    public static final RegistryObject<Item> SILENT_WOOD_FRUIT = ITEMS.register("silent_wood_fruit", () -> new TASpecialItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod((2.5F)).build()), true));
    public static final RegistryObject<Item> GOLDEN_SILENT_WOOD_FRUIT = ITEMS.register("golden_silent_wood_fruit", () -> new TASpecialItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod((5.0F)).build()), true));
    public static final RegistryObject<Item> KEBAB_WITH_MUSHROOM = ITEMS.register("kebab_with_mushroom", () -> new TASpecialItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod((15.0F))
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200), 1.0F).build()), true));
    public static final RegistryObject<Item> AURORIAN_WINTER_ROOT = ITEMS.register("aurorian_winter_root", () -> new TASpecialItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod((0.8F)).build()), true));
    public static final RegistryObject<Item> ROASTED_AURORIAN_WINTER_ROOT = ITEMS.register("roasted_aurorian_winter_root", () -> new TASpecialItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod((6.0F))
                    .effect(() -> new MobEffectInstance(TAMobEffects.WARM.get(), 400), 1.0F).build()), true));
    public static final RegistryObject<Item> DARK_STONE_SHRIMP = ITEMS.register("dark_stone_shrimp", () -> new TASpecialItem(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod((0.8F)).build()), true));

    /**
     * Key
     */
    public static final RegistryObject<Item> MOON_TEMPLE_CELL_KEY_FRAGMENT = normal("moon_temple_cell_key_fragment", true);
    public static final RegistryObject<Item> RUNE_STONE_KEY = ITEMS.register("rune_stone_key", () -> new TASpecialItem(new Item.Properties().rarity(Rarity.UNCOMMON), true));
    public static final RegistryObject<Item> DARK_STONE_KEY = ITEMS.register("dark_stone_key", () -> new TASpecialItem(new Item.Properties().rarity(Rarity.UNCOMMON), true));
    public static final RegistryObject<Item> MOON_TEMPLE_KEY = ITEMS.register("moon_temple_key", () -> new TASpecialItem(new Item.Properties().rarity(Rarity.UNCOMMON), true));
    public static final RegistryObject<Item> RUNE_STONE_LOOT_KEY = ITEMS.register("rune_stone_loot_key", () -> new TASpecialItem(new Item.Properties().rarity(Rarity.UNCOMMON), true));
    public static final RegistryObject<Item> MOON_TEMPLE_CELL_KEY = ITEMS.register("moon_temple_cell_key", () -> new TASpecialItem(new Item.Properties().rarity(Rarity.UNCOMMON), true));

    /**
     * Arrow
     */
    public static final RegistryObject<Item> CERULEAN_ARROW = ITEMS.register("cerulean_arrow", () -> new CeruleanArrow(new Item.Properties()));
    public static final RegistryObject<Item> CRYSTAL_ARROW = ITEMS.register("crystal_arrow", () -> new CrystalArrow(new Item.Properties()));

    /**
     * Tool
     */
    public static final RegistryObject<Item> ABSORPTION_ORB = ITEMS.register("absorption_orb", AbsorptionOrbItem::new);
    public static final RegistryObject<Item> SILENT_WOOD_STICK = ITEMS.register("silent_wood_stick", () -> new TASpecialItem(new Item.Properties(), true));
    public static final RegistryObject<Item> STICKY_SPIKER = ITEMS.register("sticky_spiker", () -> new SimpleThrowProjectProjectile(new Item.Properties()
            .rarity(Rarity.EPIC), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.STICKY_SPIKER::get, 1.5F, 1.0F));

    /**
     * Loot
     */
    public static final RegistryObject<Item> TROPHY_KEEPER = normal("trophy_keeper", true);
    public static final RegistryObject<Item> TROPHY_SPIDER_MOTHER = normal("trophy_spider_mother", true);
    public static final RegistryObject<Item> TROPHY_MOON_QUEEN = normal("trophy_moon_queen", true);
    public static final RegistryObject<Item> CRYSTALLINE_SPRITE = normal("crystalline_sprite", false);
    public static final RegistryObject<Item> RUNE_KNOWLEDGE_FRAGMENT = normal("rune_knowledge_fragment", false);

    /**
     * Runestone
     */
    public static final RegistryObject<Item> RUNESTONE_ICE = normal("runestone_ice", false);
    public static final RegistryObject<Item> RUNESTONE_LIFE = normal("runestone_life", false);
    public static final RegistryObject<Item> RUNESTONE_LIGHT = normal("runestone_light", false);
    public static final RegistryObject<Item> RUNESTONE_WATER = normal("runestone_water", false);
    public static final RegistryObject<Item> RUNESTONE_BLAZE = normal("runestone_blaze", false);
    public static final RegistryObject<Item> RUNESTONE_THUNDER = normal("runestone_thunder", false);
    public static final RegistryObject<Item> RUNESTONE_DARKNESS = normal("runestone_darkness", false);

    /**
     * Misc
     */
    public static final RegistryObject<Item> CRYSTAL = normal("crystal", true);
    public static final RegistryObject<Item> BROKEN_OX_HORN = normal("broken_ox_horn", true);
    public static final RegistryObject<Item> LUCKY_RABBIT_EAR = normal("lucky_rabbit_ear", true);
    public static final RegistryObject<Item> AURORIAN_STEEL_NUGGET = normal("aurorian_steel_nugget", false);
    public static final RegistryObject<Item> CERULEAN_NUGGET = normal("cerulean_nugget", false);
    public static final RegistryObject<Item> AURORIAN_COAL_NUGGET = normal("aurorian_coal_nugget", false);
    public static final RegistryObject<Item> MOONSTONE_NUGGET = normal("moonstone_nugget", false);
    public static final RegistryObject<Item> AURORIAN_CHAIN = normal("aurorian_chain", false);
    public static final RegistryObject<Item> AURORIAN_BERRY = normal("aurorian_berry", false);
    public static final RegistryObject<Item> AURORIAN_CRYSTAL = ITEMS.register("aurorian_crystal", AurorianCrystal::new);
    public static final RegistryObject<Item> EQUINOX_MUSHROOM = normal("equinox_mushroom", false);
    public static final RegistryObject<Item> DREAM_DYEING_CRYSTAL_FRAGMENT = normal("dream_dyeing_crystal_fragment", true);
    public static final RegistryObject<Item> WORLD_SCROLL_FRAGMENT = normal("world_scroll_fragment", false);
    public static final RegistryObject<Item> WORLD_SCROLL = ITEMS.register("world_scroll", WorldScroll::new);
    public static final RegistryObject<Item> DUNGEON_LOCATOR = ITEMS.register("dungeon_locator", DungeonLocatorItem::new);
    public static final RegistryObject<Item> WEBBING = ITEMS.register("webbing", () -> new SimpleThrowProjectProjectile(new Item.Properties(),
            SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.WEBBING::get, 0.5F, (0.4F / (new Random().nextFloat() * 0.4F + 0.8F))));
    public static final RegistryObject<Item> LIVING_DIVINING_ROD = ITEMS.register("living_divining_rod", LivingDiviningRod::new);
    public static final RegistryObject<Item> LOCK_PICKS = ITEMS.register("lock_picks", () -> new Item(new Item.Properties().durability(10)));
    public static final RegistryObject<Item> MOON_WATER_BUCKET = ITEMS.register("moon_water_bucket", () -> new BucketItem(TAFluids.MOON_WATER_STILL, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_WINGED_FISH_BUCKET = ITEMS.register("aurorian_winged_fish_bucket", () -> new MobBucketItem(TAEntityTypes.AURORIAN_WINGED_FISH,
            () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1)));
    public static final RegistryObject<Item> MOON_FISH_BUCKET = ITEMS.register("moon_fish_bucket", () -> new MobBucketItem(TAEntityTypes.MOON_FISH,
            () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1)));
    public static final RegistryObject<Item> DEVELOPER_GIFT = ITEMS.register("developer_gift", DeveloperGift::new);

    /**
     * Developer Item
     */
    public static final RegistryObject<Item> SLEEPING_BLACK_TEA = ITEMS.register("sleeping_black_tea", SleepingBlackTea::new);
    public static final RegistryObject<Item> WHITE_CHOCOLATE = ITEMS.register("white_chocolate", WhiteChocolate::new);
    public static final RegistryObject<Item> RED_BOOK = ITEMS.register("red_book", RedBook::new);
    public static final RegistryObject<Item> RED_BOOK_RING = ITEMS.register("red_book_ring", RedBookRing::new);
    public static final RegistryObject<Item> CAT_BELL = ITEMS.register("cat_bell", CatBell::new);
    public static final RegistryObject<Item> TSLAT_SWORD = ITEMS.register("tslat_sword", TslatSword::new);

    /**
     * Spawn Egg
     */
    public static final RegistryObject<Item> BREAD_BEAST_SPAWN_EGG = spawnEgg("bread_beast", TAEntityTypes.BREAD_BEAST, 0xc4b4a1, 0x734b41);
    public static final RegistryObject<Item> ICEFIELD_DEER_SPAWN_EGG = spawnEgg("icefield_deer", TAEntityTypes.ICEFIELD_DEER, 0xadb0c4, 0x5f4569);
    public static final RegistryObject<Item> BLUE_TAIL_WOLF_SPAWN_EGG = spawnEgg("blue_tail_wolf", TAEntityTypes.BLUE_TAIL_WOLF, 0xe1eff5, 0x6381f7);
    public static final RegistryObject<Item> MOON_FISH_SPAWN_EGG = spawnEgg("moon_fish", TAEntityTypes.MOON_FISH, 0xd1ccc5, 0x594a48);
    public static final RegistryObject<Item> AURORIAN_WINGED_FISH_SPAWN_EGG = spawnEgg("aurorian_winged_fish", TAEntityTypes.AURORIAN_WINGED_FISH, 0x4581d5, 0x1b4a8a);
    public static final RegistryObject<Item> AURORIAN_VILLAGER_SPAWN_EGG = spawnEgg("aurorian_villager", TAEntityTypes.AURORIAN_VILLAGER, 0x9e9e9e, 0x4f4f4f);
    public static final RegistryObject<Item> AURORIAN_RABBIT_SPAWN_EGG = spawnEgg("aurorian_rabbit", TAEntityTypes.AURORIAN_RABBIT, 0xc2e5e5, 0x43a2ec);
    public static final RegistryObject<Item> AURORIAN_SHEEP_SPAWN_EGG = spawnEgg("aurorian_sheep", TAEntityTypes.AURORIAN_SHEEP, 0x97b4f2, 0x7197ea);
    public static final RegistryObject<Item> AURORIAN_PIG_SPAWN_EGG = spawnEgg("aurorian_pig", TAEntityTypes.AURORIAN_PIG, 0xc6dfff, 0x5d6f93);
    public static final RegistryObject<Item> AURORIAN_COW_SPAWN_EGG = spawnEgg("aurorian_cow", TAEntityTypes.AURORIAN_COW, 0x578a91, 0x454c5b);
    public static final RegistryObject<Item> AURORIAN_PIXIE_SPAWN_EGG = spawnEgg("aurorian_pixie", TAEntityTypes.AURORIAN_PIXIE, 0x9cc6f1, 0x88b7e3);
    public static final RegistryObject<Item> AURORIAN_SLIME_SPAWN_EGG = spawnEgg("aurorian_slime", TAEntityTypes.AURORIAN_SLIME, 0x151028, 0x43a2ec);
    public static final RegistryObject<Item> DISTURBED_HOLLOW_SPAWN_EGG = spawnEgg("disturbed_hollow", TAEntityTypes.DISTURBED_HOLLOW, 0xade0f5, 0x272727);
    public static final RegistryObject<Item> UNDEAD_KNIGHT_SPAWN_EGG = spawnEgg("undead_knight", TAEntityTypes.UNDEAD_KNIGHT, 0x5c7394, 0x181b1e);
    public static final RegistryObject<Item> SPIRIT_SPAWN_EGG = spawnEgg("spirit", TAEntityTypes.SPIRIT, 0xb0b6bc, 0x303131);
    public static final RegistryObject<Item> MOON_ACOLYTE_SPAWN_EGG = spawnEgg("moon_acolyte", TAEntityTypes.MOON_ACOLYTE, 0x0270af, 0x191919);
    public static final RegistryObject<Item> SPIDERLING_SPAWN_EGG = spawnEgg("spiderling", TAEntityTypes.SPIDERLING, 0x1efefe, 0x0f1018);
    public static final RegistryObject<Item> SPIDERLING_CRYSTAL_SHELL_SPAWN_EGG = spawnEgg("spiderling_crystal_shell", TAEntityTypes.SPIDERLING_CRYSTAL_SHELL, 0xd1ffdf, 0x363675);
    public static final RegistryObject<Item> SPIDERLING_WALL_CLIMBER_SPAWN_EGG = spawnEgg("spiderling_wall_climber", TAEntityTypes.SPIDERLING_WALL_CLIMBER, 0xd1ffdf, 0x363675);
    public static final RegistryObject<Item> GIANT_CRYSTAL_SPIDER_SPAWN_EGG = spawnEgg("giant_crystal_spider", TAEntityTypes.GIANT_CRYSTAL_SPIDER, 0xd1ffdf, 0x363675);
    public static final RegistryObject<Item> RUNE_SPIDER_SPAWN_EGG = spawnEgg("rune_spider", TAEntityTypes.RUNE_SPIDER, 0xffc7f0, 0x8e69cf);
    public static final RegistryObject<Item> CRYSTALLINE_SPRITE_SPAWN_EGG = spawnEgg("crystalline_sprite", TAEntityTypes.CRYSTALLINE_SPRITE, 0xf5e2fc, 0xf0c9fd);
    public static final RegistryObject<Item> CAVE_DWELLER_SPAWN_EGG = spawnEgg("cave_dweller", TAEntityTypes.CAVE_DWELLER, 0x5e6b7d, 0xdc54f7);
    public static final RegistryObject<Item> ROCK_HAMMER_SPAWN_EGG = spawnEgg("rock_hammer", TAEntityTypes.ROCK_HAMMER, 0xe3e0d1, 0x45332e);
    public static final RegistryObject<Item> TONG_SCORPION_SPAWN_EGG = spawnEgg("tong_scorpion", TAEntityTypes.TONG_SCORPION, 0x4f4334, 0x211a18);
    public static final RegistryObject<Item> SNOW_TUNDRA_GIANT_CRAB_SPAWN_EGG = spawnEgg("snow_tundra_giant_crab", TAEntityTypes.SNOW_TUNDRA_GIANT_CRAB, 0xd8deed, 0x2347d9);
    public static final RegistryObject<Item> FLOWER_LEECH_SPAWN_EGG = spawnEgg("flower_leech", TAEntityTypes.FLOWER_LEECH, 0xcd92d1, 0x362d23);
    public static final RegistryObject<Item> FORGOTTEN_MAGIC_BOOK_SPAWN_EGG = spawnEgg("forgotten_magic_book", TAEntityTypes.FORGOTTEN_MAGIC_BOOK, 0xd9d2c5, 0x3a2f40);
    public static final RegistryObject<Item> HYPHA_WALKING_MUSHROOM_SPAWN_EGG = spawnEgg("hypha_walking_mushroom", TAEntityTypes.HYPHA_WALKING_MUSHROOM, 0xededdf, 0xc48a80);
    public static final RegistryObject<Item> MOONLIGHT_KNIGHT_SPAWN_EGG = spawnEgg("moonlight_knight", TAEntityTypes.MOONLIGHT_KNIGHT, 0xb8bdbe, 0x0955a8);
    public static final RegistryObject<Item> RUNESTONE_KEEPER_SPAWN_EGG = spawnEgg("runestone_keeper", TAEntityTypes.RUNESTONE_KEEPER, 0xccc0e7, 0x550098);
    public static final RegistryObject<Item> SPIDER_MOTHER_SPAWN_EGG = spawnEgg("spider_mother", TAEntityTypes.SPIDER_MOTHER, 0x595d70, 0x0f1018);
    public static final RegistryObject<Item> MOON_QUEEN_SPAWN_EGG = spawnEgg("moon_queen", TAEntityTypes.MOON_QUEEN, 0xff82d4, 0x313d4b);

    /**
     * Block Item
     * */
    public static final RegistryObject<Item> AURORIAN_LILY_PAD = ITEMS.register("aurorian_lily_pad",
            () -> new PlaceOnWaterBlockItem(TABlocks.AURORIAN_LILY_PAD.get(), new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_WATER_MUSHROOM = ITEMS.register("aurorian_water_mushroom",
            () -> new PlaceOnWaterBlockItem(TABlocks.AURORIAN_WATER_MUSHROOM.get(), new Item.Properties()));
    public static final RegistryObject<Item> SILENT_WOOD_CHEST = ITEMS.register("silent_wood_chest", SilentWoodChestItem::new);
    public static final RegistryObject<Item> MOON_TORCH = ITEMS.register("moon_torch", () -> new StandingAndWallBlockItem(
            TABlocks.MOON_TORCH.get(), TABlocks.MOON_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> SILENT_WOOD_TORCH = ITEMS.register("silent_wood_torch", () -> new StandingAndWallBlockItem(
            TABlocks.SILENT_WOOD_TORCH.get(), TABlocks.SILENT_WOOD_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> SILENT_WOOD_SIGN = ITEMS.register("silent_wood_sign", () -> new SignItem(
            new Item.Properties().stacksTo(16), TABlocks.SILENT_WOOD_SIGN.get(), TABlocks.SILENT_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> WEEPING_WILLOW_WOOD_SIGN = ITEMS.register("weeping_willow_wood_sign", () -> new SignItem(
            new Item.Properties().stacksTo(16), TABlocks.WEEPING_WILLOW_WOOD_SIGN.get(), TABlocks.WEEPING_WILLOW_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> CURTAIN_WOOD_SIGN = ITEMS.register("curtain_wood_sign", () -> new SignItem(
            new Item.Properties().stacksTo(16), TABlocks.CURTAIN_WOOD_SIGN.get(), TABlocks.CURTAIN_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> CURSED_FROST_WOOD_SIGN = ITEMS.register("cursed_frost_wood_sign", () -> new SignItem(
            new Item.Properties().stacksTo(16), TABlocks.CURSED_FROST_WOOD_SIGN.get(), TABlocks.CURSED_FROST_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> SILENT_WOOD_HANGING_SIGN = ITEMS.register("silent_wood_hanging_sign", () -> new HangingSignItem(
            TABlocks.SILENT_WOOD_HANGING_SIGN.get(), TABlocks.SILENT_WOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> WEEPING_WILLOW_WOOD_HANGING_SIGN = ITEMS.register("weeping_willow_wood_hanging_sign", () -> new HangingSignItem(
            TABlocks.WEEPING_WILLOW_WOOD_HANGING_SIGN.get(), TABlocks.WEEPING_WILLOW_WOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> CURTAIN_WOOD_HANGING_SIGN = ITEMS.register("curtain_wood_hanging_sign", () -> new HangingSignItem(
            TABlocks.CURTAIN_WOOD_HANGING_SIGN.get(), TABlocks.CURTAIN_WOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> CURSED_FROST_WOOD_HANGING_SIGN = ITEMS.register("cursed_frost_wood_hanging_sign", () -> new HangingSignItem(
            TABlocks.CURSED_FROST_WOOD_HANGING_SIGN.get(), TABlocks.CURSED_FROST_WOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

}