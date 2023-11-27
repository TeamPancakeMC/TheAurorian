package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.client.animation.DisturbedHollowAnimation;
import cn.teampancake.theaurorian.common.entities.monster.DisturbedHollow;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisturbedHollowModel<T extends DisturbedHollow> extends HierarchicalModel<T> {

    private final ModelPart all;
    private final ModelPart head;

    public DisturbedHollowModel(ModelPart root) {
        this.all = root.getChild("all");
        this.head = this.all.getChild("body").getChild("bone").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -26.75F, 0.0F, 0.2182F, 0.0F, 0.0F));
        body.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(31, 1).addBox(-2.75F, -1.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 13.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        body.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(31, 1).mirror().addBox(-1.25F, -1.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.75F, 13.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        PartDefinition bone = body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -13.0F, -2.5F, 10.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));
        PartDefinition head = bone.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -12.5F, -1.25F, -0.2182F, 0.0F, 0.0F));
        head.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(0, 19).addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, -0.5F));
        PartDefinition head3 = head.addOrReplaceChild("head3", CubeListBuilder.create(), PartPose.offset(-3.0F, -0.75F, 0.0F));
        head3.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 34).addBox(-1.25F, -10.25F, -4.5F, 4.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));
        PartDefinition head4 = head.addOrReplaceChild("head4", CubeListBuilder.create(), PartPose.offset(3.0F, -0.75F, 0.0F));
        head4.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-2.75F, -10.25F, -4.5F, 4.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));
        bone.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(29, 22).addBox(-4.0F, -2.5F, -2.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -12.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        bone.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(29, 22).mirror().addBox(0.0F, -2.5F, -2.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, -12.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float)Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180.0F);
        this.animate(entity.idleAnimationState, DisturbedHollowAnimation.IDLE, ageInTicks);
        this.animate(entity.attackAnimationState, DisturbedHollowAnimation.ATTACK, ageInTicks);
        if (!entity.isInWaterOrBubble()) {
            this.animateWalk(DisturbedHollowAnimation.WALK, limbSwing, limbSwingAmount, (1.5F), (2.5F));
        }
    }

    @Override
    public ModelPart root() {
        return this.all;
    }

}