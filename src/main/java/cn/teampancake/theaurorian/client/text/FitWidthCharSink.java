package cn.teampancake.theaurorian.client.text;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;

public class FitWidthCharSink implements FormattedCharSink {

    private final MutableComponent component = Component.empty();

    @Override
    public boolean accept(int positionInCurrentSequence, Style style, int codePoint) {
        String car = new String(Character.toChars(codePoint));
        this.component.append(Component.literal(car).withStyle(style));
        return true;
    }

    public static Component get(FormattedCharSequence text) {
        FitWidthCharSink visitor = new FitWidthCharSink();
        text.accept(visitor);
        return visitor.component;
    }

}