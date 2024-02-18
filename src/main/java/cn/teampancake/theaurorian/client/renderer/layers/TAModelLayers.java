package cn.teampancake.theaurorian.client.renderer.layers;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class TAModelLayers {

    public static final ModelLayerLocation LUNA_CIRCLE = register("luna_circle");
    public static final ModelLayerLocation BREAD_BEAST = register("bread_beast");
    public static final ModelLayerLocation ICEFIELD_DEER = register("icefield_deer");
    public static final ModelLayerLocation BLUE_TAIL_WOLF = register("blue_tail_wolf");
    public static final ModelLayerLocation MOON_FISH = register("moon_fish");
    public static final ModelLayerLocation AURORIAN_WINGED_FISH = register("aurorian_winged_fish");
    public static final ModelLayerLocation AURORIAN_RABBIT = register("aurorian_rabbit");
    public static final ModelLayerLocation AURORIAN_SHEEP = register("aurorian_sheep");
    public static final ModelLayerLocation AURORIAN_PIG = register("aurorian_pig");
    public static final ModelLayerLocation AURORIAN_COW = register("aurorian_cow");
    public static final ModelLayerLocation AURORIAN_SHEEP_BABY = register("aurorian_sheep_baby");
    public static final ModelLayerLocation AURORIAN_PIG_BABY = register("aurorian_pig_baby");
    public static final ModelLayerLocation AURORIAN_COW_BABY = register("aurorian_cow_baby");
    public static final ModelLayerLocation AURORIAN_PIXIE = register("aurorian_pixie");
    public static final ModelLayerLocation AURORIAN_SLIME = register("aurorian_slime");
    public static final ModelLayerLocation AURORIAN_SLIME_OUTER = register("aurorian_slime", "outer");
    public static final ModelLayerLocation DISTURBED_HOLLOW = register("disturbed_hollow");
    public static final ModelLayerLocation UNDEAD_KNIGHT = register("undead_knight");
    public static final ModelLayerLocation SPIRIT = register("spirit");
    public static final ModelLayerLocation MOON_ACOLYTE = register("moon_acolyte");
    public static final ModelLayerLocation SPIDERLING = register("spiderling");
    public static final ModelLayerLocation CRYSTALLINE_SPRITE = register("crystalline_sprite");
    public static final ModelLayerLocation CAVE_DWELLER = register("cave_dweller");
    public static final ModelLayerLocation ROCK_HAMMER = register("rock_hammer");
    public static final ModelLayerLocation TONG_SCORPION = register("tong_scorpion");
    public static final ModelLayerLocation SNOW_TUNDRA_GIANT_CRAB = register("snow_tundra_giant_crab");
    public static final ModelLayerLocation RUNESTONE_KEEPER = register("runestone_keeper");
    public static final ModelLayerLocation RUNESTONE_BOOKS = register("runestone_books");
    public static final ModelLayerLocation SPIDER_MOTHER = register("spider_mother");
    public static final ModelLayerLocation MOON_QUEEN = register("moon_queen");
    public static final ModelLayerLocation AURORIAN_STEEL_ARMOR = register("aurorian_steel_armor");
    public static final ModelLayerLocation CERULEAN_ARMOR = register("cerulean_armor");
    public static final ModelLayerLocation KNIGHT_ARMOR = register("knight_armor");
    public static final ModelLayerLocation SPECTRAL_ARMOR = register("spectral_armor");
    public static final ModelLayerLocation MYSTERIUM_ARMOR = register("mysterium_armor");

    private static ModelLayerLocation register(String path) {
        return register(path, "main");
    }

    private static ModelLayerLocation register(String path, String layer) {
        return new ModelLayerLocation(AurorianMod.prefix(path), layer);
    }

}