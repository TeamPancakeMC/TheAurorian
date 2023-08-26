package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.utils.AurorianUtil;
import cn.teampancake.theaurorian.utils.EntityHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class LivingDiviningRod extends Item implements ITooltipsItem {
    public LivingDiviningRod() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        Player player = pContext.getPlayer();
        EntityHelper.getEntitiesAround(level, player, player.getX(), player.getY(), player.getZ(), 18, false).forEach(entity -> {
            if (AurorianUtil.randomChanceOf(0.5D)) {
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120));
            }
        });
        return InteractionResult.SUCCESS;
    }
}
