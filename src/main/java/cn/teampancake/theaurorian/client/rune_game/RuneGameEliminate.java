package cn.teampancake.theaurorian.client.rune_game;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RuneGameEliminate {

    protected List<RuneGameBrand> slots = Lists.newArrayList();
    private List<RuneGameBrand> animatingBrands = Lists.newArrayList(); // 用于动画效果的品牌
    private long lastEliminateTime = 0; // 上次消除的时间
    private boolean hasEliminationSound = false; // 是否播放了消除音效

    public void addSlot(RuneGameBrand brand) {
        this.slots.add(brand);
        this.slots.sort(Comparator.comparing(brand1 -> brand1.getTexture().toString()));
        // 不自动调用eliminate，因为我们会在外部显式调用
    }

    public int eliminate() {
        // 防御性检查，确保slots不为空
        if (this.slots == null || this.slots.isEmpty()) {
            return 0;
        }
        
        int eliminatedGroups = 0; // 记录消除的组数
        
        try {
            // 使用更高效的分组方法
            Map<String, List<RuneGameBrand>> brandGroups = this.slots.stream().filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(brand -> brand.getTexture().toString()));
            boolean hasElimination = false;
            // 检查是否有可以消除的组
            for (Map.Entry<String, List<RuneGameBrand>> entry : brandGroups.entrySet()) {
                // 防御性检查，确保entry和value不为null
                if (entry == null || entry.getValue() == null) {
                    continue;
                }
                
                // 只要有3个或更多相同符文就消除
                if (entry.getValue().size() >= 3) {
                    hasElimination = true;
                    eliminatedGroups++; // 增加消除组数
                    
                    // 将要消除的品牌添加到动画列表
                    this.animatingBrands.addAll(entry.getValue());
                    // 从slots中移除这些品牌
                    final String textureKey = entry.getKey();
                    this.slots.removeIf(next -> next != null && textureKey.equals(next.getTexture().toString()));
                    // 记录消除时间
                    this.lastEliminateTime = System.currentTimeMillis();
                    // 播放消除音效
                    if (!this.hasEliminationSound) {
                        try {
                            // 使用静态工厂方法创建SimpleSoundInstance
                            ResourceLocation soundId = ResourceLocation.tryParse("minecraft:entity.experience_orb.pickup");
                            if (soundId != null) {
                                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(BuiltInRegistries.SOUND_EVENT.get(soundId), 1.0F));
                            }
                            this.hasEliminationSound = true;
                        } catch (Exception ignored) {}
                    }
                }
            }
            
            // 如果没有消除，重置音效标志
            if (!hasElimination) {
                this.hasEliminationSound = false;
            }

        } catch (Exception ignored) {}
        return eliminatedGroups;
    }

    public void render(GuiGraphics graphics, int x, int y) {
        int x1 = x + 4;
        int y1 = y + 4;
        
        // 渲染常规插槽
        for (int i = 0; i < this.slots.size(); i++) {
            this.slots.get(i).renderBrandEliminate(graphics, (x1 + i * 20) + (i * 2 + i), y1);
        }
        
        // 渲染动画效果
        long currentTime = System.currentTimeMillis();
        if (!this.animatingBrands.isEmpty() && currentTime - this.lastEliminateTime < 500) {
            // 动画持续500毫秒
            float progress = (currentTime - this.lastEliminateTime) / 500.0f;
            
            for (int i = 0; i < this.animatingBrands.size(); i++) {
                RuneGameBrand brand = this.animatingBrands.get(i);
                // 计算动画位置和透明度
                int animX = x1 + (i * 20) + (i * 2 + i);
                int animY = y1 - (int)(20 * progress); // 向上移动
                float alpha = 1.0f - progress; // 逐渐消失
                
                // 渲染带有动画效果的品牌
                brand.renderBrandEliminateWithAlpha(graphics, animX, animY, alpha);
            }
            
            // 如果动画结束，清除动画列表
            if (progress >= 1.0f) {
                this.animatingBrands.clear();
            }
        } else {
            this.animatingBrands.clear();
        }
    }

    public List<RuneGameBrand> getSlots() {
        return this.slots;
    }

}