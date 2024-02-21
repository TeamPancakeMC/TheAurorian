package cn.teampancake.theaurorian.client.animation.definitions;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianPixieAnimation {

    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(1.5f).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wing_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, 16.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(60f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wing_left2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, -16.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -2f, 0f), AnimationChannel.Interpolations.LINEAR))).build();

    public static final AnimationDefinition FLY = AnimationDefinition.Builder.withLength(2.5f).looping()
            .addAnimation("wing_left", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.8343334f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0834333f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.4167667f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5834333f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.6766667f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.75f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.8343333f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.0834335f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.1676665f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.3433335f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.4167665f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.875f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wing_left2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.8343334f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0834333f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.4167667f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5834333f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.6766667f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.75f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.8343333f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9167667f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.0834335f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.1676665f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.3433335f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.4167665f, KeyframeAnimations.degreeVec(-22.5f, 0f, -22.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(-22.5f, 0f, 22.5f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.875f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
    
    public static final AnimationDefinition TRADE = AnimationDefinition.Builder.withLength(1.25f).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.625f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.25f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wing_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-55f, 67.5f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(60f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("wing_left2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-55f, -67.5f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("all", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -2f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
    
}