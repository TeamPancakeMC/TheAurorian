package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiderMotherModel<T extends SpiderMother> extends HierarchicalModel<T> {

    private final ModelPart body;
    private final ModelPart head;

    public SpiderMotherModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = this.body.getChild("body_2").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition body_2 = body.addOrReplaceChild("body_2", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-10.0F, -9.0F, -9.0F, 20.0F, 13.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(64, 0).addBox(-6.0F, 3.25F, -4.5F, 12.0F, 5.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition head = body_2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 37).addBox(-5.5F, -3.0F, -11.0F, 11.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, -7.5F, 0.2618F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(-16, 57).mirror().addBox(5.0F, 1.0F, -17.25F, 6.0F, 0.0F, 16.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3065F, 0.0832F, 0.0263F));
        head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(-16, 57).addBox(-11.0F, 1.0F, -17.25F, 6.0F, 0.0F, 16.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3065F, -0.0832F, -0.0263F));
        PartDefinition fang_right = head.addOrReplaceChild("fang_right", CubeListBuilder.create().texOffs(45, 44).addBox(-2.0F, -4.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -2.0F, -11.0F, 0.2597F, 0.0338F, -0.1265F));
        fang_right.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(62, 44).addBox(0.0F, -5.75F, -0.5F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -4.0F, -1.0F, -0.3491F, 0.0F, 0.0F));
        PartDefinition fang_left = head.addOrReplaceChild("fang_left", CubeListBuilder.create().texOffs(45, 44).mirror().addBox(-2.0F, -4.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, -2.0F, -11.0F, 0.2597F, -0.0338F, 0.1265F));
        fang_left.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(62, 44).mirror().addBox(0.0F, -5.75F, -0.5F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.0F, -1.0F, -0.3491F, 0.0F, 0.0F));
        PartDefinition body_3 = body_2.addOrReplaceChild("body_3", CubeListBuilder.create()
                .texOffs(71, 24).addBox(-12.0F, -9.0F, 0.0F, 24.0F, 19.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(139, 10).addBox(-7.0F, -6.5F, 16.0F, 14.0F, 14.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 57).addBox(-5.0F, -4.5F, 33.0F, 10.0F, 10.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 12.5F, 0.3054F, 0.0F, 0.0F));
        body_3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(203, 11).addBox(-9.3436F, -0.2851F, 1.1056F, 6.0F, 6.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 18.0F, 0.1462F, -0.5659F, -2.2177F));
        body_3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(203, 11).addBox(-9.3436F, -6.2851F, 4.1056F, 6.0F, 6.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 18.0F, 0.0569F, -0.4557F, -1.015F));
        body_3.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(203, 11).addBox(-7.3436F, -8.2851F, 3.1056F, 6.0F, 6.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 18.0F, 0.4238F, -0.4005F, -0.3439F));
        body_3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(203, 11).addBox(-2.0F, 0.5F, 19.0F, 6.0F, 6.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.3491F, 0.0F));
        PartDefinition leg_blade_right = body_2.addOrReplaceChild("leg_blade_right", CubeListBuilder.create().texOffs(57, 62).addBox(-4.0F, -2.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.75F, -4.5F, -6.0F, -1.2859F, 0.8221F, -0.2874F));
        PartDefinition leg_blade_right_2 = leg_blade_right.addOrReplaceChild("leg_blade_right_2", CubeListBuilder.create().texOffs(97, 66).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 22.0F, 0.0F, 0.0F, -0.3054F, -0.48F));
        PartDefinition leg_blade_right_3 = leg_blade_right_2.addOrReplaceChild("leg_blade_right_3", CubeListBuilder.create()
                .texOffs(57, 62).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(78, 63).addBox(-7.5F, -1.0F, -2.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.6277F, -0.559F, -1.099F));
        leg_blade_right_3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(158, 43).addBox(-11.0F, -11.5F, 0.0F, 10.0F, 43.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0436F));
        PartDefinition leg_blade_left = body_2.addOrReplaceChild("leg_blade_left", CubeListBuilder.create().texOffs(57, 62).mirror().addBox(-1.0F, -2.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.75F, -4.5F, -6.0F, -1.2859F, -0.8221F, 0.2874F));
        PartDefinition leg_blade_left_2 = leg_blade_left.addOrReplaceChild("leg_blade_left_2", CubeListBuilder.create().texOffs(97, 66).mirror().addBox(-2.0F, -0.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 22.0F, 0.0F, 0.0F, 0.3054F, 0.48F));
        PartDefinition leg_blade_left_3 = leg_blade_left_2.addOrReplaceChild("leg_blade_left_3", CubeListBuilder.create()
                .texOffs(57, 62).mirror().addBox(-2.5F, 0.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(78, 63).mirror().addBox(2.5F, -1.0F, -2.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.6277F, 0.559F, 1.099F));
        leg_blade_left_3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(158, 43).mirror().addBox(1.0F, -11.5F, 0.0F, 10.0F, 43.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0436F));
        PartDefinition leg_right_a = body_2.addOrReplaceChild("leg_right_a", CubeListBuilder.create().texOffs(57, 62).addBox(-4.0F, -2.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 2.0F, 1.0F, -0.2982F, 0.221F, 2.1085F));
        PartDefinition leg_right_a_2 = leg_right_a.addOrReplaceChild("leg_right_a_2", CubeListBuilder.create()
                .texOffs(97, 66).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(97, 75).addBox(-9.0F, -2.0F, 0.0F, 7.0F, 4.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.5F, 23.5F, 0.0F, 0.0F, 0.0F, -0.7854F));
        leg_right_a_2.addOrReplaceChild("leg_right_a_3", CubeListBuilder.create()
                .texOffs(57, 62).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(78, 74).addBox(-6.5F, 13.5F, 0.0F, 9.0F, 23.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, 0.0F, -0.9599F));
        PartDefinition leg_left_a = body_2.addOrReplaceChild("leg_left_a", CubeListBuilder.create().texOffs(57, 62).mirror().addBox(-1.0F, -2.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, 2.0F, 1.0F, -0.2982F, -0.221F, -2.1085F));
        PartDefinition leg_left_a_2 = leg_left_a.addOrReplaceChild("leg_left_a_2", CubeListBuilder.create()
                .texOffs(97, 66).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(97, 75).mirror().addBox(2.0F, -2.0F, 0.0F, 7.0F, 4.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(1.5F, 23.5F, 0.0F, 0.0F, 0.0F, 0.7854F));
        leg_left_a_2.addOrReplaceChild("leg_left_a_3", CubeListBuilder.create()
                .texOffs(57, 62).mirror().addBox(-2.5F, 0.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(78, 74).mirror().addBox(-2.5F, 13.5F, 0.0F, 9.0F, 23.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, 0.0F, 0.9599F));
        PartDefinition leg_right_b = body_2.addOrReplaceChild("leg_right_b", CubeListBuilder.create().texOffs(57, 62).addBox(-4.0F, -2.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 2.0F, 6.25F, 0.0141F, -0.0268F, 2.1244F));
        PartDefinition leg_right_b_2 = leg_right_b.addOrReplaceChild("leg_right_b_2", CubeListBuilder.create()
                .texOffs(97, 66).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(97, 75).addBox(-9.0F, -2.0F, 0.0F, 7.0F, 4.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.5F, 23.5F, 0.0F, 0.0F, 0.0F, -0.7854F));
        leg_right_b_2.addOrReplaceChild("leg_right_b_3", CubeListBuilder.create()
                .texOffs(57, 62).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(78, 74).addBox(-6.5F, 13.5F, 0.0F, 9.0F, 23.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, 0.0F, -0.9599F));
        PartDefinition leg_left_b = body_2.addOrReplaceChild("leg_left_b", CubeListBuilder.create().texOffs(57, 62).mirror().addBox(-1.0F, -2.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, 2.0F, 6.25F, 0.0141F, 0.0268F, -2.1244F));
        PartDefinition leg_left_b_2 = leg_left_b.addOrReplaceChild("leg_left_b_2", CubeListBuilder.create()
                .texOffs(97, 66).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(97, 75).mirror().addBox(2.0F, -2.0F, 0.0F, 7.0F, 4.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(1.5F, 23.5F, 0.0F, 0.0F, 0.0F, 0.7854F));
        leg_left_b_2.addOrReplaceChild("leg_left_b_3", CubeListBuilder.create()
                .texOffs(57, 62).mirror().addBox(-2.5F, 0.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(78, 74).mirror().addBox(-2.5F, 13.5F, 0.0F, 9.0F, 23.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, 0.0F, 0.9599F));
        PartDefinition leg_right_c = body_2.addOrReplaceChild("leg_right_c", CubeListBuilder.create().texOffs(57, 62).addBox(-4.0F, -2.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 2.0F, 12.25F, 0.3108F, -0.2071F, 2.1022F));
        PartDefinition leg_right_c_2 = leg_right_c.addOrReplaceChild("leg_right_c_2", CubeListBuilder.create()
                .texOffs(97, 66).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(97, 75).addBox(-9.0F, -2.0F, 0.0F, 7.0F, 4.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.5F, 23.5F, 0.0F, 0.0F, 0.0F, -0.7854F));
        leg_right_c_2.addOrReplaceChild("leg_right_c_3", CubeListBuilder.create()
                .texOffs(57, 62).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(78, 74).addBox(-6.5F, 13.5F, 0.0F, 9.0F, 23.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, 0.0F, -0.9599F));
        PartDefinition leg_left_c = body_2.addOrReplaceChild("leg_left_c", CubeListBuilder.create().texOffs(57, 62).mirror().addBox(-1.0F, -2.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, 2.0F, 12.25F, 0.3108F, 0.2071F, -2.1022F));
        PartDefinition leg_left_c_2 = leg_left_c.addOrReplaceChild("leg_left_c_2", CubeListBuilder.create()
                .texOffs(97, 66).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(97, 75).mirror().addBox(2.0F, -2.0F, 0.0F, 7.0F, 4.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(1.5F, 23.5F, 0.0F, 0.0F, 0.0F, 0.7854F));
        leg_left_c_2.addOrReplaceChild("leg_left_c_3", CubeListBuilder.create()
                .texOffs(57, 62).mirror().addBox(-2.5F, 0.0F, -2.5F, 5.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(78, 74).mirror().addBox(-2.5F, 13.5F, 0.0F, 9.0F, 23.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, 0.0F, 0.9599F));
        return LayerDefinition.create(meshDefinition, 256, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float)Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180.0F);
    }

    @Override
    public ModelPart root() {
        return this.body;
    }

}