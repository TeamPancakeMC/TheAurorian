package cn.teampancake.theaurorian.common.items.shield;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MoonStoneShield extends ShieldItem {
    public MoonStoneShield() {
        super(new Properties().durability(512));
    }


    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        Level level = event.getEntity().level();
        boolean bool = level.random.nextInt(100) < 50;
        LivingEntity entity = event.getEntity();
        ItemStack offhandItem = entity.getOffhandItem();
        if (offhandItem.getItem() instanceof MoonStoneShield) {
            if (level.isNight() && bool) {
                return;
            }
            event.setBlockedDamage(event.getBlockedDamage() * 0.5f);
        }
    }

}
