package cn.teampancake.theaurorian.common.equipment_set;

import cn.teampancake.theaurorian.common.registry.TABiomes;
import cn.teampancake.theaurorian.common.registry.TACapability;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import com.pancake.pancake_lib.api.IEquipmentSet;
import com.pancake.pancake_lib.common.bonus.BonusHandler;
import com.pancake.pancake_lib.common.equipment_set.EquipmentSet;
import com.pancake.pancake_lib.common.equippable.EquippableGroup;
import com.pancake.pancake_lib.common.equippable.VanillaIEquippable;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.TickEvent;

import java.util.function.Function;

public class MysteriumWoolArmorSet extends EquipmentSet {

    @Override
    public void init(BonusHandler<IEquipmentSet> bonusHandler, EquippableGroup equippableGroup) {
        equippableGroup
                .addGroup(VanillaIEquippable.of(EquipmentSlot.HEAD), TAItems.MYSTERIUM_WOOL_HELMET.get())
                .addGroup(VanillaIEquippable.of(EquipmentSlot.CHEST), TAItems.MYSTERIUM_WOOL_CHESTPLATE.get())
                .addGroup(VanillaIEquippable.of(EquipmentSlot.LEGS), TAItems.MYSTERIUM_WOOL_LEGGINGS.get())
                .addGroup(VanillaIEquippable.of(EquipmentSlot.FEET), TAItems.MYSTERIUM_WOOL_BOOTS.get());


        bonusHandler.addEvent(TickEvent.PlayerTickEvent.class, event -> {
            Player player = event.player;
            Level level = player.level();
            if (event.phase == TickEvent.Phase.END && level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
                Holder<Biome> biomeHolder = serverLevel.getBiome(player.blockPosition());
                boolean isSnowField = biomeHolder.is(TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD);
                if (serverPlayer.gameMode.isSurvival() &&
                        !checkEquippable(serverPlayer) &&
                        !player.hasEffect(TAMobEffects.WARM.get()) &&
                        !player.hasEffect(TAMobEffects.FROSTBITE.get()) &&
                        isSnowField && player.tickCount % 60 == 0) {
                    unequippedEffect(serverPlayer, aFloat -> aFloat);
                }
            }
        });
    }


    public static void unequippedEffect(LivingEntity livingEntity, Function<Float, Float> amount) {
        livingEntity.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> miscNBT.ticksFrostbite = 140);
        livingEntity.setSharedFlagOnFire(false);
        livingEntity.hurt(livingEntity.damageSources().freeze(), amount.apply(1.0F));
    }
}
