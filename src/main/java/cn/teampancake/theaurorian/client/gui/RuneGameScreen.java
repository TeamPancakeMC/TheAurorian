package cn.teampancake.theaurorian.client.gui;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.rune_game.RuneGameBrand;
import cn.teampancake.theaurorian.client.rune_game.RuneGameEliminate;
import cn.teampancake.theaurorian.client.rune_game.RuneGameLayer;
import cn.teampancake.theaurorian.client.rune_game.RuneGameMap;
import cn.teampancake.theaurorian.client.widget.RuneGameButton;
import cn.teampancake.theaurorian.client.widget.TransparentButton;
import cn.teampancake.theaurorian.common.network.RuneGameAwardStatC2SPacket;
import cn.teampancake.theaurorian.common.network.RuneGameTimeConsumingRecordC2SPacket;
import cn.teampancake.theaurorian.common.network.RuneGameWinC2SPacket;
import cn.teampancake.theaurorian.common.registry.TAStats;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class RuneGameScreen extends Screen {

    private static final ResourceLocation BACKGROUND = TheAurorian.prefix("textures/gui/rune/card_slots_bg.png");
    private static final ResourceLocation SLOTS = TheAurorian.prefix("textures/gui/rune/card_slots.png");
    private static final String PREFIX = TheAurorian.MOD_ID + ".rune_game_screen.";
    private static final Component GAME_OVER = Component.translatable(PREFIX + "game_over");
    private static final Component PLAY_AGAIN = Component.translatable(PREFIX + "play_again");
    private static final Component QUIT_GAME = Component.translatable(PREFIX + "quit_game");
    protected final int checkAreaWidth = 166;
    protected final int checkAreaHeight = 28;
    private int gameTime;
    protected int[][][] level;
    @Nullable
    private RuneGameMap runeGameMap;
    private final RuneGameEliminate runeGameEliminate = new RuneGameEliminate();
    private final List<Button> buttonList = Lists.newArrayList();
    @Nullable
    private TransparentButton playAgainButton;
    @Nullable
    private TransparentButton quitGameButton;
    private GameStatus gameStatus = GameStatus.PLAYING;
    private boolean flag = true;
    private boolean gameOver = false;

    public RuneGameScreen(int[][][] level) {
        super(Component.nullToEmpty("rune_game"));
        this.level = level;
    }

    @Override
    protected void init() {
        this.buttonList.clear();
        this.playAgainButton = new TransparentButton(this.width / 2 - 100, this.height / 4 + 62, 200, 20, PLAY_AGAIN, button -> {
            this.renderables.clear();
            this.gameTime = 0;
            this.gameOver = false;
            this.runeGameMap = this.initMap();
            this.runeGameMap.checkAll();
            this.setButtonsState(false);
        });
        this.quitGameButton = new TransparentButton(this.width / 2 - 100, this.height / 4 + 90, 200, 20, QUIT_GAME, button -> this.onClose());
        this.buttonList.add(this.addRenderableWidget(this.playAgainButton));
        this.buttonList.add(this.addRenderableWidget(this.quitGameButton));
        this.setButtonsState(false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(BACKGROUND, 0, 0, 0, 0, this.width, this.height, this.width, this.height);
        if (this.flag) {
            this.gameTime = 0;
            this.flag = false;
            this.gameOver = false;
            this.renderables.clear();
            this.runeGameMap = this.initMap();
            this.runeGameMap.checkAll();
        }

        int slotsX = this.width / 2 - this.checkAreaWidth / 2;
        int slotsY = this.height - this.checkAreaHeight - 20;
        guiGraphics.blit(SLOTS, slotsX, slotsY, 0, 0, this.checkAreaWidth, this.checkAreaHeight, this.checkAreaWidth, this.checkAreaHeight);
        this.runeGameEliminate.render(guiGraphics, this.width / 2 - checkAreaWidth / 2, this.height - this.checkAreaHeight - 20);
        if (this.gameOver) {
            if (this.playAgainButton != null && this.quitGameButton != null) {
                this.buttonList.add(this.addRenderableWidget(this.playAgainButton));
                this.buttonList.add(this.addRenderableWidget(this.quitGameButton));
                this.setButtonsState(true);
            }

            PoseStack pose = guiGraphics.pose();
            pose.pushPose();
            pose.scale(2.5F, 2.5F, 2.5F);
            int color = this.gameStatus == GameStatus.WIN ? 5635925 : 16733525;
            guiGraphics.drawCenteredString(this.font, GAME_OVER, this.width / 5, 25, color);
            pose.popPose();
            if (this.minecraft != null) {
                pose.pushPose();
                LocalPlayer player = this.minecraft.player;
                Component bestGameTime = Component.empty();
                if (player != null && this.gameStatus == GameStatus.WIN) {
                    Stat<ResourceLocation> stat = Stats.CUSTOM.get(TAStats.RUNE_GAME_BEST_TIME.get());
                    int value = player.getStats().getValue(stat);
                    int time = value == 0 ? this.gameTime : value;
                    bestGameTime = formatElapsedTime(time).withStyle(ChatFormatting.GREEN);
                }

                Component gameTimeText = formatElapsedTime(this.gameTime).withStyle(ChatFormatting.YELLOW);
                Component currentGameInfo = Component.translatable(PREFIX + "current_game_info", gameTimeText, bestGameTime);
                guiGraphics.drawCenteredString(this.font, currentGameInfo, this.width / 2, 100, 16777215);
                pose.popPose();
            }

            if (this.runeGameMap != null) {
                Arrays.stream(this.runeGameMap.getLayers()).flatMap(layer -> Arrays.stream(layer.getBrands()))
                        .flatMap(Arrays::stream).filter(brand -> brand != null && brand.hasBrand() != 0)
                        .forEach(brand -> brand.getButton().visible = false);
                this.runeGameEliminate.getSlots().clear();
            }
        } else {
            this.flatRuneGameMap(guiGraphics);
        }

        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public void tick() {
        if (!this.gameOver) {
            PacketDistributor.sendToServer(new RuneGameAwardStatC2SPacket(1));
            this.gameTime++;
        }
    }

    @Override
    protected void repositionElements() {
        if (this.gameOver) {
            super.repositionElements();
        }
    }

    public static MutableComponent formatElapsedTime(int elapsedTicks) {
        int elapsedSeconds = elapsedTicks / 20;
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        int hours = minutes / 60;
        minutes = minutes % 60;
        if (elapsedSeconds < 60) {
            return Component.translatable(PREFIX + "sec", seconds);
        } else if (elapsedSeconds < 3600) {
            return Component.translatable(PREFIX + "min_sec", minutes, seconds);
        } else {
            return Component.translatable(PREFIX + "h_min_sec", hours, minutes, seconds);
        }
    }

    private void setButtonsState(boolean state) {
        this.buttonList.forEach(button -> {
            button.active = state;
            button.visible = state;
        });
    }

    private void flatRuneGameMap(GuiGraphics guiGraphics) {
        if (this.runeGameMap != null) {
            Arrays.stream(this.runeGameMap.getLayers()).flatMap(layer -> Arrays.stream(layer.getBrands()))
                    .flatMap(Arrays::stream).filter(brand -> brand != null && brand.hasBrand() != 0)
                    .forEach(brand -> {
                        if (brand.isGray()) {
                            brand.renderBrand(guiGraphics);
                        } else {
                            RuneGameButton button = brand.getButton();
                            if (button != null && !button.isAdded()) {
                                this.addRenderableWidget(button);
                                button.setAdded(true);
                            }
                        }
                    });
        }
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
                        int x1 = this.width / 2 + x - brands.length * RuneGameBrand.BRAND_SIZE / 2;
                        int y1 = this.height / 2 + y - brands[row].length * RuneGameBrand.BRAND_SIZE / 2;
                        int finalCol = col;
                        int finalRow = row;
                        RuneGameButton elementImageButton = new RuneGameButton(x1, y1, (button) -> {
                            if (!brand.isGray()) {
                                this.removeWidget(button);
                                brands[finalRow][finalCol] = null;
                                this.runeGameEliminate.addSlot(brand);
                                int size = this.runeGameEliminate.getSlots().size();
                                if (size >= 7) {
                                    PacketDistributor.sendToServer(new RuneGameAwardStatC2SPacket(0));
                                    this.gameStatus = GameStatus.LOST;
                                    this.gameOver = true;
                                }

                                if (this.renderables.isEmpty() && size < 3) {
                                    PacketDistributor.sendToServer(new RuneGameAwardStatC2SPacket(0));
                                    PacketDistributor.sendToServer(new RuneGameWinC2SPacket(Boolean.TRUE));
                                    PacketDistributor.sendToServer(new RuneGameTimeConsumingRecordC2SPacket(this.gameTime));
                                    this.gameStatus = GameStatus.WIN;
                                    this.gameOver = true;
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

    private enum GameStatus {

        WIN, LOST, PLAYING

    }

}