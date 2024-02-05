package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.items.developer.IDeveloperItem;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DeveloperGift extends Item {

    public DeveloperGift() {
        super(new Properties().fireResistant());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        List<Item> itemList = this.getDeveloperItems();
        ItemStack stack = player.getItemInHand(usedHand);
        stack.shrink(player.getAbilities().instabuild ? 0 : 1);
        int index = level.random.nextInt(itemList.size());
        player.addItem(itemList.get(index).getDefaultInstance());
        return InteractionResultHolder.consume(stack);
    }

    private List<Item> getDeveloperItems() {
        HashSet<Item> items = new HashSet<>();
        for (Item item : TACommonUtils.getKnownItems()) {
            if (item instanceof IDeveloperItem) {
                items.add(item);
            }
        }

        return new ArrayList<>(items);
    }

}