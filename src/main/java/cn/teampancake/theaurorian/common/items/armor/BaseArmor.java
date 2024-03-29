package cn.teampancake.theaurorian.common.items.armor;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class BaseArmor<T extends HumanoidModel<?>> extends ArmorItem implements ITooltipsItem {

    public static final String ARMOR_ID = AurorianMod.MOD_ID + ":textures/models/armor/";

    public BaseArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
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
            return armorSlot == EquipmentSlot.LEGS ? _default : getModel();
        }

    }

}