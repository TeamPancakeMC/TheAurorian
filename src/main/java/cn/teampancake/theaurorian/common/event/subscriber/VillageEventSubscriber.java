package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class VillageEventSubscriber {

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {

    }

}