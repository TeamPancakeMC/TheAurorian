package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.AurorianSheepBabyModel;
import cn.teampancake.theaurorian.client.model.entity.AurorianSheepModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.AurorianSheep;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianSheepRenderer extends MobRenderer<AurorianSheep, EntityModel<AurorianSheep>> {

    private static final ResourceLocation BABY = TheAurorian.prefix("textures/entity/aurorian_sheep_baby.png");
    private static final ResourceLocation ADULT = TheAurorian.prefix("textures/entity/aurorian_sheep.png");

    private final EntityModel<AurorianSheep> baby;
    private final EntityModel<AurorianSheep> adult = this.getModel();

    public AurorianSheepRenderer(EntityRendererProvider.Context context) {
        super(context, new AurorianSheepModel<>(context.bakeLayer(TAModelLayers.AURORIAN_SHEEP)), 0.7F);
        this.baby = new AurorianSheepBabyModel<>(context.bakeLayer(TAModelLayers.AURORIAN_SHEEP_BABY));
    }

    @Override
    public void render(AurorianSheep entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.model = entity.isBaby() ? this.baby : this.adult;
        this.shadowRadius = entity.isBaby() ? 0.4F : 0.7F;
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianSheep entity) {
        return entity.isBaby() ? BABY : ADULT;
    }

}