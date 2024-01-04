package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.api.IShield;
import cn.teampancake.theaurorian.common.capability.ShieldCap;
import cn.teampancake.theaurorian.common.data.pack.MaxShieldLoader;
import cn.teampancake.theaurorian.common.effect.TempShieldEffect;
import cn.teampancake.theaurorian.common.network.TAMessages;
import cn.teampancake.theaurorian.common.network.message.ShieldSyncS2CMessage;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.registry.TAShields;
import cn.teampancake.theaurorian.common.shield.MaxShieldData;
import cn.teampancake.theaurorian.common.shield.TempShield;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

@Mod.EventBusSubscriber
public class ShieldSubscriber {

    //自然恢复护盾
    @SubscribeEvent
    public static void LivingNaturalRecoveryShield(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        if (level.getGameTime() % 20 == 0 && entity instanceof ServerPlayer player) {
            ShieldCap.getCapability(entity).ifPresent(cap -> {
                cap.getShieldMap().values().stream()
                        .filter(Shield -> Shield.isNaturalRecovery(entity))
                        .forEach(Shield -> Shield.increaseShield(Shield.naturalRecovery(entity)));
                TAMessages.sendToPlayer(new ShieldSyncS2CMessage(cap.serializeNBT()),player);
            });
        }
    }

    //进入维度添加护盾上限
    @SubscribeEvent
    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            ResourceLocation location = event.getDimension().location();
            addMaxShield(MaxShieldLoader.Dimension,livingEntity,location,"dimension");
        }
    }

    //当成成就时添加护盾上限
    @SubscribeEvent
    public static void onAdvancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        Advancement advancement = event.getAdvancement();
        addMaxShield(MaxShieldLoader.Achievements,event.getEntity(),advancement.getId(),"achievements");
    }

    //当杀死特定生物时添加护盾上限
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();
        Entity directEntity = source.getDirectEntity();
        if (directEntity instanceof LivingEntity livingEntity) {
            ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
            addMaxShield(MaxShieldLoader.Entity,livingEntity,key,"entity");
        }
    }

    //物品使用事件
//    @SubscribeEvent
//    public static void onLivingEntityUseItem(LivingEntityUseItemEvent event) {
//        ItemStack item = event.getItem();
//        System.out.println("onLivingEntityUseItem: " + item);
//    }

    @SubscribeEvent
    public static void onPlayerInteractRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getItemStack();
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
        if (addMaxShield(MaxShieldLoader.Item,event.getEntity(),key,"item")) {
            itemStack.shrink(1);
        }
    }

    //穿戴装备时更改护盾上限
    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        ResourceLocation keyFrom = ForgeRegistries.ITEMS.getKey(event.getFrom().getItem());
        ResourceLocation keyTO = ForgeRegistries.ITEMS.getKey(event.getTo().getItem());
        reduceMaxShield(MaxShieldLoader.Armor,event.getEntity(),keyFrom,"armor");
        addMaxShield(MaxShieldLoader.Armor,event.getEntity(),keyTO,"armor");
    }

    //根据Buff修改护盾上限
    @SubscribeEvent
    public static void onMobEffect(MobEffectEvent.Added event) {
        ResourceLocation key = ForgeRegistries.MOB_EFFECTS.getKey(event.getEffectInstance().getEffect());
        addMaxShield(MaxShieldLoader.Buff,event.getEntity(),key,"buff");
    }

    @SubscribeEvent
    public static void onMobEffectExpired(MobEffectEvent.Expired event) {
        if (event.getEffectInstance() != null) {
            ResourceLocation key = ForgeRegistries.MOB_EFFECTS.getKey(event.getEffectInstance().getEffect());
            reduceMaxShield(MaxShieldLoader.Buff,event.getEntity(),key,"buff");
        }
    }

    //增加护盾上限
    private static boolean addMaxShield(Map<ResourceLocation, Map<ResourceLocation,Integer>> map, LivingEntity livingEntity, ResourceLocation location, String type) {
        final boolean[] flag = {false};
        map.forEach((shieldLocation, maxShieldMap) -> ShieldCap.getCapability(livingEntity).ifPresent(cap -> {
            IShield iShield = cap.getShieldMap().get(shieldLocation);
            if (iShield != null) {
                MaxShieldData maxShieldData = iShield.getMaxShieldData();
                if (maxShieldData.Vaild(type,location,true) && maxShieldMap.containsKey(location)) {
                    iShield.increaseMaxShield(maxShieldMap.get(location));
                    maxShieldData.MAP.get(type).add(location);
                    flag[0] = true;
                }
            }
        }));
        return flag[0];
    }

    //减少护盾上限
    private static boolean reduceMaxShield(Map<ResourceLocation, Map<ResourceLocation,Integer>> map, LivingEntity livingEntity, ResourceLocation location, String type) {
        final boolean[] flag = {false};
        map.forEach((shieldLocation, maxShieldMap) -> ShieldCap.getCapability(livingEntity).ifPresent(cap -> {
            IShield iShield = cap.getShieldMap().get(shieldLocation);
            if (iShield != null) {
                MaxShieldData maxShieldData = iShield.getMaxShieldData();
                if (maxShieldData.Vaild(type,location,false) && maxShieldMap.containsKey(location)) {
                    iShield.consumeMaxShield(maxShieldMap.get(location));
                    maxShieldData.MAP.get(type).remove(location);
                    flag[0] = true;
                }
            }
        }));
        return flag[0];
    }

    //护盾抵消伤害
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();
        float amount = event.getAmount();
        ShieldCap.getCapability(entity).ifPresent(cap -> {
            float damage = cap.damage(entity, source, amount);
            event.setAmount(damage);
            if (entity instanceof ServerPlayer player){
                TAMessages.sendToPlayer(new ShieldSyncS2CMessage(cap.serializeNBT()),player);
            }
        });
    }

    //临时护盾Buff结束时移除临时护盾
    @SubscribeEvent
    public static void onTempShieldEffectExpired(MobEffectEvent.Expired event) {
        MobEffect effect = event.getEffectInstance().getEffect();
        LivingEntity livingEntity = event.getEntity();
        if (effect instanceof TempShieldEffect tempShieldEffect) {
            ShieldCap.getCapability(livingEntity).ifPresent(cap -> {
                if (cap.getShieldMap().get(TAShields.TEMP.getId()) instanceof TempShield tempShield) {
                    if (livingEntity instanceof ServerPlayer player){
                        tempShield.clearTempShield(player);
                    }
                }
            });
        }
    }

    //极光护盾免疫眩晕
    @SubscribeEvent
    public static void onMobEffect(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        ShieldCap.getCapability(entity).ifPresent(cap -> {
            IShield iShield = cap.getShieldMap().get(TAShields.AURORIAN.getId());
            if (iShield != null && iShield.getShield() / iShield.getMaxShield() > 0.85f) {
                if (event.getEffectInstance().getEffect() == TAMobEffects.STUN.get()) {
                    event.setResult(MobEffectEvent.Result.DENY);
                }
            }
        });
    }

}