package cn.teampancake.theaurorian.common.items;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class CereleanBucket extends BucketItem {
    public CereleanBucket(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
        builder.stacksTo(1);
    }

    //TODO Method Use

}
