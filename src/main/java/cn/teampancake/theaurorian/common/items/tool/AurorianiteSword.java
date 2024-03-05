package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AurorianiteSword extends SwordItem implements ITooltipsItem {

    public AurorianiteSword() {
        super(TAToolTiers.AURORIANITE, 4, -2.4F, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        AABB aabb = (new AABB(player.getOnPos())).inflate(5.0D);
        List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, aabb);
        player.getCooldowns().addCooldown(this, AurorianConfig.Config_AurorianiteSwordCooldown.get());
        entities.stream().filter(entity -> !entity.getType().is(Tags.EntityTypes.BOSSES)).forEach(entity -> {
            entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60));
            entity.moveTo(new Vec3(entity.getX(), entity.getY() + 0.5D, entity.getZ()));
        });

        if (level.isClientSide) {
            player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1f, 2f);
        } else {
            if (player instanceof ServerPlayer serverPlayer) {
                player.getItemInHand(usedHand).hurt(5, level.random, serverPlayer);
            }
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(usedHand));
    }

}