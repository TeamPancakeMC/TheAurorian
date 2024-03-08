package cn.teampancake.theaurorian.common.items.armor;

import cn.teampancake.theaurorian.client.model.entity.armor.MysteriumWoolArmorModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class MysteriumWoolArmor extends BaseArmor<MysteriumWoolArmorModel> {

    public MysteriumWoolArmor(Type type) {
        super(ArmorMaterials.LEATHER, type, new Item.Properties().rarity(Rarity.RARE));
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return isWearFullArmor(wearer);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected MysteriumWoolArmorModel getModel() {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        ModelPart modelPart = modelSet.bakeLayer(TAModelLayers.MYSTERIUM_ARMOR);
        return new MysteriumWoolArmorModel(modelPart);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return ARMOR_ID + "mysterium_armor.png";
    }

    public static boolean isWearFullArmor(LivingEntity wearer) {
        boolean hasThisHelmet = wearer.getItemBySlot(EquipmentSlot.HEAD).is(TAItems.MYSTERIUM_WOOL_HELMET.get());
        boolean hasThisChestplate = wearer.getItemBySlot(EquipmentSlot.CHEST).is(TAItems.MYSTERIUM_WOOL_CHESTPLATE.get());
        boolean hasThisLeggings = wearer.getItemBySlot(EquipmentSlot.LEGS).is(TAItems.MYSTERIUM_WOOL_LEGGINGS.get());
        boolean hasThisBoots = wearer.getItemBySlot(EquipmentSlot.FEET).is(TAItems.MYSTERIUM_WOOL_BOOTS.get());
        return hasThisHelmet && hasThisChestplate && hasThisLeggings && hasThisBoots;
    }

}