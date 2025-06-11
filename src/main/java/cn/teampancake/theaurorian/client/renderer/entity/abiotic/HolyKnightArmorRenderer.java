package cn.teampancake.theaurorian.client.renderer.entity.abiotic;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.items.armor.HolyKnightArmor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.RenderUtil;

@OnlyIn(Dist.CLIENT)
public class HolyKnightArmorRenderer extends GeoArmorRenderer<HolyKnightArmor> {

    private static final GeoModel<HolyKnightArmor> MODEL = new DefaultedItemGeoModel<>(TheAurorian.prefix("holy_knight_armor"));

    @Nullable
    private GeoBone armorLegs = null;

    public HolyKnightArmorRenderer() {
        super(MODEL);
    }

    @Override
    protected void grabRelevantBones(BakedGeoModel bakedModel) {
        super.grabRelevantBones(bakedModel);
        this.armorLegs = MODEL.getBone("armorLegs").orElse(null);
    }

    @Override
    protected void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
        this.setAllVisible(false);
        switch (currentSlot) {
            case HEAD -> this.setBoneVisible(this.head, true);
            case CHEST -> {
                this.setBoneVisible(this.body, true);
                this.setBoneVisible(this.rightArm, true);
                this.setBoneVisible(this.leftArm, true);
            }
            case LEGS -> this.setBoneVisible(this.armorLegs, true);
            case FEET -> {
                this.setBoneVisible(this.rightBoot, true);
                this.setBoneVisible(this.leftBoot, true);
            }
            default -> {}
        }
    }

    @Override
    protected void applyBaseTransformations(HumanoidModel<?> baseModel) {
        if (this.head != null) {
            ModelPart headPart = baseModel.head;
            RenderUtil.matchModelPartRot(headPart, this.head);
            this.head.updatePosition(headPart.x, -headPart.y, headPart.z);
        }

        if (this.body != null) {
            ModelPart bodyPart = baseModel.body;
            RenderUtil.matchModelPartRot(bodyPart, this.body);
            this.body.updatePosition(bodyPart.x, -bodyPart.y, bodyPart.z);
        }

        if (this.rightArm != null) {
            ModelPart rightArmPart = baseModel.rightArm;
            RenderUtil.matchModelPartRot(rightArmPart, this.rightArm);
            this.rightArm.updatePosition(rightArmPart.x + 5, 2 - rightArmPart.y, rightArmPart.z);
        }

        if (this.leftArm != null) {
            ModelPart leftArmPart = baseModel.leftArm;
            RenderUtil.matchModelPartRot(leftArmPart, this.leftArm);
            this.leftArm.updatePosition(leftArmPart.x - 5f, 2f - leftArmPart.y, leftArmPart.z);
        }

        if (this.armorLegs != null) {
            ModelPart bodyPart = baseModel.body;
            RenderUtil.matchModelPartRot(bodyPart, this.armorLegs);
            this.armorLegs.updatePosition(bodyPart.x, -bodyPart.y, bodyPart.z);
        }

        if (this.rightBoot != null) {
            ModelPart rightLegPart = baseModel.rightLeg;
            RenderUtil.matchModelPartRot(rightLegPart, this.rightBoot);
            this.rightBoot.updatePosition(rightLegPart.x + 2, 12 - rightLegPart.y, rightLegPart.z);
        }

        if (this.leftBoot != null) {
            ModelPart leftLegPart = baseModel.leftLeg;
            RenderUtil.matchModelPartRot(leftLegPart, this.leftBoot);
            this.leftBoot.updatePosition(leftLegPart.x - 2, 12 - leftLegPart.y, leftLegPart.z);
        }
    }

    @Override
    public void setAllVisible(boolean visible) {
        super.setAllVisible(visible);
        this.setBoneVisible(this.armorLegs, visible);
    }

}