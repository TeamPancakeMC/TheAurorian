package cn.teampancake.theaurorian.common.items.shield;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CrystallineShield extends ShieldItem implements ITooltipsItem {
    public CrystallineShield() {
        super(new Properties()
                .rarity(Rarity.EPIC)
                .defaultDurability(512));
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 100;
    }


    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack offhandItem = entity.getOffhandItem();
        if (offhandItem.getItem() instanceof CrystallineShield) {
            if (entity instanceof ServerPlayer player) {
                ItemStack mainhand = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (mainhand.getDamageValue() < mainhand.getMaxDamage() && mainhand.isDamageableItem()) {
                    mainhand.setDamageValue(mainhand.getDamageValue() - 1);
                }
                player.getCooldowns().addCooldown(offhandItem.getItem(), 20);
            }
        }
    }
}
