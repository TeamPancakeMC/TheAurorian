package cn.teampancake.theaurorian.common.blocks.entity.crystal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AbstractLunarCrystalBlockEntity extends BlockEntity implements GeoBlockEntity {

    private static final RawAnimation ACTIVE = RawAnimation.begin().thenPlay("misc.active");
    private static final RawAnimation ACTIVE_IDLE = RawAnimation.begin().thenLoop("misc.active_idle");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public boolean activating;
    public boolean activated;
    public int activeTime;

    public AbstractLunarCrystalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AbstractLunarCrystalBlockEntity blockEntity) {
        if (blockEntity.activating && --blockEntity.activeTime == 0) {
            blockEntity.activating = false;
            blockEntity.activated = true;
            blockEntity.updateBlock();
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "active_controller", state -> PlayState.STOP)
                .triggerableAnim("active_animation", ACTIVE).transitionLength(5));
        controllers.add(new AnimationController<>(this, "Active", state -> {
            AbstractLunarCrystalBlockEntity blockEntity = state.getAnimatable();
            if (!blockEntity.activating && blockEntity.activated) {
                return state.setAndContinue(ACTIVE_IDLE);
            } else {
                state.setAnimation(DefaultAnimations.IDLE);
                return PlayState.STOP;
            }
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.activating = tag.getBoolean("Activating");
        this.activated = tag.getBoolean("Activated");
        this.activeTime = tag.getInt("ActiveTime");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("Activating", this.activating);
        tag.putBoolean("Activated", this.activated);
        tag.putInt("ActiveTime", this.activeTime);
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

    protected void updateBlock() {
        if (this.level != null) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
            this.setChanged();
        }
    }

}