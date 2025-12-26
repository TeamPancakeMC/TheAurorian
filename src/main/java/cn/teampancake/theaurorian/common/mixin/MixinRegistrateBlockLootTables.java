package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

@Mixin(RegistrateBlockLootTables.class)
public class MixinRegistrateBlockLootTables extends VanillaBlockLoot {
    
    public MixinRegistrateBlockLootTables(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        this.generate();
        Set<ResourceKey<LootTable>> set = new HashSet<>();
        for (Block block : TACommonUtils.getKnownBlocks()) {
            ResourceKey<LootTable> lootTable = block.getLootTable();
            if (lootTable != BuiltInLootTables.EMPTY && set.add(lootTable)) {
                LootTable.Builder builder = this.map.remove(lootTable);
                if (builder != null) output.accept(lootTable, builder);
            }
        }
    }

}