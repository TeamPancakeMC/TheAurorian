package cn.teampancake.theaurorian.common.data.tags;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class TAEntityTags {

    public static final TagKey<EntityType<?>> AFFECTED_BY_NIGHTMARE_MODE = create("affected_by_nightmare_mode");

    private static TagKey<EntityType<?>> create(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, AurorianMod.prefix(name));
    }

}