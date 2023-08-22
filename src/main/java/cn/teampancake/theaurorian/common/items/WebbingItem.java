package cn.teampancake.theaurorian.common.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class WebbingItem extends Item {

    public WebbingItem() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemInHand = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW,
                net.minecraft.sounds.SoundSource.NEUTRAL, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            //TODO: WebbingEntity
        }
        return InteractionResult.SUCCESS;
    }
}