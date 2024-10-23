package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
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

public class AurorianCrystal extends Item {

    public AurorianCrystal() {
        super(TAItemProperties.get().durability(1).addItemTag(TAItemTags.IS_LEGENDARY).hasTooltips().isSimpleModelItem());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemInHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof ServerPlayer serverPlayer) {
            MinecraftServer server = serverPlayer.getServer();
            if (server != null) {
                ResourceKey<Level> dimension = serverPlayer.level().dimension();
                ServerLevel overworld = server.getLevel(Level.OVERWORLD);
                ServerLevel aurorian = server.getLevel(TADimensions.AURORIAN_DIMENSION);
                if (overworld != null && dimension == TADimensions.AURORIAN_DIMENSION) {
                    serverPlayer.teleportTo(overworld, 0, 100, 0, serverPlayer.getYRot(), serverPlayer.getXRot());
                }
                if (aurorian != null && dimension == Level.OVERWORLD) {
                    serverPlayer.teleportTo(aurorian, 0, 100, 0, serverPlayer.getYRot(), serverPlayer.getXRot());
                }
            }
        }

        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 20 * 2;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

}