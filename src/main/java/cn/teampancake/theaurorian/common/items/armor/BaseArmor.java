package cn.teampancake.theaurorian.common.items.armor;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("removal")
public abstract class BaseArmor<T extends HumanoidModel<?>> extends ArmorItem {

    public static final String ARMOR_ID = "textures/models/armor/";

    public BaseArmor(Holder<ArmorMaterial> material, Type type, Item.Properties properties) {
        super(material, type, properties
                .component(TADataComponents.ITEM_TAGS, List.of(TAItemTags.IS_EPIC))
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new CustomArmorModel());
    }

    @OnlyIn(Dist.CLIENT)
    protected abstract T getModel();

    private class CustomArmorModel implements IClientItemExtensions {

        @Override @NotNull
        public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
            return getModel();
        }

    }

}