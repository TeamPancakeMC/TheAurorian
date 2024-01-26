package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.particle.WickParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TAParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AurorianMod.MOD_ID);
    public static final RegistryObject<SimpleParticleType> WICK = PARTICLE_TYPES.register("wick", () -> new SimpleParticleType(Boolean.FALSE));
    public static final RegistryObject<SimpleParticleType> STICK_SPIKER = PARTICLE_TYPES.register("stick_spiker", () -> new SimpleParticleType(Boolean.FALSE));
    public static final RegistryObject<SimpleParticleType> MAGIC_PURPLE = PARTICLE_TYPES.register("magic_purple", () -> new SimpleParticleType(Boolean.FALSE));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(WICK.get(), WickParticle.Provider::new);
        event.registerSpriteSet(MAGIC_PURPLE.get(), WickParticle.Provider::new);
    }

}