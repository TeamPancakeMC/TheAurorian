package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TAPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(BuiltInRegistries.POTION, TheAurorian.MOD_ID);
    public static final DeferredHolder<Potion, Potion> OMNI = POTIONS.register("omni", () -> new Potion());

}