package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.TAServerLevel;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;

/** @noinspection deprecation*/
@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class LevelEventSubscriber {

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        LevelAccessor levelAccessor = event.getLevel();
        MinecraftServer server = levelAccessor.getServer();
        if (server != null) {
            WorldData worldData = server.worldData;
            long seed = worldData.worldGenOptions().seed();
            TADimensions.seed = seed;
            DerivedLevelData derivedLevelData = new DerivedLevelData(worldData, worldData.overworldData());
            LevelStem levelStem = server.registries.compositeAccess().registryOrThrow(Registries.LEVEL_STEM).getOrThrow(TADimensions.AURORIAN_LEVEL_STEM);
            ChunkProgressListener listener = server.progressListenerFactory.create(worldData.getGameRules().getInt(GameRules.RULE_SPAWN_CHUNK_RADIUS));
            TAServerLevel serverLevel = new TAServerLevel(server, derivedLevelData, levelStem, listener, worldData.isDebugWorld(), seed);
            server.forgeGetWorldMap().put(TADimensions.AURORIAN_DIMENSION, serverLevel);
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