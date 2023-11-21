package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.data.tags.ModBlockTags;
import cn.teampancake.theaurorian.data.tags.ModItemTags;
import cn.teampancake.theaurorian.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(Tags.Items.TOOLS_SHIELDS).add(ModItems.CERULEAN_SHIELD.get(),
                ModItems.CRYSTALLINE_SHIELD.get(), ModItems.MOON_SHIELD.get(),
                ModItems.UMBRA_SHIELD.get(), ModItems.MOONSTONE_SHIELD.get());
        this.tag(ModItemTags.DUNGEON_KEY).add(ModItems.RUNE_STONE_KEY.get())
                .add(ModItems.RUNE_STONE_LOOT_KEY.get()).add(ModItems.MOON_TEMPLE_KEY.get())
                .add(ModItems.MOON_TEMPLE_CELL_KEY.get()).add(ModItems.DARK_STONE_KEY.get());
        this.tag(ModItemTags.SPECTRAL_ARMOR).add(ModItems.SPECTRAL_HELMET.get(),
                ModItems.SPECTRAL_CHESTPLATE.get(), ModItems.SPECTRAL_LEGGINGS.get(),
                ModItems.SPECTRAL_BOOTS.get());
        this.tag(ModItemTags.HAS_CUSTOM_ARMOR_MODEL).add(ModItems.AURORIAN_STEEL_HELMET.get(),
                ModItems.AURORIAN_STEEL_CHESTPLATE.get(), ModItems.AURORIAN_STEEL_LEGGINGS.get(),
                ModItems.AURORIAN_STEEL_BOOTS.get(), ModItems.CERULEAN_HELMET.get(),
                ModItems.CERULEAN_CHESTPLATE.get(), ModItems.CERULEAN_LEGGINGS.get(),
                ModItems.CERULEAN_BOOTS.get(), ModItems.KNIGHT_HELMET.get(),
                ModItems.KNIGHT_CHESTPLATE.get(), ModItems.KNIGHT_LEGGINGS.get(),
                ModItems.KNIGHT_BOOTS.get(), ModItems.SPECTRAL_HELMET.get(),
                ModItems.SPECTRAL_CHESTPLATE.get(), ModItems.SPECTRAL_LEGGINGS.get(),
                ModItems.SPECTRAL_BOOTS.get());
        this.tag(ModItemTags.AURORIAN_SLIME_BOOTS).add(ModItems.AURORIAN_SLIME_BOOTS.get());
        this.tag(ItemTags.ARROWS).add(ModItems.CERULEAN_ARROW.get(), ModItems.CRYSTAL_ARROW.get());
        this.copy(ModBlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON, ModItemTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON);
        this.copy(ModBlockTags.SILENT_TREE_LOGS, ModItemTags.SILENT_TREE_LOGS);
        this.copy(ModBlockTags.WEEPING_WILLOW_LOGS, ModItemTags.WEEPING_WILLOW_LOGS);

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.AURORIAN_STEEL_HELMET.get(),
                        ModItems.AURORIAN_STEEL_CHESTPLATE.get(),
                        ModItems.AURORIAN_STEEL_LEGGINGS.get(),
                        ModItems.AURORIAN_STEEL_BOOTS.get(),
                        ModItems.CERULEAN_HELMET.get(),
                        ModItems.CERULEAN_CHESTPLATE.get(),
                        ModItems.CERULEAN_LEGGINGS.get(),
                        ModItems.CERULEAN_BOOTS.get(),
                        ModItems.KNIGHT_HELMET.get(),
                        ModItems.KNIGHT_CHESTPLATE.get(),
                        ModItems.KNIGHT_LEGGINGS.get(),
                        ModItems.KNIGHT_BOOTS.get(),
                        ModItems.SPECTRAL_HELMET.get(),
                        ModItems.SPECTRAL_CHESTPLATE.get(),
                        ModItems.SPECTRAL_LEGGINGS.get(),
                        ModItems.SPECTRAL_BOOTS.get(),
                        ModItems.AURORIAN_SLIME_BOOTS.get(),
                        ModItems.SPIKED_CHESTPLATE.get()
                );

    }

}