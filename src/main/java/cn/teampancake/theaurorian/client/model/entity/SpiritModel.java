package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.client.animation.SpiritAnimation;
import cn.teampancake.theaurorian.common.entities.monster.Spirit;
import com.mojang.math.Constants;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiritModel<T extends Spirit> extends HierarchicalModel<T> {
    
    private final ModelPart body;
    private final ModelPart head;

    public SpiritModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partdefinition = meshDefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -2.0F, 8.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
        PartDefinition body_lower = body.addOrReplaceChild("body_lower", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, -2.0F));
        body_lower.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(25, 0).addBox(-4.5F, 0.0F, -0.5F, 9.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 1.0F));
        head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -7.5F, 0.9F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-3.5F, -7.0F, -7.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
        PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(31, 17).addBox(-3.0F, -1.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -1.0F, 0.0F, 0.4363F, 0.0F, 0.48F));
        PartDefinition arm_right_2 = arm_right.addOrReplaceChild("arm_right_2", CubeListBuilder.create().texOffs(44, 14).addBox(-2.0F, -8.176F, -0.8951F, 4.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 8.25F, 0.0F, -1.2654F, 0.0F, 0.0F));
        PartDefinition finger_mid = arm_right_2.addOrReplaceChild("finger_mid", CubeListBuilder.create().texOffs(44, 36).addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 7.824F, 2.1049F, 0.0F, 0.0F, -0.2182F));
        PartDefinition finger_mid_2 = finger_mid.addOrReplaceChild("finger_mid_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, -0.2182F));
        finger_mid_2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(52, 37).addBox(0.0F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));
        PartDefinition finger_left = arm_right_2.addOrReplaceChild("finger_left", CubeListBuilder.create().texOffs(44, 36).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.824F, 1.1049F, -0.2559F, -0.056F, -0.211F));
        finger_left.addOrReplaceChild("finger_left_2", CubeListBuilder.create().texOffs(52, 37).addBox(0.0F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 4.0F, -1.0F, 0.0F, 0.0F, -0.7854F));
        PartDefinition finger_right = arm_right_2.addOrReplaceChild("finger_right", CubeListBuilder.create().texOffs(44, 36).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 7.824F, 3.1049F, 0.2986F, 0.0651F, -0.2084F));
        PartDefinition finger_right_2 = finger_right.addOrReplaceChild("finger_right_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 1.0F, 0.0F, 0.0F, -0.48F));
        finger_right_2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(52, 37).addBox(0.0F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));
        PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(31, 17).mirror().addBox(0.0F, -1.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, -1.0F, 0.0F, 0.4363F, 0.0F, -0.48F));
        PartDefinition arm_left_2 = arm_left.addOrReplaceChild("arm_left_2", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-2.0F, -8.176F, -0.8951F, 4.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.75F, 8.25F, 0.0F, -1.2654F, 0.0F, 0.0F));
        PartDefinition finger_mid2 = arm_left_2.addOrReplaceChild("finger_mid2", CubeListBuilder.create().texOffs(44, 36).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 7.824F, 2.1049F, 0.0F, 0.0F, 0.2182F));
        PartDefinition finger_mid_3 = finger_mid2.addOrReplaceChild("finger_mid_3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.2182F));
        finger_mid_3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(52, 37).mirror().addBox(-1.0F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));
        PartDefinition finger_left2 = arm_left_2.addOrReplaceChild("finger_left2", CubeListBuilder.create().texOffs(44, 36).mirror().addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 7.824F, 1.1049F, -0.2559F, 0.056F, 0.211F));
        finger_left2.addOrReplaceChild("finger_left_3", CubeListBuilder.create().texOffs(52, 37).mirror().addBox(-1.0F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 4.0F, -1.0F, 0.0F, 0.0F, 0.7854F));
        PartDefinition finger_right2 = arm_left_2.addOrReplaceChild("finger_right2", CubeListBuilder.create().texOffs(44, 36).mirror().addBox(-2.0F, 0.0F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 7.824F, 3.1049F, 0.2986F, -0.0651F, 0.2084F));
        PartDefinition finger_right_3 = finger_right2.addOrReplaceChild("finger_right_3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.48F));
        finger_right_3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(52, 37).mirror().addBox(-1.0F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * Constants.DEG_TO_RAD;
        this.head.yRot = netHeadYaw * Constants.DEG_TO_RAD;
        this.animate(entity.idleAnimationState, SpiritAnimation.MISC_IDLE, ageInTicks);
        this.animate(entity.attackAnimationState, SpiritAnimation.ATTACK_SWING, ageInTicks);
        if (!entity.isInWaterOrBubble()) {
            this.animateWalk(SpiritAnimation.MOVE_WALK, limbSwing, limbSwingAmount, (2.0F), (2.5F));
        }
    }

    @Override
    public ModelPart root() {
        return this.body;
    }
    
}