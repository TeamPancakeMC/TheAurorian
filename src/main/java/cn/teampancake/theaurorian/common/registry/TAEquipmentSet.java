package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.pancakelib.api.IEquipmentSet;
import cn.teampancake.pancakelib.common.init.ModEquipmentSet;
import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.equipment_set.CrystalRuneArmorSet;
import cn.teampancake.theaurorian.common.equipment_set.KnightArmorSet;
import cn.teampancake.theaurorian.common.equipment_set.MysteriumWoolArmorSet;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TAEquipmentSet {

    public static final DeferredRegister<IEquipmentSet> EQUIPMENT_SET = DeferredRegister.create(ModEquipmentSet.EQUIPMENT_SET_KEY, AurorianMod.MOD_ID);
    public static final RegistryObject<IEquipmentSet> KNIGHT_ARMOR_SET = EQUIPMENT_SET.register("knight_armor_set", KnightArmorSet::new);
    public static final RegistryObject<IEquipmentSet> MYSTERIUM_WOOL_ARMOR_SET = EQUIPMENT_SET.register("mysterium_wool_armor_set", MysteriumWoolArmorSet::new);
    public static final RegistryObject<IEquipmentSet> CRYSTAL_RUNE_ARMOR_SET = EQUIPMENT_SET.register("crystal_rune_armor_set", CrystalRuneArmorSet::new);

}