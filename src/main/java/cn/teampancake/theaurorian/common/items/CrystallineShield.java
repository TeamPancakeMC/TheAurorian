package cn.teampancake.theaurorian.common.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;

public class CrystallineShield extends ShieldItem implements ITooltipsItem{
    public CrystallineShield() {
        super(new Properties()
                .rarity(Rarity.EPIC)
                .defaultDurability(512));
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 100;
    }

}
