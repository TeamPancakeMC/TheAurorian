package cn.teampancake.theaurorian.common.items.block;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.function.Consumer;

public class LaserCrystal extends BlockItem implements GeoItem {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public LaserCrystal() {
        super(TABlocks.LASER_CRYSTAL.get(), new Item.Properties()
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new CustomItemModel());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private static class CustomItemModel implements GeoRenderProvider {

        @Override
        public @Nullable BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
            return new CulledItemRenderer();
        }

    }

    private static class CulledItemRenderer extends GeoItemRenderer<LaserCrystal> {

        public CulledItemRenderer() {
            super(new DefaultedBlockGeoModel<>(TABlocks.LASER_CRYSTAL.getId()));
        }

        @Override
        public RenderType getRenderType(LaserCrystal animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
            return RenderType.entityCutout(texture);
        }

    }

}