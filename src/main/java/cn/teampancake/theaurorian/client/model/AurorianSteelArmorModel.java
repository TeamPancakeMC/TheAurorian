package cn.teampancake.theaurorian.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class AurorianSteelArmorModel extends HumanoidModel<LivingEntity> {

    public AurorianSteelArmorModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bipedHead = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(96, 112).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.52F))
                .texOffs(0, 32).addBox(-1.0F, -9.25F, -5.25F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bipedHead_r1 = bipedHead.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(9, 37).addBox(-2.0F, -3.125F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -3.0F, -4.515F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bipedHead_r2 = bipedHead.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(9, 37).mirror().addBox(0.0F, -3.125F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, -3.0F, -4.515F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bipedHeadwear = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bipedBody = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(104, 112).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
                .texOffs(0, 57).addBox(-4.0F, 9.5F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.31F))
                .texOffs(25, 57).addBox(-2.5F, 9.5F, -2.625F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = bipedBody.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(38, 53).addBox(-0.15F, 1.5F, -2.55F, 3.0F, 5.0F, 5.0F, new CubeDeformation(-0.003F))
                .texOffs(38, 53).addBox(-0.4F, -1.5F, -2.55F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 10.5F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r2 = bipedBody.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(38, 53).mirror().addBox(-2.85F, 1.5F, -2.55F, 3.0F, 5.0F, 5.0F, new CubeDeformation(-0.003F)).mirror(false)
                .texOffs(38, 53).mirror().addBox(-2.6F, -1.5F, -2.55F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 10.5F, 0.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition cube_r3 = bipedBody.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(55, 55).addBox(-3.0F, -0.25F, 0.15F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.5F, 2.325F, 0.0873F, 0.0F, 0.0F));

        PartDefinition bipedRightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(112, 112).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition cube_r4 = bipedRightArm.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 43).addBox(-2.5F, -4.0F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition bipedLeftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(112, 112).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition cube_r5 = bipedLeftArm.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 43).mirror().addBox(-1.5F, -4.0F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition bipedRightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(112, 112).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F))
                .texOffs(16, 33).addBox(-2.0F, -0.25F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.54F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition bipedLeftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(112, 112).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)).mirror(false)
                .texOffs(16, 33).mirror().addBox(-2.0F, -0.25F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.54F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

}