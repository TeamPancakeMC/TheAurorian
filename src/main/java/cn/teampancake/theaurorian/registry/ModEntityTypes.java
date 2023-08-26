package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.renderer.CeruleanArrowRenderer;
import cn.teampancake.theaurorian.client.renderer.CrystalArrowRenderer;
import cn.teampancake.theaurorian.client.renderer.CrystallineBeamRenderer;
import cn.teampancake.theaurorian.common.entities.projectile.CeruleanArrowEntity;
import cn.teampancake.theaurorian.common.entities.projectile.CrystalArrowEntity;
import cn.teampancake.theaurorian.common.entities.projectile.StickySpikerEntity;
import cn.teampancake.theaurorian.common.entities.projectile.WebbingEntity;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AurorianMod.MOD_ID);
    public static final RegistryObject<EntityType<Arrow>> CRYSTALLINE_BEAM = ENTITY_TYPES.register("crystalline_beam",
            () -> EntityType.Builder.<Arrow>of(Arrow::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .clientTrackingRange(4).updateInterval(20).build("crystalline_beam"));
    public static final RegistryObject<EntityType<CeruleanArrowEntity>> CERULEAN_ARROW = ENTITY_TYPES.register("cerulean_arrow",
            () -> EntityType.Builder.<CeruleanArrowEntity>of(CeruleanArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .clientTrackingRange(4).updateInterval(20).build("cerulean_arrow"));
    public static final RegistryObject<EntityType<CrystalArrowEntity>> CRYSTAL_ARROW = ENTITY_TYPES.register("crystal_arrow",
            () -> EntityType.Builder.<CrystalArrowEntity>of(CrystalArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .clientTrackingRange(4).updateInterval(20).build("crystal_arrow"));
    public static final RegistryObject<EntityType<StickySpikerEntity>> STICKY_SPIKER = ENTITY_TYPES.register("sticky_spiker",
            () -> EntityType.Builder.<StickySpikerEntity>of(StickySpikerEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange(4).updateInterval(10).build("sticky_spiker"));
    public static final RegistryObject<EntityType<WebbingEntity>> WEBBING = ENTITY_TYPES.register("webbing",
            () -> EntityType.Builder.<WebbingEntity>of(WebbingEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange(4).updateInterval(10).build("webbing"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CRYSTALLINE_BEAM.get(), CrystallineBeamRenderer::new);
        event.registerEntityRenderer(CERULEAN_ARROW.get(), CeruleanArrowRenderer::new);
        event.registerEntityRenderer(CRYSTAL_ARROW.get(), CrystalArrowRenderer::new);
        event.registerEntityRenderer(STICKY_SPIKER.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(WEBBING.get(), ThrownItemRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {

    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {

    }

}