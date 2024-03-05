package cn.teampancake.theaurorian.common.registry;

import net.minecraft.world.level.GameRules;

public class TAGameRules {

    public static final GameRules.Key<GameRules.BooleanValue> RULE_ENABLE_AURORIAN_BLESS = GameRules.register("enableAurorianBless", GameRules.Category.UPDATES, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> RULE_ENABLE_NIGHTMARE_MODE = GameRules.register("enableNightmareMode", GameRules.Category.MOBS, GameRules.BooleanValue.create(false));
    public static final GameRules.Key<GameRules.IntegerValue> RULE_NIGHTMARE_MODE_MULTIPLIER = GameRules.register("nightmareModeMultiplier", GameRules.Category.MOBS, GameRules.IntegerValue.create(1));

}