package cn.teampancake.theaurorian.client.model.entity.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrystalRuneArmorModel extends HumanoidModel<LivingEntity> {

    public CrystalRuneArmorModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(96, 112).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.52F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(2.5F, -3.25F, -1.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -8.0F, -3.0F, 0.4363F, 0.0F, 0.3054F));
        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 37).mirror().addBox(3.5F, -3.75F, -2.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -8.0F, -3.0F, -0.1666F, -0.0522F, 0.0044F));
        head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(0, 37).addBox(-4.5F, -3.75F, -2.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -3.0F, -0.1666F, 0.0522F, -0.0044F));
        head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(0, 32).addBox(-4.5F, -3.25F, -1.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -3.0F, 0.4363F, 0.0F, -0.3054F));
        partDefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(104, 112).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
                .texOffs(0, 57).addBox(-4.0F, 9.25F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.31F))
                .texOffs(42, 56).addBox(-2.0F, 8.5F, -2.75F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(53, 56).addBox(-2.0F, 8.5F, 1.75F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 73).addBox(-2.5F, 13.25F, 2.45F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(25, 52).mirror().addBox(-1.75F, -2.5F, -2.5F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 10.5F, 0.0F, 0.0F, 0.0F, -0.2182F));
        body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 63).mirror().addBox(-3.0F, -2.25F, -2.5F, 4.0F, 15.0F, 5.0F, new CubeDeformation(-0.02F)).mirror(false), PartPose.offsetAndRotation(4.0F, 10.5F, 0.0F, 0.0F, 0.0F, -0.1309F));
        body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 63).addBox(-1.0F, -2.25F, -2.5F, 4.0F, 15.0F, 5.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(-4.0F, 10.5F, 0.0F, 0.0F, 0.0F, 0.1309F));
        body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(25, 52).addBox(-1.25F, -2.5F, -2.5F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 10.5F, 0.0F, 0.0F, 0.0F, 0.2182F));
        body.addOrReplaceChild("crystal_2", CubeListBuilder.create().texOffs(34, 72).addBox(-1.5F, -8.0F, -1.5F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 8.0F, -0.2618F, 0.0F, 0.0F));
        body.addOrReplaceChild("crystal_1", CubeListBuilder.create().texOffs(34, 72).addBox(-1.5F, -8.0F, -1.5F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -4.0F, 7.0F, -0.2618F, 0.0F, 0.3927F));
        body.addOrReplaceChild("crystal_3", CubeListBuilder.create().texOffs(34, 72).mirror().addBox(-1.5F, -8.0F, -1.5F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, -4.0F, 7.0F, -0.2618F, 0.0F, -0.3927F));
        PartDefinition right_arm = partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(112, 112).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F))
                .texOffs(0, 38).addBox(-4.0F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));
        right_arm.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(23, 37).addBox(-5.75F, -2.0F, -3.25F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, 0.5F, 0.0F, -0.2221F, -0.1775F, 1.5159F));
        right_arm.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(23, 37).addBox(-5.0F, -1.5F, -3.25F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, 0.5F, 0.0F, -0.0043F, -0.2834F, 0.624F));
        right_arm.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(23, 37).addBox(-5.5F, -1.75F, -3.5F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, 0.5F, 0.0F, -0.1263F, -0.2544F, 1.0759F));
        PartDefinition crystal_right = right_arm.addOrReplaceChild("crystal_right", CubeListBuilder.create(), PartPose.offset(-4.0F, -3.0F, 0.0F));
        crystal_right.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(25, 73).addBox(0.75F, -6.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        crystal_right.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(25, 73).addBox(-6.0F, 6.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));
        crystal_right.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(25, 73).addBox(-1.25F, -5.75F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        crystal_right.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(25, 73).addBox(-2.25F, -5.75F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.789F));
        PartDefinition left_arm = partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(112, 112).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)).mirror(false)
                .texOffs(0, 38).mirror().addBox(-1.0F, -3.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));
        left_arm.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(23, 37).mirror().addBox(3.75F, -2.0F, -3.25F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.25F, 0.5F, 0.0F, -0.2221F, 0.1775F, -1.5159F));
        left_arm.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(23, 37).mirror().addBox(3.0F, -1.5F, -3.25F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.25F, 0.5F, 0.0F, -0.0043F, 0.2834F, -0.624F));
        left_arm.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(23, 37).mirror().addBox(3.5F, -1.75F, -3.5F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.25F, 0.5F, 0.0F, -0.1263F, 0.2544F, -1.0759F));
        PartDefinition crystal_left = left_arm.addOrReplaceChild("crystal_left", CubeListBuilder.create(), PartPose.offset(4.0F, -3.0F, 0.0F));
        crystal_left.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(25, 73).mirror().addBox(-2.75F, -6.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
        crystal_left.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(25, 73).mirror().addBox(4.0F, 6.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5236F));
        crystal_left.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(25, 73).mirror().addBox(-0.75F, -5.75F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
        crystal_left.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(25, 73).mirror().addBox(0.25F, -5.75F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.789F));
        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(112, 112).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(112, 112).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 128, 128);
    }

}