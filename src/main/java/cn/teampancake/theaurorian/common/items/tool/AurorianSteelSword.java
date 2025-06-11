package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;

import java.util.List;

public class AurorianSteelSword extends SwordItem {

    public AurorianSteelSword() {
        super(TAToolTiers.AURORIAN_STEEL, new Item.Properties()
                .attributes(createAttributes(TAToolTiers.AURORIAN_STEEL, 3, -2.4F))
                .component(TADataComponents.ITEM_TAGS, List.of(ItemTags.SWORDS, TAItemTags.IS_EPIC))
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            if (this.isEasterEggPlayer(player)) {
                target.hurt(target.damageSources().playerAttack(player), 1202.0F);
                if (!player.level().isClientSide()) {
                    String key = "messages." + this.getDescriptionId() + ".uuz.1";
                    player.displayClientMessage(Component.translatable(key, player.getName()), true);
                }
            } else if (player.hasEffect(TAMobEffects.HOLINESS)) {
                this.applyExtraDamage(target, player);
                if (target.isDeadOrDying()) {
                    this.extendHolinessEffect(player, this.isUndead(target));
                }
            }

            if (!player.getAbilities().instabuild) {
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(attacker.getUsedItemHand()));
            }
        }

        return true;
    }

    private boolean isEasterEggPlayer(Player player) {
        return player.getStringUUID().equals("6b0f5181-a732-4ee2-b53a-c5a05d6af32e");
    }

    private void applyExtraDamage(LivingEntity target, Player player) {
        float extraDamage = this.isUndead(target) ? 2.0F : 1.0F;
        target.hurt(target.damageSources().playerAttack(player), extraDamage);
    }

    private void extendHolinessEffect(Player player, boolean isUndead) {
        MobEffectInstance currentEffect = player.getEffect(TAMobEffects.HOLINESS);
        if (currentEffect != null) {
            int extraDuration = isUndead ? 60 : 30;
            currentEffect.duration += extraDuration;
        }
    }

    private boolean isUndead(LivingEntity entity) {
        return entity.getType().is(EntityTypeTags.UNDEAD);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        if (this.isEasterEggPlayer(player) && !level.isClientSide) {
            String key = "messages." + this.getDescriptionId() + ".uuz.2";
            player.displayClientMessage(Component.translatable(key, player.getName()), false);
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 2));
            player.addEffect(new MobEffectInstance(TAMobEffects.HOLINESS, 400));
            return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
        }

        player.getCooldowns().addCooldown(this, 400);
        if (!level.isClientSide) {
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
            player.addEffect(new MobEffectInstance(TAMobEffects.HOLINESS, 200));
            if (player instanceof ServerPlayer serverPlayer) {
                String messageKey = "messages." + this.getDescriptionId() + ".holiness";
                serverPlayer.sendSystemMessage(Component.translatable(messageKey));
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }

}