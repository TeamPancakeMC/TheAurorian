package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class SimpleThrowProjectProjectile extends Item {

    private final SoundEvent soundEvent;
    private final SoundSource soundSource;
    private final Supplier<EntityType<?>> projectile;
    float velocity;
    float inaccuracy;

    public SimpleThrowProjectProjectile(Item.Properties properties, SoundEvent soundEvent, SoundSource soundSource, Supplier<EntityType<?>> projectile, float velocity, float inaccuracy) {
        super(properties.component(TADataComponents.ITEM_TAGS, List.of(TAItemTags.IS_RARE))
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE));
        this.soundEvent = soundEvent;
        this.soundSource = soundSource;
        this.projectile = projectile;
        this.velocity = velocity;
        this.inaccuracy = inaccuracy;
    }

    public SimpleThrowProjectProjectile(Item.Properties properties, SoundEvent soundEvent, SoundSource soundSource, Supplier<EntityType<?>> projectile, float velocity) {
        this(properties, soundEvent, soundSource, projectile, velocity, 0.4F / (new Random().nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        float pitch = 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), this.soundEvent, this.soundSource, 0.5F, pitch);
        if (!level.isClientSide && this.projectile.get().create(level) instanceof ThrowableItemProjectile itemProjectile) {
            itemProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, this.velocity, this.inaccuracy);
            itemProjectile.setItem(itemInHand);
            level.addFreshEntity(itemProjectile);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemInHand.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }

}