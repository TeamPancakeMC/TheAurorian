package cn.teampancake.theaurorian.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class TAHumanoidModel<T extends LivingEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {

    public ModelPart head;
    public ModelPart hat;
    public ModelPart body;
    public ModelPart rightArm;
    public ModelPart leftArm;
    public ModelPart rightLeg;
    public ModelPart leftLeg;
    public ModelPart rightFoot;
    public ModelPart leftFoot;
    public TAHumanoidModel.ArmPose leftArmPose = TAHumanoidModel.ArmPose.EMPTY;
    public TAHumanoidModel.ArmPose rightArmPose = TAHumanoidModel.ArmPose.EMPTY;
    public boolean crouching;
    public float swimAmount;

    public TAHumanoidModel(ModelPart root) {
        this(root, RenderType::entityCutoutNoCull);
    }

    public TAHumanoidModel(ModelPart root, Function<ResourceLocation, RenderType> renderType) {
        super(renderType, true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);
        this.head = root.getChild("head");
        this.hat = root.getChild("hat");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.rightFoot = root.getChild("right_foot");
        this.leftFoot = root.getChild("left_foot");
    }

    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.hat, this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg, this.rightFoot, this.leftFoot);
    }

    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        this.swimAmount = entity.getSwimAmount(partialTick);
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = entity.getFallFlyingTicks() > 4;
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        if (flag) {
            this.head.xRot = (-(float)Math.PI / 4F);
        } else if (this.swimAmount > 0.0F) {
            if (entity.isVisuallySwimming()) {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, (-(float)Math.PI / 4F));
            } else {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, headPitch * ((float)Math.PI / 180F));
            }
        } else {
            this.head.xRot = headPitch * ((float)Math.PI / 180F);
        }

        this.body.yRot = 0.0F;
        this.rightArm.z = 0.0F;
        this.rightArm.x = -5.0F;
        this.leftArm.z = 0.0F;
        this.leftArm.x = 5.0F;
        float f = 1.0F;
        if (flag) {
            f = (float)entity.getDeltaMovement().lengthSqr();
            f /= 0.2F;
            f *= f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
        this.rightFoot.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
        this.leftFoot.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
        this.rightLeg.yRot = 0.005F;
        this.leftLeg.yRot = -0.005F;
        this.rightFoot.yRot = 0.005F;
        this.leftFoot.yRot = -0.005F;
        this.rightLeg.zRot = 0.005F;
        this.leftLeg.zRot = -0.005F;
        this.rightFoot.zRot = 0.005F;
        this.leftFoot.zRot = -0.005F;
        if (this.riding) {
            this.rightArm.xRot += (-(float)Math.PI / 5F);
            this.leftArm.xRot += (-(float)Math.PI / 5F);
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float)Math.PI / 10F);
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float)Math.PI / 10F);
            this.leftLeg.zRot = -0.07853982F;
            this.rightFoot.xRot = -1.4137167F;
            this.rightFoot.yRot = ((float)Math.PI / 10F);
            this.rightFoot.zRot = 0.07853982F;
            this.leftFoot.xRot = -1.4137167F;
            this.leftFoot.yRot = (-(float)Math.PI / 10F);
            this.leftFoot.zRot = -0.07853982F;
        }

        this.rightArm.yRot = 0.0F;
        this.leftArm.yRot = 0.0F;
        boolean flag2 = entity.getMainArm() == HumanoidArm.RIGHT;
        if (entity.isUsingItem()) {
            boolean flag3 = entity.getUsedItemHand() == InteractionHand.MAIN_HAND;
            if (flag3 == flag2) {
                this.poseRightArm(entity);
            } else {
                this.poseLeftArm(entity);
            }
        } else {
            boolean flag4 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
            if (flag2 != flag4) {
                this.poseLeftArm(entity);
                this.poseRightArm(entity);
            } else {
                this.poseRightArm(entity);
                this.poseLeftArm(entity);
            }
        }

        this.setupAttackAnimation(entity, ageInTicks);
        if (this.crouching) {
            this.body.xRot = 0.5F;
            this.rightArm.xRot += 0.4F;
            this.leftArm.xRot += 0.4F;
            this.rightLeg.z = 4.0F;
            this.leftLeg.z = 4.0F;
            this.rightFoot.z = 4.0F;
            this.leftFoot.z = 4.0F;
            this.rightLeg.y = 12.2F;
            this.leftLeg.y = 12.2F;
//            this.rightFoot.y = 12.2F;
//            this.leftFoot.y = 12.2F;
            this.head.y = 4.2F;
            this.body.y = 3.2F;
            this.leftArm.y = 5.2F;
            this.rightArm.y = 5.2F;
        } else {
            this.body.xRot = 0.0F;
            this.rightLeg.z = 0.0F;
            this.leftLeg.z = 0.0F;
            this.rightFoot.z = 0.0F;
            this.leftFoot.z = 0.0F;
            this.rightLeg.y = 12.0F;
            this.leftLeg.y = 12.0F;
//            this.rightFoot.y = 12.0F;
//            this.leftFoot.y = 12.0F;
            this.head.y = 0.0F;
            this.body.y = 0.0F;
            this.leftArm.y = 2.0F;
            this.rightArm.y = 2.0F;
        }

        if (this.rightArmPose != TAHumanoidModel.ArmPose.SPYGLASS) {
            AnimationUtils.bobModelPart(this.rightArm, ageInTicks, 1.0F);
        }

        if (this.leftArmPose != TAHumanoidModel.ArmPose.SPYGLASS) {
            AnimationUtils.bobModelPart(this.leftArm, ageInTicks, -1.0F);
        }

        if (this.swimAmount > 0.0F) {
            float f5 = limbSwing % 26.0F;
            HumanoidArm attackArm = this.getAttackArm(entity);
            float f1 = attackArm == HumanoidArm.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
            float f2 = attackArm == HumanoidArm.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
            if (!entity.isUsingItem()) {
                if (f5 < 14.0F) {
                    this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, 0.0F);
                    this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, 0.0F);
                    this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, (float)Math.PI);
                    this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float)Math.PI);
                    this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, (float)Math.PI + 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F));
                    this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, (float)Math.PI - 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F));
                } else if (f5 >= 14.0F && f5 < 22.0F) {
                    float f6 = (f5 - 14.0F) / 8.0F;
                    this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, ((float)Math.PI / 2F) * f6);
                    this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, ((float)Math.PI / 2F) * f6);
                    this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, (float)Math.PI);
                    this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float)Math.PI);
                    this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, 5.012389F - 1.8707964F * f6);
                    this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, 1.2707963F + 1.8707964F * f6);
                } else if (f5 >= 22.0F && f5 < 26.0F) {
                    float f3 = (f5 - 22.0F) / 4.0F;
                    this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f3);
                    this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f3);
                    this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, (float)Math.PI);
                    this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float)Math.PI);
                    this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, (float)Math.PI);
                    this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, (float)Math.PI);
                }
            }

            this.leftLeg.xRot = Mth.lerp(this.swimAmount, this.leftLeg.xRot, 0.3F * Mth.cos(limbSwing * 0.33333334F + (float)Math.PI));
            this.rightLeg.xRot = Mth.lerp(this.swimAmount, this.rightLeg.xRot, 0.3F * Mth.cos(limbSwing * 0.33333334F));
            this.leftFoot.xRot = Mth.lerp(this.swimAmount, this.leftFoot.xRot, 0.3F * Mth.cos(limbSwing * 0.33333334F + (float)Math.PI));
            this.rightFoot.xRot = Mth.lerp(this.swimAmount, this.rightFoot.xRot, 0.3F * Mth.cos(limbSwing * 0.33333334F));
        }

        this.hat.copyFrom(this.head);
    }

    private void poseRightArm(T livingEntity) {
        switch (this.rightArmPose) {
            case EMPTY -> this.rightArm.yRot = 0.0F;
            case BLOCK -> {
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - 0.9424779F;
                this.rightArm.yRot = (-(float) Math.PI / 6F);
            }
            case ITEM -> {
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - ((float) Math.PI / 10F);
                this.rightArm.yRot = 0.0F;
            }
            case THROW_SPEAR -> {
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float) Math.PI;
                this.rightArm.yRot = 0.0F;
            }
            case BOW_AND_ARROW -> {
                this.rightArm.yRot = -0.1F + this.head.yRot;
                this.leftArm.yRot = 0.1F + this.head.yRot + 0.4F;
                this.rightArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
                this.leftArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
            }
            case CROSSBOW_CHARGE -> AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, livingEntity, true);
            case CROSSBOW_HOLD -> AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
            case BRUSH -> {
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - ((float) Math.PI / 5F);
                this.rightArm.yRot = 0.0F;
            }
            case SPYGLASS -> {
                this.rightArm.xRot = Mth.clamp(this.head.xRot - 1.9198622F - (livingEntity.isCrouching() ? 0.2617994F : 0.0F), -2.4F, 3.3F);
                this.rightArm.yRot = this.head.yRot - 0.2617994F;
            }
            case TOOT_HORN -> {
                this.rightArm.xRot = Mth.clamp(this.head.xRot, -1.2F, 1.2F) - 1.4835298F;
                this.rightArm.yRot = this.head.yRot - ((float) Math.PI / 6F);
            }
        }
    }

    private void poseLeftArm(T livingEntity) {
        switch (this.leftArmPose) {
            case EMPTY -> this.leftArm.yRot = 0.0F;
            case BLOCK -> {
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - 0.9424779F;
                this.leftArm.yRot = ((float) Math.PI / 6F);
            }
            case ITEM -> {
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - ((float) Math.PI / 10F);
                this.leftArm.yRot = 0.0F;
            }
            case THROW_SPEAR -> {
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float) Math.PI;
                this.leftArm.yRot = 0.0F;
            }
            case BOW_AND_ARROW -> {
                this.rightArm.yRot = -0.1F + this.head.yRot - 0.4F;
                this.leftArm.yRot = 0.1F + this.head.yRot;
                this.rightArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
                this.leftArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
            }
            case CROSSBOW_CHARGE -> AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, livingEntity, false);
            case CROSSBOW_HOLD -> AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, false);
            case BRUSH -> {
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - ((float) Math.PI / 5F);
                this.leftArm.yRot = 0.0F;
            }
            case SPYGLASS -> {
                this.leftArm.xRot = Mth.clamp(this.head.xRot - 1.9198622F - (livingEntity.isCrouching() ? 0.2617994F : 0.0F), -2.4F, 3.3F);
                this.leftArm.yRot = this.head.yRot + 0.2617994F;
            }
            case TOOT_HORN -> {
                this.leftArm.xRot = Mth.clamp(this.head.xRot, -1.2F, 1.2F) - 1.4835298F;
                this.leftArm.yRot = this.head.yRot + ((float) Math.PI / 6F);
            }
        }
    }

    protected void setupAttackAnimation(T livingEntity, float ageInTicks) {
        if (!(this.attackTime <= 0.0F)) {
            HumanoidArm humanoidarm = this.getAttackArm(livingEntity);
            ModelPart modelpart = this.getArm(humanoidarm);
            float f = this.attackTime;
            this.body.yRot = Mth.sin(Mth.sqrt(f) * ((float)Math.PI * 2F)) * 0.2F;
            if (humanoidarm == HumanoidArm.LEFT) {
                this.body.yRot *= -1.0F;
            }

            this.rightArm.z = Mth.sin(this.body.yRot) * 5.0F;
            this.rightArm.x = -Mth.cos(this.body.yRot) * 5.0F;
            this.leftArm.z = -Mth.sin(this.body.yRot) * 5.0F;
            this.leftArm.x = Mth.cos(this.body.yRot) * 5.0F;
            this.rightArm.yRot += this.body.yRot;
            this.leftArm.yRot += this.body.yRot;
            this.leftArm.xRot += this.body.yRot;
            f = 1.0F - this.attackTime;
            f *= f;
            f *= f;
            f = 1.0F - f;
            float f1 = Mth.sin(f * (float)Math.PI);
            float f2 = Mth.sin(this.attackTime * (float)Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
            modelpart.xRot -= f1 * 1.2F + f2;
            modelpart.yRot += this.body.yRot * 2.0F;
            modelpart.zRot += Mth.sin(this.attackTime * (float)Math.PI) * -0.4F;
        }
    }

    protected float rotlerpRad(float angle, float maxAngle, float mul) {
        float f = (mul - maxAngle) % ((float)Math.PI * 2F);
        if (f < -(float)Math.PI) {
            f += ((float)Math.PI * 2F);
        }

        if (f >= (float)Math.PI) {
            f -= ((float)Math.PI * 2F);
        }

        return maxAngle + angle * f;
    }

    private float quadraticArmUpdate(float limbSwing) {
        return -65.0F * limbSwing + limbSwing * limbSwing;
    }

    public void copyPropertiesTo(TAHumanoidModel<T> model) {
        model.attackTime = this.attackTime;
        model.riding = this.riding;
        model.young = this.young;
        model.leftArmPose = this.leftArmPose;
        model.rightArmPose = this.rightArmPose;
        model.crouching = this.crouching;
        model.head.copyFrom(this.head);
        model.hat.copyFrom(this.hat);
        model.body.copyFrom(this.body);
        model.rightArm.copyFrom(this.rightArm);
        model.leftArm.copyFrom(this.leftArm);
        model.rightLeg.copyFrom(this.rightLeg);
        model.leftLeg.copyFrom(this.leftLeg);
        model.rightFoot.copyFrom(this.rightFoot);
        model.leftFoot.copyFrom(this.leftFoot);
    }

    public void setAllVisible(boolean visible) {
        this.head.visible = visible;
        this.hat.visible = visible;
        this.body.visible = visible;
        this.rightArm.visible = visible;
        this.leftArm.visible = visible;
        this.rightLeg.visible = visible;
        this.leftLeg.visible = visible;
        this.rightFoot.visible = visible;
        this.leftFoot.visible = visible;
    }

    public void setPartVisibility(EquipmentSlot slot) {
        this.setAllVisible(false);
        switch (slot) {
            case HEAD -> {
                this.head.visible = true;
                this.hat.visible = true;
            }
            case CHEST -> {
                this.rightArm.visible = true;
                this.leftArm.visible = true;
            }
            case LEGS -> {
                this.rightLeg.visible = true;
                this.leftLeg.visible = true;
            }
            case FEET -> {
                this.rightFoot.visible = true;
                this.leftFoot.visible = true;
            }
        }
    }

    public void translateToHand(HumanoidArm side, PoseStack poseStack) {
        this.getArm(side).translateAndRotate(poseStack);
    }

    protected ModelPart getArm(HumanoidArm side) {
        return side == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }

    public ModelPart getHead() {
        return this.head;
    }

    private HumanoidArm getAttackArm(T entity) {
        HumanoidArm humanoidarm = entity.getMainArm();
        return entity.swingingArm == InteractionHand.MAIN_HAND ? humanoidarm : humanoidarm.getOpposite();
    }

    @OnlyIn(Dist.CLIENT)
    public enum ArmPose {

        EMPTY(false),
        ITEM(false),
        BLOCK(false),
        BOW_AND_ARROW(true),
        THROW_SPEAR(false),
        CROSSBOW_CHARGE(true),
        CROSSBOW_HOLD(true),
        SPYGLASS(false),
        TOOT_HORN(false),
        BRUSH(false);

        private final boolean twoHanded;

        ArmPose(boolean twoHanded) {
            this.twoHanded = twoHanded;
        }

        public boolean isTwoHanded() {
            return this.twoHanded;
        }

    }

}
