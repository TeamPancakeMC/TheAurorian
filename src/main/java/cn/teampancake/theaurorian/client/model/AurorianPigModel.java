package cn.teampancake.theaurorian.client.model;

import cn.teampancake.theaurorian.common.entities.animal.AurorianPig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianPigModel<T extends AurorianPig> extends PigModel<T> {

    private final ModelPart tail;

    public AurorianPigModel(ModelPart root) {
        super(root);
        this.tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(74, 6).addBox(-4.0F, -3.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.75F, -8.0F));
        head.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(120, 11).mirror().addBox(-3.5F, -3.25F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(120, 11).addBox(2.5F, -3.25F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -6.0F, 0.8727F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(103, 9).addBox(-2.5F, -2.0F, -2.25F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -6.0F, 0.1309F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(103, 17).mirror().addBox(-2.0F, -1.75F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(4.0F, -3.0F, -1.0F, -0.3927F, 0.0F, 0.48F));
        head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(103, 17).addBox(-1.0F, -1.75F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.0F, -3.0F, -1.0F, -0.3927F, 0.0F, -0.48F));
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(1, 1).addBox(-5.0F, -6.5F, -8.0F, 10.0F, 10.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(0.0F, -10.0F, -8.0F, 0.0F, 7.0F, 15.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 16.5F, 0.0F));
        body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(42, 5).addBox(-4.0F, -4.5F, -1.75F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1745F, 0.0F, 0.0F));
        PartDefinition right_hind_leg = partDefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 29).addBox(-1.0F, -3.5571F, -4.0717F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.75F, 17.75F, 5.0F, -0.48F, 0.0F, 0.0F));
        PartDefinition right_hind_leg_1 = right_hind_leg.addOrReplaceChild("right_hind_leg_1", CubeListBuilder.create().texOffs(17, 31).addBox(-0.5F, -2.97F, -0.2186F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.75F, -1.0F, 0.9599F, 0.0F, 0.0F));
        right_hind_leg_1.addOrReplaceChild("right_hind_leg_2", CubeListBuilder.create().texOffs(28, 30).addBox(-1.0F, -3.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.5F, -0.48F, 0.0F, 0.0F));
        PartDefinition left_hind_leg = partDefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-2.0F, -3.5571F, -4.0717F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.75F, 17.75F, 5.0F, -0.48F, 0.0F, 0.0F));
        PartDefinition left_hind_leg_1 = left_hind_leg.addOrReplaceChild("left_hind_leg_1", CubeListBuilder.create().texOffs(17, 31).mirror().addBox(-1.5F, -2.97F, -0.2186F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.75F, -1.0F, 0.9599F, 0.0F, 0.0F));
        left_hind_leg_1.addOrReplaceChild("left_hind_leg_2", CubeListBuilder.create().texOffs(28, 30).mirror().addBox(-2.0F, -3.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.0F, 0.5F, -0.48F, 0.0F, 0.0F));
        PartDefinition right_front_leg = partDefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 29).addBox(-1.0F, -4.2554F, -2.6898F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.75F, 17.5F, -5.0F, 0.2618F, 0.0F, 0.0F));
        PartDefinition right_front_leg_1 = right_front_leg.addOrReplaceChild("right_front_leg_1", CubeListBuilder.create().texOffs(17, 31).addBox(-0.5F, -2.5316F, -4.7936F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 2.0F, -0.5672F, 0.0F, 0.0F));
        right_front_leg_1.addOrReplaceChild("right_front_leg_2", CubeListBuilder.create().texOffs(28, 30).addBox(-1.0F, -3.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -3.0F, 0.3054F, 0.0F, 0.0F));
        PartDefinition left_front_leg = partDefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-2.0F, -4.2554F, -2.6898F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.75F, 17.5F, -5.0F, 0.2618F, 0.0F, 0.0F));
        PartDefinition left_front_leg_1 = left_front_leg.addOrReplaceChild("left_front_leg_1", CubeListBuilder.create().texOffs(17, 31).mirror().addBox(-1.5F, -2.5316F, -4.7936F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.0F, 2.0F, -0.5672F, 0.0F, 0.0F));
        left_front_leg_1.addOrReplaceChild("left_front_leg_2", CubeListBuilder.create().texOffs(28, 30).mirror().addBox(-2.0F, -3.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.0F, -3.0F, 0.3054F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(43, 24).addBox(0.0F, -1.0F, -1.0F, 0.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.5F, 7.0F, -0.9163F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 128, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}