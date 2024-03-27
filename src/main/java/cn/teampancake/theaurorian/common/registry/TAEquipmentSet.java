package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.pancakelib.api.IEquipmentSet;
import cn.teampancake.pancakelib.common.init.ModEquipmentSet;
import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.equipment_set.KnightArmorSet;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TAEquipmentSet {
    public static final DeferredRegister<IEquipmentSet> EQUIPMENT_SET = DeferredRegister.create(ModEquipmentSet.EQUIPMENT_SET_KEY, AurorianMod.MOD_ID);

    public static final RegistryObject<IEquipmentSet> KNIGHT_ARMOR_SET = EQUIPMENT_SET.register("knight_armor_set", KnightArmorSet::new);
}
