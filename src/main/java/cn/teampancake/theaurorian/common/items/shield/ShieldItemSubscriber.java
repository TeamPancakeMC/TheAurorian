package cn.teampancake.theaurorian.common.items.shield;


import net.minecraftforge.common.MinecraftForge;

public class ShieldItemSubscriber {
    public static void register() {
        MinecraftForge.EVENT_BUS.register(CrystallineShield.class);
        MinecraftForge.EVENT_BUS.register(MoonShield.class);
        MinecraftForge.EVENT_BUS.register(UmbraShield.class);
        MinecraftForge.EVENT_BUS.register(MoonStoneShield.class);
    }
}
