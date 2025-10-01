package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

public class AstrologyTableBlockEntity extends BlockEntity implements GeoBlockEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public AstrologyTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.ASTROLOGY_TABLE.get(), pos, blockState);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle_controller", state -> {
            if (!isNight(this.level) || isAuroraNight()) {
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

    private static boolean isNight(Level level) {
        if (level == null) return false;
        long dayTime = (level.getDayTime() + 6000L) % 24000L;
        return !(dayTime > 6000 && dayTime <= 18000);
    }

    private static boolean isAuroraNight() {
        if (FMLLoader.getDist() != Dist.CLIENT) {
            return false;
        }
        try {
            return cn.teampancake.theaurorian.client.gui.hud.NightBarRender.nightType == 2;
        } catch (Throwable ignored) {
            return false;
        }
    }

}