package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

public class CrystallineSword extends SwordItem implements ITooltipsItem {

    public CrystallineSword() {
        super(TAToolTiers.CRYSTALLINE, 3, -2.4F, new Properties().rarity(Rarity.EPIC).defaultDurability(512));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (livingEntity instanceof Player player) {
            Vec3 lookAngle = player.getLookAngle();
            float pitch = 1.0F / (level.random.nextFloat() * 0.4F + 1.2F) + 0.5F;
            level.playSound(null, player.getOnPos(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, pitch);
            if (this.getUseDuration(stack) - timeCharged >= 20 && !level.isClientSide) {
                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
                Arrow arrow = TAEntityTypes.CRYSTALLINE_BEAM.get().create(level);
                if (arrow != null) {
                    arrow.shoot(lookAngle.x, lookAngle.y, lookAngle.z,3.0F, 1.0F);
                    arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    arrow.setNoGravity(true);
                    arrow.setKnockback(1);
                    level.addFreshEntity(arrow);
                }
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

}