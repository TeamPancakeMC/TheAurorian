package cn.teampancake.theaurorian.common.items.armor;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.armor.KnightArmorModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.items.TAArmorMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class KnightArmor extends BaseArmor<KnightArmorModel> {

    public KnightArmor(Type type) {
        super(TAArmorMaterials.KNIGHT, type, new Properties().rarity(Rarity.RARE));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected KnightArmorModel getModel() {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        ModelPart modelPart = modelSet.bakeLayer(TAModelLayers.KNIGHT_ARMOR);
        return new KnightArmorModel(modelPart);
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return TheAurorian.prefix(ARMOR_ID + "knight_armor.png");
    }

}