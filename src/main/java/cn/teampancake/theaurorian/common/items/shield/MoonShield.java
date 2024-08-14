package cn.teampancake.theaurorian.common.items.shield;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;

public class MoonShield extends ShieldItem implements ITooltipsItem {

    public MoonShield() {
        super(new Properties().rarity(Rarity.EPIC).durability(512));
    }

}