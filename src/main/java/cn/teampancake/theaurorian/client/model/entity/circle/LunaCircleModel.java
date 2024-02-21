package cn.teampancake.theaurorian.client.model.entity.circle;

import cn.teampancake.theaurorian.client.animation.definitions.circle.LunaCircleAnimation;
import cn.teampancake.theaurorian.common.entities.technical.LunaCircleEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LunaCircleModel<T extends LunaCircleEntity> extends HierarchicalModel<T> {

    private final ModelPart all;

    public LunaCircleModel(ModelPart root) {
        this.all = root.getChild("all");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        all.addOrReplaceChild("ring_1", CubeListBuilder.create().texOffs(-39, 80).addBox(-19.5F, 0.0F, -19.5F, 39.0F, 0.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        all.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(-32, 0).addBox(-16.0F, -0.025F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition ring_2 = all.addOrReplaceChild("ring_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        ring_2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(-23, 32).addBox(-11.5F, -0.05F, -11.5F, 23.0F, 0.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        all.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(-21, 55).addBox(-10.5F, -0.075F, -10.5F, 21.0F, 0.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        all.addOrReplaceChild("ring_3", CubeListBuilder.create().texOffs(48, 0).addBox(-8.0F, -0.1F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition ring_4 = all.addOrReplaceChild("ring_4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        ring_4.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(54, 16).addBox(-5.0F, -0.125F, -5.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        all.addOrReplaceChild("moon", CubeListBuilder.create().texOffs(59, 27).addBox(-3.0F, -0.15F, -2.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, -0.5F));
        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.rotateAnimationState, LunaCircleAnimation.ROTATE, ageInTicks);
        this.animate(entity.spawnAnimationState, LunaCircleAnimation.SPAWN, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return this.all;
    }

}