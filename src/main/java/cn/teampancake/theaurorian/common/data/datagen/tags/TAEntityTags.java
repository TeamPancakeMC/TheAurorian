package cn.teampancake.theaurorian.common.data.datagen.tags;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class TAEntityTags {

    public static final TagKey<EntityType<?>> WOLF_NON_TAME_ATTACK_TARGET = create("wolf_non_tame_attack_target");
    public static final TagKey<EntityType<?>> ALERTED_BY_BLUE_TAIL_WOLF = create("alerted_by_blue_tail_wolf");
    public static final TagKey<EntityType<?>> SENSITIVE_TO_SAVAGE = create("sensitive_to_savage");
    public static final TagKey<EntityType<?>> AURORIAN_BOSS = create("aurorian_boss");
    public static final TagKey<EntityType<?>> SPIDERLING = create("spiderling");

    private static TagKey<EntityType<?>> create(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, AurorianMod.prefix(name));
    }

}