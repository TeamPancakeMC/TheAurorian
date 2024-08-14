package cn.teampancake.theaurorian.common.items.developer;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.world.item.SwordItem;

public class TslatSword extends SwordItem implements IDeveloperItem, ITooltipsItem {

    public TslatSword() {
        super(TAToolTiers.TSLAT, new Properties().component(TADataComponents.KILL_COUNT, 0)
                .attributes(createAttributes(TAToolTiers.TSLAT, 3, 1.9F)));
    }

}