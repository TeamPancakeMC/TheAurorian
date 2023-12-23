package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.common.data.datagen.loot.TABlockLoot;
import cn.teampancake.theaurorian.common.data.datagen.loot.TAEntityLoot;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class TALootTableProvider extends LootTableProvider {

    public TALootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(TABlockLoot::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(TAEntityLoot::new, LootContextParamSets.ENTITY)));
    }

}