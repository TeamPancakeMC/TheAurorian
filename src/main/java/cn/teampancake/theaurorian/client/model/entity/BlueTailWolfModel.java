package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.animal.BlueTailWolf;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlueTailWolfModel<T extends BlueTailWolf> extends HierarchicalModel<T> {

    private final ModelPart body;

    public BlueTailWolfModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 11.75F, 0.0F));
        PartDefinition body_front = body.addOrReplaceChild("body_front", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.5F));
        body_front.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -9.0F, 10.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0873F, 0.0F, 0.0F));
        PartDefinition head = body_front.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 19).addBox(-3.5F, -3.25F, -6.5F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -10.0F));
        head.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                .texOffs(76, 19).addBox(-2.0F, 3.25F, -4.75F, 5.0F, 1.0F, 5.0F, new CubeDeformation(-0.01F))
                .texOffs(76, 4).addBox(-2.0F, 0.25F, -5.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.5F, -1.25F, -6.5F, 0.0873F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 22).mirror().addBox(1.15F, -1.75F, -6.52F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));
        head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(16, 22).addBox(-3.15F, -1.75F, -6.52F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));
        head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(44, 18).mirror().addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-3.5F, -3.25F, -1.5F, 0.0F, -0.8727F, 0.0F));
        head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(44, 18).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(3.5F, -3.25F, -1.5F, 0.0F, 0.8727F, 0.0F));
        head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(31, 22).addBox(-3.0F, 0.0F, -0.5F, 7.0F, 4.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.5F, -3.25F, -1.5F, 0.5236F, 0.0F, 0.0F));
        head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(76, 12)
                .addBox(-2.5F, 0.0F, -4.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(76, 19).addBox(-2.5F, -1.0F, -4.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(-0.02F)), PartPose.offset(0.0F, 1.75F, -7.0F));
        head.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(23, 22).addBox(-1.0F, -3.5F, -0.25F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.5F, -2.75F, -2.0F, -0.4305F, -0.0735F, -0.1585F));
        head.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(23, 22).mirror().addBox(-1.0F, -3.5F, -0.25F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(2.5F, -2.75F, -2.0F, -0.4305F, 0.0735F, 0.1585F));
        PartDefinition leg_front_right = body_front.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(0, 34).addBox(-3.0F, -2.0F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 1.25F, -5.75F, 0.3054F, 0.0F, 0.0F));
        PartDefinition leg_front_right_2 = leg_front_right.addOrReplaceChild("leg_front_right_2", CubeListBuilder.create().texOffs(21, 36).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.0F, 1.0F, -0.6981F, 0.0F, 0.0F));
        PartDefinition leg_front_right_3 = leg_front_right_2.addOrReplaceChild("leg_front_right_3", CubeListBuilder.create()
                .texOffs(36, 34).addBox(-2.0F, 0.0F, -3.75F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(51, 35).addBox(-0.5F, 1.0F, -5.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -0.25F, 0.3927F, 0.0F, 0.0F));
        leg_front_right_3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(51, 35).addBox(0.0F, -2.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, -3.75F, 0.0F, -0.0873F, 0.0F));
        leg_front_right_3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(51, 35).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.0F, -3.75F, 0.0F, 0.0873F, 0.0F));
        PartDefinition leg_front_left = body_front.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-1.0F, -2.0F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 1.25F, -5.75F, 0.3054F, 0.0F, 0.0F));
        PartDefinition leg_front_left_2 = leg_front_left.addOrReplaceChild("leg_front_left_2", CubeListBuilder.create().texOffs(21, 36).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 3.0F, 1.0F, -0.6981F, 0.0F, 0.0F));
        PartDefinition leg_front_left_3 = leg_front_left_2.addOrReplaceChild("leg_front_left_3", CubeListBuilder.create()
                .texOffs(36, 34).mirror().addBox(-2.0F, 0.0F, -3.75F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(51, 35).mirror().addBox(-0.5F, 1.0F, -5.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0F, -0.25F, 0.3927F, 0.0F, 0.0F));
        leg_front_left_3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(51, 35).mirror().addBox(-1.0F, -2.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 3.0F, -3.75F, 0.0F, 0.0873F, 0.0F));
        leg_front_left_3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(51, 35).mirror().addBox(0.0F, -2.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 3.0F, -3.75F, 0.0F, -0.0873F, 0.0F));
        PartDefinition body_back = body.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(41, 0).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));
        body_back.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 55).addBox(-4.5F, -3.275F, -2.5F, 9.0F, 9.0F, 8.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, -0.0873F, 0.0F, 0.0F));
        PartDefinition leg_back_right = body_back.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(57, 35).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 2.0F, 7.0F, 0.3054F, 0.0F, 0.0F));
        PartDefinition leg_back_right_2 = leg_back_right.addOrReplaceChild("leg_back_right_2", CubeListBuilder.create().texOffs(74, 36).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 2.25F, 0.5F, -0.5672F, 0.0F, 0.0F));
        PartDefinition leg_back_right_3 = leg_back_right_2.addOrReplaceChild("leg_back_right_3", CubeListBuilder.create()
                .texOffs(0, 47).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(51, 35).addBox(-0.5F, 1.0F, -5.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.35F, 0.25F, 0.2618F, 0.0F, 0.0F));
        leg_back_right_3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(51, 35).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 3.0F, -3.0F, 0.0F, 0.0873F, 0.0F));
        leg_back_right_3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(51, 35).addBox(0.0F, -2.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 3.0F, -3.0F, 0.0F, -0.0873F, 0.0F));
        PartDefinition leg_back_left = body_back.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(57, 35).mirror().addBox(-1.0F, -2.0F, -2.0F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 2.0F, 7.0F, 0.3054F, 0.0F, 0.0F));
        PartDefinition leg_back_left_2 = leg_back_left.addOrReplaceChild("leg_back_left_2", CubeListBuilder.create().texOffs(74, 36).mirror().addBox(-1.0F, 0.0F, -1.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 2.25F, 0.5F, -0.5672F, 0.0F, 0.0F));
        PartDefinition leg_back_left_3 = leg_back_left_2.addOrReplaceChild("leg_back_left_3", CubeListBuilder.create()
                .texOffs(0, 47).mirror().addBox(-1.5F, 0.0F, -3.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(51, 35).mirror().addBox(-0.5F, 1.0F, -5.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.35F, 0.25F, 0.2618F, 0.0F, 0.0F));
        leg_back_left_3.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(51, 35).mirror().addBox(0.0F, -2.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 3.0F, -3.0F, 0.0F, -0.0873F, 0.0F));
        leg_back_left_3.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(51, 35).mirror().addBox(-1.0F, -2.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 3.0F, -3.0F, 0.0F, 0.0873F, 0.0F));
        body_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(48, 43).addBox(0.0F, -6.0F, -1.0F, 0.0F, 12.0F, 17.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 1.0F, 11.0F));
        return LayerDefinition.create(meshDefinition, 96, 96);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
    }

    @Override
    public ModelPart root() {
        return this.body;
    }
    
}