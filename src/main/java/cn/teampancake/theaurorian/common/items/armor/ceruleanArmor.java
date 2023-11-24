package cn.teampancake.theaurorian.common.items.armor;

import cn.teampancake.theaurorian.client.model.CeruleanArmorModel;
import cn.teampancake.theaurorian.client.model.SpectralArmorModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.items.ModArmorMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

public class ceruleanArmor extends BaseArmor<CeruleanArmorModel> {
    public ceruleanArmor(Type pType) {
        super(ModArmorMaterials.CERULEAN, pType, new Item.Properties().rarity(Rarity.RARE));
    }

    @Override
    protected CeruleanArmorModel getModel() {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        ModelPart modelPart = modelSet.bakeLayer(TAModelLayers.CERULEAN_ARMOR);
        return new CeruleanArmorModel(modelPart);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return ARMOR_ID + "cerulean_armor.png";
    }
}
