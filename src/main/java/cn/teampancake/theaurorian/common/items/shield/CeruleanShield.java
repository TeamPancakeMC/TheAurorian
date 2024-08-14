package cn.teampancake.theaurorian.common.items.shield;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.NotNull;

public class CeruleanShield extends ShieldItem implements ITooltipsItem {

    public CeruleanShield() {
        super(new Properties().durability(512));
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack ToRepair, ItemStack repair) {
        return repair.getItem() == TAItems.CERULEAN_INGOT.get();
    }

}
