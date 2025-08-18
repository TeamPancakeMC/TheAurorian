package cn.teampancake.theaurorian.client.gui.screens;

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
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
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
    private static final Component GAME_WIN = Component.translatable(PREFIX + "game_win");
    private static final Component GAME_LOST = Component.translatable(PREFIX + "game_lost");
    private static final Component PLAY_AGAIN = Component.translatable(PREFIX + "play_again");
    private static final Component QUIT_GAME = Component.translatable(PREFIX + "quit_game");
    private static final Component NORMAL_MODE = Component.translatable(PREFIX + "normal_mode");
    private static final Component HARD_MODE = Component.translatable(PREFIX + "hard_mode");
    private static final Component HINT = Component.translatable(PREFIX + "hint");

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
    @Nullable
    private TransparentButton hintButton;
    @Nullable
    private TransparentButton normalModeButton;
    @Nullable
    private TransparentButton hardModeButton;
    private GameStatus gameStatus = GameStatus.PLAYING;
    private boolean flag = true;
    private boolean gameOver = false;
    private boolean buttonsAdded = false;
    private Difficulty difficulty = Difficulty.NORMAL;
    private int hintsRemaining = 3; // 默认提示次数
    @Nullable
    private RuneGameBrand highlightedBrand; // 当前高亮的符文
    private long highlightStartTime = 0; // 高亮开始时间

    // 游戏统计
    private int movesCount = 0;
    private int eliminationsCount = 0;

    private long celebrationStartTime = 0;
    private boolean playingVictoryCelebration = false;

    public RuneGameScreen(int[][][] level) {
        super(Component.nullToEmpty("rune_game"));
        this.level = level;
    }

    @Override
    protected void init() {
        this.buttonList.clear();

        // 游戏结束按钮
        this.playAgainButton = new TransparentButton(this.width / 2 - 100, this.height / 4 + 68, 200, 20, PLAY_AGAIN, button -> {
            this.renderables.clear();
            this.gameTime = 0;
            this.movesCount = 0;
            this.eliminationsCount = 0;
            this.gameOver = false;
            this.buttonsAdded = false;
            this.playingVictoryCelebration = false;
            this.celebrationStartTime = 0;
            this.resetHints();
            this.runeGameMap = this.initMap();
            this.runeGameMap.checkAll();
            this.setButtonsState(false);

            // 重新启用难度选择按钮
            if (this.normalModeButton != null) this.normalModeButton.active = true;
            if (this.hardModeButton != null) this.hardModeButton.active = true;
        });

        this.quitGameButton = new TransparentButton(this.width / 2 - 100, this.height / 4 + 96, 200, 20, QUIT_GAME, button -> this.onClose());

        // 难度选择按钮 - 移动到屏幕上方且增加间隔，防止误触
        this.normalModeButton = new TransparentButton(this.width / 2 - 120, 10, 100, 20, NORMAL_MODE, button -> {
            if (this.difficulty != Difficulty.NORMAL) {
                this.difficulty = Difficulty.NORMAL;
                this.resetGame();

                // 游戏开始后禁用难度选择按钮
                // this.normalModeButton.active = false;
                if (this.hardModeButton != null) this.hardModeButton.active = false;
            }
        });

        this.hardModeButton = new TransparentButton(this.width / 2 + 20, 10, 100, 20, HARD_MODE, button -> {
            if (this.difficulty != Difficulty.HARD) {
                this.difficulty = Difficulty.HARD;
                this.resetGame();

                // 游戏开始后禁用难度选择按钮
                if (this.normalModeButton != null) this.normalModeButton.active = false;
                this.hardModeButton.active = false;
            }
        });

        // 提示按钮
        this.hintButton = new TransparentButton(this.width - 70, 10, 60, 20,
            Component.translatable(PREFIX + "hint", this.hintsRemaining), button -> {
            if (this.hintsRemaining > 0 && !this.gameOver) {
                showHint();
                this.hintsRemaining--;
                button.setMessage(Component.translatable(PREFIX + "hint", this.hintsRemaining));
            }
        });

        this.buttonList.add(this.addRenderableWidget(this.playAgainButton));
        this.buttonList.add(this.addRenderableWidget(this.quitGameButton));
        this.buttonList.add(this.addRenderableWidget(this.normalModeButton));
        this.buttonList.add(this.addRenderableWidget(this.hardModeButton));
        this.buttonList.add(this.addRenderableWidget(this.hintButton));

        this.setButtonsState(false);
        this.normalModeButton.active = true;
        this.normalModeButton.visible = true;
        this.hardModeButton.active = true;
        this.hardModeButton.visible = true;
        this.hintButton.active = true;
        this.hintButton.visible = true;
    }

    // 重置提示次数
    private void resetHints() {
        // 所有难度模式使用相同的提示次数
        this.hintsRemaining = 3;
        if (this.hintButton != null) {
            this.hintButton.setMessage(Component.translatable(PREFIX + "hint", this.hintsRemaining));
        }
    }

    // 重置游戏
    private void resetGame() {
        this.renderables.clear();
        this.gameTime = 0;
        this.movesCount = 0;
        this.eliminationsCount = 0;
        this.gameOver = false;
        this.buttonsAdded = false;
        this.playingVictoryCelebration = false;
        this.celebrationStartTime = 0;
        this.resetHints();
        this.runeGameMap = this.initMap();
        this.runeGameMap.checkAll();
        this.setButtonsState(false);
        this.flag = false;

        // 游戏开始后禁用难度选择按钮，防止意外切换
        if (this.normalModeButton != null) this.normalModeButton.active = false;
        if (this.hardModeButton != null) this.hardModeButton.active = false;
    }

    // 显示提示
    private void showHint() {
        if (this.runeGameMap == null) return;

        // 找到一个可点击的非灰色符文
        RuneGameBrand brandToHighlight = null;

        outer:
        for (RuneGameLayer layer : this.runeGameMap.getLayers()) {
            RuneGameBrand[][] brands = layer.getBrands();
            for (RuneGameBrand[] runeGameBrands : brands) {
                for (RuneGameBrand brand : runeGameBrands) {
                    if (brand != null && brand.hasBrand() != 0 && !brand.isGray()) {
                        // 检查是否有相同类型的符文在消除区域
                        boolean hasSimilar = false;
                        for (RuneGameBrand slotBrand : this.runeGameEliminate.getSlots()) {
                            if (slotBrand.getTexture().equals(brand.getTexture())) {
                                hasSimilar = true;
                                break;
                            }
                        }

                        if (hasSimilar) {
                            brandToHighlight = brand;
                            break outer;
                        }
                    }
                }
            }
        }

        // 如果没有找到匹配的，选择任意一个可点击的符文
        if (brandToHighlight == null) {
            outer:
            for (RuneGameLayer layer : this.runeGameMap.getLayers()) {
                RuneGameBrand[][] brands = layer.getBrands();
                for (RuneGameBrand[] runeGameBrands : brands) {
                    for (RuneGameBrand brand : runeGameBrands) {
                        if (brand != null && brand.hasBrand() != 0 && !brand.isGray()) {
                            brandToHighlight = brand;
                            break outer;
                        }
                    }
                }
            }
        }

        if (brandToHighlight != null) {
            this.highlightedBrand = brandToHighlight;
            this.highlightStartTime = System.currentTimeMillis();
            if (this.minecraft != null) {
                this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_PLING, 1.0F));
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(BACKGROUND, 0, 0, 0, 0, this.width, this.height, this.width, this.height);
        if (this.flag) {
            this.gameTime = 0;
            this.movesCount = 0;
            this.eliminationsCount = 0;
            this.flag = false;
            this.gameOver = false;
            this.renderables.clear();
            resetHints();
            this.runeGameMap = this.initMap();
            this.runeGameMap.checkAll();

            // 游戏初始化后禁用难度选择按钮
            if (this.normalModeButton != null) this.normalModeButton.active = false;
            if (this.hardModeButton != null) this.hardModeButton.active = false;
        }

        int slotsX = this.width / 2 - this.checkAreaWidth / 2;
        int slotsY = this.height - this.checkAreaHeight - 20;
        guiGraphics.blit(SLOTS, slotsX, slotsY, 0, 0, this.checkAreaWidth, this.checkAreaHeight, this.checkAreaWidth, this.checkAreaHeight);
        this.runeGameEliminate.render(guiGraphics, this.width / 2 - checkAreaWidth / 2, this.height - this.checkAreaHeight - 20);

        // 渲染游戏统计信息
        if (!this.gameOver) {
            Component timeText = formatElapsedTime(this.gameTime).withStyle(ChatFormatting.WHITE);
            Component movesText = Component.literal(String.valueOf(this.movesCount)).withStyle(ChatFormatting.WHITE);
            Component elimText = Component.literal(String.valueOf(this.eliminationsCount)).withStyle(ChatFormatting.WHITE);

            guiGraphics.drawString(this.font,
                Component.translatable(PREFIX + "stats", timeText, movesText, elimText),
                10, this.height - 40, 16777215);

            // 显示当前难度
            Component diffText = Component.translatable(PREFIX + this.difficulty.name().toLowerCase())
                .withStyle(this.difficulty.getColor());
            guiGraphics.drawString(this.font, diffText, 10, 40, 16777215);
        }

        if (this.gameOver) {
            if (this.playAgainButton != null && this.quitGameButton != null && !this.buttonsAdded) {
                this.buttonList.add(this.addRenderableWidget(this.playAgainButton));
                this.buttonList.add(this.addRenderableWidget(this.quitGameButton));
                this.setButtonsState(true);
                this.buttonsAdded = true;
            }

            PoseStack pose = guiGraphics.pose();
            pose.pushPose();
            pose.scale(2.5F, 2.5F, 2.5F);
            int color = this.gameStatus == GameStatus.WIN ? 5635925 : 16733525;
            Component gameOverText = this.gameStatus == GameStatus.WIN ? GAME_WIN : GAME_LOST;
            guiGraphics.drawCenteredString(this.font, gameOverText, this.width / 5, 10, color);
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
                guiGraphics.drawCenteredString(this.font, currentGameInfo, this.width / 2, 70, 16777215);

                // 显示游戏统计
                Component movesText = Component.literal(String.valueOf(this.movesCount)).withStyle(ChatFormatting.YELLOW);
                Component elimText = Component.literal(String.valueOf(this.eliminationsCount)).withStyle(ChatFormatting.YELLOW);
                Component statsInfo = Component.translatable(PREFIX + "final_stats", movesText, elimText);
                guiGraphics.drawCenteredString(this.font, statsInfo, this.width / 2, 90, 16777215);

                // 显示总体统计数据
                if (player != null) {
                    int totalMoves = player.getStats().getValue(Stats.CUSTOM.get(TAStats.RUNE_GAME_MOVE_COUNT.get()));
                    int totalEliminations = player.getStats().getValue(Stats.CUSTOM.get(TAStats.RUNE_GAME_ELIMINATION_COUNT.get()));
                    Component totalMovesText = Component.literal(String.valueOf(totalMoves)).withStyle(ChatFormatting.GREEN);
                    Component totalElimText = Component.literal(String.valueOf(totalEliminations)).withStyle(ChatFormatting.GREEN);
                    Component totalStatsInfo = Component.translatable(PREFIX + "total_stats", totalMovesText, totalElimText);
                    guiGraphics.drawCenteredString(this.font, totalStatsInfo, this.width / 2, 110, 16777215);
                }

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

        // 渲染提示高亮
        if (this.highlightedBrand != null && !this.gameOver) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - this.highlightStartTime < 2000) { // 高亮持续2秒
                // 绘制闪烁边框
                int x = this.highlightedBrand.getRectangle().x();
                int y = this.highlightedBrand.getRectangle().y();
                int size = RuneGameBrand.BRAND_SIZE;

                // 脉动效果
                float pulseIntensity = (float)Math.sin((currentTime - this.highlightStartTime) / 200.0) * 0.5f + 0.5f;
                int color = 0xFFFFFF00 | (int)(pulseIntensity * 255) << 24;

                // 绘制高亮边框
                guiGraphics.fill(x - 2, y - 2, x + size + 2, y - 1, color); // 上
                guiGraphics.fill(x - 2, y + size + 1, x + size + 2, y + size + 2, color); // 下
                guiGraphics.fill(x - 2, y - 1, x - 1, y + size + 1, color); // 左
                guiGraphics.fill(x + size + 1, y - 1, x + size + 2, y + size + 1, color); // 右
            } else {
                this.highlightedBrand = null;
            }
        }
    }

    @Override
    public void tick() {
        if (!this.gameOver) {
            PacketDistributor.sendToServer(new RuneGameAwardStatC2SPacket(1));
            this.gameTime++;
        }

        // 处理胜利庆祝音效的延迟播放
        if (playingVictoryCelebration) {
            int elapsed = this.gameTime - (int)celebrationStartTime;

            // 间隔6个游戏刻播放第二个烟花音效
            if (elapsed == 6) {
                this.playFireworkSound(1.1F);
            }
            // 间隔12个游戏刻播放第三个烟花音效
            else if (elapsed == 12) {
                this.playFireworkSound(1.0F);

                // 播放大爆炸音效
                if (this.minecraft != null) {
                    this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.FIREWORK_ROCKET_LARGE_BLAST, 1.2F));
                }

                // 庆祝结束
                this.playingVictoryCelebration = false;
            }
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
        // 这个方法初始化符文游戏地图，并为每一层添加水平偏移
        // 每一层相对于下一层向右偏移1像素，这样玩家可以隐约看到下层符文的边缘
        // 这增加了游戏的策略性，让玩家可以提前规划消除顺序
        boolean isHardMode = this.difficulty == Difficulty.HARD;
        int runeTypesLimit = isHardMode ? 12 : 6;

        // 计算总符文数并确保是3的倍数
        int totalRunes = getValidCount(this.level);
        if (totalRunes % 3 != 0) {
            // 调整关卡大小为3的倍数，这样才能确保完美消除
            totalRunes = (totalRunes / 3) * 3;
        }

        // 创建符文游戏地图
        RuneGameMap runeGameMap = new RuneGameMap(this.level);

        // 设置符文类型限制和难度模式
        runeGameMap.setRuneTypesLimit(runeTypesLimit);
        runeGameMap.setHardMode(isHardMode);

        // 创建自定义的符文列表，确保符文数量是3的倍数
        List<RuneGameBrand> brandList = RuneGameBrand.randomBrandList(this.level, runeTypesLimit, isHardMode);

        // 重新初始化地图，使用我们自定义的符文列表
        runeGameMap.initWithCustomBrands(brandList);

        for (int i = 0; i < runeGameMap.getLayers().length; i++) {
            RuneGameLayer layer = runeGameMap.getLayers()[i];
            RuneGameBrand[][] layerBrands = layer.getBrands();

            // 计算当前层的最大行数和列数
            int maxRows = layerBrands.length;
            int maxCols = 0;
            for (RuneGameBrand[] brandRow : layerBrands) {
                maxCols = Math.max(maxCols, brandRow.length);
            }

            // 计算居中的偏移量，Y轴上移30像素避免遮挡消除栏
            int offsetX = (this.width - maxCols * RuneGameBrand.BRAND_SIZE) / 2;
            int offsetY = (this.height - maxRows * RuneGameBrand.BRAND_SIZE) / 2 - 30;

            // 添加层级水平偏移，每层相对于下一层向右偏移1像素
            // 层级编号是从上到下递增的，所以层级越高，偏移量越大
            // 最底层(i=runeGameLayers.length-1)偏移为0，每上一层增加1像素
            int layerOffset = (runeGameMap.getLayers().length - 1 - i);

            for (int row = 0; row < layerBrands.length; row++) {
                for (int col = 0; col < layerBrands[row].length; col++) {
                    RuneGameBrand brand = layerBrands[row][col];
                    if (brand != null && brand.hasBrand() != 0) {
                        int x = RuneGameBrand.BRAND_SIZE * col;
                        int y = RuneGameBrand.BRAND_SIZE * row;
                        int x1 = offsetX + x + layerOffset; // 添加层级偏移
                        int y1 = offsetY + y;
                        int finalCol = col;
                        int finalRow = row;
                        RuneGameButton elementImageButton = new RuneGameButton(x1, y1, (button) -> {
                            if (!brand.isGray()) {
                                this.removeWidget(button);
                                layerBrands[finalRow][finalCol] = null;
                                this.runeGameEliminate.addSlot(brand);
                                this.movesCount++; // 增加移动次数
                                // 记录移动次数统计
                                PacketDistributor.sendToServer(new RuneGameAwardStatC2SPacket(2));

                                // 检查是否有消除发生
                                int eliminatedGroups = this.runeGameEliminate.eliminate();

                                // 如果有消除，增加消除计数并发送统计
                                if (eliminatedGroups > 0) {
                                    this.eliminationsCount += eliminatedGroups;
                                    // 对每个消除的组发送一次统计
                                    for (int j = 0; j < eliminatedGroups; j++) {
                                        PacketDistributor.sendToServer(new RuneGameAwardStatC2SPacket(3));
                                    }
                                }

                                int size = this.runeGameEliminate.getSlots().size();

                                // 所有模式使用相同的消除栏容量限制
                                int failLimit = 7;

                                if (size >= failLimit) {
                                    PacketDistributor.sendToServer(new RuneGameAwardStatC2SPacket(0));
                                    this.gameStatus = GameStatus.LOST;
                                    this.gameOver = true;

                                    // 播放失败音效
                                    this.playDefeatSound();
                                }

                                // 检查游戏是否获胜
                                checkWinCondition();
                            }

                            runeGameMap.checkAll();
                            // 清除高亮
                            this.highlightedBrand = null;
                        });
                        brand.bindButton(elementImageButton).setPos(x1, y1);
                        elementImageButton.setBrand(brand);
                    }
                }
            }
        }

        runeGameMap.checkAll();
        return runeGameMap;
    }

    private void checkWinCondition() {
        boolean allBrandsCleared = true;

        // 检查游戏区域是否还有牌
        if (this.runeGameMap != null) {
            for (RuneGameLayer layer : this.runeGameMap.getLayers()) {
                RuneGameBrand[][] brands = layer.getBrands();
                for (RuneGameBrand[] row : brands) {
                    for (RuneGameBrand brand : row) {
                        if (brand != null && brand.hasBrand() != 0 && !brand.isGray()) {
                            // 如果还有非灰色的牌，游戏尚未结束
                            allBrandsCleared = false;
                            break;
                        }
                    }
                    if (!allBrandsCleared) break;
                }
                if (!allBrandsCleared) break;
            }
        }

        // 如果所有牌都被清除，玩家获胜条件检查
        if (allBrandsCleared) {
            // 消除最后可能的牌组
            this.runeGameEliminate.eliminate();

            // 获取消除区域中剩余牌的数量
            int remainingBrands = this.runeGameEliminate.getSlots().size();

            // 如果消除区域为空或者只剩余1-2个牌，玩家获胜
            if (remainingBrands <= 2) {
                PacketDistributor.sendToServer(new RuneGameAwardStatC2SPacket(0));
                PacketDistributor.sendToServer(new RuneGameWinC2SPacket(Boolean.TRUE));
                PacketDistributor.sendToServer(new RuneGameTimeConsumingRecordC2SPacket(this.gameTime));
                this.gameStatus = GameStatus.WIN;
                this.gameOver = true;
                this.playVictoryCelebration();
            }
        }
    }

    private int getValidCount(int[][][] level) {
        return Arrays.stream(level).flatMap(Arrays::stream).flatMapToInt(Arrays::stream)
                .reduce(0, (count, value) -> value != 0 ? count + 1 : count);
    }

    private void playFireworkSound(float pitch) {
        if (this.minecraft != null) {
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.FIREWORK_ROCKET_BLAST, pitch));
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.FIREWORK_ROCKET_LARGE_BLAST_FAR, pitch * 0.8F));
        }
    }

    private void playVictoryCelebration() {
        if (this.minecraft != null) {
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F));
            this.playFireworkSound(0.9F);

            // 使用游戏的计时器系统来延迟播放后续音效
            // 设置延迟播放标志和时间
            this.celebrationStartTime = this.gameTime;
            this.playingVictoryCelebration = true;
        }
    }

    private void playDefeatSound() {
        if (this.minecraft != null) {
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_BASS, 0.6F));
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ANVIL_LAND, 0.7F));
        }
    }

    private enum GameStatus {
        WIN, LOST, PLAYING
    }
    
    private enum Difficulty {
        NORMAL(ChatFormatting.YELLOW),
        HARD(ChatFormatting.RED);
        
        private final ChatFormatting color;
        
        Difficulty(ChatFormatting color) {
            this.color = color;
        }
        
        public ChatFormatting getColor() {
            return this.color;
        }
    }

}