package cn.teampancake.theaurorian.common.blocks.state;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TAWoodType {

    public static final WoodType SILENT = WoodType.register(new WoodType(AurorianMod.MOD_ID + ":silent", TABlockSetType.SILENT));
    public static final WoodType WEEPING_WILLOW = WoodType.register(new WoodType(AurorianMod.MOD_ID + ":weeping_willow", TABlockSetType.WEEPING_WILLOW));
    public static final WoodType CURTAIN = WoodType.register(new WoodType(AurorianMod.MOD_ID + ":curtain", TABlockSetType.CURTAIN));
    public static final WoodType CURSED_FROST = WoodType.register(new WoodType(AurorianMod.MOD_ID + ":cursed_frost", TABlockSetType.CURSED_FROST));

}