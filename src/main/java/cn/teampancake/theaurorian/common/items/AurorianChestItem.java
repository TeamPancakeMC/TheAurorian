package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.client.renderer.block.AurorainChestItemRenderer;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.List;

public class AurorianChestItem extends BlockItem {

    public AurorianChestItem() {
        super(TABlocks.AURORIAN_CHEST.get(), new Item.Properties().component(TADataComponents.ITEM_TAGS, List.of(TAItemTags.BUILDING_BLOCK)));
    }

    public static class RenderChestItem implements IClientItemExtensions {

        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return new AurorainChestItemRenderer();
        }

    }

}