package cn.teampancake.theaurorian.client.rune_game;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.client.Minecraft;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
        if (slots == null || slots.isEmpty()) {
            return 0;
        }
        
        int eliminatedGroups = 0; // 记录消除的组数
        
        try {
            // 使用更高效的分组方法
            Map<String, List<RuneGameBrand>> brandGroups = slots.stream()
                    .filter(brand -> brand != null) // 过滤掉null值
                    .collect(Collectors.groupingBy(brand -> brand.getTexture().toString()));
            
            System.out.println("符文分组：" + brandGroups.size() + " 组");
            for (Map.Entry<String, List<RuneGameBrand>> entry : brandGroups.entrySet()) {
                System.out.println("符文类型：" + entry.getKey() + ", 数量：" + entry.getValue().size());
            }
            
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
                    System.out.println("消除符文组：" + entry.getKey() + ", 数量：" + entry.getValue().size());
                    
                    // 将要消除的品牌添加到动画列表
                    animatingBrands.addAll(entry.getValue());
                    // 从slots中移除这些品牌
                    final String textureKey = entry.getKey();
                    this.slots.removeIf(next -> next != null && textureKey.equals(next.getTexture().toString()));
                    // 记录消除时间
                    lastEliminateTime = System.currentTimeMillis();
                    // 播放消除音效
                    if (!hasEliminationSound) {
                        try {
                            // 使用静态工厂方法创建SimpleSoundInstance
                            net.minecraft.resources.ResourceLocation soundId = net.minecraft.resources.ResourceLocation.tryParse("minecraft:entity.experience_orb.pickup");
                            if (soundId != null) {
                                Minecraft.getInstance().getSoundManager().play(
                                    net.minecraft.client.resources.sounds.SimpleSoundInstance.forUI(
                                        net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(soundId),
                                        1.0F
                                    )
                                );
                            }
                            hasEliminationSound = true;
                        } catch (Exception e) {
                            // 忽略音效播放错误
                        }
                    }
                }
            }
            
            // 如果没有消除，重置音效标志
            if (!hasElimination) {
                hasEliminationSound = false;
            }
            
            System.out.println("消除组数：" + eliminatedGroups);
        } catch (Exception e) {
            // 捕获并忽略任何异常，防止游戏崩溃
            System.err.println("Error in RuneGameEliminate.eliminate(): " + e.getMessage());
        }
        
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
        if (!animatingBrands.isEmpty() && currentTime - lastEliminateTime < 500) {
            // 动画持续500毫秒
            float progress = (currentTime - lastEliminateTime) / 500.0f;
            
            for (int i = 0; i < animatingBrands.size(); i++) {
                RuneGameBrand brand = animatingBrands.get(i);
                // 计算动画位置和透明度
                int animX = x1 + (i * 20) + (i * 2 + i);
                int animY = y1 - (int)(20 * progress); // 向上移动
                float alpha = 1.0f - progress; // 逐渐消失
                
                // 渲染带有动画效果的品牌
                brand.renderBrandEliminateWithAlpha(graphics, animX, animY, alpha);
            }
            
            // 如果动画结束，清除动画列表
            if (progress >= 1.0f) {
                animatingBrands.clear();
            }
        } else {
            animatingBrands.clear();
        }
    }

    public List<RuneGameBrand> getSlots() {
        return this.slots;
    }
}