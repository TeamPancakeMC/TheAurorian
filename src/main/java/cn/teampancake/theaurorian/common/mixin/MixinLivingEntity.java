package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.entities.monster.AurorianSlime;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity implements ILivingEntityExtension {

    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Inject(method = "doHurtEquipment", at = @At(value = "HEAD"), cancellable = true)
    public void doHurtEquipment(DamageSource damageSource, float damageAmount, EquipmentSlot[] slots, CallbackInfo ci) {
        if (!(damageAmount <= 0.0F) && damageSource.getEntity() instanceof AurorianSlime) {
            int i = (int) Math.max(1.0F, damageAmount / 4.0F) * 3;
            for (EquipmentSlot slot : slots) {
                ItemStack itemStack = this.getItemBySlot(slot);
                if (itemStack.getItem() instanceof ArmorItem) {
                    itemStack.hurtAndBreak(i, this.self(), slot);
                }
            }

            ci.cancel();
        }
    }

    @Inject(method = "getDamageAfterArmorAbsorb", at = @At(value = "HEAD"), cancellable = true)
    protected void getDamageAfterArmorAbsorb(DamageSource damageSource, float damageAmount, CallbackInfoReturnable<Float> cir) {
        if (damageSource.getEntity() instanceof Player player) {
            Holder<Enchantment> holder = TAEnchantments.get(player.level(), TAEnchantments.SOUL_SLASH);
            ItemStack itemInHand = player.getItemInHand(player.getUsedItemHand());
            RandomSource random = RandomSource.create();
            int level = itemInHand.getEnchantmentLevel(holder);
            if (level > 0 && random.nextFloat() <= level * 0.05F) {
                cir.setReturnValue(damageAmount);
            }
        }
    }

}