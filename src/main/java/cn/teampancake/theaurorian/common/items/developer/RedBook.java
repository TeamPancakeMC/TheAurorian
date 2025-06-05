package cn.teampancake.theaurorian.common.items.developer;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class RedBook extends Item {

    public RedBook() {
        super(new Item.Properties()
                .component(TADataComponents.ITEM_TAGS, List.of(TAItemTags.HAS_CUSTOM_TOOLTIPS))
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
                .component(TADataComponents.DEVELOPER, Unit.INSTANCE)
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        stack.shrink(1);
        player.addItem(TAItems.RED_BOOK_RING.get().getDefaultInstance());
        return InteractionResultHolder.consume(stack);
    }

}