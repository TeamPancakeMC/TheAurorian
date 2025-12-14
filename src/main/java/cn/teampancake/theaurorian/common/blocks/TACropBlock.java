package cn.teampancake.theaurorian.common.blocks;

import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

import java.util.function.Supplier;

public class TACropBlock extends BeetrootBlock {

    private final Supplier<ItemNameBlockItem> cloneItem;

    public TACropBlock(Properties properties, Supplier<ItemNameBlockItem> cloneItem) {
        super(properties);
        this.cloneItem = cloneItem;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.cloneItem.get();
    }

}