package cn.teampancake.theaurorian.common.items.developer;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RedBook extends Item {

    public RedBook(Item.Properties properties) {
        super(properties.component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RED_BOOK)
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
                .component(TADataComponents.DEVELOPER, Unit.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        stack.shrink(1);
        player.addItem(new ItemStack(TAItems.RED_BOOK_RING.get()));
        return InteractionResultHolder.consume(stack);
    }

}