package cn.teampancake.theaurorian.common.entities.phase.moonqueen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MoonQueenBackAttackPhase extends AttackPhase<MoonQueen> {

    public MoonQueenBackAttackPhase() {
        super(5, 3, 5, 200, 1);
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        // 基础条件检查
        if (!entity.getRandom().nextBoolean() || !entity.isAlive() || entity.preparationTime > 0 || !coolDownOver) {
            return false;
        }

        LivingEntity target = entity.getTarget();

        // 目标必须是玩家
        if (!(target instanceof Player player)) {
            return false;
        }

        // 玩家必须是真实玩家（非创造模式/观察者模式）
        if (!entity.isTruePlayer(player)) {
            return false;
        }

        // 距离检查：10格 < 距离 ≤ 60格
        double distance = entity.distanceTo(target);
        if (distance <= 10.0D || distance > 60.0D) {
            return false;
        }

        // 检查瞬移位置是否安全
        return entity.isTeleportPositionSafe(target);
    }

    @Override
    public void onStart(MoonQueen entity) {
        LivingEntity target = entity.getTarget();

        // 确保目标是玩家
        if (target instanceof Player player && entity.isTruePlayer(player)) {
            // 再次检查距离和安全性
            double distance = entity.distanceTo(player);
            if (distance > 10.0D && distance <= 60.0D && entity.isTeleportPositionSafe(player)) {
                entity.teleportToTheBackOfTheTarget(player);
                return;
            }
        }

        // 如果当前目标不合适，寻找合适的玩家目标
        if (!entity.level().isClientSide()) {
            List<Player> nearbyPlayers = entity.getPlayerInBoundingBoxWithInflate(60.0D);
            List<Player> validPlayers = nearbyPlayers.stream()
                .filter(entity::isTruePlayer)
                .filter(player -> {
                    double distance = entity.distanceTo(player);
                    return distance > 10.0D && distance <= 60.0D;
                })
                .filter(entity::isTeleportPositionSafe)
                .toList();

            if (!validPlayers.isEmpty()) {
                List<Player> sortedPlayers = validPlayers.size() > 1 ? getPlayers(entity, validPlayers) : validPlayers;
                Player selectedPlayer = sortedPlayers.getFirst();
                entity.setTarget(selectedPlayer);
                entity.teleportToTheBackOfTheTarget(selectedPlayer);
            }
        }
    }

    @Override
    public void tick(MoonQueen entity) {

    }

    @Override
    public boolean canContinue(MoonQueen entity) {
        return true;
    }

    @Override
    public void onStop(MoonQueen entity) {

    }

    private static @NotNull List<Player> getPlayers(MoonQueen entity, List<Player> playerList) {
        List<Player> newPlayerList = new ArrayList<>(playerList);
        newPlayerList.sort((p1, p2) -> {
            float d1 = entity.distanceTo(p1);
            float d2 = entity.distanceTo(p2);
            float h1 = p1.getHealth();
            float h2 = p2.getHealth();
            int compare = Float.compare(d1, d2);
            return compare != 0 ? compare : Float.compare(h1, h2);
        });

        return newPlayerList;
    }

}