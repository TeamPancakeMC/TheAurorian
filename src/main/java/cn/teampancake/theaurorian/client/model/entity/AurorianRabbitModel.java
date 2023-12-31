package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.animal.AurorianRabbit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianRabbitModel<T extends AurorianRabbit> extends EntityModel<T> {

    private final ModelPart all;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightRearFoot;
    private final ModelPart leftRearFoot;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private float jumpRotation;

    public AurorianRabbitModel(ModelPart root) {
        this.all = root.getChild("all");
        this.body = this.all.getChild("body");
        this.head = this.body.getChild("head");
        this.rightFrontLeg = this.body.getChild("arm_right");
        this.leftFrontLeg = this.body.getChild("arm_left");
        this.rightRearFoot = this.body.getChild("leg_right");
        this.leftRearFoot = this.body.getChild("leg_left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 23.5F, 0.0F));
        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -5.5F, -4.0F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(11, 14).addBox(-1.0F, -4.5F, 3.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(29, 3).addBox(-2.5F, -4.0F, -3.75F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(45, 5).addBox(-0.5F, -1.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.75F, -4.0F));
        head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(50, 7).mirror().addBox(-1.0F, -3.0F, -0.25F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, -4.0F, 0.25F, -0.3491F, 0.0F, 0.2618F));
        head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(50, 7).addBox(-1.0F, -3.0F, -0.25F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -4.0F, 0.25F, -0.3491F, 0.0F, -0.2618F));
        body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -0.5F, -3.0F));
        body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, -0.5F, -3.0F));
        body.addOrReplaceChild("leg_right", CubeListBuilder.create()
                .texOffs(0, 19).addBox(-0.5F, -1.75F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-0.25F, 1.0F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -1.5F, 3.0F));
        body.addOrReplaceChild("leg_left", CubeListBuilder.create()
                .texOffs(0, 19).mirror().addBox(-1.5F, -1.75F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 14).mirror().addBox(-1.75F, 1.0F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, -1.5F, 3.0F));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ageInTicks - (float) entity.tickCount;
        this.head.xRot = headPitch * ((float)Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180.0F);
        this.jumpRotation = Mth.sin(entity.getJumpCompletion(f) * (float)Math.PI);
        this.rightFrontLeg.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180.0F);
        this.leftFrontLeg.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180.0F);
        this.rightRearFoot.xRot = this.jumpRotation * 50.0F * ((float)Math.PI / 180.0F);
        this.leftRearFoot.xRot = this.jumpRotation * 50.0F * ((float)Math.PI / 180.0F);
    }

    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
        this.jumpRotation = Mth.sin(entity.getJumpCompletion(partialTick) * (float)Math.PI);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.all.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}