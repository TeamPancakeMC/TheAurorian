package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TATabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AurorianMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("tab", () -> CreativeModeTab
            .builder().title(Component.translatable("itemGroup." + AurorianMod.MOD_ID))
            .icon(() -> new ItemStack(TAItems.AURORIAN_STEEL.get()))
            .displayItems((params, output) -> TAItems.ITEMS.getEntries()
                    .forEach(item -> output.accept(item.get()))).build());

}
