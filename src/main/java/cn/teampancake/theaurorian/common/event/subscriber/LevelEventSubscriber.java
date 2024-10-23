package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.WorldData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class LevelEventSubscriber {

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        LevelAccessor levelAccessor = event.getLevel();
        MinecraftServer server = levelAccessor.getServer();
        if (server != null) {
            WorldData worldData = server.worldData;
            TADimensions.seed = worldData.worldGenOptions().seed();
        } else {
            if (levelAccessor instanceof Level level && level.dimension() == TADimensions.AURORIAN_DIMENSION) {
                float time = level.dimensionType().timeOfDay(level.getDayTime());
                double rainLevel = 1.0D - (double)(level.getRainLevel(1.0F) * 5.0F) / 16.0D;
                double thunderLevel = 1.0D - (double)(level.getThunderLevel(1.0F) * 5.0F) / 16.0D;
                double d2 = 0.5D + 2.0D * Mth.clamp(Mth.cos(time * Mth.TWO_PI), -0.25D, 0.25D);
                level.skyDarken = Math.max(5, (int)((1.0D - d2 * rainLevel * thunderLevel) * 11.0D));
            }
        }
    }

}