package cn.teampancake.theaurorian.common.level;

import cn.teampancake.theaurorian.common.network.ShowDeathScreenS2CPacket;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.scores.ScoreAccess;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;

public class TAServerPlayer {

    public static void die(ServerPlayer player, DamageSource cause) {
        boolean hardcore = player.level().getLevelData().isHardcore();
        BlockPos pos = TAEntityUtils.getLastPos(player, TAAttachmentTypes.SPAWN_POINT_OF_AURORIAN.get());
        if (player.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES)) {
            Component deathMessage = player.getCombatTracker().getDeathMessage();
            Component component1 = Component.translatable("death.attack.message_too_long",
                    Component.literal(deathMessage.getString(256)).withStyle(ChatFormatting.YELLOW));
            Component component2 = Component.translatable("death.attack.even_more_magic",
                    player.getDisplayName()).withStyle(style -> style.withHoverEvent(
                            new HoverEvent(HoverEvent.Action.SHOW_TEXT, component1)));
            PacketDistributor.sendToPlayer(player, new ShowDeathScreenS2CPacket(component2, hardcore, (pos != null)));
            Team team = player.getTeam();
            if (team == null || team.getDeathMessageVisibility() == Team.Visibility.ALWAYS) {
                player.server.getPlayerList().broadcastSystemMessage(deathMessage, Boolean.FALSE);
            } else if (team.getDeathMessageVisibility() == Team.Visibility.HIDE_FOR_OTHER_TEAMS) {
                player.server.getPlayerList().broadcastSystemToTeam(player, deathMessage);
            } else if (team.getDeathMessageVisibility() == Team.Visibility.HIDE_FOR_OWN_TEAM) {
                player.server.getPlayerList().broadcastSystemToAllExceptTeam(player, deathMessage);
            }
        } else {
            PacketDistributor.sendToPlayer(player, new ShowDeathScreenS2CPacket(CommonComponents.EMPTY, hardcore, (pos != null)));
        }

        player.removeEntitiesOnShoulder();
        if (player.level().getGameRules().getBoolean(GameRules.RULE_FORGIVE_DEAD_PLAYERS)) {
            player.tellNeutralMobsThatIDied();
        }

        if (!player.isSpectator()) {
            player.dropAllDeathLoot(player.serverLevel(), cause);
        }

        player.getScoreboard().forAllObjectives(ObjectiveCriteria.DEATH_COUNT, player, ScoreAccess::increment);
        LivingEntity killCredit = player.getKillCredit();
        if (killCredit != null) {
            player.awardStat(Stats.ENTITY_KILLED_BY.get(killCredit.getType()));
            killCredit.awardKillScore(player, player.deathScore, cause);
            player.createWitherRose(killCredit);
        }

        player.level().broadcastEntityEvent(player, (byte)3);
        player.awardStat(Stats.DEATHS);
        player.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_DEATH));
        player.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
        player.clearFire();
        player.setTicksFrozen(0);
        player.setSharedFlagOnFire(false);
        player.getCombatTracker().recheckStatus();
        player.setLastDeathLocation(Optional.of(GlobalPos.of(player.level().dimension(), player.blockPosition())));
    }
    
}