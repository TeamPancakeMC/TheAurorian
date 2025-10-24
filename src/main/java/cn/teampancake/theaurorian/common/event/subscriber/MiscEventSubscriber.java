package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.components.RunestoneLife;
import cn.teampancake.theaurorian.common.data.pack.RuneGameLoader;
import cn.teampancake.theaurorian.common.items.curio.LifeRunestone;
import cn.teampancake.theaurorian.common.items.curio.Runestone;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class MiscEventSubscriber {

    @SubscribeEvent
    public static void onDataPackLoad(AddReloadListenerEvent event) {
        event.addListener(new RuneGameLoader());
    }

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        LootTable table = event.getTable();
        if (table.getLootTableId().getPath().startsWith("chests/")) {
            LootPool.Builder builder = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F));
            RandomSource random = RandomSource.create();
            TACommonUtils.getKnownItems().forEach(item -> {
                if (item instanceof Runestone runestone) {
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

            table.addPool(builder.build());
        }
    }

}