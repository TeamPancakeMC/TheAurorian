package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.common.datamaps.AlchemyTableMaterial;
import cn.teampancake.theaurorian.common.registry.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.BiomeVillagerType;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

/** @noinspection deprecation*/
public class TADataMapProvider extends DataMapProvider {

    public TADataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        // Compostable
        this.addCompostable(0.3F, TABlocks.AURORIAN_GRASS.get());
        this.addCompostable(0.3F, TABlocks.AURORIAN_GRASS_LIGHT.get());
        this.addCompostable(0.3F, TABlocks.SILENT_TREE_SAPLING.get());
        this.addCompostable(0.3F, TABlocks.SILENT_TREE_LEAVES.get());
        this.addCompostable(0.3F, TABlocks.WEEPING_WILLOW_LEAVES.get());
        this.addCompostable(0.3F, TABlocks.CURTAIN_TREE_LEAVES.get());
        this.addCompostable(0.3F, TABlocks.CURSED_FROST_TREE_LEAVES.get());
        this.addCompostable(0.65F, TABlocks.DREAMSCAPE_PISTIL.get());
        this.addCompostable(0.65F, TABlocks.FROST_TEARS_FLOWER.get());
        this.addCompostable(0.65F, TABlocks.NEBULA_BLOSSOM_CLUSTER.get());
        this.addCompostable(0.65F, TABlocks.MOON_FROST_FLOWER.get());
        this.addCompostable(0.65F, TABlocks.VOID_CANDLE_FLOWER.get());
        this.addCompostable(0.65F, TABlocks.EQUINOX_FLOWER.get());
        this.addCompostable(0.65F, TABlocks.WICK_GRASS.get());
        this.addCompostable(0.65F, TABlocks.LAVENDER_PLANT.get());
        this.addCompostable(0.65F, TABlocks.PETUNIA_PLANT.get());
        this.addCompostable(0.65F, TABlocks.ICE_CALENDULA.get());
        this.addCompostable(0.65F, TABlocks.WINTER_ROOT.get());
        this.addCompostable(0.3F, TAItems.WEEPING_WILLOW_SAP.get());
        this.addCompostable(0.3F, TAItems.LAVENDER_SEEDS.get());
        this.addCompostable(0.3F, TAItems.SILK_BERRY.get());
        this.addCompostable(0.3F, TAItems.BLUEBERRY.get());
        this.addCompostable(0.85F, TAItems.LAVENDER_BREAD.get());
        this.addCompostable(0.65F, TAItems.TALL_WICK_GRASS.get());
        // Furnace Fuels
        this.addFurnaceFuel(TABlocks.AURORIAN_CRAFTING_TABLE.get(), 300);
        this.addFurnaceFuel(TABlocks.SILENT_WOOD_LADDER.get(), 300);
        this.addFurnaceFuel(TAItems.SILENT_WOOD_STICK.get(), 100);
        this.addFurnaceFuel(TAItems.SILENT_WOOD_BOW.get(), 300);
        this.addFurnaceFuel(TAItems.AURORIAN_COAL.get(), 1500);
        // Villager Type
        this.addVillagerType(TABiomes.AURORIAN_FOREST, TAVillagerProfession.AURORIAN_FOREST.get());
        // Alchemy Table Usable Effects
        this.addUsableEffects(MobEffects.MOVEMENT_SPEED, "!10 & !4 & 5*2+0 & >1 | !7 & !4 & 5*2+0 & >1");
        this.addUsableEffects(MobEffects.MOVEMENT_SLOWDOWN, "10 & 7 & !4 & 7+5+1-0");
        this.addUsableEffects(MobEffects.DIG_SPEED, "2 & 12+2+6-1-7 & <8");
        this.addUsableEffects(MobEffects.DIG_SLOWDOWN, "!2 & !1*2-9 & 14-5");
        this.addUsableEffects(MobEffects.DAMAGE_BOOST, "9 & 3 & 9+4+5 & <11");
        this.addUsableEffects(MobEffects.HEAL, "11 & <6");
        this.addUsableEffects(MobEffects.HARM, "!11 & 1 & 10 & !7");
        this.addUsableEffects(MobEffects.JUMP, "8 & 2+0 & <5");
        this.addUsableEffects(MobEffects.CONFUSION, "8*2-!7+4-11 & !2 | 13 & 11 & 2*3-1-5");
        this.addUsableEffects(MobEffects.REGENERATION, "!14 & 13*3-!0-!5-8");
        this.addUsableEffects(MobEffects.DAMAGE_RESISTANCE, "10 & 4 & 10+5+6 & <9");
        this.addUsableEffects(MobEffects.FIRE_RESISTANCE, "14 & !5 & 6-!1 & 14+13+12");
        this.addUsableEffects(MobEffects.WATER_BREATHING, "0+1+12 & !6 & 10 & !11 & !13");
        this.addUsableEffects(MobEffects.INVISIBILITY, "2+5+13-0-4 & !7 & !1 & >5");
        this.addUsableEffects(MobEffects.BLINDNESS, "9 & !1 & !5 & !3 & =3");
        this.addUsableEffects(MobEffects.NIGHT_VISION, "8*2-!7 & 5 & !0 & >3");
        this.addUsableEffects(MobEffects.HUNGER, ">4>6>8-3-8+2");
        this.addUsableEffects(MobEffects.WEAKNESS, "=1>5>7>9+3-7-2-11 & !10 & !0");
        this.addUsableEffects(MobEffects.POISON, "12+9 & !13 & !0");
        this.addUsableEffects(MobEffects.SLOW_FALLING, "3+8+11 & !2 & 7+4 & <10");
        this.addUsableEffects(MobEffects.CONDUIT_POWER, "1+6+13 & !9 & 5+12 & >7");
        this.addUsableEffects(MobEffects.DOLPHINS_GRACE, "4+9+14 & !1 & 8+3 & <12");
        this.addUsableEffects(MobEffects.BAD_OMEN, "7+12+0 & !5 & 11+6 & >3");
        this.addUsableEffects(MobEffects.HERO_OF_THE_VILLAGE, "2+7+10 & !13 & 1+14 & <6");
        this.addUsableEffects(MobEffects.DARKNESS, "5+10+1 & !8 & 0+13 & >4");
        this.addUsableEffects(MobEffects.LEVITATION, "6+11+2 & !3 & 9+0 & <14");
        this.addUsableEffects(MobEffects.GLOWING, "13+4+7 & !10 & 2+15 & >1");
        this.addUsableEffects(MobEffects.ABSORPTION, "0+5+8 & !12 & 3+14 & <9");
        this.addUsableEffects(MobEffects.SATURATION, "1+6+9 & !11 & 4+15 & <8");
        this.addUsableEffects(MobEffects.LUCK, "2+7+10 & !13 & 5+0 & <12");
        this.addUsableEffects(MobEffects.UNLUCK, "3+8+11 & !14 & 6+1 & <13");
        this.addUsableEffects(MobEffects.HEALTH_BOOST, "7+12+0 & !3 & 10+5 & <2");
        this.addUsableEffects(MobEffects.WITHER, "8+13+1 & !4 & 11+6 & <3");
        this.addUsableEffects(MobEffects.WIND_CHARGED, "4+9+12 & !0 & 7+2 & <15");
        this.addUsableEffects(MobEffects.WEAVING, "5+10+13 & !1 & 8+3 & <0");
        this.addUsableEffects(MobEffects.OOZING, "6+11+14 & !2 & 9+4 & <1");
        this.addUsableEffects(MobEffects.INFESTED, "7+12+15 & !3 & 10+5 & <2");
        // Alchemy Table Amplifier Effects
        this.addAmplifierEffects(MobEffects.MOVEMENT_SPEED, "7+!3-!1");
        this.addAmplifierEffects(MobEffects.DIG_SPEED, "1+0-!11");
        this.addAmplifierEffects(MobEffects.DAMAGE_BOOST, "2+7-!12");
        this.addAmplifierEffects(MobEffects.HEAL, "11+!0-!1-!14");
        this.addAmplifierEffects(MobEffects.HARM, "!11-!14+!0-!1");
        this.addAmplifierEffects(MobEffects.DAMAGE_RESISTANCE, "12-!2");
        this.addAmplifierEffects(MobEffects.POISON, "14>5");
        this.addAmplifierEffects(MobEffects.SLOW_FALLING, "3+8-!6");
        this.addAmplifierEffects(MobEffects.CONDUIT_POWER, "5+10-!2");
        this.addAmplifierEffects(MobEffects.DOLPHINS_GRACE, "7+12-!4");
        this.addAmplifierEffects(MobEffects.BAD_OMEN, "1+6-!9");
        this.addAmplifierEffects(MobEffects.DARKNESS, "8+13-!1");
        this.addAmplifierEffects(MobEffects.LEVITATION, "2+9-!14");
        this.addAmplifierEffects(MobEffects.GLOWING, "6+15-!3");
        this.addAmplifierEffects(MobEffects.ABSORPTION, "0+5-!12");
        this.addAmplifierEffects(MobEffects.SATURATION, "10+15-!8");
        this.addAmplifierEffects(MobEffects.LUCK, "3+8-!13");
        this.addAmplifierEffects(MobEffects.UNLUCK, "7+12-!4");
        this.addAmplifierEffects(MobEffects.WITHER, "2+9-!14");
        this.addAmplifierEffects(MobEffects.HEALTH_BOOST, "5+10-!9");
        this.addAmplifierEffects(MobEffects.HERO_OF_THE_VILLAGE, "4+11-!7");
        // Alchemy Table Ingredients
        this.addAlchemyIngredient(Items.SUGAR, "+0");
        this.addAlchemyIngredient(Items.GHAST_TEAR, "+11");
        this.addAlchemyIngredient(Items.BLAZE_POWDER, "+14");
        this.addAlchemyIngredient(Items.MAGMA_CREAM, "+14+6+1");
        this.addAlchemyIngredient(Items.SPIDER_EYE, "+10+7+5");
        this.addAlchemyIngredient(Items.GLISTERING_MELON_SLICE, "+2+7+10");
        this.addAlchemyIngredient(Items.FERMENTED_SPIDER_EYE, "+14+9");
        this.addAlchemyIngredient(Items.PHANTOM_MEMBRANE, "+3+8+11");
        this.addAlchemyIngredient(Items.GLOWSTONE_DUST, "+1+6+13");
        this.addAlchemyIngredient(Items.GUNPOWDER, "+4+9+14");
        this.addAlchemyIngredient(Items.RABBIT_FOOT, "+7+12+0");
        this.addAlchemyIngredient(Items.PUFFERFISH, "+5+10+1");
        this.addAlchemyIngredient(Items.GOLDEN_CARROT, "+6+11+2");
        this.addAlchemyIngredient(Items.TURTLE_HELMET, "+13+4+7");
        this.addAlchemyIngredient(Items.SLIME_BLOCK, "+1+6+9");
        this.addAlchemyIngredient(Items.STONE, "+0+5+8");
        this.addAlchemyIngredient(Items.COBWEB, "+2+7+10");
        this.addAlchemyIngredient(Items.REDSTONE, "+3+8+11");
        this.addAlchemyIngredient(Items.BREEZE_ROD, "+4+9+12");
        this.addAlchemyIngredient(Items.DRAGON_BREATH, "+5+10+13");
        this.addAlchemyIngredient(Items.ROTTEN_FLESH, "+6+11+14");
        this.addAlchemyIngredient(Items.GOLDEN_APPLE, "+7+12+0");
        this.addAlchemyIngredient(Items.WITHER_SKELETON_SKULL, "+8+13+1");
    }

    private void addCompostable(float chance, ItemLike item) {
        Builder<Compostable, Item> builder = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        builder.add(item.asItem().builtInRegistryHolder(), new Compostable(chance), false);
    }

    private void addFurnaceFuel(ItemLike item, int burnTime) {
        Builder<FurnaceFuel, Item> builder = this.builder(NeoForgeDataMaps.FURNACE_FUELS);
        builder.add(item.asItem().builtInRegistryHolder(), new FurnaceFuel(burnTime), false);
    }

    private void addVillagerType(ResourceKey<Biome> biome, VillagerType type) {
        Builder<BiomeVillagerType, Biome> builder = this.builder(NeoForgeDataMaps.VILLAGER_TYPES);
        builder.add(biome, new BiomeVillagerType(type), false);
    }

    private void addUsableEffects(Holder<MobEffect> effect, String formula) {
        Builder<AlchemyTableMaterial, MobEffect> builder = this.builder(TADataMaps.ALCHEMY_TABLE_USABLE_EFFECTS);
        builder.add(effect, new AlchemyTableMaterial(formula), false);
    }

    private void addAmplifierEffects(Holder<MobEffect> effect, String formula) {
        Builder<AlchemyTableMaterial, MobEffect> builder = this.builder(TADataMaps.ALCHEMY_TABLE_AMPLIFIER_EFFECTS);
        builder.add(effect, new AlchemyTableMaterial(formula), false);
    }
    
    private void addAlchemyIngredient(ItemLike item, String formula) {
        Builder<AlchemyTableMaterial, Item> builder = this.builder(TADataMaps.ALCHEMY_TABLE_INGREDIENTS);
        builder.add(item.asItem().builtInRegistryHolder(), new AlchemyTableMaterial(formula), false);
    }

}