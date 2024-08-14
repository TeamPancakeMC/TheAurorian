package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TremorEffect extends TAMobEffect {

    public TremorEffect() {
        super(MobEffectCategory.HARMFUL, 0x81663b);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, TheAurorian.prefix("tremor_ms"), -0.15F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    public void mainOffhandDrop(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            Inventory inventory = player.getInventory();
            ItemStack mainHand = inventory.getSelected();
            ItemStack offHand = inventory.offhand.getFirst();
            if (!mainHand.isEmpty()) {
                player.drop(mainHand.copy(), false);
                mainHand.shrink(mainHand.getCount());
            }
            if (!offHand.isEmpty()) {
                player.drop(offHand.copy(), false);
                offHand.shrink(offHand.getCount());
            }
        }
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        this.mainOffhandDrop(livingEntity);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

}