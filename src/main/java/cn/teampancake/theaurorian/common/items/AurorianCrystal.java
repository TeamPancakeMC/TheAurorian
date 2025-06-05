package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;

public class AurorianCrystal extends Item {

    public AurorianCrystal() {
        super(new Item.Properties().durability(1)
                .component(TADataComponents.ITEM_TAGS, List.of(TAItemTags.IS_LEGENDARY))
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemInHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer player) {
            MinecraftServer server = player.getServer();
            if (server != null) {
                ResourceKey<Level> dimension = player.level().dimension();
                ResourceKey<Level> overworldKey = Level.OVERWORLD;
                if (dimension != overworldKey) {
                    TAEntityUtils.teleportFromAurorianToOverworld(player, server.getLevel(overworldKey));
                }

                ResourceKey<Level> aurorianKey = TADimensions.AURORIAN_DIMENSION;
                if (dimension != aurorianKey) {
                    TAEntityUtils.teleportToAurorian(player, server.getLevel(aurorianKey));
                }
            }
        }

        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 20;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

}