package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.client.animation.UndeadKnightAnimation;
import cn.teampancake.theaurorian.common.entities.monster.UndeadKnight;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UndeadKnightModel<T extends UndeadKnight> extends HierarchicalModel<T> {

    private final ModelPart all;
    private final ModelPart head;

    public UndeadKnightModel(ModelPart root) {
        this.all = root.getChild("all");
        this.head = this.all.getChild("body").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));
        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(38, 22).addBox(0.0F, 6.0F, 0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(38, 22).addBox(-2.5F, 6.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(25, 18).addBox(-1.5F, 6.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-3.0F, 1.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(41, 2).addBox(-4.0F, 0.75F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2564F, 0.7601F, 0.1795F));
        body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.26F, 0.7731F, 0.1833F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -5.75F, 0.75F));
        head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(47, 23).mirror().addBox(0.0F, -3.0F, 0.025F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.25F, -2.0F, -1.45F, 0.0F, -1.1345F, 0.0F));
        head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(47, 23).addBox(-2.0F, -3.0F, 0.025F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.25F, -2.0F, -1.45F, 0.0F, 1.1345F, 0.0F));
        head.addOrReplaceChild("cube_r6", CubeListBuilder.create()
                .texOffs(25, 29).addBox(-2.0F, -8.0F, -4.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-2.0F, -6.0F, -4.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create(), PartPose.offset(-9.25F, -3.25F, 0.0F));
        arm_right.addOrReplaceChild("cube_r7", CubeListBuilder.create()
                .texOffs(21, 47).addBox(0.0F, -4.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(21, 47).addBox(-5.0F, 1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-4.0F, -3.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));
        PartDefinition arm_right_1_1 = arm_right.addOrReplaceChild("arm_right_1_1", CubeListBuilder.create(), PartPose.offset(-3.0F, 9.0F, 0.0F));
        PartDefinition arm_right_1 = arm_right_1_1.addOrReplaceChild("arm_right_1", CubeListBuilder.create()
                .texOffs(0, 54).addBox(-2.0F, -1.25F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(21, 55).addBox(-2.25F, 1.0F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));
        arm_right_1.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 67).addBox(-0.5F, -2.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -1.25F, 0.0F, 0.0F, 0.0F, 0.1745F));
        arm_right_1.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(40, 57).addBox(-1.75F, -1.25F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, -0.6981F));
        arm_right_1_1.addOrReplaceChild("weapon", CubeListBuilder.create().texOffs(25, 75).addBox(-1.0F, -21.0F, -1.0F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 81).addBox(-5.5F, -41.0F, 0.0F, 11.0F, 19.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 101).addBox(-2.0F, -22.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 108).addBox(-1.5F, 10.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 6.5F, 6.0F, 1.5708F, 0.0F, 1.5708F));
        PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create(), PartPose.offset(9.25F, -3.25F, 0.0F));
        arm_left.addOrReplaceChild("cube_r10", CubeListBuilder.create()
                .texOffs(21, 47).mirror().addBox(-1.0F, -4.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(21, 47).mirror().addBox(4.0F, 1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 43).mirror().addBox(-1.0F, -3.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));
        PartDefinition arm_left_1_1 = arm_left.addOrReplaceChild("arm_left_1_1", CubeListBuilder.create(), PartPose.offset(3.0F, 9.0F, 0.0F));
        PartDefinition arm_left_1 = arm_left_1_1.addOrReplaceChild("arm_left_1", CubeListBuilder.create()
                .texOffs(0, 54).mirror().addBox(-3.0F, -1.25F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(21, 55).mirror().addBox(-0.75F, 1.0F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));
        arm_left_1.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 67).mirror().addBox(-5.5F, -2.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -1.25F, 0.0F, 0.0F, 0.0F, -0.1745F));
        arm_left_1.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(40, 57).mirror().addBox(-2.25F, -1.25F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.6981F));
        arm_left_1_1.addOrReplaceChild("shield", CubeListBuilder.create()
                .texOffs(34, 80).addBox(0.0F, -9.0F, -6.0F, 1.0F, 17.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(61, 84).addBox(0.0F, 8.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.25F, 2.0F, 0.0F, 0.8309F, 0.0589F, 0.0644F));
        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float)Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180.0F);
        this.animate(entity.idleAnimationState, UndeadKnightAnimation.IDLE, ageInTicks);
        this.animate(entity.attackAnimationState, UndeadKnightAnimation.ATTACK, ageInTicks);
        if (!entity.isInWaterOrBubble()) {
            this.animateWalk(UndeadKnightAnimation.WALK, limbSwing, limbSwingAmount, (2.0F), (2.5F));
        }
    }

    @Override
    public ModelPart root() {
        return this.all;
    }

}