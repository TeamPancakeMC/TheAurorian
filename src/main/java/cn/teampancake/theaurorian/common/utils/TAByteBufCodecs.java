package cn.teampancake.theaurorian.common.utils;

import net.minecraft.core.IdMap;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;

public class TAByteBufCodecs {

    public static <T, R> StreamCodec<RegistryFriendlyByteBuf, R> registry(
            final ResourceKey<? extends Registry<T>> registryKey,
            final Function<Registry<T>, IdMap<R>> idGetter) {
        return new StreamCodec<>() {

            private IdMap<R> getRegistryOrThrow(RegistryFriendlyByteBuf byteBuf) {
                return idGetter.apply(byteBuf.registryAccess().registryOrThrow(registryKey));
            }

            public R decode(RegistryFriendlyByteBuf byteBuf) {
                return this.getRegistryOrThrow(byteBuf).byIdOrThrow(VarInt.read(byteBuf));
            }

            public void encode(RegistryFriendlyByteBuf byteBuf, R object) {
                VarInt.write(byteBuf, this.getRegistryOrThrow(byteBuf).getIdOrThrow(object));
            }

        };
    }

}