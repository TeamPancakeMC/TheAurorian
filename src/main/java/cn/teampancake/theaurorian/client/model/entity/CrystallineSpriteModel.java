package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.monster.CrystallineSprite;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrystallineSpriteModel<T extends CrystallineSprite> extends EntityModel<T> {

    private final ModelPart all;
    private final ModelPart orbit;

    public CrystallineSpriteModel(ModelPart root) {
        this.all = root.getChild("all");
        this.orbit = this.all.getChild("orbit");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create()
                .texOffs(40, 24).addBox(-3.0F, -1.25F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(33, 5).addBox(-3.5F, -11.0F, -1.5F, 7.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-2.5F, -13.0F, -1.55F, 5.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 0.0F));
        all.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(13, 13).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(0.0F, -11.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        all.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-2.0F, -5.0F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(3.5F, -6.0F, 0.0F, 0.0F, 0.0F, 0.1309F));
        all.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, -5.0F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-3.5F, -6.0F, 0.0F, 0.0F, 0.0F, -0.1309F));
        all.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.25F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition orbit = all.addOrReplaceChild("orbit", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        orbit.addOrReplaceChild("stone1", CubeListBuilder.create().texOffs(23, 22).addBox(-1.0F, -4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.48F));
        orbit.addOrReplaceChild("stone2", CubeListBuilder.create().texOffs(23, 22).mirror().addBox(-3.0F, -4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.48F));
        orbit.addOrReplaceChild("stone3", CubeListBuilder.create().texOffs(23, 22).mirror().addBox(-3.0F, -4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, -4.75F, -9.0F, -0.3138F, -0.2071F, -0.3361F));
        orbit.addOrReplaceChild("stone4", CubeListBuilder.create().texOffs(23, 22).addBox(-1.0F, -4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -4.75F, -9.0F, -0.3138F, 0.2071F, 0.3361F));
        orbit.addOrReplaceChild("stone5", CubeListBuilder.create().texOffs(23, 22).addBox(-1.0F, -4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -4.75F, 9.0F, 0.3138F, -0.2071F, 0.3361F));
        orbit.addOrReplaceChild("stone6", CubeListBuilder.create().texOffs(23, 22).mirror().addBox(-3.0F, -4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, -4.75F, 9.0F, 0.3138F, 0.2071F, -0.3361F));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.orbit.yRot = ageInTicks * 0.25F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        this.all.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

}