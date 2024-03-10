package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.registry.TAGameRules;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public interface IAffectedByNightmareMode {

    UUID HEALTH_MODIFIER_UUID = UUID.fromString("f5806a5d-4fe9-40c5-8989-6b9f4a19f22b");
    UUID ATTACK_MODIFIER_UUID = UUID.fromString("4b0a0dfc-5f8a-4797-914f-b5f072b2baf2");

    default SpawnGroupData finalizeSpawn(Monster monster, ServerLevelAccessor level, @Nullable SpawnGroupData spawnData) {
        GameRules gameRules = level.getLevel().getGameRules();
        GameRules.IntegerValue rule = gameRules.getRule(TAGameRules.RULE_NIGHTMARE_MODE_MULTIPLIER);
        AttributeInstance health = monster.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance attack = monster.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeModifier.Operation operation = AttributeModifier.Operation.MULTIPLY_BASE;
        if (gameRules.getRule(TAGameRules.RULE_ENABLE_NIGHTMARE_MODE).get()) {
            double multiplier = Math.max(1.0D, rule.get()) * 2.0D;
            if (health != null && attack != null) {
                health.addPermanentModifier(new AttributeModifier(HEALTH_MODIFIER_UUID, "Nightmare Health Enhance", multiplier, operation));
                attack.addPermanentModifier(new AttributeModifier(ATTACK_MODIFIER_UUID, "Nightmare Attack Enhance", multiplier, operation));
            }

            if (monster.getLastDamageSource() == null) {
                monster.setHealth(monster.getMaxHealth());
            }
        }

        return spawnData;
    }

}