package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.animal.AurorianCow;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianCowBabyModel<T extends Entity> extends AbstractAurorianAnimalBabyModel<T> {

    public AurorianCowBabyModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -8.5F, -5.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(34, 23).addBox(0.0F, -7.5F, 7.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 22.5F, 0.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 20).addBox(-3.0F, -2.0F, -5.75F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(25, 24).addBox(-4.5F, -4.0F, -1.75F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(25, 24).mirror().addBox(2.5F, -4.0F, -1.75F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -6.0F, -4.5F));
        body.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, 0.0F, -1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.75F, -0.5F, -3.0F));
        body.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.75F, -0.5F, 5.0F));
        body.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-2.0F, 0.0F, -2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.75F, -0.5F, 5.0F));
        body.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-2.0F, 0.0F, -1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.75F, -0.5F, -3.0F));
        return LayerDefinition.create(meshDefinition, 48, 48);
    }

}