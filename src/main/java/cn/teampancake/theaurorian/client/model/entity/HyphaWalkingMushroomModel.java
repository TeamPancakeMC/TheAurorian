package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.monster.HyphaWalkingMushroom;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class HyphaWalkingMushroomModel<T extends HyphaWalkingMushroom> extends HierarchicalModel<T> {

    private final ModelPart body;

    public HyphaWalkingMushroomModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-8.0F, -11.0F, -8.0F, 16.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 54).addBox(-7.5F, -6.0F, -7.5F, 15.0F, 7.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-6.5F, -13.5F, -6.5F, 13.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 0.0F));
        body.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(41, 9).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 1.0F, 0.0F));
        body.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(41, 9).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 1.0F, 0.0F));
        body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(48, 17).addBox(-3.0F, -1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.75F, -3.0F, 0.0F, 0.0F, 0.0F, 0.1309F));
        body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(48, 17).mirror().addBox(-1.0F, -1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.75F, -3.0F, 0.0F, 0.0F, 0.0F, -0.1309F));
        return LayerDefinition.create(meshDefinition, 64, 96);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public ModelPart root() {
        return this.body;
    }

}