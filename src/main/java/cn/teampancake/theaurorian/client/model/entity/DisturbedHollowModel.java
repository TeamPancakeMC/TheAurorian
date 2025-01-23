package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.client.animation.DisturbedHollowAnimation;
import cn.teampancake.theaurorian.common.entities.monster.DisturbedHollow;
import com.mojang.math.Constants;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisturbedHollowModel<T extends DisturbedHollow> extends HierarchicalModel<T> {

    private final ModelPart all;
    private final ModelPart body;
    private final ModelPart bone;
    private final ModelPart head;
    private final ModelPart armRight;
    private final ModelPart armLeft;

    public DisturbedHollowModel(ModelPart root) {
        this.all = root.getChild("all");
        this.body = this.all.getChild("body");
        this.bone = this.body.getChild("bone");
        this.head = this.bone.getChild("head");
        this.armRight = this.bone.getChild("arm_right");
        this.armLeft = this.bone.getChild("arm_left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -26.75F, 0.0F, 0.2182F, 0.0F, 0.0F));
        body.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(31, 1).addBox(-2.75F, -1.0F, -2.0F, 4.0F, 15.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.75F, 13.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        body.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(31, 1).mirror().addBox(-1.25F, -1.0F, -2.0F, 4.0F, 15.0F, 4.0F, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(1.75F, 13.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        PartDefinition bone = body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -13.0F, -2.5F, 10.0F, 13.0F, 5.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 13.0F, 0.0F));
        PartDefinition head = bone.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -12.5F, -1.25F, -0.2182F, 0.0F, 0.0F));
        head.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(0, 19).addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, CubeDeformation.NONE), PartPose.offset(0.0F, -3.5F, -0.5F));
        PartDefinition head3 = head.addOrReplaceChild("head3", CubeListBuilder.create(), PartPose.offset(-3.0F, -0.75F, 0.0F));
        head3.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 34).addBox(-1.25F, -10.25F, -4.5F, 4.0F, 11.0F, 8.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));
        PartDefinition head4 = head.addOrReplaceChild("head4", CubeListBuilder.create(), PartPose.offset(3.0F, -0.75F, 0.0F));
        head4.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-2.75F, -10.25F, -4.5F, 4.0F, 11.0F, 8.0F, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));
        bone.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(29, 22).addBox(-4.0F, -2.5F, -2.0F, 4.0F, 20.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-5.0F, -12.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        bone.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(29, 22).mirror().addBox(0.0F, -2.5F, -2.0F, 4.0F, 20.0F, 4.0F, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(5.0F, -12.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.all;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * Constants.DEG_TO_RAD;
        this.head.yRot = netHeadYaw * Constants.DEG_TO_RAD;
        this.animate(entity.idleAnimationState, DisturbedHollowAnimation.IDLE, ageInTicks);
        AnimationUtils.animateZombieArms(this.armLeft, this.armRight, entity.isAngry(), this.attackTime, ageInTicks);
        if (!entity.isInWaterOrBubble()) {
            this.animateWalk(DisturbedHollowAnimation.WALK, limbSwing, limbSwingAmount, (2.0F), (2.5F));
        }
    }

}