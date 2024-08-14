package cn.teampancake.theaurorian.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class LivingDiviningRod extends Item implements ITooltipsItem {

    public LivingDiviningRod() {
        super(new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.getCooldowns().addCooldown(this, 120);
        if (!level.isClientSide) {
            BlockPos blockPos = player.getOnPos();
            level.getEntitiesOfClass(LivingEntity.class, (new AABB(blockPos)).inflate(18.0D)).stream()
                    .filter(entity -> entity.isAlive() && !entity.isRemoved())
                    .filter(entity -> blockPos.closerToCenterThan(entity.position(), 18.0D))
                    .forEach(entity -> entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120)));
        }

        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }

}