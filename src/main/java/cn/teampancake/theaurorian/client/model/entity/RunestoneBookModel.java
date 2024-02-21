package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.client.animation.RunestoneBooksAnimation;
import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RunestoneBookModel<T extends RunestoneKeeper> extends HierarchicalModel<T> {

    public final ModelPart book;

    public RunestoneBookModel(ModelPart root) {
        this.book = root.getChild("book");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition book = partdefinition.addOrReplaceChild("book", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition book1 = book.addOrReplaceChild("book1", CubeListBuilder.create(), PartPose.offset(-16.9603F, -16.8467F, 3.6369F));
        PartDefinition book_1 = book1.addOrReplaceChild("book_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        book_1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -11.0F, -6.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.9603F, 5.8467F, 0.3631F, -0.1309F, 2.2253F, 0.0873F));
        PartDefinition rune1_1 = book_1.addOrReplaceChild("rune1_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        rune1_1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -11.0F, -6.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.9603F, 5.8467F, 0.3631F, -0.1309F, 2.2253F, 0.0873F));
        PartDefinition rune1_2 = book_1.addOrReplaceChild("rune1_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        rune1_2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(26, 16).addBox(-1.0F, -11.0F, -6.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.9603F, 5.8467F, 0.3631F, -0.1309F, 2.2253F, 0.0873F));
        PartDefinition rune1_3 = book_1.addOrReplaceChild("rune1_3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        rune1_3.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(52, 16).addBox(-1.0F, -11.0F, -6.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.9603F, 5.8467F, 0.3631F, -0.1309F, 2.2253F, 0.0873F));
        PartDefinition rune1_4 = book_1.addOrReplaceChild("rune1_4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        rune1_4.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(78, 16).addBox(-1.0F, -11.0F, -6.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.9603F, 5.8467F, 0.3631F, -0.1309F, 2.2253F, 0.0873F));
        PartDefinition rune1_5 = book_1.addOrReplaceChild("rune1_5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        rune1_5.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(104, 16).addBox(-1.0F, -11.0F, -6.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.9603F, 5.8467F, 0.3631F, -0.1309F, 2.2253F, 0.0873F));
        PartDefinition book_2 = book.addOrReplaceChild("book_2", CubeListBuilder.create(), PartPose.offset(13.5607F, -35.9534F, -9.9512F));
        book_2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -16.0F, -7.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5607F, 10.9534F, 2.9512F, -0.2618F, -0.9163F, 0.0F));
        PartDefinition rune2_1 = book_2.addOrReplaceChild("rune2_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        rune2_1.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 16).addBox(-6.0F, -16.0F, -7.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5607F, 10.9534F, 2.9512F, -0.2618F, -0.9163F, 0.0F));
        PartDefinition rune2_2 = book_2.addOrReplaceChild("rune2_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        rune2_2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(26, 16).addBox(-6.0F, -16.0F, -7.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5607F, 10.9534F, 2.9512F, -0.2618F, -0.9163F, 0.0F));
        PartDefinition rune2_3 = book_2.addOrReplaceChild("rune2_3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        rune2_3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(52, 16).addBox(-6.0F, -16.0F, -7.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5607F, 10.9534F, 2.9512F, -0.2618F, -0.9163F, 0.0F));
        PartDefinition rune2_4 = book_2.addOrReplaceChild("rune2_4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        rune2_4.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(78, 16).addBox(-6.0F, -16.0F, -7.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5607F, 10.9534F, 2.9512F, -0.2618F, -0.9163F, 0.0F));
        PartDefinition rune2_5 = book_2.addOrReplaceChild("rune2_5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        rune2_5.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(104, 16).addBox(-6.0F, -16.0F, -7.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5607F, 10.9534F, 2.9512F, -0.2618F, -0.9163F, 0.0F));
        return LayerDefinition.create(meshdefinition, 144, 48);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (entity.isDeadOrDying()) {
            long i = entity.deathAnimationState.getAccumulatedTime();
            KeyframeAnimations.animate(this, RunestoneBooksAnimation.DEATH, i, 1.0F, ANIMATION_VECTOR_CACHE);
        } else {
            long i = (long)(ageInTicks * 50.0F);
            this.book.yRot = ageInTicks / 10.0F;
            KeyframeAnimations.animate(this, RunestoneBooksAnimation.ALIVE, i, 1.0F, ANIMATION_VECTOR_CACHE);
        }
    }

    public @NotNull ModelPart root() {
        return this.book;
    }

}