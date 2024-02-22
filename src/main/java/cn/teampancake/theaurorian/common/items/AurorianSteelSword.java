package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.utils.AurorianSteelHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class AurorianSteelSword extends SwordItem implements ITooltipsItem {

    public AurorianSteelSword() {
        super(TAToolTiers.AURORIAN_STEEL, (3), (-2.4F), new Item.Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, entity -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        AurorianSteelHelper.handleAurorianSteelDurability(stack);
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide) {
            CompoundTag compound = stack.getOrCreateTag();
            int cooldown = compound.getInt("HolinessCooldown");
            if (cooldown > 0) {
                compound.putInt("HolinessCooldown", cooldown - 1);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        String prefix = "messages." + this.getDescriptionId() + ".holiness.";
        ItemStack itemInHand = player.getItemInHand(usedHand);
        if (!level.isClientSide) {
            CompoundTag compound = itemInHand.getOrCreateTag();
            int cooldown = compound.getInt("HolinessCooldown");
            boolean canUse = cooldown == 0 || player.getAbilities().instabuild;
            if (canUse) {
                player.addEffect(new MobEffectInstance(TAMobEffects.HOLINESS.get(), 200));
                compound.putInt("HolinessCooldown", 700);
            }

            if (player instanceof ServerPlayer serverPlayer) {
                String message = prefix + (canUse ? "success" : "fail");
                serverPlayer.sendSystemMessage(Component.translatable(message));
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        String message = "tooltips." + stack.getItem().getDescriptionId() + ".holiness.cooldown";
        int cooldown = stack.getOrCreateTag().getInt("HolinessCooldown");
        if (cooldown > 0) {
            int secTime = Mth.floor(cooldown / 20.0F);
            tooltipComponents.add(Component.translatable(message, secTime));
        }
    }

}