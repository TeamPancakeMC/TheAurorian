package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.common.components.*;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.*;
import cn.teampancake.theaurorian.common.items.armor.*;
import cn.teampancake.theaurorian.common.items.block.AstrologyTable;
import cn.teampancake.theaurorian.common.items.block.AbstractLunarCrystal;
import cn.teampancake.theaurorian.common.items.block.SacrificeTable;
import cn.teampancake.theaurorian.common.items.curio.*;
import cn.teampancake.theaurorian.common.items.curio.runestone.*;
import cn.teampancake.theaurorian.common.items.developer.*;
import cn.teampancake.theaurorian.common.items.shield.CeruleanShield;
import cn.teampancake.theaurorian.common.items.shield.CrystallineShield;
import cn.teampancake.theaurorian.common.items.shield.UmbraShield;
import cn.teampancake.theaurorian.common.items.tool.*;
import cn.teampancake.theaurorian.common.items.tool.aurorian_steel.*;
import cn.teampancake.theaurorian.common.items.tool.aurorian_stone.AurorianStoneAxe;
import cn.teampancake.theaurorian.common.items.tool.aurorian_stone.AurorianStonePickaxe;
import cn.teampancake.theaurorian.common.items.tool.aurorianite.AurorianiteAxe;
import cn.teampancake.theaurorian.common.items.tool.aurorianite.AurorianiteSword;
import cn.teampancake.theaurorian.common.items.weapon.*;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import static cn.teampancake.theaurorian.common.data.datagen.provider.TAItemModelProvider.*;
import static net.minecraft.world.item.ArmorItem.Type.*;
import static cn.teampancake.theaurorian.TheAurorian.REGISTRATE;
import static cn.teampancake.theaurorian.common.utils.TAItemRegUtils.*;

public class TAItems {

