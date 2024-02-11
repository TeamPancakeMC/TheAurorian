package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TAAttributes;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {

    protected MixinPlayer(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "hurtArmor", at = @At(value = "HEAD"), cancellable = true)
    protected void hurtArmor(DamageSource damageSource, float damage, CallbackInfo ci) {
        if (this.hasEffect(TAMobEffects.CORRUPTION.get())) {
            Attribute attribute = TAAttributes.ARMOR_HURT_ACCUMULATION.get();
            AttributeInstance instance = this.getAttribute(attribute);
            double i = this.getAttributeValue(attribute);
            if (instance != null) {
                instance.setBaseValue(i + damage);
                ci.cancel();
            }
        }
    }

}