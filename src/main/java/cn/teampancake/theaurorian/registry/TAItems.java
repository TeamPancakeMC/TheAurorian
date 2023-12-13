package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.*;
import cn.teampancake.theaurorian.common.items.armor.AurorianSteelArmor;
import cn.teampancake.theaurorian.common.items.armor.CeruleanArmor;
import cn.teampancake.theaurorian.common.items.armor.KnightArmor;
import cn.teampancake.theaurorian.common.items.armor.SpectralArmor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;

import static cn.teampancake.theaurorian.utils.TAItemRegUtils.*;
import static net.minecraft.world.item.ArmorItem.Type.*;

@SuppressWarnings("SpellCheckingInspection")
public class TAItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AurorianMod.MOD_ID);

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
    public static final RegistryObject<Item> SPIKED_CHESTPLATE = ITEMS.register("spiked_chestplate", SpikedItemArmor::new);
    public static final RegistryObject<Item> AURORIAN_SLIME_BOOTS = ITEMS.register("aurorian_slime_boots",
            () -> new ArmorItem(TAArmorMaterials.AURORIAN_SLIME, BOOTS, new Item.Properties().rarity(Rarity.RARE)));

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
    public static final RegistryObject<Item> SILENT_WOOD_PICKAXE = ITEMS.register("silent_wood_pickaxe",
            () -> new PickaxeItem(TAToolTiers.SILENT_WOOD, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> MOONSTONE_PICKAXE = ITEMS.register("moonstone_pickaxe",
            () -> new PickaxeItem(TAToolTiers.SILENT_WOOD, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> QUEENS_CHIPPER = ITEMS.register("queens_chipper", QueensChipper::new);

    /**
     * HoeItem
     */
    public static final RegistryObject<Item> AURORIAN_STEEL_HOE = ITEMS.register("aurorian_steel_hoe",
            () -> new HoeItem(TAToolTiers.AURORIAN_STEEL, -2, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STONE_HOE = ITEMS.register("aurorian_stone_hoe",
            () -> new HoeItem(TAToolTiers.AURORIAN_STONE, -1, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> SILENT_WOOD_HOE = ITEMS.register("silent_wood_hoe",
            () -> new HoeItem(TAToolTiers.SILENT_WOOD, -2, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> MOONSTONE_HOE = ITEMS.register("moonstone_hoe",
            () -> new HoeItem(TAToolTiers.SILENT_WOOD, -2, -1.0F, new Item.Properties()));

    /**
     * ShearsItem
     */
    public static final RegistryObject<Item> AURORIAN_STEEL_SICKLE = ITEMS.register("silent_wood_sickle",
            () -> new ShearsItem(new Item.Properties().durability(50)));
    public static final RegistryObject<Item> AURORIAN_STONE_SICKLE = ITEMS.register("aurorian_stone_sickle",
            () -> new ShearsItem(new Item.Properties().durability(150)));
    public static final RegistryObject<Item> MOONSTONE_SICKLE = ITEMS.register("moonstone_sickle", MoonstoneSickle::new);

    /**
     * ShieldItem
     */
    public static final RegistryObject<Item> UMBRA_SHIELD = ITEMS.register("umbra_shield", UmbraShield::new);
    public static final RegistryObject<Item> CERULEAN_SHIELD = ITEMS.register("cerulean_shield", CeruleanShield::new);
    public static final RegistryObject<Item> CRYSTALLINE_SHIELD = ITEMS.register("crystalline_shield", CrystallineShield::new);
    public static final RegistryObject<Item> MOONSTONE_SHIELD = ITEMS.register("moonstone_shield", () -> new ShieldItem(new Item.Properties()));
    public static final RegistryObject<Item> MOON_SHIELD = ITEMS.register("moon_shield", () -> new ShieldItem(new Item.Properties()));

    /**
     * BowItem
     */
    public static final RegistryObject<Item> SILENT_WOOD_BOW = ITEMS.register("silent_wood_bow", () -> new BowItem(new Item.Properties().durability(384)));
    public static final RegistryObject<Item> KEEPERS_BOW = ITEMS.register("keepers_bow", KeepersBow::new);

    /**
     * Tea
     */
    public static final RegistryObject<Item> TEA_CUP = normal("tea_cup", false);
    public static final RegistryObject<Item> BEPSI = ITEMS.register("bepsi", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(0).saturationMod(0)
                    .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600), 1.0F).build())));
    public static final RegistryObject<Item> LAVENDER_TEA = ITEMS.register("lavender_tea", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(0).saturationMod(0)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300), 1.0F).build())));
    public static final RegistryObject<Item> SILKBERRY_TEA = ITEMS.register("silk_berry_tea", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(0).saturationMod(0)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100), 1.0F).build())));
    public static final RegistryObject<Item> LAVENDER_SEEDY_TEA = ITEMS.register("lavender_seedy_tea", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(0).saturationMod(0)
                    .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 1.0F).build())));
    public static final RegistryObject<Item> PETUNIA_TEA = ITEMS.register("petunia_tea", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(0).saturationMod(0)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300), 1.0F).build())));
    public static final RegistryObject<Item> SLEEPING_BLACK_TEA = ITEMS.register("sleeping_black_tea", SleepingBlackTea::new);

    /**
     * Food
     */
    public static final RegistryObject<Item> SILK_BERRY_JAM = food("silk_berry_jam", 2, 0.5F, false);
    public static final RegistryObject<Item> SILK_BERRY_JAM_SANDWICH = food("silk_berry_jam_sandwich", 6, 0.9F, true);
    public static final RegistryObject<Item> AURORIAN_PORK = food("aurorian_pork", 3, 0.3F, false);
    public static final RegistryObject<Item> AURORIAN_BACON = food("aurorian_bacon", 2, 0.8F, true);
    public static final RegistryObject<Item> COOKED_AURORIAN_PORK = food("cooked_aurorian_pork", 8, 0.8F, true);
    public static final RegistryObject<Item> AURORIAN_SLIMEBALL = food("aurorian_slimeball", 1, 0.2F, false);
    public static final RegistryObject<Item> SILK_SHROOM_STEW = food("silk_shroom_stew", 6, 1F, false);
    public static final RegistryObject<Item> LAVENDER_BREAD = food("lavender_bread", 4, 0.4F, false);
    public static final RegistryObject<Item> SOULLESS_FLESH = food("soulless_flesh", 2, 0.1F, false);
    public static final RegistryObject<Item> LAVENDER_SEEDS = alias("lavender_seeds", TABlocks.LAVENDER_CROP, new Item.Properties());
    public static final RegistryObject<Item> WEEPING_WILLOW_SAP = ITEMS.register("weeping_willow_sap", WeepingWillowSap::new);
    public static final RegistryObject<Item> SILK_BERRY = alias("silk_berry", TABlocks.SILK_BERRY_CROP, new Item.Properties().food(
            new FoodProperties.Builder().nutrition(1).saturationMod(0.1F).build()));
    //TODO Translation and Recipe
