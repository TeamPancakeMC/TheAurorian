package cn.teampancake.theaurorian.mixin;

import cn.teampancake.theaurorian.common.items.ModArmorMaterials;
import cn.teampancake.theaurorian.utils.AurorianSteelHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DiggerItem.class)
public class MixinDiggerItem {

    @Inject(method = "mineBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER))
    public void mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() == ModArmorMaterials.AURORIAN_STEEL) {
            AurorianSteelHelper.handleAurorianSteelDurability(stack);
        }
    }

}