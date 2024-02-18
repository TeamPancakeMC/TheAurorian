package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TABiomes;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {

    @Shadow public abstract boolean hasEffect(MobEffect effect);

    public MixinLivingEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "tryAddFrost", at = @At(value = "HEAD"), cancellable = true)
    protected void tryAddFrost(CallbackInfo ci) {
        if (this.level().getBiome(this.blockPosition()).is(TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD)) {
            ci.cancel();
        }
    }

    @ModifyArg(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/WalkAnimationState;setSpeed(F)V"))
    public float hurt(float speed) {
        return this.hasEffect(TAMobEffects.CORRUPTION.get()) ? 0.0F : speed;
    }

    @ModifyArg(method = "handleDamageEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/WalkAnimationState;setSpeed(F)V"))
    public float handleDamageEvent(float speed) {
        return this.hasEffect(TAMobEffects.CORRUPTION.get()) ? 0.0F : speed;
    }

}