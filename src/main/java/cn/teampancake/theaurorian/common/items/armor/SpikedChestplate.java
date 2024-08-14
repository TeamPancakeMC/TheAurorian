package cn.teampancake.theaurorian.common.items.armor;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.armor.SpikedChestplateModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.items.TAArmorMaterials;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class SpikedChestplate extends BaseArmor<SpikedChestplateModel> {

    public SpikedChestplate() {
        super(TAArmorMaterials.SPECTRAL, Type.CHESTPLATE, new Properties());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected SpikedChestplateModel getModel() {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        ModelPart modelPart = modelSet.bakeLayer(TAModelLayers.SPIKED_CHESTPLATE);
        return new SpikedChestplateModel(modelPart);
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return TheAurorian.prefix(ARMOR_ID + "spiked_chestplate.png");
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        ResourceKey<Enchantment> enchantment = TAEnchantments.SPRING_OF_LIFE;
        Holder<Enchantment> holder = TAEnchantments.get(level, enchantment);
        if (entity instanceof Player player) {
            if (player.isShiftKeyDown()) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10));
                stack.enchant(holder, 3);
            } else {
                stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).keySet().remove(holder);
            }
        }
    }

}