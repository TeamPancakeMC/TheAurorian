package cn.teampancake.theaurorian.common.level;

import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;

public class TAFoodData {

    public static void tick(FoodData foodData, Player player) {
        Difficulty difficulty = player.level().getDifficulty();
        foodData.lastFoodLevel = foodData.foodLevel;
        if (foodData.exhaustionLevel > 4.0F) {
            foodData.exhaustionLevel -= 4.0F;
            if (foodData.saturationLevel > 0.0F) {
                foodData.saturationLevel = Math.max(foodData.saturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                foodData.foodLevel = Math.max(foodData.foodLevel - 1, 0);
            }
        }

        boolean flag1 = player.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
        boolean flag2 = player.hasEffect(TAMobEffects.INCANTATION) || player.hasEffect(TAMobEffects.PRESSURE);
        if (flag1 && !flag2 && foodData.saturationLevel > 0.0F && player.isHurt() && foodData.foodLevel >= 20) {
            foodData.tickTimer++;
            if (foodData.tickTimer >= 10) {
                float f = Math.min(foodData.saturationLevel, 6.0F);
                player.heal(f / 6.0F);
                foodData.addExhaustion(f);
                foodData.tickTimer = 0;
            }
        } else if (flag1 && !flag2 && foodData.foodLevel >= 18 && player.isHurt()) {
            foodData.tickTimer++;
            if (foodData.tickTimer >= 80) {
                player.heal(1.0F);
                foodData.addExhaustion(6.0F);
                foodData.tickTimer = 0;
            }
        } else if (foodData.foodLevel <= 0) {
            foodData.tickTimer++;
            if (foodData.tickTimer >= 80) {
                if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                    player.hurt(player.damageSources().starve(), 1.0F);
                }

                foodData.tickTimer = 0;
            }
        } else {
            foodData.tickTimer = 0;
        }
    }

}