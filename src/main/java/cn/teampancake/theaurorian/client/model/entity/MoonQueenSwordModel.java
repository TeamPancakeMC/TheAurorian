package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.projectile.MoonQueenSword;
import com.mojang.math.Constants;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoonQueenSwordModel extends HierarchicalModel<MoonQueenSword> {

    private final ModelPart sword;

    public MoonQueenSwordModel(ModelPart root) {
        this.sword = root.getChild("sword");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition sword = partDefinition.addOrReplaceChild("sword", CubeListBuilder.create()
                .texOffs(2, 16).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(2, 5).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 1.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(29, -6).addBox(0.0F, -29.0F, -3.5F, 0.0F, 25.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 19.875F, 14.0F, 1.5708F, 0.0F, 0.0F));
        sword.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(11, 15)
                        .addBox(-1.0F, -2.5F, -0.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        sword.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(22, 16)
                        .addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.003F)),
                PartPose.offsetAndRotation(0.0F, -4.0F, -2.0F, -0.3491F, 0.0F, 0.0F));
        sword.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(22, 16)
                        .addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.002F)),
                PartPose.offsetAndRotation(0.0F, -4.0F, 2.0F, 0.3491F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(MoonQueenSword entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isRotate()) {
            this.sword.zRot = ageInTicks * 32.0F * Constants.DEG_TO_RAD;
        }
    }

    @Override
    public ModelPart root() {
        return this.sword;
    }

}