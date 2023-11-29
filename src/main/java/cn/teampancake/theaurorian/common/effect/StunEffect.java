package cn.teampancake.theaurorian.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class StunEffect extends MobEffect {

    private static final String STUN_UUID = "e8c3b0b0-3d6c-11eb-b378-0242ac130002";

    public StunEffect() {
        super(MobEffectCategory.HARMFUL, 0x8B0000);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, STUN_UUID, -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

}