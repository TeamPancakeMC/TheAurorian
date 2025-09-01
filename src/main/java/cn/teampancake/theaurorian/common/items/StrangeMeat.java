package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class StrangeMeat extends Item {

    private static final List<Holder<MobEffect>> EFFECT_LIST = List.of(
            MobEffects.FIRE_RESISTANCE, MobEffects.DAMAGE_BOOST, MobEffects.ABSORPTION, MobEffects.REGENERATION,
            MobEffects.DAMAGE_RESISTANCE, MobEffects.DIG_SPEED, MobEffects.MOVEMENT_SPEED);

    public StrangeMeat() {
        super(new Item.Properties().rarity(Rarity.EPIC).durability(10)
                .food(new FoodProperties.Builder().nutrition(8).saturationModifier((0.9F)).alwaysEdible().build())
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE));
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