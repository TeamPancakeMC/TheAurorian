package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.entities.projectiles.CrystallineBeamEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CrystallineSword extends SwordItem implements ITooltipsItem{
    public CrystallineSword() {
        super(ModToolTiers.CRYSTALLINE, 3, -2.4F, new Properties().rarity(Rarity.EPIC).defaultDurability(512));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            int i = this.getUseDuration(stack) - timeLeft;

            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack,level,player, i, true);
            if (i < 20) {
                return;
            }

            if (!level.isClientSide) {
                CrystallineBeamEntity entity_tipped_arrow = new CrystallineBeamEntity(level, player);
                entity_tipped_arrow.shoot(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z,1.4F, 1.0F);
                stack.hurt(1,level.random ,(ServerPlayer) player);
                level.addFreshEntity(entity_tipped_arrow);
            }

            level.playSound(null, player.getX(),player.getY(),player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, 1.0F / (level.random.nextFloat() * 0.4F + 1.2F) + 1 * 0.5F);
        }
    }

}
