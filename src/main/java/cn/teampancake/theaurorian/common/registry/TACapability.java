package cn.teampancake.theaurorian.common.registry;


import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.capability.RuneCap;
import cn.teampancake.theaurorian.common.capability.ShieldCap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TACapability {

    public static final Capability<ShieldCap> SHIELD_CAP = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<RuneCap> RUNE_CAP = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ShieldCap.class);
        event.register(RuneCap.class);
    }

    @SubscribeEvent
    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof LivingEntity) {
            event.addCapability(AurorianMod.prefix("shield"), new ShieldCap.Provider(TAShields.SHIELD.getEntries()));
        }
    }

    @SubscribeEvent
    public static void attachItemCapability(AttachCapabilitiesEvent<Item> event) {
        Item item = event.getObject();
        if (item instanceof TieredItem || item instanceof ArmorItem) {
            //Todo: Add runestone's data to the item capability.
        }
    }

}