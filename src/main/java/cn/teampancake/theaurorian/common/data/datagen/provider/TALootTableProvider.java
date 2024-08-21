package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.common.data.datagen.loot.TABlockLoot;
import cn.teampancake.theaurorian.common.data.datagen.loot.TAChestLoot;
import cn.teampancake.theaurorian.common.data.datagen.loot.TAEntityLoot;
import cn.teampancake.theaurorian.common.data.datagen.loot.TAFishingLoot;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TALootTableProvider extends LootTableProvider {

    public TALootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(TABlockLoot::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(TAChestLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(TAEntityLoot::new, LootContextParamSets.ENTITY),
                new SubProviderEntry(TAFishingLoot::new, LootContextParamSets.FISHING)), provider);
    }

}