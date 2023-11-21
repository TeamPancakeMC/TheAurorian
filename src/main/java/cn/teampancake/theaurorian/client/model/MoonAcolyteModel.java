package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.common.entities.monster.MoonAcolyte;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MoonAcolyteModel<T extends MoonAcolyte> extends SimpleHumanoidModel<T> {

    public MoonAcolyteModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(36, 46).addBox(-3.5F, -8.0F, -3.5F, 7.0F, 11.0F, 7.0F, new CubeDeformation(-0.002F))
                .texOffs(33, 2).addBox(-2.5F, -7.0F, -2.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -2.0F, -3.0F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));
        body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(0.0F, -1.25F, -3.5F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.002F)).mirror(false), PartPose.offsetAndRotation(3.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
        body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(38, 29).mirror().addBox(-0.325F, 2.2F, -3.5F, 6.0F, 9.0F, 7.0F, new CubeDeformation(-0.002F)).mirror(false), PartPose.offsetAndRotation(3.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.3054F));
        body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(38, 29).addBox(-5.675F, 2.2F, -3.5F, 6.0F, 9.0F, 7.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(-3.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.3054F));
        body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 42).addBox(-6.0F, -1.25F, -3.5F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-3.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        body.addOrReplaceChild("body_lower", CubeListBuilder.create().texOffs(0, 13).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));
        PartDefinition right_arm = partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 26).addBox(-2.5F, -1.0F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 5.0F, 0.0F, -0.5465F, 0.5739F, 0.2367F));
        PartDefinition right_arm_1 = right_arm.addOrReplaceChild("right_arm_1", CubeListBuilder.create().texOffs(13, 26).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 9.0F, 0.0F, -0.3887F, -0.4242F, -0.7443F));
        right_arm_1.addOrReplaceChild("right_claw_1", CubeListBuilder.create().texOffs(26, 26).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.3491F, 0.0F, 0.0F));
        right_arm_1.addOrReplaceChild("right_claw_2", CubeListBuilder.create().texOffs(26, 26).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 9.0F, 0.0F, 0.3463F, -0.0447F, 0.1231F));
        right_arm_1.addOrReplaceChild("right_claw_3", CubeListBuilder.create().texOffs(26, 26).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, 9.0F, 0.0F, 0.3463F, 0.0447F, -0.1231F));
        PartDefinition left_arm = partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 26).mirror().addBox(-0.5F, -1.0F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 5.0F, 0.0F, -0.5465F, -0.5739F, -0.2367F));
        PartDefinition left_arm_1 = left_arm.addOrReplaceChild("left_arm_1", CubeListBuilder.create().texOffs(13, 26).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 9.0F, 0.0F, -0.3887F, 0.4242F, 0.7443F));
        left_arm_1.addOrReplaceChild("left_claw_1", CubeListBuilder.create().texOffs(26, 26).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.3491F, 0.0F, 0.0F));
        left_arm_1.addOrReplaceChild("left_claw_2", CubeListBuilder.create().texOffs(26, 26).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.25F, 9.0F, 0.0F, 0.3463F, 0.0447F, -0.1231F));
        left_arm_1.addOrReplaceChild("left_claw_3", CubeListBuilder.create().texOffs(26, 26).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.25F, 9.0F, 0.0F, 0.3463F, -0.0447F, 0.1231F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightArm, this.leftArm);
    }

}