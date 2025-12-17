package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.stream.Stream;

public class TACreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheAurorian.MOD_ID);
    private static final String PREFIX = "itemGroup." + TheAurorian.MOD_ID;

    static {
        TABS.register("building_tab", () -> CreativeModeTab.builder().title(Component.translatable(PREFIX + ".building"))
                .icon(() -> new ItemStack(TABlocks.AURORIAN_STONE.get())).displayItems((parameters, output) -> TACommonUtils.getKnownBlockStream()
                        .filter(block -> block.properties().requiredFeatures.contains(TAFeatureFlags.BUILDING)).forEach(output::accept)).build());
        TABS.register("normal_tab", () -> CreativeModeTab.builder().title(Component.translatable(PREFIX + ".normal"))
                .icon(() -> new ItemStack(TAItems.AURORIAN_CRYSTAL.get())).displayItems((parameters, output) -> {
                    Stream<Block> stream = TACommonUtils.getKnownItemStream().filter(item -> item instanceof BlockItem).map(Block::byItem);
                    stream.filter(block -> !block.properties().requiredFeatures.contains(TAFeatureFlags.BUILDING)).forEach(output::accept);
                    TACommonUtils.getKnownItemStream().filter(item -> !(item instanceof BlockItem)).forEach(output::accept);
                    parameters.holders().lookup(Registries.PAINTING_VARIANT).ifPresent(lookup ->
                            CreativeModeTabs.generatePresetPaintings(output, parameters.holders(), lookup, holder -> {
                                String namespace = holder.value().assetId().getNamespace();
                                return holder.is(PaintingVariantTags.PLACEABLE) && namespace.equals(TheAurorian.MOD_ID);
                            }, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                }).build());
    }

}