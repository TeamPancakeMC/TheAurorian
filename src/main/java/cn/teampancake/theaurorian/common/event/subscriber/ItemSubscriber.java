package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.items.shield.CrystallineShield;
import cn.teampancake.theaurorian.common.items.shield.ShieldItemSubscriber;
import cn.teampancake.theaurorian.common.items.tool.ToolSubscriber;
import net.minecraftforge.common.MinecraftForge;

public class ItemSubscriber {
    public static void register() {
        ShieldItemSubscriber.register();
        MinecraftForge.EVENT_BUS.register(ToolSubscriber.class);
    }
}
