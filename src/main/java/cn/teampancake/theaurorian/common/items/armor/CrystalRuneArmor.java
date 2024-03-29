package cn.teampancake.theaurorian.common.items.armor;

import cn.teampancake.theaurorian.client.model.entity.armor.CrystalRuneArmorModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.items.TAArmorMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class CrystalRuneArmor extends BaseArmor<CrystalRuneArmorModel> {

    public CrystalRuneArmor(Type type) {
        super(TAArmorMaterials.CRYSTAL_RUNE, type, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected CrystalRuneArmorModel getModel() {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        ModelPart modelPart = modelSet.bakeLayer(TAModelLayers.CRYSTAL_RUNE_ARMOR);
        return new CrystalRuneArmorModel(modelPart);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return ARMOR_ID + "crystal_rune_armor.png";
    }

}