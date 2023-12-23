package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.config.AurorianConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

public class AbsorptionOrbItem extends Item implements ITooltipsItem {

    public AbsorptionOrbItem(){
        super(new Properties()
                .rarity(Rarity.EPIC)
                .defaultDurability(250));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity entity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, entity, pSlotId, pIsSelected);
        if (entity instanceof ServerPlayer p) {
            if (p.getItemInHand(InteractionHand.OFF_HAND).getItem() == this) {
                ItemStack offhand = p.getItemInHand(InteractionHand.OFF_HAND);
                ItemStack mainhand = p.getItemInHand(InteractionHand.MAIN_HAND);
                switch (AurorianConfig.Config_OrbOfAbsorptionWhitelistBlacklist.get()) {
                    default -> {
                        if (mainhand.isDamageableItem() && mainhand.isDamageableItem()) {
                            if (!p.isCreative()) {
                                offhand.hurt(1, pLevel.random, p);
                            }
                            mainhand.setDamageValue(mainhand.getDamageValue() - 1);
                        }
                    }
                    case 1 -> {
                        for (String i : AurorianConfig.Config_OrbOfAbsorptionList.get()) {
                            if (mainhand.getItem().getDescriptionId().equals(i)) {
                                if (mainhand.isDamageableItem() && mainhand.isDamaged()) {
                                    if (!p.isCreative()) {
                                        offhand.hurt(1, pLevel.random, p);
                                    }
                                    mainhand.setDamageValue(mainhand.getDamageValue() - 1);
                                    return;
                                }
                            }
                        }
                    }
                    case 2 -> {
                        for (String i : AurorianConfig.Config_OrbOfAbsorptionList.get()) {
                            if (mainhand.getItem().getDescriptionId().equals(i))
                                return;
                        }
                        if (mainhand.isDamageableItem() && mainhand.isDamaged()) {
                            if (!p.isCreative()) {
                                offhand.hurt(1, pLevel.random, p);

                            }
                            mainhand.setDamageValue(mainhand.getDamageValue() - 1);
                        }
                    }
                }
            }
        }
    }

}