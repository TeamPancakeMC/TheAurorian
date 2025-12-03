package cn.teampancake.theaurorian.client.model.block;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class LumenCrystalModel<T extends GeoAnimatable> extends DefaultedBlockGeoModel<T> {

    public LumenCrystalModel(ResourceLocation assetSubpath) {
        super(assetSubpath);
    }

    @Override
    public ResourceLocation getModelResource(T animatable, @Nullable GeoRenderer<T> renderer) {
        return this.buildFormattedModelPath(TheAurorian.prefix("lumen_crystal"));
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return this.buildFormattedAnimationPath(TheAurorian.prefix("lumen_crystal"));
    }

}
