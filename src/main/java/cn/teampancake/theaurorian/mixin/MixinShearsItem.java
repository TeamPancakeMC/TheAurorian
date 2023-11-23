package cn.teampancake.theaurorian.mixin;

import cn.teampancake.theaurorian.registry.TAItems;
import cn.teampancake.theaurorian.utils.MoonstoneHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public class MixinShearsItem {

    @Inject(method = "mineBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER))
    public void mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        ShearsItem shearsItem = (ShearsItem) (Object) this;
        if (shearsItem == TAItems.MOONSTONE_SICKLE.get()) {
            MoonstoneHelper.handleMoonstoneDurability(stack, livingEntity);
        }
    }

}