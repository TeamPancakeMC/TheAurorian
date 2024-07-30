package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TAVillagerType {
    public static final DeferredRegister<VillagerType> TA_VILLAGER_TYPES = DeferredRegister.create(BuiltInRegistries.VILLAGER_TYPE.key(),AurorianMod.MOD_ID);

    public static final RegistryObject<VillagerType> AURORIAN_PLAINS = TA_VILLAGER_TYPES.register("aurorian_plain",()-> create("aurorian_plain"));

    private static VillagerType create(String key) {
        return new VillagerType(key);
    }
}
