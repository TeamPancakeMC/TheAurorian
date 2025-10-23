package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.monster.HyphaWalkingMushroom;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Constants;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HyphaWalkingMushroomModel<T extends HyphaWalkingMushroom> extends EntityModel<T> {

    private final ModelPart body;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public HyphaWalkingMushroomModel(ModelPart root) {
        this.body = root.getChild("body");
        this.rightLeg = this.body.getChild("leg_right");
        this.leftLeg = this.body.getChild("leg_left");
        this.rightArm = this.body.getChild("arm_right");
        this.leftArm = this.body.getChild("arm_left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-8.0F, -11.0F, -8.0F, 16.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 54).addBox(-7.5F, -6.0F, -7.5F, 15.0F, 7.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-6.5F, -13.5F, -6.5F, 13.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 0.0F));
        body.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(41, 9).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 1.0F, 0.0F));
        body.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(41, 9).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 1.0F, 0.0F));
        body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(48, 17).addBox(-3.0F, -1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.75F, -3.0F, 0.0F, 0.0F, 0.0F, 0.1309F));
        body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(48, 17).mirror().addBox(-1.0F, -1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.75F, -3.0F, 0.0F, 0.0F, 0.0F, -0.1309F));
        return LayerDefinition.create(meshDefinition, 64, 96);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + Constants.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + Constants.PI) * 1.4F * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        ImmutableList<ModelPart> bodyParts = ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg);
        bodyParts.forEach(modelPart -> modelPart.render(poseStack, vertexConsumer, packedLight, packedOverlay, color));
    }

}