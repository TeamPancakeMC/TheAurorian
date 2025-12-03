package cn.teampancake.theaurorian.common.items.block;

import cn.teampancake.theaurorian.client.model.block.LumenCrystalModel;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.function.Consumer;

public class AbstractLumenCrystal extends BlockItem implements GeoItem {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public AbstractLumenCrystal(Block block) {
        super(block, new Item.Properties().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC));
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

    private class CustomItemModel implements GeoRenderProvider {

        @Override
        public @Nullable BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
            return new CulledItemRenderer();
        }

    }

    private class CulledItemRenderer extends GeoItemRenderer<AbstractLumenCrystal> {

        public CulledItemRenderer() {
            super(new LumenCrystalModel<>(BuiltInRegistries.BLOCK.getKey(getBlock())));
        }

        @Override
        public RenderType getRenderType(AbstractLumenCrystal animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
            return RenderType.entityCutout(texture);
        }

    }

}