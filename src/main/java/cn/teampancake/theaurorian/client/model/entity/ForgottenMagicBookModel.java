package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.monster.ForgottenMagicBook;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ForgottenMagicBookModel<T extends ForgottenMagicBook> extends HierarchicalModel<T> {

    private final ModelPart body;

    public ForgottenMagicBookModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition book_right = body.addOrReplaceChild("book_right", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        book_right.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(0, 14).addBox(-7.975F, -2.25F, -5.5F, 8.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.0F, -2.0F, -6.0F, 9.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));
        PartDefinition page_right = book_right.addOrReplaceChild("page_right", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.1309F));
        page_right.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 14).addBox(-7.6277F, -0.2804F, -5.5F, 8.0F, 0.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
        PartDefinition book_left = body.addOrReplaceChild("book_left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        book_left.addOrReplaceChild("cube_r3", CubeListBuilder.create()
                .texOffs(0, 14).mirror().addBox(-0.025F, -2.25F, -5.5F, 8.0F, 1.0F, 11.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(0.0F, -2.0F, -6.0F, 9.0F, 2.0F, 12.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));
        PartDefinition page_left = book_left.addOrReplaceChild("page_left", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.1745F));
        page_left.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-0.3723F, -0.2804F, -5.5F, 8.0F, 0.0F, 11.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        PartDefinition hand = body.addOrReplaceChild("hand", CubeListBuilder.create()
                .texOffs(0, 26).addBox(-1.0F, -1.75F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-2.5F, -6.75F, -1.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));
        PartDefinition hand_2 = hand.addOrReplaceChild("hand_2", CubeListBuilder.create().texOffs(0, 39).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.25F, 0.0F, -0.2618F, 0.0F, 0.0F));
        PartDefinition hand_3 = hand_2.addOrReplaceChild("hand_3", CubeListBuilder.create().texOffs(0, 39).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -13.0F, 2.0F, 1.0472F, 0.0F, 0.0F));
        PartDefinition hand_4 = hand_3.addOrReplaceChild("hand_4", CubeListBuilder.create().texOffs(17, 31).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -14.0F, 0.5F, 1.2217F, 0.0F, 0.0F));
        PartDefinition finger_a_1 = hand_4.addOrReplaceChild("finger_a_1", CubeListBuilder.create().texOffs(9, 45).addBox(-0.5F, -8.5F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -5.0F, -0.5F, 0.3491F, 0.0F, 0.0F));
        finger_a_1.addOrReplaceChild("finger_b_1", CubeListBuilder.create().texOffs(14, 45).addBox(-0.5F, -9.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.25F, 0.6981F, 0.0F, 0.0F));
        PartDefinition finger_a_2 = hand_4.addOrReplaceChild("finger_a_2", CubeListBuilder.create().texOffs(9, 45).addBox(-0.5F, -8.5F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.75F, -5.0F, -0.5F, 0.3927F, 0.0F, 0.3054F));
        finger_a_2.addOrReplaceChild("finger_b_2", CubeListBuilder.create().texOffs(14, 45).addBox(-0.5F, -9.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -7.75F, 0.5F, 1.0036F, 0.0F, 0.0F));
        PartDefinition finger_a_3 = hand_4.addOrReplaceChild("finger_a_3", CubeListBuilder.create().texOffs(9, 45).addBox(-0.5F, -8.5F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.75F, -5.0F, -0.5F, 0.48F, 0.0F, -0.2618F));
        finger_a_3.addOrReplaceChild("finger_b_3", CubeListBuilder.create().texOffs(14, 45).addBox(-0.5F, -9.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -7.75F, 0.75F, 1.0908F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
    }

    @Override
    public ModelPart root() {
        return this.body;
    }

}