package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
    @Redirect(method = "placeNewPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;load(Lnet/minecraft/server/level/ServerPlayer;)Lnet/minecraft/nbt/CompoundTag;"))
    private CompoundTag modifyLoad(PlayerList instance, ServerPlayer player) {
        if(AurorianConfig.CONFIG_DEFAULT_SPAWN_IN_AURORIAN_DIMENSION.get()){
            CompoundTag compoundtag = instance.load(player);
            ResourceKey<Level> resourcekey = compoundtag != null ? DimensionType.parseLegacy(new Dynamic<>(NbtOps.INSTANCE, compoundtag.get("Dimension"))).resultOrPartial(AurorianMod.LOGGER::error).orElse(Level.OVERWORLD) : TADimensions.AURORIAN_DIMENSION;
            if(resourcekey == TADimensions.AURORIAN_DIMENSION) {
                if(compoundtag == null)
                    compoundtag = new CompoundTag();
                compoundtag.putString("Dimension", "theaurorian:the_aurorian");
                return compoundtag;
            }
        }
        return instance.load(player);
    }
}
