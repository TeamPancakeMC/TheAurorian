package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.monster.TongScorpion;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TongScorpionModel<T extends TongScorpion> extends HierarchicalModel<T> {

    private final ModelPart body;

    public TongScorpionModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 1.0F));
        PartDefinition body_2 = body.addOrReplaceChild("body_2", CubeListBuilder.create()
                .texOffs(1, 9).addBox(-5.0F, 1.0F, -11.5F, 10.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(29, 30).addBox(-4.0F, 0.0F, 4.0F, 8.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));
        PartDefinition somite_1 = body_2.addOrReplaceChild("somite_1", CubeListBuilder.create(), PartPose.offset(3.0F, -1.0F, -6.0F));
        somite_1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(23, 0).mirror().addBox(0.0F, 0.0F, -2.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0852F, -0.0189F, 0.2174F));
        somite_1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));
        somite_1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(23, 0).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.0852F, 0.0189F, -0.2174F));
        PartDefinition somite_3 = body_2.addOrReplaceChild("somite_3", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -0.5F, -10.0F, 0.1745F, 0.0F, 0.0F));
        somite_3.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(23, 0).mirror().addBox(0.0F, 0.0F, -2.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0852F, -0.0189F, 0.2174F));
        somite_3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));
        somite_3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(23, 0).addBox(-3.0F, 0.0F, -2.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.0852F, 0.0189F, -0.2174F));
        PartDefinition somite_2 = body_2.addOrReplaceChild("somite_2", CubeListBuilder.create(), PartPose.offset(3.0F, -1.0F, -2.0F));
        somite_2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(23, 0).mirror().addBox(0.0F, 0.0F, -2.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0852F, -0.0189F, 0.2174F));
        somite_2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));
        somite_2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(23, 0).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.0852F, 0.0189F, -0.2174F));
        PartDefinition somite_4 = body_2.addOrReplaceChild("somite_4", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -0.75F, 2.0F, -0.1745F, 0.0F, 0.0F));
        somite_4.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(23, 0).mirror().addBox(0.0F, 0.0F, -2.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0852F, -0.0189F, 0.2174F));
        somite_4.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));
        somite_4.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(23, 0).addBox(-3.0F, 0.0F, -2.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.0852F, 0.0189F, -0.2174F));
        PartDefinition head = body_2.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 29).addBox(-4.0F, -1.75F, -6.0F, 8.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-3.5F, -1.825F, -3.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-2.25F, -1.825F, -5.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).mirror().addBox(1.25F, -1.825F, -5.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 29).mirror().addBox(2.5F, -1.825F, -3.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.0F, -11.0F, 0.1745F, 0.0F, 0.0F));
        head.addOrReplaceChild("fung_right", CubeListBuilder.create().texOffs(42, 2).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, -6.0F, -0.2618F, 0.0F, 0.0F));
        head.addOrReplaceChild("fung_left", CubeListBuilder.create().texOffs(42, 2).mirror().addBox(-2.0F, -1.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, 0.0F, -6.0F, -0.2618F, 0.0F, 0.0F));
        PartDefinition leg_left_a = body.addOrReplaceChild("leg_left_a", CubeListBuilder.create().texOffs(37, 8).addBox(-1.75F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 3.0F, -7.5F, -0.4396F, 0.1186F, 1.9079F));
        leg_left_a.addOrReplaceChild("leg_left_a_2", CubeListBuilder.create().texOffs(37, 8).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.25F, 5.0F, 0.0F, 0.0F, 0.0F, -1.2217F));
        PartDefinition leg_right_a = body.addOrReplaceChild("leg_right_a", CubeListBuilder.create().texOffs(37, 8).mirror().addBox(-0.25F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 3.0F, -7.5F, -0.4396F, -0.1186F, -1.9079F));
        leg_right_a.addOrReplaceChild("leg_right_a_2", CubeListBuilder.create().texOffs(37, 8).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(1.25F, 5.0F, 0.0F, 0.0F, 0.0F, 1.2217F));
        PartDefinition leg_left_b = body.addOrReplaceChild("leg_left_b", CubeListBuilder.create().texOffs(37, 8).addBox(-1.75F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 3.0F, -5.25F, -0.1314F, 0.0865F, 1.9521F));
        leg_left_b.addOrReplaceChild("leg_left_b_2", CubeListBuilder.create().texOffs(37, 8).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.25F, 5.0F, 0.0F, 0.0F, 0.0F, -1.2217F));
        PartDefinition leg_right_b = body.addOrReplaceChild("leg_right_b", CubeListBuilder.create().texOffs(37, 8).mirror().addBox(-0.25F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 3.0F, -5.25F, -0.1314F, -0.0865F, -1.9521F));
        leg_right_b.addOrReplaceChild("leg_right_b_2", CubeListBuilder.create().texOffs(37, 8).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(1.25F, 5.0F, 0.0F, 0.0F, 0.0F, 1.2217F));
        PartDefinition leg_left_c = body.addOrReplaceChild("leg_left_c", CubeListBuilder.create().texOffs(37, 8).addBox(-1.75F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 3.0F, -2.25F, 0.0F, 0.0F, 1.9635F));
        leg_left_c.addOrReplaceChild("leg_left_c_3", CubeListBuilder.create().texOffs(37, 8).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.25F, 5.0F, 0.0F, 0.0F, 0.0F, -1.2217F));
        PartDefinition leg_right_c = body.addOrReplaceChild("leg_right_c", CubeListBuilder.create().texOffs(37, 8).mirror().addBox(-0.25F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 3.0F, -2.25F, 0.0F, 0.0F, -1.9635F));
        leg_right_c.addOrReplaceChild("leg_right_c_3", CubeListBuilder.create().texOffs(37, 8).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(1.25F, 5.0F, 0.0F, 0.0F, 0.0F, 1.2217F));
        PartDefinition leg_left_d = body.addOrReplaceChild("leg_left_d", CubeListBuilder.create().texOffs(37, 8).addBox(-1.75F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 3.0F, 0.75F, 0.3079F, -0.1248F, 1.9239F));
        leg_left_d.addOrReplaceChild("leg_left_d_2", CubeListBuilder.create().texOffs(37, 8).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.25F, 5.0F, 0.0F, 0.0F, 0.0F, -1.2217F));
        PartDefinition leg_right_d = body.addOrReplaceChild("leg_right_d", CubeListBuilder.create().texOffs(37, 8).mirror().addBox(-0.25F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 3.0F, 0.75F, 0.3079F, 0.1248F, -1.9239F));
        leg_right_d.addOrReplaceChild("leg_right_d_2", CubeListBuilder.create().texOffs(37, 8).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(1.25F, 5.0F, 0.0F, 0.0F, 0.0F, 1.2217F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(12, 39).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 1.0F, 8.75F, 0.5672F, 0.0F, 0.0F));
        PartDefinition tail_2 = tail.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(12, 49).addBox(-2.5F, -1.5F, 0.0F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, 0.48F, 0.0F, 0.0F));
        PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create().texOffs(26, 54).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, 0.5236F, 0.0F, 0.0F));
        PartDefinition tail_4 = tail_3.addOrReplaceChild("tail_4", CubeListBuilder.create().texOffs(39, 39).addBox(-2.5F, -2.0F, -1.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.9599F, 0.0F, 0.0F));
        tail_4.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(44, 44).addBox(0.0F, 0.0F, 3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));
        PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(37, 8).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, -12.0F, -0.3281F, -0.0881F, 1.4004F));
        PartDefinition arm_right_2 = arm_right.addOrReplaceChild("arm_right_2", CubeListBuilder.create().texOffs(37, 8).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 2.75F, 1.0F, -1.0036F, 0.0F, 0.0F));
        PartDefinition arm_right_3 = arm_right_2.addOrReplaceChild("arm_right_3", CubeListBuilder.create()
                .texOffs(46, 8).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 10).addBox(-1.5F, 4.0F, 0.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, -0.5672F, 0.0F, 0.0F));
        arm_right_3.addOrReplaceChild("arm_right_4", CubeListBuilder.create().texOffs(57, 7).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -0.5F));
        PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(37, 8).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 2.0F, -12.0F, -0.3281F, 0.0881F, -1.4004F));
        PartDefinition arm_left_2 = arm_left.addOrReplaceChild("arm_left_2", CubeListBuilder.create().texOffs(37, 8).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.75F, 1.0F, -1.0036F, 0.0F, 0.0F));
        PartDefinition arm_left_3 = arm_left_2.addOrReplaceChild("arm_left_3", CubeListBuilder.create().texOffs(46, 8).mirror().addBox(-1.5F, -1.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, -0.5672F, 0.0F, 0.0F));
        arm_left_3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 39).mirror().addBox(-1.5F, -2.0F, -0.25F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));
        arm_left_3.addOrReplaceChild("arm_left_4", CubeListBuilder.create().texOffs(0, 8).mirror().addBox(-1.0F, -0.5F, -1.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 4.0F, 0.5F));
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