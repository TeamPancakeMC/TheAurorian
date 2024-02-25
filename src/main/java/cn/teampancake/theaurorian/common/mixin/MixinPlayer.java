package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TACapability;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
        if (this.hasEffect(TAMobEffects.TOUGH.get())) {
            ci.cancel();
        }

        if (this.hasEffect(TAMobEffects.CORRUPTION.get())) {
            this.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
                float i = miscNBT.getArmorHurtAccumulation();
                miscNBT.setArmorHurtAccumulation(i + damage);
            });

            ci.cancel();
        }
    }

}