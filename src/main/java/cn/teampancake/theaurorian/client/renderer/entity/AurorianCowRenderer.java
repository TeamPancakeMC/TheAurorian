package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.AurorianCowBabyModel;
import cn.teampancake.theaurorian.client.model.entity.AurorianCowModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.AurorianCow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianCowRenderer extends MobRenderer<AurorianCow, EntityModel<AurorianCow>> {

    private static final ResourceLocation BABY = AurorianMod.prefix("textures/entity/aurorian_cow_baby.png");
    private static final ResourceLocation ADULT = AurorianMod.prefix("textures/entity/aurorian_cow.png");

    private final EntityModel<AurorianCow> baby;
    private final EntityModel<AurorianCow> adult = this.getModel();

    public AurorianCowRenderer(EntityRendererProvider.Context context) {
        super(context, new AurorianCowModel<>(context.bakeLayer(TAModelLayers.AURORIAN_COW)), 1.0F);
        this.baby = new AurorianCowBabyModel<>(context.bakeLayer(TAModelLayers.AURORIAN_COW_BABY));
    }

    @Override
    public void render(AurorianCow entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.model = entity.isBaby() ? this.baby : this.adult;
        this.shadowRadius = entity.isBaby() ? 0.7F : 1.0F;
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianCow entity) {
        return entity.isBaby() ? BABY : ADULT;
    }

}