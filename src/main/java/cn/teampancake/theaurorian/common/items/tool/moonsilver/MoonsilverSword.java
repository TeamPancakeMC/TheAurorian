package cn.teampancake.theaurorian.common.items.tool.moonsilver;

import cn.teampancake.theaurorian.common.items.tool.GeoHandheldToolRenderer;
import cn.teampancake.theaurorian.common.registry.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;

import java.util.function.Consumer;

public class MoonsilverSword extends SwordItem implements GeoItem {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public MoonsilverSword(Properties properties) {
        super(TAToolTiers.MOONSILVER, properties);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoHandheldToolRenderer<MoonsilverSword>(TAItems.MOONSILVER_SWORD.getId()));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
        if (damageSource.getEntity() instanceof Player player && player.hasEffect(TAMobEffects.HOLINESS)) {
            return target.getType().is(EntityTypeTags.UNDEAD) ? 2.0F : 1.0F;
        }

        return 0.0F;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            if (!player.level().isClientSide() && this.isEasterEggPlayer(player)) {
                target.hurt(target.damageSources().playerAttack(player), 1202.0F);
                String key = "messages." + this.getDescriptionId() + ".uuz.1";
                player.displayClientMessage(Component.translatable(key, player.getName()), true);
            }

            if (!player.getAbilities().instabuild) {
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
            }
        }

        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        if (this.isEasterEggPlayer(player) && !level.isClientSide) {
            String key = "messages." + this.getDescriptionId() + ".uuz.2";
            player.displayClientMessage(Component.translatable(key, player.getName()), false);
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 2));
            player.addEffect(new MobEffectInstance(TAMobEffects.HOLINESS, 400));
        }

        if (!level.isClientSide) {
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
            player.addEffect(new MobEffectInstance(TAMobEffects.HOLINESS, 200));
            if (player instanceof ServerPlayer serverPlayer) {
                String messageKey = "messages." + this.getDescriptionId() + ".holiness";
                serverPlayer.sendSystemMessage(Component.translatable(messageKey));
            }
        }

        player.getCooldowns().addCooldown(this, 400);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }

    private boolean isEasterEggPlayer(Player player) {
        return player.getStringUUID().equals("6b0f5181-a732-4ee2-b53a-c5a05d6af32e");
    }

}