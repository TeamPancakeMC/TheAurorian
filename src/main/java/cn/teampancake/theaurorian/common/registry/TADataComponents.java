package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.tooltips.ItemTooltip;
import cn.teampancake.theaurorian.common.components.*;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

/** @noinspection deprecation*/
public class TADataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, TheAurorian.MOD_ID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> SHIELD = DATA_COMPONENT_TYPE.register("shield",
            () -> DataComponentType.<Float>builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> MAX_SHIELD = DATA_COMPONENT_TYPE.register("max_shield",
            () -> DataComponentType.<Float>builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> RANK = DATA_COMPONENT_TYPE.register("rank",
            () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
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
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> FIXED_HEALTH_BOOST = DATA_COMPONENT_TYPE.register("fixed_health_boost",
            () -> DataComponentType.<Float>builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> FIXED_SPEED_BOOST = DATA_COMPONENT_TYPE.register("fixed_speed_boost",
            () -> DataComponentType.<Float>builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> FIXED_CHOP_BOOST = DATA_COMPONENT_TYPE.register("fixed_chop_boost",
            () -> DataComponentType.<Float>builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> FIXED_MINING_BOOST = DATA_COMPONENT_TYPE.register("fixed_mining_boost",
            () -> DataComponentType.<Float>builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RunestoneLife>> RUNESTONE_LIFE = DATA_COMPONENT_TYPE.register("runestone_life",
            () -> DataComponentType.<RunestoneLife>builder().persistent(RunestoneLife.CODEC).networkSynchronized(RunestoneLife.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RunestoneIce>> RUNESTONE_ICE = DATA_COMPONENT_TYPE.register("runestone_ice",
            () -> DataComponentType.<RunestoneIce>builder().persistent(RunestoneIce.CODEC).networkSynchronized(RunestoneIce.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RunestoneWater>> RUNESTONE_LIGHT = DATA_COMPONENT_TYPE.register("runestone_light",
            () -> DataComponentType.<RunestoneWater>builder().persistent(RunestoneWater.CODEC).networkSynchronized(RunestoneWater.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RunestoneWater>> RUNESTONE_WATER = DATA_COMPONENT_TYPE.register("runestone_water",
            () -> DataComponentType.<RunestoneWater>builder().persistent(RunestoneWater.CODEC).networkSynchronized(RunestoneWater.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RunestoneBlaze>> RUNESTONE_BLAZE = DATA_COMPONENT_TYPE.register("runestone_blaze",
            () -> DataComponentType.<RunestoneBlaze>builder().persistent(RunestoneBlaze.CODEC).networkSynchronized(RunestoneBlaze.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RunestoneThunder>> RUNESTONE_THUNDER = DATA_COMPONENT_TYPE.register("runestone_thunder",
            () -> DataComponentType.<RunestoneThunder>builder().persistent(RunestoneThunder.CODEC).networkSynchronized(RunestoneThunder.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RunestoneDarkness>> RUNESTONE_DARKNESS = DATA_COMPONENT_TYPE.register("runestone_darkness",
            () -> DataComponentType.<RunestoneDarkness>builder().persistent(RunestoneDarkness.CODEC).networkSynchronized(RunestoneDarkness.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RunestoneStorm>> RUNESTONE_STORM = DATA_COMPONENT_TYPE.register("runestone_storm",
            () -> DataComponentType.<RunestoneStorm>builder().persistent(RunestoneStorm.CODEC).networkSynchronized(RunestoneStorm.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RunestoneNature>> RUNESTONE_NATURE = DATA_COMPONENT_TYPE.register("runestone_nature",
            () -> DataComponentType.<RunestoneNature>builder().persistent(RunestoneNature.CODEC).networkSynchronized(RunestoneNature.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RunestoneMountain>> RUNESTONE_MOUNTAIN = DATA_COMPONENT_TYPE.register("runestone_mountain",
            () -> DataComponentType.<RunestoneMountain>builder().persistent(RunestoneMountain.CODEC).networkSynchronized(RunestoneMountain.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> ADVANCED_RUNESTONE = DATA_COMPONENT_TYPE.register("advanced_runestone",
            () -> DataComponentType.<Unit>builder().persistent(Unit.CODEC).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build());
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
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ChapterContent>>> CHAPTERS = DATA_COMPONENT_TYPE.register("chapters",
            () -> DataComponentType.<List<ChapterContent>>builder().persistent(Codec.list(ChapterContent.CODEC)).networkSynchronized(ChapterContent.STREAM_CODEC.apply(ByteBufCodecs.list())).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> NOTE_CHAPTER = DATA_COMPONENT_TYPE.register("note_chapter",
            () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Holder<ItemTooltip>>> ITEM_TOOLTIP = DATA_COMPONENT_TYPE.register("item_tooltip",
            () -> DataComponentType.<Holder<ItemTooltip>>builder().persistent(ItemTooltip.CODEC).networkSynchronized(ItemTooltip.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> NOTE_PASSPORT = DATA_COMPONENT_TYPE.register("note_passport",
            () -> DataComponentType.<Unit>builder().persistent(Unit.CODEC).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> NERF = DATA_COMPONENT_TYPE.register("nerf",
            () -> DataComponentType.<Unit>builder().persistent(Unit.CODEC).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build());

}