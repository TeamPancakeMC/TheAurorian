package cn.teampancake.theaurorian.common.level.storage;

import cn.teampancake.theaurorian.common.components.RunestoneLife;
import cn.teampancake.theaurorian.common.items.curio.LifeRunestone;
import cn.teampancake.theaurorian.common.items.curio.Runestone;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItems;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class RunestoneChestLootModifier extends LootModifier {

    public static final MapCodec<RunestoneChestLootModifier> CODEC = RecordCodecBuilder.mapCodec(
            instance -> codecStart(instance).apply(instance, RunestoneChestLootModifier::new));

    public RunestoneChestLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (ResourceKey<LootTable> key : BuiltInLootTables.all().stream().filter(key -> key.location().getPath().startsWith("chests/")).toList()) {
            LootTable extraTable = context.getResolver().get(Registries.LOOT_TABLE, key).map(Holder::value).orElse(LootTable.EMPTY);
            LootPool.Builder builder = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F));
            RandomSource random = context.getLevel().random;
            TAItems.ITEMS.getEntries().forEach(holder -> {
                if (holder.get() instanceof Runestone runestone) {
                    float lootChance = runestone.getLootChance();
                    if (lootChance > 0.0F) {
                        LootItemCondition.Builder randomChance = LootItemRandomChanceCondition.randomChance(lootChance);
                        RunestoneLife runestoneLife = runestone.components().get(TADataComponents.RUNESTONE_LIFE.get());
                        if (runestone instanceof LifeRunestone lifeRunestone && runestoneLife != null) {
                            DataComponentType<Double> component = TADataComponents.FIX_HEALTH_BOOST.get();
                            double boost = random.nextInt(runestoneLife.minHealthBoost(), runestoneLife.maxHealthBoost());
                            LootItemConditionalFunction.Builder<?> setComponent = SetComponentsFunction.setComponent(component, boost);
                            builder.add(LootItem.lootTableItem(lifeRunestone).when(randomChance).apply(setComponent));
                        } else {
                            builder.add(LootItem.lootTableItem(runestone)).when(randomChance);
                        }
                    }
                }
            });

            extraTable.addPool(builder.build());
            extraTable.getRandomItems(context, generatedLoot::add);
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

}