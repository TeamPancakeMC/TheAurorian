package cn.teampancake.theaurorian.common.blocks.state;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TAWoodType {

    public static final WoodType SILENT_WOOD = WoodType.register(new WoodType(AurorianMod.MOD_ID + ":silent_wood", TABlockSetType.SILENT_TREE));
    public static final WoodType WEEPING_WILLOW = WoodType.register(new WoodType(AurorianMod.MOD_ID + ":weeping_willow", TABlockSetType.WEEPING_WILLOW));
    public static final WoodType CURTAIN_TREE = WoodType.register(new WoodType(AurorianMod.MOD_ID + ":curtain_tree", TABlockSetType.CURTAIN_TREE));
    public static final WoodType CURSED_FROST_TREE = WoodType.register(new WoodType(AurorianMod.MOD_ID + ":cursed_frost_tree", TABlockSetType.CURSED_FROST_TREE));

}