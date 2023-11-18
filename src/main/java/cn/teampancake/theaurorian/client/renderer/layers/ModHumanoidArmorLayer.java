package cn.teampancake.theaurorian.client.renderer.layers;

import cn.teampancake.theaurorian.AurorianMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModHumanoidArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    private final A armorModel;

    public ModHumanoidArmorLayer(RenderLayerParent<T, M> renderer, A armorModel) {
        super(renderer);
        this.armorModel = armorModel;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.HEAD, packedLight, this.armorModel);
        this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.CHEST, packedLight, this.armorModel);
        this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.LEGS, packedLight, this.armorModel);
        this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.FEET, packedLight, this.armorModel);
    }

    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, T livingEntity, EquipmentSlot slot, int packedLight, A model) {
        ItemStack slotStack = livingEntity.getItemBySlot(slot);
        Item slotItem = slotStack.getItem();
        if (slotItem instanceof ArmorItem armorItem) {
            if (armorItem.getEquipmentSlot() == slot) {
                this.getParentModel().copyPropertiesTo(model);
                this.setPartVisibility(model, slot);
                String texture = armorItem.getMaterial().getName() + "_armor.png";
                ResourceLocation armorResource = AurorianMod.prefix("textures/models/armor/" + texture);
                this.render(poseStack, buffer, RenderType.armorCutoutNoCull(armorResource), packedLight, model);
                if (slotStack.hasFoil()) {
                    this.render(poseStack, buffer, RenderType.armorEntityGlint(), packedLight, model);
                }
            }
        }
    }

    protected void setPartVisibility(A model, EquipmentSlot slot) {
        model.setAllVisible(false);
        switch (slot) {
            case HEAD -> {
                model.head.visible = true;
                model.hat.visible = true;
            }
            case CHEST -> {
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
            }
            case LEGS -> {
                model.body.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
            case FEET -> {
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
        }
    }
    
    private void render(PoseStack poseStack, MultiBufferSource buffer, RenderType renderType, int packedLight, Model model) {
        model.renderToBuffer(poseStack, buffer.getBuffer(renderType), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

}