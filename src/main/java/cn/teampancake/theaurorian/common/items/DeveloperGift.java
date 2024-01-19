package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DeveloperGift extends Item {

    public DeveloperGift() {
        super(new Properties().fireResistant());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        stack.shrink(1);
        int random = (int) (Math.random()*7);
        switch (random){
            case 0:
                player.addItem(TAItems.SLEEPING_BLACK_TEA.get().getDefaultInstance());
                break;
            case 1:
                player.addItem(TAItems.WHITE_CHOCOLATE.get().getDefaultInstance());
                break;
            case 2:
                player.addItem(TAItems.RED_BOOK.get().getDefaultInstance());
                break;
            case 3:
                player.addItem(TAItems.RED_BOOK_RING.get().getDefaultInstance());
                break;
            case 4:
                player.addItem(TAItems.STAR_OCEAN_CROSSBOW.get().getDefaultInstance());
                break;
            case 5:
                player.addItem(TAItems.CAT_BELL.get().getDefaultInstance());
                break;
            case 6:
                player.addItem(TAItems.TSLAT_SWORD.get().getDefaultInstance());
                break;
        }


        return InteractionResultHolder.consume(stack);
    }
}