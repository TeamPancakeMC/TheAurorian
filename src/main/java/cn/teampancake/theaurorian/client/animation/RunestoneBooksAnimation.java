package cn.teampancake.theaurorian.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RunestoneBooksAnimation {

    public static final AnimationDefinition ALIVE = AnimationDefinition.Builder.withLength(3.5f).looping()
            .addAnimation("book_1", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.9167666f, KeyframeAnimations.posVec(0f, 2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.8343333f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.75f, KeyframeAnimations.posVec(0f, 2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("book_1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.4583433f, KeyframeAnimations.degreeVec(0f, 0f, -25f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("book_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.9167666f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.8343333f, KeyframeAnimations.posVec(0f, 2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.posVec(0f, 2f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("book_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -25f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, -25f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune2_3", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune2_4", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune1_3", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune1_4", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune2_1", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune1_1", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune2_2", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune1_2", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
    
    public static final AnimationDefinition DEATH = AnimationDefinition.Builder.withLength(3f).looping()
            .addAnimation("book_2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.posVec(0f, -29.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5834333f, KeyframeAnimations.posVec(-1f, -27.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.5834335f, KeyframeAnimations.posVec(-2f, -34f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("book_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -25f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(0f, 0f, -2.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.2083435f, KeyframeAnimations.degreeVec(-2.2f, -36.45f, -74.43f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune2_3", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune2_4", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune1_3", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune1_4", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune2_1", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune1_1", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune2_2", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune1_2", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune1_5", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rune2_5", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("book1", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 2f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.9167666f, KeyframeAnimations.posVec(0f, -10.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.1676667f, KeyframeAnimations.posVec(-2f, -8.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.9583433f, KeyframeAnimations.posVec(-3f, -15f, 0f), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("book1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.7083433f, KeyframeAnimations.degreeVec(-2.66f, -37.53f, -99.87f), AnimationChannel.Interpolations.LINEAR))).build();

}