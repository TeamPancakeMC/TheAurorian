package cn.teampancake.theaurorian.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RunestoneKeeperAnimation {
    
    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(4.5f).looping()
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
                    new Keyframe(0f, KeyframeAnimations.degreeVec(9.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.posVec(0f, -0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.degreeVec(0f, 0f, -3f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.posVec(0f, -0.7f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(0f, 0f, 10f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("shield", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(0f, 0f, 10f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.posVec(0f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.375f, KeyframeAnimations.posVec(0f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.degreeVec(0f, 0f, 720f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.posVec(0f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.375f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.degreeVec(0f, 0f, -1080f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.375f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(3f).looping()
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, -1.3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.posVec(0f, 0.7f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, -1.3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.posVec(0f, -0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.625f, KeyframeAnimations.posVec(0f, 0.7f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.posVec(0f, -0.5f, -1.3f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-15.01f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(20.99f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(-10.01f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(-15.01f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(20.99f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.625f, KeyframeAnimations.degreeVec(-10.01f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(-15.01f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0.7f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -0.5f, -1.3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.875f, KeyframeAnimations.posVec(0f, 0.7f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.posVec(0f, -0.5f, -1.3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(20.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-10.01f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(-15.01f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(20.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.875f, KeyframeAnimations.degreeVec(-10.01f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(-15.01f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(20.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.posVec(0f, 0.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.875f, KeyframeAnimations.posVec(0f, 0.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.625f, KeyframeAnimations.posVec(0f, 0.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0.04f, -4.99f, -0.44f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(4.87f, 4.9f, -1.31f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(0.04f, -4.99f, -0.44f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(4.87f, 4.9f, -1.31f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(0.04f, -4.99f, -0.44f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-9.99f, -0.52f, -2.95f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(9.99f, 0.52f, -2.95f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(-9.99f, -0.52f, -2.95f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(9.99f, 0.52f, -2.95f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(-9.99f, -0.52f, -2.95f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("shield", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(10f, -0.17f, 0.98f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(-30f, 0.5f, 0.87f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(10f, -0.17f, 0.98f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(-30f, 0.5f, 0.87f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(10f, -0.17f, 0.98f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 0f, 720f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 0f, -1080f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, -2.48f, 0.44f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(-10.06f, 7.45f, -0.66f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(-5f, -2.48f, 0.44f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(-10.06f, 7.45f, -0.66f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(-5f, -2.48f, 0.44f), AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition RUN = AnimationDefinition.Builder.withLength(1.3433333f).looping()
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.7f, -1.8f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.1676667f, KeyframeAnimations.posVec(0f, 0.7f, -1.8f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-17.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(17.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-15f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-17.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(17.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(-15f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(-17.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, 0.7f, -1.8f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.8343334f, KeyframeAnimations.posVec(0f, 0.7f, -1.8f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(17.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-7.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-17.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(17.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.8343334f, KeyframeAnimations.degreeVec(-7.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(-17.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(17.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.8343334f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.1676667f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.posVec(0f, 0.4f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-22.42f, -1.91f, -7.62f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(24.92f, 2.11f, -7.53f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-22.42f, -1.91f, -7.62f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(24.92f, 2.11f, -7.53f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(-22.42f, -1.91f, -7.62f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("shield", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-51.83f, -9.89f, -4.69f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-39.57f, -6.41f, -4.69f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-51.83f, -9.89f, -4.69f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(-39.57f, -6.41f, -4.69f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(-51.83f, -9.89f, -4.69f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(0f, 0f, 720f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(0f, 0f, -1080f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, 0.2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.8343334f, KeyframeAnimations.posVec(0f, 0.2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.1676667f, KeyframeAnimations.posVec(0f, 0.2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-10.08f, -7.4f, 1.31f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-25.92f, 11.15f, -7.41f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-5.07f, 9.97f, -0.66f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-25.19f, -5.51f, 3.51f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-10.08f, -7.4f, 1.31f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.8343334f, KeyframeAnimations.degreeVec(-25.92f, 11.15f, -7.41f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(-5.07f, 9.97f, -0.66f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(-25.19f, -5.51f, 3.51f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(-10.08f, -7.4f, 1.31f), AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition ATTACK_1 = AnimationDefinition.Builder.withLength(0.75f)
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-15f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-17.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(-5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(9.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(9.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-12.5f, 0f, -3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(15f, 0f, -3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, -3f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("shield", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-119.72f, 14.24f, -17.6f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-69.95f, -3.11f, -19.94f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-62.45f, -3.11f, -19.94f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(45f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 360f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, -720f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("hat", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.041676664f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, 4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("hat", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.125f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition ATTACK_2 = AnimationDefinition.Builder.withLength(3.5f)
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.posVec(0f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(-5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(9.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(9.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.degreeVec(14.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(9.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.875f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.4583435f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(20.35f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-17.5f, 0f, -3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.degreeVec(-40.73f, -13.36f, -18.02f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.9583434f, KeyframeAnimations.degreeVec(-36.93f, -8.13f, -15.78f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(-40.73f, -13.36f, -18.02f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5834333f, KeyframeAnimations.degreeVec(-36.93f, -8.13f, -15.78f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.degreeVec(-40.73f, -13.36f, -18.02f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(-36.93f, -8.13f, -15.78f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5416765f, KeyframeAnimations.degreeVec(-40.73f, -13.36f, -18.02f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(14.61f, 6.04f, -7.72f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, -3f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("shield", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.degreeVec(-112.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.875f, KeyframeAnimations.degreeVec(-107.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(-112.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.375f, KeyframeAnimations.degreeVec(-107.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.625f, KeyframeAnimations.degreeVec(-112.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.875f, KeyframeAnimations.degreeVec(-107.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.125f, KeyframeAnimations.degreeVec(-112.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.375f, KeyframeAnimations.degreeVec(-107.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.8343335f, KeyframeAnimations.degreeVec(65f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.625f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 360f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.625f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, -720f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(8.75f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.875f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.75f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.625f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(2.5f, KeyframeAnimations.posVec(0f, 0.2f, 0.2f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition ATTACK_3 = AnimationDefinition.Builder.withLength(1f)
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.posVec(0f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-1.21f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(-15f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-8.38f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(-5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.20834334f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(9.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(9.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(4.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(9.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(10.06f, 12.49f, 0.55f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0.57f, -27.08f, -5.24f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(-38.43f, 39.01f, 35.43f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-109.04f, -34.85f, 0.07f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -3f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("shield", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-15.65f, 23.8f, 11.63f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(47.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(40.83f, 40.4f, 27.01f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 360f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -720f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-7f, 19.86f, -2.39f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.20834334f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, -2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(-5f, -5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(12.44f, 9.94f, -1.31f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition DEATH = AnimationDefinition.Builder.withLength(3f)
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -0.2f, 3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.posVec(0f, 1.8f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(-90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(2.5f, 0.22f, 2.49f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(9.99f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-22.51f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.1676665f, KeyframeAnimations.degreeVec(2.49f, 0.33f, -2.48f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(-60f, 0f, -3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.4167667f, KeyframeAnimations.degreeVec(-10f, 0f, -3f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("shield", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(-80f, 0f, -3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(-9.67f, 2.58f, 11.78f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.posVec(-3f, -6f, 4f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(87.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_1", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.scaleVec(1f, 1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 0f, -5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(0f, 0f, -257.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wand_2", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.scaleVec(1f, 1f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.8343334f, KeyframeAnimations.degreeVec(45f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.8343333f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("hat", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.4f, -5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.posVec(0f, -1.6f, -5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("hat", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(83.53f, 24.09f, -6.88f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("hat", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 3f, -8f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head_2", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5834334f, KeyframeAnimations.scaleVec(1f, 0f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("shield2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5416767f, KeyframeAnimations.degreeVec(-305f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("shield2", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5416767f, KeyframeAnimations.scaleVec(1f, 1f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
    
}