package cn.teampancake.theaurorian.compat.thirst;

import cn.teampancake.theaurorian.common.registry.TAItems;
import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;

public class ThirstWasTakenCompat {

    public static void init() {
        NeoForge.EVENT_BUS.register(ThirstWasTakenCompat.class);
    }

    /**
     * Add Drinks here.
     */
    @SubscribeEvent
    public static void registerDrinks(RegisterThirstValueEvent event){
        event.addDrink(TAItems.LAVENDER_TEA.get(), 10, 14);
        event.addDrink(TAItems.SILK_BERRY_TEA.get(), 10, 14);
        event.addDrink(TAItems.LAVENDER_SEEDY_TEA.get(), 10, 14);
        event.addDrink(TAItems.PETUNIA_TEA.get(), 10, 14);
        event.addDrink(TAItems.SLEEPING_BLACK_TEA.get(),10, 14);
        event.addDrink(TAItems.BEPSI.get(), 6, 8);
        event.addDrink(TAItems.AURORIAN_SPECIALTY_DRINK.get(), 8, 14);
        event.addDrink(TAItems.MOONLIT_BLUEBERRY_SPECIALTY_DRINK.get(), 10, 14);
        event.addFood(TAItems.SILK_SHROOM_STEW.get(), 4, 4);
        event.addFood(TAItems.LAVENDER_SALAD.get(), 4, 4);
        event.addFood(TAItems.SILENT_WOOD_FRUIT.get(), 1, 4);
    }

}