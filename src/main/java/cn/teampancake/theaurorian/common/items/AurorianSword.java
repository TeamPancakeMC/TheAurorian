package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.util.EntityHelper;
import cn.teampancake.theaurorian.config.AurorianConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class AurorianSword extends SwordItem {
    public AurorianSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties properties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, properties);
        properties.rarity(Rarity.EPIC);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pUsedHand) {
        if (player.getItemInHand(pUsedHand).isEmpty() || player.isCrouching()) {
            List<LivingEntity> entities = EntityHelper.getEntitiesAround(level,player,player.getX(), player.getY() + 2.5D, player.getZ(), 5, 2.5D, false);
            for (LivingEntity e : entities) {
                if (!e.getType().is(Tags.EntityTypes.BOSSES)) {
                    e.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60));
                    e.moveTo(new Vec3(0,0.5D,0));
                }
            }
            if (level.isClientSide) {
                player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1f, 2f);
            }else {
                player.getItemInHand(pUsedHand).hurt(5,level.random,(ServerPlayer) player);
            }
            player.getCooldowns().addCooldown(this, AurorianConfig.Config_AurorianiteSwordCooldown.get());
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(pUsedHand));
        }

        return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> tooltip, TooltipFlag pIsAdvanced) {
        ModItems.appendTooltip(stack, tooltip);
    }
}
