package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.animal.BreadBeast;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BreadBeastModel<T extends BreadBeast> extends HierarchicalModel<T> {

    private final ModelPart body;

    public BreadBeastModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.0F, -10.0F, 12.0F, 10.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.75F, 0.0F));
        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -9.25F));
        neck.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 30).addBox(-3.0F, -15.0F, -1.5F, 6.0F, 19.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 30).addBox(-3.5F, -5.0F, -5.0F, 7.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, -2.0F));
        head.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(0, -3).addBox(0.0F, 0.0F, -1.5F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-3.25F, -2.5F, 3.25F, 0.2869F, -0.1464F, 0.2699F));
        head.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(0, -3).mirror().addBox(0.0F, 0.0F, -1.5F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(3.25F, -2.5F, 3.25F, 0.2869F, 0.1464F, -0.2699F));
        body.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(0, 55).addBox(-1.0F, 0.0F, -2.0F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, 2.25F, -6.5F));
        body.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(0, 55).addBox(-1.0F, 0.0F, -2.0F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.25F, 2.25F, 6.5F));
        body.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(0, 55).mirror().addBox(-4.0F, 0.0F, -2.0F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.25F, 2.25F, 6.5F));
        body.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(0, 55).mirror().addBox(-4.0F, 0.0F, -2.0F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.5F, 2.25F, -6.5F));
        body.addOrReplaceChild("tail_full", CubeListBuilder.create().texOffs(20, 63).addBox(-3.0F, 0.0F, -0.25F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 10.0F, -0.3927F, 0.0F, 0.0F));
        body.addOrReplaceChild("tail_eaten_1", CubeListBuilder.create().texOffs(52, 65).addBox(-3.0F, 0.0F, -0.25F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 10.0F, -0.3927F, 0.0F, 0.0F));
        body.addOrReplaceChild("tail_eaten_2", CubeListBuilder.create().texOffs(55, 56).addBox(-3.0F, 0.0F, -0.25F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 10.0F, -0.3927F, 0.0F, 0.0F));
        body.addOrReplaceChild("tail_empty", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, 10.0F, -0.3927F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 80, 80);
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