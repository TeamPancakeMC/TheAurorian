package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.utils.AurorianSteelHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class AurorianSteelSword extends SwordItem implements ITooltipsItem {

    public AurorianSteelSword() {
        super(TAToolTiers.AURORIAN_STEEL, (3), (-2.4F), new Item.Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        AurorianSteelHelper.handleAurorianSteelDurability(stack);
        return true;
    }

}