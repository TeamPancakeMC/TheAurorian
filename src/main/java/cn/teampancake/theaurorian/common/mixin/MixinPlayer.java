package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TACapability;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {

    protected MixinPlayer(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "wantsToStopRiding", at = @At(value = "HEAD"), cancellable = true)
    protected void wantsToStopRiding(CallbackInfoReturnable<Boolean> cir) {
        if (this.isPassenger() && this.hasEffect(TAMobEffects.PARALYSIS.get())) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "hurtArmor", at = @At(value = "HEAD"), cancellable = true)
    protected void hurtArmor(DamageSource damageSource, float damage, CallbackInfo ci) {
        if (this.hasEffect(TAMobEffects.CORRUPTION.get()) || this.hasEffect(TAMobEffects.TOUGH.get())) {
            if (this.hasEffect(TAMobEffects.CORRUPTION.get())) {
                this.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
                    float i = miscNBT.armorHurtAccumulation;
                    miscNBT.armorHurtAccumulation = i + damage;
                });
            }

            ci.cancel();
        }
    }

    @Inject(method = "makeStuckInBlock", at = @At(value = "HEAD"), cancellable = true)
    public void makeStuckInBlock(BlockState state, Vec3 motionMultiplier, CallbackInfo ci) {
        int i = EnchantmentHelper.getEnchantmentLevel(TAEnchantments.COBWEB_CROSSING.get(), this);
        if (state.getBlock() instanceof WebBlock && i > 0) {
            ci.cancel();
        }
    }

}