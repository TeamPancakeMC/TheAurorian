package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.common.entities.monster.Spirit;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SpiritModel<T extends Spirit> extends SimpleHumanoidModel<T> {

    public SpiritModel(ModelPart root) {
        super(root);
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -2.5F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
        PartDefinition neck = all.addOrReplaceChild("head", CubeListBuilder.create().texOffs(25, 2).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 0.0F, 0.1309F, 0.0F, 0.0F));
        PartDefinition head = neck.addOrReplaceChild("head_1", CubeListBuilder.create(), PartPose.offset(0.0F, -1.75F, -1.25F));
        head.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(46, 14).addBox(-2.5F, -4.0F, 1.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(21, 11).addBox(-2.5F, -4.0F, -6.0F, 5.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.3491F, 0.0F, 0.0F));
        all.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 12).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-3.5F, 5.0F, -0.5F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -2.0F, 0.48F, 0.0F, 0.0F));
        PartDefinition right_arm = all.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 36).addBox(-3.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0873F));
        PartDefinition right_arm_1 = right_arm.addOrReplaceChild("right_arm_1", CubeListBuilder.create().texOffs(0, 47).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 5.0F, 0.25F, -0.303F, -0.0393F, -0.1249F));
        right_arm_1.addOrReplaceChild("right_finger_1", CubeListBuilder.create().texOffs(17, 54).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 9.0F, 0.0F));
        right_arm_1.addOrReplaceChild("right_finger_3", CubeListBuilder.create().texOffs(17, 54).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 9.0F, -2.0F, 0.0F, -0.7854F, 0.0F));
        right_arm_1.addOrReplaceChild("right_finger_2", CubeListBuilder.create().texOffs(17, 54).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 9.0F, 2.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition left_arm = all.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 36).mirror().addBox(0.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.25F, -1.0F, 0.0F, 0.0F, 0.0F, -0.0873F));
        PartDefinition left_arm_1 = left_arm.addOrReplaceChild("left_arm_1", CubeListBuilder.create().texOffs(0, 47).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 5.0F, 0.25F, -0.303F, 0.0393F, 0.1249F));
        left_arm_1.addOrReplaceChild("left_finger_1", CubeListBuilder.create().texOffs(17, 54).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 9.0F, 0.0F));
        left_arm_1.addOrReplaceChild("left_finger_3", CubeListBuilder.create().texOffs(17, 54).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 9.0F, -2.0F, 0.0F, 0.7854F, 0.0F));
        left_arm_1.addOrReplaceChild("left_finger_2", CubeListBuilder.create().texOffs(17, 54).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 9.0F, 2.0F, 0.0F, -0.7854F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightArm, this.leftArm);
    }

}