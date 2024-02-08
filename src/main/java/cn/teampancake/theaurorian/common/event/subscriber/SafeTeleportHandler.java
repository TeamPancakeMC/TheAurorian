package cn.teampancake.theaurorian.common.event.subscriber;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber
public class SafeTeleportHandler {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player entity = event.getEntity();
        Level level = entity.level();
        new Thread(() -> teleportToSafeLocation(level, entity, 100)).start();
    }

    private static void teleportToSafeLocation(Level level, Player player, int radius) {
        if (!isLocationSafe(player).isEmpty()) {
            BlockPos safePos = findSafeLocation(level, player, radius);
            if (safePos != null) {
                player.teleportTo(safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5);
            } else {
                randomTeleportTo(level, player);
                safePos = findSafeLocation(level, player, radius);
                if (safePos != null) {
                    player.teleportTo(safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5);
                } else {
                    createPlatformAtPlayer(level, player);
                }
            }
        }
    }

    private static void createPlatformAtPlayer(Level level, Player player) {
        BlockPos pos = player.blockPosition();
        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                BlockPos platformPos = pos.offset(x, -1, z);
                for (int i = 0; i < 1; i++) {
                    level.setBlockAndUpdate(platformPos.above(i), Blocks.AIR.defaultBlockState());
                }
                level.setBlockAndUpdate(platformPos, Blocks.STONE.defaultBlockState());
            }
        }
    }

    private static void randomTeleportTo(Level level, Player entity) {
        Random random = new Random();
        int x = random.nextInt(250);
        int z = random.nextInt(250);
        int y = random.nextInt(level.getHeight());
        entity.teleportTo(x, y, z);
    }

    private static List<Direction> isLocationSafe(Player player) {
        BlockPos pos = player.blockPosition();
        Level level = player.level();
        List<Direction> directions = new ArrayList<>();
        if (!level.noCollision(player)) {
            directions.add(Direction.NORTH);
            directions.add(Direction.SOUTH);
            directions.add(Direction.WEST);
            directions.add(Direction.EAST);
        }

        if (level.getBlockState(pos.below()).getBlock() == Blocks.AIR || level.getBlockState(pos.below()).getBlock() == Blocks.BEDROCK) {
            directions.add(Direction.DOWN);
        }

        if (!level.getFluidState(pos).isEmpty()) {
            directions.add(Direction.UP);
        }

        return directions;
    }

    private static BlockPos findSafeLocation(Level level, Player player, int radius) {
        List<Direction> directions = isLocationSafe(player);
        for (Direction direction : directions) {
            for (int i = 1; i <= 10; ++i) {
                BlockPos pos = player.blockPosition().relative(direction, i);
                if (isAreaSafe(level, pos)) {
                    return pos.relative(Direction.UP);
                }
            }
        }

        Queue<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();
        queue.offer(player.blockPosition());

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            if (isAreaSafe(level, current)) {
                return current.relative(Direction.UP);
            }

            if (player.blockPosition().distManhattan(current) <= radius) {
                for (Direction direction : Direction.values()) {
                    BlockPos neighbor = current.relative(direction);
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        return null;
    }

    private static boolean isAreaSafe(Level level, BlockPos pos) {
        if (!level.getBlockState(pos).isAir() && level.getFluidState(pos).isEmpty()) {
            for (int i = 1; i <= 2; ++i) {
                if (!level.getBlockState(pos.above(i)).isAir()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}