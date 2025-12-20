package cn.teampancake.theaurorian.common.items.tool.aurorian_steel;

import cn.teampancake.theaurorian.common.items.tool.GeoHandheldToolRenderer;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class AurorianSteelBow extends BowItem implements GeoItem {

    private static final RawAnimation PULL = RawAnimation.begin().thenPlay("misc.pull");
    private static final RawAnimation TAUT = RawAnimation.begin().thenPlay("misc.taut");
    private static final RawAnimation SHAKE = RawAnimation.begin().thenPlay("misc.shake");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AurorianSteelBow(Properties properties) {
        super(properties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoHandheldToolRenderer<AurorianSteelBow>(TAItems.AURORIAN_STEEL_BOW.getId()));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController(this));
        controllers.add(new AnimationController<>(this, "pull_controller", state -> PlayState.STOP)
                .triggerableAnim("pull_animation", PULL).transitionLength(1));
        controllers.add(new AnimationController<>(this, "taut_controller", state -> PlayState.STOP)
                .triggerableAnim("taut_animation", TAUT).transitionLength(1));
        controllers.add(new AnimationController<>(this, "shake_controller", state -> PlayState.STOP)
                .triggerableAnim("shake_animation", SHAKE).transitionLength(1));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (level instanceof ServerLevel serverLevel && stack.getUseDuration(livingEntity) - remainingUseDuration > 20) {
            this.triggerAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "taut_controller", "taut_animation");
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        boolean flag = !player.getProjectile(itemInHand).isEmpty();
        if (!player.hasInfiniteMaterials() && !flag) {
            return InteractionResultHolder.fail(itemInHand);
        } else {
            player.startUsingItem(hand);
            if (level instanceof ServerLevel serverLevel) {
                long assignId = GeoItem.getOrAssignId(itemInHand, serverLevel);
                this.getAnimationControllers(itemInHand, serverLevel).remove("Idle");
                this.getAnimationControllers(itemInHand, serverLevel).forEach((s, controller) -> controller.stop());
                this.triggerAnim(player, assignId, "pull_controller", "pull_animation");
            }

            return InteractionResultHolder.consume(itemInHand);
        }
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            this.getAnimationControllers(stack, serverLevel).put("Idle", this.genericIdleController());
        }
    }

    @Override
    protected void shoot(
            ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, List<ItemStack> projectileItems,
            float velocity, float inaccuracy, boolean isCrit, @Nullable LivingEntity target) {
        super.shoot(level, shooter, hand, weapon, projectileItems, velocity, inaccuracy, isCrit, target);
        this.getAnimationControllers(weapon, level).put("Idle", this.genericIdleController());
    }

    private Map<String, AnimationController<GeoAnimatable>> getAnimationControllers(ItemStack stack, ServerLevel level) {
        return this.cache.getManagerForId(GeoItem.getOrAssignId(stack, level)).getAnimationControllers();
    }

    private AnimationController<GeoAnimatable> genericIdleController() {
        return new AnimationController<>(this, "Idle", 0, state -> state.setAndContinue(DefaultAnimations.IDLE));
    }

}