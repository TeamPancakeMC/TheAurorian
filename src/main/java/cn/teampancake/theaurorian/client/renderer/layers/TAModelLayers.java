package cn.teampancake.theaurorian.client.renderer.layers;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class TAModelLayers {

    public static final ModelLayerLocation LUNA_CIRCLE = register("luna_circle");
    public static final ModelLayerLocation AURORIAN_RABBIT = register("aurorian_rabbit");
    public static final ModelLayerLocation AURORIAN_SHEEP = register("aurorian_sheep");
    public static final ModelLayerLocation AURORIAN_PIG = register("aurorian_pig");
    public static final ModelLayerLocation AURORIAN_PIXIE = register("aurorian_pixie");
    public static final ModelLayerLocation AURORIAN_SLIME = register("aurorian_slime");
    public static final ModelLayerLocation AURORIAN_SLIME_OUTER = register("aurorian_slime", "outer");
    public static final ModelLayerLocation DISTURBED_HOLLOW = register("disturbed_hollow");
    public static final ModelLayerLocation UNDEAD_KNIGHT = register("undead_knight");
    public static final ModelLayerLocation SPIRIT = register("spirit");
    public static final ModelLayerLocation MOON_ACOLYTE = register("moon_acolyte");
    public static final ModelLayerLocation SPIDERLING = register("spiderling");
    public static final ModelLayerLocation CRYSTALLINE_SPRITE = register("crystalline_sprite");
    public static final ModelLayerLocation RUNESTONE_KEEPER = register("runestone_keeper");
    public static final ModelLayerLocation RUNESTONE_BOOKS = register("runestone_books");
    public static final ModelLayerLocation SPIDER_MOTHER = register("spider_mother");
    public static final ModelLayerLocation MOON_QUEEN = register("moon_queen");
    public static final ModelLayerLocation AURORIAN_STEEL_ARMOR = register("aurorian_steel_armor");
    public static final ModelLayerLocation CERULEAN_ARMOR = register("cerulean_armor");
    public static final ModelLayerLocation KNIGHT_ARMOR = register("knight_armor");
    public static final ModelLayerLocation SPECTRAL_ARMOR = register("spectral_armor");

    private static ModelLayerLocation register(String path) {
        return register(path, "main");
    }

    private static ModelLayerLocation register(String path, String layer) {
        return new ModelLayerLocation(AurorianMod.prefix(path), layer);
    }

}