package cn.teampancake.theaurorian.client.model.entity;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianCowModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart body;
    private final ModelPart head;
    protected final ModelPart rightHindLeg;
    protected final ModelPart leftHindLeg;
    protected final ModelPart rightFrontLeg;
    protected final ModelPart leftFrontLeg;

    public AurorianCowModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.rightHindLeg = this.body.getChild("right_leg_2");
        this.leftHindLeg = this.body.getChild("left_leg_2");
        this.rightFrontLeg = this.body.getChild("right_leg_1");
        this.leftFrontLeg = this.body.getChild("left_leg_1");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -4.0F, -11.0F, 18.0F, 16.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.5F, 0.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 53).addBox(-5.0F, -4.0F, -11.75F, 10.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.25F, -10.75F, 0.3054F, 0.0F, 0.0F));
        PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create().texOffs(43, 47).addBox(-4.7899F, -0.7158F, -2.2687F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -2.0F, -2.0F, -0.6378F, 0.1582F, 0.2095F));
        horn.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(43, 53).addBox(-0.6569F, -6.2642F, -2.2687F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));
        PartDefinition horn2 = head.addOrReplaceChild("horn2", CubeListBuilder.create().texOffs(43, 47).mirror().addBox(-0.2101F, -0.7158F, -2.2687F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, -2.0F, -2.0F, -0.6378F, -0.1582F, -0.2095F));
        horn2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(43, 53).mirror().addBox(-2.3431F, -6.2642F, -2.2687F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));
        head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 38).addBox(-4.5F, 0.0F, -10.25F, 9.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));
        body.addOrReplaceChild("right_leg_1", CubeListBuilder.create().texOffs(56, 53).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, 11.5F, -7.0F));
        body.addOrReplaceChild("left_leg_1", CubeListBuilder.create().texOffs(56, 53).mirror().addBox(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.5F, 11.5F, -7.0F));
        body.addOrReplaceChild("right_leg_2", CubeListBuilder.create().texOffs(56, 53).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, 11.5F, 7.0F));
        body.addOrReplaceChild("left_leg_2", CubeListBuilder.create().texOffs(56, 53).mirror().addBox(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.5F, 11.5F, 7.0F));
        body.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(81, 23).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(81, 39).addBox(-1.5F, 11.0F, 0.5F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 2.0F, 11.0F));
        return LayerDefinition.create(meshDefinition, 96, 96);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
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