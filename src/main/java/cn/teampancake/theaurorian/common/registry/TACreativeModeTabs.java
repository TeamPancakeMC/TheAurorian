package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TACreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheAurorian.MOD_ID);

    private static boolean isBuildingBlock(Item item) {
        return item instanceof BlockItem blockItem && blockItem.getBlock().properties() instanceof TABlockProperties properties && properties.isBuildingBlock;
    }

    static {
        TABS.register("normal_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + TheAurorian.MOD_ID + ".normal"))
                .icon(() -> new ItemStack(TAItems.AURORIAN_CRYSTAL.get())).displayItems((params, output) -> {
                    TAItems.ITEMS.getEntries().stream().map(DeferredHolder::get).filter(item -> !isBuildingBlock(item)).forEach(output::accept);
                    params.holders().lookup(Registries.PAINTING_VARIANT).ifPresent(lookup -> CreativeModeTabs.generatePresetPaintings(output, params.holders(), lookup,
                            holder -> holder.is(PaintingVariantTags.PLACEABLE) && holder.value().assetId().getNamespace().equals(TheAurorian.MOD_ID),
                            CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                }).build());
        TABS.register("building_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + TheAurorian.MOD_ID + ".building"))
                .icon(() -> new ItemStack(TABlocks.AURORIAN_STONE.get())).displayItems((params, output) -> TAItems.ITEMS.getEntries().stream()
                        .map(DeferredHolder::get).filter(TACreativeModeTabs::isBuildingBlock).forEach(output::accept)).build());
    }

}