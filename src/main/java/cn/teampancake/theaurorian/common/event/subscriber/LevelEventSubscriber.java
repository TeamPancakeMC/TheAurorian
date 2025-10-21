package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.data.sky_color.ClientSkyColorData;
import cn.teampancake.theaurorian.common.level.data.sky_color.SkyColorDataStorage;
import cn.teampancake.theaurorian.common.level.data.sky_color.SkyColorManager;
import cn.teampancake.theaurorian.common.level.data.world_event.WorldEventDataStorage;
import cn.teampancake.theaurorian.common.level.data.world_event.WorldEventManager;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class LevelEventSubscriber {

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            if (TACommonUtils.isAurorianDimension(serverLevel)) {
                WorldEventManager.initialize(serverLevel);
                SkyColorDataStorage.get(serverLevel);
                WorldEventDataStorage.get(serverLevel);
            }
        }
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof Level level) {
            if (level.isClientSide()) {
                ClientSkyColorData.clearClientData(level);
            } else if (level instanceof ServerLevel serverLevel) {
                WorldEventDataStorage.get(serverLevel).setDirty();
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Pre event) {
        for (ServerLevel level : event.getServer().getAllLevels()) {
            if (TACommonUtils.isAurorianDimension(level)) {
                SkyColorManager.updateSkyColors(level);
                WorldEventManager.updateWorldEvents(level);
            }
        }
    }

}