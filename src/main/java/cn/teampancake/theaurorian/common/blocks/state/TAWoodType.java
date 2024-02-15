package cn.teampancake.theaurorian.common.blocks.state;

import net.minecraft.world.level.block.state.properties.WoodType;

public class TAWoodType {

    public static final WoodType SILENT_WOOD = WoodType.register(new WoodType("silent_wood", TABlockSetType.SILENT_TREE));
    public static final WoodType WEEPING_WILLOW = WoodType.register(new WoodType("weeping_willow", TABlockSetType.WEEPING_WILLOW));
    public static final WoodType CURTAIN_TREE = WoodType.register(new WoodType("curtain_tree", TABlockSetType.CURTAIN_TREE));
    public static final WoodType CURSED_FROST_TREE = WoodType.register(new WoodType("cursed_frost_tree", TABlockSetType.CURSED_FROST_TREE));

}