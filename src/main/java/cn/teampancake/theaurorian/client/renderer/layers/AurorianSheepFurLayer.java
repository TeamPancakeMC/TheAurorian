package cn.teampancake.theaurorian.client.renderer.layers;

import cn.teampancake.theaurorian.common.entities.animal.AurorianSheep;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class AurorianSheepFurLayer extends RenderLayer<AurorianSheep, SheepModel<AurorianSheep>> {

    private static final ResourceLocation SHEEP_FUR_LOCATION = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    private final SheepFurModel<AurorianSheep> model;
    
    public AurorianSheepFurLayer(RenderLayerParent<AurorianSheep, SheepModel<AurorianSheep>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new SheepFurModel<>(modelSet.bakeLayer(ModelLayers.SHEEP_FUR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AurorianSheep livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!livingEntity.isSheared()) {
            if (livingEntity.isInvisible()) {
                if (Minecraft.getInstance().shouldEntityAppearGlowing(livingEntity)) {
                    this.getParentModel().copyPropertiesTo(this.model);
                    this.model.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTick);
                    this.model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.outline(SHEEP_FUR_LOCATION));
                    this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
                }
            } else {
                float f;
                float f1;
                float f2;
                if (livingEntity.hasCustomName() && "jeb_".equals(livingEntity.getName().getString())) {
                    int i = livingEntity.tickCount / 25 + livingEntity.getId();
                    int j = DyeColor.values().length;
                    int k = i % j;
                    int l = (i + 1) % j;
                    float f3 = ((float)(livingEntity.tickCount % 25) + partialTick) / 25.0F;
                    float[] afloat1 = AurorianSheep.getColorArray(DyeColor.byId(k));
                    float[] afloat2 = AurorianSheep.getColorArray(DyeColor.byId(l));
                    f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
                    f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
                    f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
                } else {
                    float[] afloat = AurorianSheep.getColorArray(livingEntity.getColor());
                    f = afloat[0];
                    f1 = afloat[1];
                    f2 = afloat[2];
                }
                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, SHEEP_FUR_LOCATION, poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTick, f, f1, f2);
            }
        }
    }
    
}