package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.config.AurorianConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class UmbraSword extends SwordItem implements ITooltipsItem {

    public UmbraSword() {
        super(ModToolTiers.UMBRA, 7, 1.6f, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    @NotNull
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack itemInHand = context.getItemInHand();
        if (player != null && player.isShiftKeyDown()) {
            itemInHand.hurtAndBreak(20, player, (playerEntity) -> {
                playerEntity.broadcastBreakEvent(context.getHand());
            });

            int time = 120;
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, time, 4, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, time, 4, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, time, 2, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, time, 1, false, false));

            player.getCooldowns().addCooldown(this,     AurorianConfig.Config_UmbraSwordCooldown.get());

            if (level.isClientSide) {
                player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1f, 0.5f);
                player.playSound(SoundEvents.IRON_DOOR_OPEN, 1f, 0.25f);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}