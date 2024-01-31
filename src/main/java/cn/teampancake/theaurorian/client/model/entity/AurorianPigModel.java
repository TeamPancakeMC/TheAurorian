package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.client.animation.AurorianPigAnimation;
import cn.teampancake.theaurorian.common.entities.animal.AurorianPig;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianPigModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart all;
    private final ModelPart head;

    public AurorianPigModel(ModelPart root) {
        this.all = root.getChild("all");
        this.head = this.all.getChild("body").getChild("bone13").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 16.5F, 0.0F));
        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bone13 = body.addOrReplaceChild("bone13", CubeListBuilder.create()
                .texOffs(1, 1).addBox(-5.0F, -8.75F, -8.0F, 10.0F, 10.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(0.0F, -12.25F, -8.0F, 0.0F, 7.0F, 15.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 2.25F, 0.0F));
        bone13.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(42, 5).addBox(-4.0F, -4.5F, -1.75F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.25F, 2.0F, -0.1745F, 0.0F, 0.0F));
        PartDefinition head = bone13.addOrReplaceChild("head", CubeListBuilder.create().texOffs(74, 6).addBox(-4.0F, -3.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -8.0F));
        head.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                .texOffs(120, 11).mirror().addBox(-3.5F, -3.25F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(120, 11).addBox(2.5F, -3.25F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -6.0F, 0.8727F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(103, 9).addBox(-2.5F, -2.0F, -2.25F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -6.0F, 0.1309F, 0.0F, 0.0F));
        head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(103, 17).mirror().addBox(-2.0F, -1.75F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(4.0F, -3.0F, -1.0F, -0.3927F, 0.0F, 0.48F));
        head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(103, 17).addBox(-1.0F, -1.75F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.0F, -3.0F, -1.0F, -0.3927F, 0.0F, -0.48F));
        bone13.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(43, 24).addBox(0.0F, -1.0F, -1.0F, 0.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.25F, 7.0F, -0.9163F, 0.0F, 0.0F));
        PartDefinition bone7 = body.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offset(-4.75F, 0.0F, -5.5F));
        PartDefinition front_leg_right = bone7.addOrReplaceChild("front_leg_right", CubeListBuilder.create().texOffs(0, 29).addBox(-1.0F, -4.2554F, -2.6898F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.5F, 0.2618F, 0.0F, 0.0F));
        PartDefinition bone8 = front_leg_right.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, -0.15F));
        PartDefinition front_leg_right_1 = bone8.addOrReplaceChild("front_leg_right_1", CubeListBuilder.create().texOffs(17, 31).addBox(-0.5F, -2.5316F, -4.7936F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.5F, 2.15F, -0.5672F, 0.0F, 0.0F));
        PartDefinition bone9 = front_leg_right_1.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -3.0F));
        bone9.addOrReplaceChild("front_leg_right_2", CubeListBuilder.create().texOffs(28, 30).addBox(-1.0F, -3.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.3054F, 0.0F, 0.0F));
        PartDefinition bone = body.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(4.75F, 0.0F, -5.5F));
        PartDefinition front_leg_left = bone.addOrReplaceChild("front_leg_left", CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-2.0F, -4.2554F, -2.6898F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 0.5F, 0.2618F, 0.0F, 0.0F));
        PartDefinition bone2 = front_leg_left.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, -0.15F));
        PartDefinition front_leg_left_1 = bone2.addOrReplaceChild("front_leg_left_1", CubeListBuilder.create().texOffs(17, 31).mirror().addBox(-1.5F, -2.5316F, -4.7936F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.5F, 2.15F, -0.5672F, 0.0F, 0.0F));
        PartDefinition bone3 = front_leg_left_1.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -3.0F));
        bone3.addOrReplaceChild("front_leg_left_2", CubeListBuilder.create().texOffs(28, 30).mirror().addBox(-2.0F, -3.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.3054F, 0.0F, 0.0F));
        PartDefinition bone10 = body.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offset(4.75F, 0.25F, 4.0F));
        PartDefinition back_leg_left = bone10.addOrReplaceChild("back_leg_left", CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-2.0F, -3.5571F, -4.0717F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, -0.48F, 0.0F, 0.0F));
        PartDefinition bone11 = back_leg_left.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -1.5F));
        PartDefinition back_leg_left_1 = bone11.addOrReplaceChild("back_leg_left_1", CubeListBuilder.create().texOffs(17, 31).mirror().addBox(-1.5F, -2.97F, -0.2186F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.75F, 0.5F, 0.9599F, 0.0F, 0.0F));
        PartDefinition bone12 = back_leg_left_1.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, 1.5F));
        bone12.addOrReplaceChild("back_leg_left_2", CubeListBuilder.create().texOffs(28, 30).mirror().addBox(-2.0F, -3.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.5F, -1.0F, -0.48F, 0.0F, 0.0F));
        PartDefinition bone4 = body.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(-4.75F, 0.25F, 4.0F));
        PartDefinition back_leg_right = bone4.addOrReplaceChild("back_leg_right", CubeListBuilder.create().texOffs(0, 29).addBox(-1.0F, -3.5571F, -4.0717F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, -0.48F, 0.0F, 0.0F));
        PartDefinition bone5 = back_leg_right.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -1.5F));
        PartDefinition back_leg_right_1 = bone5.addOrReplaceChild("back_leg_right_1", CubeListBuilder.create().texOffs(17, 31).addBox(-0.5F, -2.97F, -0.2186F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.75F, 0.5F, 0.9599F, 0.0F, 0.0F));
        PartDefinition bone6 = back_leg_right_1.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, 2.25F, 1.5F));
        bone6.addOrReplaceChild("back_leg_right_2", CubeListBuilder.create().texOffs(28, 30).addBox(-1.0F, -3.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.75F, -1.0F, -0.48F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 128, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float)Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180.0F);
        if (!entity.isInWaterOrBubble()) {
            this.animateWalk(AurorianPigAnimation.WALK, limbSwing, limbSwingAmount, (2.0F), (2.5F));
        }
    }

    @Override
    public ModelPart root() {
        return this.all;
    }

}