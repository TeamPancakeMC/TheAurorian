package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAStructureTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TAStructureTagsProvider extends StructureTagsProvider {
    public TAStructureTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, providerCompletableFuture, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(TAStructureTags.RUNESTONE_DUNGEON).addOptional(AurorianMod.prefix("runestone_dungeon"));
        this.tag(TAStructureTags.DARKSTONE_DUNGEON).addOptional(AurorianMod.prefix("darkstone_dungeon"));
        this.tag(TAStructureTags.MOON_TEMPLE).addOptional(AurorianMod.prefix("moon_temple"));
    }
}
