package cn.teampancake.theaurorian.client.gui;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.rune_game.dao.RuneGameBrand;
import cn.teampancake.theaurorian.client.rune_game.dao.RuneGameEliminate;
import cn.teampancake.theaurorian.client.rune_game.dao.RuneGameLayer;
import cn.teampancake.theaurorian.client.rune_game.dao.RuneGameMap;
import cn.teampancake.theaurorian.client.widget.RuneGameButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;

@OnlyIn(Dist.CLIENT)
public class RuneGameScreen extends Screen {
    private static final ResourceLocation BACKGROUND = AurorianMod.prefix("textures/gui/rune/card_slots_bg.png");
    private static final ResourceLocation SLOTS = AurorianMod.prefix("textures/gui/rune/card_slots.png");
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
        graphics.blit(BACKGROUND, 0, 0, 0, 0, width, height, width, height);

        if (flag) {
            this.runeGameMap = initMap();
            flag = false;
            runeGameMap.checkAll();
        }

        Arrays.stream(runeGameMap.getLayers())
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

        graphics.blit(SLOTS, width / 2 - checkAreaWidth / 2, height - checkAreaHeight - 20, 0, 0, 166, 28, 166, 28);
        runeGameEliminate.render(graphics, width / 2 - checkAreaWidth / 2, height - checkAreaHeight - 20);

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    private RuneGameMap initMap() {
        RuneGameMap runeGameMap = new RuneGameMap(level);

        for (int i = 0; i < runeGameMap.getLayers().length; i++) {
            RuneGameLayer layer = runeGameMap.getLayers()[i];
            RuneGameBrand[][] brands = layer.getBrands();

            for (int row = 0; row < brands.length; row++) {
                for (int col = 0; col < brands[row].length; col++) {
                    RuneGameBrand brand = brands[row][col];
                    if (brand != null && brand.hasBrand() != 0) {
                        int x = RuneGameBrand.BRAND_SIZE * col;
                        int y = RuneGameBrand.BRAND_SIZE * row;

                        int x1 = width / 2 + x  - brands.length * RuneGameBrand.BRAND_SIZE / 2;
                        int y1 = height / 2 + y - brands[row].length * RuneGameBrand.BRAND_SIZE / 2;

                        int finalCol = col;
                        int finalRow = row;
                        RuneGameButton elementImageButton = new RuneGameButton(x1, y1, (button) -> {
                            if (brand.isGray()){
                            }else {
                                this.removeWidget(button);
                                brands[finalRow][finalCol] = null;
                                runeGameEliminate.addSlot(brand);
                                if (runeGameEliminate.getSlots().size() >= 7){
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