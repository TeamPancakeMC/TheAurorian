package cn.teampancake.theaurorian.compat;

import cn.teampancake.theaurorian.registry.TAItems;
import dev.ghen.thirst.api.ThirstHelper;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ThirstWasTakenCompat {
    public static boolean init = false;

    public ThirstWasTakenCompat(){
    }
    public static void init(){
        MinecraftForge.EVENT_BUS.register(ThirstWasTakenCompat.class);
    }

    public static void DrinkList(){
        ThirstHelper.addDrink(TAItems.LAVENDER_TEA.get(),10,14);
        ThirstHelper.addDrink(TAItems.SILKBERRY_TEA.get(),10,14);
        ThirstHelper.addDrink(TAItems.LAVENDER_SEEDY_TEA.get(),10,14);
        ThirstHelper.addDrink(TAItems.PETUNIA_TEA.get(),10,14);
        ThirstHelper.addDrink(TAItems.SLEEPING_BLACK_TEA.get(),10,14);
        ThirstHelper.addDrink(TAItems.BEPSI.get(),6,8);
        ThirstHelper.addFood(TAItems.SILK_SHROOM_STEW.get(),4,4);
    }

    @SubscribeEvent
    public static void registerDrinks(LivingEntityUseItemEvent.Finish event){
        if(!init){
            DrinkList();
            init=true;
        }
    }

    @SubscribeEvent
    public static void registerDrinks(RenderTooltipEvent.GatherComponents event){
        if(!init){
            DrinkList();
            init=true;
        }
    }

}
