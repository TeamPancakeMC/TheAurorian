package cn.teampancake.theaurorian.common.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SimpleThrowProjectProjectile extends Item implements ITooltipsItem{
    private SoundEvent soundEvent;
    private SoundSource soundSource;
    private EntityType<?> projectile;
    float pVelocity;
    float pInaccuracy;

    public SimpleThrowProjectProjectile(Properties pProperties, SoundEvent soundEvent, SoundSource soundSource, EntityType<?> projectile, float pVelocity, float pInaccuracy) {
        super(pProperties);
        this.soundEvent = soundEvent;
        this.soundSource = soundSource;
        this.projectile = projectile;
        this.pVelocity = pVelocity;
        this.pInaccuracy = pInaccuracy;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW,
                SoundSource.NEUTRAL, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            Entity entity = projectile.create(level);
            if (entity instanceof ThrowableItemProjectile projectile) {
                projectile.setItem(stack);
                projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, pInaccuracy, pInaccuracy);
            }

        }
        return InteractionResult.SUCCESS;
    }

}
