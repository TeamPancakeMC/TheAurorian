package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.animal.AurorianPig;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class AurorianPigBabyModel<T extends AurorianPig> extends AbstractAurorianAnimalBabyModel<T> {

    public AurorianPigBabyModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -3.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.5F, 0.0F));
        body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(9, 20).addBox(0.0F, -2.0F, 0.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -2.0F, 3.5F, 0.3054F, 0.0F, 0.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 12).addBox(-2.5F, -3.5F, -4.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(19, 17).addBox(-2.0F, -1.5F, -5.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -3.5F));
        body.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(0, 21).addBox(-0.5F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.75F, -0.5F, -2.0F));
        body.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(0, 21).addBox(-0.5F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.75F, -0.5F, 2.0F));
        body.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(0, 21).mirror().addBox(-1.5F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.75F, -0.5F, 2.0F));
        body.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(0, 21).mirror().addBox(-1.5F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.75F, -0.5F, -2.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

}