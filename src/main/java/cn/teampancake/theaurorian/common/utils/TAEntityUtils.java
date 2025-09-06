package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAStructureTags;
import cn.teampancake.theaurorian.common.level.structure.structures.RuinsAltarStructure;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import net.neoforged.neoforge.attachment.AttachmentType;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TAEntityUtils {

    public static boolean canReachTarget(Mob entity, double range) {
        LivingEntity target = entity.getTarget();
        if (target == null) return false;
        AABB aabb = entity.getBoundingBox().inflate(range);
        for (LivingEntity livingEntity : entity.level().getNearbyEntities(
                LivingEntity.class, TargetingConditions.DEFAULT, entity, aabb)) {
            if (livingEntity.getUUID().equals(target.getUUID())) return true;
        }

        return false;
    }

    public static void performMeleeAttack(Mob entity, double range) {
        LivingEntity target = entity.getTarget();
        if (target == null) return;
        AABB aabb = entity.getBoundingBox().inflate(range);
        for (LivingEntity livingEntity : entity.level().getNearbyEntities(
                LivingEntity.class, TargetingConditions.DEFAULT, entity, aabb)) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                livingEntity.invulnerableTime = 0;
                entity.doHurtTarget(livingEntity);
            }
        }
    }

    @Nullable
    public static <T extends LivingEntity> T getNearestEntity(LivingEntity current, Class<T> target, double distance) {
        AABB area = current.getBoundingBox().inflate(distance);
        List<T> list = current.level().getEntitiesOfClass(target, area);
        double d0 = Double.MAX_VALUE;
        T object = null;
        for (T entity : list) {
            double d1 = entity.distanceToSqr(current);
            if (d1 < d0) {
                d0 = d1;
                object = entity;
            }
        }

        return object;
    }

    public static void teleportToAurorian(ServerPlayer player, @Nullable ServerLevel serverLevel) {
        if (serverLevel != null && serverLevel.dimension() == TADimensions.AURORIAN_DIMENSION) {
            BlockPos lastPos = getLastPos(player, TAAttachmentTypes.LAST_POS_OF_LEAVE_AURORIAN.get());
            BlockPos playerPos = player.blockPosition();
            if (player.getData(TAAttachmentTypes.TELEPORT_TO_AURORIAN_COUNT.get()) == 0 || lastPos == null) {
                StructureManager structureManager = serverLevel.structureManager();
                for (StructureStart structureStart : structureManager.startsForStructure(
                        new ChunkPos(playerPos.getX() / 16, playerPos.getZ() / 16),
                        structure -> structure instanceof RuinsAltarStructure)) {
                    if (!structureStart.isValid()) continue;
                    BlockPos structureCenter = structureStart.getBoundingBox().getCenter();
                    AABB aabb = new AABB(structureCenter.below()).inflate(3.0F);
                    List<BlockPos> suitablePosList = new ArrayList<>();
                    for (BlockPos blockPos : BlockPos.betweenClosedStream(aabb).toList()) {
                        BlockState tempState = serverLevel.getBlockState(blockPos);
                        BlockState aboveState = serverLevel.getBlockState(blockPos.above());
                        if ((!tempState.isEmpty() || !tempState.canBeReplaced()) && aboveState.isEmpty()) {
                            suitablePosList.add(blockPos);
                        }
                    }

                    if (!suitablePosList.isEmpty()) {
                        BlockPos targetPos = suitablePosList.get(serverLevel.random.nextInt(suitablePosList.size()));
                        player.teleportTo(serverLevel, targetPos.getX(), targetPos.getY(), targetPos.getZ(), player.getYRot(), player.getXRot());
                        player.setData(TAAttachmentTypes.LAST_POS_OF_LEAVE_OVERWORLD.get(), playerPos);
                        return;
                    }
                }

                BlockPos altarPos = serverLevel.findNearestMapStructure(TAStructureTags.RUINS_ALTAR, playerPos, 100, Boolean.FALSE);
                StructureTemplateManager templateManager = serverLevel.getServer().getStructureManager();
                ResourceLocation id = TheAurorian.prefix("ruins/ruins_altar/ruins_altar");
                Optional<StructureTemplate> templateOptional = templateManager.get(id);
                if (altarPos != null && templateOptional.isPresent()) {
                    Vec3i templateSize = templateOptional.get().getSize();
                    ChunkAccess chunkAccess = serverLevel.getChunk(altarPos);
                    ServerChunkCache chunkSource = serverLevel.getChunkSource();
                    ChunkPos chunkPos = chunkAccess.getPos();
                    int xSize = templateSize.getX() / 2;
                    int zSize = templateSize.getZ() / 2;
                    int x = chunkPos.getBlockX(xSize);
                    int z = chunkPos.getBlockZ(zSize);
                    int y = chunkSource.getGenerator().getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG,
                            chunkAccess.getHeightAccessorForGeneration(), chunkSource.randomState());
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = serverLevel.getBlockState(pos);
                    while (!state.isEmpty() || !state.canBeReplaced()) {
                        state = serverLevel.getBlockState(pos.above());
                        pos = pos.above();
                    }

                    player.teleportTo(serverLevel, x, pos.getY(), z, player.getYRot(), player.getXRot());
                    player.setData(TAAttachmentTypes.LAST_POS_OF_LEAVE_OVERWORLD.get(), playerPos);
                }
            } else {
                player.teleportTo(serverLevel, lastPos.getX(), lastPos.getY(), lastPos.getZ(), player.getYRot(), player.getXRot());
                player.setData(TAAttachmentTypes.LAST_POS_OF_LEAVE_OVERWORLD.get(), playerPos);
            }
        }
    }

    public static void teleportFromAurorianToOverworld(ServerPlayer player, @Nullable ServerLevel overworld) {
        if (overworld != null && overworld.dimension() == Level.OVERWORLD) {
            BlockPos lastPos = getLastPos(player, TAAttachmentTypes.LAST_POS_OF_LEAVE_OVERWORLD.get());
            BlockPos targetPos = lastPos == null ? overworld.getLevelData().getSpawnPos() : lastPos;
            player.setData(TAAttachmentTypes.LAST_POS_OF_LEAVE_AURORIAN.get(), player.blockPosition());
            player.teleportTo(overworld, targetPos.getX(), targetPos.getY(), targetPos.getZ(), player.getYRot(), player.getXRot());
        }
    }

    @Nullable
    public static BlockPos getLastPos(ServerPlayer player, AttachmentType<BlockPos> type) {
        BlockPos lastPos = player.getData(type);
        try {
            Class<AttachmentHolder> clazz = AttachmentHolder.class;
            Method method = clazz.getDeclaredMethod("getAttachmentMap");
            Object object = method.invoke(player);
            if (object instanceof Map<?, ?> map) {
                lastPos = (BlockPos) map.get(type);
            }

        } catch (Exception ignored) {}
        return lastPos;
    }

    public static boolean isPlayerNearStructure(LivingEntity entity, ResourceKey<Structure> targetStructure, double radius) {
        if (!(entity instanceof Player player) || player.getAbilities().instabuild) return false;
        if (!(player.level() instanceof ServerLevel level)) return false;
        BlockPos playerPos = player.blockPosition();
        int searchRadius = Mth.ceil(radius / 16.0F) + 1;
        for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int z = -searchRadius; z <= searchRadius; z++) {
                int cx = playerPos.getX() / 16 + x;
                int cz = playerPos.getZ() / 16 + z;
                ChunkPos chunkPos = new ChunkPos(cx, cz);
                List<StructureStart> structureStarts = level.structureManager()
                        .startsForStructure(chunkPos, structure -> level.registryAccess()
                                .registryOrThrow(Registries.STRUCTURE)
                                .getHolder(targetStructure).isPresent());
                for (StructureStart structureStart : structureStarts) {
                    if (structureStart.isValid()) {
                        BlockPos structureCenter = structureStart.getBoundingBox().getCenter();
                        double distance = Math.sqrt(playerPos.distSqr(structureCenter));
                        if (distance <= radius) return true;
                    }
                }
            }
        }
        
        return false;
    }

}