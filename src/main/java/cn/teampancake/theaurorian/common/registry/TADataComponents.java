package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.tooltips.ItemTooltip;
import cn.teampancake.theaurorian.common.components.AlchemyProduct;
import cn.teampancake.theaurorian.common.components.RuneGame;
import cn.teampancake.theaurorian.common.components.SourceOfTerra;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

/** @noinspection deprecation*/
public class TADataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, TheAurorian.MOD_ID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> KILL_COUNT = DATA_COMPONENT_TYPE.register("kill_count",
            () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemEnchantments>> FORBIDDEN_CURSE = DATA_COMPONENT_TYPE.register("forbidden_curse",
            () -> DataComponentType.<ItemEnchantments>builder().persistent(ItemEnchantments.CODEC).networkSynchronized(ItemEnchantments.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomData>> SPRING_OF_LIFE = DATA_COMPONENT_TYPE.register("spring_of_life",
            () -> DataComponentType.<CustomData>builder().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RuneGame>> RUNE_GAME = DATA_COMPONENT_TYPE.register("rune_game",
            () -> DataComponentType.<RuneGame>builder().persistent(RuneGame.CODEC).networkSynchronized(RuneGame.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SourceOfTerra>> SOURCE_OF_TERRA = DATA_COMPONENT_TYPE.register("source_of_terra",
            () -> DataComponentType.<SourceOfTerra>builder().persistent(SourceOfTerra.CODEC).networkSynchronized(SourceOfTerra.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<TagKey<Item>>>> ITEM_TAGS = DATA_COMPONENT_TYPE.register("item_tags",
            () -> DataComponentType.<List<TagKey<Item>>>builder().persistent(TagKey.codec(Registries.ITEM).listOf())
                    .networkSynchronized(ByteBufCodecs.fromCodec(TagKey.codec(Registries.ITEM).listOf())).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> EXTRA_TOOLTIP = DATA_COMPONENT_TYPE.register("extra_tooltip",
            () -> DataComponentType.<Unit>builder().persistent(Unit.CODEC).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> DEVELOPER = DATA_COMPONENT_TYPE.register("developer",
            () -> DataComponentType.<Unit>builder().persistent(Unit.CODEC).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MIXING_COUNT = DATA_COMPONENT_TYPE.register("mixing_count",
            () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> INGREDIENT_APPLIER = DATA_COMPONENT_TYPE.register("ingredient_applier",
            () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> INFUSED_POTION = DATA_COMPONENT_TYPE.register("infused_potion",
            () -> DataComponentType.<Unit>builder().persistent(Unit.CODEC).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<AlchemyProduct>> ALCHEMY_PRODUCT = DATA_COMPONENT_TYPE.register("alchemy_product",
            () -> DataComponentType.<AlchemyProduct>builder().persistent(AlchemyProduct.CODEC).networkSynchronized(AlchemyProduct.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ABSORBED_EXPERIENCE = DATA_COMPONENT_TYPE.register("absorbed_experience",
            () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> HIGH_PRECISION = DATA_COMPONENT_TYPE.register("high_precision",
            () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Holder<ItemTooltip>>> ITEM_TOOLTIP = DATA_COMPONENT_TYPE.register("item_tooltip",
            () -> DataComponentType.<Holder<ItemTooltip>>builder().persistent(ItemTooltip.CODEC).networkSynchronized(ItemTooltip.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> SIMPLE_MODEL = DATA_COMPONENT_TYPE.register("simple_model",
            () -> DataComponentType.<Unit>builder().networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> NO_RUN_DATA = DATA_COMPONENT_TYPE.register("no_run_data",
            () -> DataComponentType.<Unit>builder().networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).build());

}