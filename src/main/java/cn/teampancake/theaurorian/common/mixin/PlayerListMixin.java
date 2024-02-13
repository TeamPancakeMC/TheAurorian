package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerList.class)
@SuppressWarnings("deprecation")
public abstract class PlayerListMixin {

    @Shadow @Final private MinecraftServer server;

    @Redirect(method = "placeNewPlayer", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/players/PlayerList;load(Lnet/minecraft/server/level/ServerPlayer;)Lnet/minecraft/nbt/CompoundTag;"))
    private CompoundTag modifyLoad(PlayerList instance, ServerPlayer player) {
        if (AurorianConfig.CONFIG_DEFAULT_SPAWN_IN_AURORIAN_DIMENSION.get()){
            CompoundTag compoundTag = instance.load(player);
            ResourceKey<Level> resourceKey = compoundTag != null ? DimensionType.parseLegacy(new Dynamic<>(NbtOps.INSTANCE, compoundTag.get("Dimension")))
                    .resultOrPartial(AurorianMod.LOGGER::error).orElse(Level.OVERWORLD) : TADimensions.AURORIAN_DIMENSION;
            if (resourceKey == TADimensions.AURORIAN_DIMENSION) {
                if (compoundTag == null) {
                    compoundTag = new CompoundTag();
                }
                compoundTag.putString("Dimension", "theaurorian:the_aurorian");
                return compoundTag;
            }
        }
        return instance.load(player);
    }

    @Redirect(method = "respawn", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/MinecraftServer;overworld()Lnet/minecraft/server/level/ServerLevel;"), require = 0)
    private ServerLevel changeOverworld(MinecraftServer server) {
        if (AurorianConfig.CONFIG_DEFAULT_SPAWN_IN_AURORIAN_DIMENSION.get()){
            return server.getLevel(TADimensions.AURORIAN_DIMENSION);
        }
        return server.overworld();
    }

    @ModifyVariable(method = "respawn", at = @At("STORE"), ordinal = 0)
    private ServerLevel modifyServerLevel(ServerLevel serverLevel) {
        if (AurorianConfig.CONFIG_DEFAULT_SPAWN_IN_AURORIAN_DIMENSION.get()){
            return server.getLevel(TADimensions.AURORIAN_DIMENSION);
        }
        return serverLevel;
    }

    @Redirect(method = "getPlayerForLogin", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/MinecraftServer;overworld()Lnet/minecraft/server/level/ServerLevel;"), require = 0)
    private ServerLevel changePlayerForLoginOverworld(MinecraftServer server) {
        if (AurorianConfig.CONFIG_DEFAULT_SPAWN_IN_AURORIAN_DIMENSION.get()){
            return server.getLevel(TADimensions.AURORIAN_DIMENSION);
        }
        return server.overworld();
    }

}