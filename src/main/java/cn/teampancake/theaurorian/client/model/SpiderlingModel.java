package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.common.entities.monster.Spiderling;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SpiderlingModel<T extends Spiderling> extends HierarchicalModel<T> {

    private final ModelPart all;
    private final ModelPart head;

    public SpiderlingModel(ModelPart root) {
        this.all = root.getChild("all");
        this.head = this.all.getChild("body").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, 0.0F));
        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -4.0F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
        body.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(37, 30).addBox(-2.0F, -2.5F, 7.75F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-4.5F, -4.5F, -1.0F, 9.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.3054F, 0.0F, 0.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(29, 4).addBox(-2.5F, -2.5F, -3.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(41, 6).addBox(-4.0F, 0.25F, -6.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(41, 6).mirror().addBox(1.0F, 0.25F, -6.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.75F, -4.0F));
        PartDefinition leg_right_1 = body.addOrReplaceChild("leg_right_1", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 0.75F, 0.25F, 0.0F, 0.0F, 2.0944F));
        PartDefinition leg_right_lower_1 = leg_right_1.addOrReplaceChild("leg_right_lower_1", CubeListBuilder.create().texOffs(9, 15).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 4.5F, 0.0F, 0.0F, 0.0F, -1.309F));
        leg_right_lower_1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 16).addBox(-2.75F, -0.75F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        PartDefinition leg_left_1 = body.addOrReplaceChild("leg_left_1", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-1.5F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, 0.75F, 0.25F, 0.0F, 0.0F, -2.0944F));
        PartDefinition leg_left_lower_1 = leg_left_1.addOrReplaceChild("leg_left_lower_1", CubeListBuilder.create().texOffs(9, 15).mirror().addBox(-1.0F, -1.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 4.5F, 0.0F, 0.0F, 0.0F, 1.309F));
        leg_left_lower_1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 16).mirror().addBox(-0.25F, -0.75F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
        PartDefinition leg_right_3 = body.addOrReplaceChild("leg_right_3", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 0.75F, -1.25F, -0.3927F, 0.0F, 2.0944F));
        PartDefinition leg_right_lower_3 = leg_right_3.addOrReplaceChild("leg_right_lower_3", CubeListBuilder.create().texOffs(9, 15).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 4.5F, 0.0F, -0.2212F, 0.1714F, -1.3377F));
        leg_right_lower_3.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(16, 16).addBox(-2.75F, -0.75F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        PartDefinition leg_left_3 = body.addOrReplaceChild("leg_left_3", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-1.5F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, 0.75F, -1.25F, -0.3927F, 0.0F, -2.0944F));
        PartDefinition leg_left_lower_3 = leg_left_3.addOrReplaceChild("leg_left_lower_3", CubeListBuilder.create().texOffs(9, 15).mirror().addBox(-1.0F, -1.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 4.5F, 0.0F, -0.2212F, -0.1714F, 1.3377F));
        leg_left_lower_3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(16, 16).mirror().addBox(-0.25F, -0.75F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
        PartDefinition leg_right_4 = body.addOrReplaceChild("leg_right_4", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, 0.75F, -2.0F, -0.9997F, 0.1103F, 2.165F));
        PartDefinition leg_right_lower_4 = leg_right_4.addOrReplaceChild("leg_right_lower_4", CubeListBuilder.create().texOffs(9, 15).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 4.5F, 0.0F, -0.2685F, 0.309F, -1.1669F));
        leg_right_lower_4.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(16, 16).addBox(-2.75F, -0.75F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        PartDefinition leg_left_4 = body.addOrReplaceChild("leg_left_4", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-1.5F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.25F, 0.75F, -2.0F, -0.9997F, -0.1103F, -2.165F));
        PartDefinition leg_left_lower_4 = leg_left_4.addOrReplaceChild("leg_left_lower_4", CubeListBuilder.create().texOffs(9, 15).mirror().addBox(-1.0F, -1.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 4.5F, 0.0F, -0.2685F, -0.309F, 1.1669F));
        leg_left_lower_4.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(16, 16).mirror().addBox(-0.25F, -0.75F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
        PartDefinition leg_right_2 = body.addOrReplaceChild("leg_right_2", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 0.75F, 2.0F, 0.3444F, -0.1925F, 2.0608F));
        PartDefinition leg_right_lower_2 = leg_right_2.addOrReplaceChild("leg_right_lower_2", CubeListBuilder.create().texOffs(9, 15).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 4.5F, 0.0F, 0.1309F, -0.0873F, -1.309F));
        leg_right_lower_2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(16, 16).addBox(-2.75F, -0.75F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        PartDefinition leg_left_2 = body.addOrReplaceChild("leg_left_2", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-1.5F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, 0.75F, 2.0F, 0.3444F, 0.1925F, -2.0608F));
        PartDefinition leg_left_lower_2 = leg_left_2.addOrReplaceChild("leg_left_lower_2", CubeListBuilder.create().texOffs(9, 15).mirror().addBox(-1.0F, -1.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 4.5F, 0.0F, 0.1309F, 0.0873F, 1.309F));
        leg_left_lower_2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(16, 16).mirror().addBox(-0.25F, -0.75F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
        return LayerDefinition.create(meshDefinition, 64, 64);
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