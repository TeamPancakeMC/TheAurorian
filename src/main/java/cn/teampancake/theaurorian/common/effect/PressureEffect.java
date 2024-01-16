package cn.teampancake.theaurorian.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PressureEffect extends TAMobEffect {

    public PressureEffect() {
        super(MobEffectCategory.HARMFUL, 0x714bdb);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of();
    }

}