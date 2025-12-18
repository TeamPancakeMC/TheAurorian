package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GeoHandheldToolRenderer<T extends Item & GeoAnimatable> implements GeoRenderProvider {

    private final ResourceLocation assetPath;

    public GeoHandheldToolRenderer(ResourceLocation assetPath) {
        this.assetPath = assetPath;
    }

    @Override
    public @Nullable BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
        return new GeoItemRenderer<>(new CustomItemModel(this.assetPath));
    }

    private class CustomItemModel extends DefaultedItemGeoModel<T> {

        public CustomItemModel(ResourceLocation assetSubpath) {
            super(assetSubpath);
        }

        @Override
        public ResourceLocation getTextureResource(T animatable) {
            return this.buildFormattedTexturePath(TheAurorian.prefix(assetPath.getPath() + "_3d"));
        }

    }

}