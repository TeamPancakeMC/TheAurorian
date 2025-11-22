package cn.teampancake.theaurorian.common.level;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings({"deprecation", "OverrideOnly"})
public class TANaturalSpawner {

    private static final MobCategory[] SPAWNING_CATEGORIES = Stream.of(MobCategory.values())
            .filter(category -> category != MobCategory.MISC).toArray(MobCategory[]::new);

    public static SpawnState createState(
            int spawnableChunkCount, Iterable<Entity> entities,
            NaturalSpawner.ChunkGetter chunkGetter, LocalMobCapCalculator calculator) {
        PotentialCalculator potentialCalculator = new PotentialCalculator();
        Object2IntOpenHashMap<MobCategory> object2IntOpenHashMap = new Object2IntOpenHashMap<>();
        for (Entity entity : entities) {
            if (entity instanceof Mob mob && (mob.isPersistenceRequired() || mob.requiresCustomPersistence())) continue;
            MobCategory mobCategory = entity.getClassification(true);
            if (mobCategory != MobCategory.MISC) {
                BlockPos blockPos = entity.blockPosition();
                chunkGetter.query(ChunkPos.asLong(blockPos), chunk -> {
                    MobSpawnSettings.MobSpawnCost mobSpawnCost = getRoughBiome(blockPos, chunk)
                            .getMobSettings().getMobSpawnCost(entity.getType());
                    if (mobSpawnCost != null) potentialCalculator.addCharge(entity.blockPosition(), mobSpawnCost.charge());
                    if (entity instanceof Mob) calculator.addMob(chunk.getPos(), mobCategory);
                    object2IntOpenHashMap.addTo(mobCategory, 1);
                });
            }
        }

        return new SpawnState(spawnableChunkCount, object2IntOpenHashMap, potentialCalculator, calculator);
    }

    static Biome getRoughBiome(BlockPos pos, ChunkAccess chunk) {
        return chunk.getNoiseBiome(QuartPos.fromBlock(pos.getX()), QuartPos.fromBlock(pos.getY()), QuartPos.fromBlock(pos.getZ())).value();
    }

    public static void spawnForChunk(
            ServerLevel level, LevelChunk chunk, SpawnState spawnState,
            boolean spawnFriendlies, boolean spawnMonsters, boolean forcedDespawn) {
        level.getProfiler().push("spawner");
        for (MobCategory mobcategory : SPAWNING_CATEGORIES) {
            if ((spawnFriendlies || !mobcategory.isFriendly())
                    && (spawnMonsters || mobcategory.isFriendly())
                    && (forcedDespawn || !mobcategory.isPersistent())
                    && spawnState.canSpawnForCategory(mobcategory, chunk.getPos())) {
                spawnCategoryForChunk(mobcategory, level, chunk, spawnState::canSpawn, spawnState::afterSpawn);
            }
        }

        level.getProfiler().pop();
    }

    public static void spawnCategoryForChunk(
            MobCategory category, ServerLevel level, LevelChunk chunk,
            NaturalSpawner.SpawnPredicate filter,
            NaturalSpawner.AfterSpawnCallback callback) {
        BlockPos blockPos = getRandomPosWithin(level, chunk);
        if (blockPos.getY() >= level.getMinBuildHeight() + 1) {
            spawnCategoryForPosition(category, level, chunk, blockPos, filter, callback);
        }
    }

