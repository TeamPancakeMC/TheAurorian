package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.armor.*;
import cn.teampancake.theaurorian.client.model.entity.circle.LunaCircleModel;
import cn.teampancake.theaurorian.client.model.entity.*;
import cn.teampancake.theaurorian.client.renderer.entity.*;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.*;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.monster.*;
import cn.teampancake.theaurorian.common.entities.projectile.*;
import cn.teampancake.theaurorian.common.entities.technical.LunaCircleEntity;
import net.minecraft.client.model.SlimeModel;
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
public class TAEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AurorianMod.MOD_ID);
    //Projectile
    public static final RegistryObject<EntityType<Arrow>> CRYSTALLINE_BEAM = ENTITY_TYPES.register("crystalline_beam",
            () -> EntityType.Builder.<Arrow>of(Arrow::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .clientTrackingRange((4)).updateInterval((20)).build("crystalline_beam"));
    public static final RegistryObject<EntityType<CeruleanArrowEntity>> CERULEAN_ARROW = ENTITY_TYPES.register("cerulean_arrow",
            () -> EntityType.Builder.<CeruleanArrowEntity>of(CeruleanArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .clientTrackingRange((4)).updateInterval((20)).build("cerulean_arrow"));
    public static final RegistryObject<EntityType<CrystalArrowEntity>> CRYSTAL_ARROW = ENTITY_TYPES.register("crystal_arrow",
            () -> EntityType.Builder.<CrystalArrowEntity>of(CrystalArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .clientTrackingRange((4)).updateInterval((20)).build("crystal_arrow"));
    public static final RegistryObject<EntityType<StickySpikerEntity>> STICKY_SPIKER = ENTITY_TYPES.register("sticky_spiker",
            () -> EntityType.Builder.<StickySpikerEntity>of(StickySpikerEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange((4)).updateInterval((10)).build("sticky_spiker"));
    public static final RegistryObject<EntityType<WebbingEntity>> WEBBING = ENTITY_TYPES.register("webbing",
            () -> EntityType.Builder.<WebbingEntity>of(WebbingEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange((4)).updateInterval((10)).build("webbing"));
    public static final RegistryObject<EntityType<EyeOfDisturbedEntity>> EYE_OF_DISTURBED = ENTITY_TYPES.register("eye_of_disturbed",
            () -> EntityType.Builder.<EyeOfDisturbedEntity>of(EyeOfDisturbedEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange((4)).updateInterval((10)).fireImmune().build("eye_of_disturbed"));
    public static final RegistryObject<EntityType<LunaCircleEntity>> LUNA_CIRCLE = ENTITY_TYPES.register("luna_circle",
            () -> EntityType.Builder.of(LunaCircleEntity::new, MobCategory.MISC).sized(5.0F, 0.1F)
                    .clientTrackingRange((4)).updateInterval((10)).build("luna_circle"));
    //Animal
    public static final RegistryObject<EntityType<BreadBeast>> BREAD_BEAST = ENTITY_TYPES.register("bread_beast",
            () -> EntityType.Builder.of(BreadBeast::new, MobCategory.CREATURE).sized(1.0F, 2.0F)
                    .clientTrackingRange((10)).build("bread_beast"));
    public static final RegistryObject<EntityType<MoonFish>> MOON_FISH = ENTITY_TYPES.register("moon_fish",
            () -> EntityType.Builder.of(MoonFish::new, MobCategory.WATER_AMBIENT).sized(0.8F, 0.5F)
                    .clientTrackingRange((4)).build("moon_fish"));
    public static final RegistryObject<EntityType<AurorianWingedFish>> AURORIAN_WINGED_FISH = ENTITY_TYPES.register("aurorian_winged_fish",
            () -> EntityType.Builder.of(AurorianWingedFish::new, MobCategory.WATER_AMBIENT).sized(0.8F, 0.5F)
                    .clientTrackingRange((4)).build("aurorian_winged_fish"));
    public static final RegistryObject<EntityType<AurorianRabbit>> AURORIAN_RABBIT = ENTITY_TYPES.register("aurorian_rabbit",
            () -> EntityType.Builder.of(AurorianRabbit::new, MobCategory.CREATURE).sized(0.4F, 0.5F)
                    .clientTrackingRange((8)).build("aurorian_rabbit"));
    public static final RegistryObject<EntityType<AurorianSheep>> AURORIAN_SHEEP = ENTITY_TYPES.register("aurorian_sheep",
            () -> EntityType.Builder.of(AurorianSheep::new, MobCategory.CREATURE).sized(1.0F, 1.3F)
                    .clientTrackingRange((10)).build("aurorian_sheep"));
    public static final RegistryObject<EntityType<AurorianPig>> AURORIAN_PIG = ENTITY_TYPES.register("aurorian_pig",
            () -> EntityType.Builder.of(AurorianPig::new, MobCategory.CREATURE).sized(0.9F, 0.9F)
                    .clientTrackingRange((10)).build("aurorian_pig"));
    public static final RegistryObject<EntityType<AurorianCow>> AURORIAN_COW = ENTITY_TYPES.register("aurorian_cow",
            () -> EntityType.Builder.of(AurorianCow::new, MobCategory.CREATURE).sized(1.5F, 1.46F)
                    .clientTrackingRange((10)).build("aurorian_cow"));
    public static final RegistryObject<EntityType<AurorianPixie>> AURORIAN_PIXIE = ENTITY_TYPES.register("aurorian_pixie",
            () -> EntityType.Builder.of(AurorianPixie::new, MobCategory.CREATURE).sized(0.6F, 0.375F)
                    .clientTrackingRange((8)).build("aurorian_pixie"));
    //Monster
    public static final RegistryObject<EntityType<AurorianSlime>> AURORIAN_SLIME = ENTITY_TYPES.register("aurorian_slime",
            () -> EntityType.Builder.of(AurorianSlime::new, MobCategory.MONSTER).sized(2.04F, 2.04F)
                    .clientTrackingRange((10)).build("aurorian_slime"));
    public static final RegistryObject<EntityType<DisturbedHollow>> DISTURBED_HOLLOW = ENTITY_TYPES.register("disturbed_hollow",
            () -> EntityType.Builder.of(DisturbedHollow::new, MobCategory.MONSTER).sized(0.6F, 1.95F)
                    .clientTrackingRange((8)).build("disturbed_hollow"));
    public static final RegistryObject<EntityType<UndeadKnight>> UNDEAD_KNIGHT = ENTITY_TYPES.register("undead_knight",
            () -> EntityType.Builder.of(UndeadKnight::new, MobCategory.MONSTER).sized(0.78F, 2.535F)
                    .clientTrackingRange((8)).fireImmune().build("undead_knight"));
    public static final RegistryObject<EntityType<Spirit>> SPIRIT = ENTITY_TYPES.register("spirit",
            () -> EntityType.Builder.of(Spirit::new, MobCategory.MONSTER).sized(0.6F, 1.95F)
                    .clientTrackingRange((8)).build("spirit"));
    public static final RegistryObject<EntityType<MoonAcolyte>> MOON_ACOLYTE = ENTITY_TYPES.register("moon_acolyte",
            () -> EntityType.Builder.of(MoonAcolyte::new, MobCategory.MONSTER).sized(0.6F, 1.95F)
                    .clientTrackingRange((8)).build("moon_acolyte"));
    public static final RegistryObject<EntityType<Spiderling>> SPIDERLING = ENTITY_TYPES.register("spiderling",
            () -> EntityType.Builder.of(Spiderling::new, MobCategory.MONSTER).sized(0.7F, 0.45F)
                    .clientTrackingRange((8)).build("spiderling"));
    public static final RegistryObject<EntityType<CrystallineSprite>> CRYSTALLINE_SPRITE = ENTITY_TYPES.register("crystalline_sprite",
            () -> EntityType.Builder.of(CrystallineSprite::new, MobCategory.MONSTER).sized(1.0F, 1.5F)
                    .clientTrackingRange((8)).build("crystalline_sprite"));
    public static final RegistryObject<EntityType<CaveDweller>> CAVE_DWELLER = ENTITY_TYPES.register("cave_dweller",
            () -> EntityType.Builder.of(CaveDweller::new, MobCategory.MONSTER).sized(2.0F, 3.0F)
                    .clientTrackingRange((8)).build("cave_dweller"));
    public static final RegistryObject<EntityType<RockHammer>> ROCK_HAMMER = ENTITY_TYPES.register("rock_hammer",
            () -> EntityType.Builder.of(RockHammer::new, MobCategory.MONSTER).sized(3.0F, 3.0F)
                    .clientTrackingRange((8)).build("rock_hammer"));
    public static final RegistryObject<EntityType<TongScorpion>> TONG_SCORPION = ENTITY_TYPES.register("tong_scorpion",
            () -> EntityType.Builder.of(TongScorpion::new, MobCategory.MONSTER).sized(2.5F, 1.5F)
                    .clientTrackingRange((8)).build("tong_scorpion"));
    //Boss
    public static final RegistryObject<EntityType<RunestoneKeeper>> RUNESTONE_KEEPER = ENTITY_TYPES.register("runestone_keeper",
            () -> EntityType.Builder.of(RunestoneKeeper::new, MobCategory.MONSTER).sized(1.4F, 4.2F)
                    .clientTrackingRange((8)).fireImmune().build("runestone_keeper"));
    public static final RegistryObject<EntityType<SpiderMother>> SPIDER_MOTHER = ENTITY_TYPES.register("spider_mother",
            () -> EntityType.Builder.of(SpiderMother::new, MobCategory.MONSTER).sized(2.8F, 1.8F)
                    .clientTrackingRange((8)).fireImmune().build("spider_mother"));
    public static final RegistryObject<EntityType<MoonQueen>> MOON_QUEEN = ENTITY_TYPES.register("moon_queen",
            () -> EntityType.Builder.of(MoonQueen::new, MobCategory.MONSTER).sized(0.54F, 1.755F)
                    .clientTrackingRange((8)).fireImmune().build("moon_queen"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CRYSTALLINE_BEAM.get(), CrystallineBeamRenderer::new);
        event.registerEntityRenderer(CERULEAN_ARROW.get(), CeruleanArrowRenderer::new);
        event.registerEntityRenderer(CRYSTAL_ARROW.get(), CrystalArrowRenderer::new);
        event.registerEntityRenderer(STICKY_SPIKER.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(WEBBING.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EYE_OF_DISTURBED.get(), EyeOfDisturbedRenderer::new);
        event.registerEntityRenderer(LUNA_CIRCLE.get(), LunaCircleRenderer::new);
        event.registerEntityRenderer(BREAD_BEAST.get(), BreadBeastRenderer::new);
        event.registerEntityRenderer(MOON_FISH.get(), MoonFishRenderer::new);
        event.registerEntityRenderer(AURORIAN_WINGED_FISH.get(), AurorianWingedFishRenderer::new);
        event.registerEntityRenderer(AURORIAN_RABBIT.get(), AurorianRabbitRenderer::new);
        event.registerEntityRenderer(AURORIAN_SHEEP.get(), AurorianSheepRenderer::new);
        event.registerEntityRenderer(AURORIAN_PIG.get(), AurorianPigRenderer::new);
        event.registerEntityRenderer(AURORIAN_COW.get(), AurorianCowRenderer::new);
        event.registerEntityRenderer(AURORIAN_PIXIE.get(), AurorianPixieRenderer::new);
        event.registerEntityRenderer(AURORIAN_SLIME.get(), AurorianSlimeRenderer::new);
        event.registerEntityRenderer(DISTURBED_HOLLOW.get(), DisturbedHollowRenderer::new);
        event.registerEntityRenderer(UNDEAD_KNIGHT.get(), UndeadKnightRenderer::new);
        event.registerEntityRenderer(SPIRIT.get(), SpiritRenderer::new);
        event.registerEntityRenderer(MOON_ACOLYTE.get(), MoonAcolyteRenderer::new);
        event.registerEntityRenderer(SPIDERLING.get(), SpiderlingRenderer::new);
        event.registerEntityRenderer(CRYSTALLINE_SPRITE.get(), CrystallineSpriteRenderer::new);
        event.registerEntityRenderer(CAVE_DWELLER.get(), CaveDwellerRenderer::new);
        event.registerEntityRenderer(ROCK_HAMMER.get(), RockHammerRenderer::new);
        event.registerEntityRenderer(TONG_SCORPION.get(), TongScorpionRenderer::new);
        event.registerEntityRenderer(RUNESTONE_KEEPER.get(), RunestoneKeeperRenderer::new);
        event.registerEntityRenderer(SPIDER_MOTHER.get(), SpiderMotherRenderer::new);
        event.registerEntityRenderer(MOON_QUEEN.get(), MoonQueenRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TAModelLayers.LUNA_CIRCLE, LunaCircleModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.BREAD_BEAST, BreadBeastModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.MOON_FISH, MoonFishModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_WINGED_FISH, AurorianWingedFishModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_RABBIT, AurorianRabbitModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_SHEEP, AurorianSheepModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_PIG, AurorianPigModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_COW, AurorianCowModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_SHEEP_BABY, AurorianSheepBabyModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_PIG_BABY, AurorianPigBabyModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_COW_BABY, AurorianCowBabyModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_PIXIE, AurorianPixieModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_SLIME, SlimeModel::createInnerBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_SLIME_OUTER, SlimeModel::createOuterBodyLayer);
        event.registerLayerDefinition(TAModelLayers.DISTURBED_HOLLOW, DisturbedHollowModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.UNDEAD_KNIGHT, UndeadKnightModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.SPIRIT, SpiritModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.MOON_ACOLYTE, MoonAcolyteModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.SPIDERLING, SpiderlingModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.CRYSTALLINE_SPRITE, CrystallineSpriteModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.CAVE_DWELLER, CaveDwellerModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.ROCK_HAMMER, RockHammerModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.TONG_SCORPION, TongScorpionModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.RUNESTONE_KEEPER, RunestoneKeeperModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.RUNESTONE_BOOKS, RunestoneBookModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.SPIDER_MOTHER, SpiderMotherModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.MOON_QUEEN, MoonQueenModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_STEEL_ARMOR, AurorianSteelArmorModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.CERULEAN_ARMOR, CeruleanArmorModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.KNIGHT_ARMOR, KnightArmorModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.SPECTRAL_ARMOR, SpectralArmorModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.MYSTERIUM_ARMOR, MysteriumWoolArmorModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        normalEntitySpawn(event, BREAD_BEAST.get(), SpawnPlacements.Type.ON_GROUND, BreadBeast::checkBreadBeastSpawnRules);
        normalEntitySpawn(event, MOON_FISH.get(), SpawnPlacements.Type.IN_WATER, AbstractAurorianFish::checkAbstractAurorianFishSpawnRules);
        normalEntitySpawn(event, AURORIAN_WINGED_FISH.get(), SpawnPlacements.Type.IN_WATER, AbstractAurorianFish::checkAbstractAurorianFishSpawnRules);
        normalEntitySpawn(event, AURORIAN_RABBIT.get(), SpawnPlacements.Type.ON_GROUND, AurorianRabbit::checkAurorianRabbitSpawnRules);
        normalEntitySpawn(event, AURORIAN_SHEEP.get(), SpawnPlacements.Type.ON_GROUND, AurorianSheep::checkAurorianSheepSpawnRules);
        normalEntitySpawn(event, AURORIAN_PIG.get(), SpawnPlacements.Type.ON_GROUND, AurorianPig::checkAurorianPigSpawnRules);
        normalEntitySpawn(event, AURORIAN_COW.get(), SpawnPlacements.Type.ON_GROUND, AurorianCow::checkAurorianCowSpawnRules);
        normalEntitySpawn(event, AURORIAN_SLIME.get(), SpawnPlacements.Type.ON_GROUND, AurorianSlime::checkAurorianSlimeSpawnRules);
        normalEntitySpawn(event, DISTURBED_HOLLOW.get(), SpawnPlacements.Type.ON_GROUND, Monster::checkMonsterSpawnRules);
        normalEntitySpawn(event, UNDEAD_KNIGHT.get(), SpawnPlacements.Type.ON_GROUND, UndeadKnight::checkUndeadKnightSpawnRules);
        normalEntitySpawn(event, SPIRIT.get(), SpawnPlacements.Type.ON_GROUND, Spirit::checkSpiritSpawnRules);
        normalEntitySpawn(event, MOON_ACOLYTE.get(), SpawnPlacements.Type.ON_GROUND, MoonAcolyte::checkMoonAcolyteRules);
        normalEntitySpawn(event, SPIDERLING.get(), SpawnPlacements.Type.ON_GROUND, Spiderling::checkSpiderlingSpawnRules);
        normalEntitySpawn(event, CRYSTALLINE_SPRITE.get(), SpawnPlacements.Type.ON_GROUND, CrystallineSprite::checkCrystallineSpriteRules);
        normalEntitySpawn(event, CAVE_DWELLER.get(), SpawnPlacements.Type.ON_GROUND, CaveDweller::checkMonsterSpawnRules);
        normalEntitySpawn(event, ROCK_HAMMER.get(), SpawnPlacements.Type.ON_GROUND, RockHammer::checkRockHammerSpawnRules);
        normalEntitySpawn(event, TONG_SCORPION.get(), SpawnPlacements.Type.ON_GROUND, TongScorpion::checkTongScorpionSpawnRules);
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(BREAD_BEAST.get(), BreadBeast.createAttributes().build());
        event.put(MOON_FISH.get(), MoonFish.createAttributes().build());
        event.put(AURORIAN_WINGED_FISH.get(), AurorianWingedFish.createAttributes().build());
        event.put(AURORIAN_RABBIT.get(), AurorianRabbit.createAttributes().build());
        event.put(AURORIAN_SHEEP.get(), AurorianSheep.createAttributes().build());
        event.put(AURORIAN_PIG.get(), AurorianPig.createAttributes().build());
        event.put(AURORIAN_COW.get(), AurorianCow.createAttributes().build());
        event.put(AURORIAN_PIXIE.get(), AurorianPixie.createAttributes().build());
        event.put(AURORIAN_SLIME.get(), AurorianSlime.createMobAttributes().build());
        event.put(DISTURBED_HOLLOW.get(), DisturbedHollow.createAttributes().build());
        event.put(UNDEAD_KNIGHT.get(), UndeadKnight.createAttributes().build());
        event.put(SPIRIT.get(), Spirit.createAttributes().build());
        event.put(MOON_ACOLYTE.get(), MoonAcolyte.createAttributes().build());
        event.put(SPIDERLING.get(), Spiderling.createAttributes().build());
        event.put(CRYSTALLINE_SPRITE.get(), CrystallineSprite.createAttributes().build());
        event.put(CAVE_DWELLER.get(), CaveDweller.createAttributes().build());
        event.put(ROCK_HAMMER.get(), RockHammer.createAttributes().build());
        event.put(TONG_SCORPION.get(), TongScorpion.createAttributes().build());
        event.put(RUNESTONE_KEEPER.get(), RunestoneKeeper.createAttributes().build());
        event.put(SPIDER_MOTHER.get(), SpiderMother.createAttributes().build());
        event.put(MOON_QUEEN.get(), MoonQueen.createAttributes().build());
    }

    private static <T extends Entity> void normalEntitySpawn(SpawnPlacementRegisterEvent event, EntityType<T> entityType, SpawnPlacements.Type type, SpawnPlacements.SpawnPredicate<T> predicate) {
        event.register(entityType, type, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, predicate, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

}