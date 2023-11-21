package cn.teampancake.theaurorian.mixin;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.data.tags.ModItemTags;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class MixinHumanoidArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    public MixinHumanoidArmorLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Inject(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/Model;ZFFFLnet/minecraft/resources/ResourceLocation;)V", ordinal = 2, shift = At.Shift.BEFORE), remap = false, cancellable = true)
    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, T livingEntity, EquipmentSlot slot, int packedLight, A model, CallbackInfo ci) {
        ItemStack slotStack = livingEntity.getItemBySlot(slot);
        if (slotStack.getItem() instanceof ArmorItem armorItem && slotStack.is(ModItemTags.HAS_CUSTOM_ARMOR_MODEL)) {
            String texture = armorItem.getMaterial().getName() + "_armor.png";
            ResourceLocation armorResource = AurorianMod.prefix("textures/models/armor/" + texture);
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(armorResource));
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            ci.cancel();
        }
    }

}