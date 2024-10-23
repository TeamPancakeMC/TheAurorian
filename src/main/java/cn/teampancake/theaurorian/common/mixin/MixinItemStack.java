package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class MixinItemStack implements IItemStackExtension {

    @Inject(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V", shift = At.Shift.AFTER))
    public void hurtAndBreak(int damage, ServerLevel level, LivingEntity entity, Consumer<Item> onBreak, CallbackInfo ci) {
        AABB aabb = entity.getBoundingBox().inflate(15.0D);
        Holder<Enchantment> enchantment = TAEnchantments.get(level, TAEnchantments.AMNESIA_CURSE);
        if (!level.isClientSide() && this.getEnchantmentLevel(enchantment) > 0) {
            level.getEntitiesOfClass(LivingEntity.class, aabb).forEach(target -> {
                target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 600));
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600));
            });
        }
    }

}