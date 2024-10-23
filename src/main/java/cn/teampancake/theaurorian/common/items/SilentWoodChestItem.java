package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.client.renderer.block.SilentWoodChestItemRenderer;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class SilentWoodChestItem extends BlockItem {

    public SilentWoodChestItem() {
        super(TABlocks.SILENT_WOOD_CHEST.get(), TAItemProperties.get().addItemTag(TAItemTags.BUILDING_BLOCK));
    }

    public static class RenderChestItem implements IClientItemExtensions {

        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return new SilentWoodChestItemRenderer();
        }

    }

}