package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public class AurorianSteelSword extends SwordItem {

    public AurorianSteelSword() {
        super(TAToolTiers.AURORIAN_STEEL, new Item.Properties()
                .attributes(createAttributes(TAToolTiers.AURORIAN_STEEL, (3), (-2.4F)))
                .component(TADataComponents.ITEM_TAGS, List.of(ItemTags.SWORDS, TAItemTags.IS_EPIC))
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // 检查攻击者是否有神圣效果
        if (attacker instanceof Player player && hasHolinessEffect(player)) {
            // 对亡灵生物额外+2伤害，其他生物+1伤害
            float extraDamage = isUndead(target) ? 2.0F : 1.0F;
            if (extraDamage > 0) {
                target.hurt(target.damageSources().playerAttack(player), extraDamage);
            }
        }

        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    // 检查是否有神圣效果
    private boolean hasHolinessEffect(Player player) {
        for (MobEffectInstance effect : player.getActiveEffects()) {
            if (effect.is(TAMobEffects.HOLINESS)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否为亡灵生物
    private boolean isUndead(LivingEntity entity) {
        return entity.getType().is(EntityTypeTags.UNDEAD);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        String message = "messages." + this.getDescriptionId() + ".holiness";
        ItemStack itemInHand = player.getItemInHand(usedHand);
        player.getCooldowns().addCooldown(this, 700);
        if (!level.isClientSide) {
            // 使用原版效果 - 获取实际的MobEffect实例
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));

            // 使用模组效果 - 获取实际的MobEffect实例
            player.addEffect(new MobEffectInstance(TAMobEffects.HOLINESS, 200));

            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.sendSystemMessage(Component.translatable(message));
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }
}