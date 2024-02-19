package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.animal.IcefieldDeer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IcefieldDeerModel<T extends IcefieldDeer> extends HierarchicalModel<T> {

    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public IcefieldDeerModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = this.body.getChild("neck").getChild("head");
        this.rightHindLeg = this.body.getChild("right_hind_leg");
        this.leftHindLeg = this.body.getChild("left_hind_leg");
        this.rightFrontLeg = this.body.getChild("right_front_leg");
        this.leftFrontLeg = this.body.getChild("left_front_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.0F, -7.0F, 8.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));
        body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(30, 0).addBox(-1.5F, -1.25F, -0.75F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, 0.3054F, 0.0F, 0.0F));
        body.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(45, 9).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.05F, 4.0F, -4.95F));
        body.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(45, 9).mirror().addBox(-2.0F, 0.0F, -2.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.05F, 4.0F, -4.95F));
        body.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(45, 9).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.05F, 4.0F, 5.05F));
        body.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(45, 9).mirror().addBox(-2.0F, 0.0F, -2.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.05F, 4.0F, 5.05F));
        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 21).addBox(-2.5F, -8.5F, -0.5F, 5.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -7.0F));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(19, 21).addBox(-3.0F, -5.0F, -4.0F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.01F))
                .texOffs(47, 28).addBox(-2.5F, -3.0F, -6.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -7.5F, 1.0F));
        head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(1, 32).mirror().addBox(0.0F, -7.75F, -2.75F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(2.0F, -5.0F, -2.0F, -0.2182F, 0.0F, 0.5236F));
        head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(1, 32).addBox(0.0F, -7.75F, -2.75F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-3.0F, -5.0F, -2.0F, -0.2182F, 0.0F, -0.5236F));
        head.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(31, 8).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -3.5F, 3.0F, 0.5051F, -0.5086F, -0.263F));
        head.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(31, 8).mirror().addBox(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, -3.5F, 3.0F, 0.5051F, 0.5086F, 0.263F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float)Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180.0F);
        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public ModelPart root() {
        return this.body;
    }
    
}
