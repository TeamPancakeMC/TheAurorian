package cn.teampancake.theaurorian.common.items.armor;

import cn.teampancake.theaurorian.client.model.entity.armor.CeruleanArmorModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAArmorMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class CeruleanArmor extends BaseArmor<CeruleanArmorModel> {

    public CeruleanArmor(Type type) {
        super(TAArmorMaterials.CERULEAN, type, new Item.Properties().rarity(Rarity.RARE));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
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