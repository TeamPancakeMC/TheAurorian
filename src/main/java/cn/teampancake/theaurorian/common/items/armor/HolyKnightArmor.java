package cn.teampancake.theaurorian.common.items.armor;

import cn.teampancake.theaurorian.client.renderer.entity.abiotic.HolyKnightArmorRenderer;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TAArmorMaterials;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("removal")
public class HolyKnightArmor extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public HolyKnightArmor(Type type) {
        super(TAArmorMaterials.KNIGHT, type, new Item.Properties()
                .component(TADataComponents.ITEM_TAGS, List.of(TAItemTags.IS_LEGENDARY))
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new CustomArmorModel());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private static class CustomArmorModel implements GeoRenderProvider {

        @Nullable
        private GeoArmorRenderer<?> renderer;

        @Override
        public @Nullable <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {
            if (this.renderer == null) {
                this.renderer = new HolyKnightArmorRenderer();
            }

            this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
            return this.renderer;
        }

    }

}