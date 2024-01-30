package cn.teampancake.theaurorian.common.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class KeepersBow extends BowItem implements ITooltipsItem {

    public KeepersBow() {
        super(new Properties().defaultDurability(512).rarity(Rarity.RARE));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack itemStack = player.getProjectile(stack);
            int i = this.getUseDuration(stack) - timeLeft;
            boolean hasAmmo = !itemStack.isEmpty() || flag;
            i = ForgeEventFactory.onArrowLoose(stack, level, player, i, hasAmmo);
            if (i > 0 && hasAmmo) {
                if (itemStack.isEmpty()) {
                    itemStack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (f >= 1.0D) {
                    boolean flag1 = player.getAbilities().instabuild || (itemStack.getItem() instanceof ArrowItem arrowItem && arrowItem.isInfinite(itemStack, stack, player));
                    if (!level.isClientSide()) {
                        ArrowItem arrowItem = itemStack.getItem() instanceof ArrowItem arrow ? arrow : (ArrowItem) Items.ARROW;
                        for (int j = -1; j < 2; j++) {
                            double angle = j * 10.0D * ((float)Math.PI / 180.0D);
                            Vec3 upVector = player.getUpVector(1.0F);
                            Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(angle, upVector.x, upVector.y, upVector.z);
                            Vector3f vector3f = player.getViewVector(1.0F).toVector3f().rotate(quaternionf);
                            AbstractArrow abstractArrow = arrowItem.createArrow(level, itemStack, player);
                            abstractArrow.shoot(vector3f.x(), vector3f.y(), vector3f.z(), f * 3.0F, 1.0F);
                            if (j != 0 || flag1 || player.getAbilities().instabuild && (itemStack.getItem() == Items.SPECTRAL_ARROW || itemStack.getItem() == Items.TIPPED_ARROW)) {
                                abstractArrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }

                            abstractArrow.setCritArrow(f == 1.0F);
                            int p = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                            if (p > 0) {
                                abstractArrow.setBaseDamage(abstractArrow.getBaseDamage() + p * 0.5D + 0.5D);
                            }

                            int k = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                            if (k > 0) {
                                abstractArrow.setKnockback(k);
                            }

                            if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                                abstractArrow.setSecondsOnFire(100);
                            }

                            level.addFreshEntity(abstractArrow);
                        }

                        stack.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(player.getUsedItemHand()));
                    }

                    float pitch = 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F;
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, pitch);
                    if (!flag1 && !player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                        if (itemStack.isEmpty()) {
                            player.getInventory().removeItem(itemStack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

}