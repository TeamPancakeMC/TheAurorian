package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.client.text.FitWidthCharSink;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings({"ConstantConditions"})
public class TATooltipRenderUtils {

    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public static void wrapLongLines(List<ClientTooltipComponent> components, Font font, int maxSize) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof ClientTextTooltip clientTextTooltip) {
                Component component = FitWidthCharSink.get(clientTextTooltip.text);
                if (!component.getSiblings().isEmpty()) {
                    List<ClientTooltipComponent> wrapped = font.split(component, maxSize).stream().map(ClientTooltipComponent::create).toList();
                    components.remove(i);
                    components.addAll(i, wrapped);
                }
            }
        }
    }

    public static void wrapNewLines(List<ClientTooltipComponent> components) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof ClientTextTooltip clientTextTooltip) {
                Component component = FitWidthCharSink.get(clientTextTooltip.text);
                List<Component> children = component.getSiblings();
                for (int j = 0; j < children.size() - 1; j++) {
                    if ((children.get(j).getString() + children.get(j + 1).getString()).equals("\\n")) {
                        components.set(i, ClientTooltipComponent.create(textWithChildren(children, 0, j).getVisualOrderText()));
                        components.add(i + 1, ClientTooltipComponent.create(textWithChildren(children, j + 2, children.size()).getVisualOrderText()));
                        break;
                    }
                }
            }
        }
    }

    public static Component textWithChildren(List<Component> children, int from, int end) {
        MutableComponent text = Component.literal("");
        for (int i = from; i < end; i++) {
            text.append(children.get(i));
        }

        return text;
    }

}