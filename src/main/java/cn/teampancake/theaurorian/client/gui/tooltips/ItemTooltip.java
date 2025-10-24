package cn.teampancake.theaurorian.client.gui.tooltips;

import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import cn.teampancake.theaurorian.common.utils.TAByteBufCodecs;
import cn.teampancake.theaurorian.common.utils.TATooltipRenderUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.joml.Matrix4f;
import org.joml.Vector2ic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** @noinspection deprecation*/
public class ItemTooltip {

    public static final Codec<Holder<ItemTooltip>> CODEC = TAItemTooltips.REGISTRY.holderByNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ItemTooltip>> STREAM_CODEC = TAByteBufCodecs.registry(TAItemTooltips.KEY, Registry::asHolderIdMap);

    final int backgroundColor;
    final int outerColor;
    final int intermediateColor;
    final int innerColor;
    final int textTop;
    final int maxTextWidth;
    final boolean centerFont;
    final TooltipAtlas[] atlases;
    boolean shouldFlip = false;

    public ItemTooltip(Properties properties) {
        this.backgroundColor = properties.backgroundColor;
        this.outerColor = properties.outerColor;
        this.intermediateColor = properties.intermediateColor;
        this.innerColor = properties.innerColor;
        this.textTop = properties.textTop;
        this.maxTextWidth = properties.maxTextWidth;
        this.centerFont = properties.centerFont;
        this.atlases = properties.atlases;
    }

    public void renderTooltips(RenderTooltipEvent.Pre event, int maxTextWidth) {
        List<ClientTooltipComponent> components = new ArrayList<>(event.getComponents());
        ClientTooltipPositioner positioner = event.getTooltipPositioner();
        GuiGraphics graphics = event.getGraphics();
        Font font = event.getFont();
        int mouseX = event.getX();
        int mouseY = event.getY();
        int width = event.getScreenWidth();
        int height = event.getScreenHeight();
        int i0 = 0;
        int j0 = components.size() == 1 ? -2 : 0;
        this.fixTooltipComponent(components, font, mouseX, width, maxTextWidth);
        for (ClientTooltipComponent component : components) {
            int k = component.getWidth(font);
            j0 += component.getHeight();
            if (k > i0) i0 = k;
        }

        int tooltipWidth = i0;
        int tooltipHeight = j0;
        Vector2ic vector2ic = positioner.positionTooltip(width, height, mouseX, mouseY, tooltipWidth, tooltipHeight);
        int tooltipPosX = this.shouldFlip ? mouseX - 16 - i0 : mouseX + 12, tooltipPosY = vector2ic.y();
        graphics.pose().pushPose();
        graphics.drawManaged(() -> {
            int i = tooltipPosX - 3;
            int j = tooltipPosY - 3 - 12;
            int k = tooltipWidth + 3 + 3;
            int l = tooltipHeight + 3 + 5 + this.textTop - 2;
            TooltipRenderUtil.renderRectangle(graphics, i, j, k, l, 400, this.backgroundColor);
            TooltipRenderUtil.renderHorizontalLine(graphics, i, j - 1, k, 400, this.outerColor);
            TooltipRenderUtil.renderHorizontalLine(graphics, i, j + l, k, 400, this.outerColor);
            TooltipRenderUtil.renderHorizontalLine(graphics, i + 2, j - 1 + 2, k - 2, 400, this.innerColor);
            TooltipRenderUtil.renderHorizontalLine(graphics, i + 2, j + l - 2, k - 2, 400, this.innerColor);
            TooltipRenderUtil.renderVerticalLineGradient(graphics, i - 1, j, l, 400, this.outerColor, this.outerColor);
            TooltipRenderUtil.renderVerticalLineGradient(graphics, i + k, j, l, 400, this.outerColor, this.outerColor);
            TooltipRenderUtil.renderVerticalLineGradient(graphics, i - 1 + 2, j, l - 2, 400, this.innerColor, this.innerColor);
            TooltipRenderUtil.renderVerticalLineGradient(graphics, i + k - 2, j, l - 2, 400, this.innerColor, this.innerColor);
            TooltipRenderUtil.renderFrameGradient(graphics, i, j + 1, k, l, 400, this.intermediateColor, this.intermediateColor);
        });

        graphics.pose().translate(0.0F, 0.0F, 400.0F);
        Matrix4f matrix = graphics.pose().last().pose();
        int k1 = tooltipPosY + this.textTop - 13;
        if (this.centerFont) {
            for (int l1 = 0; l1 < components.size(); l1++) {
                ClientTooltipComponent component = components.get(l1);
                int k0 = tooltipPosX + (tooltipWidth - component.getWidth(font)) / 2;
                component.renderText(font, k0, k1, matrix, graphics.bufferSource());
                k1 += component.getHeight() + (l1 == 0 ? 2 : 0);
            }
        } else {
            for (int l1 = 0; l1 < components.size(); l1++) {
                ClientTooltipComponent component = components.get(l1);
                component.renderText(font, tooltipPosX, k1, matrix, graphics.bufferSource());
                k1 += component.getHeight() + (l1 == 0 ? 2 : 0);
            }
        }

        k1 = tooltipPosY + this.textTop - 13;
        RenderSystem.enableBlend();
        this.renderCustomTooltip(graphics,
                tooltipPosX, tooltipPosY,
                tooltipWidth, tooltipHeight);
        RenderSystem.disableBlend();
        for (int k2 = 0; k2 < components.size(); k2++) {
            ClientTooltipComponent component = components.get(k2);
            component.renderImage(font, tooltipPosX, k1, graphics);
            k1 += component.getHeight() + (k2 == 0 ? 2 : 0);
        }

        graphics.pose().popPose();
    }

