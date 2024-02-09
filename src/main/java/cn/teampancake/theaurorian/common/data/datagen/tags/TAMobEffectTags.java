package cn.teampancake.theaurorian.common.data.datagen.tags;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

public class TAMobEffectTags {

    public static final TagKey<MobEffect> MOON_QUEEN_ONLY = create("moon_queen_only");

    private static TagKey<MobEffect> create(String name) {
        return TagKey.create(Registries.MOB_EFFECT, AurorianMod.prefix(name));
    }

}