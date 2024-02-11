package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TAAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, AurorianMod.MOD_ID);
    public static final RegistryObject<Attribute> DAMAGE_ACCUMULATION = register("damage_accumulation", 0.0D, 0.0D, 1024.0D);
    public static final RegistryObject<Attribute> EXHAUSTION_ACCUMULATION = register("exhaustion_accumulation", 0.0D, 0.0D, 1024.0D);
    public static final RegistryObject<Attribute> ARMOR_HURT_ACCUMULATION = register("armor_hurt_accumulation", 0.0D, 0.0D, Short.MAX_VALUE);

    @SuppressWarnings("SameParameterValue")
    private static RegistryObject<Attribute> register(String id, double defaultValue, double min, double max) {
        return ATTRIBUTES.register(id, () -> new RangedAttribute(("attribute.name." + id), defaultValue, min, max));
    }

    @SubscribeEvent
    public static void onEntityAddAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(entityType -> event.add(entityType, DAMAGE_ACCUMULATION.get()));
        event.add(EntityType.PLAYER, EXHAUSTION_ACCUMULATION.get());
        event.add(EntityType.PLAYER, ARMOR_HURT_ACCUMULATION.get());
    }

}