package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.pack.RuneGameLoader;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class MiscEventSubscriber {

    @SubscribeEvent
    public static void onDataPackLoad(AddReloadListenerEvent event) {
        event.addListener(new RuneGameLoader());
    }

}