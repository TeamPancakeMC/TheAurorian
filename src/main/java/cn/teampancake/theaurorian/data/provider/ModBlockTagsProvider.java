package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.data.tags.ModBlockTags;
import cn.teampancake.theaurorian.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModBlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON)
                .add(ModBlocks.RUNE_STONE.get()).add(ModBlocks.MOON_TEMPLE_BRICKS.get())
                .add(ModBlocks.DARK_STONE_BRICKS.get()).add(ModBlocks.DARK_STONE_FANCY.get())
                .add(ModBlocks.DARK_STONE_LAYERS.get()).add(ModBlocks.RUNE_STONE_LAMP.get())
                .add(ModBlocks.MOON_TEMPLE_LAMP.get()).add(ModBlocks.DARK_STONE_LAMP.get())
                .add(ModBlocks.SMOOTH_RUNE_STONE.get()).add(ModBlocks.SMOOTH_MOON_TEMPLE_BRICKS.get());
        tag(ModBlockTags.DUNGEON_BRICKS)
                .add(ModBlocks.RUNE_STONE.get())
                .add(ModBlocks.MOON_TEMPLE_BRICKS.get())
                .add(ModBlocks.DARK_STONE_BRICKS.get())
                .add(ModBlocks.DARK_STONE_FANCY.get())
                .add(ModBlocks.DARK_STONE_LAYERS.get())
                .add(ModBlocks.SMOOTH_RUNE_STONE.get())
                .add(ModBlocks.SMOOTH_MOON_TEMPLE_BRICKS.get())
                .add(ModBlocks.RUNE_STONE_LAMP.get())
                .add(ModBlocks.MOON_TEMPLE_LAMP.get())
                .add(ModBlocks.DARK_STONE_LAMP.get())
                .add(ModBlocks.CERULEAN_BLOCK.get())
                .add(ModBlocks.MOONSTONE_BLOCK.get())
                .add(ModBlocks.AURORIAN_COAL_BLOCK.get())
                .add(ModBlocks.AURORIAN_STEEL_BLOCK.get())
                .add(ModBlocks.MYSTICAL_BARRIER.get())
                .add(ModBlocks.RUNE_STONE_BARS.get())
                .add(ModBlocks.MOON_TEMPLE_BARS.get())
                .add(ModBlocks.RUNE_STONE_GATE.get())
                .add(ModBlocks.MOON_TEMPLE_GATE.get())
                .add(ModBlocks.DARK_STONE_GATE.get())
                .add(ModBlocks.RUNE_STONE_LOOT_GATE.get())
                .add(ModBlocks.MOON_TEMPLE_CELL_GATE.get())
                .add(ModBlocks.RUNE_STONE_STAIRS.get())
                .add(ModBlocks.MOON_TEMPLE_STAIRS.get())
                .add(ModBlocks.DARK_STONE_STAIRS.get());
    }

}