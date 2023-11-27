package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.common.entities.monster.Spirit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class SpiritModel<T extends Spirit> extends EntityModel<T> {

    public ModelPart head;
    public ModelPart body;
    public ModelPart rightArm;
    public ModelPart leftArm;

    public SpiritModel(ModelPart root) {
        this(root, RenderType::entityCutoutNoCull);
    }

    public SpiritModel(ModelPart root, Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
        this.body = root.getChild("body");
        this.head = this.body.getChild("neck");
        this.rightArm = this.body.getChild("arm_right");
        this.leftArm = this.body.getChild("arm_left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -2.5F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
        body.addOrReplaceChild("body_lower", CubeListBuilder.create().texOffs(0, 12)
                .addBox(-3.0F, 0.0F, 0.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-3.5F, 5.0F, -0.5F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -2.0F, 0.48F, 0.0F, 0.0F));
        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(25, 2).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 0.0F, 0.1309F, 0.0F, 0.0F));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -1.75F, -1.25F));
        head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(46, 14)
                .addBox(-2.5F, -4.0F, 1.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(21, 11).addBox(-2.5F, -4.0F, -6.0F, 5.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.3491F, 0.0F, 0.0F));
        PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(0, 36).addBox(-3.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0873F));
        PartDefinition arm_right_1 = arm_right.addOrReplaceChild("arm_right_1", CubeListBuilder.create().texOffs(0, 47).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 5.0F, 0.25F, -0.303F, -0.0393F, -0.1249F));
        arm_right_1.addOrReplaceChild("finger_right_1", CubeListBuilder.create().texOffs(17, 54).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 9.0F, 0.0F));
        arm_right_1.addOrReplaceChild("finger_right_3", CubeListBuilder.create().texOffs(17, 54).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 9.0F, -2.0F, 0.0F, -0.7854F, 0.0F));
        arm_right_1.addOrReplaceChild("finger_right_2", CubeListBuilder.create().texOffs(17, 54).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 9.0F, 2.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(0, 36).mirror().addBox(0.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.25F, -1.0F, 0.0F, 0.0F, 0.0F, -0.0873F));
        PartDefinition arm_left_1 = arm_left.addOrReplaceChild("arm_left_1", CubeListBuilder.create().texOffs(0, 47).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 5.0F, 0.25F, -0.303F, 0.0393F, 0.1249F));
        arm_left_1.addOrReplaceChild("finger_left_1", CubeListBuilder.create().texOffs(17, 54).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 9.0F, 0.0F));
        arm_left_1.addOrReplaceChild("finger_left_3", CubeListBuilder.create().texOffs(17, 54).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 9.0F, -2.0F, 0.0F, 0.7854F, 0.0F));
        arm_left_1.addOrReplaceChild("finger_left_2", CubeListBuilder.create().texOffs(17, 54).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 9.0F, 2.0F, 0.0F, -0.7854F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180.0F);
        this.head.xRot = headPitch * ((float)Math.PI / 180.0F);
        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, entity.isAggressive(), this.attackTime, ageInTicks);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}