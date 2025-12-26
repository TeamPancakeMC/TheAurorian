package cn.teampancake.theaurorian.common.items.tool.moonsilver;

import cn.teampancake.theaurorian.common.items.tool.GeoHandheldToolRenderer;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAToolTiers;
import net.minecraft.world.item.SwordItem;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class MoonsilverGreatSword extends SwordItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MoonsilverGreatSword(Properties properties) {
        super(TAToolTiers.MOONSILVER, properties.durability(2000));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoHandheldToolRenderer<MoonsilverGreatSword>(TAItems.MOONSILVER_GREAT_SWORD.getId()));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}
