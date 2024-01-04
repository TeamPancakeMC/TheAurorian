package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.client.animation.MoonQueenAnimation;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoonQueenModel<T extends MoonQueen> extends HierarchicalModel<T> {

    private final ModelPart all;
    private final ModelPart head;

    public MoonQueenModel(ModelPart root) {
        this.all = root.getChild("all");
        this.head = this.all.getChild("body_higher").getChild("neck").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 4.625F, 0.0F));
        PartDefinition body_higher = all.addOrReplaceChild("body_higher", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        body_higher.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(19, 21).addBox(0.275F, -5.9F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-3.0F, 2.1F, -0.075F, -0.1298F, -0.017F, -0.1298F));
        body_higher.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(19, 21).mirror().addBox(-1.275F, -5.9F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(3.0F, 2.1F, -0.075F, -0.1298F, 0.017F, 0.1298F));
        body_higher.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(30, 23).mirror().addBox(-0.025F, -1.0474F, -1.2265F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.85F, -0.775F, -0.5233F, 0.0167F, 0.0403F));
        body_higher.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(30, 23).addBox(-2.975F, -1.0474F, -1.2265F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.85F, -0.775F, -0.5233F, -0.0167F, -0.0403F));
        body_higher.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 21).addBox(-2.5F, -6.0F, -2.0F, 5.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.1F, -0.075F, -0.1309F, 0.0F, 0.0F));
        PartDefinition neck = body_higher.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 15).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.75F, 0.75F));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.5F, -7.0F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-4.0F, -7.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(43, 24).addBox(-4.0F, -6.5F, -3.85F, 8.0F, 9.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.25F, 0.0F));
        head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(98, 0).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 14.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 0.1309F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(112, 18).mirror().addBox(-1.375F, -3.0F, -3.4F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -4.0F, 0.0F, 0.0F, 0.0F, -0.0873F));
        head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(112, 18).addBox(-0.625F, -3.0F, -3.4F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0873F));
        head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(69, 7).addBox(-4.1101F, -1.2266F, -0.15F, 7.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -4.0F, -0.0873F, 0.0F, -0.0873F));
        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(29, 6).addBox(-1.0F, 0.0F, -0.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, -3.125F, -3.425F));
        eye_right.addOrReplaceChild("eyeball_right", CubeListBuilder.create().texOffs(29, 10).addBox(-0.5F, -1.0F, -0.125F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 1.0F, 0.0F));
        eye_right.addOrReplaceChild("brown_right", CubeListBuilder.create()
                .texOffs(23, 5).addBox(-0.975F, -0.75F, -0.15F, 2.0F, 1.0F, 0.175F, new CubeDeformation(0.0F))
                .texOffs(23, 5).addBox(-1.725F, -0.25F, -0.15F, 0.75F, 1.0F, 0.175F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(29, 6).mirror().addBox(-1.0F, 0.0F, -0.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.9F, -3.125F, -3.425F));
        eye_left.addOrReplaceChild("eyeball_left", CubeListBuilder.create().texOffs(29, 10).mirror().addBox(-0.5F, -1.0F, -0.125F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.25F, 1.0F, 0.0F));
        eye_left.addOrReplaceChild("brown_left", CubeListBuilder.create().texOffs(23, 5).mirror().addBox(-1.025F, -0.75F, -0.15F, 2.0F, 1.0F, 0.175F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(23, 5).mirror().addBox(0.975F, -0.25F, -0.15F, 0.75F, 1.0F, 0.175F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition arm_right = body_higher.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(0, 42).addBox(-2.5F, -0.75F, -1.0F, 2.5F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -2.75F, 0.0F, 0.0F, 0.0F, 0.1309F));
        arm_right.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(30, 57).addBox(-1.75F, -1.0F, -1.5F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 2.0F, 0.0F, 0.0F, 0.0F, -0.1745F));
        PartDefinition arm_right_2 = arm_right.addOrReplaceChild("arm_right_2", CubeListBuilder.create()
                .texOffs(11, 42).addBox(-1.0F, 0.25F, -1.0F, 2.5F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(47, 57).addBox(-1.5F, -0.5F, -1.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(60, 57).addBox(-2.0F, 2.5F, -1.5F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 5.0F, 0.0F));
        PartDefinition sword = arm_right_2.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(33, 83).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(33, 72).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(60, 61).addBox(0.0F, -29.0F, -3.5F, 0.0F, 25.0F, 7.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 5.0F, 1.0F, 1.5708F, 0.0F, 0.0F));
        sword.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(42, 82).addBox(-1.0F, -2.5F, -0.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        sword.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(53, 83).addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(0.0F, -4.0F, -2.0F, -0.3491F, 0.0F, 0.0F));
        sword.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(53, 83).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(0.0F, -4.0F, 2.0F, 0.3491F, 0.0F, 0.0F));
        PartDefinition arm_left = body_higher.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(0.0F, -0.75F, -1.0F, 2.5F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, -2.75F, 0.0F, 0.0F, 0.0F, -0.1309F));
        arm_left.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(30, 57).mirror().addBox(-2.25F, -1.0F, -1.5F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.1745F));
        arm_left.addOrReplaceChild("arm_left_2", CubeListBuilder.create()
                .texOffs(11, 42).mirror().addBox(-1.5F, 0.25F, -1.0F, 2.5F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(47, 57).mirror().addBox(-0.5F, -0.5F, -1.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(60, 57).mirror().addBox(0.0F, 2.5F, -1.5F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 5.0F, 0.0F));
        PartDefinition body_lower = all.addOrReplaceChild("body_lower", CubeListBuilder.create().texOffs(0, 32).addBox(-2.5F, 0.0F, -2.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 1.85F, 0.0F));
        body_lower.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(19, 32).mirror().addBox(-1.825F, -0.2F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(2.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));
        body_lower.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 94).addBox(-0.4241F, -0.3156F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.003F)), PartPose.offsetAndRotation(-3.5F, 4.0F, 0.0F, 0.3054F, 0.0F, 0.3054F));
        body_lower.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 94).mirror().addBox(-3.5759F, -0.3156F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.003F)).mirror(false), PartPose.offsetAndRotation(3.5F, 4.0F, 0.0F, 0.3054F, 0.0F, -0.3054F));
        body_lower.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(20, 81).mirror().addBox(-3.0759F, -0.0656F, -0.25F, 6.0F, 11.0F, 0.25F, new CubeDeformation(0.003F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.75F, 2.0F, 0.2923F, 0.0F, 0.0F));
        body_lower.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 81).mirror().addBox(-2.5009F, -0.0656F, 0.05F, 5.0F, 11.0F, 0.25F, new CubeDeformation(0.003F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.75F, -2.0F, -0.2923F, 0.0F, 0.0F));
        body_lower.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-3.5759F, -0.3156F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.003F)).mirror(false), PartPose.offsetAndRotation(3.5F, 4.0F, 0.0F, -0.3054F, 0.0F, -0.3054F));
        body_lower.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(11, 77).mirror().addBox(0.4241F, -0.3156F, -2.0F, 0.025F, 12.0F, 4.0F, new CubeDeformation(0.003F)).mirror(false), PartPose.offsetAndRotation(3.5F, 4.0F, 0.0F, 0.0F, 0.0F, -0.3054F));
        body_lower.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(11, 77).addBox(-0.4491F, -0.3156F, -2.0F, 0.025F, 12.0F, 4.0F, new CubeDeformation(0.003F)), PartPose.offsetAndRotation(-3.5F, 4.0F, 0.0F, 0.0F, 0.0F, 0.3054F));
        body_lower.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 64).addBox(-0.4241F, -0.3156F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.003F)), PartPose.offsetAndRotation(-3.5F, 4.0F, 0.0F, -0.3054F, 0.0F, 0.3054F));
        body_lower.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(19, 32).addBox(-0.175F, -0.2F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-2.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));
        PartDefinition leg_right = body_lower.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(0, 52).addBox(-2.0F, 0.0F, -2.0F, 3.5F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 4.5F, 0.0F, 0.0F, 0.0F, -0.0436F));
        leg_right.addOrReplaceChild("leg_right_2", CubeListBuilder.create().texOffs(15, 52).addBox(-2.0F, 0.0F, -2.0F, 3.5F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.95F, 0.0F, 0.0F, 0.0F, 0.0436F));
        PartDefinition leg_left = body_lower.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(0, 52).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.5F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 4.5F, 0.0F, 0.0F, 0.0F, 0.0436F));
        leg_left.addOrReplaceChild("leg_left_2", CubeListBuilder.create().texOffs(15, 52).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.5F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.95F, 0.0F, 0.0F, 0.0F, -0.0436F));
        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float)Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180.0F);
        this.animate(entity.idleAnimationState, MoonQueenAnimation.IDLE, ageInTicks);
        this.animate(entity.meleeAttackAnimationState, MoonQueenAnimation.MELEE_ATTACK, ageInTicks, 1.5F);
        this.animate(entity.defendAnimationState, MoonQueenAnimation.DEFEND, ageInTicks);
        this.animate(entity.skillLunaBefallAnimationState, MoonQueenAnimation.SKILL_LUNA_BEFALL, ageInTicks);
        this.animate(entity.skillLunaBefallEndAnimationState, MoonQueenAnimation.SKILL_LUNA_BEFALL_END, ageInTicks);
        if (!entity.isInWaterOrBubble()) {
            this.animateWalk(MoonQueenAnimation.WALK, limbSwing, limbSwingAmount, (1.5F), (2.5F));
        }
    }

    @Override
    public ModelPart root() {
        return this.all;
    }

}