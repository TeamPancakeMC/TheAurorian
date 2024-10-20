package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.*;
import cn.teampancake.theaurorian.client.model.entity.armor.*;
import cn.teampancake.theaurorian.client.renderer.entity.*;
import cn.teampancake.theaurorian.client.renderer.entity.abiotic.*;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.TASpawnRules;
import cn.teampancake.theaurorian.common.entities.animal.*;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.boss.MoonlightKnight;
import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.monster.*;
import cn.teampancake.theaurorian.common.entities.npc.AurorianVillager;
import cn.teampancake.theaurorian.common.entities.projectile.*;
import cn.teampancake.theaurorian.common.entities.projectile.blade_waves.BladeWave;
import cn.teampancake.theaurorian.common.entities.technical.LunaCircleEntity;
import cn.teampancake.theaurorian.common.entities.technical.SitEntity;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@EventBusSubscriber(modid = TheAurorian.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TAEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, TheAurorian.MOD_ID);
    //Projectile
    public static final DeferredHolder<EntityType<?>, EntityType<EyeOfDisturbedEntity>> EYE_OF_DISTURBED = ENTITY_TYPES.register("eye_of_disturbed",
            () -> EntityType.Builder.<EyeOfDisturbedEntity>of(EyeOfDisturbedEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange((4)).updateInterval((10)).fireImmune().build("eye_of_disturbed"));
    public static final DeferredHolder<EntityType<?>, EntityType<Arrow>> CRYSTALLINE_BEAM = ENTITY_TYPES.register("crystalline_beam",
            () -> EntityType.Builder.<Arrow>of(Arrow::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .clientTrackingRange((4)).updateInterval((20)).build("crystalline_beam"));
    public static final DeferredHolder<EntityType<?>, EntityType<CeruleanArrowEntity>> CERULEAN_ARROW = ENTITY_TYPES.register("cerulean_arrow",
            () -> EntityType.Builder.<CeruleanArrowEntity>of(CeruleanArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .clientTrackingRange((4)).updateInterval((20)).build("cerulean_arrow"));
    public static final DeferredHolder<EntityType<?>, EntityType<CrystalArrowEntity>> CRYSTAL_ARROW = ENTITY_TYPES.register("crystal_arrow",
            () -> EntityType.Builder.<CrystalArrowEntity>of(CrystalArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .clientTrackingRange((4)).updateInterval((20)).build("crystal_arrow"));
    public static final DeferredHolder<EntityType<?>, EntityType<StickySpikerEntity>> STICKY_SPIKER = ENTITY_TYPES.register("sticky_spiker",
            () -> EntityType.Builder.<StickySpikerEntity>of(StickySpikerEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange((4)).updateInterval((10)).build("sticky_spiker"));
    public static final DeferredHolder<EntityType<?>, EntityType<WebbingEntity>> WEBBING = ENTITY_TYPES.register("webbing",
            () -> EntityType.Builder.<WebbingEntity>of(WebbingEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange((4)).updateInterval((10)).build("webbing"));
    public static final DeferredHolder<EntityType<?>, EntityType<SitEntity>> SIT = ENTITY_TYPES.register("sit",
            () -> EntityType.Builder.<SitEntity>of(SitEntity::new, MobCategory.MISC).sized(0.1F, 0.1F)
                    .clientTrackingRange((4)).updateInterval((10)).build("sit"));
    public static final DeferredHolder<EntityType<?>, EntityType<LunaCircleEntity>> LUNA_CIRCLE = ENTITY_TYPES.register("luna_circle",
            () -> EntityType.Builder.of(LunaCircleEntity::new, MobCategory.MISC).sized(5.0F, 0.1F)
                    .clientTrackingRange((4)).updateInterval((10)).build("luna_circle"));
    public static final DeferredHolder<EntityType<?>, EntityType<FallingFireball>> FALLING_FIREBALL = ENTITY_TYPES.register("falling_fireball",
            () -> EntityType.Builder.<FallingFireball>of(FallingFireball::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .clientTrackingRange((4)).updateInterval((10)).build("falling_fireball"));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrownAxe>> THROWN_AXE = ENTITY_TYPES.register("thrown_axe",
            () -> EntityType.Builder.<ThrownAxe>of(ThrownAxe::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .clientTrackingRange((4)).updateInterval((10)).fireImmune().build("thrown_axe"));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrownShuriken>> THROWN_SHURIKEN = ENTITY_TYPES.register("thrown_shuriken",
            () -> EntityType.Builder.<ThrownShuriken>of(ThrownShuriken::new, MobCategory.MISC).sized(0.3F, 0.3F)
                    .clientTrackingRange((4)).updateInterval((10)).fireImmune().build("thrown_shuriken"));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrownSlateBrick>> THROWN_SLATE_BRICK = ENTITY_TYPES.register("thrown_slate_brick",
            () -> EntityType.Builder.<ThrownSlateBrick>of(ThrownSlateBrick::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange((4)).updateInterval((10)).fireImmune().build("thrown_slate_brick"));
    public static final DeferredHolder<EntityType<?>, EntityType<UnstableCrystal>> UNSTABLE_CRYSTAL = ENTITY_TYPES.register("unstable_crystal",
            () -> EntityType.Builder.<UnstableCrystal>of(UnstableCrystal::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange((4)).updateInterval((10)).fireImmune().build("unstable_crystal"));
    public static final DeferredHolder<EntityType<?>, EntityType<BladeWave>> BLADE_WAVE = ENTITY_TYPES.register("blade_wave",
            () -> EntityType.Builder.<BladeWave>of(BladeWave::new, MobCategory.MISC).sized(5.5F, 1.5F)
                    .clientTrackingRange((4)).updateInterval((10)).fireImmune().build("blade_wave"));
    public static final DeferredHolder<EntityType<?>, EntityType<MoonQueenSword>> MOON_QUEEN_SWORD = ENTITY_TYPES.register("moon_queen_sword",
            () -> EntityType.Builder.<MoonQueenSword>of(MoonQueenSword::new, MobCategory.MISC).sized(1.0F, 1.0F)
                    .clientTrackingRange((4)).updateInterval((10)).fireImmune().build("moon_queen_sword"));
    //NPC
    public static final DeferredHolder<EntityType<?>, EntityType<AurorianVillager>> AURORIAN_VILLAGER = ENTITY_TYPES.register("aurorian_villager",
            () -> EntityType.Builder.of(AurorianVillager::new, MobCategory.CREATURE).sized(0.6F, 1.85F)
                    .clientTrackingRange((8)).build("aurorian_villager"));
    //Animal
    public static final DeferredHolder<EntityType<?>, EntityType<BreadBeast>> BREAD_BEAST = ENTITY_TYPES.register("bread_beast",
            () -> EntityType.Builder.of(BreadBeast::new, MobCategory.CREATURE).sized(1.0F, 2.0F)
                    .clientTrackingRange((10)).build("bread_beast"));
    public static final DeferredHolder<EntityType<?>, EntityType<IcefieldDeer>> ICEFIELD_DEER = ENTITY_TYPES.register("icefield_deer",
            () -> EntityType.Builder.of(IcefieldDeer::new, MobCategory.CREATURE).sized(1.0F, 2.0F)
                    .clientTrackingRange((10)).build("icefield_deer"));
    public static final DeferredHolder<EntityType<?>, EntityType<BlueTailWolf>> BLUE_TAIL_WOLF = ENTITY_TYPES.register("blue_tail_wolf",
            () -> EntityType.Builder.of(BlueTailWolf::new, MobCategory.CREATURE).sized(1.2F, 1.0F)
                    .clientTrackingRange((10)).build("blue_tail_wolf"));
    public static final DeferredHolder<EntityType<?>, EntityType<MoonFish>> MOON_FISH = ENTITY_TYPES.register("moon_fish",
            () -> EntityType.Builder.of(MoonFish::new, MobCategory.WATER_AMBIENT).sized(0.8F, 0.5F)
                    .clientTrackingRange((4)).build("moon_fish"));
    public static final DeferredHolder<EntityType<?>, EntityType<AurorianWingedFish>> AURORIAN_WINGED_FISH = ENTITY_TYPES.register("aurorian_winged_fish",
            () -> EntityType.Builder.of(AurorianWingedFish::new, MobCategory.WATER_AMBIENT).sized(0.8F, 0.5F)
                    .clientTrackingRange((4)).build("aurorian_winged_fish"));
    public static final DeferredHolder<EntityType<?>, EntityType<AurorianRabbit>> AURORIAN_RABBIT = ENTITY_TYPES.register("aurorian_rabbit",
            () -> EntityType.Builder.of(AurorianRabbit::new, MobCategory.CREATURE).sized(0.4F, 0.5F)
                    .clientTrackingRange((8)).build("aurorian_rabbit"));
    public static final DeferredHolder<EntityType<?>, EntityType<AurorianSheep>> AURORIAN_SHEEP = ENTITY_TYPES.register("aurorian_sheep",
            () -> EntityType.Builder.of(AurorianSheep::new, MobCategory.CREATURE).sized(1.0F, 1.3F)
                    .clientTrackingRange((10)).build("aurorian_sheep"));
    public static final DeferredHolder<EntityType<?>, EntityType<AurorianPig>> AURORIAN_PIG = ENTITY_TYPES.register("aurorian_pig",
            () -> EntityType.Builder.of(AurorianPig::new, MobCategory.CREATURE).sized(0.9F, 0.9F)
                    .clientTrackingRange((10)).build("aurorian_pig"));
    public static final DeferredHolder<EntityType<?>, EntityType<AurorianCow>> AURORIAN_COW = ENTITY_TYPES.register("aurorian_cow",
            () -> EntityType.Builder.of(AurorianCow::new, MobCategory.CREATURE).sized(1.5F, 1.46F)
                    .clientTrackingRange((10)).build("aurorian_cow"));
    public static final DeferredHolder<EntityType<?>, EntityType<AurorianPixie>> AURORIAN_PIXIE = ENTITY_TYPES.register("aurorian_pixie",
            () -> EntityType.Builder.of(AurorianPixie::new, MobCategory.CREATURE).sized(0.6F, 0.375F)
                    .clientTrackingRange((8)).build("aurorian_pixie"));
    public static final DeferredHolder<EntityType<?>, EntityType<SnowTundraGiantCrab>> SNOW_TUNDRA_GIANT_CRAB = ENTITY_TYPES.register("snow_tundra_giant_crab",
            () -> EntityType.Builder.of(SnowTundraGiantCrab::new, MobCategory.CREATURE).sized(4.0F, 3.5F)
                    .clientTrackingRange((8)).build("snow_tundra_giant_crab"));
    //Monster
    public static final DeferredHolder<EntityType<?>, EntityType<AurorianSlime>> AURORIAN_SLIME = ENTITY_TYPES.register("aurorian_slime",
            () -> EntityType.Builder.of(AurorianSlime::new, MobCategory.MONSTER).sized(2.04F, 2.04F)
                    .clientTrackingRange((10)).build("aurorian_slime"));
    public static final DeferredHolder<EntityType<?>, EntityType<DisturbedHollow>> DISTURBED_HOLLOW = ENTITY_TYPES.register("disturbed_hollow",
            () -> EntityType.Builder.of(DisturbedHollow::new, MobCategory.MONSTER).sized(0.6F, 1.95F)
                    .clientTrackingRange((8)).build("disturbed_hollow"));
    public static final DeferredHolder<EntityType<?>, EntityType<UndeadKnight>> UNDEAD_KNIGHT = ENTITY_TYPES.register("undead_knight",
            () -> EntityType.Builder.of(UndeadKnight::new, MobCategory.MONSTER).sized(0.78F, 2.535F)
                    .clientTrackingRange((8)).fireImmune().build("undead_knight"));
    public static final DeferredHolder<EntityType<?>, EntityType<Spirit>> SPIRIT = ENTITY_TYPES.register("spirit",
            () -> EntityType.Builder.of(Spirit::new, MobCategory.MONSTER).sized(0.6F, 1.95F)
                    .clientTrackingRange((8)).build("spirit"));
    public static final DeferredHolder<EntityType<?>, EntityType<MoonAcolyte>> MOON_ACOLYTE = ENTITY_TYPES.register("moon_acolyte",
            () -> EntityType.Builder.of(MoonAcolyte::new, MobCategory.MONSTER).sized(0.6F, 1.95F)
                    .clientTrackingRange((8)).build("moon_acolyte"));
    public static final DeferredHolder<EntityType<?>, EntityType<Spiderling>> SPIDERLING = ENTITY_TYPES.register("spiderling",
            () -> EntityType.Builder.of(Spiderling::new, MobCategory.MONSTER).sized(0.7F, 0.45F).eyeHeight((0.45F))
                    .clientTrackingRange((8)).build("spiderling"));
    public static final DeferredHolder<EntityType<?>, EntityType<SpiderlingCrystalShell>> SPIDERLING_CRYSTAL_SHELL = ENTITY_TYPES.register("spiderling_crystal_shell",
            () -> EntityType.Builder.of(SpiderlingCrystalShell::new, MobCategory.MONSTER).sized(1.5F, 1.5F)
                    .clientTrackingRange((8)).build("spiderling_crystal_shell"));
    public static final DeferredHolder<EntityType<?>, EntityType<SpiderlingWallClimber>> SPIDERLING_WALL_CLIMBER = ENTITY_TYPES.register("spiderling_wall_climber",
            () -> EntityType.Builder.of(SpiderlingWallClimber::new, MobCategory.MONSTER).sized(1.6F, 0.5F)
                    .clientTrackingRange((8)).build("spiderling_wall_climber"));
    public static final DeferredHolder<EntityType<?>, EntityType<GiantCrystalSpider>> GIANT_CRYSTAL_SPIDER = ENTITY_TYPES.register("giant_crystal_spider",
            () -> EntityType.Builder.of(GiantCrystalSpider::new, MobCategory.MONSTER).sized(1.5F, 3.5F)
                    .clientTrackingRange((8)).build("giant_crystal_spider"));
    public static final DeferredHolder<EntityType<?>, EntityType<RuneSpider>> RUNE_SPIDER = ENTITY_TYPES.register("rune_spider",
            () -> EntityType.Builder.of(RuneSpider::new, MobCategory.MONSTER).sized(1.5F, 3.5F)
                    .clientTrackingRange((8)).build("rune_spider"));
    public static final DeferredHolder<EntityType<?>, EntityType<CrystallineSprite>> CRYSTALLINE_SPRITE = ENTITY_TYPES.register("crystalline_sprite",
            () -> EntityType.Builder.of(CrystallineSprite::new, MobCategory.MONSTER).sized(1.0F, 1.5F)
                    .clientTrackingRange((8)).build("crystalline_sprite"));
    public static final DeferredHolder<EntityType<?>, EntityType<CaveDweller>> CAVE_DWELLER = ENTITY_TYPES.register("cave_dweller",
            () -> EntityType.Builder.of(CaveDweller::new, MobCategory.MONSTER).sized(2.0F, 3.0F)
                    .clientTrackingRange((8)).build("cave_dweller"));
    public static final DeferredHolder<EntityType<?>, EntityType<RockHammer>> ROCK_HAMMER = ENTITY_TYPES.register("rock_hammer",
            () -> EntityType.Builder.of(RockHammer::new, MobCategory.MONSTER).sized(3.0F, 3.0F)
                    .clientTrackingRange((8)).build("rock_hammer"));
    public static final DeferredHolder<EntityType<?>, EntityType<TongScorpion>> TONG_SCORPION = ENTITY_TYPES.register("tong_scorpion",
            () -> EntityType.Builder.of(TongScorpion::new, MobCategory.MONSTER).sized(2.5F, 1.5F)
                    .clientTrackingRange((8)).build("tong_scorpion"));
    public static final DeferredHolder<EntityType<?>, EntityType<FlowerLeech>> FLOWER_LEECH = ENTITY_TYPES.register("flower_leech",
            () -> EntityType.Builder.of(FlowerLeech::new, MobCategory.MONSTER).sized(1.0F, 2.0F)
                    .clientTrackingRange((8)).build("flower_leech"));
    public static final DeferredHolder<EntityType<?>, EntityType<ForgottenMagicBook>> FORGOTTEN_MAGIC_BOOK = ENTITY_TYPES.register("forgotten_magic_book",
            () -> EntityType.Builder.of(ForgottenMagicBook::new, MobCategory.MONSTER).sized(1.0F, 2.5F)
                    .clientTrackingRange((8)).build("forgotten_magic_book"));
    public static final DeferredHolder<EntityType<?>, EntityType<HyphaWalkingMushroom>> HYPHA_WALKING_MUSHROOM = ENTITY_TYPES.register("hypha_walking_mushroom",
            () -> EntityType.Builder.of(HyphaWalkingMushroom::new, MobCategory.MONSTER).sized(1.0F, 1.5F)
                    .clientTrackingRange((8)).build("hypha_walking_mushroom"));
    //Boss
    public static final DeferredHolder<EntityType<?>, EntityType<MoonlightKnight>> MOONLIGHT_KNIGHT = ENTITY_TYPES.register("moonlight_knight",
            () -> EntityType.Builder.of(MoonlightKnight::new, MobCategory.MONSTER).sized(3.0F, 5.0F)
                    .clientTrackingRange((8)).fireImmune().build("moonlight_knight"));
    public static final DeferredHolder<EntityType<?>, EntityType<RunestoneKeeper>> RUNESTONE_KEEPER = ENTITY_TYPES.register("runestone_keeper",
            () -> EntityType.Builder.of(RunestoneKeeper::new, MobCategory.MONSTER).sized(1.4F, 4.2F)
                    .clientTrackingRange((8)).fireImmune().build("runestone_keeper"));
    public static final DeferredHolder<EntityType<?>, EntityType<SpiderMother>> SPIDER_MOTHER = ENTITY_TYPES.register("spider_mother",
            () -> EntityType.Builder.of(SpiderMother::new, MobCategory.MONSTER).sized(4.5F, 3.0F).eyeHeight((1.1F))
                    .clientTrackingRange((8)).fireImmune().build("spider_mother"));
    public static final DeferredHolder<EntityType<?>, EntityType<MoonQueen>> MOON_QUEEN = ENTITY_TYPES.register("moon_queen",
            () -> EntityType.Builder.of(MoonQueen::new, MobCategory.MONSTER).sized(0.54F, 1.755F)
                    .clientTrackingRange((8)).fireImmune().build("moon_queen"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EYE_OF_DISTURBED.get(), EyeOfDisturbedRenderer::new);
        event.registerEntityRenderer(CRYSTALLINE_BEAM.get(), CrystallineBeamRenderer::new);
        event.registerEntityRenderer(CERULEAN_ARROW.get(), CeruleanArrowRenderer::new);
        event.registerEntityRenderer(CRYSTAL_ARROW.get(), CrystalArrowRenderer::new);
        event.registerEntityRenderer(STICKY_SPIKER.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(WEBBING.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(SIT.get(), SitRenderer::new);
        event.registerEntityRenderer(LUNA_CIRCLE.get(), LunaCircleRenderer::new);
        event.registerEntityRenderer(MOON_QUEEN_SWORD.get(), MoonQueenSwordRenderer::new);
        event.registerEntityRenderer(FALLING_FIREBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(THROWN_AXE.get(), ThrownAxeRenderer::new);
        event.registerEntityRenderer(THROWN_SHURIKEN.get(), ThrownShurikenRenderer::new);
        event.registerEntityRenderer(THROWN_SLATE_BRICK.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UNSTABLE_CRYSTAL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(BLADE_WAVE.get(), BladeWaveRenderer::new);
        event.registerEntityRenderer(BREAD_BEAST.get(), BreadBeastRenderer::new);
        event.registerEntityRenderer(ICEFIELD_DEER.get(), IcefieldDeerRenderer::new);
        event.registerEntityRenderer(BLUE_TAIL_WOLF.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(BLUE_TAIL_WOLF.getId(), Boolean.TRUE)));
        event.registerEntityRenderer(AURORIAN_VILLAGER.get(), AurorianVillagerRenderer::new);
        event.registerEntityRenderer(MOON_FISH.get(), MoonFishRenderer::new);
        event.registerEntityRenderer(AURORIAN_WINGED_FISH.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(AURORIAN_WINGED_FISH.getId())));
        event.registerEntityRenderer(AURORIAN_RABBIT.get(), AurorianRabbitRenderer::new);
        event.registerEntityRenderer(AURORIAN_SHEEP.get(), AurorianSheepRenderer::new);
        event.registerEntityRenderer(AURORIAN_PIG.get(), AurorianPigRenderer::new);
        event.registerEntityRenderer(AURORIAN_COW.get(), AurorianCowRenderer::new);
        event.registerEntityRenderer(AURORIAN_PIXIE.get(), AurorianPixieRenderer::new);
        event.registerEntityRenderer(AURORIAN_SLIME.get(), AurorianSlimeRenderer::new);
        event.registerEntityRenderer(DISTURBED_HOLLOW.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(DISTURBED_HOLLOW.getId(), Boolean.TRUE)));
        event.registerEntityRenderer(UNDEAD_KNIGHT.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(UNDEAD_KNIGHT.getId(), Boolean.TRUE)));
        event.registerEntityRenderer(SPIRIT.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(SPIRIT.getId(), Boolean.TRUE)));
        event.registerEntityRenderer(MOON_ACOLYTE.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(MOON_ACOLYTE.getId(), Boolean.TRUE)));
        event.registerEntityRenderer(SPIDERLING.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(SPIDERLING.getId(), Boolean.TRUE)));
        event.registerEntityRenderer(SPIDERLING_CRYSTAL_SHELL.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(SPIDERLING_CRYSTAL_SHELL.getId(), Boolean.TRUE)));
        event.registerEntityRenderer(SPIDERLING_WALL_CLIMBER.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(SPIDERLING_WALL_CLIMBER.getId(), Boolean.TRUE)));
        event.registerEntityRenderer(GIANT_CRYSTAL_SPIDER.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(GIANT_CRYSTAL_SPIDER.getId(), Boolean.TRUE)));
        event.registerEntityRenderer(RUNE_SPIDER.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(RUNE_SPIDER.getId(), Boolean.TRUE)));
        event.registerEntityRenderer(CRYSTALLINE_SPRITE.get(), CrystallineSpriteRenderer::new);
        event.registerEntityRenderer(CAVE_DWELLER.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(CAVE_DWELLER.getId())));
        event.registerEntityRenderer(ROCK_HAMMER.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(ROCK_HAMMER.getId())));
        event.registerEntityRenderer(TONG_SCORPION.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(TONG_SCORPION.getId())));
        event.registerEntityRenderer(SNOW_TUNDRA_GIANT_CRAB.get(), context -> new GeoEntityRenderer<>(
                context, new DefaultedEntityGeoModel<>(SNOW_TUNDRA_GIANT_CRAB.getId())));
        event.registerEntityRenderer(FLOWER_LEECH.get(), FlowerLeechRenderer::new);
        event.registerEntityRenderer(FORGOTTEN_MAGIC_BOOK.get(), ForgottenMagicBookRenderer::new);
        event.registerEntityRenderer(HYPHA_WALKING_MUSHROOM.get(), HyphaWalkingMushroomRenderer::new);
        event.registerEntityRenderer(MOONLIGHT_KNIGHT.get(), context -> new DeathWithoutRotationRenderer<>(
                context, new DefaultedEntityGeoModel<>(MOONLIGHT_KNIGHT.getId(), Boolean.TRUE)));
        event.registerEntityRenderer(RUNESTONE_KEEPER.get(), RunestoneKeeperRenderer::new);
        event.registerEntityRenderer(SPIDER_MOTHER.get(), SpiderMotherRenderer::new);
        event.registerEntityRenderer(MOON_QUEEN.get(), MoonQueenRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TAModelLayers.BREAD_BEAST, BreadBeastModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.ICEFIELD_DEER, IcefieldDeerModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.MOON_FISH, MoonFishModel::createBodyLayer);
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
        event.registerLayerDefinition(TAModelLayers.CRYSTALLINE_SPRITE, CrystallineSpriteModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.FLOWER_LEECH, FlowerLeechModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.FORGOTTEN_MAGIC_BOOK, ForgottenMagicBookModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.HYPHA_WALKING_MUSHROOM, HyphaWalkingMushroomModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.SPIKED_CHESTPLATE, SpikedChestplateModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_SLIME_BOOTS, AurorianSlimeBootsModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.AURORIAN_STEEL_ARMOR, AurorianSteelArmorModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.CERULEAN_ARMOR, CeruleanArmorModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.KNIGHT_ARMOR, KnightArmorModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.SPECTRAL_ARMOR, SpectralArmorModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.MYSTERIUM_ARMOR, MysteriumWoolArmorModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.CRYSTAL_RUNE_ARMOR, CrystalRuneArmorModel::createBodyLayer);
        event.registerLayerDefinition(TAModelLayers.MOON_QUEEN_SWORD, MoonQueenSwordModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        registerNormalSpawn(event, BREAD_BEAST.get(), SpawnPlacementTypes.ON_GROUND, TASpawnRules::checkAnimalSpawnRules);
        registerNormalSpawn(event, ICEFIELD_DEER.get(), SpawnPlacementTypes.ON_GROUND, TASpawnRules::checkAnimalSpawnRules);
        registerNormalSpawn(event, BLUE_TAIL_WOLF.get(), SpawnPlacementTypes.ON_GROUND, TASpawnRules::checkAnimalSpawnRules);
        registerNormalSpawn(event, MOON_FISH.get(), SpawnPlacementTypes.IN_WATER, TASpawnRules::checkWaterAnimalSpawnRules);
        registerNormalSpawn(event, AURORIAN_WINGED_FISH.get(), SpawnPlacementTypes.IN_WATER, TASpawnRules::checkWaterAnimalSpawnRules);
        registerNormalSpawn(event, AURORIAN_RABBIT.get(), SpawnPlacementTypes.ON_GROUND, TASpawnRules::checkAnimalSpawnRules);
        registerNormalSpawn(event, AURORIAN_SHEEP.get(), SpawnPlacementTypes.ON_GROUND, TASpawnRules::checkAnimalSpawnRules);
        registerNormalSpawn(event, AURORIAN_PIG.get(), SpawnPlacementTypes.ON_GROUND, TASpawnRules::checkAnimalSpawnRules);
        registerNormalSpawn(event, AURORIAN_COW.get(), SpawnPlacementTypes.ON_GROUND, TASpawnRules::checkAnimalSpawnRules);
        registerNormalSpawn(event, AURORIAN_SLIME.get(), SpawnPlacementTypes.ON_GROUND, AurorianSlime::checkSpawnRules);
        registerNormalSpawn(event, DISTURBED_HOLLOW.get(), SpawnPlacementTypes.ON_GROUND, Monster::checkMonsterSpawnRules);
        registerNormalSpawn(event, UNDEAD_KNIGHT.get(), SpawnPlacementTypes.ON_GROUND, UndeadKnight::checkSpawnRules);
        registerNormalSpawn(event, SPIRIT.get(), SpawnPlacementTypes.ON_GROUND, Spirit::checkSpawnRules);
        registerNormalSpawn(event, MOON_ACOLYTE.get(), SpawnPlacementTypes.ON_GROUND, MoonAcolyte::checkSpawnRules);
        registerNormalSpawn(event, SPIDERLING.get(), SpawnPlacementTypes.ON_GROUND, Spiderling::checkSpawnRules);
        registerNormalSpawn(event, CRYSTALLINE_SPRITE.get(), SpawnPlacementTypes.ON_GROUND, CrystallineSprite::checkSpawnRules);
        registerNormalSpawn(event, CAVE_DWELLER.get(), SpawnPlacementTypes.ON_GROUND, CaveDweller::checkMonsterSpawnRules);
        registerNormalSpawn(event, ROCK_HAMMER.get(), SpawnPlacementTypes.ON_GROUND, RockHammer::checkSpawnRules);
        registerNormalSpawn(event, TONG_SCORPION.get(), SpawnPlacementTypes.ON_GROUND, TongScorpion::checkSpawnRules);
        registerNormalSpawn(event, SNOW_TUNDRA_GIANT_CRAB.get(), SpawnPlacementTypes.ON_GROUND, SnowTundraGiantCrab::checkSpawnRules);
        registerNormalSpawn(event, FLOWER_LEECH.get(), SpawnPlacementTypes.IN_WATER, FlowerLeech::checkSpawnRules);
        registerNormalSpawn(event, FORGOTTEN_MAGIC_BOOK.get(), SpawnPlacementTypes.ON_GROUND, ForgottenMagicBook::checkSpawnRules);
        registerNormalSpawn(event, HYPHA_WALKING_MUSHROOM.get(), SpawnPlacementTypes.ON_GROUND, HyphaWalkingMushroom::checkSpawnRules);
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(BREAD_BEAST.get(), BreadBeast.createAttributes().build());
        event.put(ICEFIELD_DEER.get(), IcefieldDeer.createAttributes().build());
        event.put(BLUE_TAIL_WOLF.get(), BlueTailWolf.createAttributes().build());
        event.put(MOON_FISH.get(), MoonFish.createAttributes().build());
        event.put(AURORIAN_WINGED_FISH.get(), AurorianWingedFish.createAttributes().build());
        event.put(AURORIAN_VILLAGER.get(), AurorianVillager.createAttributes().build());
        event.put(AURORIAN_RABBIT.get(), AurorianRabbit.createAttributes().build());
        event.put(AURORIAN_SHEEP.get(), AurorianSheep.createAttributes().build());
        event.put(AURORIAN_PIG.get(), AurorianPig.createAttributes().build());
        event.put(AURORIAN_COW.get(), AurorianCow.createAttributes().build());
        event.put(AURORIAN_PIXIE.get(), AurorianPixie.createAttributes().build());
        event.put(AURORIAN_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(DISTURBED_HOLLOW.get(), DisturbedHollow.createAttributes().build());
        event.put(UNDEAD_KNIGHT.get(), UndeadKnight.createAttributes().build());
        event.put(SPIRIT.get(), Spirit.createAttributes().build());
        event.put(MOON_ACOLYTE.get(), MoonAcolyte.createAttributes().build());
        event.put(SPIDERLING.get(), Spiderling.createAttributes().build());
        event.put(SPIDERLING_CRYSTAL_SHELL.get(), SpiderlingCrystalShell.createAttributes().build());
        event.put(SPIDERLING_WALL_CLIMBER.get(), SpiderlingWallClimber.createAttributes().build());
        event.put(GIANT_CRYSTAL_SPIDER.get(), GiantCrystalSpider.createAttributes().build());
        event.put(RUNE_SPIDER.get(), RuneSpider.createAttributes().build());
        event.put(CRYSTALLINE_SPRITE.get(), CrystallineSprite.createAttributes().build());
        event.put(CAVE_DWELLER.get(), CaveDweller.createAttributes().build());
        event.put(ROCK_HAMMER.get(), RockHammer.createAttributes().build());
        event.put(TONG_SCORPION.get(), TongScorpion.createAttributes().build());
        event.put(SNOW_TUNDRA_GIANT_CRAB.get(), SnowTundraGiantCrab.createAttributes().build());
        event.put(FLOWER_LEECH.get(), FlowerLeech.createAttributes().build());
        event.put(FORGOTTEN_MAGIC_BOOK.get(), ForgottenMagicBook.createAttributes().build());
        event.put(HYPHA_WALKING_MUSHROOM.get(), HyphaWalkingMushroom.createAttributes().build());
        event.put(MOONLIGHT_KNIGHT.get(), MoonlightKnight.createAttributes().build());
        event.put(RUNESTONE_KEEPER.get(), RunestoneKeeper.createAttributes().build());
        event.put(SPIDER_MOTHER.get(), SpiderMother.createAttributes().build());
        event.put(MOON_QUEEN.get(), MoonQueen.createAttributes().build());
    }

    private static <T extends Entity> void registerNormalSpawn(RegisterSpawnPlacementsEvent event, EntityType<T> entityType, SpawnPlacementType type, SpawnPlacements.SpawnPredicate<T> predicate) {
        event.register(entityType, type, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, predicate, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

}