    protected void renderCustomTooltip(GuiGraphics graphics, int x, int y, int width, int height) {
        List<Position> positions = new ArrayList<>();
        Arrays.asList(this.atlases).forEach(atlas -> positions.add(atlas.position));
        this.renderTooltipFrame(graphics, x, y, width, height);
        if (positions.contains(Position.TOP)) {
            this.renderTooltipTopIcon(graphics, x, y, width, height);
        }

        if (positions.contains(Position.BOTTOM)) {
            this.renderTooltipBottomIcon(graphics, x, y, width, height);
        }
    }

    protected void renderTooltipFrame(GuiGraphics graphics, int x, int y, int width, int height) {
        for (int k = 0; k < 4; k++) {
            TooltipAtlas atlas = this.atlases[k];
            int i = atlas.position.useWidth() ? width : 0;
            int j = atlas.position.useHeight() ?
                    height + this.textTop : 0;
            graphics.blit(atlas.atlasLocation(),
                    x + i + atlas.xOffset,
                    y + j + atlas.yOffset,
                    0, 0,
                    atlas.width, atlas.height,
                    atlas.width, atlas.height);
        }
    }

    protected void renderTooltipTopIcon(GuiGraphics graphics, int x, int y, int width, int height) {
        TooltipAtlas atlas = this.atlases[4];
        graphics.blit(atlas.atlasLocation(),
                x + (width - atlas.width()) / 2,
                y + atlas.yOffset(), 0, 0,
                atlas.width(), atlas.height(),
                atlas.width(), atlas.height());
    }

    protected void renderTooltipBottomIcon(GuiGraphics graphics, int x, int y, int width, int height) {
        TooltipAtlas atlas = this.atlases[5];
        int x2 = x + (width - atlas.width()) / 2;
        int y2 = y + height + atlas.yOffset();
        graphics.blit(atlas.atlasLocation(),
                x2, y2, 0, 0,
                atlas.width(), atlas.height(),
                atlas.width(), atlas.height());
    }

