package cn.teampancake.theaurorian.common.items.shield;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;

public class CrystallineShield extends ShieldItem implements ITooltipsItem {

    public CrystallineShield() {
        super(new Properties().rarity(Rarity.EPIC).durability(512));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 100;
    }

}