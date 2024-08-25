package cn.teampancake.theaurorian.common.effect;

import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectCategory;

public class ConfusionEffect extends TAMobEffect {

    public ConfusionEffect() {
        super(MobEffectCategory.HARMFUL, 0xEB00c4);
    }

    public static void onMovementInputUpdate(Input input, LocalPlayer player) {
        swapValues(input);
        invertValues(input);
        calculate(input, player.isMovingSlowly());
    }

    private static void swapValues(Input input) {
        boolean temp = input.jumping;
        input.jumping = input.shiftKeyDown;
        input.shiftKeyDown = temp;
    }

    private static void invertValues(Input input) {
        input.up = !input.up;
        input.down = !input.down;
        input.left = !input.left;
        input.right = !input.right;
        input.leftImpulse = -input.leftImpulse;
        input.forwardImpulse = -input.forwardImpulse;
    }

    private static void calculate(Input input, boolean isMovingSlowly) {
        input.forwardImpulse = calculate(input.up, input.down, isMovingSlowly);
        input.leftImpulse = calculate(input.left, input.right, isMovingSlowly);
    }

    private static float calculate(boolean a, boolean b, boolean isMovingSlowly) {
        if (a == b) {
            return 0.0F;
        }
        if (a) {
            return (isMovingSlowly ? 0.3F : 1.0F);
        }
        return -1.0F * (isMovingSlowly ? 0.3F : 1.0F);
    }

}