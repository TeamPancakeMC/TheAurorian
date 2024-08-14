package cn.teampancake.theaurorian.common.level.feature.ruin;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.feature.TemplateFeature;
import cn.teampancake.theaurorian.common.level.processors.DecorationProcessor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.Nullable;

public class MediumRuinFeature extends TemplateFeature<NoneFeatureConfiguration> {

    private final String nbtFilename;

    public MediumRuinFeature(String nbtFilename) {
        super(NoneFeatureConfiguration.CODEC);
        this.nbtFilename = nbtFilename;
    }

    @Override
    protected @Nullable StructureTemplate getTemplate(StructureTemplateManager templateManager, RandomSource random) {
        return templateManager.getOrCreate(AurorianMod.prefix("ruins/medium_ruins/" + this.nbtFilename));
    }

    @Override
    protected void modifySettings(StructurePlaceSettings settings, RandomSource random, NoneFeatureConfiguration config) {
        settings.addProcessor(DecorationProcessor.INSTANCE);
    }

}