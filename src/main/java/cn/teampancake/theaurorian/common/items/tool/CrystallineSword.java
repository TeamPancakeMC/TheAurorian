package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.registry.TASoundEvents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

public class CrystallineSword extends SwordItem {

    public CrystallineSword() {
        super(TAToolTiers.CRYSTALLINE, TAItemProperties.get().rarity(Rarity.EPIC).durability(512)
                .attributes(createAttributes(TAToolTiers.CRYSTALLINE, (3), (-2.4F)))
                .addItemTag(ItemTags.SWORDS, TAItemTags.IS_EPIC).hasTooltips());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.playSound(TASoundEvents.CRYSTALLINE_SWORD_USE.get());
        ItemStack itemInHand = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemInHand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration >= 0 && livingEntity instanceof Player player) {
            int i = player.getTicksUsingItem();
            if (i > 60 && player.tickCount % 100 == 0) {
                player.playSound(TASoundEvents.CRYSTALLINE_SWORD_CHARGING.get());
            }

            if (!level.isClientSide && i > 120 && player.tickCount % 20 == 0) {
                player.hurt(level.damageSources().magic(), 3.0F);
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if (livingEntity instanceof Player player) {
            int i = this.getUseDuration(stack, player) - timeLeft;
            if (i < 10) return;
            Vec3 lookAngle = player.getLookAngle();
            if (player instanceof ServerPlayer serverPlayer) {
                ResourceLocation id = TASoundEvents.CRYSTALLINE_SWORD_CHARGING.getId();
                serverPlayer.connection.send(new ClientboundStopSoundPacket(id, SoundSource.PLAYERS));
            }

            player.playSound(TASoundEvents.CRYSTALLINE_SWORD_SHOOT.get());
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
            Arrow arrow = TAEntityTypes.CRYSTALLINE_BEAM.get().create(level);
            if (!level.isClientSide && arrow != null) {
                arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                arrow.setPos(player.getX(), player.getY() + 1.5F, player.getZ());
                arrow.shoot(lookAngle.x, lookAngle.y, lookAngle.z, 3.0F, 1.0F);
                arrow.addEffect(new MobEffectInstance(TAMobEffects.STUN, 40));
                arrow.setBaseDamage(8.0D + 5.0D * player.getTicksUsingItem() / 20.0D);
                arrow.setNoGravity(true);
                level.addFreshEntity(arrow);
            }

            if (!player.getAbilities().instabuild) {
                player.getCooldowns().addCooldown(this, 600);
            }
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
            if (itemStack.is(TAItems.CRYSTALLINE_SWORD.get()) && entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                return CRYSTALLINE_SWORD_SHOOT.getValue();
            }

            return HumanoidModel.ArmPose.EMPTY;
        }

        @Override
        public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
            int i = arm == HumanoidArm.RIGHT ? 1 : -1;
            float useDuration = (float) itemInHand.getUseDuration(player);
            float remainingTicks = (float) player.getUseItemRemainingTicks();
            float f7 = useDuration - (remainingTicks - partialTick + 1.0F);
            float f12 = f7 / 20.0F;
            f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
            if (f12 > 1.0F) {
                f12 = 1.0F;
            }

            if (player.getUseItem() == itemInHand && player.isUsingItem()) {
                float limit = (float)i * -0.641864F;
                poseStack.translate(i * 0.56F, -0.52F, -0.72F);
                poseStack.translate(Math.max(limit, limit * 3.0F * f12), 0.05F, 0.0F);
                poseStack.mulPose(Axis.XN.rotationDegrees(Math.min(90.0F, 90.0F * 3.0F * f12)));
                poseStack.mulPose(Axis.YN.rotation(0.0F));
                return true;
            }

            return false;
        }

    }

}