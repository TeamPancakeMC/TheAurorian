package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.storage.RunestoneChestLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class TAGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public TAGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TheAurorian.MOD_ID);
    }

    @Override
    protected void start() {
        this.add("runestone_loot_from_chest", new RunestoneChestLootModifier(new LootItemCondition[]{}));
    }

}