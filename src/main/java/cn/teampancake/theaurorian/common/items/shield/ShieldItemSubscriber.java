package cn.teampancake.theaurorian.common.items.shield;

import net.neoforged.neoforge.common.NeoForge;

public class ShieldItemSubscriber {

    public static void register() {
        NeoForge.EVENT_BUS.register(CrystallineShield.class);
        NeoForge.EVENT_BUS.register(MoonShield.class);
        NeoForge.EVENT_BUS.register(UmbraShield.class);
        NeoForge.EVENT_BUS.register(MoonStoneShield.class);
    }

}