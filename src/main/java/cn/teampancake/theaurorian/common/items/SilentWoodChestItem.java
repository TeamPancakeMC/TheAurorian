package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.client.renderer.block.SilentWoodChestItemRenderer;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class SilentWoodChestItem extends BlockItem {

    public SilentWoodChestItem() {
        super(TABlocks.SILENT_WOOD_CHEST.get(), new Properties());
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new RenderChestItem());
    }

    private static class RenderChestItem implements IClientItemExtensions {

        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return new SilentWoodChestItemRenderer();
        }

    }

}