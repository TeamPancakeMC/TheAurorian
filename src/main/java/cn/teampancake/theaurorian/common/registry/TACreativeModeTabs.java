package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TACreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheAurorian.MOD_ID);

    private static boolean isDeveloperItem(Item item) {
        return item == TAItems.DEVELOPER_GIFT.get() || TACommonUtils.getItemProperties(item).isDeveloperItem;
    }

    private static boolean isBuildingBlock(Item item) {
        return item instanceof BlockItem blockItem && blockItem.getBlock().properties() instanceof TABlockProperties properties && properties.isBuildingBlock;
    }

    static {
        TABS.register("normal_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + TheAurorian.MOD_ID + ".normal"))
                .icon(() -> new ItemStack(TAItems.AURORIAN_STEEL.get())).displayItems((params, output) -> TAItems.ITEMS.getEntries().stream()
                        .map(DeferredHolder::get).filter(item -> !isDeveloperItem(item) && !isBuildingBlock(item)).forEach(output::accept)).build());
        TABS.register("building_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + TheAurorian.MOD_ID + ".building"))
                .icon(() -> new ItemStack(TABlocks.AURORIAN_STONE.get())).displayItems((params, output) -> TAItems.ITEMS.getEntries().stream()
                        .map(DeferredHolder::get).filter(TACreativeModeTabs::isBuildingBlock).forEach(output::accept)).build());
    }

}