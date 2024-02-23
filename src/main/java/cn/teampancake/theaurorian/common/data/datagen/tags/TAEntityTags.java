package cn.teampancake.theaurorian.common.data.datagen.tags;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class TAEntityTags {

    public static final TagKey<EntityType<?>> AFFECTED_BY_NIGHTMARE_MODE = create("affected_by_nightmare_mode");
    public static final TagKey<EntityType<?>> WOLF_NON_TAME_ATTACK_TARGET = create("wolf_non_tame_attack_target");

    private static TagKey<EntityType<?>> create(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, AurorianMod.prefix(name));
    }

}