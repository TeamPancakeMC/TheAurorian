package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.*;
import cn.teampancake.theaurorian.common.items.armor.*;
import cn.teampancake.theaurorian.common.items.developer.*;
import cn.teampancake.theaurorian.common.items.shield.CeruleanShield;
import cn.teampancake.theaurorian.common.items.shield.CrystallineShield;
import cn.teampancake.theaurorian.common.items.shield.UmbraShield;
import cn.teampancake.theaurorian.common.items.tool.*;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static cn.teampancake.theaurorian.common.utils.TAItemRegUtils.*;
import static net.minecraft.world.item.ArmorItem.Type.*;

public class TAItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(TheAurorian.MOD_ID);

    /**
     * Materials
     */
    public static final DeferredHolder<Item, Item> RAW_CERULEAN = normal("raw_cerulean", TAItemProperties.get());
    public static final DeferredHolder<Item, Item> RAW_MOONSTONE = normal("raw_moonstone", TAItemProperties.get());
    public static final DeferredHolder<Item, Item> AURORIAN_COAL = normal("aurorian_coal", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> AURORIANITE_INGOT = normal("aurorianite_ingot", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL = normal("aurorian_steel", TAItemProperties.get().addItemTag(TAItemTags.IS_EPIC).hasTooltips());
    public static final DeferredHolder<Item, Item> CERULEAN_INGOT = normal("cerulean_ingot", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> CRYSTALLINE_INGOT = normal("crystalline_ingot", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> MOONSTONE_INGOT = normal("moonstone_ingot", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> UMBRA_INGOT = normal("umbra_ingot", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> LAVENDER = normal("lavender", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> PLANT_FIBER = normal("plant_fiber", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> AURORIANITE_SCRAP = normal("aurorianite_scrap", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> CRYSTALLINE_SCRAP = normal("crystalline_scrap", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> UMBRA_SCRAP = normal("umbra_scrap", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> SPECTRAL_SILK = normal("spectral_silk", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> DARK_AMULET = normal("dark_amulet", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> DUNGEON_KEEPER_AMULET = normal("dungeon_keeper_amulet", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());

    /**
     * Armor Item
     */
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_HELMET = ITEMS.register("aurorian_steel_helmet", () -> new AurorianSteelArmor(HELMET));
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_CHESTPLATE = ITEMS.register("aurorian_steel_chestplate", () -> new AurorianSteelArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_LEGGINGS = ITEMS.register("aurorian_steel_leggings", () -> new AurorianSteelArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_BOOTS = ITEMS.register("aurorian_steel_boots", () -> new AurorianSteelArmor(BOOTS));
    public static final DeferredHolder<Item, Item> CERULEAN_HELMET = ITEMS.register("cerulean_helmet", () -> new CeruleanArmor(HELMET));
    public static final DeferredHolder<Item, Item> CERULEAN_CHESTPLATE = ITEMS.register("cerulean_chestplate", () -> new CeruleanArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> CERULEAN_LEGGINGS = ITEMS.register("cerulean_leggings", () -> new CeruleanArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> CERULEAN_BOOTS = ITEMS.register("cerulean_boots", () -> new CeruleanArmor(BOOTS));
    public static final DeferredHolder<Item, Item> KNIGHT_HELMET = ITEMS.register("knight_helmet", () -> new KnightArmor(HELMET));
    public static final DeferredHolder<Item, Item> KNIGHT_CHESTPLATE = ITEMS.register("knight_chestplate", () -> new KnightArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> KNIGHT_LEGGINGS = ITEMS.register("knight_leggings", () -> new KnightArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> KNIGHT_BOOTS = ITEMS.register("knight_boots", () -> new KnightArmor(BOOTS));
    public static final DeferredHolder<Item, Item> SPECTRAL_HELMET = ITEMS.register("spectral_helmet", () -> new SpectralArmor(HELMET));
    public static final DeferredHolder<Item, Item> SPECTRAL_CHESTPLATE = ITEMS.register("spectral_chestplate", () -> new SpectralArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> SPECTRAL_LEGGINGS = ITEMS.register("spectral_leggings", () -> new SpectralArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> SPECTRAL_BOOTS = ITEMS.register("spectral_boots", () -> new SpectralArmor(BOOTS));
    public static final DeferredHolder<Item, Item> MYSTERIUM_WOOL_HELMET = ITEMS.register("mysterium_wool_helmet", () -> new MysteriumWoolArmor(HELMET));
    public static final DeferredHolder<Item, Item> MYSTERIUM_WOOL_CHESTPLATE = ITEMS.register("mysterium_wool_chestplate", () -> new MysteriumWoolArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> MYSTERIUM_WOOL_LEGGINGS = ITEMS.register("mysterium_wool_leggings", () -> new MysteriumWoolArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> MYSTERIUM_WOOL_BOOTS = ITEMS.register("mysterium_wool_boots", () -> new MysteriumWoolArmor(BOOTS));
    public static final DeferredHolder<Item, Item> CRYSTAL_RUNE_HELMET = ITEMS.register("crystal_rune_helmet", () -> new CrystalRuneArmor(HELMET));
    public static final DeferredHolder<Item, Item> CRYSTAL_RUNE_CHESTPLATE = ITEMS.register("crystal_rune_chestplate", () -> new CrystalRuneArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> CRYSTAL_RUNE_LEGGINGS = ITEMS.register("crystal_rune_leggings", () -> new CrystalRuneArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> CRYSTAL_RUNE_BOOTS = ITEMS.register("crystal_rune_boots", () -> new CrystalRuneArmor(BOOTS));
    public static final DeferredHolder<Item, Item> SPIKED_CHESTPLATE = ITEMS.register("spiked_chestplate", SpikedChestplate::new);
    public static final DeferredHolder<Item, Item> AURORIAN_SLIME_BOOTS = ITEMS.register("aurorian_slime_boots", AurorianSlimeBoots::new);

    /**
     * SwordItem
     */
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_SWORD = ITEMS.register("aurorian_steel_sword", AurorianSteelSword::new);
    public static final DeferredHolder<Item, Item> AURORIAN_STONE_SWORD = ITEMS.register("aurorian_stone_sword",
            () -> new SwordItem(TAToolTiers.AURORIAN_STONE, TAItemProperties.get().attributes(
                    SwordItem.createAttributes(TAToolTiers.AURORIAN_STONE, (3), (-2.4F))).addItemTag(ItemTags.SWORDS, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> SILENT_WOOD_SWORD = ITEMS.register("silent_wood_sword",
            () -> new SwordItem(TAToolTiers.SILENT_WOOD, TAItemProperties.get().attributes(
                    SwordItem.createAttributes(TAToolTiers.SILENT_WOOD, (4), (-1.6F))).addItemTag(ItemTags.SWORDS, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> UMBRA_SWORD = ITEMS.register("umbra_sword", UmbraSword::new);
    public static final DeferredHolder<Item, Item> AURORIANITE_SWORD = ITEMS.register("aurorianite_sword", AurorianiteSword::new);
    public static final DeferredHolder<Item, Item> CRYSTALLINE_SWORD = ITEMS.register("crystalline_sword", CrystallineSword::new);
    public static final DeferredHolder<Item, Item> MOONSTONE_SWORD = ITEMS.register("moonstone_sword",
            () -> new SwordItem(TAToolTiers.SILENT_WOOD, TAItemProperties.get().attributes(
                    SwordItem.createAttributes(TAToolTiers.SILENT_WOOD, (4), (-1.6F))).addItemTag(ItemTags.SWORDS, TAItemTags.IS_EPIC)));

    /**
     * ShovelItem
     */
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_SHOVEL = ITEMS.register("aurorian_steel_shovel",
            () -> new ShovelItem(TAToolTiers.AURORIAN_STEEL, TAItemProperties.get().attributes(
                    ShovelItem.createAttributes(TAToolTiers.AURORIAN_STEEL, (1.5F), (-3.0F))).addItemTag(ItemTags.SHOVELS, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> AURORIAN_STONE_SHOVEL = ITEMS.register("aurorian_stone_shovel",
            () -> new ShovelItem(TAToolTiers.AURORIAN_STONE, TAItemProperties.get().attributes(
                    ShovelItem.createAttributes(TAToolTiers.AURORIAN_STONE, (1.5F), (-3.0F))).addItemTag(ItemTags.SHOVELS, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> SILENT_WOOD_SHOVEL = ITEMS.register("silent_wood_shovel",
            () -> new ShovelItem(TAToolTiers.SILENT_WOOD, TAItemProperties.get().attributes(
                    ShovelItem.createAttributes(TAToolTiers.SILENT_WOOD, (1.5F), (-3.0F))).addItemTag(ItemTags.SHOVELS, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> MOONSTONE_SHOVEL = ITEMS.register("moonstone_shovel",
            () -> new ShovelItem(TAToolTiers.SILENT_WOOD, TAItemProperties.get().attributes(
                    ShovelItem.createAttributes(TAToolTiers.SILENT_WOOD, (1.5F), (-3.0F))).addItemTag(ItemTags.SHOVELS, TAItemTags.IS_EPIC)));

    /**
     * AxeItem
     */
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_AXE = ITEMS.register("aurorian_steel_axe",
            () -> new AxeItem(TAToolTiers.AURORIAN_STEEL, TAItemProperties.get().attributes(
                    AxeItem.createAttributes(TAToolTiers.AURORIAN_STEEL, (6.0F), (-3.0F))).addItemTag(ItemTags.AXES, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> AURORIANITE_AXE = ITEMS.register("aurorianite_axe", AurorianiteAxe::new);
    public static final DeferredHolder<Item, Item> AURORIAN_STONE_AXE = ITEMS.register("aurorian_stone_axe", AurorianStoneAxe::new);
    public static final DeferredHolder<Item, Item> SILENT_WOOD_AXE = ITEMS.register("silent_wood_axe",
            () -> new AxeItem(TAToolTiers.SILENT_WOOD, TAItemProperties.get().attributes(
                    AxeItem.createAttributes(TAToolTiers.SILENT_WOOD, (6.0F), (-3.2F))).addItemTag(ItemTags.AXES, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> MOONSTONE_AXE = ITEMS.register("moonstone_axe",
            () -> new AxeItem(TAToolTiers.SILENT_WOOD, TAItemProperties.get().addItemTag(ItemTags.AXES, TAItemTags.IS_EPIC)));

    /**
     * PickaxeItem
     */
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_PICKAXE = ITEMS.register("aurorian_steel_pickaxe",
            () -> new PickaxeItem(TAToolTiers.AURORIAN_STEEL, TAItemProperties.get().attributes(
                    PickaxeItem.createAttributes(TAToolTiers.AURORIAN_STEEL, (1), (-2.8F))).addItemTag(ItemTags.PICKAXES, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> AURORIANITE_PICKAXE = ITEMS.register("aurorianite_pickaxe",
            () -> new PickaxeItem(TAToolTiers.AURORIANITE, TAItemProperties.get().rarity(Rarity.EPIC).attributes(
                    PickaxeItem.createAttributes(TAToolTiers.AURORIAN_STEEL, (1), (-3.0F))).addItemTag(ItemTags.PICKAXES, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> UMBRA_PICKAXE = ITEMS.register("umbra_pickaxe", UmbraPickaxe::new);
    public static final DeferredHolder<Item, Item> CRYSTALLINE_PICKAXE = ITEMS.register("crystalline_pickaxe", CrystallinePickaxe::new);
    public static final DeferredHolder<Item, Item> AURORIAN_STONE_PICKAXE = ITEMS.register("aurorian_stone_pickaxe", AurorianStonePickaxe::new);
    public static final DeferredHolder<Item, Item> SILENT_WOOD_PICKAXE = ITEMS.register("silent_wood_pickaxe", SilentWoodPickaxe::new);
    public static final DeferredHolder<Item, Item> MOONSTONE_PICKAXE = ITEMS.register("moonstone_pickaxe",
            () -> new PickaxeItem(TAToolTiers.SILENT_WOOD, TAItemProperties.get().attributes(
                    PickaxeItem.createAttributes(TAToolTiers.SILENT_WOOD, (1), (-2.8F))).addItemTag(ItemTags.PICKAXES, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> QUEENS_CHIPPER = ITEMS.register("queens_chipper", QueensChipper::new);

    /**
     * HoeItem
     */
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_HOE = ITEMS.register("aurorian_steel_hoe",
            () -> new HoeItem(TAToolTiers.AURORIAN_STEEL, TAItemProperties.get().attributes(
                    HoeItem.createAttributes(TAToolTiers.AURORIAN_STEEL, (-2), (-1.0F))).addItemTag(ItemTags.HOES, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> AURORIAN_STONE_HOE = ITEMS.register("aurorian_stone_hoe",
            () -> new HoeItem(TAToolTiers.AURORIAN_STONE, TAItemProperties.get().attributes(
                    HoeItem.createAttributes(TAToolTiers.AURORIAN_STONE, (-2), (-1.0F))).addItemTag(ItemTags.HOES, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> SILENT_WOOD_HOE = ITEMS.register("silent_wood_hoe",
            () -> new HoeItem(TAToolTiers.SILENT_WOOD, TAItemProperties.get().attributes(
                    HoeItem.createAttributes(TAToolTiers.SILENT_WOOD, (-2), (-1.0F))).addItemTag(ItemTags.HOES, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> MOONSTONE_HOE = ITEMS.register("moonstone_hoe",
            () -> new HoeItem(TAToolTiers.SILENT_WOOD, TAItemProperties.get().attributes(
                    HoeItem.createAttributes(TAToolTiers.SILENT_WOOD, (-2), (-1.0F))).addItemTag(ItemTags.HOES, TAItemTags.IS_EPIC)));

    /**
     * ShearsItem
     */
    public static final DeferredHolder<Item, Item> SILENT_WOOD_SICKLE = ITEMS.register("silent_wood_sickle", () -> new ShearsItem(TAItemProperties.get()
            .durability(50).addItemTag(ItemTags.DURABILITY_ENCHANTABLE, ItemTags.MINING_ENCHANTABLE, TAItemTags.IS_EPIC).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> AURORIAN_STONE_SICKLE = ITEMS.register("aurorian_stone_sickle", () -> new ShearsItem(TAItemProperties.get()
            .durability(150).addItemTag(ItemTags.DURABILITY_ENCHANTABLE, ItemTags.MINING_ENCHANTABLE, TAItemTags.IS_EPIC).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> MOONSTONE_SICKLE = ITEMS.register("moonstone_sickle", MoonstoneSickle::new);
    
    /**
     * ShieldItem
     */
    public static final DeferredHolder<Item, Item> MOON_SHIELD = ITEMS.register("moon_shield",
            () -> new ShieldItem(TAItemProperties.get().rarity(Rarity.EPIC).durability(512)
                    .addItemTag(Tags.Items.TOOLS_SHIELD, ItemTags.DURABILITY_ENCHANTABLE, TAItemTags.IS_EPIC).hasTooltips()));
    public static final DeferredHolder<Item, Item> UMBRA_SHIELD = ITEMS.register("umbra_shield", UmbraShield::new);
    public static final DeferredHolder<Item, Item> CERULEAN_SHIELD = ITEMS.register("cerulean_shield", CeruleanShield::new);
    public static final DeferredHolder<Item, Item> CRYSTALLINE_SHIELD = ITEMS.register("crystalline_shield", CrystallineShield::new);
    public static final DeferredHolder<Item, Item> MOONSTONE_SHIELD = ITEMS.register("moonstone_shield", () -> new ShieldItem(TAItemProperties.get()
            .addItemTag(Tags.Items.TOOLS_SHIELD, ItemTags.DURABILITY_ENCHANTABLE, TAItemTags.IS_EPIC).durability(512)));

    /**
     * BowItem
     */
    public static final DeferredHolder<Item, Item> SILENT_WOOD_BOW = ITEMS.register("silent_wood_bow", () -> new BowItem(TAItemProperties.get()
            .durability(384).addItemTag(ItemTags.DURABILITY_ENCHANTABLE, ItemTags.BOW_ENCHANTABLE, Tags.Items.TOOLS_BOW, TAItemTags.IS_EPIC)));
    public static final DeferredHolder<Item, Item> KEEPERS_BOW = ITEMS.register("keepers_bow", () -> new BowItem(TAItemProperties.get()
            .durability(512).rarity(Rarity.RARE).addItemTag(ItemTags.DURABILITY_ENCHANTABLE, ItemTags.BOW_ENCHANTABLE, Tags.Items.TOOLS_BOW, TAItemTags.IS_EPIC).hasTooltips()));

    /**
     * Throwable Weapons
     */
    public static final DeferredHolder<Item, Item> MOON_SHURIKEN = ITEMS.register("moon_shuriken", () -> new SimpleThrowProjectProjectile(
            TAItemProperties.get().addItemTag(TAItemTags.THROWABLE_WEAPONS), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.THROWN_SHURIKEN::get, 1.5F));
    public static final DeferredHolder<Item, Item> UNSTABLE_CRYSTAL = ITEMS.register("unstable_crystal", () -> new SimpleThrowProjectProjectile(
            TAItemProperties.get().addItemTag(TAItemTags.THROWABLE_WEAPONS).stacksTo(16), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.UNSTABLE_CRYSTAL::get, 1.5F));
    public static final DeferredHolder<Item, Item> AURORIAN_SLATE_BRICK = ITEMS.register("aurorian_slate_brick", AurorianSlateBrick::new);

    /**
     * Tea
     */
    public static final DeferredHolder<Item, Item> TEA_CUP = normal("tea_cup", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).isSimpleModelItem());
    public static final DeferredHolder<Item, Item> LAVENDER_TEA = ITEMS.register("lavender_tea", () -> new TeaFood(TAItemProperties.get()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300), 1.0F).build())));
    public static final DeferredHolder<Item, Item> SILK_BERRY_TEA = ITEMS.register("silk_berry_tea", () -> new TeaFood(TAItemProperties.get()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100), 1.0F).build())));
    public static final DeferredHolder<Item, Item> LAVENDER_SEEDY_TEA = ITEMS.register("lavender_seedy_tea", () -> new TeaFood(TAItemProperties.get()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 1.0F).build())));
    public static final DeferredHolder<Item, Item> PETUNIA_TEA = ITEMS.register("petunia_tea", () -> new TeaFood(TAItemProperties.get()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300), 1.0F).build())));
    public static final DeferredHolder<Item, Item> BEPSI = ITEMS.register("bepsi", () -> new TeaFood(TAItemProperties.get()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600), 1.0F).build())));

    /**
     * Food
     */
    public static final DeferredHolder<Item, Item> TALL_WICK_GRASS = ITEMS.register("tall_wick_grass", () -> new DoubleHighBlockItem(TABlocks.TALL_WICK_GRASS.get(), TAItemProperties.get().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> AURORIAN_BEEF = ITEMS.register("aurorian_beef", () -> new Item(TAItemProperties.get().food(Foods.BEEF).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> AURORIAN_PORK = ITEMS.register("aurorian_pork", () -> new Item(TAItemProperties.get().food(Foods.PORKCHOP).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> AURORIAN_MUTTON = ITEMS.register("aurorian_mutton", () -> new Item(TAItemProperties.get().food(Foods.MUTTON).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> AURORIAN_RABBIT = ITEMS.register("aurorian_rabbit", () -> new Item(TAItemProperties.get().food(Foods.RABBIT).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> COOKED_AURORIAN_BEEF = ITEMS.register("cooked_aurorian_beef", () -> new Item(TAItemProperties.get().food(Foods.COOKED_BEEF).addItemTag(TAItemTags.COOKED_MEAT).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> COOKED_AURORIAN_PORK = ITEMS.register("cooked_aurorian_pork", () -> new Item(TAItemProperties.get().food(Foods.COOKED_PORKCHOP).addItemTag(TAItemTags.COOKED_MEAT).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> COOKED_AURORIAN_MUTTON = ITEMS.register("cooked_aurorian_mutton", () -> new Item(TAItemProperties.get().food(Foods.COOKED_MUTTON).addItemTag(TAItemTags.COOKED_MEAT).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> COOKED_AURORIAN_RABBIT = ITEMS.register("cooked_aurorian_rabbit", () -> new Item(TAItemProperties.get().food(Foods.COOKED_RABBIT).addItemTag(TAItemTags.COOKED_MEAT).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> WEEPING_WILLOW_SAP = ITEMS.register("weeping_willow_sap", WeepingWillowSap::new);
    public static final DeferredHolder<Item, Item> SILK_BERRY_JAM = food("silk_berry_jam", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE), 2, 0.5F);
    public static final DeferredHolder<Item, Item> SILK_BERRY_JAM_SANDWICH = food("silk_berry_jam_sandwich", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE), 6, 0.9F);
    public static final DeferredHolder<Item, Item> AURORIAN_SLIMEBALL = food("aurorian_slimeball", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE), 1, 0.2F);
    public static final DeferredHolder<Item, Item> SILK_SHROOM_STEW = food("silk_shroom_stew", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE), 6, 1F);
    public static final DeferredHolder<Item, Item> LAVENDER_BREAD = food("lavender_bread", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE), 4, 0.4F);
    public static final DeferredHolder<Item, Item> SOULLESS_FLESH = food("soulless_flesh", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE), 2, 0.1F);
    public static final DeferredHolder<Item, Item> MOON_FISH = food("moon_fish", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE), 2, 0.4F);
    public static final DeferredHolder<Item, Item> AURORIAN_WINGED_FISH = food("aurorian_winged_fish", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE), 2,0.4F);
    public static final DeferredHolder<Item, Item> COOKED_MOON_FISH = food("cooked_moon_fish",TAItemProperties.get().addItemTag(TAItemTags.IS_RARE, TAItemTags.COOKED_MEAT), 5,6);
    public static final DeferredHolder<Item, Item> COOKED_AURORIAN_WINGED_FISH = food("cooked_aurorian_winged_fish", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE, TAItemTags.COOKED_MEAT), 5,6);
    public static final DeferredHolder<Item, Item> LAVENDER_SEEDS = alias("lavender_seeds", TABlocks.LAVENDER_CROP, TAItemProperties.get().isSimpleModelItem());
    public static final DeferredHolder<Item, Item> SILK_BERRY = alias("silk_berry", TABlocks.SILK_BERRY_CROP, TAItemProperties.get()
            .food(new FoodProperties.Builder().nutrition(1).saturationModifier((0.1F)).build()).addItemTag(TAItemTags.IS_RARE).isSimpleModelItem());
    public static final DeferredHolder<Item, Item> BLUEBERRY = alias("blueberry", TABlocks.BLUEBERRY_BUSH, TAItemProperties.get().food(Foods.SWEET_BERRIES).isSimpleModelItem());
    public static final DeferredHolder<Item, Item> CANDY = food("candy", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE), 4, 0.2F);
    public static final DeferredHolder<Item, Item> CANDY_CANE = ITEMS.register("candy_cane", () -> new Item(TAItemProperties.get().food(new FoodProperties.Builder().nutrition(4).saturationModifier((0.4F))
            .effect(() -> new MobEffectInstance(MobEffects.LUCK, 300), 1.0F).build()).addItemTag(TAItemTags.IS_RARE).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> GINGERBREAD_MAN = ITEMS.register("gingerbread_man", () -> new Item(TAItemProperties.get().food(new FoodProperties.Builder().nutrition(6).saturationModifier((0.4F))
            .effect(() -> new MobEffectInstance(TAMobEffects.WARM, 1200), 1.0F).build()).addItemTag(TAItemTags.IS_RARE).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> AURORIAN_SPECIALTY_DRINK = ITEMS.register("aurorian_specialty_drink", () -> new Item(TAItemProperties.get()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600), 1.0F).build()).addItemTag(TAItemTags.IS_RARE).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> MOONLIT_BLUEBERRY_SPECIALTY_DRINK = ITEMS.register("moonlit_blueberry_specialty_drink", () -> new Item(TAItemProperties.get()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1), 1.0F).build()).addItemTag(TAItemTags.IS_RARE).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> AURORIAN_BACON = ITEMS.register("aurorian_bacon", () -> new Item(TAItemProperties.get().food(new FoodProperties.Builder().nutrition(2).saturationModifier((0.8F))
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60), 1.0F).build()).addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> STRANGE_MEAT = ITEMS.register("strange_meat", StrangeMeat::new);
    public static final DeferredHolder<Item, Item> LAVENDER_SALAD = ITEMS.register("lavender_salad", () -> new Item(TAItemProperties.get().food(new FoodProperties.Builder().nutrition(4).saturationModifier((5.0F))
            .effect(() -> new MobEffectInstance(TAMobEffects.NATURE, 600), 1.0F).build()).addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> FAKE_ALGAL_PIT_FISH = ITEMS.register("fake_algal_pit_fish", () -> new Item(TAItemProperties.get()
            .food(new FoodProperties.Builder().nutrition(4).saturationModifier((5.0F)).build()).addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> SASHIMI = ITEMS.register("sashimi", () -> new Item(TAItemProperties.get().food(new FoodProperties.Builder().nutrition(5).saturationModifier((2.0F))
            .effect(() -> new MobEffectInstance(MobEffects.LUCK, 400), 1.0F).build()).addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> SILENT_WOOD_FRUIT = ITEMS.register("silent_wood_fruit", () -> new Item(TAItemProperties.get()
            .food(new FoodProperties.Builder().nutrition(3).saturationModifier((2.5F)).build()).addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> GOLDEN_SILENT_WOOD_FRUIT = ITEMS.register("golden_silent_wood_fruit", () -> new Item(TAItemProperties.get()
            .food(new FoodProperties.Builder().nutrition(4).saturationModifier((5.0F)).build()).addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> KEBAB_WITH_MUSHROOM = ITEMS.register("kebab_with_mushroom",
            () -> new Item(TAItemProperties.get().food(new FoodProperties.Builder().nutrition(12).saturationModifier((15.0F))
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200), 1.0F).build())
                    .addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> AURORIAN_WINTER_ROOT = ITEMS.register("aurorian_winter_root", () -> new Item(TAItemProperties.get()
            .food(new FoodProperties.Builder().nutrition(1).saturationModifier((0.8F)).build()).addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> ROASTED_AURORIAN_WINTER_ROOT = ITEMS.register("roasted_aurorian_winter_root",
            () -> new Item(TAItemProperties.get().food(new FoodProperties.Builder().nutrition(4).saturationModifier((6.0F))
                    .effect(() -> new MobEffectInstance(TAMobEffects.WARM, 400), 1.0F).build())
                    .addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> DARK_STONE_SHRIMP = ITEMS.register("dark_stone_shrimp", () -> new Item(TAItemProperties.get()
            .food(new FoodProperties.Builder().nutrition(3).saturationModifier((0.8F)).build()).addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem()));

    /**
     * Key
     */
    public static final DeferredHolder<Item, Item> MOON_TEMPLE_CELL_KEY_FRAGMENT = normal("moon_temple_cell_key_fragment", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> RUNE_STONE_KEY = ITEMS.register("rune_stone_key", () -> new Item(TAItemProperties.get().rarity(Rarity.UNCOMMON).addItemTag(TAItemTags.DUNGEON_KEY).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> DARK_STONE_KEY = ITEMS.register("dark_stone_key", () -> new Item(TAItemProperties.get().rarity(Rarity.UNCOMMON).addItemTag(TAItemTags.DUNGEON_KEY).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> MOON_TEMPLE_KEY = ITEMS.register("moon_temple_key", () -> new Item(TAItemProperties.get().rarity(Rarity.UNCOMMON).addItemTag(TAItemTags.DUNGEON_KEY).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> RUNE_STONE_LOOT_KEY = ITEMS.register("rune_stone_loot_key", () -> new Item(TAItemProperties.get().rarity(Rarity.UNCOMMON).addItemTag(TAItemTags.DUNGEON_KEY).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> MOON_TEMPLE_CELL_KEY = ITEMS.register("moon_temple_cell_key", () -> new Item(TAItemProperties.get().rarity(Rarity.UNCOMMON).addItemTag(TAItemTags.DUNGEON_KEY).hasTooltips().isSimpleModelItem()));

    /**
     * Arrow
     */
    public static final DeferredHolder<Item, Item> CERULEAN_ARROW = ITEMS.register("cerulean_arrow", () -> new CeruleanArrow(TAItemProperties.get().addItemTag(ItemTags.ARROWS, TAItemTags.IS_RARE).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> CRYSTAL_ARROW = ITEMS.register("crystal_arrow", () -> new CrystalArrow(TAItemProperties.get().addItemTag(ItemTags.ARROWS, TAItemTags.IS_RARE).isSimpleModelItem()));

    /**
     * Tool
     */
    public static final DeferredHolder<Item, Item> ABSORPTION_ORB = ITEMS.register("absorption_orb", AbsorptionOrbItem::new);
    public static final DeferredHolder<Item, Item> SILENT_WOOD_STICK = ITEMS.register("silent_wood_stick", () -> new Item(TAItemProperties.get().addItemTag(TAItemTags.IS_RARE, Tags.Items.RODS_WOODEN).hasTooltips().isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> STICKY_SPIKER = ITEMS.register("sticky_spiker", () -> new SimpleThrowProjectProjectile(TAItemProperties.get()
            .rarity(Rarity.EPIC).addItemTag(TAItemTags.IS_RARE).isSimpleModelItem(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.STICKY_SPIKER::get, 1.5F, 1.0F));

    /**
     * Loot
     */
    public static final DeferredHolder<Item, Item> TROPHY_KEEPER = normal("trophy_keeper", TAItemProperties.get().addItemTag(TAItemTags.IS_LEGENDARY).hasTooltips());
    public static final DeferredHolder<Item, Item> TROPHY_SPIDER_MOTHER = normal("trophy_spider_mother", TAItemProperties.get().addItemTag(TAItemTags.IS_LEGENDARY).hasTooltips());
    public static final DeferredHolder<Item, Item> TROPHY_MOON_QUEEN = normal("trophy_moon_queen", TAItemProperties.get().addItemTag(TAItemTags.IS_LEGENDARY).hasTooltips());
    public static final DeferredHolder<Item, Item> RUNE_KNOWLEDGE_FRAGMENT = normal("rune_knowledge_fragment", TAItemProperties.get().addItemTag(TAItemTags.IS_LEGENDARY));

    /**
     * Runestone
     */
    public static final DeferredHolder<Item, Item> RUNESTONE_ICE = normal("runestone_ice", TAItemProperties.get().addItemTag(TAItemTags.RUNESTONE));
    public static final DeferredHolder<Item, Item> RUNESTONE_LIFE = normal("runestone_life", TAItemProperties.get().addItemTag(TAItemTags.RUNESTONE));
    public static final DeferredHolder<Item, Item> RUNESTONE_LIGHT = normal("runestone_light", TAItemProperties.get().addItemTag(TAItemTags.RUNESTONE));
    public static final DeferredHolder<Item, Item> RUNESTONE_WATER = normal("runestone_water", TAItemProperties.get().addItemTag(TAItemTags.RUNESTONE));
    public static final DeferredHolder<Item, Item> RUNESTONE_BLAZE = normal("runestone_blaze", TAItemProperties.get().addItemTag(TAItemTags.RUNESTONE));
    public static final DeferredHolder<Item, Item> RUNESTONE_THUNDER = normal("runestone_thunder", TAItemProperties.get().addItemTag(TAItemTags.RUNESTONE));
    public static final DeferredHolder<Item, Item> RUNESTONE_DARKNESS = normal("runestone_darkness", TAItemProperties.get().addItemTag(TAItemTags.RUNESTONE));

    /**
     * Misc
     */
    public static final DeferredHolder<Item, Item> CRYSTAL = normal("crystal", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).hasTooltips());
    public static final DeferredHolder<Item, Item> BROKEN_OX_HORN = normal("broken_ox_horn", TAItemProperties.get().hasTooltips());
    public static final DeferredHolder<Item, Item> LUCKY_RABBIT_EAR = normal("lucky_rabbit_ear", TAItemProperties.get().hasTooltips());
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_NUGGET = normal("aurorian_steel_nugget", TAItemProperties.get());
    public static final DeferredHolder<Item, Item> CERULEAN_NUGGET = normal("cerulean_nugget", TAItemProperties.get());
    public static final DeferredHolder<Item, Item> AURORIAN_COAL_NUGGET = normal("aurorian_coal_nugget", TAItemProperties.get());
    public static final DeferredHolder<Item, Item> MOONSTONE_NUGGET = normal("moonstone_nugget", TAItemProperties.get());
    public static final DeferredHolder<Item, Item> AURORIAN_CHAIN = normal("aurorian_chain", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE));
    public static final DeferredHolder<Item, Item> AURORIAN_BERRY = normal("aurorian_berry", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE));
    public static final DeferredHolder<Item, Item> AURORIAN_CRYSTAL = ITEMS.register("aurorian_crystal", AurorianCrystal::new);
    public static final DeferredHolder<Item, Item> EQUINOX_MUSHROOM = normal("equinox_mushroom", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE));
    public static final DeferredHolder<Item, Item> DREAM_DYEING_CRYSTAL_FRAGMENT = normal("dream_dyeing_crystal_fragment", TAItemProperties.get().addItemTag(TAItemTags.HAS_CUSTOM_TOOLTIPS).hasTooltips());
    public static final DeferredHolder<Item, Item> WORLD_SCROLL_FRAGMENT = normal("world_scroll_fragment", TAItemProperties.get().addItemTag(TAItemTags.IS_RARE));
    public static final DeferredHolder<Item, Item> WORLD_SCROLL = ITEMS.register("world_scroll", WorldScroll::new);
    public static final DeferredHolder<Item, Item> DUNGEON_LOCATOR = ITEMS.register("dungeon_locator", DungeonLocatorItem::new);
    public static final DeferredHolder<Item, Item> WEBBING = ITEMS.register("webbing", () -> new SimpleThrowProjectProjectile(
            TAItemProperties.get(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.WEBBING::get, 0.5F));
    public static final DeferredHolder<Item, Item> LIVING_DIVINING_ROD = ITEMS.register("living_divining_rod", LivingDiviningRod::new);
    public static final DeferredHolder<Item, Item> LOCK_PICKS = ITEMS.register("lock_picks", () -> new Item(TAItemProperties.get().durability(10).addItemTag(TAItemTags.IS_EPIC).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> MOON_WATER_BUCKET = ITEMS.register("moon_water_bucket", () -> new BucketItem(
            TAFluids.MOON_WATER_STILL.get(), TAItemProperties.get().stacksTo(1).addItemTag(TAItemTags.IS_RARE).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> AURORIAN_WINGED_FISH_BUCKET = ITEMS.register("aurorian_winged_fish_bucket", () -> new MobBucketItem(TAEntityTypes.AURORIAN_WINGED_FISH.get(),
            Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, TAItemProperties.get().stacksTo(1).addItemTag(TAItemTags.IS_RARE).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> MOON_FISH_BUCKET = ITEMS.register("moon_fish_bucket", () -> new MobBucketItem(TAEntityTypes.MOON_FISH.get(),
            Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, TAItemProperties.get().stacksTo(1).addItemTag(TAItemTags.IS_RARE).isSimpleModelItem()));
    public static final DeferredHolder<Item, Item> DEVELOPER_GIFT = ITEMS.register("developer_gift", DeveloperGift::new);

    /**
     * Developer Item
     */
    public static final DeferredHolder<Item, Item> SLEEPING_BLACK_TEA = normal("sleeping_black_tea", TAItemProperties.get()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(TAMobEffects.WARM, 100), 1.0F).build())
            .addItemTag(TAItemTags.IS_MYTHICAL).hasTooltips().isDeveloperItem());
    public static final DeferredHolder<Item, Item> WHITE_CHOCOLATE = normal("white_chocolate", TAItemProperties.get()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(TAMobEffects.PARALYSIS, 2400), 1.0F).build())
            .addItemTag(TAItemTags.HAS_CUSTOM_TOOLTIPS).hasTooltips().isDeveloperItem());
    public static final DeferredHolder<Item, Item> RED_BOOK = ITEMS.register("red_book", RedBook::new);
    public static final DeferredHolder<Item, Item> RED_BOOK_RING = normal("red_book_ring",
            TAItemProperties.get().addItemTag(TAItemTags.HAS_CUSTOM_TOOLTIPS).hasTooltips().isDeveloperItem());
    public static final DeferredHolder<Item, Item> CAT_BELL = ITEMS.register("cat_bell", CatBell::new);
    public static final DeferredHolder<Item, Item> TSLAT_SWORD = ITEMS.register("tslat_sword", () -> new SwordItem(TAToolTiers.TSLAT, TAItemProperties.get()
            .component(TADataComponents.KILL_COUNT.get(), 0).attributes(SwordItem.createAttributes(TAToolTiers.TSLAT, 3, 1.9F))
            .addItemTag(ItemTags.SWORDS, TAItemTags.HAS_CUSTOM_TOOLTIPS).hasTooltips().isDeveloperItem()));

    /**
     * Spawn Egg
     */
    public static final DeferredHolder<Item, Item> BREAD_BEAST_SPAWN_EGG = spawnEgg("bread_beast", TAEntityTypes.BREAD_BEAST, 0xc4b4a1, 0x734b41);
    public static final DeferredHolder<Item, Item> ICEFIELD_DEER_SPAWN_EGG = spawnEgg("icefield_deer", TAEntityTypes.ICEFIELD_DEER, 0xadb0c4, 0x5f4569);
    public static final DeferredHolder<Item, Item> BLUE_TAIL_WOLF_SPAWN_EGG = spawnEgg("blue_tail_wolf", TAEntityTypes.BLUE_TAIL_WOLF, 0xe1eff5, 0x6381f7);
    public static final DeferredHolder<Item, Item> MOON_FISH_SPAWN_EGG = spawnEgg("moon_fish", TAEntityTypes.MOON_FISH, 0xd1ccc5, 0x594a48);
    public static final DeferredHolder<Item, Item> AURORIAN_WINGED_FISH_SPAWN_EGG = spawnEgg("aurorian_winged_fish", TAEntityTypes.AURORIAN_WINGED_FISH, 0x4581d5, 0x1b4a8a);
    public static final DeferredHolder<Item, Item> AURORIAN_VILLAGER_SPAWN_EGG = spawnEgg("aurorian_villager", TAEntityTypes.AURORIAN_VILLAGER, 0x9e9e9e, 0x4f4f4f);
    public static final DeferredHolder<Item, Item> AURORIAN_RABBIT_SPAWN_EGG = spawnEgg("aurorian_rabbit", TAEntityTypes.AURORIAN_RABBIT, 0xc2e5e5, 0x43a2ec);
    public static final DeferredHolder<Item, Item> AURORIAN_SHEEP_SPAWN_EGG = spawnEgg("aurorian_sheep", TAEntityTypes.AURORIAN_SHEEP, 0x97b4f2, 0x7197ea);
    public static final DeferredHolder<Item, Item> AURORIAN_PIG_SPAWN_EGG = spawnEgg("aurorian_pig", TAEntityTypes.AURORIAN_PIG, 0xc6dfff, 0x5d6f93);
    public static final DeferredHolder<Item, Item> AURORIAN_COW_SPAWN_EGG = spawnEgg("aurorian_cow", TAEntityTypes.AURORIAN_COW, 0x578a91, 0x454c5b);
    public static final DeferredHolder<Item, Item> AURORIAN_PIXIE_SPAWN_EGG = spawnEgg("aurorian_pixie", TAEntityTypes.AURORIAN_PIXIE, 0x9cc6f1, 0x88b7e3);
    public static final DeferredHolder<Item, Item> AURORIAN_SLIME_SPAWN_EGG = spawnEgg("aurorian_slime", TAEntityTypes.AURORIAN_SLIME, 0x151028, 0x43a2ec);
    public static final DeferredHolder<Item, Item> DISTURBED_HOLLOW_SPAWN_EGG = spawnEgg("disturbed_hollow", TAEntityTypes.DISTURBED_HOLLOW, 0xade0f5, 0x272727);
    public static final DeferredHolder<Item, Item> UNDEAD_KNIGHT_SPAWN_EGG = spawnEgg("undead_knight", TAEntityTypes.UNDEAD_KNIGHT, 0x5c7394, 0x181b1e);
    public static final DeferredHolder<Item, Item> SPIRIT_SPAWN_EGG = spawnEgg("spirit", TAEntityTypes.SPIRIT, 0xb0b6bc, 0x303131);
    public static final DeferredHolder<Item, Item> MOON_ACOLYTE_SPAWN_EGG = spawnEgg("moon_acolyte", TAEntityTypes.MOON_ACOLYTE, 0x0270af, 0x191919);
    public static final DeferredHolder<Item, Item> SPIDERLING_SPAWN_EGG = spawnEgg("spiderling", TAEntityTypes.SPIDERLING, 0x1efefe, 0x0f1018);
    public static final DeferredHolder<Item, Item> SPIDERLING_CRYSTAL_SHELL_SPAWN_EGG = spawnEgg("spiderling_crystal_shell", TAEntityTypes.SPIDERLING_CRYSTAL_SHELL, 0xd1ffdf, 0x363675);
    public static final DeferredHolder<Item, Item> SPIDERLING_WALL_CLIMBER_SPAWN_EGG = spawnEgg("spiderling_wall_climber", TAEntityTypes.SPIDERLING_WALL_CLIMBER, 0xd1ffdf, 0x363675);
    public static final DeferredHolder<Item, Item> GIANT_CRYSTAL_SPIDER_SPAWN_EGG = spawnEgg("giant_crystal_spider", TAEntityTypes.GIANT_CRYSTAL_SPIDER, 0xd1ffdf, 0x363675);
    public static final DeferredHolder<Item, Item> RUNE_SPIDER_SPAWN_EGG = spawnEgg("rune_spider", TAEntityTypes.RUNE_SPIDER, 0xffc7f0, 0x8e69cf);
    public static final DeferredHolder<Item, Item> CRYSTALLINE_SPRITE_SPAWN_EGG = spawnEgg("crystalline_sprite", TAEntityTypes.CRYSTALLINE_SPRITE, 0xf5e2fc, 0xf0c9fd);
    public static final DeferredHolder<Item, Item> CAVE_DWELLER_SPAWN_EGG = spawnEgg("cave_dweller", TAEntityTypes.CAVE_DWELLER, 0x5e6b7d, 0xdc54f7);
    public static final DeferredHolder<Item, Item> ROCK_HAMMER_SPAWN_EGG = spawnEgg("rock_hammer", TAEntityTypes.ROCK_HAMMER, 0xe3e0d1, 0x45332e);
    public static final DeferredHolder<Item, Item> TONG_SCORPION_SPAWN_EGG = spawnEgg("tong_scorpion", TAEntityTypes.TONG_SCORPION, 0x4f4334, 0x211a18);
    public static final DeferredHolder<Item, Item> SNOW_TUNDRA_GIANT_CRAB_SPAWN_EGG = spawnEgg("snow_tundra_giant_crab", TAEntityTypes.SNOW_TUNDRA_GIANT_CRAB, 0xd8deed, 0x2347d9);
    public static final DeferredHolder<Item, Item> FLOWER_LEECH_SPAWN_EGG = spawnEgg("flower_leech", TAEntityTypes.FLOWER_LEECH, 0xcd92d1, 0x362d23);
    public static final DeferredHolder<Item, Item> FORGOTTEN_MAGIC_BOOK_SPAWN_EGG = spawnEgg("forgotten_magic_book", TAEntityTypes.FORGOTTEN_MAGIC_BOOK, 0xd9d2c5, 0x3a2f40);
    public static final DeferredHolder<Item, Item> HYPHA_WALKING_MUSHROOM_SPAWN_EGG = spawnEgg("hypha_walking_mushroom", TAEntityTypes.HYPHA_WALKING_MUSHROOM, 0xededdf, 0xc48a80);
    public static final DeferredHolder<Item, Item> MOONLIGHT_KNIGHT_SPAWN_EGG = spawnEgg("moonlight_knight", TAEntityTypes.MOONLIGHT_KNIGHT, 0xb8bdbe, 0x0955a8);
    public static final DeferredHolder<Item, Item> RUNESTONE_KEEPER_SPAWN_EGG = spawnEgg("runestone_keeper", TAEntityTypes.RUNESTONE_KEEPER, 0xccc0e7, 0x550098);
    public static final DeferredHolder<Item, Item> SPIDER_MOTHER_SPAWN_EGG = spawnEgg("spider_mother", TAEntityTypes.SPIDER_MOTHER, 0x595d70, 0x0f1018);
    public static final DeferredHolder<Item, Item> MOON_QUEEN_SPAWN_EGG = spawnEgg("moon_queen", TAEntityTypes.MOON_QUEEN, 0xff82d4, 0x313d4b);
    
    /**
     * Block Item
     * */
    public static final DeferredHolder<Item, Item> AURORIAN_LILY_PAD = ITEMS.register("aurorian_lily_pad",
            () -> new PlaceOnWaterBlockItem(TABlocks.AURORIAN_LILY_PAD.get(), TAItemProperties.get().addItemTag(TAItemTags.BUILDING_BLOCK)));
    public static final DeferredHolder<Item, Item> AURORIAN_WATER_MUSHROOM = ITEMS.register("aurorian_water_mushroom",
            () -> new PlaceOnWaterBlockItem(TABlocks.AURORIAN_WATER_MUSHROOM.get(), TAItemProperties.get().addItemTag(TAItemTags.BUILDING_BLOCK)));
    public static final DeferredHolder<Item, Item> SILENT_WOOD_CHEST = ITEMS.register("silent_wood_chest", SilentWoodChestItem::new);
    public static final DeferredHolder<Item, Item> MOON_TORCH = ITEMS.register("moon_torch", () -> new StandingAndWallBlockItem(
            TABlocks.MOON_TORCH.get(), TABlocks.MOON_WALL_TORCH.get(), TAItemProperties.get().addItemTag(TAItemTags.BUILDING_BLOCK), Direction.DOWN));
    public static final DeferredHolder<Item, Item> SILENT_WOOD_TORCH = ITEMS.register("silent_wood_torch", () -> new StandingAndWallBlockItem(
            TABlocks.SILENT_WOOD_TORCH.get(), TABlocks.SILENT_WOOD_WALL_TORCH.get(), TAItemProperties.get().addItemTag(TAItemTags.BUILDING_BLOCK), Direction.DOWN));
    public static final DeferredHolder<Item, Item> SILENT_WOOD_SIGN = ITEMS.register("silent_wood_sign", () -> new SignItem(
            TAItemProperties.get().stacksTo(16).addItemTag(TAItemTags.BUILDING_BLOCK), TABlocks.SILENT_WOOD_SIGN.get(), TABlocks.SILENT_WOOD_WALL_SIGN.get()));
    public static final DeferredHolder<Item, Item> WEEPING_WILLOW_WOOD_SIGN = ITEMS.register("weeping_willow_wood_sign", () -> new SignItem(
            TAItemProperties.get().stacksTo(16).addItemTag(TAItemTags.BUILDING_BLOCK), TABlocks.WEEPING_WILLOW_WOOD_SIGN.get(), TABlocks.WEEPING_WILLOW_WOOD_WALL_SIGN.get()));
    public static final DeferredHolder<Item, Item> CURTAIN_WOOD_SIGN = ITEMS.register("curtain_wood_sign", () -> new SignItem(
            TAItemProperties.get().stacksTo(16).addItemTag(TAItemTags.BUILDING_BLOCK), TABlocks.CURTAIN_WOOD_SIGN.get(), TABlocks.CURTAIN_WOOD_WALL_SIGN.get()));
    public static final DeferredHolder<Item, Item> CURSED_FROST_WOOD_SIGN = ITEMS.register("cursed_frost_wood_sign", () -> new SignItem(
            TAItemProperties.get().stacksTo(16).addItemTag(TAItemTags.BUILDING_BLOCK), TABlocks.CURSED_FROST_WOOD_SIGN.get(), TABlocks.CURSED_FROST_WOOD_WALL_SIGN.get()));
    public static final DeferredHolder<Item, Item> SILENT_WOOD_HANGING_SIGN = ITEMS.register("silent_wood_hanging_sign", () -> new HangingSignItem(
            TABlocks.SILENT_WOOD_HANGING_SIGN.get(), TABlocks.SILENT_WOOD_WALL_HANGING_SIGN.get(), TAItemProperties.get().stacksTo(16).addItemTag(TAItemTags.BUILDING_BLOCK)));
    public static final DeferredHolder<Item, Item> WEEPING_WILLOW_WOOD_HANGING_SIGN = ITEMS.register("weeping_willow_wood_hanging_sign", () -> new HangingSignItem(
            TABlocks.WEEPING_WILLOW_WOOD_HANGING_SIGN.get(), TABlocks.WEEPING_WILLOW_WOOD_WALL_HANGING_SIGN.get(), TAItemProperties.get().stacksTo(16).addItemTag(TAItemTags.BUILDING_BLOCK)));
    public static final DeferredHolder<Item, Item> CURTAIN_WOOD_HANGING_SIGN = ITEMS.register("curtain_wood_hanging_sign", () -> new HangingSignItem(
            TABlocks.CURTAIN_WOOD_HANGING_SIGN.get(), TABlocks.CURTAIN_WOOD_WALL_HANGING_SIGN.get(), TAItemProperties.get().stacksTo(16).addItemTag(TAItemTags.BUILDING_BLOCK)));
    public static final DeferredHolder<Item, Item> CURSED_FROST_WOOD_HANGING_SIGN = ITEMS.register("cursed_frost_wood_hanging_sign", () -> new HangingSignItem(
            TABlocks.CURSED_FROST_WOOD_HANGING_SIGN.get(), TABlocks.CURSED_FROST_WOOD_WALL_HANGING_SIGN.get(), TAItemProperties.get().stacksTo(16).addItemTag(TAItemTags.BUILDING_BLOCK)));
    
}