package cn.teampancake.theaurorian.client.widget;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;

public class TransparentButton extends CustomSpritesButton {

    public TransparentButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
    }

    @Override
    protected WidgetSprites getButtonSprites() {
        return new WidgetSprites(
                TheAurorian.prefix("widget/button"),
                TheAurorian.prefix("widget/button_disabled"),
                TheAurorian.prefix("widget/button_highlighted"));
    }

}