package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.items.tool.AurorianSteelSword;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * 极光钢剑事件处理器
 */
@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class AurorianSteelSwordEventSubscriber {

    private static final int HOLINESS_EXTENSION_TICKS = 30; // 1.5秒 = 30刻

    /**
     * 处理实体死亡事件，延长神圣效果
     */
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player && 
                player.hasEffect(TAMobEffects.HOLINESS) && 
                player.getMainHandItem().getItem() instanceof AurorianSteelSword) {
            
            // 延长神圣效果1.5秒
            MobEffectInstance holinessEffect = player.getEffect(TAMobEffects.HOLINESS);
            if (holinessEffect != null) {
                int currentDuration = holinessEffect.getDuration();
                player.addEffect(new MobEffectInstance(TAMobEffects.HOLINESS, 
                                                     currentDuration + HOLINESS_EXTENSION_TICKS, 
                                                     holinessEffect.getAmplifier(), 
                                                     holinessEffect.isAmbient(), 
                                                     holinessEffect.isVisible(), 
                                                     holinessEffect.showIcon()));
            }
        }
    }
} 