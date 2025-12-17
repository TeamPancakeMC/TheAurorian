package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class EnabledFeaturesBlockItem extends BlockItem {

    public EnabledFeaturesBlockItem(Block block, Properties properties) {
        super(block, properties.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.UNCOMMON));
    }

    @Override
    public boolean isEnabled(FeatureFlagSet enabledFeatures) {
        return true;
    }

}