package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.inventory.MoonlightForgeMenu;
import cn.teampancake.theaurorian.client.inventory.ScrapperMenu;
import cn.teampancake.theaurorian.client.gui.MoonlightForgeScreen;
import cn.teampancake.theaurorian.client.gui.ScrapperScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TAMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, AurorianMod.MOD_ID);
    public static final RegistryObject<MenuType<MoonlightForgeMenu>> MOONLIGHT_FORGE_MENU = register(MoonlightForgeMenu::new, "moonlight_forge");
    public static final RegistryObject<MenuType<ScrapperMenu>> SCRAPPER_MENU = register(ScrapperMenu::new, "scrapper");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(IContainerFactory<T> factory, String name) {
        return MENU_TYPES.register(name + "_menu", () -> IForgeMenuType.create(factory));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerScreen(FMLClientSetupEvent event) {
        MenuScreens.register(MOONLIGHT_FORGE_MENU.get(), MoonlightForgeScreen::new);
        MenuScreens.register(SCRAPPER_MENU.get(), ScrapperScreen::new);
    }

}