package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
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

public class StrangeMeat extends Item {

    public StrangeMeat() {
        super(TAItemProperties.get().rarity(Rarity.EPIC).durability(10)
                .food(new FoodProperties.Builder().nutrition(8).saturationModifier((0.9F)).alwaysEdible()
                        .build()).addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem());
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
                int select = level.random.nextInt(7);
                switch (select) {
                    case 0 -> player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400));
                    case 1 -> player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400));
                    case 2 -> player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400));
                    case 3 -> player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 2400));
                    case 4 -> player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2400));
                    case 5 -> player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 2400));
                    case 6 -> player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2400));
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 64;
    }

}