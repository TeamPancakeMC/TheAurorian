package cn.teampancake.theaurorian.client.renderer.entity.abiotic;

import cn.teampancake.theaurorian.common.entities.projectile.ThrownShuriken;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ThrownShurikenRenderer extends EntityRenderer<ThrownShuriken> {

    private final ItemRenderer itemRenderer;

    public ThrownShurikenRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ThrownShuriken entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        ItemStack itemStack = entity.getItem();
        BakedModel model = this.itemRenderer.getModel(itemStack, entity.level(), null, 1);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.scale(2.0F, 2.0F, 2.0F);
        poseStack.mulPose(Axis.ZP.rotation(partialTicks));
        this.itemRenderer.render(itemStack, ItemDisplayContext.NONE, false,
                poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownShuriken entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }

}