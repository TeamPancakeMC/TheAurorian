package cn.teampancake.theaurorian.common.items.armor;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.armor.KnightArmorModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.items.TAArmorMaterials;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class KnightArmor extends BaseArmor<KnightArmorModel> {

    public KnightArmor(Type type) {
        super(TAArmorMaterials.KNIGHT, type, TAItemProperties.get().rarity(Rarity.RARE));
    }

    public static boolean isWearFullArmor(LivingEntity entity) {
        HashSet<Item> set = new HashSet<>();
        entity.getArmorSlots().forEach(stack -> {
            if (stack.getItem() instanceof KnightArmor) {
                set.add(stack.getItem());
            }
        });

        return set.size() == 4;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player && !player.hasEffect(MobEffects.DAMAGE_BOOST) && isWearFullArmor(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200));
        }
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