package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LaserCrystalBlockEntity extends BlockEntity implements GeoBlockEntity {

    private static final RawAnimation ACTIVE = RawAnimation.begin().thenPlay("misc.active");
    private static final RawAnimation ACTIVE_IDLE = RawAnimation.begin().thenLoop("misc.active_idle");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public boolean activating;
    public boolean activated;
    public int activeTime;

    public LaserCrystalBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.LASER_CRYSTAL.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, LaserCrystalBlockEntity blockEntity) {
        if (level.tickRateManager().runsNormally() && blockEntity.activating && --blockEntity.activeTime == 0) {
            blockEntity.triggerAnim("active_idle_controller", "active_idle_animation");
            blockEntity.activating = false;
            blockEntity.activated = true;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "active_controller", state -> PlayState.STOP)
                .triggerableAnim("active_animation", ACTIVE).transitionLength(5));
        controllers.add(new AnimationController<>(this, "active_idle_controller", state -> PlayState.CONTINUE)
                .triggerableAnim("active_idle_animation", ACTIVE_IDLE).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
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

}