package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.common.entities.animal.AurorianSheep;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianSheepModel<T extends AurorianSheep> extends SheepModel<T> {

    private final ModelPart tail;

    public AurorianSheepModel(ModelPart root) {
        super(root);
        this.tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 11.75F, -6.75F));
        head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -2.0F, -7.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.829F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                .texOffs(0, 42).mirror().addBox(-1.25F, -4.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 42).addBox(6.25F, -4.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, -6.0F, 10.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, -1.0F));
        body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 24).mirror().addBox(0.0F, 0.0F, -6.0F, 0.0F, 3.0F, 14.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(5.0F, 5.0F, 0.0F, 0.0F, 0.0F, -0.1309F));
        body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, 0.0F, -6.0F, 0.0F, 3.0F, 14.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-5.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.1309F));
        body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(33, 35).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 8.0F, 0.1309F, 0.0F, 0.0F));
        body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(33, 35).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -6.0F, -0.1309F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(26, 41).addBox(-1.75F, -1.0F, -2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 21.0F, 5.0F));
        partDefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(26, 41).mirror().addBox(-2.25F, -1.0F, -2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 21.0F, 5.0F));
        partDefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(26, 41).addBox(-1.75F, -1.0F, -2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 21.0F, -4.0F));
        partDefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(26, 41).mirror().addBox(-2.25F, -1.0F, -2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 21.0F, -4.0F));
        PartDefinition tail = partDefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 7.0F));
        tail.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(13, 42).addBox(-1.5F, -1.0F, -0.75F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        this.head.y = 12.0F + entity.getHeadEatPositionScale(partialTick) * 9.0F;
        this.headXRot = entity.getHeadEatAngleScale(partialTick);
    }

}