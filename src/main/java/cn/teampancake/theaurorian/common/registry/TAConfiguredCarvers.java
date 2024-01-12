package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.level.carver.AurorianCarver;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class TAConfiguredCarvers {

    public static final AurorianCarver TA_CAVE = new AurorianCarver();
    public static final ResourceKey<ConfiguredWorldCarver<?>> TA_CAVE_CONFIGURED = createKey("ta_cave");
    public static final ResourceKey<ConfiguredWorldCarver<?>> TA_CAVE_EXTRA_UNDERGROUND_CONFIGURED = createKey("ta_cave_extra_underground");

    private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_CARVER, AurorianMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<ConfiguredWorldCarver<?>> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        context.register(TA_CAVE_CONFIGURED,
                TA_CAVE.configured(new CaveCarverConfiguration((0.15F),
                UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top()),
                UniformFloat.of((0.1F), (0.9F)), VerticalAnchor.aboveBottom(8),
                blocks.getOrThrow(TABlockTags.AURORIAN_CARVER_REPLACEABLES),
                UniformFloat.of((0.7F), (1.4F)), UniformFloat.of((0.8F), (1.3F)),
                UniformFloat.of((-1.0F), (-0.4F)))));
        context.register(TA_CAVE_EXTRA_UNDERGROUND_CONFIGURED,
                TA_CAVE.configured(new CaveCarverConfiguration((0.07F),
                UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(0)),
                UniformFloat.of((0.1F), (0.9F)), VerticalAnchor.aboveBottom(8),
                blocks.getOrThrow(TABlockTags.AURORIAN_CARVER_REPLACEABLES),
                UniformFloat.of((0.7F), (1.4F)), UniformFloat.of((0.8F), (1.3F)),
                UniformFloat.of((-1.0F), (-0.4F)))));
    }

}