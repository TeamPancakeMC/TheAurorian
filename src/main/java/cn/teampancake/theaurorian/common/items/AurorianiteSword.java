package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.config.AurorianConfig;
import cn.teampancake.theaurorian.utils.EntityHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AurorianiteSword extends SwordItem implements ITooltipsItem{

    public AurorianiteSword() {
        super(TAToolTiers.AURORIANITE, 4, -2.4F,new Properties().rarity(Rarity.EPIC));
    }



    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pUsedHand) {
        if (player.getItemInHand(pUsedHand).isEmpty() || player.isCrouching()) {
            List<LivingEntity> entities = EntityHelper.getEntitiesAround(level,player,player.getX(), player.getY() + 2.5D, player.getZ(), 5, 2.5D, false);
            for (LivingEntity e : entities) {
                if (!e.getType().is(Tags.EntityTypes.BOSSES)) {
                    e.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60));
                    e.moveTo(new Vec3(e.getX(),e.getY()+0.5D,e.getZ()));
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
}