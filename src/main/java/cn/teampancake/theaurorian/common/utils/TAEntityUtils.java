package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAStructureTags;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import net.neoforged.neoforge.attachment.AttachmentType;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

public class TAEntityUtils {

    public static boolean canReachTarget(Mob entity, double range) {
        LivingEntity target = entity.getTarget();
        AABB aabb = entity.getBoundingBox().inflate(range);
        if (target == null) {
            return false;
        }

        for (LivingEntity livingEntity : entity.level().getNearbyEntities(
                LivingEntity.class, TargetingConditions.DEFAULT, entity, aabb)) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                return true;
            }
        }

        return false;
    }

    public static void performMeleeAttack(Mob entity, double range) {
        LivingEntity target = entity.getTarget();
        if (target != null) {
            AABB aabb = entity.getBoundingBox().inflate(range);
            for (LivingEntity livingEntity : entity.level().getNearbyEntities(
                    LivingEntity.class, TargetingConditions.DEFAULT, entity, aabb)) {
                if (livingEntity.getUUID().equals(target.getUUID())) {
                    livingEntity.invulnerableTime = 0;
                    entity.doHurtTarget(livingEntity);
                }
            }
        }
    }

    public static boolean canArmorTriggerEnchantmentEffect(LivingEntity entity, ResourceKey<Enchantment> key) {
        Holder<Enchantment> holder = TAEnchantments.get(entity.level(), key);
        double totalProbability = 1.0D;
        for (ItemStack stack : entity.getArmorSlots()) {
            if (!stack.isEmpty()) {
                int level = stack.getEnchantmentLevel(holder);
                double p1 = level * 0.01D;
                double p2 = 1.0D - p1;
                totalProbability *= p2;
            }
        }

        return Math.random() < 1.0D - totalProbability;
    }

    public static void teleportToAurorian(ServerPlayer player, @Nullable ServerLevel aurorian) {
        if (aurorian != null && aurorian.dimension() == TADimensions.AURORIAN_DIMENSION) {
            BlockPos lastPos = getLastPos(player, TAAttachmentTypes.LAST_POS_OF_LEAVE_AURORIAN.get());
            TagKey<Structure> tagKey = TAStructureTags.RUINS_ALTAR;
            BlockPos playerPos = player.blockPosition();
            if (player.getData(TAAttachmentTypes.TELEPORT_TO_AURORIAN_COUNT.get()) == 0 || lastPos == null) {
                BlockPos altarPos = aurorian.findNearestMapStructure(tagKey, playerPos, 100, Boolean.FALSE);
                StructureTemplateManager templateManager = aurorian.getServer().getStructureManager();
                ResourceLocation id = TheAurorian.prefix("ruins/ruins_altar/ruins_altar");
                Optional<StructureTemplate> templateOptional = templateManager.get(id);
                if (altarPos != null && templateOptional.isPresent()) {
                    Vec3i templateSize = templateOptional.get().getSize();
                    ChunkAccess chunkAccess = aurorian.getChunk(altarPos);
                    ServerChunkCache chunkSource = aurorian.getChunkSource();
                    ChunkPos chunkPos = chunkAccess.getPos();
                    int x = chunkPos.getBlockX(templateSize.getX() / 2);
                    int z = chunkPos.getBlockZ(templateSize.getZ() / 2);
                    int y = chunkSource.getGenerator().getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG,
                            chunkAccess.getHeightAccessorForGeneration(), chunkSource.randomState());
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = aurorian.getBlockState(pos);
                    while (!state.isEmpty() || !state.canBeReplaced()) {
                        state = aurorian.getBlockState(pos.above());
                        pos = pos.above();
                    }

                    player.teleportTo(aurorian, x, pos.getY(), z, player.getYRot(), player.getXRot());
                    player.setData(TAAttachmentTypes.LAST_POS_OF_LEAVE_OVERWORLD.get(), playerPos);
                }
            } else {
                player.teleportTo(aurorian, lastPos.getX(), lastPos.getY(), lastPos.getZ(), player.getYRot(), player.getXRot());
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

}