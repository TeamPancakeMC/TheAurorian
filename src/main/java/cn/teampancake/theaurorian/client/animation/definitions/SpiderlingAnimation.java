package cn.teampancake.theaurorian.client.animation.definitions;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiderlingAnimation {

    public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(0.6766666f).looping()
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, -0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.4f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(20.05f, -11.04f, -4.73f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(7.25f, -3.83f, 14.27f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(9.8f, -6.49f, 1.23f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(20.05f, -11.04f, -4.73f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_lower_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-2.76f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-19.98f, -0.85f, -2.35f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-17.76f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-2.76f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(7.3f, -6.49f, 1.23f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(2.57f, -2.62f, 6.11f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(10.86f, -14.99f, -7.41f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(7.3f, -6.49f, 1.23f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_lower_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-7.76f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(22.24f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-15.63f, 6.31f, 11.24f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-7.76f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(4.8f, -6.49f, 1.23f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(20.05f, -11.04f, -4.73f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(7.25f, -3.83f, 14.27f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(4.8f, -6.49f, 1.23f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_lower_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-14.64f, -0.23f, -2.56f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(9.74f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-20.62f, -1.46f, -2.12f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-14.64f, -0.23f, -2.56f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(20.05f, -11.04f, -4.73f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(1.21f, -7.61f, -0.32f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-2.7f, -6.49f, 1.23f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(20.05f, -11.04f, -4.73f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_lower_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(24.74f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-12.09f, -1.06f, -2.3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-12.76f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(24.74f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(20.05f, -11.04f, -4.73f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(7.25f, -3.83f, 14.27f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(4.8f, -6.49f, 1.23f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(20.05f, -11.04f, -4.73f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_lower_4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(9.74f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-20.62f, -1.46f, -2.12f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-19.32f, 1.44f, 2.16f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(9.74f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-2.7f, -6.49f, 1.23f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(17.57f, -2.62f, 6.11f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(10.86f, -14.99f, -7.41f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-2.7f, -6.49f, 1.23f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_lower_4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-21.12f, 7.17f, 10.72f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(4.74f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-15.63f, 6.31f, 11.24f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-21.12f, 7.17f, 10.72f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-2.7f, -6.49f, 1.23f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(16.58f, -8.14f, -9.08f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(7.25f, -3.83f, 14.27f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-2.7f, -6.49f, 1.23f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_lower_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-20.26f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(7.25f, 5.5f, 1.75f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-20.62f, -1.46f, -2.12f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-20.26f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(7.53f, -12.19f, -2.51f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(1.21f, -7.61f, -0.32f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-2.7f, -6.49f, 1.23f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(7.53f, -12.19f, -2.51f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_lower_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(27.24f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-12.09f, -1.06f, -2.3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-12.76f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(27.24f, 4.23f, 6.59f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
    
    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(2.5f).looping()
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.posVec(0f, -0.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, 3.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, -3.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, 3.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, -3.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, 3.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, -3.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, 3.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, -3.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
    
    public static final AnimationDefinition ATTACK = AnimationDefinition.Builder.withLength(0.75f)
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 7.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, -16f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, -7.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 16f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 7.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, -16f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, -7.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 16f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-0.32f, -1.71f, -2.71f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 20f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_lower_4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-2.18f, 1.71f, 2.71f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0.64f, -6.51f, -23.76f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_lower_4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-1.79f, 3.51f, 29.39f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 7.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(9.41f, -8.55f, -17.27f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_right_lower_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-5.68f, -0.28f, 20.02f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, -7.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(9.41f, -8.55f, 17.27f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leg_left_lower_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(6.22f, 5.16f, -34.34f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0.5f, -0.2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, -22.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 22.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone4", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.375f, KeyframeAnimations.degreeVec(-10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();

}