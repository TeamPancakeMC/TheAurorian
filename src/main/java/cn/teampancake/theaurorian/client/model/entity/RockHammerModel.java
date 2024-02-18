package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.common.entities.monster.RockHammer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RockHammerModel<T extends RockHammer> extends HierarchicalModel<T> {

    private final ModelPart body;

    public RockHammerModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -7.25F, 1.0F));
        PartDefinition body_2 = body.addOrReplaceChild("body_2", CubeListBuilder.create(), PartPose.offset(-4.0F, -3.5F, 11.0F));
        body_2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(96, 52).mirror().addBox(-10.25F, 4.0471F, -0.411F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4659F, -0.1198F, -0.2333F));
        body_2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(96, 52).addBox(7.25F, 4.0471F, -0.411F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(8.0F, 0.0F, 0.0F, -0.4659F, 0.1198F, 0.2333F));
        body_2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(83, 51).addBox(4.8F, -0.9529F, -0.911F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(8.0F, 0.0F, 0.0F, -0.4659F, -0.1198F, -0.2333F));
        body_2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(83, 51).mirror().addBox(-7.8F, -0.9529F, -0.911F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4659F, 0.1198F, 0.2333F));
        body_2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(60, 64).addBox(-4.0F, -12.0F, -12.0F, 8.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 12.0F, 4.0F, -0.48F, 0.0F, 0.0F));
        body_2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(90, 68).mirror().addBox(-7.5F, -2.2029F, -0.911F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4765F, -0.0603F, -0.1163F));
        body_2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(90, 68).addBox(-0.5F, -2.2029F, -0.911F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, 0.0F, -0.4765F, 0.0603F, 0.1163F));
        body_2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(105, 47).addBox(-2.0F, -4.5618F, -2.0306F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -2.5F, 2.0F, -1.0037F, -0.4727F, 0.6149F));
        body_2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(96, 52).addBox(7.25F, 4.0471F, -0.411F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(8.0F, -4.25F, -7.75F, -0.4659F, 0.1198F, 0.2333F));
        body_2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(83, 51).addBox(4.8F, -0.9529F, -0.911F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(8.0F, -4.25F, -7.75F, -0.4659F, -0.1198F, -0.2333F));
        body_2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(90, 68).addBox(-0.5F, -2.2029F, -0.911F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -4.25F, -7.75F, -0.4765F, 0.0603F, 0.1163F));
        body_2.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(105, 47).addBox(-2.0F, -6.5618F, -2.0306F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -6.75F, -5.75F, -1.0037F, -0.4727F, 0.6149F));
        body_2.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(90, 68).mirror().addBox(-7.5F, -2.2029F, -0.911F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.25F, -7.75F, -0.4765F, -0.0603F, -0.1163F));
        body_2.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(60, 64).addBox(-4.0F, -12.0F, -12.0F, 8.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 7.75F, -3.75F, -0.48F, 0.0F, 0.0F));
        body_2.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(83, 51).mirror().addBox(-7.8F, -0.9529F, -0.911F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.25F, -7.75F, -0.4659F, 0.1198F, 0.2333F));
        body_2.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(96, 52).mirror().addBox(-10.25F, 4.0471F, -0.411F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.25F, -7.75F, -0.4659F, -0.1198F, -0.2333F));
        body_2.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(90, 68).mirror().addBox(-7.5F, -2.2029F, -0.911F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -8.5F, -15.0F, -0.4765F, -0.0603F, -0.1163F));
        body_2.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(96, 52).mirror().addBox(-10.25F, 4.0471F, -0.411F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, -8.5F, -15.0F, -0.4659F, -0.1198F, -0.2333F));
        body_2.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(83, 51).mirror().addBox(-7.8F, -0.9529F, -0.911F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, -8.5F, -15.0F, -0.4659F, 0.1198F, 0.2333F));
        body_2.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(96, 52).addBox(7.25F, 4.0471F, -0.411F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(8.0F, -8.5F, -15.0F, -0.4659F, 0.1198F, 0.2333F));
        body_2.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(83, 51).addBox(4.8F, -0.9529F, -0.911F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(8.0F, -8.5F, -15.0F, -0.4659F, -0.1198F, -0.2333F));
        body_2.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(90, 68).addBox(-0.5F, -2.2029F, -0.911F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -8.5F, -15.0F, -0.4765F, 0.0603F, 0.1163F));
        body_2.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(105, 47).addBox(-2.0F, -7.5618F, -2.0306F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -11.0F, -13.0F, -1.0037F, -0.4727F, 0.6149F));
        body_2.addOrReplaceChild("cube_r24", CubeListBuilder.create()
                .texOffs(60, 64).addBox(-4.0F, -12.0F, -12.0F, 8.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-11.0F, -8.0F, -13.0F, 22.0F, 18.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 3.5F, -11.0F, -0.48F, 0.0F, 0.0F));
        body_2.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(86, 55).addBox(-5.75F, -8.75F, 17.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(4.0F, 6.0F, -9.0F, -0.7182F, -0.2046F, -0.2284F));
        body_2.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(85, 54).addBox(-9.75F, -10.75F, 16.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(4.0F, 6.0F, -9.0F, -0.7341F, 0.1176F, 0.1293F));
        body_2.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(67, 68).addBox(-6.75F, -12.5F, 16.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 6.0F, -9.0F, -0.7399F, -0.0589F, -0.0644F));
        body_2.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(67, 68).mirror().addBox(1.75F, -12.5F, 16.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 6.0F, -9.0F, -0.7399F, 0.0589F, 0.0644F));
        body_2.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(86, 55).mirror().addBox(4.75F, -8.75F, 17.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(4.0F, 6.0F, -9.0F, -0.7182F, 0.2046F, 0.2284F));
        body_2.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(126, 53).addBox(-3.0F, -13.5F, 15.0F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 6.0F, -9.0F, -0.7418F, 0.0F, 0.0F));
        body_2.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(85, 54).mirror().addBox(7.75F, -10.75F, 16.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(4.0F, 6.0F, -9.0F, -0.7341F, -0.1176F, -0.1293F));
        body_2.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(149, 51).addBox(-1.5F, -3.5244F, -2.0096F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.625F, 9.0F, 12.0F, -1.2152F, -0.3018F, 0.6915F));
        body_2.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(67, 68).mirror().addBox(1.75F, -12.5F, 16.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 1.0F, -14.0F, -0.7399F, 0.0589F, 0.0644F));
        body_2.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(85, 54).mirror().addBox(7.75F, -10.75F, 16.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(4.0F, 1.0F, -14.0F, -0.7341F, -0.1176F, -0.1293F));
        body_2.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(86, 55).mirror().addBox(4.75F, -8.75F, 17.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(4.0F, 1.0F, -14.0F, -0.7182F, 0.2046F, 0.2284F));
        body_2.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(86, 55).addBox(-5.75F, -8.75F, 17.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(4.0F, 1.0F, -14.0F, -0.7182F, -0.2046F, -0.2284F));
        body_2.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(85, 54).addBox(-9.75F, -10.75F, 16.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(4.0F, 1.0F, -14.0F, -0.7341F, 0.1176F, 0.1293F));
        body_2.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(67, 68).addBox(-6.75F, -12.5F, 16.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, -14.0F, -0.7399F, -0.0589F, -0.0644F));
        body_2.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(126, 53).addBox(-3.0F, -13.5F, 15.0F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, -14.0F, -0.7418F, 0.0F, 0.0F));
        body_2.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(149, 51).addBox(-1.5F, -5.5244F, -2.0096F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.625F, 4.0F, 7.0F, -1.2133F, -0.2995F, 0.6839F));
        body_2.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(101, 18).addBox(-7.0F, -10.5F, 12.0F, 14.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 3.5F, -11.0F, -0.7418F, 0.0F, 0.0F));
        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -11.0F));
        neck.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(86, 55).mirror().addBox(6.5F, 1.6437F, -1.9655F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, -6.0F, -6.0F, 0.1719F, -0.0302F, 0.1719F));
        neck.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(94, 69).mirror().addBox(2.5F, -1.1063F, -2.4655F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -6.0F, -6.0F, 0.1739F, -0.0151F, 0.0859F));
        neck.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(85, 54).mirror().addBox(6.0F, -0.1063F, -2.4655F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, -6.0F, -6.0F, 0.1731F, 0.0227F, -0.1289F));
        neck.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(149, 51).addBox(-1.575F, -4.579F, -1.546F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.75F, -7.75F, -0.423F, -0.7198F, 0.3038F));
        neck.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(126, 53).addBox(-3.0F, -9.0F, -2.75F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -6.0F, 0.1745F, 0.0F, 0.0F));
        neck.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(94, 69).addBox(-7.5F, -1.1063F, -2.4655F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -6.0F, 0.1739F, 0.0151F, -0.0859F));
        neck.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(85, 54).addBox(-8.0F, -0.1063F, -2.4655F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -6.0F, -6.0F, 0.1731F, -0.0227F, 0.1289F));
        neck.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(86, 55).addBox(-8.5F, 1.6437F, -1.9655F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -6.0F, -6.0F, 0.1719F, 0.0302F, -0.1719F));
        neck.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(85, 54).mirror().addBox(6.0F, -0.1063F, -2.4655F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, -7.25F, 1.0F, 0.1731F, 0.0227F, -0.1289F));
        neck.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(94, 69).mirror().addBox(2.5F, -1.1063F, -2.4655F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -7.25F, 1.0F, 0.1739F, -0.0151F, 0.0859F));
        neck.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(86, 55).mirror().addBox(6.5F, 1.6437F, -1.9655F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, -7.25F, 1.0F, 0.1719F, -0.0302F, 0.1719F));
        neck.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(86, 55).addBox(-8.5F, 1.6437F, -1.9655F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -7.25F, 1.0F, 0.1719F, 0.0302F, -0.1719F));
        neck.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(85, 54).addBox(-8.0F, -0.1063F, -2.4655F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -7.25F, 1.0F, 0.1731F, -0.0227F, 0.1289F));
        neck.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(94, 69).addBox(-7.5F, -1.1063F, -2.4655F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.25F, 1.0F, 0.1739F, 0.0151F, -0.0859F));
        neck.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(149, 51).addBox(-1.575F, -5.579F, -1.546F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, -0.75F, -0.423F, -0.7198F, 0.3038F));
        neck.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(126, 53).addBox(-3.0F, -9.0F, -2.75F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 1.0F, 0.1745F, 0.0F, 0.0F));
        neck.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(0, 46).addBox(-7.0F, -6.0F, -11.0F, 14.0F, 13.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 75).addBox(-6.0F, -3.0F, -14.0F, 12.0F, 8.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(73, 18).addBox(-5.5F, 5.0F, -12.75F, 11.0F, 2.0F, 8.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.0F, -11.0F, 0.3927F, 0.0F, 0.0F));
        head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 98).addBox(-5.5F, 0.0F, -11.0F, 11.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -1.0F));
        PartDefinition leg_left = body.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(0, 114).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 15.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5F, 12.0F, 7.0F, -0.829F, 0.0F, 0.0F));
        PartDefinition leg_left_2 = leg_left.addOrReplaceChild("leg_left_2", CubeListBuilder.create().texOffs(43, 117).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.75F, -1.25F, 1.2654F, 0.0F, 0.0F));
        PartDefinition foot_left = leg_left_2.addOrReplaceChild("foot_left", CubeListBuilder.create()
                .texOffs(76, 122).addBox(-5.0F, 0.0F, -12.0F, 10.0F, 5.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(68, 116).addBox(-1.0F, 1.0F, -16.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.5F, 6.5F, -0.4363F, 0.0F, 0.0F));
        foot_left.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(68, 116).addBox(0.0F, -4.0F, -4.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 5.0F, -12.0F, 0.0F, -0.1745F, 0.0F));
        foot_left.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(68, 116).addBox(-2.0F, -4.0F, -4.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 5.0F, -12.0F, 0.0F, 0.1745F, 0.0F));
        PartDefinition leg_right = body.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(0, 114).mirror().addBox(-5.0F, -4.0F, -5.0F, 10.0F, 15.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.5F, 12.0F, 7.0F, -0.829F, 0.0F, 0.0F));
        PartDefinition leg_right_2 = leg_right.addOrReplaceChild("leg_right_2", CubeListBuilder.create().texOffs(43, 117).mirror().addBox(-4.0F, 0.0F, 0.0F, 8.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 8.75F, -1.25F, 1.2654F, 0.0F, 0.0F));
        PartDefinition foot_right = leg_right_2.addOrReplaceChild("foot_right", CubeListBuilder.create()
                .texOffs(76, 122).mirror().addBox(-5.0F, 0.0F, -12.0F, 10.0F, 5.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(68, 116).mirror().addBox(-1.0F, 1.0F, -16.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 13.5F, 6.5F, -0.4363F, 0.0F, 0.0F));
        foot_right.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(68, 116).mirror().addBox(-2.0F, -4.0F, -4.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 5.0F, -12.0F, 0.0F, 0.1745F, 0.0F));
        foot_right.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(68, 116).mirror().addBox(0.0F, -4.0F, -4.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 5.0F, -12.0F, 0.0F, -0.1745F, 0.0F));
        PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(55, 76).addBox(-6.0F, 0.0F, -4.0F, 8.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 3.0F, -10.0F, -0.7733F, 0.0992F, 0.2427F));
        PartDefinition arm_right_2 = arm_right.addOrReplaceChild("arm_right_2", CubeListBuilder.create()
                .texOffs(88, 78).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(129, 73).addBox(-6.0F, 7.0F, -6.0F, 12.0F, 13.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 14.0F, 0.0F, 0.5938F, 0.1671F, -0.2024F));
        arm_right_2.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(166, 69).addBox(1.25F, -2.0F, 2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1535F, 0.1284F, 0.1242F));
        arm_right_2.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(166, 69).addBox(1.5F, -4.0F, -8.75F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3201F, -0.1995F, 0.2234F));
        arm_right_2.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(166, 69).addBox(-7.25F, -3.0F, -7.25F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.215F, -0.0376F, -0.135F));
        arm_right_2.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(166, 69).addBox(-8.0F, -3.0F, 2.25F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, -0.3054F));
        PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(55, 76).mirror().addBox(-2.0F, 0.0F, -4.0F, 8.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 3.0F, -10.0F, -0.7733F, -0.0992F, -0.2427F));
        PartDefinition arm_left_2 = arm_left.addOrReplaceChild("arm_left_2", CubeListBuilder.create()
                .texOffs(88, 78).mirror().addBox(-5.0F, -3.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(129, 73).mirror().addBox(-6.0F, 7.0F, -6.0F, 12.0F, 13.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 14.0F, 0.0F, 0.5938F, -0.1671F, 0.2024F));
        arm_left_2.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(166, 69).mirror().addBox(-6.25F, -2.0F, 2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1535F, -0.1284F, -0.1242F));
        arm_left_2.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(166, 69).mirror().addBox(-6.5F, -4.0F, -8.75F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3201F, 0.1995F, -0.2234F));
        arm_left_2.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(166, 69).mirror().addBox(2.25F, -3.0F, -7.25F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.215F, 0.0376F, 0.135F));
        arm_left_2.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(166, 69).mirror().addBox(3.0F, -3.0F, 2.25F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.3054F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 141).addBox(-6.0F, -7.0F, -1.0F, 12.0F, 13.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.0F, 17.0F, -0.5236F, 0.0F, 0.0F));
        PartDefinition tail_2 = tail.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(49, 143).addBox(-5.0F, -6.0F, -2.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.2182F, 0.0F, 0.0F));
        tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create()
                .texOffs(90, 143).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(123, 122).addBox(-5.0F, -4.5F, 9.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(127, 141).addBox(-6.0F, -10.5F, 6.0F, 12.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(127, 163).addBox(-6.0F, 3.5F, 6.0F, 12.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 8.0F, 0.2182F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 192, 192);
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