package cn.teampancake.theaurorian.client.renderer.layers;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TAModelLayers {

    public static final ModelLayerLocation BREAD_BEAST = register("bread_beast");
    public static final ModelLayerLocation ICEFIELD_DEER = register("icefield_deer");
    public static final ModelLayerLocation MOON_FISH = register("moon_fish");
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
    public static final ModelLayerLocation CRYSTALLINE_SPRITE = register("crystalline_sprite");
    public static final ModelLayerLocation FLOWER_LEECH = register("flower_leech");
    public static final ModelLayerLocation FORGOTTEN_MAGIC_BOOK = register("forgotten_magic_book");
    public static final ModelLayerLocation HYPHA_WALKING_MUSHROOM = register("hypha_walking_mushroom");
    public static final ModelLayerLocation SPIKED_CHESTPLATE = register("spiked_chestplate");
    public static final ModelLayerLocation AURORIAN_SLIME_BOOTS = register("aurorian_slime_boots");
    public static final ModelLayerLocation AURORIAN_STEEL_ARMOR = register("aurorian_steel_armor");
    public static final ModelLayerLocation CERULEAN_ARMOR = register("cerulean_armor");
    public static final ModelLayerLocation KNIGHT_ARMOR = register("knight_armor");
    public static final ModelLayerLocation SPECTRAL_ARMOR = register("spectral_armor");
    public static final ModelLayerLocation MYSTERIUM_ARMOR = register("mysterium_armor");
    public static final ModelLayerLocation CRYSTAL_RUNE_ARMOR = register("crystal_rune_armor");
    public static final ModelLayerLocation MOON_QUEEN_SWORD = register("moon_queen_sword");

    private static ModelLayerLocation register(String path) {
        return register(path, "main");
    }

    private static ModelLayerLocation register(String path, String layer) {
        return new ModelLayerLocation(TheAurorian.prefix(path), layer);
    }

}