package cn.teampancake.theaurorian.common.equipment_set;

import cn.teampancake.pancakelib.api.IEquipmentSet;
import cn.teampancake.pancakelib.common.bonus.BonusHandler;
import cn.teampancake.pancakelib.common.equipment_set.EquipmentSet;
import cn.teampancake.pancakelib.common.equippable.EquippableGroup;
import cn.teampancake.pancakelib.common.equippable.VanillaIEquippable;
import cn.teampancake.theaurorian.common.network.TAMessages;
import cn.teampancake.theaurorian.common.network.message.CrystalRuneSetC2SMessage;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAKeyMappings;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

public class CrystalRuneArmorSet extends EquipmentSet {
    public static final int GENERATE_TIME = 20 * 8;
    public static final int MAX_CRYSTAL_PILLAR = 3;

    @Override
    public void init(BonusHandler<IEquipmentSet> bonusHandler, EquippableGroup equippableGroup) {
        equippableGroup
                .addGroup(VanillaIEquippable.of(EquipmentSlot.HEAD), TAItems.CRYSTAL_RUNE_HELMET.get())
                .addGroup(VanillaIEquippable.of(EquipmentSlot.CHEST),TAItems.CRYSTAL_RUNE_CHESTPLATE.get())
                .addGroup(VanillaIEquippable.of(EquipmentSlot.LEGS),TAItems.CRYSTAL_RUNE_LEGGINGS.get())
                .addGroup(VanillaIEquippable.of(EquipmentSlot.FEET),TAItems.CRYSTAL_RUNE_BOOTS.get());


        bonusHandler.addEvent(TickEvent.PlayerTickEvent.class, event -> {
            Player player = event.player;
            Vec3 movement = player.getDeltaMovement();
            if (!player.getAbilities().flying && player.horizontalCollision){
                movement = new Vec3(movement.x, 0.2D, movement.z);
            }
            player.setDeltaMovement(movement);
        });

        bonusHandler.addEvent(TickEvent.PlayerTickEvent.class,event -> {
            Player player = event.player;
            CompoundTag persistentData = player.getPersistentData();
            int count = persistentData.getInt("crystalPillarCount");
            System.out.println(count);
            if (count >= MAX_CRYSTAL_PILLAR){
                return;
            }
            if (player.tickCount % GENERATE_TIME == 0){
                persistentData.putInt("crystalPillarCount", count + 1);
            }
        });

        bonusHandler.addEvent(TickEvent.ClientTickEvent.class,event ->{
            if (event.phase == TickEvent.Phase.END) {
                while (TAKeyMappings.ARMOR_SET.get().consumeClick()) {
                    TAMessages.sendToServer(new CrystalRuneSetC2SMessage());
                }
            }
        });
    }
}
