package cn.teampancake.theaurorian.common.items;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class StrangeMeat extends Item {

    private static final List<Holder<MobEffect>> EFFECT_LIST = List.of(
            MobEffects.FIRE_RESISTANCE, MobEffects.DAMAGE_BOOST, MobEffects.ABSORPTION, MobEffects.REGENERATION,
            MobEffects.DAMAGE_RESISTANCE, MobEffects.DIG_SPEED, MobEffects.MOVEMENT_SPEED);

    public StrangeMeat(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        FoodProperties foodProperties = stack.getFoodProperties(player);
        if (player != null && foodProperties != null) {
            float pitch = level.random.nextFloat() * 0.1F + 0.9F;
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, pitch);
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
            player.eat(level, stack, foodProperties);
            if (stack.getDamageValue() == stack.getMaxDamage()) {
                int select = level.random.nextInt(EFFECT_LIST.size());
                player.addEffect(new MobEffectInstance(EFFECT_LIST.get(select), 2400));
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 64;
    }

}