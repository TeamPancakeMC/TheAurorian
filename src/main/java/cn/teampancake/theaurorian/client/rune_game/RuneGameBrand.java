package cn.teampancake.theaurorian.client.rune_game;

import cn.teampancake.theaurorian.api.IRune;
import cn.teampancake.theaurorian.client.widget.RuneGameButton;
import cn.teampancake.theaurorian.common.registry.TARunes;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RuneGameBrand {

    public static final int BRAND_SIZE = 20;
    private final int DIFF = 20;
    //    private final String elementName;
    private final ResourceLocation texture;
    protected RuneGameRectangle runeGameRectangle;
    private RuneGameButton button;
    private boolean isGray = false;
    private int hasBrand = 0;
    private int x;
    private int y;

    public RuneGameBrand(IRune texture) {
        this.texture = TARunes.RUNE_GAME_TEXTURES.get(texture);
    }

    public static IRune randomElement() {
        return TARunes.RUNE_GAME_TEXTURES.keySet().stream()
                .skip((int) (Math.random() * TARunes.RUNE_GAME_TEXTURES.size()))
                .findFirst().orElse(TARunes.AURORIAN.get());
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public RuneGameBrand setPos(int x, int y) {
        this.x = x;
        this.y = y;
        this.button.setPos(x, y);
        this.runeGameRectangle = new RuneGameRectangle(x, y, BRAND_SIZE, BRAND_SIZE);
        return this;
    }

    public RuneGameBrand bindButton(RuneGameButton button) {
        this.button = button;
        return this;
    }

    public RuneGameButton getButton() {
        return button;
    }

    public int hasBrand() {
        return this.hasBrand;
    }

    public boolean isGray() {
        return this.isGray;
    }

    public void setGray(boolean gray) {
        this.isGray = gray;
    }

    public RuneGameRectangle getRectangle() {
        return this.runeGameRectangle;
    }

    public void setHasBrand(int hasBrand) {
        this.hasBrand = hasBrand;
    }

    public void renderBrand(@NotNull GuiGraphics graphics) {
        if (this.isGray) {
            graphics.blit(this.texture, x, y, BRAND_SIZE, DIFF, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE * 2);
        } else {
            graphics.blit(this.texture, x, y, 0, 0, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE * 2);
        }
    }

    public void renderBrandEliminate(@NotNull GuiGraphics graphics, int x, int y) {
        graphics.blit(this.texture, x, y, 0, 0, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE * 2);
    }

    public static List<RuneGameBrand> randomBrandList(int[][][] level) {
        int validCount = getValidCount(level);
        List<RuneGameBrand> brandList = Lists.newArrayList();
        for (int i = 0; i < validCount; i = i + 3) {
            IRune iRune = RuneGameBrand.randomElement();
            brandList.add(new RuneGameBrand(iRune));
            brandList.add(new RuneGameBrand(iRune));
            brandList.add(new RuneGameBrand(iRune));
        }

        Collections.shuffle(brandList);
        return brandList;
    }

    public static int getValidCount(int[][][] level) {
        return Arrays.stream(level).flatMap(Arrays::stream).flatMapToInt(Arrays::stream)
                .reduce(0, (count, value) -> value != 0 ? count + 1 : count);
    }

}