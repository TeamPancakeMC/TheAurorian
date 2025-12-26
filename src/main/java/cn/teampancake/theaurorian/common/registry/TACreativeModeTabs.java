package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TACreativeModeTabs {

    private static final String PREFIX = "itemGroup." + TheAurorian.MOD_ID;
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheAurorian.MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BUILDING = TABS.register("building_tab",
            () -> CreativeModeTab.builder().title(Component.translatable(PREFIX + ".building"))
                    .icon(() -> new ItemStack(TABlocks.AURORIAN_STONE.get())).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NORMAL = TABS.register("normal_tab",
            () -> CreativeModeTab.builder().title(Component.translatable(PREFIX + ".normal"))
                    .icon(() -> new ItemStack(TAItems.AURORIAN_CRYSTAL.get())).displayItems((parameters, output) ->
                            parameters.holders().lookup(Registries.PAINTING_VARIANT).ifPresent(lookup ->
                                    CreativeModeTabs.generatePresetPaintings(output, parameters.holders(), lookup, holder -> {
                                        String namespace = holder.value().assetId().getNamespace();
                                        return holder.is(PaintingVariantTags.PLACEABLE) && namespace.equals(TheAurorian.MOD_ID);
                                        }, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS))).build());

}