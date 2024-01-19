package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;


public class AurorianCrystal extends Item implements ITooltipsItem{
    public AurorianCrystal() {
        super(new Item.Properties().durability(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        System.out.println("finishUsingItem");
        if (pLivingEntity instanceof ServerPlayer serverPlayer) {

            MinecraftServer server = serverPlayer.getServer();
            ResourceKey<Level> dimension = serverPlayer.level().dimension();
            ServerLevel aurorian = server.getLevel(TADimensions.AURORIAN_DIMENSION);
            ServerLevel overworld = server.getLevel(Level.OVERWORLD);

            if (dimension == TADimensions.AURORIAN_DIMENSION) {
                serverPlayer.teleportTo(overworld, 0, 100, 0, serverPlayer.getYRot(), serverPlayer.getXRot());
            }
            if (dimension == Level.OVERWORLD) {
                serverPlayer.teleportTo(aurorian, 0, 100, 0, serverPlayer.getYRot(), serverPlayer.getXRot());
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        System.out.println("releaseUsing");
        super.releaseUsing(stack, level, livingEntity, timeLeft);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 20 * 2;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

}
