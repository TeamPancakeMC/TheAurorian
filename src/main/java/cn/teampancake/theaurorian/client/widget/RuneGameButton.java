package cn.teampancake.theaurorian.client.widget;

import cn.teampancake.theaurorian.client.rune_game.RuneGameBrand;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RuneGameButton extends Button {

    private RuneGameBrand brand;
    protected boolean isAdded = false;

    public RuneGameButton(int x, int y, OnPress onPress) {
        super(x, y, RuneGameBrand.BRAND_SIZE, RuneGameBrand.BRAND_SIZE, Component.nullToEmpty(""), onPress, DEFAULT_NARRATION);
    }

    public void setBrand(RuneGameBrand brand) {
        this.brand = brand;
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.brand.renderBrand(graphics);
    }

    public void setPos(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public boolean isAdded() {
        return this.isAdded;
    }

    public void setAdded(boolean added) {
        this.isAdded = added;
    }

}