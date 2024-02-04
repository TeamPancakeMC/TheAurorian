package cn.teampancake.theaurorian.common.items.developer;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public class StarOceanCrossbow extends CrossbowItem implements IDeveloperItem, ITooltipsItem {

    public StarOceanCrossbow() {
        super(new Item.Properties().durability(500));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (isCharged(itemInHand)) {
            performShooting(level, player, hand, itemInHand, getShootingPower(itemInHand));
            setCharged(itemInHand, Boolean.FALSE);
            return InteractionResultHolder.consume(itemInHand);
        } else if (!player.getProjectile(itemInHand).isEmpty()) {
            if (!isCharged(itemInHand)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                player.startUsingItem(hand);
            }

            return InteractionResultHolder.consume(itemInHand);
        } else {
            return InteractionResultHolder.fail(itemInHand);
        }
    }

    private static void shootProjectile(Level level, LivingEntity shooter, InteractionHand hand, ItemStack crossbowStack, ItemStack ammoStack, float soundPitch, float velocity, float projectileAngle) {
        boolean isCreativeMode = shooter instanceof Player player && player.getAbilities().instabuild;
        if (!level.isClientSide) {
            Projectile projectile;
            if (ammoStack.is(Items.FIREWORK_ROCKET)) {
                double y = shooter.getEyeY() - (double)0.15F;
                projectile = new FireworkRocketEntity(level, ammoStack, shooter, shooter.getX(), y, shooter.getZ(), true);
            } else {
                projectile = getArrow(level, shooter);
                if (isCreativeMode || projectileAngle != 0.0F) {
                    ((AbstractArrow)projectile).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
            }

            if (shooter instanceof CrossbowAttackMob crossbowAttackMob) {
                LivingEntity target = crossbowAttackMob.getTarget();
                if (target != null) {
                    crossbowAttackMob.shootCrossbowProjectile(target, crossbowStack, projectile, projectileAngle);
                }
            } else {
                double angle = projectileAngle * ((float)Math.PI / 180.0F);
                Vec3 vec31 = shooter.getUpVector(1.0F);
                Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(angle, vec31.x, vec31.y, vec31.z);
                Vector3f vector3f = shooter.getViewVector(1.0F).toVector3f().rotate(quaternionf);
                projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), velocity, (float) 1.0);
            }

            crossbowStack.hurtAndBreak(1, shooter, (living) -> living.broadcastBreakEvent(hand));
            level.addFreshEntity(projectile);
            level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, soundPitch);
        }
    }

    private static AbstractArrow getArrow(Level level, LivingEntity livingEntity) {
        AbstractArrow abstractArrow = new SpectralArrow(level, livingEntity);
        abstractArrow.setCritArrow(livingEntity instanceof Player);
        abstractArrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractArrow.setShotFromCrossbow(true);
        abstractArrow.setPierceLevel((byte)3);
        return abstractArrow;
    }

    public static void performShooting(Level level, LivingEntity shooter, InteractionHand usedHand, ItemStack crossbowStack, float velocity) {
        List<ItemStack> list = getChargedProjectiles(crossbowStack);
        float[] pitches = getShotPitches(shooter.getRandom());
        float[] angles = new float[] {0.0F, -10.0F, 10.0F};
        for (int i = 0; i < list.size(); ++i) {
            ItemStack itemStack = list.get(i);
            if (!itemStack.isEmpty()) {
                shootProjectile(level, shooter, usedHand, crossbowStack, itemStack, pitches[i], velocity, angles[i]);
            }
        }

        onCrossbowShot(level, shooter, crossbowStack);
    }

}