//    public static final RegistryObject<Item> STRANGE_MEAT_FOOD = ITEMS.register("strange_meat_food", StrangeMeatFood::new);

    /**
     * Materials
     */
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
     * Key
     */
    public static final RegistryObject<Item> MOON_TEMPLE_CELL_KEY_FRAGMENT = normal("moon_temple_cell_key_fragment", true);
    public static final RegistryObject<Item> RUNE_STONE_KEY = ITEMS.register("rune_stone_key", () -> new AbstractSpecialItem(new Item.Properties().rarity(Rarity.UNCOMMON), true));
    public static final RegistryObject<Item> DARK_STONE_KEY = ITEMS.register("dark_stone_key", () -> new AbstractSpecialItem(new Item.Properties().rarity(Rarity.UNCOMMON), true));
    public static final RegistryObject<Item> MOON_TEMPLE_KEY = ITEMS.register("moon_temple_key", () -> new AbstractSpecialItem(new Item.Properties().rarity(Rarity.UNCOMMON), true));
    public static final RegistryObject<Item> RUNE_STONE_LOOT_KEY = ITEMS.register("rune_stone_loot_key", () -> new AbstractSpecialItem(new Item.Properties().rarity(Rarity.UNCOMMON), true));
    public static final RegistryObject<Item> MOON_TEMPLE_CELL_KEY = ITEMS.register("moon_temple_cell_key", () -> new AbstractSpecialItem(new Item.Properties().rarity(Rarity.UNCOMMON), true));

    /**
     * Arrow
     */
    public static final RegistryObject<Item> CERULEAN_ARROW = ITEMS.register("cerulean_arrow", () -> new CeruleanArrow(new Item.Properties()));
    public static final RegistryObject<Item> CRYSTAL_ARROW = ITEMS.register("crystal_arrow", () -> new CrystalArrow(new Item.Properties()));

    /**
     * Tool
     */
    public static final RegistryObject<Item> ABSORPTION_ORB = ITEMS.register("absorption_orb", AbsorptionOrbItem::new);
    public static final RegistryObject<Item> SILENT_WOOD_STICK = ITEMS.register("silent_wood_stick", SilentWoodStickItem::new);
    public static final RegistryObject<Item> STICKY_SPIKER = ITEMS.register("sticky_spiker", () -> new SimpleThrowProjectProjectile(new Item.Properties()
            .rarity(Rarity.EPIC), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.STICKY_SPIKER::get, 1.5F, 1.0F));

    /**
     * Loot
     */
    public static final RegistryObject<Item> TROPHY_KEEPER = normal("trophy_keeper", true);
    public static final RegistryObject<Item> TROPHY_SPIDER_MOTHER = normal("trophy_spider_mother", true);
    public static final RegistryObject<Item> TROPHY_MOON_QUEEN = normal("trophy_moon_queen", true);
    public static final RegistryObject<Item> CRYSTALLINE_SPRITE = normal("crystalline_sprite", false);

    /**
     * Spawn Egg
     */
    public static final RegistryObject<Item> AURORIAN_RABBIT_SPAWN_EGG = spawnEgg("aurorian_rabbit", TAEntityTypes.AURORIAN_RABBIT, 0xc2e5e5, 0x43a2ec);
    public static final RegistryObject<Item> AURORIAN_SHEEP_SPAWN_EGG = spawnEgg("aurorian_sheep", TAEntityTypes.AURORIAN_SHEEP, 0x97b4f2, 0x7197ea);
    public static final RegistryObject<Item> AURORIAN_PIG_SPAWN_EGG = spawnEgg("aurorian_pig", TAEntityTypes.AURORIAN_PIG, 0xc6dfff, 0x5d6f93);
    public static final RegistryObject<Item> AURORIAN_PIXIE_SPAWN_EGG = spawnEgg("aurorian_pixie", TAEntityTypes.AURORIAN_PIXIE, 0x9cc6f1, 0x88b7e3);
    public static final RegistryObject<Item> AURORIAN_SLIME_SPAWN_EGG = spawnEgg("aurorian_slime", TAEntityTypes.AURORIAN_SLIME, 0x151028, 0x43a2ec);
    public static final RegistryObject<Item> DISTURBED_HOLLOW_SPAWN_EGG = spawnEgg("disturbed_hollow", TAEntityTypes.DISTURBED_HOLLOW, 0xade0f5, 0x272727);
    public static final RegistryObject<Item> UNDEAD_KNIGHT_SPAWN_EGG = spawnEgg("undead_knight", TAEntityTypes.UNDEAD_KNIGHT, 0x5c7394, 0x181b1e);
    public static final RegistryObject<Item> SPIRIT_SPAWN_EGG = spawnEgg("spirit", TAEntityTypes.SPIRIT, 0xb0b6bc, 0x303131);
    public static final RegistryObject<Item> MOON_ACOLYTE_SPAWN_EGG = spawnEgg("moon_acolyte", TAEntityTypes.MOON_ACOLYTE, 0x0270af, 0x191919);
    public static final RegistryObject<Item> SPIDERLING_SPAWN_EGG = spawnEgg("spiderling", TAEntityTypes.SPIDERLING, 0x1efefe, 0x0f1018);
    public static final RegistryObject<Item> CRYSTALLINE_SPRITE_SPAWN_EGG = spawnEgg("crystalline_sprite", TAEntityTypes.CRYSTALLINE_SPRITE, 0xf5e2fc, 0xf0c9fd);
    public static final RegistryObject<Item> RUNESTONE_KEEPER_SPAWN_EGG = spawnEgg("runestone_keeper", TAEntityTypes.RUNESTONE_KEEPER, 0xccc0e7, 0x550098);
    public static final RegistryObject<Item> SPIDER_MOTHER_SPAWN_EGG = spawnEgg("spider_mother", TAEntityTypes.SPIDER_MOTHER, 0x595d70, 0x0f1018);
    public static final RegistryObject<Item> MOON_QUEEN_SPAWN_EGG = spawnEgg("moon_queen", TAEntityTypes.MOON_QUEEN, 0xff82d4, 0x313d4b);

    /**
     * Misc
     */
    public static final RegistryObject<Item> CRYSTAL = normal("crystal", true);
    public static final RegistryObject<Item> AURORIAN_STEEL_NUGGET = normal("aurorian_steel_nugget", true);
    public static final RegistryObject<Item> CERULEAN_NUGGET = normal("cerulean_nugget", true);
    public static final RegistryObject<Item> AURORIAN_COAL_NUGGET = normal("aurorian_coal_nugget", true);
    public static final RegistryObject<Item> MOONSTONE_NUGGET = normal("moonstone_nugget", true);
    public static final RegistryObject<Item> LOCK_PICKS = ITEMS.register("lock_picks",
            () -> new Item(new Item.Properties().stacksTo(1).durability(10)));
    public static final RegistryObject<Item> DUNGEON_LOCATOR = ITEMS.register("dungeon_locator", DungeonLocatorItem::new);
    public static final RegistryObject<Item> WEBBING = ITEMS.register("webbing", () -> new SimpleThrowProjectProjectile(new Item.Properties(),
            SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.WEBBING::get, 0.5F, (0.4F / (new Random().nextFloat() * 0.4F + 0.8F))));
    public static final RegistryObject<Item> LIVING_DIVINING_ROD = ITEMS.register("living_divining_rod", LivingDiviningRod::new);


}