package cn.teampancake.theaurorian.common.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class DungeonLocatorItem extends Item implements ITooltipsItem{
    public DungeonLocatorItem() {
        super(new Properties()
                .defaultDurability(30)
                .stacksTo(1)
                .rarity(Rarity.EPIC));
    }

    private CompoundTag getNBT(ItemStack stack) {
        CompoundTag nbt = new CompoundTag();
        if (stack.hasTag()) {
            nbt = stack.getTag();
        }
        return nbt;
    }

    private String getSelectedDungeon(ItemStack stack) {
        String blockname = this.getNBT(stack).getString("dungeon");
        if (blockname.isEmpty()) {
            return "Runestone";
        } else {
            return blockname;
        }
    }

    private boolean setSelectedDungeon(ItemStack stack, String dungeon) {
        CompoundTag nbt = this.getNBT(stack);
        if (dungeon.isEmpty()) {
            nbt.putString("dungeon", "Runestone");
            return true;
        }
        if (!dungeon.equals(this.getSelectedDungeon(stack))) {
            nbt.putString("dungeon", dungeon);
            return true;
        }
        return false;
    }
}
