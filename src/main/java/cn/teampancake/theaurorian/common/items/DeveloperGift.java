package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
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

    public DeveloperGift() {
        super(TAItemProperties.get().fireResistant().addItemTag(TAItemTags.IS_LEGENDARY).isSimpleModelItem());
    }

    @Nullable
    private static List<Item> developerItems = null;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (developerItems == null) developerItems = getDeveloperItems();
        ItemStack stack = player.getItemInHand(usedHand);
        stack.shrink(player.getAbilities().instabuild ? 0 : 1);
        int index = level.random.nextInt(developerItems.size());
        player.addItem(developerItems.get(index).getDefaultInstance());
        return InteractionResultHolder.consume(stack);
    }

    private static List<Item> getDeveloperItems() {
        HashSet<Item> items = new HashSet<>();
        TACommonUtils.getKnownItemStream().filter(item -> TACommonUtils.getItemProperties(item).isDeveloperItem).forEach(items::add);
        return new ArrayList<>(items);
    }

}