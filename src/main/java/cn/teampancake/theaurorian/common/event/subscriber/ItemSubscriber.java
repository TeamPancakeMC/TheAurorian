package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.common.items.shield.ShieldItemSubscriber;
import net.minecraftforge.common.MinecraftForge;

public class ItemSubscriber {
    public static void register() {
        ShieldItemSubscriber.register();
    }
}
