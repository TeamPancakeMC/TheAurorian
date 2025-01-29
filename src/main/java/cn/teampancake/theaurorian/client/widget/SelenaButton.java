package cn.teampancake.theaurorian.client.widget;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SelenaButton extends CustomSpritesButton {

    public SelenaButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
    }

    @Override
    protected WidgetSprites getButtonSprites() {
        return new WidgetSprites(
                TheAurorian.prefix("widget/selena_button"),
                TheAurorian.prefix("widget/selena_button_disabled"),
                TheAurorian.prefix("widget/selena_button_highlighted"));
    }

}