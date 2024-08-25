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
        return entity.getRandom().nextBoolean() && entity.isAlive() && entity.preparationTime <= 0;
    }

    @Override
    public void onStart(MoonQueen entity) {
        LivingEntity target = entity.getTarget();
        if (target != null) {
            entity.teleportToTheBackOfTheTarget(target);
        } else {
            if (!entity.level().isClientSide()) {
                List<Player> list = entity.getPlayerInBoundingBoxWithInflate(30.0D);
                if (!list.isEmpty()) {
                    List<Player> playerList = list.size() > 1 ? getPlayers(entity, list) : list;
                    entity.setTarget(playerList.getFirst());
                    entity.teleportToTheBackOfTheTarget(playerList.getFirst());
                }
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