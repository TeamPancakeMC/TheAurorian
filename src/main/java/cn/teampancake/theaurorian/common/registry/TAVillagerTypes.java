package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.npc.VillagerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TAVillagerTypes {

    public static final DeferredRegister<VillagerType> VILLAGER_TYPES = DeferredRegister.create(BuiltInRegistries.VILLAGER_TYPE.key(), TheAurorian.MOD_ID);
    public static final DeferredHolder<VillagerType, VillagerType> AURORIAN_PLAINS = VILLAGER_TYPES.register("aurorian_plain", () -> new VillagerType("aurorian_plain"));

}