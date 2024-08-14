package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.inventory.AlchemyTableMenu;
import cn.teampancake.theaurorian.client.inventory.MoonlightForgeMenu;
import cn.teampancake.theaurorian.client.inventory.ScrapperMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TAMenus {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, AurorianMod.MOD_ID);
    public static final DeferredHolder<MenuType<?>, MenuType<MoonlightForgeMenu>> MOONLIGHT_FORGE_MENU = register(MoonlightForgeMenu::new, "moonlight_forge");
    public static final DeferredHolder<MenuType<?>, MenuType<AlchemyTableMenu>> ALCHEMY_TABLE_MENU = register(AlchemyTableMenu::new, "alchemy_table");
    public static final DeferredHolder<MenuType<?>, MenuType<ScrapperMenu>> SCRAPPER_MENU = register(ScrapperMenu::new, "scrapper");

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register(IContainerFactory<T> factory, String name) {
        return MENUS.register(name + "_menu", () -> new MenuType<>(factory, FeatureFlags.DEFAULT_FLAGS));
    }

}