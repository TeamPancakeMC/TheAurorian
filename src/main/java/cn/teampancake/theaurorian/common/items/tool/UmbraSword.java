package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;

public class UmbraSword extends SwordItem {

    public UmbraSword() {
        super(TAToolTiers.UMBRA, TAItemProperties.get().rarity(Rarity.EPIC)
                .attributes(createAttributes(TAToolTiers.UMBRA, (7), (1.6F)))
                .addItemTag(ItemTags.SWORDS, TAItemTags.IS_EPIC).hasTooltips());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        player.getCooldowns().addCooldown(this, 900);
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
            itemInHand.hurtAndBreak(20, player, LivingEntity.getSlotForHand(usedHand));
        }

        return InteractionResultHolder.success(itemInHand);
    }

}