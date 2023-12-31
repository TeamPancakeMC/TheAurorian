package cn.teampancake.theaurorian.client.model.entity.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class CeruleanArmorModel extends HumanoidModel<LivingEntity> {

    public CeruleanArmorModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bipedHead = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(96, 112).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.52F))
                .texOffs(56, 19).addBox(-3.26F, 0.625F, -4.39F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.13F))
                .texOffs(56, 19).mirror().addBox(1.26F, 0.625F, -4.39F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.13F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bipedHead_r1 = bipedHead.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(56, 23).addBox(-1.0F, -3.0F, -0.4F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(0.0F, -8.5F, -4.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition bipedHeadwear = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bipedBody = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(104, 112).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
                .texOffs(0, 45).addBox(-4.0F, 9.5F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.31F))
                .texOffs(19, 58).addBox(-3.0F, 8.8F, 2.4F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(19, 68).addBox(-2.5F, 9.55F, -2.35F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = bipedBody.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 52).mirror().addBox(-2.25F, -2.0F, -2.5F, 4.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 11.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r2 = bipedBody.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 52).addBox(-1.75F, -2.0F, -2.5F, 4.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 11.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition bipedBody_r1 = bipedBody.addOrReplaceChild("bipedBody_r1", CubeListBuilder.create().texOffs(75, 21).mirror().addBox(-0.5F, -4.0F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 2.5F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition bipedBody_r2 = bipedBody.addOrReplaceChild("bipedBody_r2", CubeListBuilder.create().texOffs(75, 21).addBox(-1.5F, -4.0F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 2.5F, 0.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition bipedRightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(112, 112).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition bipedRightArm_r1 = bipedRightArm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(17, 37).addBox(-5.5F, -3.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 33).addBox(-1.5F, -3.0F, -2.5F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition bipedLeftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(112, 112).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition bipedLeftArm_r1 = bipedLeftArm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(17, 37).mirror().addBox(1.5F, -3.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 33).mirror().addBox(-1.5F, -3.0F, -2.5F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition bipedRightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(112, 112).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F))
                .texOffs(32, 53).addBox(-2.0F, 3.75F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.45F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition bipedLeftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 53).mirror().addBox(-2.0F, 3.75F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.45F)).mirror(false)
                .texOffs(112, 112).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

}