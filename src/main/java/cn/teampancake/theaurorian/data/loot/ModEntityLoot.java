package cn.teampancake.theaurorian.data.loot;

import com.google.common.collect.Sets;
import net.minecraft.data.loot.packs.VanillaEntityLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

@ParametersAreNonnullByDefault
public class ModEntityLoot extends VanillaEntityLoot {

    @Override
    public void generate() {

    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        this.generate();
        Set<ResourceLocation> set = Sets.newHashSet();
        for (EntityType<?> entityType : ForgeRegistries.ENTITY_TYPES) {
            if (entityType.isEnabled(this.allowed) && this.canHaveLootTable(entityType)) {
                Map<ResourceLocation, LootTable.Builder> map = this.map.remove(entityType);
                ResourceLocation resource = entityType.getDefaultLootTable();
                if (resource.equals(BuiltInLootTables.EMPTY) && map != null) {
                    map.forEach((resourceLocation, builder) -> {
                        if (set.add(resourceLocation)) {
                            biConsumer.accept(resourceLocation, builder);
                        }
                    });
                }
            }
        }
    }

}