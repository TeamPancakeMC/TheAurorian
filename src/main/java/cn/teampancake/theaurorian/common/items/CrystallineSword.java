package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class CrystallineSword extends SwordItem implements ITooltipsItem {

    public CrystallineSword() {
        super(TAToolTiers.CRYSTALLINE, 3, -2.4F, new Properties().rarity(Rarity.EPIC).defaultDurability(512));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player){
            Vec3 lookAngle = player.getLookAngle();
            float pitch = 1.0F / (level.random.nextFloat() * 0.4F + 1.2F) + 0.5F;
            level.playSound(null, player.getOnPos(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, pitch);
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
            Arrow arrow = TAEntityTypes.CRYSTALLINE_BEAM.get().create(level);
            if (arrow != null) {
                arrow.setPos(player.getX(), player.getY() + 1.5, player.getZ());
                arrow.shoot(lookAngle.x, lookAngle.y, lookAngle.z,3.0F, 1.0F);
                arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                arrow.setNoGravity(true);
                arrow.setKnockback(1);
                level.addFreshEntity(arrow);
            }
        }
        return stack;
    }


    @Override
    public int getUseDuration(ItemStack pStack) {
        return 20 * 2;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

}