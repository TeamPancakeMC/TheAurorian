package cn.teampancake.theaurorian.common.items.developer;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RedBook extends Item implements IDeveloperItem, ITooltipsItem {

    public RedBook() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        stack.shrink(1);
        player.addItem(TAItems.RED_BOOK_RING.get().getDefaultInstance());
        return InteractionResultHolder.consume(stack);
    }

}