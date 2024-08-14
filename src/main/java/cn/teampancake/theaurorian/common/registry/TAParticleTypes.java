package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.particle.WickParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = TheAurorian.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TAParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, TheAurorian.MOD_ID);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WICK = PARTICLE_TYPES.register("wick", () -> new SimpleParticleType(Boolean.FALSE));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> STICK_SPIKER = PARTICLE_TYPES.register("stick_spiker", () -> new SimpleParticleType(Boolean.FALSE));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MAGIC_PURPLE = PARTICLE_TYPES.register("magic_purple", () -> new SimpleParticleType(Boolean.FALSE));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(WICK.get(), WickParticle.Provider::new);
        event.registerSpriteSet(MAGIC_PURPLE.get(), FlameParticle.Provider::new);
    }

}