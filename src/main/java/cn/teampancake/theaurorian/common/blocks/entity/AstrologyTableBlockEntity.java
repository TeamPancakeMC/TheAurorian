package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

import javax.annotation.Nullable;

public class AstrologyTableBlockEntity extends BlockEntity implements GeoBlockEntity {

    private int forecastDays;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public AstrologyTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.ASTROLOGY_TABLE.get(), pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.forecastDays = tag.getInt("ForecastDays");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("ForecastDays", this.forecastDays);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle_controller", state -> {
            if (!isNight(this.level)) {
                return PlayState.STOP;
            }

            state.setAndContinue(RawAnimation.begin().thenLoop("misc.idle"));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private static boolean isNight(@Nullable Level level) {
        if (level == null) return false;
        long dayTime = (level.getDayTime() + 6000L) % 24000L;
        return !(dayTime > 6000 && dayTime <= 18000);
    }

}