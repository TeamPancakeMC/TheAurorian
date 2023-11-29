package cn.teampancake.theaurorian.common.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class KeepersBow extends BowItem implements ITooltipsItem {

    public KeepersBow() {
        super(new Properties()
                .defaultDurability(512)
                .rarity(Rarity.RARE));
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 40;
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity entityLiving, int pTimeLeft) {
        if (entityLiving instanceof Player) {
            for (int i = 0; i < 3; i++) {
                super.releaseUsing(pStack, pLevel, entityLiving, pTimeLeft);
            }
        }
    }

}
