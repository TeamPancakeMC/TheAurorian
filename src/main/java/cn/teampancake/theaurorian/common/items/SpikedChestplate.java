package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.client.model.entity.armor.SpikedChestplateModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.items.armor.BaseArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static net.minecraft.world.item.ArmorItem.Type.CHESTPLATE;

public class SpikedChestplate extends BaseArmor<SpikedChestplateModel> {

    public SpikedChestplate() {
        super(TAArmorMaterials.SPECTRAL, CHESTPLATE, new Item.Properties());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected SpikedChestplateModel getModel() {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        ModelPart modelPart = modelSet.bakeLayer(TAModelLayers.SPIKED_CHESTPLATE);
        return new SpikedChestplateModel(modelPart);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return ARMOR_ID + "spiked_chestplate.png";
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player) {
            if (player.isShiftKeyDown()) {
                player.getArmorSlots().forEach(itemStack -> {
                    if (itemStack.getItem() instanceof SpikedChestplate) {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10));
                        itemStack.enchant(Enchantments.THORNS, 3);
                    }
                });
            } else {
                player.getArmorSlots().forEach(itemStack -> {
                    if (itemStack.getItem() instanceof SpikedChestplate) {
                        Map<Enchantment, Integer> ench = EnchantmentHelper.getEnchantments(itemStack);
                        ench.remove(Enchantments.THORNS);
                        EnchantmentHelper.setEnchantments(ench, itemStack);
                    }
                });
            }
        }
    }

}