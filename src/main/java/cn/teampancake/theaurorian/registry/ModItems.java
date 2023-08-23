package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.*;
import cn.teampancake.theaurorian.config.AurorianConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Random;

import static cn.teampancake.theaurorian.common.items.ModArmorMaterials.*;
import static cn.teampancake.theaurorian.utils.ModItemRegUtils.food;
import static cn.teampancake.theaurorian.utils.ModItemRegUtils.normal;
import static net.minecraft.world.item.ArmorItem.Type.*;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AurorianMod.MOD_ID);

    public static final RegistryObject<Item> CUP = normal("cup", true);
    public static final RegistryObject<Item> AURORIAN_COAL = normal("aurorian_coal", true);
    public static final RegistryObject<Item> AURORIANITE_INGOT = normal("aurorianite_ingot", true);
    public static final RegistryObject<Item> AURORIAN_STEEL = normal("aurorian_steel", true);
    public static final RegistryObject<Item> CERULEAN_INGOT = normal("cerulean_ingot", true);
    public static final RegistryObject<Item> CRYSTALLINE_INGOT = normal("crystalline_ingot", true);
    public static final RegistryObject<Item> MOONSTONE_INGOT = normal("moonstone_ingot", true);
    public static final RegistryObject<Item> UMBRA_INGOT = normal("umbra_ingot", true);
    public static final RegistryObject<Item> LAVENDER = normal("lavender", true);
    public static final RegistryObject<Item> MOON_TEMPLE_CELL_KEY_FRAGMENT = normal("moon_temple_cell_key_fragment", true);
    public static final RegistryObject<Item> AURORIAN_STEEL_NUGGET = normal("aurorian_steel_nugget", true);
    public static final RegistryObject<Item> CERULEAN_NUGGET = normal("cerulean_nugget", true);
    public static final RegistryObject<Item> AURORIAN_COAL_NUGGET = normal("aurorian_coal_nugget", true);
    public static final RegistryObject<Item> MOONSTONE_NUGGET = normal("moonstone_nugget", true);
    public static final RegistryObject<Item> PLANTFIBER = normal("plant_fiber", true);
    public static final RegistryObject<Item> SCRAP_AURORIANITE = normal("scrap_aurorianite", true);
    public static final RegistryObject<Item> SCRAP_CRYSTALLINE = normal("scrap_crystalline", true);
    public static final RegistryObject<Item> SCRAP_UMBRA = normal("scrap_umbra", true);
    public static final RegistryObject<Item> SPECTRAL_SILK = normal("spectral_silk", true);
    public static final RegistryObject<Item> TROPHY_KEEPER = normal("trophy_keeper", true);
    public static final RegistryObject<Item> TROPHY_MOON_QUEEN = normal("trophy_moon_queen", true);
    public static final RegistryObject<Item> TROPHY_SPIDER = normal("trophy_spider", true);
    public static final RegistryObject<Item> SILK_BERRY_JAM = food("silk_berry_jam", 2, 0.5F, false);
    public static final RegistryObject<Item> SILK_BERRY_JAM_SANDWICH = food("silk_berry_jam_sandwich", 6, 0.9F, true);
    public static final RegistryObject<Item> AURORIAN_PORK = food("aurorian_pork", 3, 0.3F, false);
    public static final RegistryObject<Item> AURORIAN_BACON = food("aurorian_bacon", 2, 0.8F, true);
    public static final RegistryObject<Item> COOKED_AURORIAN_PORK = food("cooked_aurorian_pork", 8, 0.8F, true);
    public static final RegistryObject<Item> AURORIAN_SLIMEBALL = food("aurorian_slimeball", 1, 0.2F, false);
    public static final RegistryObject<Item> SILK_SHROOM_STEW = food("silk_shroom_stew", 6, 1F, false);
    public static final RegistryObject<Item> LAVENDER_BREAD = food("lavender_bread", 4, 0.4F, false);
    public static final RegistryObject<Item> SOULLESS_FLESH = food("soulless_flesh", 2, 0.1F, false);
    //Sword and Tools
    public static final RegistryObject<Item> AURORIAN_STEEL_SWORD = ITEMS.register("aurorian_steel_sword",
            () -> new SwordItem(ModToolTiers.AURORIAN_STEEL, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STEEL_SHOVEL = ITEMS.register("aurorian_steel_shovel",
            () -> new ShovelItem(ModToolTiers.AURORIAN_STEEL, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STEEL_PICKAXE = ITEMS.register("aurorian_steel_pickaxe",
            () -> new PickaxeItem(ModToolTiers.AURORIAN_STEEL, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STEEL_AXE = ITEMS.register("aurorian_steel_axe",
            () -> new AxeItem(ModToolTiers.AURORIAN_STEEL, 6.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STEEL_HOE = ITEMS.register("aurorian_steel_hoe",
            () -> new HoeItem(ModToolTiers.AURORIAN_STEEL, -2, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STONE_SWORD = ITEMS.register("aurorian_stone_sword",
            () -> new SwordItem(ModToolTiers.AURORIAN_STONE, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STONE_SHOVEL = ITEMS.register("aurorian_stone_shovel",
            () -> new ShovelItem(ModToolTiers.AURORIAN_STONE, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STONE_PICKAXE = ITEMS.register("aurorian_stone_pickaxe", AurorianStonePickaxe::new);
    public static final RegistryObject<Item> AURORIAN_STONE_AXE = ITEMS.register("aurorian_stone_axe", AurorianStoneAxe::new);
    public static final RegistryObject<Item> AURORIAN_STONE_HOE = ITEMS.register("aurorian_stone_hoe",
            () -> new HoeItem(ModToolTiers.AURORIAN_STONE, -1, -1.0F, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STONE_SICKLE = ITEMS.register("aurorian_stone_sickle",
            () -> new ShearsItem(new Item.Properties().durability(150)));
    public static final RegistryObject<Item> AURORIANITE_PICKAXE = ITEMS.register("aurorianite_pickaxe",
            () -> new PickaxeItem(ModToolTiers.AURORIANITE, 1, -3.0F, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> AURORIANITE_AXE = ITEMS.register("aurorianite_axe", AurorianiteAxe::new);
    //Armor Item
    public static final RegistryObject<Item> AURORIAN_STEEL_HELMET = ITEMS.register("aurorian_steel_helmet", () -> new ArmorItem(ModArmorMaterials.AURORIAN_STEEL, HELMET, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STEEL_CHESTPLATE = ITEMS.register("aurorian_steel_chestplate", () -> new ArmorItem(ModArmorMaterials.AURORIAN_STEEL, CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STEEL_LEGGINGS = ITEMS.register("aurorian_steel_leggings", () -> new ArmorItem(ModArmorMaterials.AURORIAN_STEEL, LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> AURORIAN_STEEL_BOOTS = ITEMS.register("aurorian_steel_boots", () -> new ArmorItem(ModArmorMaterials.AURORIAN_STEEL, BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> CERULEAN_HELMET = ITEMS.register("cerulean_helmet", () -> new ArmorItem(CERULEAN, HELMET, new Item.Properties()));
    public static final RegistryObject<Item> CERULEAN_CHESTPLATE = ITEMS.register("cerulean_chestplate", () -> new ArmorItem(CERULEAN, CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> CERULEAN_LEGGINGS = ITEMS.register("cerulean_leggings", () -> new ArmorItem(CERULEAN, LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> CERULEAN_BOOTS = ITEMS.register("cerulean_boots", () -> new ArmorItem(CERULEAN, BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> KNIGHT_HELMET = ITEMS.register("knight_helmet", () -> new ArmorItem(KNIGHT, HELMET, new Item.Properties()));
    public static final RegistryObject<Item> KNIGHT_CHESTPLATE = ITEMS.register("knight_chestplate", () -> new ArmorItem(KNIGHT, CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> KNIGHT_LEGGINGS = ITEMS.register("knight_leggings", () -> new ArmorItem(KNIGHT, LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> KNIGHT_BOOTS = ITEMS.register("knight_boots", () -> new ArmorItem(KNIGHT, BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> SPECTRAL_HELMET = ITEMS.register("spectral_helmet", () -> new ArmorItem(SPECTRAL, HELMET, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SPECTRAL_CHESTPLATE = ITEMS.register("spectral_chestplate", () -> new ArmorItem(SPECTRAL, CHESTPLATE, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SPECTRAL_LEGGINGS = ITEMS.register("spectral_leggings", () -> new ArmorItem(SPECTRAL, LEGGINGS, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SPECTRAL_BOOTS = ITEMS.register("spectral_boots", () -> new ArmorItem(SPECTRAL, BOOTS, new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> ABSORPTION_ORB = ITEMS.register("absorption_orb", AbsorptionOrbItem::new);
    public static final RegistryObject<Item> DARK_AMULET = normal("dark_amulet", true);
    public static final RegistryObject<Item> KEEPER_AMULET= normal("keeper_amulet", true);
    public static final RegistryObject<Item> AURORIANITE_SWORD = ITEMS.register("aurorianite_sword", AurorianiteSword::new);
    public static final RegistryObject<Item> CERULEAN_SHIELD=ITEMS.register("cerulean_shield",CeruleanShield::new);
    public static final RegistryObject<Item> CRYSTALLINE_PICKAXE=ITEMS.register("crystalline_pickaxe",CrystallinePickaxe::new);

    public static final RegistryObject<Item> WEEPING_WILLOW_SAP = ITEMS.register("weeping_willow_sap",WeepingWillowSap::new);
    public static final RegistryObject<Item> UMBRA_SWORD = ITEMS.register("umbra_sword", UmbraSword::new);
    public static final RegistryObject<Item> UMBRA_SHIELD = ITEMS.register("umbra_shield", UmbraShield::new);
    public static final RegistryObject<Item> UMBRA_PICKAXE = ITEMS.register("umbra_pickaxe", UmbraPickaxe::new);
    public static final RegistryObject<Item> TEA_CUP = normal("tea_cup", false);
    public static final RegistryObject<Item> BEPSI = ITEMS.register("bepsi", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(0).saturationMod(0).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600), 1.0F).build())));
    public static final RegistryObject<Item> LAVENDER_TEA = ITEMS.register("lavender_tea", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(0).saturationMod(0).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300 * AurorianConfig.CONFIG_TEA_EFFECT_DURATION_MULIPLIER.get()), 1.0F).build())));
    public static final RegistryObject<Item> SILKBERRY_TEA = ITEMS.register("silkberry_tea", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(0).saturationMod(0).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100 * AurorianConfig.CONFIG_TEA_EFFECT_DURATION_MULIPLIER.get()), 1.0F).build())));
    public static final RegistryObject<Item> SEEDY_TEA = ITEMS.register("seedy_tea", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(0).saturationMod(0).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200 * AurorianConfig.CONFIG_TEA_EFFECT_DURATION_MULIPLIER.get()), 1.0F).build())));

    public static final RegistryObject<Item> PETUNIA_TEA = ITEMS.register("petunia_tea", () -> new TeaFood(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(0).saturationMod(0).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300 * AurorianConfig.CONFIG_TEA_EFFECT_DURATION_MULIPLIER.get()), 1.0F).build())));

    public static final RegistryObject<Item> WEBBING = ITEMS.register("webbing", () -> new SimpleThrowProjectProjectile(new Item.Properties(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, EntityType.SNOWBALL, 0.5F, 0.4F / (new Random().nextFloat() * 0.4F + 0.8F)));
    public static final RegistryObject<Item> STICKY_SPIKER = ITEMS.register("sticky_spiker", () -> new SimpleThrowProjectProjectile(new Item.Properties().rarity(Rarity.EPIC), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, EntityType.SNOWBALL, 1.5F, 1.0F));
    public static final RegistryObject<Item> AURORIAN_STEEL_HELMET_SPIKED = ITEMS.register("aurorian_steel_helmet_spiked", SpikedItemArmor::new);

}