package cn.teampancake.theaurorian.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.event.RegisterAurorianSkyColorEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class LevelEventSubscriber {

    @SubscribeEvent
    public static void registerAurorianSkyColor(RegisterAurorianSkyColorEvent event) {
        event.register(AurorianMod.prefix("ta_red"), 0xf10029);
    }

}
