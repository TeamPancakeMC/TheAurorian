package cn.teampancake.theaurorian.common.registry;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class TATreeGrower {

    public static final TreeGrower SILENT_TREE = new TreeGrower("silent_tree",
            Optional.empty(), Optional.of(TAConfiguredFeatures.SILENT_TREE), Optional.empty());
    public static final TreeGrower CURTAIN_TREE = new TreeGrower("curtain_tree",
            Optional.empty(), Optional.empty(), Optional.empty());
    public static final TreeGrower CURSED_FROST_TREE = new TreeGrower("cursed_frost_tree",
            Optional.empty(), Optional.empty(), Optional.empty());

}