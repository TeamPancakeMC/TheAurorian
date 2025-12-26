package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

@SuppressWarnings("unused")
public class CrystallineSwordPedestalBlockEntity extends SimpleContainerBlockEntity implements GeoBlockEntity {

    private static final RawAnimation SEAL = RawAnimation.begin().thenPlay("misc.seal");
    private static final RawAnimation UNSEAL = RawAnimation.begin().thenPlay("misc.unseal");
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean seal = true;
    public boolean sealing;
    public boolean unsealing;
    public int sealTick;
    public int unsealTick;

    public CrystallineSwordPedestalBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.CRYSTALLINE_SWORD_PEDESTAL.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CrystallineSwordPedestalBlockEntity blockEntity) {
        if (blockEntity.unsealing && --blockEntity.unsealTick == 0) {
            blockEntity.seal = false;
            blockEntity.unsealing = false;
            blockEntity.markUpdated();
            return;
        }

        if (blockEntity.sealing && --blockEntity.sealTick == 0) {
            blockEntity.seal = true;
            blockEntity.sealing = false;
            blockEntity.markUpdated();
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Seal", state -> PlayState.STOP)
                .triggerableAnim("seal_animation", SEAL).transitionLength(1));
        controllers.add(new AnimationController<>(this, "Unseal", state -> PlayState.STOP)
                .triggerableAnim("unseal_animation", UNSEAL).transitionLength(1));
        controllers.add(new AnimationController<>(this, "Idle", state -> {
            PlayState idleState = state.setAndContinue(DefaultAnimations.IDLE);
            return state.getAnimatable().seal ? idleState : PlayState.STOP;
        }).transitionLength(0));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    public boolean isSeal() {
        return this.seal;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items.clear();
        this.sealTick = tag.getInt("SealTick");
        this.unsealTick = tag.getInt("UnsealTick");
        this.sealing = tag.getBoolean("Sealing");
        this.unsealing = tag.getBoolean("Unsealing");
        this.seal = tag.getBoolean("Seal");
        ContainerHelper.loadAllItems(tag, this.items, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("SealTick", this.sealTick);
        tag.putInt("UnsealTick", this.unsealTick);
        tag.putBoolean("Sealing", this.sealing);
        tag.putBoolean("Unsealing", this.unsealing);
        tag.putBoolean("Seal", this.seal);
        ContainerHelper.saveAllItems(tag, this.items, Boolean.TRUE, registries);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
        this.handleUpdateTag(pkt.getTag(), lookupProvider);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, registries);
        return tag;
    }

}