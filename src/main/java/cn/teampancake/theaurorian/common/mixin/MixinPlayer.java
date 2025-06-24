package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {

    @Shadow @Final private Abilities abilities;
    @Shadow protected FoodData foodData;

    protected MixinPlayer(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "wantsToStopRiding", at = @At(value = "HEAD"), cancellable = true)
    protected void wantsToStopRiding(CallbackInfoReturnable<Boolean> cir) {
        if (this.isPassenger() && this.hasEffect(TAMobEffects.PARALYSIS)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "makeStuckInBlock", at = @At(value = "HEAD"), cancellable = true)
    public void makeStuckInBlock(BlockState state, Vec3 motionMultiplier, CallbackInfo ci) {
        Holder<Enchantment> enchantment = TAEnchantments.get(this.level(), TAEnchantments.COBWEB_CROSSING);
        int i = EnchantmentHelper.getEnchantmentLevel(enchantment, this);
        if (state.getBlock() instanceof WebBlock && i > 0) {
            ci.cancel();
        }
    }

    @Inject(method = "causeFoodExhaustion", at = @At(value = "HEAD"), cancellable = true)
    public void causeFoodExhaustion(float exhaustion, CallbackInfo ci) {
        Holder<Enchantment> enchantment = TAEnchantments.get(this.level(), TAEnchantments.WIND_RUNNER);
        if (EnchantmentHelper.getEnchantmentLevel(enchantment, this) > 0) {
            if (!this.abilities.invulnerable && !this.level().isClientSide) {
                this.foodData.addExhaustion((exhaustion / 2.0F));
                ci.cancel();
            }
        }
    }

}