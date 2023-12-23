package cn.teampancake.theaurorian.client.rune_game.dao;

import java.util.List;
import java.util.Queue;

public class RuneGameLayer {
    protected RuneGameBrand[][] brands;
    protected int maxRow;
    protected int maxCol;
    protected RuneGameLayer parent;
    public RuneGameLayer(int[][] level, Queue<RuneGameBrand> brands) {
        this.maxRow = level.length;
        this.maxCol = level[0].length;
        this.brands = new RuneGameBrand[maxRow][maxCol];

        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                int hasBrand = level[i][j];
                if (hasBrand != 0) {
                    RuneGameBrand poll = brands.poll();
                    poll.setHasBrand(hasBrand);
                    this.brands[i][j] = poll;
                }else {
                    RuneGameBrand brand = new RuneGameBrand(RuneGameBrand.randomElement());
                    brand.setHasBrand(hasBrand);
                    this.brands[i][j] = brand;
                }
            }
        }
    }

    public RuneGameLayer(int[][] level, List<RuneGameBrand> brandList) {
        this.maxRow = level.length;
        this.maxCol = level[0].length;
        this.brands = new RuneGameBrand[maxRow][maxCol];

        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                int hasBrand = level[i][j];
                if (hasBrand != 0) {
                    RuneGameBrand brand = brandList.get(0);
                    brand.setHasBrand(hasBrand);
                    this.brands[i][j] = brand;
                    brandList.remove(0);
                }else {
                    RuneGameBrand brand = new RuneGameBrand(RuneGameBrand.randomElement());
                    brand.setHasBrand(hasBrand);
                    this.brands[i][j] = brand;
                }
            }
        }
    }

    public RuneGameBrand[][] getBrands() {
        return brands;
    }

    public RuneGameLayer getParent() {
        return parent;
    }

    public void setParent(RuneGameLayer parent) {
        this.parent = parent;
    }
}