    public static void spawnCategoryForPosition(
            MobCategory category, ServerLevel level, ChunkAccess chunk,
            BlockPos pos, NaturalSpawner.SpawnPredicate filter,
            NaturalSpawner.AfterSpawnCallback callback) {
        StructureManager structureManager = level.structureManager();
        ChunkGenerator chunkGenerator = level.getChunkSource().getGenerator();
        int i = pos.getY();
        BlockState blockState = chunk.getBlockState(pos);
        if (!blockState.isRedstoneConductor(chunk, pos)) {
            int playerCount = level.getPlayers((player) -> true).size();
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            int j = 0;
            for (int k = 0; k < 3; k++) {
                int l = pos.getX();
                int i1 = pos.getZ();
                MobSpawnSettings.SpawnerData spawnerData = null;
                SpawnGroupData spawnGroupData = null;
                int k1 = Mth.ceil(level.random.nextFloat() * 4.0F);
                int l1 = 0;
                boolean eliteSpawnAttempted = false;
                for (int i2 = 0; i2 < k1; i2++) {
                    l += level.random.nextInt(6) - level.random.nextInt(6);
                    i1 += level.random.nextInt(6) - level.random.nextInt(6);
                    mutableBlockPos.set(l, i, i1);
                    double d0 = (double)l + 0.5;
                    double d1 = (double)i1 + 0.5;
                    Player player = level.getNearestPlayer(d0, i, d1, -1.0, false);
                    if (player == null) continue;
                    double d2 = player.distanceToSqr(d0, i, d1);
                    if (isRightDistanceToPlayerAndSpawnPoint(level, chunk, mutableBlockPos, d2)) {
                        if (spawnerData == null) {
                            Optional<MobSpawnSettings.SpawnerData> optional = getRandomSpawnMobAt(level, structureManager, chunkGenerator, category, level.random, mutableBlockPos);
                            if (optional.isEmpty()) break;
                            spawnerData = optional.get();
                            k1 = spawnerData.minCount + level.random.nextInt(1 + spawnerData.maxCount - spawnerData.minCount);
                        }

                        if (isValidSpawnPostitionForType(
                                level, category, structureManager, chunkGenerator, spawnerData, mutableBlockPos, d2)
                                && filter.test(spawnerData.type, mutableBlockPos, chunk)) {
                            Mob mob = getMobForSpawn(level, spawnerData.type);
                            if (mob == null) return;
                            mob.moveTo(d0, i, d1, level.random.nextFloat() * 360.0F, 0.0F);
                            if (isValidPositionForMob(level, mob, d2)) {
                                boolean shouldBeElite = false;
                                if (!eliteSpawnAttempted && !hasEliteNearby(level, mutableBlockPos, 64.0)) {
                                    float eliteChance = 0.05F + 0.03F * playerCount;
                                    eliteChance = Math.min(eliteChance, 0.80F);
                                    if (level.random.nextFloat() < eliteChance) {
                                        shouldBeElite = true;
                                        eliteSpawnAttempted = true;
                                    }
                                }

                                if (shouldBeElite && TAWorldEvents.BLOOD_MOON.get().isActive(level)) {
                                    mob.setData(TAAttachmentTypes.IS_ELITE, true);
                                }

                                spawnGroupData = mob.finalizeSpawn(level, level.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.NATURAL, spawnGroupData);
                                j++;
                                l1++;
                                level.addFreshEntityWithPassengers(mob);
                                callback.run(mob, chunk);
                                if (j >= EventHooks.getMaxSpawnClusterSize(mob)) return;
                                if (mob.isMaxGroupSizeReached(l1)) break;
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean isRightDistanceToPlayerAndSpawnPoint(ServerLevel level, ChunkAccess chunk, BlockPos.MutableBlockPos pos, double distance) {
        if (distance <= 576.0) {
            return false;
        } else {
            Vec3 vec3 = new Vec3((double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5);
            return !level.getSharedSpawnPos().closerToCenterThan(vec3, 24.0) && (Objects.equals(new ChunkPos(pos), chunk.getPos()) || level.isNaturalSpawningAllowed(pos));
        }
    }

    private static boolean isValidSpawnPostitionForType(
            ServerLevel level, MobCategory category, StructureManager structureManager,
            ChunkGenerator generator, MobSpawnSettings.SpawnerData data,
            BlockPos.MutableBlockPos pos, double distance) {
        EntityType<?> entityType = data.type;
        if (entityType.getCategory() == MobCategory.MISC) {
            return false;
        } else if (!entityType.canSpawnFarFromPlayer()
                && distance > (double)(entityType.getCategory().getDespawnDistance() *
                entityType.getCategory().getDespawnDistance())) {
            return false;
        } else if (!entityType.canSummon() || !canSpawnMobAt(level, structureManager, generator, category, data, pos)) {
            return false;
        } else if (!SpawnPlacements.isSpawnPositionOk(entityType, level, pos)) {
            return false;
        } else {
            boolean noCollision = level.noCollision(entityType.getSpawnAABB((double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5));
            return SpawnPlacements.checkSpawnRules(entityType, level, MobSpawnType.NATURAL, pos, level.random) && noCollision;
        }
    }

    @Nullable
    private static Mob getMobForSpawn(ServerLevel level, EntityType<?> entityType) {
        try {
            if (entityType.create(level) instanceof Mob mob) return mob;
            TheAurorian.LOGGER.warn("Can't spawn entity of type: {}", BuiltInRegistries.ENTITY_TYPE.getKey(entityType));
        } catch (Exception exception) {
            TheAurorian.LOGGER.warn("Failed to create mob", exception);
        }

        return null;
    }

    private static boolean isValidPositionForMob(ServerLevel level, Mob mob, double distance) {
        return (!(distance > (double) (mob.getType().getCategory().getDespawnDistance() * mob.getType().getCategory().getDespawnDistance()))
                || !mob.removeWhenFarAway(distance)) && EventHooks.checkSpawnPosition(mob, level, MobSpawnType.NATURAL);
    }

    private static Optional<MobSpawnSettings.SpawnerData> getRandomSpawnMobAt(
            ServerLevel level, StructureManager structureManager, ChunkGenerator generator,
            MobCategory category, RandomSource random, BlockPos pos) {
        Holder<Biome> holder = level.getBiome(pos);
        return category == MobCategory.WATER_AMBIENT && holder.is(BiomeTags.REDUCED_WATER_AMBIENT_SPAWNS) && random.nextFloat() < 0.98F
                ? Optional.empty() : mobsAt(level, structureManager, generator, category, pos, holder).getRandom(random);
    }

    private static boolean canSpawnMobAt(
            ServerLevel level, StructureManager structureManager,
            ChunkGenerator generator, MobCategory category,
            MobSpawnSettings.SpawnerData data, BlockPos pos) {
        return mobsAt(level, structureManager, generator, category, pos, null).unwrap().contains(data);
    }

    private static WeightedRandomList<MobSpawnSettings.SpawnerData> mobsAt(
            ServerLevel level, StructureManager structureManager, ChunkGenerator generator, MobCategory category, BlockPos pos, @Nullable Holder<Biome> biome) {
        return EventHooks.getPotentialSpawns(level, category, pos, generator.getMobsAt(biome != null ? biome : level.getBiome(pos), structureManager, category, pos));
    }

    private static BlockPos getRandomPosWithin(Level level, LevelChunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        int i = chunkPos.getMinBlockX() + level.random.nextInt(16);
        int j = chunkPos.getMinBlockZ() + level.random.nextInt(16);
        int k = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, i, j) + 1;
        int l = Mth.randomBetweenInclusive(level.random, level.getMinBuildHeight(), k);
        return new BlockPos(i, l, j);
    }

    public static boolean hasEliteNearby(ServerLevel level, BlockPos pos, double range) {
        List<Entity> nearbyMobs = level.getEntitiesOfClass(Entity.class, new AABB(pos).inflate(range),
                entity -> entity instanceof Mob && entity.getData(TAAttachmentTypes.IS_ELITE));
        return !nearbyMobs.isEmpty();
    }

    public static void spawnMobsForChunkGeneration(ServerLevelAccessor levelAccessor, Holder<Biome> biome, ChunkPos chunkPos, RandomSource random) {
        ServerLevel level = levelAccessor.getLevel();
        MobSpawnSettings mobSpawnSettings = biome.value().getMobSettings();
        WeightedRandomList<MobSpawnSettings.SpawnerData> weightedRandomList = mobSpawnSettings.getMobs(MobCategory.CREATURE);
        boolean isBloodMoon = TAWorldEvents.BLOOD_MOON.get().isActive(level);
        if (!weightedRandomList.isEmpty()) {
            int i = chunkPos.getMinBlockX();
            int j = chunkPos.getMinBlockZ();
            int m = isBloodMoon ? 3 : 1;
            while (random.nextFloat() < mobSpawnSettings.getCreatureProbability() * m) {
                Optional<MobSpawnSettings.SpawnerData> optional = weightedRandomList.getRandom(random);
                if (optional.isPresent()) {
                    MobSpawnSettings.SpawnerData spawnerData = optional.get();
                    int k = spawnerData.minCount + random.nextInt(1 + spawnerData.maxCount - spawnerData.minCount);
                    SpawnGroupData spawnGroupData = null;
                    int l = i + random.nextInt(16);
                    int i1 = j + random.nextInt(16);
                    int j1 = l;
                    int k1 = i1;
                    if (isBloodMoon && spawnerData.type.create(level) instanceof Enemy) k *= 3;
                    for (int l1 = 0; l1 < k; l1++) {
                        boolean flag = false;
                        for (int i2 = 0; !flag && i2 < 4; i2++) {
                            BlockPos blockPos = getTopNonCollidingPos(levelAccessor, spawnerData.type, l, i1);
                            if (spawnerData.type.canSummon() && SpawnPlacements.isSpawnPositionOk(spawnerData.type, levelAccessor, blockPos)) {
                                double f = spawnerData.type.getWidth();
                                double d0 = Mth.clamp(l, (double)i + f, (double)i + 16.0 - f);
                                double d1 = Mth.clamp(i1, (double)j + f, (double)j + 16.0 - f);
                                if (!levelAccessor.noCollision(spawnerData.type.getSpawnAABB(d0, blockPos.getY(), d1))
                                        || !SpawnPlacements.checkSpawnRules(spawnerData.type, levelAccessor, MobSpawnType.CHUNK_GENERATION,
                                        BlockPos.containing(d0, blockPos.getY(), d1), levelAccessor.getRandom())) {
                                    continue;
                                }

                                Entity entity = spawnerData.type.create(level);
                                if (entity == null) continue;
                                entity.moveTo(d0, blockPos.getY(), d1, random.nextFloat() * 360.0F, 0.0F);
                                if (entity instanceof Mob mob && EventHooks.checkSpawnPosition(mob, levelAccessor, MobSpawnType.CHUNK_GENERATION)) {
                                    DifficultyInstance difficulty = levelAccessor.getCurrentDifficultyAt(mob.blockPosition());
                                    spawnGroupData = mob.finalizeSpawn(levelAccessor, difficulty, MobSpawnType.CHUNK_GENERATION, spawnGroupData);
                                    levelAccessor.addFreshEntityWithPassengers(mob);
                                    flag = true;
                                }
                            }

                            l += random.nextInt(5) - random.nextInt(5);
                            for (i1 += random.nextInt(5) - random.nextInt(5);
                                 l < i || l >= i + 16 || i1 < j || i1 >= j + 16;
                                 i1 = k1 + random.nextInt(5) - random.nextInt(5)) {
                                l = j1 + random.nextInt(5) - random.nextInt(5);
                            }
                        }
                    }
                }
            }
        }
    }

    private static BlockPos getTopNonCollidingPos(LevelReader level, EntityType<?> entityType, int x, int z) {
        int i = level.getHeight(SpawnPlacements.getHeightmapType(entityType), x, z);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(x, i, z);
        if (level.dimensionType().hasCeiling()) {
            do {
                mutableBlockPos.move(Direction.DOWN);
            } while (!level.getBlockState(mutableBlockPos).isAir());

            do {
                mutableBlockPos.move(Direction.DOWN);
            } while (level.getBlockState(mutableBlockPos).isAir() && mutableBlockPos.getY() > level.getMinBuildHeight());
        }

        return SpawnPlacements.getPlacementType(entityType).adjustSpawnPosition(level, mutableBlockPos.immutable());
    }

    public static class SpawnState {

        private final int spawnableChunkCount;
        private final Object2IntOpenHashMap<MobCategory> mobCategoryCounts;
        private final PotentialCalculator spawnPotential;
        private final LocalMobCapCalculator localMobCapCalculator;
        @Nullable
        private BlockPos lastCheckedPos;
        @Nullable
        private EntityType<?> lastCheckedType;
        private double lastCharge;

        SpawnState(int spawnableChunkCount, Object2IntOpenHashMap<MobCategory> mobCategoryCounts, PotentialCalculator spawnPotential, LocalMobCapCalculator localMobCapCalculator) {
            this.spawnableChunkCount = spawnableChunkCount;
            this.mobCategoryCounts = mobCategoryCounts;
            this.spawnPotential = spawnPotential;
            this.localMobCapCalculator = localMobCapCalculator;
        }

        private boolean canSpawn(EntityType<?> entityType, BlockPos pos, ChunkAccess chunk) {
            this.lastCheckedPos = pos;
            this.lastCheckedType = entityType;
            MobSpawnSettings.MobSpawnCost mobSpawnCost = getRoughBiome(pos, chunk)
                    .getMobSettings().getMobSpawnCost(entityType);
            if (mobSpawnCost == null) {
                this.lastCharge = 0.0;
                return true;
            } else {
                double d0 = mobSpawnCost.charge();
                this.lastCharge = d0;
                double d1 = this.spawnPotential.getPotentialEnergyChange(pos, d0);
                return d1 <= mobSpawnCost.energyBudget();
            }
        }

        private void afterSpawn(Mob mob, ChunkAccess chunk) {
            EntityType<?> entityType = mob.getType();
            BlockPos blockpos = mob.blockPosition();
            double d0;
            if (blockpos.equals(this.lastCheckedPos) && entityType == this.lastCheckedType) {
                d0 = this.lastCharge;
            } else {
                MobSpawnSettings.MobSpawnCost mobSpawnCost = getRoughBiome(blockpos, chunk)
                        .getMobSettings().getMobSpawnCost(entityType);
                if (mobSpawnCost != null) {
                    d0 = mobSpawnCost.charge();
                } else {
                    d0 = 0.0;
                }
            }

            this.spawnPotential.addCharge(blockpos, d0);
            MobCategory mobCategory = entityType.getCategory();
            this.mobCategoryCounts.addTo(mobCategory, 1);
            this.localMobCapCalculator.addMob(new ChunkPos(blockpos), mobCategory);
        }

        boolean canSpawnForCategory(MobCategory category, ChunkPos pos) {
            int i = category.getMaxInstancesPerChunk() * this.spawnableChunkCount / Mth.square(17);
            return this.mobCategoryCounts.getInt(category) < i && this.localMobCapCalculator.canSpawn(category, pos);
        }

    }

}