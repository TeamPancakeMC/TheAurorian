package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.client.animation.MoonAcolyteAnimation;
import cn.teampancake.theaurorian.common.entities.monster.MoonAcolyte;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoonAcolyteModel<T extends MoonAcolyte> extends HierarchicalModel<T> {

    private final ModelPart all;
    private final ModelPart head;

    public MoonAcolyteModel(ModelPart root) {
        this.all = root.getChild("all");
        this.head = this.all.getChild("body").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));
        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -2.0F, -3.0F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(0.0F, -1.25F, -3.5F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.002F)).mirror(false), PartPose.offsetAndRotation(3.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
        body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(38, 29).mirror().addBox(-0.325F, 2.2F, -3.5F, 6.0F, 9.0F, 7.0F, new CubeDeformation(-0.002F)).mirror(false), PartPose.offsetAndRotation(3.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.3054F));
        body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(38, 29).addBox(-5.675F, 2.2F, -3.5F, 6.0F, 9.0F, 7.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(-3.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.3054F));
        body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 42).addBox(-6.0F, -1.25F, -3.5F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-3.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        body.addOrReplaceChild("body_lower", CubeListBuilder.create().texOffs(0, 13).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(33, 2).addBox(-2.5F, -7.0F, -2.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 46).addBox(-3.5F, -8.0F, -3.5F, 7.0F, 11.0F, 7.0F, new CubeDeformation(-0.002F)), PartPose.offset(0.0F, -2.0F, 0.0F));
        PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(0, 26).addBox(-2.5F, -1.0F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, -0.5465F, 0.5739F, 0.2367F));
        PartDefinition arm_right_1 = arm_right.addOrReplaceChild("arm_right_1", CubeListBuilder.create().texOffs(13, 26).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 9.0F, 0.0F, -0.3887F, -0.4242F, -0.7443F));
        arm_right_1.addOrReplaceChild("claw_right_1", CubeListBuilder.create().texOffs(26, 26).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.3491F, 0.0F, 0.0F));
        arm_right_1.addOrReplaceChild("claw_right_2", CubeListBuilder.create().texOffs(26, 26).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 9.0F, 0.0F, 0.3463F, -0.0447F, 0.1231F));
        arm_right_1.addOrReplaceChild("claw_right_3", CubeListBuilder.create().texOffs(26, 26).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, 9.0F, 0.0F, 0.3463F, 0.0447F, -0.1231F));
        PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(0, 26).mirror().addBox(-0.5F, -1.0F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, -0.5465F, -0.5739F, -0.2367F));
        PartDefinition arm_left_1 = arm_left.addOrReplaceChild("arm_left_1", CubeListBuilder.create().texOffs(13, 26).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 9.0F, 0.0F, -0.3887F, 0.4242F, 0.7443F));
        arm_left_1.addOrReplaceChild("claw_left_1", CubeListBuilder.create().texOffs(26, 26).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.3491F, 0.0F, 0.0F));
        arm_left_1.addOrReplaceChild("claw_left_2", CubeListBuilder.create().texOffs(26, 26).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.25F, 9.0F, 0.0F, 0.3463F, 0.0447F, -0.1231F));
        arm_left_1.addOrReplaceChild("claw_left_3", CubeListBuilder.create().texOffs(26, 26).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.25F, 9.0F, 0.0F, 0.3463F, -0.0447F, 0.1231F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float)Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180.0F);
        this.animate(entity.idleAnimationState, MoonAcolyteAnimation.IDLE, ageInTicks);
        this.animate(entity.attackAnimationState, MoonAcolyteAnimation.ATTACK, ageInTicks);
        if (!entity.isInWaterOrBubble()) {
            this.animateWalk(MoonAcolyteAnimation.WALK, limbSwing, limbSwingAmount, (2.0F), (2.5F));
        }
    }

    @Override
    public ModelPart root() {
        return this.all;
    }

}