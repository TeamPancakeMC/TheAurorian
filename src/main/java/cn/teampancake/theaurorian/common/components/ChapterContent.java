package cn.teampancake.theaurorian.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ChapterContent(int index, Component name) {

    public static final Codec<ChapterContent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("index").forGetter(ChapterContent::index),
                    ComponentSerialization.CODEC.fieldOf("name").forGetter(ChapterContent::name))
            .apply(instance, ChapterContent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ChapterContent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ChapterContent::index, ComponentSerialization.TRUSTED_STREAM_CODEC, ChapterContent::name, ChapterContent::new);

}