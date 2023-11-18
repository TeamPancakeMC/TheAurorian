package cn.teampancake.theaurorian.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class SpectralArmorModel<T extends LivingEntity> extends HumanoidModel<T> {

    public SpectralArmorModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition bipedHead = partDefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(96, 112).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.52F))
                .texOffs(32, 0).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.7F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        bipedHead.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(57, 18).addBox(-2.0F, -9.25F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.004F)), PartPose.offsetAndRotation(4.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.0873F));
        bipedHead.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(66, 18).addBox(3.0F, -5.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(4.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.7418F));
        bipedHead.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(57, 18).mirror().addBox(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -5.0F, 0.0F, 0.0F, 0.0F, -1.0472F));
        bipedHead.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(66, 18).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.5236F));
        partDefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(104, 112).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
                .texOffs(0, 44).addBox(-4.0F, 9.75F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.31F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bipedRightArm = partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(112, 112).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F))
                .texOffs(0, 33).addBox(-4.0F, -2.5F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));
        bipedRightArm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(19, 35).addBox(0.0F, -1.5F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.25F, 6.5F, 0.0F, 0.0F, 0.1745F, 0.1309F));
        bipedRightArm.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(19, 35).addBox(0.0F, -1.5F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.25F, 6.5F, 0.0F, 0.0F, -0.1745F, 0.1309F));
        bipedRightArm.addOrReplaceChild("right_arm_r3", CubeListBuilder.create().texOffs(19, 35).addBox(0.0F, -1.5F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.25F, 3.5F, 0.0F, 0.0F, -0.1745F, 0.1309F));
        bipedRightArm.addOrReplaceChild("right_arm_r4", CubeListBuilder.create().texOffs(19, 35).addBox(0.0F, -1.5F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.25F, 3.5F, 0.0F, 0.0F, 0.1745F, 0.1309F));
        bipedRightArm.addOrReplaceChild("right_arm_r5", CubeListBuilder.create().texOffs(66, 18).addBox(-4.0F, -5.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-3.5F, -0.5F, 0.0F, 0.0F, 0.0F, 0.4363F));
        bipedRightArm.addOrReplaceChild("right_arm_r6", CubeListBuilder.create().texOffs(57, 18).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -0.5F, 0.0F, 0.0F, 0.0F, -0.4363F));
        PartDefinition bipedLeftArm = partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(0, 33).mirror().addBox(0.0F, -2.5F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(112, 112).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));
        bipedLeftArm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(19, 35).mirror().addBox(-2.0F, -1.5F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.25F, 6.5F, 0.0F, 0.0F, 0.1745F, -0.1309F));
        bipedLeftArm.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(19, 35).mirror().addBox(-2.0F, -1.5F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.25F, 3.5F, 0.0F, 0.0F, 0.1745F, -0.1309F));
        bipedLeftArm.addOrReplaceChild("left_arm_r3", CubeListBuilder.create().texOffs(57, 18).mirror().addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, -0.5F, 0.0F, 0.0F, 0.0F, 0.4363F));
        bipedLeftArm.addOrReplaceChild("left_arm_r4", CubeListBuilder.create().texOffs(66, 18).mirror().addBox(2.0F, -5.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.002F)).mirror(false), PartPose.offsetAndRotation(3.5F, -0.5F, 0.0F, 0.0F, 0.0F, -0.4363F));
        bipedLeftArm.addOrReplaceChild("left_arm_r5", CubeListBuilder.create().texOffs(19, 35).mirror().addBox(-2.0F, -1.5F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.25F, 6.5F, 0.0F, 0.0F, -0.1745F, -0.1309F));
        bipedLeftArm.addOrReplaceChild("left_arm_r6", CubeListBuilder.create().texOffs(19, 35).mirror().addBox(-2.0F, -1.5F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.25F, 3.5F, 0.0F, 0.0F, -0.1745F, -0.1309F));
        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(112, 112).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F))
                .texOffs(0, 52).addBox(-2.5F, 6.25F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(21, 52).addBox(-2.5F, 3.25F, -2.5F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(21, 52).mirror().addBox(-0.5F, 3.25F, -2.5F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.2F)).mirror(false)
                .texOffs(0, 52).mirror().addBox(-2.5F, 6.25F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(112, 112).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 128, 128);
    }

}