package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.common.items.shield.ShieldItemSubscriber;
import net.neoforged.neoforge.common.NeoForge;

public class ItemSubscriber {

    public static void register() {
        ShieldItemSubscriber.register();
        NeoForge.EVENT_BUS.register(ToolEventSubscriber.class);
    }

}