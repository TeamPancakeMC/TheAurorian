package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TAAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, AurorianMod.MOD_ID);
    public static final RegistryObject<Attribute> MAX_BOSS_HEALTH = register("max_boss_health", 200.0D, 0.0D, Double.MAX_VALUE, true);

    @SuppressWarnings("SameParameterValue")
    private static RegistryObject<Attribute> register(String id, double defaultValue, double min, double max, boolean watch) {
        return ATTRIBUTES.register(id, () -> new RangedAttribute(("attribute.name." + id), defaultValue, min, max).setSyncable(watch));
    }

    @SubscribeEvent
    public static void onEntityAddAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().stream().filter(type -> type.is(TAEntityTags.AURORIAN_BOSS)).forEach(type -> event.add(type, MAX_BOSS_HEALTH.get()));
    }

}