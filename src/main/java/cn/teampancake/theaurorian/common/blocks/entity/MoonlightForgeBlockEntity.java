package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.client.inventory.MoonlightForgeMenu;
import cn.teampancake.theaurorian.common.items.crafting.MoonlightForgeRecipe;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class MoonlightForgeBlockEntity extends SimpleContainerBlockEntity implements WorldlyContainer {

    private int craftProgress;
    public boolean hasMoonLight, isCrafting, isPowered;
    private final ContainerData containerData = new Data();
    private final RecipeManager.CachedCheck<Container, ? extends MoonlightForgeRecipe> quickCheck;
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};

    public MoonlightForgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.MOONLIGHT_FORGE.get(), pos, blockState);
        this.quickCheck = RecipeManager.createCheck(TARecipes.MOONLIGHT_FORGE_RECIPE.get());
        this.handler = new SimpleContainerBlockEntity.Handler(3);
    }

    @SuppressWarnings("unused")
    public static void serverTick(Level level, BlockPos pos, BlockState state, MoonlightForgeBlockEntity blockEntity) {
        if (!level.isClientSide()) {
            ItemStack equipment = blockEntity.handler.getStackInSlot(0);
            ItemStack upgradeMaterial = blockEntity.handler.getStackInSlot(1);
            NonNullList<ItemStack> inventory = blockEntity.handler.getStacks();
            MoonlightForgeRecipe recipe = !equipment.isEmpty() ? blockEntity.quickCheck
                    .getRecipeFor(blockEntity, level).orElse(null) : null;
            blockEntity.isPowered = level.hasNeighborSignal(pos);
            blockEntity.hasMoonLight = level.canSeeSky(pos.above()) &&(level.dimension() == TADimensions.AURORIAN_DIMENSION || level.isNight());
            blockEntity.isCrafting = blockEntity.canWork(level.registryAccess(), recipe, inventory);
            if (blockEntity.isCrafting && upgradeMaterial.getCount() > 0) {
                float heightPercent = (float) pos.getY() / (float) level.getHeight();
                int tickInterval = 2;
                if (heightPercent <= 0.25) {
                    tickInterval += 16;
                } else if (heightPercent > 0.25 && heightPercent <= 0.5) {
                    tickInterval += 8;
                } else if (heightPercent > 0.5 && heightPercent <= 0.75) {
                    tickInterval += 4;
                }

                if (level.getGameTime() % tickInterval == 0) {
	                int newVal = blockEntity.craftProgress + 1;
                    if (newVal >= 100) {
                        blockEntity.stopCrafting();
                        blockEntity.startWork(level.registryAccess(), recipe, inventory);
                    } else {
                        blockEntity.craftProgress = newVal;
                    }
                }
            }
	        blockEntity.updateClient(); // Sync craft progress to client
        }
    }

    protected void startWork(RegistryAccess registryAccess, @Nullable Recipe<Container> recipe, NonNullList<ItemStack> inventory) {
        if (recipe != null && this.canWork(registryAccess, recipe, inventory)) {
            ItemStack copyOfResultStack = recipe.assemble(this, registryAccess);
            ItemStack equipmentStack = inventory.get(0), resultStack = inventory.get(2), materialStack = inventory.get(1);
            if (equipmentStack.isEnchanted()) {
                equipmentStack.getAllEnchantments().forEach(copyOfResultStack::enchant);
            }
            if (resultStack.isEmpty()) {
                inventory.set(2, copyOfResultStack.copy());
                equipmentStack.shrink(1);
				materialStack.shrink(1);
            }
        }
    }

    protected boolean canWork(RegistryAccess registryAccess, @Nullable Recipe<Container> recipe, NonNullList<ItemStack> inventory) {
        if (!inventory.get(0).isEmpty() && recipe != null) {
            ItemStack copyOfResultStack = recipe.assemble(this, registryAccess);
            boolean flag = this.hasMoonLight && !this.isPowered;
            return !copyOfResultStack.isEmpty() && inventory.get(2).isEmpty() && flag;
        } else {
            return false;
        }
    }

    public boolean isCrafting() {
        return !this.isPowered && this.hasMoonLight && this.craftProgress > 0;
    }

    private void stopCrafting() {
        this.isCrafting = false;
        this.craftProgress = 0;
        this.updateClient();
    }

    public void updateClient() {
        this.setChanged();
        if (this.level != null) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
        }
    }

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag nbt = super.getUpdateTag();
		this.saveAdditional(nbt);
		return nbt;
	}

	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
        this.hasMoonLight = tag.getBoolean("has_moonlight");
        this.isPowered = tag.getBoolean("is_powered");
        this.craftProgress = tag.getInt("craft_progress");
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putInt("craft_progress", this.craftProgress);
		tag.putBoolean("is_powered", this.isPowered);
		tag.putBoolean("has_moonlight", this.hasMoonLight);
	}

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return index != 2;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == 2;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new MoonlightForgeMenu(containerId, inventory, this, this.containerData);
    }

    private class Data implements ContainerData {

        @Override
        public int get(int index) {
            if (index == 0) {
                return craftProgress;
            } else {
                return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                craftProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }

    }

}