    private void fixTooltipComponent(List<ClientTooltipComponent> components, Font font, int x, int width, int maxTextWidth) {
        this.shouldFlip = false;
        int forcedWidth = 0;
        for (ClientTooltipComponent component : components) {
            if (!(component instanceof ClientTextTooltip)) {
                int fontWidth = component.getWidth(font);
                if (fontWidth > forcedWidth) {
                    forcedWidth = fontWidth;
                }
            }
        }

        int maxWidth = width - 20 - x;
        if (forcedWidth > maxWidth || maxWidth < 100) {
            this.shouldFlip = true;
            maxWidth = x - 28;
        }

        TATooltipRenderUtils.wrapNewLines(components);
        if (this.maxTextWidth > 0) {
            TATooltipRenderUtils.wrapLongLines(components, font, this.maxTextWidth);
        } else if (maxTextWidth > 0) {
            TATooltipRenderUtils.wrapLongLines(components, font, maxTextWidth);
        } else TATooltipRenderUtils.wrapLongLines(components, font, maxWidth);
    }

    public enum Position implements StringRepresentable {

        NONE("none"),
        TOP("top"),
        TOP_LEFT("top_left"),
        TOP_RIGHT("top_right"),
        BOTTOM("bottom"),
        BOTTOM_LEFT("bottom_left"),
        BOTTOM_RIGHT("bottom_right");

        public static final Codec<Position> CODEC = StringRepresentable.fromEnum(Position::values);
        public static final StreamCodec<ByteBuf, Position> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);
        private final String name;

        Position(String name) {
            this.name = name;
        }

        public boolean useWidth() {
            return this == TOP_RIGHT || this == BOTTOM_RIGHT;
        }

        public boolean useHeight() {
            return this == BOTTOM_LEFT || this == BOTTOM_RIGHT;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

    }

    public record TooltipAtlas(ResourceLocation atlasLocation, int xOffset, int yOffset, int width, int height, Position position) {

        public static final Codec<TooltipAtlas> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("atlas_location").forGetter(TooltipAtlas::atlasLocation),
                Codec.INT.fieldOf("x_offset").forGetter(TooltipAtlas::xOffset),
                Codec.INT.fieldOf("y_offset").forGetter(TooltipAtlas::yOffset),
                Codec.INT.fieldOf("width").forGetter(TooltipAtlas::width),
                Codec.INT.fieldOf("height").forGetter(TooltipAtlas::height),
                Position.CODEC.fieldOf("position").forGetter(TooltipAtlas::position)).apply(instance, TooltipAtlas::new));
        public static final StreamCodec<FriendlyByteBuf, TooltipAtlas> STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC, TooltipAtlas::atlasLocation,
                ByteBufCodecs.INT, TooltipAtlas::xOffset, ByteBufCodecs.INT, TooltipAtlas::yOffset,
                ByteBufCodecs.INT, TooltipAtlas::width, ByteBufCodecs.INT, TooltipAtlas::height,
                Position.STREAM_CODEC, TooltipAtlas::position, TooltipAtlas::new);

        public ResourceLocation atlasLocation() {
            String pathPrefix = "textures/gui/tooltips/";
            String pathSuffix = "/" + this.position.name + ".png";
            return this.atlasLocation.withPrefix(pathPrefix).withSuffix(pathSuffix);
        }

    }

    public static class Properties {

        private int backgroundColor = -267386864;
        private int outerColor;
        private int intermediateColor;
        private int innerColor;
        private int textTop;
        private int maxTextWidth;
        private boolean centerFont;
        private TooltipAtlas[] atlases = {};

        public Properties backgroundColor(int color) {
            this.backgroundColor = color;
            return this;
        }

        public Properties outerColor(int color) {
            this.outerColor = color;
            return this;
        }

        public Properties intermediateColor(int color) {
            this.intermediateColor = color;
            return this;
        }

        public Properties innerColor(int color) {
            this.innerColor = color;
            return this;
        }

        public Properties textTop(int top) {
            this.textTop = top;
            return this;
        }

        public Properties maxTextWidth(int width) {
            this.maxTextWidth = width;
            return this;
        }

        public Properties centerFont() {
            this.centerFont = true;
            return this;
        }

        public Properties atlases(TooltipAtlas ...atlases) {
            this.atlases = atlases;
            return this;
        }

    }

}