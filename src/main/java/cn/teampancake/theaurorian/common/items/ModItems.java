package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AurorianMod.MOD_ID);

    public static final RegistryObject<Item> ABSORPTION_ORB;

    public ModItems() {
    }

    static {
        ABSORPTION_ORB = ITEMS.register("absorptionorb", AbsorptionOrbItem::new);
    }
}