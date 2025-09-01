package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DeveloperGift extends Item {

    @Nullable
    private static List<Item> developerItems = null;

    public DeveloperGift() {
        super(new Item.Properties().fireResistant()
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.LEGENDARY)
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (developerItems == null) developerItems = getDeveloperItems();
        ItemStack stack = player.getItemInHand(usedHand);
        stack.consume(1, player);
        int index = level.random.nextInt(developerItems.size());
        player.addItem(developerItems.get(index).getDefaultInstance());
        return InteractionResultHolder.consume(stack);
    }

    private static List<Item> getDeveloperItems() {
        HashSet<Item> items = new HashSet<>();
        TACommonUtils.getKnownItemStream().filter(item -> item.components().has(TADataComponents.DEVELOPER.get())).forEach(items::add);
        return new ArrayList<>(items);
    }

}