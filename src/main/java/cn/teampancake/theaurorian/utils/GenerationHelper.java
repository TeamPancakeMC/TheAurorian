package cn.teampancake.theaurorian.utils;

import cn.teampancake.theaurorian.config.AurorianConfig;
import cn.teampancake.theaurorian.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Map;
import java.util.Optional;

public class GenerationHelper {

    public interface IChunkSpecific {
        boolean isValidChunkForGen(int chunkX, int chunkZ, int offsetX, int offsetZ);
    }

    /**
     * Shorter version of
     * "world.getSaveHandler().getStructureTemplateManager().getTemplate()" for
     * readablility
     */
    public static Optional<StructureTemplate> getTemplate(Level world, ResourceLocation structure) {
        return world.getServer().getStructureManager().get(structure);
    }

    /**
     * Returns true if the position is within the specified range of the
     * structure.
     *
     * @param structure Structure.
     * @param worldIn World.
     * @param pos Position.
     * @param range Range in blocks.
     * @param searchdistance Search distance in chunks.
     */
    public static boolean isNearStructure(IChunkSpecific structure, Level worldIn, BlockPos pos, int range, int searchdistance) {
        int chunkX = worldIn.getChunk(pos).getPos().x;
        int chunkZ = worldIn.getChunk(pos).getPos().z;
        if (worldIn.dimension()== AurorianConfig.Config_AurorianDimID) {
            for (int x = -(searchdistance / 2); x < (searchdistance / 2); x++) {
                for (int z = -(searchdistance / 2); z < (searchdistance / 2); z++) {
                    if (structure.isValidChunkForGen(chunkX + x, chunkZ + z, 0, 0)) {
                        int blocksaway = (int) pos.distToCenterSqr((chunkX + x) * 16, pos.getY(), (chunkZ + z) * 16)^(1/2);
                        if (blocksaway <= range) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the nearest Aurorian structure to the player within the distance
     * specified
     *
     * @param structure Structure.
     * @param player Player.
     * @param distance Search distance in chunks.
     * @return ChunkPos of structure.
     */
    public static ChunkPos getNearestStructure(IChunkSpecific structure, Player player, int distance) {
        int playerchunkX = player.chunkPosition().x;
        int playerchunkZ = player.chunkPosition().z;
        ChunkPos closest = null;
        int closestdist = 0;
        if (player.level().dimension() == AurorianConfig.Config_AurorianDimID) {
            for (int x = -(distance / 2); x < (distance / 2); x++) {
                for (int z = -(distance / 2); z < (distance / 2); z++) {
                    if (structure.isValidChunkForGen(playerchunkX + x, playerchunkZ + z, 0, 0)) {
                        int blocksaway = (int) player.distanceToSqr((playerchunkX + x) * 16, player.getY(), (playerchunkZ + z) * 16)^(1/2);
                        if (closest == null || blocksaway < closestdist) {
                            closest = new ChunkPos(playerchunkX + x, playerchunkZ + z);
                            closestdist = blocksaway;
                        }
                    }
                }
            }
        }
        return closest;
    }

    /**
     * Will fill chests that have data blocks above them using the given loot
     * table.
     *
     * @param world World.
     * @param position Stucture Position.
     * @param template Struture Template.
     * @param settings Placement Settings.
     * @param dataTag Tag Data tag of Data Blocks.
     * @param lootTable Loot table to use.
     */
    public static void populateChestsInTemplate(Level world, BlockPos position, StructureTemplate template, PlacementSettings settings, String dataTag, ResourceLocation lootTable) {
        Map<BlockPos, String> map = template.getDataBlocks(position, settings);
        for (Map.Entry<BlockPos, String> entry : map.entrySet()) {
            if (dataTag.equals(entry.getValue())) {
                BlockPos blockpos2 = entry.getKey();
                world.setBlock(blockpos2, Blocks.AIR.defaultBlockState(), 3);
                BlockEntity blockEntity = world.getBlockEntity(blockpos2.below());
                if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
                    chestBlockEntity.setLootTable(lootTable, world.random.nextLong());
                }
            }
        }
    }

    /**
     * Returns true if the chunk is flat determined by maximumDifference.
     *
     * @param pos Position of the chunk.
     * @param maximumDifference Maximum number of blocks to allow between the
     * highest block and lowest block in the chunk.
     * Lower values mean flatter terrain.
     */
    public static boolean isTerrainFlat(Level worldIn, BlockPos pos, int maximumDifference) {
        int low = 255;
        int high = 0;
        for (int x = 0; x <= 16; x++) {
            for (int z = 0; z <= 16; z++) {
                for (int y = 190; y >= 60; y--) {
                    if (worldIn.getBlockState(new BlockPos(pos.getX() + x, y, pos.getZ() + z)).getBlock() == ModBlocks.AURORIAN_GRASS_BLOCK.get()) {
                        if (y >= high) {
                            high = y;
                        }
                        if (y <= low) {
                            low = y;
                        }
                        break;
                    }
                }
            }
        }
        return (high - low) <= maximumDifference;
    }

}
