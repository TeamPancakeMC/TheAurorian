package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.monster.FlowerLeech;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlowerLeechModel<T extends FlowerLeech> extends HierarchicalModel<T> {

    private final ModelPart body;

    public FlowerLeechModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, -2.5F));
        body.addOrReplaceChild("body_1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -3.0F, 7.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -2.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(23, 0).addBox(-3.5F, -3.5F, -3.0F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -5.0F));
        PartDefinition body_flower = body.addOrReplaceChild("body_flower", CubeListBuilder.create()
                .texOffs(0, 11).addBox(-3.5F, -14.0F, -1.0F, 7.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 33).addBox(-4.5F, -21.0F, -2.0F, 9.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(28, 30).addBox(-4.0F, -23.5F, -1.5F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 49).addBox(-4.0F, -14.0F, -1.5F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));
        body_flower.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(37, 42).addBox(-4.5F, -9.0F, 0.0F, 9.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -23.5F, 2.5F, 0.0F, 0.7854F, 0.0F));
        body_flower.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(37, 42).addBox(-4.5F, -9.0F, 0.0F, 9.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -23.5F, 2.5F, 0.0F, -0.7854F, 0.0F));
        PartDefinition leave_a_1 = body_flower.addOrReplaceChild("leave_a_1", CubeListBuilder.create().texOffs(22, 10).addBox(-5.5F, 0.0F, -6.0F, 11.0F, 0.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -21.0F, -1.5F, -0.4363F, 0.0F, 0.0F));
        leave_a_1.addOrReplaceChild("leave_b_1", CubeListBuilder.create().texOffs(20, 16).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, -6.0F, 1.3963F, 0.0F, 0.0F));
        PartDefinition leave_a_2 = body_flower.addOrReplaceChild("leave_a_2", CubeListBuilder.create().texOffs(22, 10).addBox(-5.5F, 0.0F, -6.0F, 11.0F, 0.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -21.0F, 6.5F, 2.7053F, 0.0F, 3.1416F));
        leave_a_2.addOrReplaceChild("leave_b_2", CubeListBuilder.create().texOffs(20, 16).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, -6.0F, 1.3963F, 0.0F, 0.0F));
        PartDefinition leave_a_3 = body_flower.addOrReplaceChild("leave_a_3", CubeListBuilder.create().texOffs(22, 10).addBox(-5.0F, 0.0F, -6.0F, 11.0F, 0.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(4.0F, -21.0F, 2.0F, 0.0F, -1.5708F, -0.4363F));
        leave_a_3.addOrReplaceChild("leave_b_3", CubeListBuilder.create().texOffs(20, 16).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.5F, 0.0F, -6.0F, 1.3963F, 0.0F, 0.0F));
        PartDefinition leave_a_4 = body_flower.addOrReplaceChild("leave_a_4", CubeListBuilder.create().texOffs(22, 10).addBox(-5.0F, 0.0F, -6.0F, 11.0F, 0.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-4.0F, -21.0F, 3.0F, 0.0F, 1.5708F, 0.4363F));
        leave_a_4.addOrReplaceChild("leave_b_4", CubeListBuilder.create().texOffs(20, 16).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.5F, 0.0F, -6.0F, 1.3963F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
    }

    @Override
    public ModelPart root() {
        return this.body;
    }

}