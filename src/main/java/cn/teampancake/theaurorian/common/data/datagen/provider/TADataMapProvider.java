package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
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
    protected void gather() {
        // COMPOSTABLES
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
        // FURNACE_FUELS
        this.addFurnaceFuel(TABlocks.AURORIAN_CRAFTING_TABLE.get(), 300);
        this.addFurnaceFuel(TABlocks.SILENT_WOOD_LADDER.get(), 300);
        this.addFurnaceFuel(TAItems.SILENT_WOOD_STICK.get(), 100);
        this.addFurnaceFuel(TAItems.SILENT_WOOD_BOW.get(), 300);
        this.addFurnaceFuel(TAItems.AURORIAN_COAL.get(), 1500);
    }

    private void addCompostable(float chance, ItemLike item) {
        Builder<Compostable, Item> builder = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        builder.add(item.asItem().builtInRegistryHolder(), new Compostable(chance), false);
    }

    private void addFurnaceFuel(ItemLike item, int burnTime) {
        Builder<FurnaceFuel, Item> builder = this.builder(NeoForgeDataMaps.FURNACE_FUELS);
        builder.add(item.asItem().builtInRegistryHolder(), new FurnaceFuel(burnTime), false);
    }

}