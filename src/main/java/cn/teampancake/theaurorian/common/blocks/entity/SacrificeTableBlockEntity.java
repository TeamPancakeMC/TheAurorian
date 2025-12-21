package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SacrificeTableBlockEntity extends SimpleContainerBlockEntity implements GeoBlockEntity {

    public int guardTime;
    public long placementTime;
    private final NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public SacrificeTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.SACRIFICE_TABLE.get(), pos, blockState);
        this.placementTime = System.currentTimeMillis();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SacrificeTableBlockEntity blockEntity) {
        if (blockEntity.guardTime > 0 && level.tickRateManager().runsNormally()) {
            if (blockEntity.hasActiveEarlierTableInRange(level, pos)) {
                blockEntity.guardTime--;
                int expectedFoodCount = blockEntity.guardTime / (20 * 60 * 20);
                int currentFoodCount = blockEntity.getCurrentFoodCount();
                while (currentFoodCount > expectedFoodCount) {
                    blockEntity.removeRandomFood();
                    setChanged(level, pos, state);
                    currentFoodCount--;
                }
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
    
    private boolean hasActiveEarlierTableInRange(Level level, BlockPos pos) {
        int range = 128;
        List<SacrificeTableBlockEntity> earlierTables = new ArrayList<>();
        for (int dx = -range; dx <= range; dx += 16) {
            for (int dz = -range; dz <= range; dz += 16) {
                BlockPos checkPos = pos.offset(dx, 0, dz);
                if (level.getBlockEntity(checkPos) instanceof SacrificeTableBlockEntity otherTable) {
                    if (pos.distSqr(checkPos) <= Mth.square(range) &&
                        otherTable.placementTime < this.placementTime && 
                        otherTable.guardTime > 0) {
                        earlierTables.add(otherTable);
                    }
                }
            }
        }

        this.checkDetailedRangeForEarlierTables(level, pos, range, earlierTables);
        return earlierTables.isEmpty();
    }
    
    private void checkDetailedRangeForEarlierTables(Level level, BlockPos pos, int range, List<SacrificeTableBlockEntity> earlierTables) {
        BlockPos minPos = pos.offset(-range, -range, -range);
        BlockPos maxPos = pos.offset(range, range, range);
        for (int x = minPos.getX(); x <= maxPos.getX(); x += 4) {
            for (int y = minPos.getY(); y <= maxPos.getY(); y += 4) {
                for (int z = minPos.getZ(); z <= maxPos.getZ(); z += 4) {
                    BlockPos checkPos = new BlockPos(x, y, z);
                    if (checkPos.equals(pos)) continue;
                    if (level.getBlockEntity(checkPos) instanceof SacrificeTableBlockEntity otherTable) {
                        if (pos.distSqr(checkPos) <= Mth.square(range) &&
                            otherTable.placementTime < this.placementTime && 
                            otherTable.guardTime > 0) {
                            if (!earlierTables.contains(otherTable)) {
                                earlierTables.add(otherTable);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean placeFood(@Nullable LivingEntity entity, ItemStack food) {
        FoodProperties properties = food.get(DataComponents.FOOD);
        if (this.level != null && properties != null) {
            GameEvent.Context context = GameEvent.Context.of(entity, this.getBlockState());
            List<Integer> emptySlots = new ArrayList<>();
            for (int i = 0; i < this.items.size(); i++) {
                if (this.items.get(i).isEmpty()) {
                    emptySlots.add(i);
                }
            }

            if (!emptySlots.isEmpty()) {
                int slotIndex = emptySlots.get(this.level.random.nextInt(emptySlots.size()));
                this.items.set(slotIndex, food.consumeAndReturn(1, entity));
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), context);
                if (this.hasActiveEarlierTableInRange(this.level, this.getBlockPos())) {
                    this.guardTime += 24000;
                }
                
                this.markUpdated();
                return true;
            }
        }

        return false;
    }

    public void removeRandomFood() {
        if (this.level != null) {
            List<Integer> foodSlots = new ArrayList<>();
            for (int i = 0; i < this.items.size(); i++) {
                ItemStack itemStack = this.items.get(i);
                if (!itemStack.isEmpty() && itemStack.has(DataComponents.FOOD)) {
                    foodSlots.add(i);
                }
            }

            if (!foodSlots.isEmpty()) {
                int randomIndex = this.level.random.nextInt(foodSlots.size());
                int slotIndex = foodSlots.get(randomIndex);
                this.items.set(slotIndex, ItemStack.EMPTY);
                this.markUpdated();
            }
        }
    }
    
    public int getCurrentFoodCount() {
        int count = 0;
        for (ItemStack itemStack : this.items) {
            if (!itemStack.isEmpty() && itemStack.has(DataComponents.FOOD)) {
                count++;
            }
        }

        return count;
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.guardTime = tag.getInt("GuardTime");
        this.placementTime = tag.getLong("PlacementTime");
        this.items.clear();
        ContainerHelper.loadAllItems(tag, this.items, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("GuardTime", this.guardTime);
        tag.putLong("PlacementTime", this.placementTime);
        ContainerHelper.saveAllItems(tag, this.items, true, registries);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}