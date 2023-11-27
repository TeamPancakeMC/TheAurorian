package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SpiderMotherModel<T extends SpiderMother> extends HierarchicalModel<T> {

    private final ModelPart all;
    private final ModelPart head;

    public SpiderMotherModel(ModelPart root) {
        this.all = root.getChild("all");
        this.head = this.all.getChild("body").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));
        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -5.0F, 16.0F, 14.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(88, 32).addBox(-1.5F, -3.25F, 20.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(88, 32).addBox(-6.5F, -8.25F, 21.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(88, 32).addBox(-8.0F, -2.0F, 24.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(88, 32).addBox(0.0F, -10.0F, 24.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(70, 53).addBox(-5.0F, -7.0F, 24.0F, 10.0F, 10.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(0, 82).addBox(-11.0F, -12.0F, -2.0F, 22.0F, 20.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.3927F, 0.0F, 0.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(63, 7).addBox(-5.0F, -4.0F, -8.0F, 10.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(-12, 30).addBox(-8.0F, 1.75F, -14.5F, 7.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(-12, 30).mirror().addBox(1.0F, 1.75F, -14.5F, 7.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -1.0F, -5.0F));
        PartDefinition leg_right_1 = body.addOrReplaceChild("leg_right_1", CubeListBuilder.create().texOffs(0, 43).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 0.0F, 4.0F, 0.0F, 0.0F, 2.2689F));
        PartDefinition leg_right_lower_1 = leg_right_1.addOrReplaceChild("leg_right_lower_1", CubeListBuilder.create()
                .texOffs(17, 45).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 44).addBox(-2.0F, 8.5F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 14.0F, 0.0F, 0.0F, 0.0F, -1.6581F));
        leg_right_lower_1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(45, 44).addBox(-7.0F, -6.0F, 0.0F, 8.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.5F, 0.0F, 0.0F, 0.0F, -0.2182F));
        PartDefinition leg_left_1 = body.addOrReplaceChild("leg_left_1", CubeListBuilder.create().texOffs(0, 43).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, 0.0F, 4.0F, 0.0F, 0.0F, -2.2689F));
        PartDefinition leg_left_lower_1 = leg_left_1.addOrReplaceChild("leg_left_lower_1", CubeListBuilder.create()
                .texOffs(17, 45).mirror().addBox(-2.0F, -1.5F, -1.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 44).mirror().addBox(-2.0F, 8.5F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 14.0F, 0.0F, 0.0F, 0.0F, 1.6581F));
        leg_left_lower_1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(45, 44).mirror().addBox(-1.0F, -6.0F, 0.0F, 8.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 17.5F, 0.0F, 0.0F, 0.0F, 0.2182F));
        PartDefinition leg_right_3 = body.addOrReplaceChild("leg_right_3", CubeListBuilder.create().texOffs(0, 43).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.5F, -0.3008F, 0.1754F, 2.2723F));
        PartDefinition leg_right_lower_3 = leg_right_3.addOrReplaceChild("leg_right_lower_3", CubeListBuilder.create()
                .texOffs(17, 45).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 44).addBox(-2.0F, 8.5F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 14.0F, 0.0F, 0.0F, 0.0F, -1.6581F));
        leg_right_lower_3.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(45, 44).addBox(-7.0F, -6.0F, 0.0F, 8.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.5F, 0.0F, 0.0F, 0.0F, -0.2182F));
        PartDefinition leg_left_3 = body.addOrReplaceChild("leg_left_3", CubeListBuilder.create().texOffs(0, 43).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, 0.0F, 0.5F, -0.3008F, -0.1754F, -2.2723F));
        PartDefinition leg_left_lower_3 = leg_left_3.addOrReplaceChild("leg_left_lower_3", CubeListBuilder.create()
                .texOffs(17, 45).mirror().addBox(-2.0F, -1.5F, -1.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 44).mirror().addBox(-2.0F, 8.5F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 14.0F, 0.0F, 0.0F, 0.0F, 1.6581F));
        leg_left_lower_3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(45, 44).mirror().addBox(-1.0F, -6.0F, 0.0F, 8.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 17.5F, 0.0F, 0.0F, 0.0F, 0.2182F));
        PartDefinition leg_right_4 = body.addOrReplaceChild("leg_right_4", CubeListBuilder.create().texOffs(0, 43).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 0.0F, -4.5F, -0.651F, 0.3876F, 2.1124F));
        PartDefinition leg_right_lower_4 = leg_right_4.addOrReplaceChild("leg_right_lower_4", CubeListBuilder.create()
                .texOffs(17, 45).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 44).addBox(-2.0F, 8.5F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 14.0F, 0.0F, 0.0F, 0.0F, -1.6581F));
        leg_right_lower_4.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(45, 44).addBox(-7.0F, -6.0F, 0.0F, 8.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.5F, 0.0F, 0.0F, 0.0F, -0.2182F));
        PartDefinition leg_left_4 = body.addOrReplaceChild("leg_left_4", CubeListBuilder.create().texOffs(0, 43).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, 0.0F, -4.5F, -0.651F, -0.3876F, -2.1124F));
        PartDefinition leg_left_lower_4 = leg_left_4.addOrReplaceChild("leg_left_lower_4", CubeListBuilder.create()
                .texOffs(17, 45).mirror().addBox(-2.0F, -1.5F, -1.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 44).mirror().addBox(-2.0F, 8.5F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 14.0F, 0.0F, 0.0F, 0.0F, 1.6581F));
        leg_left_lower_4.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(45, 44).mirror().addBox(-1.0F, -6.0F, 0.0F, 8.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 17.5F, 0.0F, 0.0F, 0.0F, 0.2182F));
        PartDefinition leg_right_2 = body.addOrReplaceChild("leg_right_2", CubeListBuilder.create().texOffs(0, 43).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 0.0F, 7.0F, 0.3522F, -0.3018F, 2.2042F));
        PartDefinition leg_right_lower_2 = leg_right_2.addOrReplaceChild("leg_right_lower_2", CubeListBuilder.create()
                .texOffs(17, 45).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 44).addBox(-2.0F, 8.5F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 14.0F, 0.0F, 0.0F, 0.0F, -1.6581F));
        leg_right_lower_2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(45, 44).addBox(-7.0F, -6.0F, 0.0F, 8.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.5F, 0.0F, 0.0F, 0.0F, -0.2182F));
        PartDefinition leg_left_2 = body.addOrReplaceChild("leg_left_2", CubeListBuilder.create().texOffs(0, 43).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, 0.0F, 7.0F, 0.3522F, 0.3018F, -2.2042F));
        PartDefinition leg_left_lower_2 = leg_left_2.addOrReplaceChild("leg_left_lower_2", CubeListBuilder.create()
                .texOffs(17, 45).mirror().addBox(-2.0F, -1.5F, -1.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 44).mirror().addBox(-2.0F, 8.5F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 14.0F, 0.0F, 0.0F, 0.0F, 1.6581F));
        leg_left_lower_2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(45, 44).mirror().addBox(-1.0F, -6.0F, 0.0F, 8.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 17.5F, 0.0F, 0.0F, 0.0F, 0.2182F));
        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float)Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180.0F);
    }

    @Override
    public ModelPart root() {
        return this.all;
    }

}