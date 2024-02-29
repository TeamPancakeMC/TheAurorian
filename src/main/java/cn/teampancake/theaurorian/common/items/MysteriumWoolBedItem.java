package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.client.renderer.block.MysteriumWoolBedItemRenderer;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BedItem;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class MysteriumWoolBedItem extends BedItem {

    public MysteriumWoolBedItem() {
        super(TABlocks.MYSTERIUM_WOOL_BED.get(), new Properties());
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new RenderBedItem());
    }

    private static class RenderBedItem implements IClientItemExtensions {

        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return new MysteriumWoolBedItemRenderer();
        }

    }

}
