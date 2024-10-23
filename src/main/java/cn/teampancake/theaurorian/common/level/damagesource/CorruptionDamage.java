package cn.teampancake.theaurorian.common.level.damagesource;

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

import java.util.HashMap;
import java.util.Map;

public class CorruptionDamage extends DamageSource {

    public CorruptionDamage(Holder<DamageType> type) {
        super(type);
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity livingEntity) {
        boolean flag = livingEntity instanceof ServerPlayer || livingEntity.hasCustomName();
        if (this.typeHolder().is(TADamageTypes.CORRUPTION) && flag) {
            Map<Integer, Integer> styles = new HashMap<>();
            styles.put(1, ChatFormatting.DARK_RED.getColor());
            styles.put(2, DyeColor.CYAN.getTextColor());
            styles.put(3, DyeColor.PINK.getTextColor());
            RandomSource random = livingEntity.getRandom();
            int easterEgg = random.nextBoolean() ? 2 : 3;
            int index = random.nextInt(10) < 9 ? 1 : easterEgg;
            String s = "death.attack.corruption_" + index;
            Style style = Style.EMPTY.withColor(styles.get(index));
            return Component.translatable(s, livingEntity.getDisplayName()).setStyle(style);
        }

        return super.getLocalizedDeathMessage(livingEntity);
    }

}