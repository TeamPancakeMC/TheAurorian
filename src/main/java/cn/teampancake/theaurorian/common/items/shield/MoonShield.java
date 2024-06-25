package cn.teampancake.theaurorian.common.items.shield;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MoonShield extends ShieldItem implements ITooltipsItem {
    public MoonShield() {
        super(new Properties()
                .rarity(Rarity.EPIC)
                .defaultDurability(512));
    }
    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Item item = livingEntity.getUseItem().getItem();
        Entity entity = event.getDamageSource().getEntity();
        if (item == TAItems.MOON_SHIELD.get()) {
            if (entity != null && livingEntity instanceof LivingEntity) {
                entity.setPos(entity.getX(), entity.getY() + 5, entity.getZ());
            }
        }
    }
}
