package cn.teampancake.theaurorian.common.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TremorEffect extends TAMobEffect{
    public TremorEffect() {
        super(MobEffectCategory.HARMFUL, 0x81663b);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "D0F744C3-4AB0-4A1E-8033-3EA3F2B55899", -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public void mainOffhandDrop(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            Inventory inventory = player.getInventory();
            ItemStack mainHand = inventory.getSelected();
            ItemStack offHand = inventory.offhand.get(0);
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
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        mainOffhandDrop(pLivingEntity);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
