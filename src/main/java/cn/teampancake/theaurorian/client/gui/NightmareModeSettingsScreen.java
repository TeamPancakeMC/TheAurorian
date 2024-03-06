package cn.teampancake.theaurorian.client.gui;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings({"ConstantConditions"})
public class NightmareModeSettingsScreen extends Screen {

    private final Component[] entityTypeList = new Component[] {
            Component.translatable(TAEntityTypes.RUNESTONE_KEEPER.get().getDescriptionId()),
            Component.translatable(TAEntityTypes.SPIDER_MOTHER.get().getDescriptionId()),
            Component.translatable(TAEntityTypes.MOON_QUEEN.get().getDescriptionId())
    };

    public NightmareModeSettingsScreen() {
        super(Component.translatable("theaurorian.title.nightmare_mode_settings"));
    }

    @Override
    protected void init() {
        GridLayout.RowHelper rowHelper = (new GridLayout()).columnSpacing(10).createRowHelper(2);
        rowHelper.addChild(Button.builder(CommonComponents.GUI_DONE, (button) -> this.minecraft.setScreen(null)).build());
        rowHelper.addChild(Button.builder(CommonComponents.GUI_CANCEL, (button) -> this.minecraft.setScreen(null)).build());
        rowHelper.getGrid().visitWidgets(this::addRenderableWidget);
        rowHelper.getGrid().setPosition(this.width / 2 - 155, this.height - 28);
        rowHelper.getGrid().arrangeElements();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderDirtBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
        guiGraphics.drawCenteredString(this.font, this.entityTypeList[0], this.width / 2, 48, 16777215);
        guiGraphics.drawCenteredString(this.font, this.entityTypeList[1], this.width / 3, 48, 16777215);
        guiGraphics.drawCenteredString(this.font, this.entityTypeList[2], this.width * 2 / 3, 48, 16777215);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

}