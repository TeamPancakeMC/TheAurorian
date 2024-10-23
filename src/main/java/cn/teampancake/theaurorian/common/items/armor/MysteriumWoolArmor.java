package cn.teampancake.theaurorian.common.items.armor;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.armor.MysteriumWoolArmorModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class MysteriumWoolArmor extends BaseArmor<MysteriumWoolArmorModel> {

    public MysteriumWoolArmor(Type type) {
        super(ArmorMaterials.LEATHER, type, TAItemProperties.get().rarity(Rarity.RARE));
    }

    public static boolean isWearFullArmor(LivingEntity entity) {
        HashSet<Item> set = new HashSet<>();
        entity.getArmorSlots().forEach(stack -> {
            if (stack.getItem() instanceof MysteriumWoolArmor) {
                set.add(stack.getItem());
            }
        });

        return set.size() == 4;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected MysteriumWoolArmorModel getModel() {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        ModelPart modelPart = modelSet.bakeLayer(TAModelLayers.MYSTERIUM_ARMOR);
        return new MysteriumWoolArmorModel(modelPart);
    }

    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return TheAurorian.prefix(ARMOR_ID + "mysterium_armor.png");
    }

}