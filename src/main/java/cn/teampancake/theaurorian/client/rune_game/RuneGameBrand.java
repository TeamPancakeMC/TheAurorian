package cn.teampancake.theaurorian.client.rune_game;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.api.IRune;
import cn.teampancake.theaurorian.client.widget.RuneGameButton;
import cn.teampancake.theaurorian.common.registry.TARunes;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import com.mojang.blaze3d.systems.RenderSystem;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RuneGameBrand {

    public static final int BRAND_SIZE = 20;
    private static final int DIFF = 20;
    private final ResourceLocation texture;
    protected RuneGameRectangle runeGameRectangle;
    private RuneGameButton button;
    private boolean isGray = false;
    private int hasBrand = 0;
    private int x;
    private int y;
    private float scale = 1.0f;
    private float rotation = 0.0f;
    private long lastUpdateTime = 0;

    public RuneGameBrand(IRune rune) {
        this.texture = TheAurorian.prefix("textures/gui/rune/sprite_rune_" + rune.name() + ".png");
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public static IRune randomElement() {
        Collection<DeferredHolder<IRune, ? extends IRune>> entries = TARunes.RUNES.getEntries();
        List<IRune> runesList = entries.stream()
                .map(DeferredHolder::get)
                .collect(java.util.stream.Collectors.toList());
        return runesList.get(RandomSource.create().nextInt(runesList.size()));
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

    public void updateEffects() {
        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - lastUpdateTime;
        
        float pulsePhase = (currentTime % 2000) / 2000.0f;
        this.scale = 1.0f + 0.1f * (float)Math.sin(pulsePhase * Math.PI * 2);
        
        this.rotation = 0.0f;
        
        this.lastUpdateTime = currentTime;
    }

    public void renderBrand(@NotNull GuiGraphics graphics) {
        updateEffects();
        
        if (!this.isGray && this.hasBrand != 0) {
            renderWithEffects(graphics, x, y, 1.0f);
        } else if (this.isGray) {
            graphics.blit(this.texture, x, y, BRAND_SIZE, DIFF, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE * 2);
        } else {
            graphics.blit(this.texture, x, y, 0, 0, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE * 2);
        }
    }

    public void renderBrandEliminate(@NotNull GuiGraphics graphics, int x, int y) {
        graphics.blit(this.texture, x, y, 0, 0, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE * 2);
    }
    
    public void renderBrandEliminateWithAlpha(@NotNull GuiGraphics graphics, int x, int y, float alpha) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        
        graphics.blit(this.texture, x, y, 0, 0, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE * 2);
        
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
    
    private void renderWithEffects(GuiGraphics graphics, int x, int y, float alpha) {
        graphics.pose().pushPose();
        
        graphics.pose().translate(x + BRAND_SIZE / 2.0f, y + BRAND_SIZE / 2.0f, 0);
        graphics.pose().scale(scale, scale, 1.0f);
        graphics.pose().translate(-BRAND_SIZE / 2.0f, -BRAND_SIZE / 2.0f, 0);
        
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        
        graphics.blit(this.texture, 0, 0, 0, 0, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE, BRAND_SIZE * 2);
        
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        
        graphics.pose().popPose();
    }

    /**
     * 根据游戏级别创建随机符文列表
     * @param level 游戏级别
     * @return 随机符文列表
     */
    public static List<RuneGameBrand> randomBrandList(int[][][] level) {
        return randomBrandList(level, 12, false);
    }
    
    /**
     * 根据游戏级别创建随机符文列表，可限制符文类型数量
     * @param level 游戏级别
     * @param typeLimit 符文类型数量限制（最大为12）
     * @param isHardMode 是否为困难模式（影响符文数量）
     * @return 随机符文列表
     */
    public static List<RuneGameBrand> randomBrandList(int[][][] level, int typeLimit, boolean isHardMode) {
        // 有效的符文数量
        int validCount = getValidCount(level);
        
        // 如果是困难模式，增加25%的符文数量
        if (isHardMode) {
            validCount = (int)(validCount * 1.25);
        }
        
        // 获取所有可用的符文类型
        List<IRune> availableRunes = TARunes.RUNES.getEntries().stream()
                .map(DeferredHolder::get)
                .collect(java.util.stream.Collectors.toList());
        
        // 限制符文类型数量
        int actualTypeLimit = Math.min(typeLimit, availableRunes.size());
        
        // 随机选择指定数量的符文类型
        List<IRune> selectedRunes = new java.util.ArrayList<>(availableRunes);
        Collections.shuffle(selectedRunes);
        selectedRunes = selectedRunes.subList(0, actualTypeLimit);
        
        // 计算每种符文的数量，确保能被3整除
        int totalSets = validCount / 3;
        int[] setDistribution = new int[actualTypeLimit];
        
        // 最少每种符文一组（3个）
        for (int i = 0; i < actualTypeLimit && totalSets > 0; i++) {
            setDistribution[i] = 1;
            totalSets--;
        }
        
        // 分配剩余的组
        RandomSource random = RandomSource.create();
        while (totalSets > 0) {
            int index = random.nextInt(actualTypeLimit);
            setDistribution[index]++;
            totalSets--;
        }
        
        // 创建符文列表
        List<RuneGameBrand> brandList = Lists.newArrayList();
        for (int i = 0; i < actualTypeLimit; i++) {
            IRune rune = selectedRunes.get(i);
            for (int j = 0; j < setDistribution[i] * 3; j++) {
                RuneGameBrand brand = new RuneGameBrand(rune);
                brandList.add(brand);
            }
        }
        
        // 如果还有余数，添加一些填充符文(这种情况实际上不应该发生，因为我们确保所有牌都是3的倍数)
        int remainder = validCount - brandList.size();
        if (remainder > 0) {
            for (int i = 0; i < remainder; i++) {
                int runeIndex = random.nextInt(actualTypeLimit);
                IRune fillerRune = selectedRunes.get(runeIndex);
                RuneGameBrand fillerBrand = new RuneGameBrand(fillerRune);
                brandList.add(fillerBrand);
            }
        }
        
        // 打乱顺序
        Collections.shuffle(brandList);
        
        return brandList;
    }

    public static int getValidCount(int[][][] level) {
        return Arrays.stream(level).flatMap(Arrays::stream).flatMapToInt(Arrays::stream)
                .reduce(0, (count, value) -> value != 0 ? count + 1 : count);
    }
}