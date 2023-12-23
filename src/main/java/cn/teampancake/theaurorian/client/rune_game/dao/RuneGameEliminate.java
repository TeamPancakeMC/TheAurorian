package cn.teampancake.theaurorian.client.rune_game.dao;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiGraphics;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RuneGameEliminate {
    protected List<RuneGameBrand> slots = Lists.newArrayList();

    public void addSlot(RuneGameBrand brand) {
        slots.add(brand);
        slots.sort(Comparator.comparing(brand1 -> brand1.getTexture().toString()));
        eliminate();
    }

    public void eliminate() {
        Map<String, List<RuneGameBrand>> collect = slots.stream().collect(Collectors.groupingBy((RuneGameBrand brand) -> brand.getTexture().toString()));
        collect.forEach((key, value) -> {
            if (value.size() >= 3) {
                slots.removeIf(next -> next.getTexture().toString().equals(key));
            }
        });
    }
    public void render(GuiGraphics graphics,int x,int y){
        int x1 = x + 4;
        int y1 = y + 4;

        for (int i = 0; i < slots.size(); i++) {
            slots.get(i).renderBrandEliminate(graphics,(x1 + i * 20) + (i * 2 + i) ,y1);
        }
    }

    public List<RuneGameBrand> getSlots() {
        return slots;
    }
}
