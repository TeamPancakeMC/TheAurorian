package cn.teampancake.theaurorian.common.items.block;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class CrystallineSwordPedestal extends BlockItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public CrystallineSwordPedestal(Properties properties) {
        super(TABlocks.CRYSTALLINE_SWORD_PEDESTAL.get(), properties.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new Provider());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private static class Provider implements GeoRenderProvider {

        @Override
        public @Nullable BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
            return new CustomItemRenderer();
        }

    }

    private static class CustomItemRenderer extends GeoItemRenderer<CrystallineSwordPedestal> {

        public CustomItemRenderer() {
            super(new CustomItemModel(TABlocks.CRYSTALLINE_SWORD_PEDESTAL.getId()));
        }

        @Override
        public RenderType getRenderType(CrystallineSwordPedestal animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
            return RenderType.entityCutout(texture);
        }

    }

    private static class CustomItemModel extends DefaultedItemGeoModel<CrystallineSwordPedestal> {

        private final ResourceLocation assetSubpath;

        public CustomItemModel(ResourceLocation assetSubpath) {
            super(assetSubpath);
            this.assetSubpath = assetSubpath;
        }

        @Override
        public ResourceLocation getTextureResource(CrystallineSwordPedestal animatable) {
            return TheAurorian.prefix("textures/block/" + this.assetSubpath.getPath() + ".png");
        }

    }

}