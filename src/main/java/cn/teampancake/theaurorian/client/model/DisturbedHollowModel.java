package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.common.entities.monster.DisturbedHollow;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisturbedHollowModel<T extends DisturbedHollow> extends SimpleHumanoidModel<T> {

    public ModelPart rightLeg;
    public ModelPart leftLeg;

    public DisturbedHollowModel(ModelPart root) {
        super(root);
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.75F, 0.0F, 0.2182F, 0.0F, 0.0F));
        PartDefinition head = all.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 19).addBox(-3.5F, -7.0F, -4.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, -1.25F, -0.2182F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-2.75F, -10.25F, -4.5F, 4.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, -0.75F, 0.0F, 0.0F, 0.0F, 0.2182F));
        head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 34).addBox(-1.25F, -10.25F, -4.5F, 4.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -0.75F, 0.0F, 0.0F, 0.0F, -0.2182F));
        all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -2.5F, 10.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        all.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(29, 22).addBox(-4.0F, -2.5F, -2.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 1.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        all.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(29, 22).mirror().addBox(0.0F, -2.5F, -2.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 1.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        all.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(31, 1).addBox(-2.75F, -1.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 13.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        all.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(31, 1).mirror().addBox(-1.25F, -1.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.75F, 13.0F, 0.0F, -0.2182F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg);
    }

}