package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAStructureTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TAStructureTagsProvider extends StructureTagsProvider {

    public TAStructureTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, providerCompletableFuture, TheAurorian.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(TAStructureTags.RUNESTONE_DUNGEON).addOptional(TheAurorian.prefix("runestone_dungeon"));
        this.tag(TAStructureTags.DARKSTONE_DUNGEON).addOptional(TheAurorian.prefix("darkstone_dungeon"));
        this.tag(TAStructureTags.MOON_TEMPLE).addOptional(TheAurorian.prefix("moon_temple"));
    }

}