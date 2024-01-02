package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SafeTeleportSubscriber {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player entity = event.getEntity();
        Level level = entity.level();
        new Thread(() -> {
            if (!isLocationSafe(entity)) {
                BlockPos safePos = findSafeLocation(level, entity.blockPosition(), 100);
                if (safePos != null) {
                    entity.teleportTo(safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5);
                }else {
                    AurorianMod.LOGGER.debug("No safe location found for player " + entity.getName().getString() + " at " + entity.blockPosition());
                }
            }
        }).start();
    }

    // 检测特定位置是否安全
    private static boolean isLocationSafe(Player player) {
        BlockPos pos = player.blockPosition();
        Level level = player.level();

        return level.noCollision(player)
                && level.getBlockState(pos.below()).getBlock() != Blocks.AIR
                && level.getFluidState(pos).isEmpty();
    }

    // 寻找安全的位置
    private static BlockPos findSafeLocation(Level level, BlockPos start, int radius) {
        for (int x = -radius; x <= radius; ++x) {
            for (int y = -radius; y <= radius; ++y) {
                for (int z = -radius; z <= radius; ++z) {
                    BlockPos pos = start.offset(x, y, z);
                    if (isAreaSafe(level, pos)) {
                        return pos.relative(Direction.UP);
                    }
                }
            }
        }
        return null;
    }

    // 检查一个区域是否安全
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