package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.base.VerticalSlabBlockWithBase;
import cn.teampancake.theaurorian.common.blocks.base.VerticalStairBlockWithBase;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TACreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheAurorian.MOD_ID);

    static final DeferredHolder<CreativeModeTab, CreativeModeTab> NORMAL = TABS.register("normal_tab", () -> CreativeModeTab
            .builder().title(Component.translatable("itemGroup." + TheAurorian.MOD_ID + ".normal"))
            .icon(() -> new ItemStack(TAItems.AURORIAN_STEEL.get()))
            .displayItems((params, output) -> TAItems.ITEMS.getEntries().forEach(item -> {
                if (!isOnlyUseForBuilding(item.get())) {
                    output.accept(item.get());
                }
            })).build());

    static final DeferredHolder<CreativeModeTab, CreativeModeTab> BUILDING = TABS.register("building_tab", () -> CreativeModeTab
            .builder().title(Component.translatable("itemGroup." + TheAurorian.MOD_ID + ".building"))
            .icon(() -> new ItemStack(TABlocks.AURORIAN_STONE.get()))
            .displayItems(((params, output) -> TAItems.ITEMS.getEntries().forEach(item -> {
                if (isOnlyUseForBuilding(item.get())) {
                    output.accept(item.get());
                }
            }))).build());

    private static boolean isOnlyUseForBuilding(Item item) {
        return item instanceof BlockItem blockItem && (
                blockItem.getBlock() instanceof SlabBlock ||
                blockItem.getBlock() instanceof StairBlock ||
                blockItem.getBlock() instanceof WallBlock ||
                blockItem.getBlock() instanceof VerticalSlabBlockWithBase ||
                blockItem.getBlock() instanceof VerticalStairBlockWithBase);
    }

}