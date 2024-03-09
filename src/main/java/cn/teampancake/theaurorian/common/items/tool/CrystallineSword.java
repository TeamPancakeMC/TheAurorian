package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CrystallineSword extends SwordItem implements ITooltipsItem {

    public CrystallineSword() {
        super(TAToolTiers.CRYSTALLINE, (3), (-2.4F), new Properties().rarity(Rarity.EPIC).defaultDurability(512));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if (livingEntity instanceof Player player) {
            int i = this.getUseDuration(stack) - timeLeft;
            if (i < 10) return;
            Vec3 lookAngle = player.getLookAngle();
            float pitch = 1.0F / (level.random.nextFloat() * 0.4F + 1.2F) + 0.5F;
            level.playSound(null, player.getOnPos(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, pitch);
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
            Arrow arrow = TAEntityTypes.CRYSTALLINE_BEAM.get().create(level);
            if (!level.isClientSide && arrow != null) {
                arrow.setPos(player.getX(), player.getY() + 1.5F, player.getZ());
                arrow.shoot(lookAngle.x, lookAngle.y, lookAngle.z, 3.0F, 1.0F);
                arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                arrow.setNoGravity(true);
                arrow.addEffect(new MobEffectInstance(TAMobEffects.STUN.get(),20 * 2));
                arrow.setBaseDamage(8 + (double) 7 / 60 * i);
                level.addFreshEntity(arrow);
            }

            if (!player.getAbilities().instabuild) {
                player.getCooldowns().addCooldown(this, 600);
            }
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new CrystallineSwordUseAnim());
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 60;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    private static class CrystallineSwordUseAnim implements IClientItemExtensions {
        
        private static final HumanoidModel.ArmPose CRYSTALLINE_SWORD_SHOOT =
                HumanoidModel.ArmPose.create("crystalline_sword_shoot", true, ((model, entity, arm) -> {
                    model.rightArm.yRot = -0.1F + model.head.yRot - 0.4F;
                    model.leftArm.yRot = 0.1F + model.head.yRot + 0.4F;
                    model.rightArm.xRot = (-(float)Math.PI / 2F) + model.head.xRot;
                    model.leftArm.xRot = (-(float)Math.PI / 2F) + model.head.xRot;
                }));

        @Override
        public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            if (itemStack.is(TAItems.CRYSTALLINE_SWORD.get()) && entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                return CRYSTALLINE_SWORD_SHOOT;
            }

            return HumanoidModel.ArmPose.EMPTY;
        }

        @Override
        public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
            int i = arm == HumanoidArm.RIGHT ? 1 : -1;
            float useDuration = (float) itemInHand.getUseDuration();
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