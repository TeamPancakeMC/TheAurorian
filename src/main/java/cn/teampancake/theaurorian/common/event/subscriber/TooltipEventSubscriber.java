package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.utils.TATooltipRenderUtils;
import cn.teampancake.theaurorian.compat.ModernUICompat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class TooltipEventSubscriber {

    private static final ResourceLocation UNCOMMON = AurorianMod.prefix("textures/gui/tooltips/uncommon.png");
    private static final ResourceLocation RARE = AurorianMod.prefix("textures/gui/tooltips/rare.png");
    private static final ResourceLocation EPIC = AurorianMod.prefix("textures/gui/tooltips/epic.png");
    private static final ResourceLocation LEGENDARY = AurorianMod.prefix("textures/gui/tooltips/legendary.png");
    private static final ResourceLocation MYTHICAL = AurorianMod.prefix("textures/gui/tooltips/mythical.png");
    private static final ResourceLocation SLEEPING_BLACK_TEA = AurorianMod.prefix("textures/gui/tooltips/sleeping_black_tea.png");
    private static final ResourceLocation WHITE_CHOCOLATE = AurorianMod.prefix("textures/gui/tooltips/white_chocolate.png");
    private static final ResourceLocation RED_BOOK = AurorianMod.prefix("textures/gui/tooltips/red_book.png");
    private static final ResourceLocation STAR_OCEAN_CROSSBOW = AurorianMod.prefix("textures/gui/tooltips/star_ocean_crossbow.png");
    private static final ResourceLocation DREAM_DYEING_CRYSTAL_FRAGMENT = AurorianMod.prefix("textures/gui/tooltips/dream_dyeing_crystal_fragment.png");
    private static final ResourceLocation CAT_BELL = AurorianMod.prefix("textures/gui/tooltips/cat_bell.png");
    private static final ResourceLocation TSLAT_SWORD = AurorianMod.prefix("textures/gui/tooltips/tslat_sword.png");

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @OnlyIn(Dist.CLIENT)
    public static void onRenderTooltips(RenderTooltipEvent.Pre event) {
        ModernUICompat.toggleModernUITooltipRenderer(true);
        ItemStack itemStack = event.getItemStack();
        CompoundTag tag = itemStack.getTag();

        if (itemStack.is(TAItemTags.HAS_CUSTOM_TOOLTIPS)) {
            event.setCanceled(true);
            ModernUICompat.toggleModernUITooltipRenderer(false);
            if (itemStack.is(TAItemTags.BUILDING_BLOCK)) TooltipData.UNCOMMON_ITEM.renderTooltips(event);
            if (itemStack.is(TAItemTags.IS_RARE)) TooltipData.RARE_ITEM.renderTooltips(event);
            if (itemStack.is(TAItemTags.IS_EPIC)) TooltipData.EPIC_ITEM.renderTooltips(event);
            if (itemStack.is(TAItemTags.IS_LEGENDARY)) TooltipData.LEGENDARY_ITEM.renderTooltips(event);
            if (itemStack.is(TAItemTags.IS_MYTHICAL)) TooltipData.MYTHICAL_ITEM.renderTooltips(event);
            if (itemStack.is(TAItems.WHITE_CHOCOLATE.get())) TooltipData.WHITE_CHOCOLATE_ITEM.renderTooltips(event);
            if (itemStack.is(TAItems.RED_BOOK.get()) || itemStack.is(TAItems.RED_BOOK_RING.get()))
                TooltipData.RED_BOOK_ITEM.renderTooltips(event);
            if (itemStack.is(TAItems.STAR_OCEAN_CROSSBOW.get()))
                TooltipData.STAR_OCEAN_CROSSBOW_ITEM.renderTooltips(event);
            if (itemStack.is(TAItems.DREAM_DYEING_CRYSTAL_FRAGMENT.get()))
                TooltipData.DREAM_DYEING_CRYSTAL_FRAGMENT_ITEM.renderTooltips(event);
            if (itemStack.is(TAItems.CAT_BELL.get())) TooltipData.CAT_BELL_ITEM.renderTooltips(event);
            if (itemStack.is(TAItems.TSLAT_SWORD.get())) TooltipData.TSLAT_SWORD_ITEM.renderTooltips(event);
        }

    }


    public record TooltipData(ResourceLocation texture, int backgroundColor, int borderColor, int textureWidth, int textureHeight, int textureOffsetX, int textureOffsetY,
                       int[] xOffset, int[] yOffset, int[] uOffset, int[] vOffset, int[] uWidth, int[] vHeight) {
        public static final TooltipData TSLAT_SWORD_ITEM = new TooltipData(
                TSLAT_SWORD,
                -267386864,
                0xff37f037,
                -267386864,
                64,
                64,
                2,
                new int[] {-4, -4, -1, -1},
                new int[] {-16, -13, -16, -13, -19},
                new int[] {0, 0, 5, 5, 10},
                new int[] {0, 5, 0, 5, 0},
                new int[] {5, 5, 5, 5, 22},
                new int[] {5, 5, 5, 5, 7}
        );

        public static final TooltipData CAT_BELL_ITEM = new TooltipData(
                CAT_BELL,
                0xff272736,
                0xff4b5bab,
                0xff272736,
                48,
                32,
                0,
                new int[] {-4, -4, -5, -5},
                new int[] {-16, -17, -16, -17},
                new int[] {0, 0, 9, 9},
                new int[] {0, 9, 0, 9},
                new int[] {9, 9, 9, 11},
                new int[] {9, 9, 9, 9}
        );

        public static final TooltipData DREAM_DYEING_CRYSTAL_FRAGMENT_ITEM = new TooltipData(
                DREAM_DYEING_CRYSTAL_FRAGMENT,
                0xffcf5eac,
                0xffffddeb,
                0xffcf5eac,
                48,
                32,
                2,
                new int[] {-5, -5, -4, -4},
                new int[] {-17, -16, -17, -16, -18, -7},
                new int[] {0, 0, 9, 9, 18, 18},
                new int[] {0, 9, 0, 9, 0, 9},
                new int[] {9, 9, 9, 9, 14, 14},
                new int[] {9, 9, 9, 9, 9, 5}
        );

        public static final TooltipData STAR_OCEAN_CROSSBOW_ITEM = new TooltipData(
                STAR_OCEAN_CROSSBOW,
                -267386864,
                0xff217279,
                -267386864,
                64,
                64,
                0,
                new int[] {-4, -4, -3, -4},
                new int[] {-16, -18, -18, -16},
                new int[] {0, 0, 8, 12},
                new int[] {0, 9, 0, 11},
                new int[] {8, 12, 9, 8},
                new int[] {8, 10, 9, 8}
        );

        public static final TooltipData RED_BOOK_ITEM = new TooltipData(
                RED_BOOK,
                -267386864,
                0xff6c337d,
                -267386864,
                48,
                32,
                4,
                new int[] {-5, -5, -4, -4},
                new int[] {-17, -15, -17, -15, -25},
                new int[] {0, 0, 9, 9, 18},
                new int[] {0, 9, 0, 9, 0},
                new int[] {9, 9, 9, 9, 23},
                new int[] {9, 9, 9, 9, 16}
        );

        public static final TooltipData WHITE_CHOCOLATE_ITEM = new TooltipData(
                WHITE_CHOCOLATE,
                0xff694e36,
                0xffddcea3,
                0xff503a26,
                128,
                128,
                5,
                new int[] {-6, -26, -5, -2},
                new int[] {-18, -40, -18, -40, -22},
                new int[] {0, 0, 11, 28, 22},
                new int[] {0, 15, 0, 15, 0},
                new int[] {11, 28, 11, 28, 18},
                new int[] {11, 34, 11, 34, 15}
        );

        public static final TooltipData MYTHICAL_ITEM = new TooltipData(
                MYTHICAL,
                0xff621748,
                0xff3d003d,
                0xff621748,
                48,
                32,
                4,
                new int[] {-5, -5, -4, -4},
                new int[] {-17, -16, -17, -16, -20, -6},
                new int[] {0, 0, 9, 9, 18, 18},
                new int[] {0, 9, 0, 9, 0, 12},
                new int[] {9, 9, 9, 9, 18, 14},
                new int[] {9, 9, 9, 9, 12, 5}
        );

        public static final TooltipData LEGENDARY_ITEM = new TooltipData(
                LEGENDARY,
                0xff412051,
                0xffe88a36,
                0xff14182e,
                48,
                32,
                4,
                new int[] {-5, -5, -4, -4},
                new int[] {-17, -16, -17, -16, -20, -5},
                new int[] {0, 0, 9, 9, 18, 18},
                new int[] {0, 9, 0, 9, 0, 11},
                new int[] {9, 9, 9, 9, 18, 10},
                new int[] {9, 9, 9, 9, 11, 4}
        );

        public static final TooltipData EPIC_ITEM = new TooltipData(
                EPIC,
                0xff804085,
                0xffe3a084,
                0xff804085,
                48,
                32,
                4,
                new int[] {-5, -5, -4, -4},
                new int[] {-17, -16, -17, -16, -21},
                new int[] {0, 0, 9, 9, 18},
                new int[] {0, 9, 0, 9, 0},
                new int[] {9, 9, 9, 9, 18},
                new int[] {9, 9, 9, 9, 11}
        );

        public static final TooltipData RARE_ITEM = new TooltipData(
                RARE,
                0xff686f99,
                0xfff1f2ff,
                0xff686f99,
                48,
                32,
                4,
                new int[] {-5, -5, -4, -4},
                new int[] {-17, -16, -17, -16, -20},
                new int[] {0, 0, 9, 9, 18},
                new int[] {0, 9, 0, 9, 0},
                new int[] {9, 9, 9, 9, 12},
                new int[] {9, 9, 9, 9, 10}
        );

        public static final TooltipData UNCOMMON_ITEM = new TooltipData(
                UNCOMMON,
                0xff686f99,
                0xfff1f2ff,
                0xff686f99,
                48,
                32,
                2,
                new int[] {-5, -5, -3, -3},
                new int[] {-17, -15, -17, -15, -18},
                new int[] {0, 0, 8, 8, 16},
                new int[] {0, 8, 0, 8, 0},
                new int[] {8, 8, 8, 8, 12},
                new int[] {8, 8, 8, 8, 6}
        );

        public void renderTooltips(RenderTooltipEvent.Pre event) {
            TATooltipRenderUtils.renderTooltips(event, texture, backgroundColor, borderColor, textureWidth, textureHeight, textureOffsetX, textureOffsetY,
                    xOffset, yOffset, uOffset, vOffset, uWidth, vHeight);
        }

    }


}