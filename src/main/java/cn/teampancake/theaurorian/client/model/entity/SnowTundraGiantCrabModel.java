package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrab;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowTundraGiantCrabModel<T extends SnowTundraGiantCrab> extends HierarchicalModel<T> {

    private final ModelPart body;

    public SnowTundraGiantCrabModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-35.0F, -22.0F, -24.0F, 70.0F, 42.0F, 50.0F, new CubeDeformation(0.0F))
                .texOffs(46, 38).addBox(-31.0F, -18.0F, 26.0F, 62.0F, 38.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(191, 0).addBox(-32.0F, -30.0F, -19.0F, 53.0F, 8.0F, 41.0F, new CubeDeformation(0.0F))
                .texOffs(237, 105).addBox(-19.0F, -62.0F, -7.0F, 15.0F, 32.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(298, 116).addBox(-8.0F, -47.0F, -10.0F, 10.0F, 26.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(298, 116).addBox(14.0F, -40.0F, -10.0F, 10.0F, 26.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(339, 122).addBox(20.0F, -34.0F, -2.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(339, 122).addBox(2.0F, -34.0F, 19.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(339, 122).addBox(0.0F, -30.0F, 24.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(339, 122).addBox(5.0F, -41.0F, 6.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(339, 122).addBox(-24.0F, -41.0F, 12.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(339, 122).addBox(-24.0F, -36.0F, -21.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(298, 116).addBox(-6.0F, -45.0F, 17.0F, 10.0F, 26.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(298, 116).addBox(-11.0F, -56.0F, 0.0F, 10.0F, 26.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.0F));
        PartDefinition claw_right = body.addOrReplaceChild("claw_right", CubeListBuilder.create()
                .texOffs(0, 92).addBox(-16.5F, -10.0F, -12.0F, 18.0F, 17.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 92).addBox(1.5F, -10.0F, -12.0F, 18.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-33.0F, 3.0F, -10.0F, 0.1694F, -0.7167F, -0.3902F));
        PartDefinition claw_right_2 = claw_right.addOrReplaceChild("claw_right_2", CubeListBuilder.create()
                .texOffs(0, 127).addBox(-12.0F, -15.0F, -16.0F, 30.0F, 27.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(93, 127).addBox(18.0F, -15.0F, -16.0F, 20.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.5F, 0.0F, 3.25F, 0.7821F, 1.269F, 1.1123F));
        claw_right_2.addOrReplaceChild("claw_right_3", CubeListBuilder.create().texOffs(166, 129).addBox(0.0F, -2.0F, -7.0F, 21.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 5.0F, -8.0F, 0.0F, 0.0F, 0.1745F));
        PartDefinition claw_left = body.addOrReplaceChild("claw_left", CubeListBuilder.create()
                .texOffs(0, 92).mirror().addBox(-1.5F, -10.0F, -12.0F, 18.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 92).mirror().addBox(-19.5F, -10.0F, -12.0F, 18.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(33.0F, 3.0F, -10.0F, 0.1694F, 0.7167F, 0.3902F));
        PartDefinition claw_left_2 = claw_left.addOrReplaceChild("claw_left_2", CubeListBuilder.create()
                .texOffs(0, 127).mirror().addBox(-18.0F, -15.0F, -16.0F, 30.0F, 27.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(93, 127).mirror().addBox(-38.0F, -15.0F, -16.0F, 20.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(15.5F, 0.0F, 3.25F, 0.7821F, -1.269F, -1.1123F));
        claw_left_2.addOrReplaceChild("claw_left_3", CubeListBuilder.create().texOffs(166, 129).mirror().addBox(-21.0F, -2.0F, -7.0F, 21.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-15.0F, 5.0F, -8.0F, 0.0F, 0.0F, -0.1745F));
        PartDefinition leg_right_1_a = body.addOrReplaceChild("leg_right_1_a", CubeListBuilder.create().texOffs(241, 62).addBox(-9.0F, -7.0F, -6.0F, 14.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-32.75F, 16.0F, -17.5F, -0.225F, -0.257F, 0.6612F));
        leg_right_1_a.addOrReplaceChild("leg_right_1_b", CubeListBuilder.create().texOffs(298, 65).addBox(-4.0F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 7.0F, 0.0F, 0.0F, 0.0F, -0.6981F));
        PartDefinition leg_left_1_a = body.addOrReplaceChild("leg_left_1_a", CubeListBuilder.create().texOffs(241, 62).mirror().addBox(-5.0F, -7.0F, -6.0F, 14.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(32.75F, 16.0F, -17.5F, -0.225F, 0.257F, -0.6612F));
        leg_left_1_a.addOrReplaceChild("leg_left_1_b", CubeListBuilder.create().texOffs(298, 65).mirror().addBox(-3.0F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, 7.0F, 0.0F, 0.0F, 0.0F, 0.6981F));
        PartDefinition leg_right_2_a = body.addOrReplaceChild("leg_right_2_a", CubeListBuilder.create().texOffs(241, 62).addBox(-9.0F, -7.0F, -6.0F, 14.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.0F, 16.0F, -2.0F, 0.0F, 0.0F, 0.6109F));
        leg_right_2_a.addOrReplaceChild("leg_right_2_b", CubeListBuilder.create().texOffs(298, 65).addBox(-4.0F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 7.0F, 0.0F, 0.0F, 0.0F, -0.6981F));
        PartDefinition leg_left_2_a = body.addOrReplaceChild("leg_left_2_a", CubeListBuilder.create().texOffs(241, 62).mirror().addBox(-5.0F, -7.0F, -6.0F, 14.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(35.0F, 16.0F, -2.0F, 0.0F, 0.0F, -0.6109F));
        leg_left_2_a.addOrReplaceChild("leg_left_2_b", CubeListBuilder.create().texOffs(298, 65).mirror().addBox(-3.0F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, 7.0F, 0.0F, 0.0F, 0.0F, 0.6981F));
        PartDefinition leg_right_3_a = body.addOrReplaceChild("leg_right_3_a", CubeListBuilder.create().texOffs(241, 62).addBox(-9.0F, -7.0F, -6.0F, 14.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.5F, 16.0F, 13.25F, 0.1309F, 0.1309F, 0.6109F));
        leg_right_3_a.addOrReplaceChild("leg_right_3_b", CubeListBuilder.create().texOffs(298, 65).addBox(-4.0F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 7.0F, 0.0F, 0.0F, 0.0F, -0.6981F));
        PartDefinition leg_left_3_a = body.addOrReplaceChild("leg_left_3_a", CubeListBuilder.create().texOffs(241, 62).mirror().addBox(-5.0F, -7.0F, -6.0F, 14.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(35.5F, 16.0F, 13.25F, 0.1309F, -0.1309F, -0.6109F));
        leg_left_3_a.addOrReplaceChild("leg_left_3_b", CubeListBuilder.create().texOffs(298, 65).mirror().addBox(-3.0F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, 7.0F, 0.0F, 0.0F, 0.0F, 0.6981F));
        PartDefinition leg_right_4_a = body.addOrReplaceChild("leg_right_4_a", CubeListBuilder.create().texOffs(241, 62).addBox(-9.0F, -7.0F, -6.0F, 14.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-33.0F, 16.0F, 28.75F, 0.2726F, 0.2988F, 0.6695F));
        leg_right_4_a.addOrReplaceChild("leg_right_4_b", CubeListBuilder.create().texOffs(298, 65).addBox(-4.0F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 7.0F, 0.0F, 0.0F, 0.0F, -0.6981F));
        PartDefinition leg_left_4_a = body.addOrReplaceChild("leg_left_4_a", CubeListBuilder.create().texOffs(241, 62).mirror().addBox(-5.0F, -7.0F, -6.0F, 14.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(33.0F, 16.0F, 28.75F, 0.2726F, -0.2988F, -0.6695F));
        leg_left_4_a.addOrReplaceChild("leg_left_4_b", CubeListBuilder.create().texOffs(298, 65).mirror().addBox(-3.0F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, 7.0F, 0.0F, 0.0F, 0.0F, 0.6981F));
        return LayerDefinition.create(meshDefinition, 384, 192);
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