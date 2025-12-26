package cn.teampancake.theaurorian.common.items.tool.crystalline;

import cn.teampancake.theaurorian.common.items.tool.GeoHandheldToolRenderer;
import cn.teampancake.theaurorian.common.registry.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CrystallineSword extends SwordItem implements GeoItem {

    private static final int DAMAGE_START_TIME = 120;
    private static final int MAX_CHARGE_TIME = 60;
    private static final int SUPER_CHARGE_TIME = 120;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public CrystallineSword(Item.Properties properties) {
        super(TAToolTiers.CRYSTALLINE, properties.rarity(Rarity.EPIC)
                .attributes(createAttributes(TAToolTiers.CRYSTALLINE, 3, -2.4F))
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoHandheldToolRenderer<CrystallineSword>(TAItems.CRYSTALLINE_SWORD.getId()));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemInHand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration < 0 || !(livingEntity instanceof Player player)) return;
        int ticksUsed = player.getTicksUsingItem();
        if (ticksUsed >= SUPER_CHARGE_TIME) {
            int remainingDurability = stack.getMaxDamage() - stack.getDamageValue();
            if (remainingDurability <= 15) {
                if (ticksUsed % 10 == 0 && level.isClientSide) {
                    Vec3 pos = player.getEyePosition();
                    Vec3 look = player.getLookAngle();
                    Vec3 particlePos = pos.add(look.scale(2.0));
                    for (int i = 0; i < 10; i++) {
                        double offsetX = level.getRandom().nextDouble() - 0.5;
                        double offsetY = level.getRandom().nextDouble() - 0.5;
                        double offsetZ = level.getRandom().nextDouble() - 0.5;
                        level.addParticle(new DustParticleOptions(new Vector3f(1.0F, 0.2F, 0.2F), 1.0F),
                                particlePos.x + offsetX, particlePos.y + offsetY, particlePos.z + offsetZ, 0, 0, 0);
                    }
                }
            }
        }

        if (ticksUsed <= MAX_CHARGE_TIME) {
            if (ticksUsed % 40 == 0) {
                player.playSound(TASoundEvents.CRYSTALLINE_SWORD_CHARGING.get(), 0.7F, 0.8F);
            }
        } else {
            if ((ticksUsed - MAX_CHARGE_TIME) % 100 == 0) {
                player.playSound(TASoundEvents.CRYSTALLINE_SWORD_CHARGING.get(), 1.0F, 1.0F);
            }
        }

        if (level.isClientSide) {
            CrystallineSwordBeamUtils.spawnMagicCircleParticles(level, player, ticksUsed);
        }

        if (!level.isClientSide && ticksUsed > DAMAGE_START_TIME && player.tickCount % 20 == 0) {
            player.hurt(level.damageSources().magic(), 1.0F);
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if (!(livingEntity instanceof Player player)) return;
        int chargeTime = this.getUseDuration(stack, player) - timeLeft;
        if (chargeTime < 5) return;
        this.stopChargingSound(player);
        player.playSound(TASoundEvents.CRYSTALLINE_SWORD_SHOOT.get(), 1.0F, 1.0F);
        CrystallineSwordBeamUtils.fireBeam(stack, level, player, chargeTime);
    }

    private void stopChargingSound(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ResourceLocation chargingId = TASoundEvents.CRYSTALLINE_SWORD_CHARGING.getId();
            serverPlayer.connection.send(new ClientboundStopSoundPacket(chargingId, SoundSource.PLAYERS));
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        if (target.isDeadOrDying() && attacker instanceof Player player) {
            player.getCooldowns().removeCooldown(TAItems.CRYSTALLINE_SWORD.get());
        }

        return result;
    }

    public static class CrystallineSwordUseAnim implements IClientItemExtensions {

        public static final EnumProxy<HumanoidModel.ArmPose> CRYSTALLINE_SWORD_SHOOT = new EnumProxy<>(
                HumanoidModel.ArmPose.class, true, ((IArmPoseTransformer) (model, entity, arm) -> {
            model.rightArm.yRot = -0.1F + model.head.yRot - 0.4F;
            model.leftArm.yRot = 0.1F + model.head.yRot + 0.4F;
            model.rightArm.xRot = (-(float)Math.PI / 2.0F) + model.head.xRot;
            model.leftArm.xRot = (-(float)Math.PI / 2.0F) + model.head.xRot;
        }));

        @Override
        public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            if (itemStack.is(TAItems.CRYSTALLINE_SWORD.get()) &&
                    entityLiving.getUsedItemHand() == hand &&
                    entityLiving.getUseItemRemainingTicks() > 0) {
                return CRYSTALLINE_SWORD_SHOOT.getValue();
            }

            return HumanoidModel.ArmPose.EMPTY;
        }

        @Override
        public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
            if (!(player.getUseItem() == itemInHand && player.isUsingItem())) return false;
            int i = arm == HumanoidArm.RIGHT ? 1 : -1;
            float useDuration = (float) itemInHand.getUseDuration(player);
            float remainingTicks = (float) player.getUseItemRemainingTicks();
            float animProgress = useDuration - (remainingTicks - partialTick + 1.0F);
            float normalizedProgress = animProgress / 20.0F;
            normalizedProgress = (normalizedProgress * normalizedProgress + normalizedProgress * 2.0F) / 3.0F;
            normalizedProgress = Math.min(normalizedProgress, 1.0F);
            float limit = (float)i * -0.641864F;
            poseStack.translate(i * 0.56F, -0.52F, -0.72F);
            poseStack.translate(Math.max(limit, limit * 3.0F * normalizedProgress), 0.05F, 0.0F);
            poseStack.mulPose(Axis.XN.rotationDegrees(Math.min(90.0F, 90.0F * 3.0F * normalizedProgress)));
            poseStack.mulPose(Axis.YN.rotation(0.0F));
            return true;
        }
    }

}