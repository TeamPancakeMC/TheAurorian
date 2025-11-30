package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.components.RunestoneLife;
import cn.teampancake.theaurorian.common.data.pack.RuneGameLoader;
import cn.teampancake.theaurorian.common.items.curio.runestone.*;
import cn.teampancake.theaurorian.common.level.storage.functions.SetBoostFunction;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
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
            TACommonUtils.getKnownItems().forEach(item -> {
                if (item instanceof Runestone runestone) {
                    float lootChance = runestone.getLootChance();
                    DataComponentMap components = runestone.components();
                    if (lootChance > 0.0F && !components.has(TADataComponents.ADVANCED_RUNESTONE.get())) {
                        int weight = Mth.floor(100.0F * lootChance);
                        RunestoneLife runestoneLife = components.get(TADataComponents.RUNESTONE_LIFE.get());
                        LootItemConditionalFunction.Builder<?> setCount = SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F));
                        if (runestone instanceof LifeRunestone lifeRunestone && runestoneLife != null) {
                            UniformGenerator between = UniformGenerator.between(runestoneLife.minHealthBoost(), runestoneLife.maxHealthBoost());
                            LootItemConditionalFunction.Builder<?> setBoost = SetBoostFunction.setBoost(TADataComponents.FIXED_HEALTH_BOOST.get(), between);
                            builder.add(LootItem.lootTableItem(lifeRunestone).setWeight(weight).apply(setCount).apply(setBoost));
                        } else {
                            builder.add(LootItem.lootTableItem(runestone).setWeight(weight).apply(setCount));
                        }
                    }
                }
            });

            table.addPool(builder.build());
        }
    }

}