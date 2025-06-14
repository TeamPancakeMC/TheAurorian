package cn.teampancake.theaurorian.common.items.tool;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class KeepersBow extends BowItem {

    public KeepersBow(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            ItemStack projectile = player.getProjectile(stack);
            if (!projectile.isEmpty()) {
                int i = this.getUseDuration(stack, entityLiving) - timeLeft;
                i = EventHooks.onArrowLoose(stack, level, player, i, !projectile.isEmpty());
                if (i < 0) return;
                float f = getPowerForTime(i);
                if (f >= 0.1D) {
                    List<ItemStack> list = modifiedDraw(stack, projectile, player);
                    float pitch = 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F;
                    if (level instanceof ServerLevel serverLevel && !list.isEmpty()) {
                        this.shoot(serverLevel, player, player.getUsedItemHand(), stack, list, f * 3.0F, 2.5F, f == 1.0F, null);
                    }

                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, pitch);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    protected void shoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, List<ItemStack> projectileItems,
                         float velocity, float inaccuracy, boolean isCrit, @Nullable LivingEntity target) {
        float f = EnchantmentHelper.processProjectileSpread(level, weapon, shooter, 0.0F);
        float f1 = projectileItems.size() == 1 ? 0.0F : 2.0F * f / (float)(projectileItems.size() - 1);
        float f2 = (float)((projectileItems.size() - 1) % 2) * f1 / 2.0F;
        float f3 = 1.0F;
        for (int i = 0; i < projectileItems.size(); i++) {
            ItemStack itemStack = projectileItems.get(i);
            if (!itemStack.isEmpty()) {
                float f4 = f2 + f3 * (float)((i + 1) / 2) * f1;
                f3 = -f3;
                Projectile projectile = this.createProjectile(level, shooter, weapon, itemStack, isCrit);
                if (projectile instanceof AbstractArrow abstractArrow) {
                    abstractArrow.setCritArrow(isCrit);
                    if (i < projectileItems.size() / 3) {
                        if (shooter instanceof Player player && !player.getAbilities().instabuild) {
                            abstractArrow.pickup = AbstractArrow.Pickup.ALLOWED;
                        }
                    } else {
                        abstractArrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }
                }

                this.shootProjectile(shooter, projectile, i, velocity, inaccuracy, f4, target);
                level.addFreshEntity(projectile);
                weapon.hurtAndBreak(this.getDurabilityUse(itemStack), shooter, LivingEntity.getSlotForHand(hand));
                if (weapon.isEmpty()) {
                    break;
                }
            }
        }
    }

    protected static List<ItemStack> modifiedDraw(ItemStack weapon, ItemStack ammo, LivingEntity shooter) {
        if (ammo.isEmpty()) {
            return List.of();
        } else {
            int i = shooter.level() instanceof ServerLevel serverLevel ? EnchantmentHelper.processProjectileCount(serverLevel, weapon, shooter, 1) : 1;
            List<ItemStack> list = new ArrayList<>(i);
            ItemStack itemStack = ammo.copy();
            for (int j = 0; j < i * 3; j++) {
                ItemStack useAmmo = useAmmo(weapon, j == 0 ? ammo : itemStack, shooter, j > 0);
                if (!useAmmo.isEmpty()) {
                    list.add(useAmmo);
                }
            }

            return list;
        }
    }

}