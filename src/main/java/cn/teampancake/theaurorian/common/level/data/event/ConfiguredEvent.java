package cn.teampancake.theaurorian.common.level.data.event;

import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import com.mojang.serialization.Codec;

public record ConfiguredEvent<WC extends BaseEventConfig, W extends BaseWorldEvent<WC>>(W event, WC config) {

    public static final Codec<ConfiguredEvent<?, ?>> DIRECT_CODEC = TAWorldEvents.REGISTRY.byNameCodec()
            .dispatch(configuration -> configuration.event, BaseWorldEvent::configuredCodec);

}