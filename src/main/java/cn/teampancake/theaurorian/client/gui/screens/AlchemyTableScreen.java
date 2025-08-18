package cn.teampancake.theaurorian.client.gui.screens;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.inventory.AlchemyTableMenu;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.ItemDecoratorHandler;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class AlchemyTableScreen extends AbstractContainerScreen<AlchemyTableMenu> {

    private static final ResourceLocation CONTAINER_LOCATION = TheAurorian.prefix("textures/gui/alchemy_table.png");
    private static final ResourceLocation BOTTLE = TheAurorian.prefix("alchemy_table/bottle");
    private static final ResourceLocation SLOT = TheAurorian.prefix("alchemy_table/slot");
    private static final ResourceLocation SLOT_HIGHLIGHTED = TheAurorian.prefix("alchemy_table/slot_highlighted");
    private static final ResourceLocation RESET_BUTTON = TheAurorian.prefix("alchemy_table/reset_button");
    private static final ResourceLocation RESET_BUTTON_HIGHLIGHTED = TheAurorian.prefix("alchemy_table/reset_button_highlighted");

    public AlchemyTableScreen(AlchemyTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {}

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        ContainerData containerData = this.menu.getContainerData();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        float k1 = containerData.get(0);
        float k2 = containerData.get(1);
        PoseStack poseStack = guiGraphics.pose();
        guiGraphics.blit(CONTAINER_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        poseStack.pushPose();
        int v = Mth.floor(42 * (k1 / k2));
        int y = this.height - j - 90 - v;
        guiGraphics.blit(CONTAINER_LOCATION, i + 109, y, 176, 42 - v, 30, v);
        poseStack.popPose();
    }

    @Override
    public void renderFloatingItem(GuiGraphics guiGraphics, ItemStack stack, int x, int y, String text) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, 232.0F);
        guiGraphics.renderItem(stack, x, y);
        Font font = IClientItemExtensions.of(stack).getFont(stack, IClientItemExtensions.FontContext.ITEM_COUNT);
        this.renderItemDecorations(guiGraphics, font == null ? this.font : font, stack, x, y - (this.draggingItem.isEmpty() ? 0 : 8), text);
        guiGraphics.pose().popPose();
    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        super.renderSlot(guiGraphics, slot);
    }

    @Override
    protected void renderSlotContents(GuiGraphics guiGraphics, ItemStack itemStack, Slot slot, @Nullable String countString) {
        this.renderItemDecorations(guiGraphics, this.font, itemStack, slot.x, slot.y, countString);
        int seed = slot.x + slot.y * this.imageWidth;
        if (slot.isFake()) {
            guiGraphics.renderFakeItem(itemStack, slot.x, slot.y, seed);
        } else {
            guiGraphics.renderItem(itemStack, slot.x, slot.y, seed);
        }
    }

    private void renderItemDecorations(GuiGraphics guiGraphics, Font font, ItemStack stack, int x, int y, @Nullable String text) {
        if (this.minecraft != null && !stack.isEmpty()) {
            guiGraphics.pose().pushPose();
            Boolean ingredientApplier = stack.get(TADataComponents.INGREDIENT_APPLIER);
            if (ingredientApplier != null && ingredientApplier) {
                guiGraphics.fill(RenderType.guiOverlay(), x, y, x + 16, y + 16, FastColor.ARGB32.color(192, 0x7FCC19));
            }

            if (stack.getCount() != 1 || text != null) {
                String s = text == null ? String.valueOf(stack.getCount()) : text;
                guiGraphics.pose().translate(0.0F, 0.0F, 200.0F);
                guiGraphics.drawString(font, s, x + 19 - 2 - font.width(s), y + 6 + 3, 16777215, true);
            }

            if (stack.isBarVisible()) {
                int l = stack.getBarWidth();
                int i = stack.getBarColor();
                int j = x + 2;
                int k = y + 13;
                guiGraphics.fill(RenderType.guiOverlay(), j, k, j + 13, k + 2, -16777216);
                guiGraphics.fill(RenderType.guiOverlay(), j, k, j + l, k + 1, i | 0xFF000000);
            }

            LocalPlayer player = this.minecraft.player;
            float partialTick = this.minecraft.getTimer().getGameTimeDeltaPartialTick(true);
            float f = player == null ? 0.0F : player.getCooldowns().getCooldownPercent(stack.getItem(), partialTick);
            if (f > 0.0F) {
                int i1 = y + Mth.floor(16.0F * (1.0F - f));
                int j1 = i1 + Mth.ceil(16.0F * f);
                guiGraphics.fill(RenderType.guiOverlay(), x, i1, x + 16, j1, Integer.MAX_VALUE);
            }

            guiGraphics.pose().popPose();
            //noinspection UnstableApiUsage
            ItemDecoratorHandler.of(stack).render(guiGraphics, font, stack, x, y);
        }
    }

}