    /**
     * Materials
     */
    public static final DeferredHolder<Item, Item> RAW_CERULEAN = simple("raw_cerulean", Item.Properties::new);
    public static final DeferredHolder<Item, Item> RAW_MOONSTONE = simple("raw_moonstone", Item.Properties::new);
    public static final DeferredHolder<Item, Item> AURORIAN_COAL = simple("aurorian_coal", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> AURORIANITE_INGOT = simple("aurorianite_ingot", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL = simple("aurorian_steel", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC));
    public static final DeferredHolder<Item, Item> CERULEAN_INGOT = simple("cerulean_ingot", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> CRYSTALLINE_INGOT = simple("crystalline_ingot", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> MOONSTONE_INGOT = simple("moonstone_ingot", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> UMBRA_INGOT = simple("umbra_ingot", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> LAVENDER = simple("lavender", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> PLANT_FIBER = simple("plant_fiber", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> AURORIANITE_SCRAP = simple("aurorianite_scrap", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> CRYSTALLINE_SCRAP = simple("crystalline_scrap", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> UMBRA_SCRAP = simple("umbra_scrap", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> SPECTRAL_SILK = simple("spectral_silk", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> DARK_AMULET = simple("dark_amulet", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> DUNGEON_KEEPER_AMULET = simple("dungeon_keeper_amulet", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));

    /**
     * Armor Item
     */
    public static final DeferredHolder<Item, Item> HOLY_KNIGHT_HELMET = register("holy_knight_helmet", properties -> new HolyKnightArmor(HELMET));
    public static final DeferredHolder<Item, Item> HOLY_KNIGHT_CHESTPLATE = register("holy_knight_chestplate", properties -> new HolyKnightArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> HOLY_KNIGHT_LEGGINGS = register("holy_knight_leggings", properties -> new HolyKnightArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> HOLY_KNIGHT_BOOTS = register("holy_knight_boots", properties -> new HolyKnightArmor(BOOTS));
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_HELMET = register("aurorian_steel_helmet", properties -> new AurorianSteelArmor(HELMET));
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_CHESTPLATE = register("aurorian_steel_chestplate", properties -> new AurorianSteelArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_LEGGINGS = register("aurorian_steel_leggings", properties -> new AurorianSteelArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_BOOTS = register("aurorian_steel_boots", properties -> new AurorianSteelArmor(BOOTS));
    public static final DeferredHolder<Item, Item> CERULEAN_HELMET = register("cerulean_helmet", properties -> new CeruleanArmor(HELMET));
    public static final DeferredHolder<Item, Item> CERULEAN_CHESTPLATE = register("cerulean_chestplate", properties -> new CeruleanArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> CERULEAN_LEGGINGS = register("cerulean_leggings", properties -> new CeruleanArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> CERULEAN_BOOTS = register("cerulean_boots", properties -> new CeruleanArmor(BOOTS));
    public static final DeferredHolder<Item, Item> KNIGHT_HELMET = register("knight_helmet", properties -> new KnightArmor(HELMET));
    public static final DeferredHolder<Item, Item> KNIGHT_CHESTPLATE = register("knight_chestplate", properties -> new KnightArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> KNIGHT_LEGGINGS = register("knight_leggings", properties -> new KnightArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> KNIGHT_BOOTS = register("knight_boots", properties -> new KnightArmor(BOOTS));
    public static final DeferredHolder<Item, Item> SPECTRAL_HELMET = register("spectral_helmet", properties -> new SpectralArmor(HELMET));
    public static final DeferredHolder<Item, Item> SPECTRAL_CHESTPLATE = register("spectral_chestplate", properties -> new SpectralArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> SPECTRAL_LEGGINGS = register("spectral_leggings", properties -> new SpectralArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> SPECTRAL_BOOTS = register("spectral_boots", properties -> new SpectralArmor(BOOTS));
    public static final DeferredHolder<Item, Item> MYSTERIUM_WOOL_HELMET = register("mysterium_wool_helmet", properties -> new MysteriumWoolArmor(HELMET));
    public static final DeferredHolder<Item, Item> MYSTERIUM_WOOL_CHESTPLATE = register("mysterium_wool_chestplate", properties -> new MysteriumWoolArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> MYSTERIUM_WOOL_LEGGINGS = register("mysterium_wool_leggings", properties -> new MysteriumWoolArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> MYSTERIUM_WOOL_BOOTS = register("mysterium_wool_boots", properties -> new MysteriumWoolArmor(BOOTS));
    public static final DeferredHolder<Item, Item> CRYSTAL_RUNE_HELMET = register("crystal_rune_helmet", properties -> new CrystalRuneArmor(HELMET));
    public static final DeferredHolder<Item, Item> CRYSTAL_RUNE_CHESTPLATE = register("crystal_rune_chestplate", properties -> new CrystalRuneArmor(CHESTPLATE));
    public static final DeferredHolder<Item, Item> CRYSTAL_RUNE_LEGGINGS = register("crystal_rune_leggings", properties -> new CrystalRuneArmor(LEGGINGS));
    public static final DeferredHolder<Item, Item> CRYSTAL_RUNE_BOOTS = register("crystal_rune_boots", properties -> new CrystalRuneArmor(BOOTS));
    public static final DeferredHolder<Item, Item> SPIKED_CHESTPLATE = register("spiked_chestplate", SpikedChestplate::new);
    public static final DeferredHolder<Item, Item> AURORIAN_SLIME_BOOTS = register("aurorian_slime_boots", AurorianSlimeBoots::new);

    /**
     * SwordItem
     */
    public static final DeferredHolder<Item, AurorianSteelDagger> AURORIAN_STEEL_DAGGER = toolBuilder("aurorian_steel_dagger", AurorianSteelDagger::new).tag(ItemTags.SWORDS).register();
    public static final DeferredHolder<Item, AurorianSteelSword> AURORIAN_STEEL_SWORD = REGISTRATE.item("aurorian_steel_sword", properties -> new AurorianSteelSword(
            properties.rarity(Rarity.RARE).attributes(SwordItem.createAttributes(TAToolTiers.AURORIAN_STEEL, 3, -2.4F)).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC)))
            .tag(ItemTags.SWORDS).model((ctx, prov) -> separateTransforms(ctx.getName(), prov, prov.existingFileHelper)).register();
    public static final DeferredHolder<Item, SwordItem> AURORIAN_ALLOY_STEEL_SWORD = toolBuilder("aurorian_alloy_steel_sword", properties -> new SwordItem(TAToolTiers.AURORIAN_ALLOY_STEEL,
            properties.fireResistant().attributes(SwordItem.createAttributes(TAToolTiers.AURORIAN_ALLOY_STEEL, 0, -2.4F)).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.SWORDS).register();
    public static final DeferredHolder<Item, SwordItem> AURORIAN_STONE_SWORD = toolBuilder("aurorian_stone_sword", properties -> new SwordItem(TAToolTiers.AURORIAN_STONE,
            properties.attributes(SwordItem.createAttributes(TAToolTiers.AURORIAN_STONE, 3, -2.4F)).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.SWORDS).register();
    public static final DeferredHolder<Item, SwordItem> SILENT_WOOD_SWORD = toolBuilder("silent_wood_sword", properties -> new SwordItem(TAToolTiers.SILENT_WOOD,
            properties.attributes(SwordItem.createAttributes(TAToolTiers.SILENT_WOOD, 4, -1.6F)).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.SWORDS).register();
    public static final DeferredHolder<Item, UmbraSword> UMBRA_SWORD = REGISTRATE.item("umbra_sword", UmbraSword::new).tag(ItemTags.SWORDS).register();
    public static final DeferredHolder<Item, AurorianiteSword> AURORIANITE_SWORD = REGISTRATE.item("aurorianite_sword", AurorianiteSword::new).tag(ItemTags.SWORDS).register();
    public static final DeferredHolder<Item, CrystallineSword> CRYSTALLINE_SWORD = REGISTRATE.item("crystalline_sword", CrystallineSword::new).tag(ItemTags.SWORDS).register();
    public static final DeferredHolder<Item, SwordItem> MOONSTONE_SWORD = REGISTRATE.item("moonstone_sword", properties -> new SwordItem(TAToolTiers.SILENT_WOOD,
            properties.attributes(SwordItem.createAttributes(TAToolTiers.SILENT_WOOD, 4, -1.6F)).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.SWORDS).register();
    public static final DeferredHolder<Item, TASwordItem> KOPISH_DAGGER = toolBuilder("kopish_dagger", properties -> new TASwordItem(
            properties.durability(70).attributes(TASwordItem.createAttributes(7.0F, 2.2F))
                    .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.LEGENDARY), 10)).tag(ItemTags.SWORDS).register();
    public static final DeferredHolder<Item, TASwordItem> STEEL_DAGGER = toolBuilder("steel_dagger", properties -> new TASwordItem(
            properties.durability(280).attributes(TASwordItem.createAttributes(8.0F, 2.2F))
                    .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC), 10)).tag(ItemTags.SWORDS).register();

    /**
     * ShovelItem
     */
    public static final DeferredHolder<Item, AurorianSteelShovel> AURORIAN_STEEL_SHOVEL = REGISTRATE.item("aurorian_steel_shovel", properties -> new AurorianSteelShovel(
            properties.attributes(ShovelItem.createAttributes(TAToolTiers.AURORIAN_STEEL, 1.5F, -3.0F)).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON)))
            .tag(ItemTags.SHOVELS).model((ctx, prov) -> separateTransforms(ctx.getName(), prov, prov.existingFileHelper)).register();
    public static final DeferredHolder<Item, ShovelItem> AURORIAN_STONE_SHOVEL = toolBuilder("aurorian_stone_shovel", properties -> new ShovelItem(
            TAToolTiers.AURORIAN_STONE, properties.attributes(ShovelItem.createAttributes(TAToolTiers.AURORIAN_STONE, 1.5F, -3.0F))
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON))).tag(ItemTags.SHOVELS).register();
    public static final DeferredHolder<Item, ShovelItem> SILENT_WOOD_SHOVEL = toolBuilder("silent_wood_shovel", properties -> new ShovelItem(
            TAToolTiers.SILENT_WOOD, properties.attributes(ShovelItem.createAttributes(TAToolTiers.SILENT_WOOD, 1.5F, -3.0F))
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.SHOVELS).register();
    public static final DeferredHolder<Item, ShovelItem> MOONSTONE_SHOVEL = toolBuilder("moonstone_shovel", properties -> new ShovelItem(
            TAToolTiers.SILENT_WOOD, properties.attributes(ShovelItem.createAttributes(TAToolTiers.SILENT_WOOD, 1.5F, -3.0F))
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.SHOVELS).register();

    /**
     * AxeItem
     */
    public static final DeferredHolder<Item, AurorianSteelAxe> AURORIAN_STEEL_AXE = REGISTRATE.item("aurorian_steel_axe", properties -> new AurorianSteelAxe(
            properties.attributes(AxeItem.createAttributes(TAToolTiers.AURORIAN_STEEL, 6.0F, -3.0F)).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC)))
            .tag(ItemTags.AXES).model((ctx, prov) -> separateTransforms(ctx.getName(), prov, prov.existingFileHelper)).register();
    public static final DeferredHolder<Item, AurorianiteAxe> AURORIANITE_AXE = toolBuilder("aurorianite_axe", AurorianiteAxe::new).tag(ItemTags.AXES).register();
    public static final DeferredHolder<Item, AurorianStoneAxe> AURORIAN_STONE_AXE = toolBuilder("aurorian_stone_axe", AurorianStoneAxe::new).tag(ItemTags.AXES).register();
    public static final DeferredHolder<Item, AxeItem> SILENT_WOOD_AXE = toolBuilder("silent_wood_axe", properties -> new AxeItem(
            TAToolTiers.SILENT_WOOD, properties.attributes(AxeItem.createAttributes(TAToolTiers.SILENT_WOOD, 6.0F, -3.2F))
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.AXES).register();
    public static final DeferredHolder<Item, AxeItem> MOONSTONE_AXE = toolBuilder("moonstone_axe", properties -> new AxeItem(
            TAToolTiers.SILENT_WOOD, properties.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.AXES).register();

    /**
     * PickaxeItem
     */
    public static final DeferredHolder<Item, QueensChipper> QUEENS_CHIPPER = toolBuilder("queens_chipper", QueensChipper::new).tag(ItemTags.PICKAXES).register();
    public static final DeferredHolder<Item, AurorianSteelPickaxe> AURORIAN_STEEL_PICKAXE = REGISTRATE.item("aurorian_steel_pickaxe", properties -> new AurorianSteelPickaxe(
            properties.attributes(PickaxeItem.createAttributes(TAToolTiers.AURORIAN_STEEL, 1, -2.8F)).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC)))
            .tag(ItemTags.PICKAXES).model((ctx, prov) -> separateTransforms(ctx.getName(), prov, prov.existingFileHelper)).register();
    public static final DeferredHolder<Item, PickaxeItem> AURORIANITE_PICKAXE = toolBuilder("aurorianite_pickaxe", properties -> new PickaxeItem(
            TAToolTiers.AURORIANITE, properties.rarity(Rarity.EPIC).attributes(PickaxeItem.createAttributes(TAToolTiers.AURORIAN_STEEL, 1, -3.0F))
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.PICKAXES).register();
    public static final DeferredHolder<Item, UmbraPickaxe> UMBRA_PICKAXE = toolBuilder("umbra_pickaxe", UmbraPickaxe::new).tag(ItemTags.PICKAXES).register();
    public static final DeferredHolder<Item, CrystallinePickaxe> CRYSTALLINE_PICKAXE = toolBuilder("crystalline_pickaxe", CrystallinePickaxe::new).tag(ItemTags.PICKAXES).register();
    public static final DeferredHolder<Item, AurorianStonePickaxe> AURORIAN_STONE_PICKAXE = toolBuilder("aurorian_stone_pickaxe", AurorianStonePickaxe::new).tag(ItemTags.PICKAXES).register();
    public static final DeferredHolder<Item, SilentWoodPickaxe> SILENT_WOOD_PICKAXE = toolBuilder("silent_wood_pickaxe", SilentWoodPickaxe::new).tag(ItemTags.PICKAXES).register();
    public static final DeferredHolder<Item, PickaxeItem> MOONSTONE_PICKAXE = toolBuilder("moonstone_pickaxe", properties -> new PickaxeItem(
            TAToolTiers.SILENT_WOOD, properties.attributes(PickaxeItem.createAttributes(TAToolTiers.SILENT_WOOD, 1, -2.8F))
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.PICKAXES).register();

    /**
     * HoeItem
     */
    public static final DeferredHolder<Item, HoeItem> AURORIAN_STEEL_HOE = toolBuilder("aurorian_steel_hoe", properties -> new HoeItem(TAToolTiers.AURORIAN_STEEL, properties.attributes(
            HoeItem.createAttributes(TAToolTiers.AURORIAN_STEEL, -2, -1.0F)).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.HOES).register();
    public static final DeferredHolder<Item, HoeItem> AURORIAN_STONE_HOE = toolBuilder("aurorian_stone_hoe", properties -> new HoeItem(TAToolTiers.AURORIAN_STONE, properties.attributes(
            HoeItem.createAttributes(TAToolTiers.AURORIAN_STONE, -2, -1.0F)).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.HOES).register();
    public static final DeferredHolder<Item, HoeItem> SILENT_WOOD_HOE = toolBuilder("silent_wood_hoe", properties -> new HoeItem(TAToolTiers.SILENT_WOOD, properties.attributes(
            HoeItem.createAttributes(TAToolTiers.SILENT_WOOD, -2, -1.0F)).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.HOES).register();
    public static final DeferredHolder<Item, HoeItem> MOONSTONE_HOE = toolBuilder("moonstone_hoe", properties -> new HoeItem(TAToolTiers.SILENT_WOOD, properties.attributes(
            HoeItem.createAttributes(TAToolTiers.SILENT_WOOD, -2, -1.0F)).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.HOES).register();

    /**
     * ShearsItem
     */
    public static final DeferredHolder<Item, ShearsItem> SILENT_WOOD_SICKLE = toolBuilder("silent_wood_sickle", properties -> new ShearsItem(
            properties.durability(50).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.DURABILITY_ENCHANTABLE, ItemTags.MINING_ENCHANTABLE).register();
    public static final DeferredHolder<Item, ShearsItem> AURORIAN_STONE_SICKLE = toolBuilder("aurorian_stone_sickle", properties -> new ShearsItem(
            properties.durability(150).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.DURABILITY_ENCHANTABLE, ItemTags.MINING_ENCHANTABLE).register();
    public static final DeferredHolder<Item, MoonstoneSickle> MOONSTONE_SICKLE = toolBuilder("moonstone_sickle", MoonstoneSickle::new).tag(ItemTags.DURABILITY_ENCHANTABLE, ItemTags.MINING_ENCHANTABLE).register();

    /**
     * ShieldItem
     */
    public static final DeferredHolder<Item, ShieldItem> MOON_SHIELD = registerBuilder("moon_shield", properties -> new ShieldItem(
            properties.rarity(Rarity.EPIC).durability(512).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC)))
            .tag(ItemTags.DURABILITY_ENCHANTABLE).model((ctx, prov) -> shieldItem(ctx.get(), prov)).register();
    public static final DeferredHolder<Item, UmbraShield> UMBRA_SHIELD = registerBuilder("umbra_shield", UmbraShield::new)
            .tag(ItemTags.DURABILITY_ENCHANTABLE).model((ctx, prov) -> shieldItem(ctx.get(), prov)).register();
    public static final DeferredHolder<Item, CeruleanShield> CERULEAN_SHIELD = registerBuilder("cerulean_shield", CeruleanShield::new)
            .tag(ItemTags.DURABILITY_ENCHANTABLE).model((ctx, prov) -> shieldItem(ctx.get(), prov)).register();
    public static final DeferredHolder<Item, CrystallineShield> CRYSTALLINE_SHIELD = registerBuilder("crystalline_shield", CrystallineShield::new)
            .tag(ItemTags.DURABILITY_ENCHANTABLE).model((ctx, prov) -> shieldItem(ctx.get(), prov)).register();
    public static final DeferredHolder<Item, ShieldItem> MOONSTONE_SHIELD = registerBuilder("moonstone_shield", properties -> new ShieldItem(
            properties.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC).durability(512)))
            .tag(ItemTags.DURABILITY_ENCHANTABLE).model((ctx, prov) -> shieldItem(ctx.get(), prov)).register();

    /**
     * BowItem
     */
    public static final DeferredHolder<Item, BowItem> SILENT_WOOD_BOW = REGISTRATE.item("silent_wood_bow", properties -> new BowItem(properties.durability(384)
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.DURABILITY_ENCHANTABLE, ItemTags.BOW_ENCHANTABLE).model((ctx, prov) -> bowItem(ctx.get(), prov)).register();
    public static final DeferredHolder<Item, KeepersBow> KEEPERS_BOW = REGISTRATE.item("keepers_bow", properties -> new KeepersBow(properties.durability(512).rarity(Rarity.RARE)
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.DURABILITY_ENCHANTABLE, ItemTags.BOW_ENCHANTABLE).model((ctx, prov) -> bowItem(ctx.get(), prov)).register();
    public static final DeferredHolder<Item, AurorianSteelBow> AURORIAN_STEEL_BOW = REGISTRATE.item("aurorian_steel_bow", properties -> new AurorianSteelBow(properties.durability(512).rarity(Rarity.RARE)
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC))).tag(ItemTags.DURABILITY_ENCHANTABLE, ItemTags.BOW_ENCHANTABLE).register();

    /**
     * Throwable Weapons
     */
    public static final DeferredHolder<Item, Item> MOON_SHURIKEN = register("moon_shuriken", properties -> new SimpleThrowProjectProjectile(
            properties, SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.THROWN_SHURIKEN::get, 1.5F));
    public static final DeferredHolder<Item, Item> UNSTABLE_CRYSTAL = register("unstable_crystal", properties -> new SimpleThrowProjectProjectile(
            properties.stacksTo(16), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.UNSTABLE_CRYSTAL::get, 1.5F));
    public static final DeferredHolder<Item, Item> AURORIAN_SLATE_BRICK = register("aurorian_slate_brick", AurorianSlateBrick::new);

    /**
     * Tea
     */
    public static final DeferredHolder<Item, Item> TEA_CUP = simple("tea_cup", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> LAVENDER_TEA = register("lavender_tea", properties -> new TeaFood(
            properties.food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300), 1.0F).build())));
    public static final DeferredHolder<Item, Item> SILK_BERRY_TEA = register("silk_berry_tea", properties -> new TeaFood(
            properties.food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100), 1.0F).build())));
    public static final DeferredHolder<Item, Item> LAVENDER_SEEDY_TEA = register("lavender_seedy_tea", properties -> new TeaFood(
            properties.food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 1.0F).build())));
    public static final DeferredHolder<Item, Item> PETUNIA_TEA = register("petunia_tea", properties -> new TeaFood(
            properties.food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300), 1.0F).build())));
    public static final DeferredHolder<Item, Item> BEPSI = register("bepsi", properties -> new TeaFood(
            properties.food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600), 1.0F).build())));

    /**
     * Food
     */
    public static final DeferredHolder<Item, DoubleHighBlockItem> TALL_WICK_GRASS = REGISTRATE.item("tall_wick_grass",
            properties -> new DoubleHighBlockItem(TABlocks.TALL_WICK_GRASS.get(), properties)).register();
    public static final DeferredHolder<Item, Item> AURORIAN_BEEF = register("aurorian_beef", properties -> new Item(properties.food(Foods.BEEF)));
    public static final DeferredHolder<Item, Item> AURORIAN_PORK = register("aurorian_pork", properties -> new Item(properties.food(Foods.PORKCHOP)));
    public static final DeferredHolder<Item, Item> AURORIAN_MUTTON = register("aurorian_mutton", properties -> new Item(properties.food(Foods.MUTTON)));
    public static final DeferredHolder<Item, Item> AURORIAN_RABBIT = register("aurorian_rabbit", properties -> new Item(properties.food(Foods.RABBIT)));
    public static final DeferredHolder<Item, Item> COOKED_AURORIAN_BEEF = REGISTRATE.item("cooked_aurorian_beef",
            properties -> new Item(properties.food(Foods.COOKED_BEEF))).tag(TAItemTags.COOKED_MEAT).defaultModel().register();
    public static final DeferredHolder<Item, Item> COOKED_AURORIAN_PORK = REGISTRATE.item("cooked_aurorian_pork",
            properties -> new Item(properties.food(Foods.COOKED_PORKCHOP))).tag(TAItemTags.COOKED_MEAT).defaultModel().register();
    public static final DeferredHolder<Item, Item> COOKED_AURORIAN_MUTTON = REGISTRATE.item("cooked_aurorian_mutton",
            properties -> new Item(properties.food(Foods.COOKED_MUTTON))).tag(TAItemTags.COOKED_MEAT).defaultModel().register();
    public static final DeferredHolder<Item, Item> COOKED_AURORIAN_RABBIT = REGISTRATE.item("cooked_aurorian_rabbit",
            properties -> new Item(properties.food(Foods.COOKED_RABBIT))).tag(TAItemTags.COOKED_MEAT).defaultModel().register();
    public static final DeferredHolder<Item, Item> WEEPING_WILLOW_SAP = register("weeping_willow_sap", WeepingWillowSap::new);
    public static final DeferredHolder<Item, Item> SILK_BERRY_JAM = food("silk_berry_jam", Item.Properties::new, 2, 0.5F);
    public static final DeferredHolder<Item, Item> SILK_BERRY_JAM_SANDWICH = food("silk_berry_jam_sandwich", Item.Properties::new, 6, 0.9F);
    public static final DeferredHolder<Item, Item> AURORIAN_SLIMEBALL = food("aurorian_slimeball", Item.Properties::new, 1, 0.2F);
    public static final DeferredHolder<Item, Item> SILK_SHROOM_STEW = food("silk_shroom_stew", Item.Properties::new, 6, 1F);
    public static final DeferredHolder<Item, Item> LAVENDER_BREAD = food("lavender_bread", Item.Properties::new, 4, 0.4F);
    public static final DeferredHolder<Item, Item> SOULLESS_FLESH = food("soulless_flesh", Item.Properties::new, 2, 0.1F);
    public static final DeferredHolder<Item, Item> MOON_FISH = food("moon_fish", Item.Properties::new, 2, 0.4F);
    public static final DeferredHolder<Item, Item> AURORIAN_WINGED_FISH = food("aurorian_winged_fish", Item.Properties::new, 2,0.4F);
    public static final DeferredHolder<Item, Item> COOKED_MOON_FISH = food("cooked_moon_fish", Item.Properties::new, 5,6, TAItemTags.COOKED_MEAT);
    public static final DeferredHolder<Item, Item> COOKED_AURORIAN_WINGED_FISH = food("cooked_aurorian_winged_fish", Item.Properties::new, 5,6, TAItemTags.COOKED_MEAT);
    public static final DeferredHolder<Item, ItemNameBlockItem> LAVENDER_SEEDS = alias("lavender_seeds", TABlocks.LAVENDER_CROP, Item.Properties::new);
    public static final DeferredHolder<Item, ItemNameBlockItem> SILK_BERRY = alias("silk_berry", TABlocks.SILK_BERRY_CROP, () -> new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, ItemNameBlockItem> BLUEBERRY = alias("blueberry", TABlocks.BLUEBERRY_BUSH, () -> new Item.Properties().food(Foods.SWEET_BERRIES));
    public static final DeferredHolder<Item, Item> CANDY = food("candy", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE), 4, 0.2F);
    public static final DeferredHolder<Item, Item> CANDY_CANE = register("candy_cane", properties -> new Item(properties.food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.4F)
            .effect(() -> new MobEffectInstance(MobEffects.LUCK, 300), 1.0F).build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> GINGERBREAD_MAN = register("gingerbread_man", properties -> new Item(properties.food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.4F)
            .effect(() -> new MobEffectInstance(TAMobEffects.WARM, 1200), 1.0F).build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> AURORIAN_SPECIALTY_DRINK = register("aurorian_specialty_drink", properties -> new Item(
            properties.food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600), 1.0F).build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> MOONLIT_BLUEBERRY_SPECIALTY_DRINK = register("moonlit_blueberry_specialty_drink", properties -> new Item(
            properties.food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1), 1.0F).build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> AURORIAN_BACON = register("aurorian_bacon", properties -> new Item(properties.food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.8F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60), 1.0F).build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> STRANGE_MEAT = register("strange_meat", properties -> new StrangeMeat(properties.rarity(Rarity.EPIC).durability(10)
            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.9F).alwaysEdible().build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> LAVENDER_SALAD = register("lavender_salad", properties -> new Item(properties.food(new FoodProperties.Builder().nutrition(4).saturationModifier(5.0F)
            .effect(() -> new MobEffectInstance(TAMobEffects.NATURE, 600), 1.0F).build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> FAKE_ALGAL_PIT_FISH = register("fake_algal_pit_fish", properties -> new Item(properties
            .food(new FoodProperties.Builder().nutrition(4).saturationModifier(5.0F).build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> SASHIMI = register("sashimi", properties -> new Item(properties.food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.LUCK, 400), 1.0F).build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> SILENT_WOOD_FRUIT = register("silent_wood_fruit", properties -> new Item(properties
            .food(new FoodProperties.Builder().nutrition(3).saturationModifier(2.5F).build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> GOLDEN_SILENT_WOOD_FRUIT = register("golden_silent_wood_fruit", properties -> new Item(properties
            .food(new FoodProperties.Builder().nutrition(4).saturationModifier(5.0F).build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> KEBAB_WITH_MUSHROOM = register("kebab_with_mushroom",
            properties -> new Item(properties.food(new FoodProperties.Builder().nutrition(12).saturationModifier(15.0F)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200), 1.0F).build())
                    .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> AURORIAN_WINTER_ROOT = register("aurorian_winter_root", properties -> new Item(properties
            .food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.8F).build()).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> ROASTED_AURORIAN_WINTER_ROOT = register("roasted_aurorian_winter_root",
            properties -> new Item(properties.food(new FoodProperties.Builder().nutrition(4).saturationModifier(6.0F)
                    .effect(() -> new MobEffectInstance(TAMobEffects.WARM, 400), 1.0F).build())
                    .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> DARK_STONE_SHRIMP = register("dark_stone_shrimp",
            properties -> new Item(properties.food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.8F).build())
                    .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));

    /**
     * Key
     */
    public static final DeferredHolder<Item, Item> MOON_TEMPLE_CELL_KEY_FRAGMENT = simple("moon_temple_cell_key_fragment",
            () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> RUNE_STONE_KEY = register("rune_stone_key",
            properties -> new Item(properties.rarity(Rarity.UNCOMMON).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> DARK_STONE_KEY = register("dark_stone_key",
            properties -> new Item(properties.rarity(Rarity.UNCOMMON).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> MOON_TEMPLE_KEY = register("moon_temple_key",
            properties -> new Item(properties.rarity(Rarity.UNCOMMON).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> RUNE_STONE_LOOT_KEY = register("rune_stone_loot_key",
            properties -> new Item(properties.rarity(Rarity.UNCOMMON).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> MOON_TEMPLE_CELL_KEY = register("moon_temple_cell_key",
            properties -> new Item(properties.rarity(Rarity.UNCOMMON).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));

    /**
     * Arrow
     */
    public static final DeferredHolder<Item, CeruleanArrow> CERULEAN_ARROW = REGISTRATE.item("cerulean_arrow",
            properties -> new CeruleanArrow(properties.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE))).tag(ItemTags.ARROWS).defaultModel().register();
    public static final DeferredHolder<Item, CrystalArrow> CRYSTAL_ARROW = REGISTRATE.item("crystal_arrow",
            properties -> new CrystalArrow(properties.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE))).tag(ItemTags.ARROWS).defaultModel().register();

    /**
     * Tool
     */
    public static final DeferredHolder<Item, Item> ABSORPTION_ORB = register("absorption_orb", AbsorptionOrb::new);
    public static final DeferredHolder<Item, Item> STICKY_SPIKER = register("sticky_spiker",
            properties -> new SimpleThrowProjectProjectile(properties.rarity(Rarity.EPIC).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE),
                    SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.STICKY_SPIKER::get, 1.5F, 1.0F));
    public static final DeferredHolder<Item, ItemNameBlockItem> SILENT_WOOD_STICK = alias("silent_wood_stick", TABlocks.SILENT_WOOD_STICK,
            () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));

    /**
     * Loot
     */
    public static final DeferredHolder<Item, Item> TROPHY_KEEPER = simple("trophy_keeper", () -> new Item.Properties()
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.LEGENDARY).component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE));
    public static final DeferredHolder<Item, Item> TROPHY_SPIDER_MOTHER = simple("trophy_spider_mother", () -> new Item.Properties()
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.LEGENDARY).component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE));
    public static final DeferredHolder<Item, Item> TROPHY_MOON_QUEEN = simple("trophy_moon_queen", () -> new Item.Properties()
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.LEGENDARY).component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE));
    public static final DeferredHolder<Item, Item> RUNE_KNOWLEDGE_FRAGMENT = register("rune_knowledge_fragment", RuneKnowledgeFragment::new);

    /**
     * Runestone
     */
    public static final DeferredHolder<Item, IceRunestone> RUNESTONE_ICE_1 = registerBuilder("runestone_ice_1", properties -> new IceRunestone(
            properties.component(TADataComponents.RUNESTONE_ICE, new RunestoneIce(0.1f, 0.3f, 1.0f)), 1)).register();
    public static final DeferredHolder<Item, IceRunestone> RUNESTONE_ICE_2 = registerBuilder("runestone_ice_2", properties -> new IceRunestone(
            properties.component(TADataComponents.RUNESTONE_ICE, new RunestoneIce(0.1f, 0.3f, 1.5f)), 2)).register();
    public static final DeferredHolder<Item, IceRunestone> RUNESTONE_ICE_3 = registerBuilder("runestone_ice_3", properties -> new IceRunestone(
            properties.component(TADataComponents.RUNESTONE_ICE, new RunestoneIce(0.31f, 0.5f, 1.5f)), 3)).register();
    public static final DeferredHolder<Item, IceRunestone> RUNESTONE_ICE_4 = registerBuilder("runestone_ice_4", properties -> new IceRunestone(
            properties.component(TADataComponents.RUNESTONE_ICE, new RunestoneIce(0.31f, 0.5f, 2.0f)), 4)).register();
    public static final DeferredHolder<Item, IceRunestone> RUNESTONE_ICE_5 = registerBuilder("runestone_ice_5", properties -> new IceRunestone(
            properties.component(TADataComponents.RUNESTONE_ICE, new RunestoneIce(1.0f, 1.0f, 2.0f)), 5)).register();
    public static final DeferredHolder<Item, LifeRunestone> RUNESTONE_LIFE_1 = registerBuilder("runestone_life_1", properties -> new LifeRunestone(
            properties.component(TADataComponents.RUNESTONE_LIFE, new RunestoneLife(1, 6)).component(TADataComponents.FIXED_HEALTH_BOOST, 5.0F), 1)).register();
    public static final DeferredHolder<Item, LifeRunestone> RUNESTONE_LIFE_2 = registerBuilder("runestone_life_2", properties -> new LifeRunestone(
            properties.component(TADataComponents.RUNESTONE_LIFE, new RunestoneLife(6, 11)).component(TADataComponents.FIXED_HEALTH_BOOST, 10.0F), 2)).register();
    public static final DeferredHolder<Item, LifeRunestone> RUNESTONE_LIFE_3 = registerBuilder("runestone_life_3", properties -> new LifeRunestone(
            properties.component(TADataComponents.RUNESTONE_LIFE, new RunestoneLife(11, 16)).component(TADataComponents.FIXED_HEALTH_BOOST, 15.0F), 3)).register();
    public static final DeferredHolder<Item, LifeRunestone> RUNESTONE_LIFE_4 = registerBuilder("runestone_life_4", properties -> new LifeRunestone(
            properties.component(TADataComponents.RUNESTONE_LIFE, new RunestoneLife(16, 21)).component(TADataComponents.FIXED_HEALTH_BOOST, 20.0F), 4)).register();
    public static final DeferredHolder<Item, LifeRunestone> RUNESTONE_LIFE_5 = registerBuilder("runestone_life_5", properties -> new LifeRunestone(
            properties.component(TADataComponents.RUNESTONE_LIFE, new RunestoneLife(21, 26)).component(TADataComponents.FIXED_HEALTH_BOOST, 25.0F), 5)).register();
    public static final DeferredHolder<Item, LightRunestone> RUNESTONE_LIGHT_1 = registerBuilder("runestone_light_1", properties -> new LightRunestone(
            properties.component(TADataComponents.RUNESTONE_LIGHT, new RunestoneWater(0.1f, 0.2f, 1.0f)), 1)).register();
    public static final DeferredHolder<Item, LightRunestone> RUNESTONE_LIGHT_2 = registerBuilder("runestone_light_2", properties -> new LightRunestone(
            properties.component(TADataComponents.RUNESTONE_LIGHT, new RunestoneWater(0.21f, 0.3f, 1.0f)), 2)).register();
    public static final DeferredHolder<Item, LightRunestone> RUNESTONE_LIGHT_3 = registerBuilder("runestone_light_3", properties -> new LightRunestone(
            properties.component(TADataComponents.RUNESTONE_LIGHT, new RunestoneWater(0.21f, 0.3f, 2.0f)), 3)).register();
    public static final DeferredHolder<Item, LightRunestone> RUNESTONE_LIGHT_4 = registerBuilder("runestone_light_4", properties -> new LightRunestone(
            properties.component(TADataComponents.RUNESTONE_LIGHT, new RunestoneWater(0.31f, 0.4f, 2.0f)), 4)).register();
    public static final DeferredHolder<Item, LightRunestone> RUNESTONE_LIGHT_5 = registerBuilder("runestone_light_5", properties -> new LightRunestone(
            properties.component(TADataComponents.RUNESTONE_LIGHT, new RunestoneWater(1.0f, 1.0f, 2.0f)), 5)).register();
    public static final DeferredHolder<Item, WaterRunestone> RUNESTONE_WATER_1 = registerBuilder("runestone_water_1", properties -> new WaterRunestone(
            properties.component(TADataComponents.RUNESTONE_WATER, new RunestoneWater(0.1f, 0.2f, 1.0f)), 1)).register();
    public static final DeferredHolder<Item, WaterRunestone> RUNESTONE_WATER_2 = registerBuilder("runestone_water_2", properties -> new WaterRunestone(
            properties.component(TADataComponents.RUNESTONE_WATER, new RunestoneWater(0.21f, 0.3f, 1.0f)), 2)).register();
    public static final DeferredHolder<Item, WaterRunestone> RUNESTONE_WATER_3 = registerBuilder("runestone_water_3", properties -> new WaterRunestone(
            properties.component(TADataComponents.RUNESTONE_WATER, new RunestoneWater(0.25f, 0.35f, 2.0f)), 3)).register();
    public static final DeferredHolder<Item, WaterRunestone> RUNESTONE_WATER_4 = registerBuilder("runestone_water_4", properties -> new WaterRunestone(
            properties.component(TADataComponents.RUNESTONE_WATER, new RunestoneWater(0.36f, 0.5f, 2.0f)), 4)).register();
    public static final DeferredHolder<Item, WaterRunestone> RUNESTONE_WATER_5 = registerBuilder("runestone_water_5", properties -> new WaterRunestone(
            properties.component(TADataComponents.RUNESTONE_WATER, new RunestoneWater(1.0f, 1.0f, 2.0f)), 5)).register();
    public static final DeferredHolder<Item, BlazeRunestone> RUNESTONE_BLAZE_1 = registerBuilder("runestone_blaze_1", properties -> new BlazeRunestone(
            properties.component(TADataComponents.RUNESTONE_BLAZE, new RunestoneBlaze(0.2f, 0.3f, 0.01f, 0.1f)), 1)).register();
    public static final DeferredHolder<Item, BlazeRunestone> RUNESTONE_BLAZE_2 = registerBuilder("runestone_blaze_2", properties -> new BlazeRunestone(
            properties.component(TADataComponents.RUNESTONE_BLAZE, new RunestoneBlaze(0.3f, 0.4f, 0.11f, 0.2f)), 2)).register();
    public static final DeferredHolder<Item, BlazeRunestone> RUNESTONE_BLAZE_3 = registerBuilder("runestone_blaze_3", properties -> new BlazeRunestone(
            properties.component(TADataComponents.RUNESTONE_BLAZE, new RunestoneBlaze(0.3f, 0.4f, 0.21f, 0.3f)), 3)).register();
    public static final DeferredHolder<Item, BlazeRunestone> RUNESTONE_BLAZE_4 = registerBuilder("runestone_blaze_4", properties -> new BlazeRunestone(
            properties.component(TADataComponents.RUNESTONE_BLAZE, new RunestoneBlaze(0.4f, 0.6f, 0.31f, 0.4f)), 4)).register();
    public static final DeferredHolder<Item, BlazeRunestone> RUNESTONE_BLAZE_5 = registerBuilder("runestone_blaze_5", properties -> new BlazeRunestone(
            properties.component(TADataComponents.RUNESTONE_BLAZE, new RunestoneBlaze(1.0f, 1.0f, 0.5f, 0.5f)), 5)).register();
    public static final DeferredHolder<Item, ThunderRunestone> RUNESTONE_THUNDER_1 = registerBuilder("runestone_thunder_1", properties -> new ThunderRunestone(
            properties.component(TADataComponents.RUNESTONE_THUNDER, new RunestoneThunder(0.01f, 0.15f)), 1)).register();
    public static final DeferredHolder<Item, ThunderRunestone> RUNESTONE_THUNDER_2 = registerBuilder("runestone_thunder_2", properties -> new ThunderRunestone(
            properties.component(TADataComponents.RUNESTONE_THUNDER, new RunestoneThunder(0.16f, 0.30f)), 2)).register();
    public static final DeferredHolder<Item, ThunderRunestone> RUNESTONE_THUNDER_3 = registerBuilder("runestone_thunder_3", properties -> new ThunderRunestone(
            properties.component(TADataComponents.RUNESTONE_THUNDER, new RunestoneThunder(0.31f, 0.45f)), 3)).register();
    public static final DeferredHolder<Item, ThunderRunestone> RUNESTONE_THUNDER_4 = registerBuilder("runestone_thunder_4", properties -> new ThunderRunestone(
            properties.component(TADataComponents.RUNESTONE_THUNDER, new RunestoneThunder(0.46f, 0.6f)), 4)).register();
    public static final DeferredHolder<Item, ThunderRunestone> RUNESTONE_THUNDER_5 = registerBuilder("runestone_thunder_5", properties -> new ThunderRunestone(
            properties.component(TADataComponents.RUNESTONE_THUNDER, new RunestoneThunder(1.0f, 1.0f)), 5)).register();
    public static final DeferredHolder<Item, DarknessRunestone> RUNESTONE_DARKNESS_1 = registerBuilder("runestone_darkness_1", properties -> new DarknessRunestone(
            properties.component(TADataComponents.RUNESTONE_DARKNESS, new RunestoneDarkness(0.4F, 0.5F, 0.15F, 0.25F)), 1, 3.0D)).register();
    public static final DeferredHolder<Item, DarknessRunestone> RUNESTONE_DARKNESS_2 = registerBuilder("runestone_darkness_2", properties -> new DarknessRunestone(
            properties.component(TADataComponents.RUNESTONE_DARKNESS, new RunestoneDarkness(0.3F, 0.4F, 0.25F, 0.35F)), 2, 4.0D)).register();
    public static final DeferredHolder<Item, DarknessRunestone> RUNESTONE_DARKNESS_3 = registerBuilder("runestone_darkness_3", properties -> new DarknessRunestone(
            properties.component(TADataComponents.RUNESTONE_DARKNESS, new RunestoneDarkness(0.3F, 0.4F, 0.35F, 0.5F)), 3, 5.0D)).register();
    public static final DeferredHolder<Item, DarknessRunestone> RUNESTONE_DARKNESS_4 = registerBuilder("runestone_darkness_4", properties -> new DarknessRunestone(
            properties.component(TADataComponents.RUNESTONE_DARKNESS, new RunestoneDarkness(0.4F, 0.5F, 0.5F, 0.6F)), 4, 6.0D)).register();
    public static final DeferredHolder<Item, DarknessRunestone> RUNESTONE_DARKNESS_5 = registerBuilder("runestone_darkness_5", properties -> new DarknessRunestone(
            properties.component(TADataComponents.RUNESTONE_DARKNESS, new RunestoneDarkness(1.0F, 1.0F, 0.6F, 0.7F)), 5, 6.0D)).register();
    public static final DeferredHolder<Item, StormRunestone> RUNESTONE_STORM_1 = registerBuilder("runestone_storm_1", properties -> new StormRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_STORM, new RunestoneStorm(0.05F, 0.1F, 1.0f, 1.5f)).component(TADataComponents.FIXED_SPEED_BOOST, 0.1F), 1)).register();
    public static final DeferredHolder<Item, StormRunestone> RUNESTONE_STORM_2 = registerBuilder("runestone_storm_2", properties -> new StormRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_STORM, new RunestoneStorm(0.1F, 0.15F, 2.0f, 1.5f)).component(TADataComponents.FIXED_SPEED_BOOST, 0.15F), 2)).register();
    public static final DeferredHolder<Item, StormRunestone> RUNESTONE_STORM_3 = registerBuilder("runestone_storm_3", properties -> new StormRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_STORM, new RunestoneStorm(0.15F, 0.2F, 2.0f, 2.0f)).component(TADataComponents.FIXED_SPEED_BOOST, 0.2F), 3)).register();
    public static final DeferredHolder<Item, StormRunestone> RUNESTONE_STORM_4 = registerBuilder("runestone_storm_4", properties -> new StormRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_STORM, new RunestoneStorm(0.2F, 0.3F, 3.0f, 2.0f)).component(TADataComponents.FIXED_SPEED_BOOST, 0.3F), 4)).register();
    public static final DeferredHolder<Item, StormRunestone> RUNESTONE_STORM_5 = registerBuilder("runestone_storm_5", properties -> new StormRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_STORM, new RunestoneStorm(0.3F, 0.4F, 4.0f, 2.0f)).component(TADataComponents.FIXED_SPEED_BOOST, 0.4F), 5)).register();
    public static final DeferredHolder<Item, NatureRunestone> RUNESTONE_NATURE_1 = registerBuilder("runestone_nature_1", properties -> new NatureRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_NATURE, new RunestoneNature(0.25F, 0.35F, 0.1F, 0.2F)).component(TADataComponents.FIXED_CHOP_BOOST, 0.35F), 1)).register();
    public static final DeferredHolder<Item, NatureRunestone> RUNESTONE_NATURE_2 = registerBuilder("runestone_nature_2", properties -> new NatureRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_NATURE, new RunestoneNature(0.36F, 0.55F, 0.21F, 0.3F)).component(TADataComponents.FIXED_CHOP_BOOST, 0.55F), 2)).register();
    public static final DeferredHolder<Item, NatureRunestone> RUNESTONE_NATURE_3 = registerBuilder("runestone_nature_3", properties -> new NatureRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_NATURE, new RunestoneNature(0.56F, 0.75F, 0.31F, 0.4F)).component(TADataComponents.FIXED_CHOP_BOOST, 0.75F), 3)).register();
    public static final DeferredHolder<Item, NatureRunestone> RUNESTONE_NATURE_4 = registerBuilder("runestone_nature_4", properties -> new NatureRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_NATURE, new RunestoneNature(0.76F, 1.0F, 0.41F, 0.5F)).component(TADataComponents.FIXED_CHOP_BOOST, 1.0F), 4)).register();
    public static final DeferredHolder<Item, NatureRunestone> RUNESTONE_NATURE_5 = registerBuilder("runestone_nature_5", properties -> new NatureRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_NATURE, new RunestoneNature(1.01F, 1.5F, 0.51F, 0.6F)).component(TADataComponents.FIXED_CHOP_BOOST, 1.5F), 5)).register();
    public static final DeferredHolder<Item, MountainRunestone> RUNESTONE_MOUNTAIN_1 = registerBuilder("runestone_mountain_1", properties -> new MountainRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_MOUNTAIN, new RunestoneMountain(0.25F, 0.35F, 0.1F, 0.2F)).component(TADataComponents.FIXED_MINING_BOOST, 0.35F), 1)).register();
    public static final DeferredHolder<Item, MountainRunestone> RUNESTONE_MOUNTAIN_2 = registerBuilder("runestone_mountain_2", properties -> new MountainRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_MOUNTAIN, new RunestoneMountain(0.36F, 0.55F, 0.21F, 0.3F)).component(TADataComponents.FIXED_MINING_BOOST, 0.55F), 2)).register();
    public static final DeferredHolder<Item, MountainRunestone> RUNESTONE_MOUNTAIN_3 = registerBuilder("runestone_mountain_3", properties -> new MountainRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_MOUNTAIN, new RunestoneMountain(0.56F, 0.75F, 0.31F, 0.4F)).component(TADataComponents.FIXED_MINING_BOOST, 0.75F), 3)).register();
    public static final DeferredHolder<Item, MountainRunestone> RUNESTONE_MOUNTAIN_4 = registerBuilder("runestone_mountain_4", properties -> new MountainRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_MOUNTAIN, new RunestoneMountain(0.76F, 1.0F, 0.41F, 0.5F)).component(TADataComponents.FIXED_MINING_BOOST, 1.0F), 4)).register();
    public static final DeferredHolder<Item, MountainRunestone> RUNESTONE_MOUNTAIN_5 = registerBuilder("runestone_mountain_5", properties -> new MountainRunestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE)
            .component(TADataComponents.RUNESTONE_MOUNTAIN, new RunestoneMountain(1.01F, 1.5F, 0.51F, 0.6F)).component(TADataComponents.FIXED_MINING_BOOST, 1.5F), 5)).register();
    public static final DeferredHolder<Item, Runestone> RUNESTONE_MOON = registerBuilder("runestone_moon", properties -> new Runestone(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE), 5)).defaultModel().register();

    /**
     * Misc
     */
    public static final DeferredHolder<Item, Item> MUSIC_DISC_AURORIAN_FOREST = simple("music_disc_aurorian_forest",
            () -> new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(TAJukeboxSongs.AURORIAN_FOREST));
    public static final DeferredHolder<Item, Item> MUSIC_DISC_MOONLIT_VEIL = simple("music_disc_moonlit_veil",
            () -> new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(TAJukeboxSongs.MOONLIT_VEIL));
    public static final DeferredHolder<Item, Item> CRYSTAL = simple("crystal",
            () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> BROKEN_OX_HORN = simple("broken_ox_horn", Item.Properties::new);
    public static final DeferredHolder<Item, Item> LUCKY_RABBIT_EAR = simple("lucky_rabbit_ear", Item.Properties::new);
    public static final DeferredHolder<Item, Item> AURORIAN_STEEL_NUGGET = simple("aurorian_steel_nugget", Item.Properties::new);
    public static final DeferredHolder<Item, Item> CERULEAN_NUGGET = simple("cerulean_nugget", Item.Properties::new);
    public static final DeferredHolder<Item, Item> AURORIAN_COAL_NUGGET = simple("aurorian_coal_nugget", Item.Properties::new);
    public static final DeferredHolder<Item, Item> MOONSTONE_NUGGET = simple("moonstone_nugget", Item.Properties::new);
    public static final DeferredHolder<Item, Item> AURORIAN_CRYSTAL = register("aurorian_crystal", AurorianCrystal::new);
    public static final DeferredHolder<Item, Item> AURORIAN_CHAIN = simple("aurorian_chain", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> AURORIAN_BERRY = simple("aurorian_berry", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> EQUINOX_MUSHROOM = simple("equinox_mushroom", () -> new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    public static final DeferredHolder<Item, Item> DREAM_DYEING_CRYSTAL_FRAGMENT = simple("dream_dyeing_crystal_fragment", () -> new Item.Properties()
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.DREAM_DYEING_CRYSTAL_FRAGMENT).component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE));
    public static final DeferredHolder<Item, Item> WORLD_SCROLL = register("world_scroll", WorldScroll::new);
    public static final DeferredHolder<Item, Item> BOOK_OF_SIN = register("book_of_sin", BookOfSin::new);
    public static final DeferredHolder<Item, Item> VAGRANT_NOTE = register("vagrant_note", VagrantNote::new);
    public static final DeferredHolder<Item, VagrantNotePage> TAYIR_NOTE_A = registerBuilder("tayir_note_a", properties -> new VagrantNotePage(1)).register();
    public static final DeferredHolder<Item, VagrantNotePage> ADVENTURER_STORY = registerBuilder("adventurer_story", properties -> new VagrantNotePage(2)).register();
    public static final DeferredHolder<Item, Item> DUNGEON_LOCATOR = register("dungeon_locator", DungeonLocator::new);
    public static final DeferredHolder<Item, Item> WEBBING = register("webbing", properties -> new SimpleThrowProjectProjectile(
            properties, SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.WEBBING::get, 0.5F));
    public static final DeferredHolder<Item, Item> LIVING_DIVINING_ROD = register("living_divining_rod", LivingDiviningRod::new);
    public static final DeferredHolder<Item, Item> LOCK_PICKS = register("lock_picks", properties -> new Item(
            properties.durability(10).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC)));
    public static final DeferredHolder<Item, Item> MOON_WATER_BUCKET = register("moon_water_bucket", properties -> new BucketItem(
            TAFluids.MOON_WATER_STILL.get(), properties.stacksTo(1).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> AURORIAN_WINGED_FISH_BUCKET = register("aurorian_winged_fish_bucket",
            properties -> new MobBucketItem(TAEntityTypes.AURORIAN_WINGED_FISH.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH,
                    properties.stacksTo(1).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> MOON_FISH_BUCKET = register("moon_fish_bucket",
            properties -> new MobBucketItem(TAEntityTypes.MOON_FISH.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH,
                    properties.stacksTo(1).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)));
    public static final DeferredHolder<Item, Item> DEVELOPER_GIFT = register("developer_gift", DeveloperGift::new);
    public static final DeferredHolder<Item, Item> CRIMSON_PACT_PENDANT = register("crimson_pact_pendant",
            properties -> new CrimsonPactPendant(properties.stacksTo(1).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC)));

    /**
     * Developer Item
     */
    public static final DeferredHolder<Item, Item> SLEEPING_BLACK_TEA = simple("sleeping_black_tea", () -> new Item.Properties()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(TAMobEffects.WARM, 100), 1.0F).build())
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.MYTHICAL)
            .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
            .component(TADataComponents.DEVELOPER, Unit.INSTANCE));
    public static final DeferredHolder<Item, Item> WHITE_CHOCOLATE = simple("white_chocolate", () -> new Item.Properties()
            .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(TAMobEffects.PARALYSIS, 2400), 1.0F).build())
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.WHITE_CHOCOLATE)
            .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
            .component(TADataComponents.DEVELOPER, Unit.INSTANCE));
    public static final DeferredHolder<Item, Item> CAT_BELL = register("cat_bell", CatBell::new);
    public static final DeferredHolder<Item, Item> RED_BOOK = register("red_book", RedBook::new);
    public static final DeferredHolder<Item, Item> RED_BOOK_RING = simple("red_book_ring", () -> new Item.Properties()
            .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RED_BOOK)
            .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
            .component(TADataComponents.DEVELOPER, Unit.INSTANCE));
    public static final DeferredHolder<Item, SwordItem> TSLAT_SWORD = toolBuilder("tslat_sword",
            properties -> new SwordItem(TAToolTiers.TSLAT, properties
                    .attributes(SwordItem.createAttributes(TAToolTiers.TSLAT, 3, 1.9F))
                    .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
                    .component(TADataComponents.DEVELOPER, Unit.INSTANCE)
                    .component(TADataComponents.KILL_COUNT, 0))).tag(ItemTags.SWORDS).register();

    /**
     * Spawn Egg
     */
    public static final DeferredHolder<Item, DeferredSpawnEggItem> BREAD_BEAST_SPAWN_EGG = spawnEgg(TAEntityTypes.BREAD_BEAST, 0xc4b4a1, 0x734b41);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> ICEFIELD_DEER_SPAWN_EGG = spawnEgg(TAEntityTypes.ICEFIELD_DEER, 0xadb0c4, 0x5f4569);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> BLUE_TAIL_WOLF_SPAWN_EGG = spawnEgg(TAEntityTypes.BLUE_TAIL_WOLF, 0xe1eff5, 0x6381f7);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> MOON_FISH_SPAWN_EGG = spawnEgg(TAEntityTypes.MOON_FISH, 0xd1ccc5, 0x594a48);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> AURORIAN_WINGED_FISH_SPAWN_EGG = spawnEgg(TAEntityTypes.AURORIAN_WINGED_FISH, 0x4581d5, 0x1b4a8a);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> AURORIAN_RABBIT_SPAWN_EGG = spawnEgg(TAEntityTypes.AURORIAN_RABBIT, 0xc2e5e5, 0x43a2ec);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> AURORIAN_SHEEP_SPAWN_EGG = spawnEgg(TAEntityTypes.AURORIAN_SHEEP, 0x97b4f2, 0x7197ea);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> AURORIAN_PIG_SPAWN_EGG = spawnEgg(TAEntityTypes.AURORIAN_PIG, 0xc6dfff, 0x5d6f93);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> AURORIAN_COW_SPAWN_EGG = spawnEgg(TAEntityTypes.AURORIAN_COW, 0x578a91, 0x454c5b);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> AURORIAN_PIXIE_SPAWN_EGG = spawnEgg(TAEntityTypes.AURORIAN_PIXIE, 0x9cc6f1, 0x88b7e3);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> AURORIAN_SLIME_SPAWN_EGG = spawnEgg(TAEntityTypes.AURORIAN_SLIME, 0x151028, 0x43a2ec);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> AURORIAN_VILLAGER_SPAWN_EGG = spawnEgg(TAEntityTypes.AURORIAN_VILLAGER, 0x9e9e9e, 0x4f4f4f);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> DISTURBED_HOLLOW_SPAWN_EGG = spawnEgg(TAEntityTypes.DISTURBED_HOLLOW, 0xade0f5, 0x272727);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> UNDEAD_KNIGHT_SPAWN_EGG = spawnEgg(TAEntityTypes.UNDEAD_KNIGHT, 0x5c7394, 0x181b1e);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> SPIRIT_SPAWN_EGG = spawnEgg(TAEntityTypes.SPIRIT, 0xb0b6bc, 0x303131);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> MOON_ACOLYTE_SPAWN_EGG = spawnEgg(TAEntityTypes.MOON_ACOLYTE, 0x0270af, 0x191919);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> SPIDERLING_SPAWN_EGG = spawnEgg(TAEntityTypes.SPIDERLING, 0x1efefe, 0x0f1018);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> SPIDERLING_CRYSTAL_SHELL_SPAWN_EGG = spawnEgg(TAEntityTypes.SPIDERLING_CRYSTAL_SHELL, 0xd1ffdf, 0x363675);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> SPIDERLING_WALL_CLIMBER_SPAWN_EGG = spawnEgg(TAEntityTypes.SPIDERLING_WALL_CLIMBER, 0xd1ffdf, 0x363675);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> GIANT_CRYSTAL_SPIDER_SPAWN_EGG = spawnEgg(TAEntityTypes.GIANT_CRYSTAL_SPIDER, 0xd1ffdf, 0x363675);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> RUNE_SPIDER_SPAWN_EGG = spawnEgg(TAEntityTypes.RUNE_SPIDER, 0xffc7f0, 0x8e69cf);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> CRYSTALLINE_SPRITE_SPAWN_EGG = spawnEgg(TAEntityTypes.CRYSTALLINE_SPRITE, 0xf5e2fc, 0xf0c9fd);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> CAVE_DWELLER_SPAWN_EGG = spawnEgg(TAEntityTypes.CAVE_DWELLER, 0x5e6b7d, 0xdc54f7);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> ROCK_HAMMER_SPAWN_EGG = spawnEgg(TAEntityTypes.ROCK_HAMMER, 0xe3e0d1, 0x45332e);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> TONG_SCORPION_SPAWN_EGG = spawnEgg(TAEntityTypes.TONG_SCORPION, 0x4f4334, 0x211a18);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> SNOW_TUNDRA_GIANT_CRAB_SPAWN_EGG = spawnEgg(TAEntityTypes.SNOW_TUNDRA_GIANT_CRAB, 0xd8deed, 0x2347d9);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> FLOWER_LEECH_SPAWN_EGG = spawnEgg(TAEntityTypes.FLOWER_LEECH, 0xcd92d1, 0x362d23);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> FORGOTTEN_MAGIC_BOOK_SPAWN_EGG = spawnEgg(TAEntityTypes.FORGOTTEN_MAGIC_BOOK, 0xd9d2c5, 0x3a2f40);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> HYPHA_WALKING_MUSHROOM_SPAWN_EGG = spawnEgg(TAEntityTypes.HYPHA_WALKING_MUSHROOM, 0xededdf, 0xc48a80);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> MOONLIGHT_KNIGHT_SPAWN_EGG = spawnEgg(TAEntityTypes.MOONLIGHT_KNIGHT, 0xb8bdbe, 0x0955a8);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> RUNESTONE_KEEPER_SPAWN_EGG = spawnEgg(TAEntityTypes.RUNESTONE_KEEPER, 0xccc0e7, 0x550098);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> SPIDER_MOTHER_SPAWN_EGG = spawnEgg(TAEntityTypes.SPIDER_MOTHER, 0x595d70, 0x0f1018);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> MOON_QUEEN_SPAWN_EGG = spawnEgg(TAEntityTypes.MOON_QUEEN, 0xff82d4, 0x313d4b);
    public static final DeferredHolder<Item, DeferredSpawnEggItem> SELENA_SPAWN_EGG = spawnEgg(TAEntityTypes.SELENA, 0xf5e0c7, 0xc78f6e);

    /**
     * Block Item
     * */
    public static final DeferredHolder<Item, PlaceOnWaterBlockItem> AURORIAN_LILY_PAD = registerBuilder("aurorian_lily_pad",
            properties -> new PlaceOnWaterBlockItem(TABlocks.AURORIAN_LILY_PAD.get(), properties.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON))).register();
    public static final DeferredHolder<Item, PlaceOnWaterBlockItem> AURORIAN_WATER_MUSHROOM = registerBuilder("aurorian_water_mushroom",
            properties -> new PlaceOnWaterBlockItem(TABlocks.AURORIAN_WATER_MUSHROOM.get(), properties.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON))).register();
    public static final DeferredHolder<Item, AurorianChestItem> AURORIAN_CHEST = registerBuilder("aurorian_chest", AurorianChestItem::new).register();
    public static final DeferredHolder<Item, AstrologyTable> ASTROLOGY_TABLE = registerBuilder("astrology_table", AstrologyTable::new).register();
    public static final DeferredHolder<Item, SacrificeTable> SACRIFICE_TABLE = registerBuilder("sacrifice_table", SacrificeTable::new).register();
    public static final DeferredHolder<Item, AbstractLunarCrystal> LUNAR_SOURCE_PRISM = registerBuilder("lunar_source_prism", properties -> new AbstractLunarCrystal(TABlocks.LUNAR_SOURCE_PRISM.get())).register();
    public static final DeferredHolder<Item, AbstractLunarCrystal> LUNAR_DEFLECTOR = registerBuilder("lunar_deflector", properties -> new AbstractLunarCrystal(TABlocks.LUNAR_DEFLECTOR.get())).register();
    public static final DeferredHolder<Item, AbstractLunarCrystal> LUNAR_SPLITTER = registerBuilder("lunar_splitter", properties -> new AbstractLunarCrystal(TABlocks.LUNAR_SPLITTER.get())).register();
    public static final DeferredHolder<Item, AbstractLunarCrystal> RECEIVING_CRYSTAL = registerBuilder("receiving_crystal", properties -> new AbstractLunarCrystal(TABlocks.RECEIVING_CRYSTAL.get())).register();
    public static final DeferredHolder<Item, StandingAndWallBlockItem> MOON_TORCH = registerBuilder("moon_torch", properties -> new StandingAndWallBlockItem(
            TABlocks.MOON_TORCH.get(), TABlocks.MOON_WALL_TORCH.get(), properties.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON), Direction.DOWN)).register();
    public static final DeferredHolder<Item, StandingAndWallBlockItem> SILENT_WOOD_TORCH = registerBuilder("silent_wood_torch", properties -> new StandingAndWallBlockItem(
            TABlocks.SILENT_WOOD_TORCH.get(), TABlocks.SILENT_WOOD_WALL_TORCH.get(), properties.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON), Direction.DOWN)).register();
    public static final DeferredHolder<Item, SignItem> SILENT_WOOD_SIGN = registerBuilder("silent_wood_sign",
            properties -> new SignItem(properties.stacksTo(16).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON),
                    TABlocks.SILENT_WOOD_SIGN.get(), TABlocks.SILENT_WOOD_WALL_SIGN.get())).defaultModel().register();
    public static final DeferredHolder<Item, SignItem> WEEPING_WILLOW_WOOD_SIGN = registerBuilder("weeping_willow_wood_sign",
            properties -> new SignItem(properties.stacksTo(16).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON),
                    TABlocks.WEEPING_WILLOW_WOOD_SIGN.get(), TABlocks.WEEPING_WILLOW_WOOD_WALL_SIGN.get())).defaultModel().register();
    public static final DeferredHolder<Item, SignItem> CURTAIN_WOOD_SIGN = registerBuilder("curtain_wood_sign",
            properties -> new SignItem(properties.stacksTo(16).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON),
                    TABlocks.CURTAIN_WOOD_SIGN.get(), TABlocks.CURTAIN_WOOD_WALL_SIGN.get())).defaultModel().register();
    public static final DeferredHolder<Item, SignItem> CURSED_FROST_WOOD_SIGN = registerBuilder("cursed_frost_wood_sign",
            properties -> new SignItem(properties.stacksTo(16).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON),
                    TABlocks.CURSED_FROST_WOOD_SIGN.get(), TABlocks.CURSED_FROST_WOOD_WALL_SIGN.get())).defaultModel().register();
    public static final DeferredHolder<Item, HangingSignItem> SILENT_WOOD_HANGING_SIGN = registerBuilder("silent_wood_hanging_sign",
            properties -> new HangingSignItem(TABlocks.SILENT_WOOD_HANGING_SIGN.get(), TABlocks.SILENT_WOOD_WALL_HANGING_SIGN.get(),
                    properties.stacksTo(16).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON))).defaultModel().register();
    public static final DeferredHolder<Item, HangingSignItem> WEEPING_WILLOW_WOOD_HANGING_SIGN = registerBuilder("weeping_willow_wood_hanging_sign",
            properties -> new HangingSignItem(TABlocks.WEEPING_WILLOW_WOOD_HANGING_SIGN.get(), TABlocks.WEEPING_WILLOW_WOOD_WALL_HANGING_SIGN.get(),
                    properties.stacksTo(16).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON))).defaultModel().register();
    public static final DeferredHolder<Item, HangingSignItem> CURTAIN_WOOD_HANGING_SIGN = registerBuilder("curtain_wood_hanging_sign",
            properties -> new HangingSignItem(TABlocks.CURTAIN_WOOD_HANGING_SIGN.get(), TABlocks.CURTAIN_WOOD_WALL_HANGING_SIGN.get(),
                    properties.stacksTo(16).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON))).defaultModel().register();
    public static final DeferredHolder<Item, HangingSignItem> CURSED_FROST_WOOD_HANGING_SIGN = registerBuilder("cursed_frost_wood_hanging_sign",
            properties -> new HangingSignItem(TABlocks.CURSED_FROST_WOOD_HANGING_SIGN.get(), TABlocks.CURSED_FROST_WOOD_WALL_HANGING_SIGN.get(),
                    properties.stacksTo(16).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON))).defaultModel().register();
    
}