package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.tooltips.ItemTooltip;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class TAItemTooltips {

    public static final ResourceKey<Registry<ItemTooltip>> KEY = ResourceKey.createRegistryKey(TheAurorian.prefix("tooltip"));
    public static final DeferredRegister<ItemTooltip> ITEM_TOOLTIPS = DeferredRegister.create(KEY, TheAurorian.MOD_ID);
    public static final Registry<ItemTooltip> REGISTRY = new RegistryBuilder<>(KEY).create();

    public static final DeferredHolder<ItemTooltip, ItemTooltip> UNCOMMON = ITEM_TOOLTIPS.register("uncommon", () -> new ItemTooltip(
            new ItemTooltip.Properties().outerColor(0xff686f99).intermediateColor(0xfff1f2ff).innerColor(0xff686f99).textTop(4).centerFont().atlases(
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("uncommon"), -5, -17, 8, 8, ItemTooltip.Position.TOP_LEFT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("uncommon"), -3, -17, 8, 8, ItemTooltip.Position.TOP_RIGHT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("uncommon"), -5, -15, 8, 8, ItemTooltip.Position.BOTTOM_LEFT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("uncommon"), -3, -15, 8, 8, ItemTooltip.Position.BOTTOM_RIGHT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("uncommon"), 0, -18, 12, 6, ItemTooltip.Position.TOP))));

    public static final DeferredHolder<ItemTooltip, ItemTooltip> RARE = ITEM_TOOLTIPS.register("rare", () -> new ItemTooltip(
            new ItemTooltip.Properties().outerColor(0xff686f99).intermediateColor(0xfff1f2ff).innerColor(0xff686f99).textTop(6).centerFont().atlases(
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("rare"), -5, -17, 9, 9, ItemTooltip.Position.TOP_LEFT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("rare"), -4, -17, 9, 9, ItemTooltip.Position.TOP_RIGHT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("rare"), -5, -16, 9, 9, ItemTooltip.Position.BOTTOM_LEFT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("rare"), -4, -16, 9, 9, ItemTooltip.Position.BOTTOM_RIGHT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("rare"), 0, -20, 12, 10, ItemTooltip.Position.TOP))));

    public static final DeferredHolder<ItemTooltip, ItemTooltip> EPIC = ITEM_TOOLTIPS.register("epic", () -> new ItemTooltip(
            new ItemTooltip.Properties().outerColor(0xff804085).intermediateColor(0xffe3a084).innerColor(0xff804085).textTop(6).centerFont().atlases(
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("epic"), -5, -17, 9, 9, ItemTooltip.Position.TOP_LEFT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("epic"), -4, -17, 9, 9, ItemTooltip.Position.TOP_RIGHT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("epic"), -5, -16, 9, 9, ItemTooltip.Position.BOTTOM_LEFT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("epic"), -4, -16, 9, 9, ItemTooltip.Position.BOTTOM_RIGHT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("epic"), 0, -21, 18, 11, ItemTooltip.Position.TOP))));

    public static final DeferredHolder<ItemTooltip, ItemTooltip> LEGENDARY = ITEM_TOOLTIPS.register("legendary", () -> new ItemTooltip(
            new ItemTooltip.Properties().outerColor(0xff412051).intermediateColor(0xffe88a36).innerColor(0xff14182e).textTop(6).centerFont().atlases(
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("legendary"), -5, -17, 9, 9, ItemTooltip.Position.TOP_LEFT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("legendary"), -4, -17, 9, 9, ItemTooltip.Position.TOP_RIGHT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("legendary"), -5, -16, 9, 9, ItemTooltip.Position.BOTTOM_LEFT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("legendary"), -4, -16, 9, 9, ItemTooltip.Position.BOTTOM_RIGHT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("legendary"), 0, -20, 18, 11, ItemTooltip.Position.TOP),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("legendary"), 0, -5, 10, 4, ItemTooltip.Position.BOTTOM))));

    public static final DeferredHolder<ItemTooltip, ItemTooltip> MYTHICAL = ITEM_TOOLTIPS.register("mythical", () -> new ItemTooltip(
            new ItemTooltip.Properties().outerColor(0xff621748).intermediateColor(0xff3d003d).innerColor(0xff621748).textTop(6).centerFont().atlases(
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("mythical"), -5, -17, 9, 9, ItemTooltip.Position.TOP_LEFT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("mythical"), -4, -17, 9, 9, ItemTooltip.Position.TOP_RIGHT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("mythical"), -5, -16, 9, 9, ItemTooltip.Position.BOTTOM_LEFT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("mythical"), -4, -16, 9, 9, ItemTooltip.Position.BOTTOM_RIGHT),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("mythical"), 0, -20, 18, 12, ItemTooltip.Position.TOP),
                    new ItemTooltip.TooltipAtlas(TheAurorian.prefix("mythical"), 0, -6, 14, 5, ItemTooltip.Position.BOTTOM))));

    public static final DeferredHolder<ItemTooltip, ItemTooltip> CAT_BELL = ITEM_TOOLTIPS.register("cat_bell", () -> new ItemTooltip(
            new ItemTooltip.Properties().outerColor(0xff272736).intermediateColor(0xff4b5bab).innerColor(0xff272736).textTop(2).centerFont().atlases(
                    new ItemTooltip.TooltipAtlas(TAItems.CAT_BELL.getId(), -4, -16, 9, 9, ItemTooltip.Position.TOP_LEFT),
                    new ItemTooltip.TooltipAtlas(TAItems.CAT_BELL.getId(), -5, -16, 9, 9, ItemTooltip.Position.TOP_RIGHT),
                    new ItemTooltip.TooltipAtlas(TAItems.CAT_BELL.getId(), -4, -17, 9, 9, ItemTooltip.Position.BOTTOM_LEFT),
                    new ItemTooltip.TooltipAtlas(TAItems.CAT_BELL.getId(), -5, -17, 11, 9, ItemTooltip.Position.BOTTOM_RIGHT))));

    public static final DeferredHolder<ItemTooltip, ItemTooltip> TSLAT_SWORD = ITEM_TOOLTIPS.register("tslat_sword", () -> new ItemTooltip(
            new ItemTooltip.Properties().outerColor(-267386864).intermediateColor(0xff37f037).innerColor(-267386864).textTop(4).centerFont().atlases(
                    new ItemTooltip.TooltipAtlas(TAItems.TSLAT_SWORD.getId(), -4, -16, 5, 5, ItemTooltip.Position.TOP_LEFT),
                    new ItemTooltip.TooltipAtlas(TAItems.TSLAT_SWORD.getId(), -1, -16, 5, 5, ItemTooltip.Position.TOP_RIGHT),
                    new ItemTooltip.TooltipAtlas(TAItems.TSLAT_SWORD.getId(), -4, -13, 5, 5, ItemTooltip.Position.BOTTOM_LEFT),
                    new ItemTooltip.TooltipAtlas(TAItems.TSLAT_SWORD.getId(), -1, -13, 5, 5, ItemTooltip.Position.BOTTOM_RIGHT),
                    new ItemTooltip.TooltipAtlas(TAItems.TSLAT_SWORD.getId(), 0, -19, 22, 7, ItemTooltip.Position.TOP))));

    public static final DeferredHolder<ItemTooltip, ItemTooltip> RED_BOOK = ITEM_TOOLTIPS.register("red_book", () -> new ItemTooltip(
            new ItemTooltip.Properties().outerColor(-267386864).intermediateColor(0xff6c337d).innerColor(-267386864).textTop(6).centerFont().atlases(
                    new ItemTooltip.TooltipAtlas(TAItems.RED_BOOK.getId(), -5, -17, 9, 9, ItemTooltip.Position.TOP_LEFT),
                    new ItemTooltip.TooltipAtlas(TAItems.RED_BOOK.getId(), -4, -17, 9, 9, ItemTooltip.Position.TOP_RIGHT),
                    new ItemTooltip.TooltipAtlas(TAItems.RED_BOOK.getId(), -5, -15, 9, 9, ItemTooltip.Position.BOTTOM_LEFT),
                    new ItemTooltip.TooltipAtlas(TAItems.RED_BOOK.getId(), -4, -15, 9, 9, ItemTooltip.Position.BOTTOM_RIGHT),
                    new ItemTooltip.TooltipAtlas(TAItems.RED_BOOK.getId(), 0, -25, 23, 16, ItemTooltip.Position.TOP))));

    public static final DeferredHolder<ItemTooltip, ItemTooltip> DREAM_DYEING_CRYSTAL_FRAGMENT = ITEM_TOOLTIPS.register("dream_dyeing_crystal_fragment", () -> new ItemTooltip(
            new ItemTooltip.Properties().outerColor(0xffcf5eac).intermediateColor(0xffffddeb).innerColor(0xffcf5eac).textTop(4).centerFont().atlases(
                    new ItemTooltip.TooltipAtlas(TAItems.DREAM_DYEING_CRYSTAL_FRAGMENT.getId(), -5, -17, 9, 9, ItemTooltip.Position.TOP_LEFT),
                    new ItemTooltip.TooltipAtlas(TAItems.DREAM_DYEING_CRYSTAL_FRAGMENT.getId(), -4, -17, 9, 9, ItemTooltip.Position.TOP_RIGHT),
                    new ItemTooltip.TooltipAtlas(TAItems.DREAM_DYEING_CRYSTAL_FRAGMENT.getId(), -5, -16, 9, 9, ItemTooltip.Position.BOTTOM_LEFT),
                    new ItemTooltip.TooltipAtlas(TAItems.DREAM_DYEING_CRYSTAL_FRAGMENT.getId(), -4, -16, 9, 9, ItemTooltip.Position.BOTTOM_RIGHT),
                    new ItemTooltip.TooltipAtlas(TAItems.DREAM_DYEING_CRYSTAL_FRAGMENT.getId(), 0, -18, 14, 9, ItemTooltip.Position.TOP),
                    new ItemTooltip.TooltipAtlas(TAItems.DREAM_DYEING_CRYSTAL_FRAGMENT.getId(), 0, -7, 14, 5, ItemTooltip.Position.BOTTOM))));

    public static final DeferredHolder<ItemTooltip, ItemTooltip> WHITE_CHOCOLATE = ITEM_TOOLTIPS.register("white_chocolate", () -> new ItemTooltip(
            new ItemTooltip.Properties().outerColor(0xff694e36).intermediateColor(0xffddcea3).innerColor(0xff503a26).textTop(7).maxTextWidth(200).centerFont().atlases(
                    new ItemTooltip.TooltipAtlas(TAItems.WHITE_CHOCOLATE.getId(), -6, -18, 11, 11, ItemTooltip.Position.TOP_LEFT),
                    new ItemTooltip.TooltipAtlas(TAItems.WHITE_CHOCOLATE.getId(), -26, -18, 11, 11, ItemTooltip.Position.TOP_RIGHT),
                    new ItemTooltip.TooltipAtlas(TAItems.WHITE_CHOCOLATE.getId(), -5, -40, 28, 34, ItemTooltip.Position.BOTTOM_LEFT),
                    new ItemTooltip.TooltipAtlas(TAItems.WHITE_CHOCOLATE.getId(), -2, -40, 28, 34, ItemTooltip.Position.BOTTOM_RIGHT),
                    new ItemTooltip.TooltipAtlas(TAItems.WHITE_CHOCOLATE.getId(), 0, -22, 18, 15, ItemTooltip.Position.TOP))));

}