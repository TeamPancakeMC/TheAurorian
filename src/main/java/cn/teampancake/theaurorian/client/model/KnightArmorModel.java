package cn.teampancake.theaurorian.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class KnightArmorModel<T extends LivingEntity> extends TAHumanoidModel<T> {

    public KnightArmorModel(ModelPart root) {
        super(root);
    }

    public static MeshDefinition createBodyLayer(CubeDeformation cubeDeformation) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition bipedHead = partDefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(96, 112).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.52F))
                .texOffs(0, 37).addBox(-0.5F, -13.0F, -4.25F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(0.0F, -13.0F, -4.25F, 0.0F, 8.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        bipedHead.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 33).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -4.25F, -0.4363F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bipedBody = partDefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(104, 112).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
                .texOffs(23, 46).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.31F))
                .texOffs(48, 56).mirror().addBox(-1.5F, 9.75F, -2.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(48, 56).mirror().addBox(-1.5F, 9.75F, 1.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(84, 50).mirror().addBox(-2.5F, 10.75F, 2.475F, 5.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
        bipedBody.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(57, 18).addBox(-4.0F, -4.0F, -1.25F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -2.0F, -0.1309F, 0.0F, 0.0F));
        PartDefinition bipedRightArm = partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(112, 112).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)), PartPose.offset(-5.0F, 2.0F, 0.0F));
        bipedRightArm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, -3.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));
        bipedRightArm.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(37, 32).addBox(-1.75F, -2.75F, -2.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 7.0F, 0.0F, 0.0F, 0.0F, -0.0873F));
        PartDefinition bipedLeftArm = partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(112, 112).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));
        bipedLeftArm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(16, 32).mirror().addBox(-1.0F, -3.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));
        bipedLeftArm.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(37, 32).mirror().addBox(-1.25F, -2.75F, -2.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.75F, 7.0F, 0.0F, 0.0F, 0.0F, 0.0873F));
        PartDefinition rightLeg = partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, -2.0F));
        rightLeg.addOrReplaceChild("right_leg_r2", CubeListBuilder.create().texOffs(65, 45).addBox(-0.75F, -1.25F, -2.5F, 4.0F, 13.0F, 5.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(-4.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.1309F));
        rightLeg.addOrReplaceChild("right_leg_r3", CubeListBuilder.create().texOffs(48, 45).addBox(-1.25F, -2.25F, -2.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.3491F));
        PartDefinition leftLeg = partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(4.0F, 11.0F, 0.0F));
        leftLeg.addOrReplaceChild("left_leg_r4", CubeListBuilder.create().texOffs(65, 45).mirror().addBox(-3.25F, -1.25F, -2.5F, 4.0F, 13.0F, 5.0F, new CubeDeformation(-0.005F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1309F));
        leftLeg.addOrReplaceChild("left_leg_r5", CubeListBuilder.create().texOffs(48, 45).mirror().addBox(-1.75F, -2.25F, -2.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));
        partDefinition.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(112, 112)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(112, 112).mirror()
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));
        return meshDefinition;
    }

}