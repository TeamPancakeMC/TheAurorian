package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TADamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(DamageSource.class)
public class MixinDamageSource {

    @Shadow @Final private Holder<DamageType> type;

    @Inject(method = "getLocalizedDeathMessage", at = @At(value = "HEAD"), cancellable = true)
    public void getLocalizedDeathMessage(LivingEntity livingEntity, CallbackInfoReturnable<Component> cir) {
        if (this.type.is(TADamageTypes.CORRUPTION) && (livingEntity instanceof ServerPlayer || livingEntity.hasCustomName())) {
            Map<Integer, Integer> styles = new HashMap<>();
            styles.put(1, ChatFormatting.DARK_RED.getColor());
            styles.put(2, DyeColor.CYAN.getTextColor());
            styles.put(3, DyeColor.PINK.getTextColor());
            RandomSource random = livingEntity.getRandom();
            int easterEgg = random.nextBoolean() ? 2 : 3;
            int index = random.nextInt(10) < 9 ? 1 : easterEgg;
            String s = "death.attack.corruption_" + index;
            Style style = Style.EMPTY.withColor(styles.get(index));
            cir.setReturnValue(Component.translatable(s, livingEntity.getDisplayName()).setStyle(style));
        }
    }

}