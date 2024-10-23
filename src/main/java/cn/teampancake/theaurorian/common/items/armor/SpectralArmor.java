package cn.teampancake.theaurorian.common.items.armor;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.armor.SpectralArmorModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAArmorMaterials;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class SpectralArmor extends BaseArmor<SpectralArmorModel> {

    public SpectralArmor(Type type) {
        super(TAArmorMaterials.SPECTRAL, type, TAItemProperties.get().rarity(Rarity.RARE).addItemTag(TAItemTags.SPECTRAL_ARMOR));
    }

    public static boolean isWearSpectralArmor(Player player) {
        int count = 0;
        for (ItemStack slot : player.getArmorSlots()) {
            if (slot.getItem() instanceof SpectralArmor) {
                count++;
            }
        }

        return count > 0 && Math.random() < 1.0D - Math.pow(1.0D - 0.06D, count);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected SpectralArmorModel getModel() {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        ModelPart modelPart = modelSet.bakeLayer(TAModelLayers.SPECTRAL_ARMOR);
        return new SpectralArmorModel(modelPart);
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return TheAurorian.prefix(ARMOR_ID + "spectral_armor.png");
    }

}