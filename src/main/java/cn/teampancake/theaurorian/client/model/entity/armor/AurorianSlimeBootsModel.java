package cn.teampancake.theaurorian.client.model.entity.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianSlimeBootsModel extends HumanoidModel<LivingEntity> {

    public AurorianSlimeBootsModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        partDefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F))
                .texOffs(0, 32).addBox(-2.5F, 4.0F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.1F))
                .texOffs(0, 40).addBox(-2.75F, 8.0F, 0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 40).addBox(-2.5F, 10.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.29F)).mirror(false)
                .texOffs(0, 32).mirror().addBox(-2.5F, 4.0F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(0, 40).mirror().addBox(0.75F, 8.0F, 0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 40).mirror().addBox(0.5F, 10.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 128, 128);
    }

}