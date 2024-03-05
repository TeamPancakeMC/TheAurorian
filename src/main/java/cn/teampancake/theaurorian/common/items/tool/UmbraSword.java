package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;

public class UmbraSword extends SwordItem implements ITooltipsItem {

    public UmbraSword() {
        super(TAToolTiers.UMBRA, 7, 1.6f, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        player.getCooldowns().addCooldown(this, AurorianConfig.Config_UmbraSwordCooldown.get());
        int time = 120;
        if (level.isClientSide) {
            player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1f, 0.5f);
            player.playSound(SoundEvents.IRON_DOOR_OPEN, 1f, 0.25f);
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, time, 4, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, time, 4, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, time, 2, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, time, 1, false, false));
        }

        if (!player.getAbilities().instabuild) {
            itemInHand.hurtAndBreak(20, player, (playerEntity) -> playerEntity.broadcastBreakEvent(usedHand));
        }

        return InteractionResultHolder.success(itemInHand);
    }

}