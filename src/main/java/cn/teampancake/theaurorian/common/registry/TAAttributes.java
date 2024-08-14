package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TAAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, AurorianMod.MOD_ID);
    public static final DeferredHolder<Attribute, Attribute> MAX_BOSS_HEALTH = register("max_boss_health", 200.0D, 0.0D, Double.MAX_VALUE, true);

    private static DeferredHolder<Attribute, Attribute> register(String id, double defaultValue, double min, double max, boolean watch) {
        return ATTRIBUTES.register(id, () -> new RangedAttribute(("attribute.name." + id), defaultValue, min, max).setSyncable(watch));
    }

    @SubscribeEvent
    public static void onEntityAddAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().stream().filter(type -> type.is(TAEntityTags.AURORIAN_BOSS)).forEach(type -> event.add(type, MAX_BOSS_HEALTH));
    }

}