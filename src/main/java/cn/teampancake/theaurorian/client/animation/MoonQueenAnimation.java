package cn.teampancake.theaurorian.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoonQueenAnimation {

    public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(1.5f).looping()
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_higher", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-24.98f, 1.06f, 2.27f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(14.99f, -0.65f, 2.41f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(-24.98f, 1.06f, 2.27f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(14.99f, 0.65f, -2.41f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(-24.98f, -1.06f, -2.27f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(14.99f, 0.65f, -2.41f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-22.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0.4f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(-19.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.posVec(0f, 0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(3.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(3.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(3.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone5", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone6", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(0f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone7", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(0f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone8", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(4f).looping()
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_higher", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.posVec(0f, 0.1f, 0.2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("eyeball_right", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(2.625f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.7083435f, KeyframeAnimations.scaleVec(1f, 0.5f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.7916765f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(2.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.7083435f, KeyframeAnimations.posVec(0f, -1.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.7916765f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_right", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(2.625f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.7083435f, KeyframeAnimations.scaleVec(1f, 1.7f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.7916765f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("eyeball_left", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(2.625f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.7083435f, KeyframeAnimations.scaleVec(1f, 0.5f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.7916765f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(2.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.7083435f, KeyframeAnimations.posVec(0f, -1.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.7916765f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_left", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(2.625f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.7083435f, KeyframeAnimations.scaleVec(1f, 1.7f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.7916765f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.posVec(0f, 0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-7.49f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4f, KeyframeAnimations.degreeVec(-7.49f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.posVec(0f, 0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-7.5f, -0.22f, -2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4f, KeyframeAnimations.degreeVec(-7.5f, -0.22f, -2.49f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition MELEE_ATTACK = AnimationDefinition.Builder.withLength(2f)
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(5.18f, -14.94f, -1.34f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(5.51f, 24.9f, 2.33f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(5.51f, 24.9f, 2.33f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(5.18f, -14.94f, -1.34f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(5.18f, -14.94f, -1.34f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_higher", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(-0.1f, -0.1f, 0.2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.posVec(0f, 0f, -0.4f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.posVec(0f, 0f, -0.4f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.posVec(-0.1f, -0.1f, 0.2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.posVec(-0.1f, -0.1f, 0.2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_higher", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-2.48f, -4.98f, -0.44f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(15.01f, 2.44f, 0.54f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(-2.48f, -4.98f, -0.44f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(-2.48f, -4.98f, -0.44f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, -15f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(7.5f, 10f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(7.5f, 10f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(7.65f, 14.96f, 0.67f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(7.5f, 10f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("eyeball_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("eyeball_right", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.scaleVec(1f, 0.5f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.scaleVec(1f, 0.5f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4583433f, KeyframeAnimations.posVec(0f, 0.2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_right", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.scaleVec(1f, 0.7f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("eyeball_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("eyeball_left", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.scaleVec(1f, 0.5f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.scaleVec(1f, 0.5f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4583433f, KeyframeAnimations.posVec(0f, 0.2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_left", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.scaleVec(1f, 0.7f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-7.49f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-114.33f, -32.44f, 16.73f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(-18.64f, -15.57f, 18.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(-18.29f, -10.39f, 17f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(-18.64f, -15.57f, 18.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(-51.3f, 41.36f, 41.55f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(-51.3f, 41.36f, 41.55f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(-7.49f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(-45f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(-45f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(0f, 0f, -37.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(0f, 0f, -37.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sword", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(31.34f, -21.41f, -7.1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(75.87f, -37.02f, 22.04f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(75.87f, -37.02f, 22.04f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(7.94f, -16.41f, -40.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(10.44f, -16.41f, -40.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-7.5f, -0.22f, -2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-87.47f, 7.27f, -2.16f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(-45.63f, 56.32f, 24.9f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(-45.63f, 56.32f, 24.9f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(-33.28f, 74.33f, 42.45f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(-33.28f, 74.33f, 42.45f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(-7.5f, -0.22f, -2.49f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-25f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-20f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(-20f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(-12.22f, 3.4f, 11.91f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(-12.22f, 3.4f, 11.91f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(-5f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(13.75f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(17.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(17.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(12.5f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(19.95f, -24.98f, -2.66f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7916766f, KeyframeAnimations.degreeVec(19.95f, -24.98f, -2.66f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(17.5f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(17.5f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(5f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-8.75f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7083434f, KeyframeAnimations.degreeVec(-3.27f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(-3.27f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(27.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(-2.51f, -0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone6", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.125f, KeyframeAnimations.degreeVec(0f, 0f, -5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone8", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone9", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.7916767f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition DEFEND = AnimationDefinition.Builder.withLength(1.25f).looping()
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(5.18f, -14.94f, -1.34f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(5.18f, -14.94f, -1.34f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_higher", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.posVec(-0.1f, -0.1f, 0.2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(-0.1f, -0.1f, 0.2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(-0.1f, -0.1f, 0.2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_higher", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-2.48f, -4.98f, -0.44f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-4.98f, -4.98f, -0.44f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-2.48f, -4.98f, -0.44f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(7.65f, 14.96f, 0.67f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(7.5f, 10f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-7.49f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-51.3f, 41.36f, 41.55f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-51.3f, 41.36f, 41.55f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(-7.49f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, -12.5f, -37.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, -12.5f, -37.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sword", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(31.95f, -13.81f, -55.8f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(34.45f, -13.81f, -55.8f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-7.5f, -0.22f, -2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-90.31f, 24.97f, -1.96f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-90.31f, 24.97f, -1.96f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(-7.5f, -0.22f, -2.49f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-15f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(-5f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(5f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.125f, KeyframeAnimations.degreeVec(-3.27f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-2.51f, -0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone6", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(0f, 0f, -5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone8", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone9", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4583433f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("eyeball_right", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2916767f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.scaleVec(1f, 0.6f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4583433f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5834334f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -1.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4583433f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_right", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2916767f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.scaleVec(1f, 1.7f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5834334f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4583433f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition SKILL_LUNA_BEFALL = AnimationDefinition.Builder.withLength(9f)
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.8343334f, KeyframeAnimations.posVec(0f, 1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.208343f, KeyframeAnimations.posVec(0f, 1.52f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.5f, KeyframeAnimations.posVec(0f, 1.53f, 3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_higher", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.208343f, KeyframeAnimations.posVec(0f, -0.2f, 0.1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.458343f, KeyframeAnimations.posVec(0f, 0f, -0.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.458344f, KeyframeAnimations.posVec(0f, 0f, -0.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, -0.4f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_higher", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.208343f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.458343f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.458344f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.208343f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.458343f, KeyframeAnimations.degreeVec(0.73f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.458344f, KeyframeAnimations.degreeVec(-4.27f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone9", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.25f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.458343f, KeyframeAnimations.degreeVec(-11.1f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.458344f, KeyframeAnimations.degreeVec(-11.1f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.5f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.5f, KeyframeAnimations.posVec(0f, -0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-7.49f, 0.33f, 2.48f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-144.52f, 28.94f, -11.12f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.208343f, KeyframeAnimations.degreeVec(-152.02f, 28.94f, -11.12f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.458343f, KeyframeAnimations.degreeVec(-62.08f, 4.1f, -23.78f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.458344f, KeyframeAnimations.degreeVec(-62.08f, 4.1f, -23.78f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(-6.93f, 2.86f, 22.33f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.208343f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.458343f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.458344f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sword", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(50f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.208343f, KeyframeAnimations.degreeVec(50f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.458343f, KeyframeAnimations.degreeVec(65.18f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.458344f, KeyframeAnimations.degreeVec(65.18f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-7.5f, -0.22f, -2.49f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(17.46f, 1.29f, -7.26f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.208343f, KeyframeAnimations.degreeVec(17.46f, 1.29f, -7.26f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(4.458343f, KeyframeAnimations.degreeVec(38.38f, 1.19f, -6.96f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.458344f, KeyframeAnimations.degreeVec(38.38f, 1.19f, -6.96f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(-6.85f, -3.06f, -24.82f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.375f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7083434f, KeyframeAnimations.degreeVec(-16.45f, -1.2f, 0.2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.5f, KeyframeAnimations.degreeVec(-16.45f, -1.2f, 0.2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(-17.5f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7083434f, KeyframeAnimations.posVec(0f, 1.3f, -0.1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.5f, KeyframeAnimations.posVec(0f, 1.3f, -0.1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.posVec(0f, 0.6f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7083434f, KeyframeAnimations.degreeVec(42.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.5f, KeyframeAnimations.degreeVec(42.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7083434f, KeyframeAnimations.degreeVec(2.5f, -0.11f, 2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.5f, KeyframeAnimations.degreeVec(2.5f, -0.11f, 2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(-10f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone8", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(8.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(-10f, 0f, 10f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone7", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(8.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(0f, 0f, 10f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone6", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(8.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(0f, 0f, -10f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone5", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(8.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(-10f, 0f, -10f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(8.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(8.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(8.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(10f, 0f, -10f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(8.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(10f, 0f, 10f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(8.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.posVec(0f, 1.1f, -0.7f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9f, KeyframeAnimations.degreeVec(47.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
    
    public static final AnimationDefinition SKILL_LUNA_BEFALL_END = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_higher", new AnimationChannel(AnimationChannel.Targets.POSITION, 
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -0.4f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, -0.4f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body_higher", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION, 
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone9", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(27.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_right", new AnimationChannel(AnimationChannel.Targets.POSITION, 
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_left", new AnimationChannel(AnimationChannel.Targets.POSITION, 
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("brown_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.POSITION, 
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-6.93f, 2.86f, 22.33f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-6.93f, 2.86f, 22.33f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sword", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.POSITION, 
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-6.85f, -3.06f, -24.82f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-6.85f, -3.06f, -24.82f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("arm_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone8", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-10f, 0f, 10f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-12.49f, 0.54f, 12.44f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-10f, 0f, 10f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone7", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 10f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 10f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone6", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -10f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, -10f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone5", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-10f, 0f, -10f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-12.49f, -0.43f, -12.46f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-10f, 0f, -10f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(17.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(10f, 0f, -10f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(12.49f, 0.54f, -12.44f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(10f, 0f, -10f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(10f, 0f, 10f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(12.49f, -0.43f, 12.46f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(10f, 0f, 10f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-17.5f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-17.5f, 0f, 2.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.POSITION, 
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.6f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.6f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-10f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-10f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.POSITION, 
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 1.1f, -0.7f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1.1f, -0.7f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(47.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(47.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
}