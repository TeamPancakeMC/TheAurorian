package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.level.TAServerLevel;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.concurrent.Executor;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Shadow @Final public Executor executor;
    @Shadow @Final public LevelStorageSource.LevelStorageAccess storageSource;
    @Shadow @Final public Map<ResourceKey<Level>, ServerLevel> levels;

    @Inject(method = "createLevels", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.AFTER))
    protected void createLevels(ChunkProgressListener listener, CallbackInfo ci, @Local(ordinal = 0) ServerLevelData serverLevelData,
                                @Local(ordinal = 0) boolean flag, @Local(ordinal = 0) Registry<LevelStem> registry, @Local(ordinal = 1) long j) {
        TAServerLevel serverLevel = new TAServerLevel((MinecraftServer) (Object) this, this.executor, this.storageSource, serverLevelData, TADimensions.AURORIAN_DIMENSION,
                registry.getOrThrow(TADimensions.AURORIAN_LEVEL_STEM), listener, flag, j, ImmutableList.of(), Boolean.TRUE, (null));
        this.levels.put(TADimensions.AURORIAN_DIMENSION, serverLevel);
    }

}