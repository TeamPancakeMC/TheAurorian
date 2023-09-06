package cn.teampancake.theaurorian.common.blocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

import java.util.function.Supplier;

public class AbstractCropBlock extends BeetrootBlock {

    private final Supplier<Item> cloneItem;

    public AbstractCropBlock(Properties properties, Supplier<Item> cloneItem) {
        super(properties);
        this.cloneItem = cloneItem;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.cloneItem.get();
    }

}