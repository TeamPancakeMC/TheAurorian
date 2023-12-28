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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
    @Redirect(method = "placeNewPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;getLevel(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/server/level/ServerLevel;"))
    private ServerLevel modifyServerLevel(MinecraftServer instance, ResourceKey<Level> pDimension) {
        if(AurorianConfig.CONFIG_DEFAULT_SPAWN_IN_AURORIAN_DIMENSION.get()){
            ServerPlayer player=instance.getPlayerList().getPlayers().get(instance.getPlayerCount()-1);
            CompoundTag compoundtag = instance.getPlayerList().load(player);
            ResourceKey<Level> resourcekey = compoundtag != null ? DimensionType.parseLegacy(new Dynamic<>(NbtOps.INSTANCE, compoundtag.get("Dimension"))).resultOrPartial(AurorianMod.LOGGER::error).orElse(Level.OVERWORLD) : TADimensions.AURORIAN_DIMENSION;
            return instance.getLevel(resourcekey);
        }
        return instance.getLevel(pDimension);
    }
}
