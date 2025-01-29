package cn.teampancake.theaurorian.client.widget;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;

public class SelenaCloseButton extends CustomSpritesButton {

    public SelenaCloseButton(int x, int y, int width, int height, OnPress onPress) {
        super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);
    }

    @Override
    protected WidgetSprites getButtonSprites() {
        return new WidgetSprites(
                TheAurorian.prefix("widget/selena_close_button"),
                TheAurorian.prefix("widget/selena_close_button_disabled"),
                TheAurorian.prefix("widget/selena_close_button_highlighted"));
    }

}