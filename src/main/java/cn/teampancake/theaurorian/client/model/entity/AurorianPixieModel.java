package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.client.animation.AurorianPixieAnimation;
import cn.teampancake.theaurorian.common.entities.animal.AurorianPixie;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianPixieModel<T extends AurorianPixie> extends HierarchicalModel<T> {

    private final ModelPart all;

    public AurorianPixieModel(ModelPart root) {
        this.all = root.getChild("all");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));
        all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition bone2 = all.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(3.1F, -2.2F, 1.0F));
        bone2.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(-8, 24).addBox(0.0F, 0.0F, -4.0F, 11.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 1.0F, -0.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition bone3 = all.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(-13.9F, -2.2F, 1.0F));
        bone3.addOrReplaceChild("wing_left2", CubeListBuilder.create().texOffs(-8, 16).addBox(-11.0F, 0.0F, -4.0F, 11.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.9F, 1.0F, -0.5F, 0.0F, 0.0F, 0.0F));
        all.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, -0.4F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.4363F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.idleAnimationState, AurorianPixieAnimation.IDLE, ageInTicks);
        this.animate(entity.tradeAnimationState, AurorianPixieAnimation.TRADE, ageInTicks);
        this.animate(entity.flyAnimationState, AurorianPixieAnimation.FLY, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return this.all;
    }

}