package cn.teampancake.theaurorian.client.gui;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.rune_game.RuneGameBrand;
import cn.teampancake.theaurorian.client.rune_game.RuneGameEliminate;
import cn.teampancake.theaurorian.client.rune_game.RuneGameLayer;
import cn.teampancake.theaurorian.client.rune_game.RuneGameMap;
import cn.teampancake.theaurorian.client.widget.RuneGameButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Arrays;

@OnlyIn(Dist.CLIENT)
public class RuneGameScreen extends Screen {

    private static final ResourceLocation BACKGROUND = TheAurorian.prefix("textures/gui/rune/card_slots_bg.png");
    private static final ResourceLocation SLOTS = TheAurorian.prefix("textures/gui/rune/card_slots.png");
    protected final int checkAreaWidth = 166;
    protected final int checkAreaHeight = 28;
    protected int[][][] level;
    private RuneGameMap runeGameMap;
    private final RuneGameEliminate runeGameEliminate = new RuneGameEliminate();
    private boolean flag = true;

    public RuneGameScreen(int[][][] level) {
        super(Component.nullToEmpty("rune_game"));
        this.level = level;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.blit(BACKGROUND, 0, 0, 0, 0, this.width, this.height, this.width, this.height);
        if (this.flag) {
            this.runeGameMap = initMap();
            this.flag = false;
            this.runeGameMap.checkAll();
        }

        Arrays.stream(this.runeGameMap.getLayers())
                .flatMap(layer -> Arrays.stream(layer.getBrands()))
                .flatMap(Arrays::stream)
                .filter(brand -> brand != null && brand.hasBrand() != 0)
                .forEach(brand -> {
                    if (brand.isGray()) {
                        brand.renderBrand(graphics);
                    } else {
                        RuneGameButton button = brand.getButton();
                        if (button != null && !button.isAdded()) {
                            this.addRenderableWidget(button);
                            button.setAdded(true);
                        }
                    }
                });

        graphics.blit(SLOTS, this.width / 2 - this.checkAreaWidth / 2, this.height - this.checkAreaHeight - 20, 0, 0, 166, 28, 166, 28);
        this.runeGameEliminate.render(graphics, this.width / 2 - checkAreaWidth / 2, this.height - this.checkAreaHeight - 20);
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    private RuneGameMap initMap() {
        RuneGameMap runeGameMap = new RuneGameMap(this.level);
        for (int i = 0; i < runeGameMap.getLayers().length; i++) {
            RuneGameLayer layer = runeGameMap.getLayers()[i];
            RuneGameBrand[][] brands = layer.getBrands();
            for (int row = 0; row < brands.length; row++) {
                for (int col = 0; col < brands[row].length; col++) {
                    RuneGameBrand brand = brands[row][col];
                    if (brand != null && brand.hasBrand() != 0) {
                        int x = RuneGameBrand.BRAND_SIZE * col;
                        int y = RuneGameBrand.BRAND_SIZE * row;
                        int x1 = this.width / 2 + x  - brands.length * RuneGameBrand.BRAND_SIZE / 2;
                        int y1 = this.height / 2 + y - brands[row].length * RuneGameBrand.BRAND_SIZE / 2;
                        int finalCol = col;
                        int finalRow = row;
                        RuneGameButton elementImageButton = new RuneGameButton(x1, y1, (button) -> {
                            if (!brand.isGray()) {
                                this.removeWidget(button);
                                brands[finalRow][finalCol] = null;
                                this.runeGameEliminate.addSlot(brand);
                                if (this.runeGameEliminate.getSlots().size() >= 7){
                                    this.onClose();
                                }
                            }

                            runeGameMap.checkAll();
                        });
                        brand.bindButton(elementImageButton).setPos(x1, y1);
                        elementImageButton.setBrand(brand);
                    }
                }
            }
        }

        return runeGameMap;
    }

}