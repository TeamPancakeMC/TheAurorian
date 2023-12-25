package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {


    @ModifyVariable(method = "placeNewPlayer", at = @At(value = "STORE", ordinal = 0), index = 8)
    private ServerLevel modifyServerLevel(ServerLevel value) {
        if(AurorianConfig.CONFIG_DEFAULT_SPAWN_IN_AURORIAN_DIMENSION.get()){
            return value.getServer().getLevel(TADimensions.AURORIAN_DIMENSION);
        }
        return value;
    }
}
