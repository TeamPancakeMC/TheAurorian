package cn.teampancake.theaurorian.client.rune_game;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RuneGameMap {

    protected RuneGameLayer[] runeGameLayers;

    public RuneGameMap(int[][][] level) {
        List<RuneGameBrand> brands = RuneGameBrand.randomBrandList(level);
        this.runeGameLayers = new RuneGameLayer[level.length];
        for (int i = 0; i < level.length; i++) {
            this.runeGameLayers[i] = new RuneGameLayer(level[i], brands);
        }

        for (int i = 0; i < this.runeGameLayers.length; i++) {
            RuneGameLayer runeGameLayer = this.runeGameLayers[i];
            if (i != 0) {
                runeGameLayer.setParent(this.runeGameLayers[i - 1]);
            }
        }
    }

    public RuneGameLayer[] getLayers() {
        return this.runeGameLayers;
    }

    public boolean check(RuneGameBrand brand, RuneGameLayer runeGameLayer) {
        boolean hasIntersect = Arrays.stream(runeGameLayer.getBrands()).flatMap(Arrays::stream)
                .filter(Objects::nonNull).filter(brand1 -> brand1.hasBrand() != 0)
                .anyMatch(brand1 -> brand.getRectangle().intersects(brand1.getRectangle()));

        if (hasIntersect) {
            return true;
        }

        if (runeGameLayer.getParent() != null) {
            return check(brand, runeGameLayer.getParent());
        } else {
            return false;
        }
    }

    public void checkAll() {
        for (RuneGameLayer runeGameLayer : this.runeGameLayers) {
            Arrays.stream(runeGameLayer.getBrands()).flatMap(Arrays::stream).filter(Objects::nonNull)
                    .filter(brand -> brand.hasBrand() != 0 && runeGameLayer.getParent() != null)
                    .forEach(brand -> brand.setGray(check(brand, runeGameLayer.getParent())));
        }
    }

}