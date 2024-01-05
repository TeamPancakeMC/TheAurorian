package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.client.animation.AurorianWingedFishAnimation;
import cn.teampancake.theaurorian.common.entities.animal.AurorianWingedFish;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianWingedFishModel<T extends AurorianWingedFish> extends HierarchicalModel<T> {

    private final ModelPart bodyFront;

    public AurorianWingedFishModel(ModelPart root) {
        this.bodyFront = root.getChild("body_front");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body_front = partDefinition.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -4.0F, 3.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, -7.0F));
        body_front.addOrReplaceChild("head", CubeListBuilder.create().texOffs(52, 0).addBox(-1.0F, -2.5F, -4.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(38, 4).addBox(-2.0F, -2.5F, -4.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -4.0F));
        body_front.addOrReplaceChild("leftFin", CubeListBuilder.create().texOffs(2, 0).addBox(-2.0075F, -2.567F, -18.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 5.0F, 14.0F, 0.0F, 0.0F, 0.6109F));
        body_front.addOrReplaceChild("rightFin", CubeListBuilder.create().texOffs(-2, 0).addBox(0.0074F, -2.567F, -11.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 5.0F, 7.0F, 0.0F, 0.0F, -0.6109F));
        body_front.addOrReplaceChild("dorsal_front", CubeListBuilder.create().texOffs(48, 11).addBox(0.0F, -5.5F, -6.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 8.0F));
        PartDefinition wing = body_front.addOrReplaceChild("wing", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 11.0F));
        PartDefinition right1 = wing.addOrReplaceChild("right1", CubeListBuilder.create(), PartPose.offset(-1.5F, -6.0F, -9.0F));
        right1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 24).addBox(-9.0F, -4.0F, 0.0F, 9.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.1781F, 0.0F));
        PartDefinition right2 = wing.addOrReplaceChild("right2", CubeListBuilder.create(), PartPose.offset(-1.5F, -6.0F, -12.5F));
        right2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(18, 20).addBox(-12.0F, -6.5F, 0.0F, 13.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        wing.addOrReplaceChild("left1", CubeListBuilder.create().texOffs(0, 24).addBox(-9.0F, -4.0F, 0.0F, 9.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -6.0F, -9.0F, 0.0F, 1.9635F, 0.0F));
        wing.addOrReplaceChild("left2", CubeListBuilder.create().texOffs(18, 20).addBox(-12.0F, -6.5F, 0.0F, 13.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -6.0F, -12.5F, 0.0F, 2.3562F, 0.0F));
        PartDefinition body_back = body_front.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(22, 5).addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));
        body_back.addOrReplaceChild("tail_fin", CubeListBuilder.create().texOffs(52, 21).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));
        body_back.addOrReplaceChild("dorsal_back", CubeListBuilder.create().texOffs(52, 9).addBox(0.0F, -6.5F, -1.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, 1.0F));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.swimAnimationState, AurorianWingedFishAnimation.SWIM, ageInTicks);
        this.animate(entity.crashAnimationState, AurorianWingedFishAnimation.CRASH, ageInTicks);
        this.animate(entity.crash2AnimationState, AurorianWingedFishAnimation.CRASH2, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return this.bodyFront;
    }

}