package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.registry.TAItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.NotNull;

public class CeruleanShield extends ShieldItem{
    public CeruleanShield() {
        super(new Properties().defaultDurability(512));
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack ToRepair, ItemStack repair) {
        return repair.getItem() == TAItems.CERULEAN_INGOT.get();
    }
}
