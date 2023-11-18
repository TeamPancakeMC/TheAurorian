package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.client.animation.RunestoneKeeperAnimation;
import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RunestoneKeeperModel<T extends RunestoneKeeper> extends HierarchicalModel<T> {

    private final ModelPart all2;
    private final ModelPart head;

    public RunestoneKeeperModel(ModelPart root) {
        this.all2 = root.getChild("all2");
        this.head = root.getChild("all2").getChild("all")
                .getChild("body_all").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all2 = partDefinition.addOrReplaceChild("all2", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition all = all2.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        all.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(74, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 32).addBox(-1.5F, 6.5F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.5F, 0.0F));
        all.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(74, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 32).addBox(-1.5F, 6.5F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -12.5F, 0.0F));
        PartDefinition body_all = all.addOrReplaceChild("body_all", CubeListBuilder.create(), PartPose.offset(0.0F, -15.0F, 0.0F));
        PartDefinition body = body_all.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(24, 16).addBox(-1.0F, -9.5F, 0.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 29).addBox(-2.0F, 1.5F, 0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-4.0F, -6.5F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 7).addBox(-6.5F, -6.6F, -2.5F, 13.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));
        body.addOrReplaceChild("bone", CubeListBuilder.create()
                .texOffs(50, 27).addBox(-4.0F, -0.5F, -3.5F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(50, 17).addBox(-4.0F, 0.5F, -3.5F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 1.5F));
        PartDefinition left_arm = body_all.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(42, 18).addBox(0.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(92, 11).addBox(-0.5F, 8.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -10.5F, 0.0F));
        PartDefinition shield = left_arm.addOrReplaceChild("shield", CubeListBuilder.create().texOffs(74, 0).addBox(0.5F, -4.0F, -4.0F, 1.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 9.0F, 0.0F));
        PartDefinition shield2 = shield.addOrReplaceChild("shield2", CubeListBuilder.create(), PartPose.offset(1.7F, 0.0F, 0.0F));
        shield2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(56, 48).addBox(0.0F, -3.0F, -3.0F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));
        PartDefinition right_arm = body_all.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(42, 18).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(46, 36).addBox(-2.5F, 6.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -10.5F, 0.0F));
        right_arm.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(32, 34).addBox(-0.8F, -1.0F, -0.2F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 4.0F, 0.0F, -0.0873F, -1.0036F, -0.1745F));
        PartDefinition wand = right_arm.addOrReplaceChild("wand", CubeListBuilder.create()
                .texOffs(0, 49).addBox(-0.5F, -0.5F, -11.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(48, 61).addBox(-0.5F, -0.5F, -13.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 60).addBox(-1.5F, -1.5F, -10.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 60).addBox(-2.5F, 0.0F, -13.5F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 9.5F, 0.0F));
        wand.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(34, 60).addBox(-2.5F, 0.0F, -2.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -11.5F, 0.0F, 0.0F, 1.5708F));
        PartDefinition wand_1 = wand.addOrReplaceChild("wand_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -11.0F));
        wand_1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(53, 42).addBox(-0.5F, -2.5F, -5.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -2.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
        PartDefinition wand_2 = wand.addOrReplaceChild("wand_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -12.0F));
        wand_2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(53, 42).addBox(-2.0F, -2.5F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 1.5708F));
        PartDefinition head = body_all.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));
        head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(32, 34).addBox(0.0F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -4.0F, 3.0F, -0.6545F, -0.5236F, 0.48F));
        head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(32, 34).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -4.0F, 3.0F, 0.0F, -1.1345F, 0.3491F));
        head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 33).addBox(-3.0F, -3.0F, -4.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -3.5F, -2.0F, 0.0F, 0.5672F, -0.1309F));
        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(72, 49).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.1745F, 0.0F, 0.0873F));
        hat.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(112, 14).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 3.0F, -0.7854F, 0.0F, 0.0F));
        hat.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(104, 24).addBox(-3.0F, -2.3F, -2.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.5F, 0.0F, -0.3491F, 0.0F, 0.0F));
        hat.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(88, 35).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, -0.0873F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_2", CubeListBuilder.create().texOffs(56, 60).addBox(-5.0F, -6.0F, -4.5F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 128, 80);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        AnimationDefinition animationDefinition = entity.isSprinting() ?
                RunestoneKeeperAnimation.RUN : RunestoneKeeperAnimation.WALK;
        this.animateWalk(animationDefinition, limbSwing, limbSwingAmount, (2.0F), (2.5F));
        this.animate(entity.idleAnimationState, RunestoneKeeperAnimation.IDLE, ageInTicks);
        this.animate(entity.attackAnimationState1, RunestoneKeeperAnimation.ATTACK_1, ageInTicks);
        this.animate(entity.attackAnimationState2, RunestoneKeeperAnimation.ATTACK_2, ageInTicks);
        this.animate(entity.attackAnimationState3, RunestoneKeeperAnimation.ATTACK_3, ageInTicks);
        this.animate(entity.deathAnimationState, RunestoneKeeperAnimation.DEATH, ageInTicks);
    }

    public @NotNull ModelPart root() {
        return this.all2;
    }

}