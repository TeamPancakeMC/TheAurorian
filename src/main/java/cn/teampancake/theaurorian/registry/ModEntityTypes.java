package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.RunestoneKeeperModel;
import cn.teampancake.theaurorian.client.renderer.entity.*;
import cn.teampancake.theaurorian.client.renderer.layers.ModModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.*;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.monster.*;
import cn.teampancake.theaurorian.common.entities.projectile.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.levelgen.Heightmap;
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

@SuppressWarnings("SpellCheckingInspection")
@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AurorianMod.MOD_ID);
    //Projectile
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
    //Animal
    public static final RegistryObject<EntityType<AurorianRabbit>> AURORIAN_RABBIT = ENTITY_TYPES.register("aurorian_rabbit",
            () -> EntityType.Builder.of(AurorianRabbit::new, MobCategory.CREATURE).sized(0.4F, 0.5F)
                    .clientTrackingRange(8).build("aurorian_rabbit"));
    public static final RegistryObject<EntityType<AurorianSheep>> AURORIAN_SHEEP = ENTITY_TYPES.register("aurorian_sheep",
            () -> EntityType.Builder.of(AurorianSheep::new, MobCategory.CREATURE).sized(0.9F, 1.3F)
                    .clientTrackingRange(10).build("aurorian_sheep"));
    public static final RegistryObject<EntityType<AurorianPig>> AURORIAN_PIG = ENTITY_TYPES.register("aurorian_pig",
            () -> EntityType.Builder.of(AurorianPig::new, MobCategory.CREATURE).sized(0.9F, 0.9F)
                    .clientTrackingRange(10).build("aurorian_pig"));
    //Monster
    public static final RegistryObject<EntityType<AurorianSlime>> AURORIAN_SLIME = ENTITY_TYPES.register("aurorian_slime",
            () -> EntityType.Builder.of(AurorianSlime::new, MobCategory.MONSTER).sized(2.04F, 2.04F)
                    .clientTrackingRange(10).build("aurorian_slime"));
    public static final RegistryObject<EntityType<DisturbedHollow>> DISTURBED_HOLLOW = ENTITY_TYPES.register("disturbed_hollow",
            () -> EntityType.Builder.of(DisturbedHollow::new, MobCategory.MONSTER).sized(0.6F, 1.95F)
                    .clientTrackingRange(8).build("disturbed_hollow"));
    public static final RegistryObject<EntityType<UndeadKnight>> UNDEAD_KNIGHT = ENTITY_TYPES.register("undead_knight",
            () -> EntityType.Builder.of(UndeadKnight::new, MobCategory.MONSTER).sized(0.78F, 2.535F)
                    .clientTrackingRange(8).fireImmune().build("undead_knight"));
    public static final RegistryObject<EntityType<Spirit>> SPIRIT = ENTITY_TYPES.register("spirit",
            () -> EntityType.Builder.of(Spirit::new, MobCategory.MONSTER).sized(0.6F, 1.95F)
                    .clientTrackingRange(8).build("spirit"));
    public static final RegistryObject<EntityType<MoonAcolyte>> MOON_ACOLYTE = ENTITY_TYPES.register("moon_acolyte",
            () -> EntityType.Builder.of(MoonAcolyte::new, MobCategory.MONSTER).sized(0.6F, 1.95F)
                    .clientTrackingRange(8).build("moon_acolyte"));
    public static final RegistryObject<EntityType<Spiderling>> SPIDERLING = ENTITY_TYPES.register("spiderling",
            () -> EntityType.Builder.of(Spiderling::new, MobCategory.MONSTER).sized(0.7F, 0.45F)
                    .clientTrackingRange(8).build("spiderling"));
    public static final RegistryObject<EntityType<CrystallineSprite>> CRYSTALLINE_SPRITE = ENTITY_TYPES.register("crystalline_sprite",
            () -> EntityType.Builder.of(CrystallineSprite::new, MobCategory.MONSTER).sized(1.0F, 1.5F)
                    .clientTrackingRange(8).build("crystalline_sprite"));
    //Boss
    public static final RegistryObject<EntityType<RunestoneKeeper>> RUNESTONE_KEEPER = ENTITY_TYPES.register("runestone_keeper",
            () -> EntityType.Builder.of(RunestoneKeeper::new, MobCategory.MONSTER).sized(1.4F, 4.2F)
                    .clientTrackingRange(8).fireImmune().build("runestone_dungeon_keeper"));
    public static final RegistryObject<EntityType<SpiderMother>> SPIDER_MOTHER = ENTITY_TYPES.register("spider_mother",
            () -> EntityType.Builder.of(SpiderMother::new, MobCategory.MONSTER).sized(2.8F, 1.8F)
                    .clientTrackingRange(8).fireImmune().build("spider_mother"));
    public static final RegistryObject<EntityType<MoonQueen>> MOON_QUEEN = ENTITY_TYPES.register("moon_queen",
            () -> EntityType.Builder.of(MoonQueen::new, MobCategory.MONSTER).sized(0.54F, 1.755F)
                    .clientTrackingRange(8).fireImmune().build("moon_queen"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CRYSTALLINE_BEAM.get(), CrystallineBeamRenderer::new);
        event.registerEntityRenderer(CERULEAN_ARROW.get(), CeruleanArrowRenderer::new);
        event.registerEntityRenderer(CRYSTAL_ARROW.get(), CrystalArrowRenderer::new);
        event.registerEntityRenderer(STICKY_SPIKER.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(WEBBING.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AURORIAN_RABBIT.get(), AurorianRabbitRenderer::new);
        event.registerEntityRenderer(AURORIAN_SHEEP.get(), AurorianSheepRenderer::new);
        event.registerEntityRenderer(AURORIAN_PIG.get(), AurorianPigRenderer::new);
        event.registerEntityRenderer(AURORIAN_SLIME.get(), AurorianSlimeRenderer::new);
        event.registerEntityRenderer(DISTURBED_HOLLOW.get(), DisturbedHollowRenderer::new);
        event.registerEntityRenderer(UNDEAD_KNIGHT.get(), UndeadKnightRenderer::new);
        event.registerEntityRenderer(SPIRIT.get(), SpiritRenderer::new);
        event.registerEntityRenderer(MOON_ACOLYTE.get(), MoonAcolyteRenderer::new);
        event.registerEntityRenderer(SPIDERLING.get(), SpiderlingRenderer::new);
        event.registerEntityRenderer(CRYSTALLINE_SPRITE.get(), CrystallineSpriteRenderer::new);
        event.registerEntityRenderer(RUNESTONE_KEEPER.get(), RunestoneKeeperRenderer::new);
        event.registerEntityRenderer(SPIDER_MOTHER.get(), SpiderMotherRenderer::new);
        event.registerEntityRenderer(MOON_QUEEN.get(), MoonQueenRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        LayerDefinition zombieLayer = LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 64);
        LayerDefinition outerLayer = LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.0F), 64, 64);
        event.registerLayerDefinition(ModModelLayers.AURORIAN_RABBIT, RabbitModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.AURORIAN_SHEEP, SheepModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.AURORIAN_PIG, () -> PigModel.createBodyLayer(new CubeDeformation(0.5F)));
        event.registerLayerDefinition(ModModelLayers.AURORIAN_SLIME, SlimeModel::createInnerBodyLayer);
        event.registerLayerDefinition(ModModelLayers.AURORIAN_SLIME_OUTER, SlimeModel::createOuterBodyLayer);
        event.registerLayerDefinition(ModModelLayers.DISTURBED_HOLLOW, () -> zombieLayer);
        event.registerLayerDefinition(ModModelLayers.UNDEAD_KNIGHT, () -> zombieLayer);
        event.registerLayerDefinition(ModModelLayers.SPIRIT, () -> zombieLayer);
        event.registerLayerDefinition(ModModelLayers.MOON_ACOLYTE, () -> zombieLayer);
        event.registerLayerDefinition(ModModelLayers.MOON_ACOLYTE_OUTER_LAYER, () -> outerLayer);
        event.registerLayerDefinition(ModModelLayers.SPIDERLING, SpiderModel::createSpiderBodyLayer);
        event.registerLayerDefinition(ModModelLayers.RUNESTONE_KEEPER, RunestoneKeeperModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SPIDER_MOTHER, SpiderModel::createSpiderBodyLayer);
        event.registerLayerDefinition(ModModelLayers.MOON_QUEEN, () -> LayerDefinition.create(
                PlayerModel.createMesh(CubeDeformation.NONE, false), 64, 64));
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        normalEntitySpawn(event, AURORIAN_RABBIT.get(), AurorianRabbit::checkAurorianRabbitSpawnRules);
        normalEntitySpawn(event, AURORIAN_SHEEP.get(), AurorianSheep::checkAurorianSheepSpawnRules);
        normalEntitySpawn(event, AURORIAN_PIG.get(), AurorianPig::checkAurorianPigSpawnRules);
        normalEntitySpawn(event, AURORIAN_SLIME.get(), AurorianSlime::checkAurorianSlimeSpawnRules);
        normalEntitySpawn(event, DISTURBED_HOLLOW.get(), Monster::checkMonsterSpawnRules);
        normalEntitySpawn(event, UNDEAD_KNIGHT.get(), UndeadKnight::checkUndeadKnightSpawnRules);
        normalEntitySpawn(event, SPIRIT.get(), Spirit::checkSpiritSpawnRules);
        normalEntitySpawn(event, MOON_ACOLYTE.get(), MoonAcolyte::checkMoonAcolyteRules);
        normalEntitySpawn(event, SPIDERLING.get(), Spiderling::checkSpiderlingSpawnRules);
        normalEntitySpawn(event, CRYSTALLINE_SPRITE.get(), CrystallineSprite::checkCrystallineSpriteRules);
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(AURORIAN_RABBIT.get(), AurorianRabbit.createAttributes().build());
        event.put(AURORIAN_SHEEP.get(), AurorianSheep.createAttributes().build());
        event.put(AURORIAN_PIG.get(), AurorianPig.createAttributes().build());
        event.put(AURORIAN_SLIME.get(), AurorianSlime.createMobAttributes().build());
        event.put(DISTURBED_HOLLOW.get(), DisturbedHollow.createAttributes().build());
        event.put(UNDEAD_KNIGHT.get(), UndeadKnight.createAttributes().build());
        event.put(SPIRIT.get(), Spirit.createAttributes().build());
        event.put(MOON_ACOLYTE.get(), MoonAcolyte.createAttributes().build());
        event.put(SPIDERLING.get(), Spiderling.createAttributes().build());
        event.put(CRYSTALLINE_SPRITE.get(), CrystallineSprite.createAttributes().build());
        event.put(RUNESTONE_KEEPER.get(), RunestoneKeeper.createAttributes().build());
        event.put(SPIDER_MOTHER.get(), SpiderMother.createAttributes().build());
        event.put(MOON_QUEEN.get(), MoonQueen.createAttributes().build());
    }

    private static <T extends Entity> void normalEntitySpawn(SpawnPlacementRegisterEvent event, EntityType<T> entityType, SpawnPlacements.SpawnPredicate<T> predicate) {
        event.register(entityType, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, predicate, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

}