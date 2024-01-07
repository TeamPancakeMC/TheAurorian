package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.client.animation.QuartzFishAnimation;
import cn.teampancake.theaurorian.common.entities.animal.MoonFish;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoonFishModel<T extends MoonFish> extends HierarchicalModel<T> {

    private final ModelPart all;

    public MoonFishModel(ModelPart root) {
        this.all = root.getChild("all");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        all.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 7).addBox(0.0F, -2.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -6.0F, 0.0F));
        all.addOrReplaceChild("main_body", CubeListBuilder.create()
                .texOffs(0, 11).addBox(-2.0F, -6.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 10).addBox(5.0F, -6.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 9).addBox(1.0F, -7.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -4.0F, -1.5F, 10.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 25).addBox(-2.0F, 0.0F, 0.0F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(19, 14).addBox(0.0F, -2.0F, -3.5F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));
        PartDefinition front_tail = all.addOrReplaceChild("front_tail", CubeListBuilder.create().texOffs(18, 17).addBox(-7.0F, -8.0F, 0.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        front_tail.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 16).mirror().addBox(-5.0F, -3.0F, 0.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, -6.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 48, 48);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(QuartzFishAnimation.QUARTZ_FISH_SWIM, limbSwing, limbSwingAmount, (1.5F), (1.0F));
    }

    @Override
    public ModelPart root() {
        return this.all;
    }

}