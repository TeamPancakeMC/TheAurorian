package cn.teampancake.theaurorian.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public abstract class SimpleHumanoidModel<T extends Monster> extends AgeableListModel<T> {

    public ModelPart head;
    public ModelPart body;
    public ModelPart rightArm;
    public ModelPart leftArm;

    public SimpleHumanoidModel(ModelPart root) {
        this(root, RenderType::entityCutoutNoCull);
    }

    public SimpleHumanoidModel(ModelPart root, Function<ResourceLocation, RenderType> renderType) {
        super(renderType, true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
    }

    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.setupAttackAnimation(entity);
        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, entity.isAggressive(), this.attackTime, ageInTicks);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    protected void setupAttackAnimation(T livingEntity) {
        if (!(this.attackTime <= 0.0F)) {
            HumanoidArm mainArm = livingEntity.getMainArm();
            HumanoidArm humanoidArm = livingEntity.swingingArm == InteractionHand.MAIN_HAND ? mainArm : mainArm.getOpposite();
            ModelPart modelPart = humanoidArm == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
            float f = this.attackTime;
            this.body.yRot = Mth.sin(Mth.sqrt(f) * ((float)Math.PI * 2F)) * 0.2F;
            if (humanoidArm == HumanoidArm.LEFT) {
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
            modelPart.xRot -= f1 * 1.2F + f2;
            modelPart.yRot += this.body.yRot * 2.0F;
            modelPart.zRot += Mth.sin(this.attackTime * (float)Math.PI) * -0.4F;
        }
    }

}