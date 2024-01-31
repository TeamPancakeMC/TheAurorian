package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.monster.CaveDweller;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CaveDwellerModel<T extends CaveDweller> extends HierarchicalModel<T> {

    private final ModelPart body;

    public CaveDwellerModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-8.0F, -15.0F, -8.0F, 16.0F, 12.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 63).addBox(-5.0F, -29.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(48, 49).addBox(-7.0F, -24.0F, -7.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(69, 49).addBox(-2.0F, -21.0F, -6.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(69, 49).addBox(-3.5F, -21.0F, 2.25F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(48, 49).addBox(1.0F, -24.0F, 2.75F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 0.0F));
        body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(48, 49).mirror().addBox(-3.0F, -9.0F, 0.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -15.0F, 2.25F, -0.211F, -0.056F, -0.2559F));
        body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(48, 49).addBox(-2.0F, -9.0F, -5.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -15.0F, -2.25F, 0.211F, -0.056F, 0.2559F));
        body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(69, 49).mirror().addBox(-2.0F, -6.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, -15.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
        body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(69, 49).addBox(-3.0F, -6.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -15.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        PartDefinition leg_front_right_1 = body.addOrReplaceChild("leg_front_right_1", CubeListBuilder.create().texOffs(0, 28).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -4.25F, -5.5F, -0.493F, -0.639F, 0.7333F));
        PartDefinition leg_front_right_2 = leg_front_right_1.addOrReplaceChild("leg_front_right_2", CubeListBuilder.create().texOffs(17, 32).addBox(-8.5F, -1.75F, -2.5F, 11.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, -0.2182F));
        PartDefinition leg_front_right_3 = leg_front_right_2.addOrReplaceChild("leg_front_right_3", CubeListBuilder.create().texOffs(0, 42).addBox(-3.0F, -4.0F, -3.0F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, 0.25F, 0.0F, 0.0F, 0.0F, -0.2618F));
        leg_front_right_3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(49, 3).addBox(0.0F, 0.0F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 9.0F, 0.0F, 0.0F, 0.0F, -0.6109F));
        leg_front_right_3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(23, 42).addBox(-5.0F, -7.0F, -4.0F, 4.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
        PartDefinition leg_back_right_1 = body.addOrReplaceChild("leg_back_right_1", CubeListBuilder.create().texOffs(0, 28).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -4.25F, 5.5F, 0.493F, 0.639F, 0.7333F));
        PartDefinition leg_back_right_2 = leg_back_right_1.addOrReplaceChild("leg_back_right_2", CubeListBuilder.create().texOffs(17, 32).addBox(-8.5F, -1.75F, -2.5F, 11.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, -0.2182F));
        PartDefinition leg_back_right_3 = leg_back_right_2.addOrReplaceChild("leg_back_right_3", CubeListBuilder.create().texOffs(0, 42).addBox(-3.0F, -4.0F, -3.0F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, 0.25F, 0.0F, 0.0F, 0.0F, -0.2618F));
        leg_back_right_3.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(49, 3).addBox(0.0F, 0.0F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 9.0F, 0.0F, 0.0F, 0.0F, -0.6109F));
        leg_back_right_3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(23, 42).addBox(-5.0F, -7.0F, -4.0F, 4.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
        PartDefinition leg_back_left_1 = body.addOrReplaceChild("leg_back_left_1", CubeListBuilder.create().texOffs(0, 28).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.5F, -4.25F, 5.5F, 0.493F, -0.639F, -0.7333F));
        PartDefinition leg_back_left_2 = leg_back_left_1.addOrReplaceChild("leg_back_left_2", CubeListBuilder.create().texOffs(17, 32).mirror().addBox(-2.5F, -1.75F, -2.5F, 11.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.2182F));
        PartDefinition leg_back_left_3 = leg_back_left_2.addOrReplaceChild("leg_back_left_3", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-2.0F, -4.0F, -3.0F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.5F, 0.25F, 0.0F, 0.0F, 0.0F, 0.2618F));
        leg_back_left_3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(49, 3).mirror().addBox(-4.0F, 0.0F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 9.0F, 0.0F, 0.0F, 0.0F, 0.6109F));
        leg_back_left_3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(23, 42).mirror().addBox(1.0F, -7.0F, -4.0F, 4.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        PartDefinition leg_front_left_1 = body.addOrReplaceChild("leg_front_left_1", CubeListBuilder.create().texOffs(0, 28).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.5F, -4.25F, -5.5F, -0.493F, 0.639F, -0.7333F));
        PartDefinition leg_front_left_2 = leg_front_left_1.addOrReplaceChild("leg_front_left_2", CubeListBuilder.create().texOffs(17, 32).mirror().addBox(-2.5F, -1.75F, -2.5F, 11.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.2182F));
        PartDefinition leg_front_left_3 = leg_front_left_2.addOrReplaceChild("leg_front_left_3", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-2.0F, -4.0F, -3.0F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.5F, 0.25F, 0.0F, 0.0F, 0.0F, 0.2618F));
        leg_front_left_3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(49, 3).mirror().addBox(-4.0F, 0.0F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 9.0F, 0.0F, 0.0F, 0.0F, 0.6109F));
        leg_front_left_3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(23, 42).mirror().addBox(1.0F, -7.0F, -4.0F, 4.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        return LayerDefinition.create(meshDefinition, 96, 96);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.body.yRot = netHeadYaw * ((float)Math.PI / 180.0F);
    }

    @Override
    public ModelPart root() {
        return this.body;
    }
    
}