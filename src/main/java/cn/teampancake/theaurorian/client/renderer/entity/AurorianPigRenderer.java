package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.AurorianPigBabyModel;
import cn.teampancake.theaurorian.client.model.entity.AurorianPigModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.AurorianPig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianPigRenderer extends MobRenderer<AurorianPig, EntityModel<AurorianPig>> {

    private static final ResourceLocation BABY = AurorianMod.prefix("textures/entity/aurorian_pig_baby.png");
    private static final ResourceLocation ADULT = AurorianMod.prefix("textures/entity/aurorian_pig.png");

    private final EntityModel<AurorianPig> baby;
    private final EntityModel<AurorianPig> adult = this.getModel();

    public AurorianPigRenderer(EntityRendererProvider.Context context) {
        super(context, new AurorianPigModel<>(context.bakeLayer(TAModelLayers.AURORIAN_PIG)), 0.7F);
        this.baby = new AurorianPigBabyModel<>(context.bakeLayer(TAModelLayers.AURORIAN_PIG_BABY));
    }

    @Override
    public void render(AurorianPig entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.model = entity.isBaby() ? this.baby : this.adult;
        this.shadowRadius = entity.isBaby() ? 0.4F : 0.7F;
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianPig entity) {
        return entity.isBaby() ? BABY : ADULT;
    }

}