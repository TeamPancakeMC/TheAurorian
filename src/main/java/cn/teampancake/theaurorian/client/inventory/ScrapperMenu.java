package cn.teampancake.theaurorian.client.inventory;

import cn.teampancake.theaurorian.common.blocks.entity.ScrapperBlockEntity;
import cn.teampancake.theaurorian.registry.ModItems;
import cn.teampancake.theaurorian.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class ScrapperMenu extends AbstractSimpleMenu {

    public final ScrapperBlockEntity scrapper;

    public ScrapperMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public ScrapperMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(ModMenuTypes.SCRAPPER_MENU.get(), containerId, inventory);
        checkContainerSize(inventory, 3);
        this.scrapper = (ScrapperBlockEntity) blockEntity;
        this.scrapper.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            this.addSlot(new FirstSlot(this, itemHandler, 0, 80, 17));
            this.addSlot(new SecondSlot(this, itemHandler, 1, 40, 37));
            this.addSlot(new ResultSlot(itemHandler, 2, 80, 58));
        });
    }

    @Override
    protected BaseContainerBlockEntity getBlockEntity() {
        return this.scrapper;
    }

    @Override
    protected boolean slot1ItemFlag(ItemStack stack) {
        return stack.isDamageableItem();
    }

    @Override
    protected boolean slot2ItemFlag(ItemStack stack) {
        return stack.is(ModItems.CRYSTAL.get());
    }

}