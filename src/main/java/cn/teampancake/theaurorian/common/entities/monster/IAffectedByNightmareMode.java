package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAGameRules;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
public interface IAffectedByNightmareMode {

    default SpawnGroupData finalizeSpawn(Monster monster, ServerLevelAccessor level, @Nullable SpawnGroupData spawnData) {
        GameRules gameRules = level.getLevel().getGameRules();
        GameRules.IntegerValue rule = gameRules.getRule(TAGameRules.RULE_NIGHTMARE_MODE_MULTIPLIER);
        AttributeInstance health = monster.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance attack = monster.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
        if (gameRules.getRule(TAGameRules.RULE_ENABLE_NIGHTMARE_MODE).get()) {
            double multiplier = Math.max(1.0D, rule.get()) * 2.0D;
            if (health != null && attack != null) {
                health.addPermanentModifier(new AttributeModifier(TheAurorian.prefix("nightmare_health_enhance"), multiplier, operation));
                attack.addPermanentModifier(new AttributeModifier(TheAurorian.prefix("nightmare_attack_enhance"), multiplier, operation));
            }

            if (monster.getLastDamageSource() == null) {
                monster.setHealth(monster.getMaxHealth());
            }
        }

        return spawnData;
    }

}