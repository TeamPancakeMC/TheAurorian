package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.developer.IDeveloperItem;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TAItemTagsProvider extends ItemTagsProvider {

    public TAItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.copy(TABlockTags.VERTICAL_STAIRS, TAItemTags.VERTICAL_STAIRS);
        this.copy(TABlockTags.VERTICAL_SLABS, TAItemTags.VERTICAL_SLABS);
        this.copy(TABlockTags.RUNE_STONE_BLOCK, TAItemTags.RUNE_STONE_BLOCK);
        this.copy(TABlockTags.SILENT_TREE_LOGS, TAItemTags.SILENT_TREE_LOGS);
        this.copy(TABlockTags.WEEPING_WILLOW_LOGS, TAItemTags.WEEPING_WILLOW_LOGS);
        this.copy(TABlockTags.CURTAIN_TREE_LOGS, TAItemTags.CURTAIN_TREE_LOGS);
        this.copy(TABlockTags.CURSED_FROST_TREE_LOGS, TAItemTags.CURSED_FROST_TREE_LOGS);
        this.copy(TABlockTags.AURORIAN_PLANKS, TAItemTags.AURORIAN_PLANKS);
        this.copy(TABlockTags.AURORIAN_GRASS_BLOCK, TAItemTags.AURORIAN_GRASS_BLOCK);
        this.copy(TABlockTags.AURORIAN_CARVER_REPLACEABLES, TAItemTags.AURORIAN_CARVER_REPLACEABLES);
        this.copy(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON, TAItemTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON);
        this.tag(Tags.Items.RODS_WOODEN).add(TAItems.SILENT_WOOD_STICK.get());
        this.tag(Tags.Items.TOOLS_CROSSBOWS).add(TAItems.STAR_OCEAN_CROSSBOW.get());
        this.tag(Tags.Items.TOOLS_BOWS).add(TAItems.SILENT_WOOD_BOW.get()).add(TAItems.KEEPERS_BOW.get());
        this.tag(Tags.Items.TOOLS_SHIELDS).add(TAItems.CERULEAN_SHIELD.get(), TAItems.CRYSTALLINE_SHIELD.get(),
                TAItems.MOON_SHIELD.get(), TAItems.UMBRA_SHIELD.get(), TAItems.MOONSTONE_SHIELD.get());
        this.tag(ItemTags.ARROWS).add(TAItems.CERULEAN_ARROW.get(), TAItems.CRYSTAL_ARROW.get());
        this.tag(TAItemTags.RUNESTONE).add(TAItems.RUNESTONE_ICE.get(), TAItems.RUNESTONE_LIFE.get(),
                TAItems.RUNESTONE_LIGHT.get(), TAItems.RUNESTONE_WATER.get(), TAItems.RUNESTONE_BLAZE.get(),
                TAItems.RUNESTONE_THUNDER.get(), TAItems.RUNESTONE_DARKNESS.get());
        this.tag(TAItemTags.DUNGEON_KEY).add(TAItems.RUNE_STONE_KEY.get()).add(TAItems.RUNE_STONE_LOOT_KEY.get())
                .add(TAItems.MOON_TEMPLE_KEY.get()).add(TAItems.MOON_TEMPLE_CELL_KEY.get()).add(TAItems.DARK_STONE_KEY.get());
        this.tag(TAItemTags.SPECTRAL_ARMOR).add(TAItems.SPECTRAL_HELMET.get(), TAItems.SPECTRAL_CHESTPLATE.get(),
                TAItems.SPECTRAL_LEGGINGS.get(), TAItems.SPECTRAL_BOOTS.get());
        this.tag(TAItemTags.HAS_CUSTOM_TOOLTIPS).addTag(TAItemTags.BUILDING_BLOCK).addTag(TAItemTags.IS_RARE)
                .addTag(TAItemTags.IS_EPIC).addTag(TAItemTags.IS_LEGENDARY).addTag(TAItemTags.IS_MYTHICAL)
                .add(TAItems.WHITE_CHOCOLATE.get(), TAItems.RED_BOOK.get(), TAItems.RED_BOOK_RING.get(),
                        TAItems.STAR_OCEAN_CROSSBOW.get(), TAItems.DREAM_DYEING_CRYSTAL_FRAGMENT.get(),
                        TAItems.CAT_BELL.get(), TAItems.TSLAT_SWORD.get());
        this.tag(TAItemTags.IS_RARE).addTag(TAItemTags.DUNGEON_KEY).add(TAItems.MOON_TEMPLE_CELL_KEY_FRAGMENT.get(),
                TAItems.TEA_CUP.get(), TAItems.AURORIAN_COAL.get(), TAItems.AURORIANITE_INGOT.get(),
                TAItems.CERULEAN_INGOT.get(), TAItems.CRYSTALLINE_INGOT.get(), TAItems.MOONSTONE_INGOT.get(),
                TAItems.UMBRA_INGOT.get(), TAItems.LAVENDER.get(), TAItems.PLANT_FIBER.get(),
                TAItems.AURORIANITE_SCRAP.get(), TAItems.CRYSTALLINE_SCRAP.get(), TAItems.UMBRA_SCRAP.get(),
                TAItems.SPECTRAL_SILK.get(), TAItems.DARK_AMULET.get(), TAItems.DUNGEON_KEEPER_AMULET.get(),
                TAItems.CERULEAN_ARROW.get(), TAItems.CRYSTAL_ARROW.get(), TAItems.ABSORPTION_ORB.get(),
                TAItems.SILENT_WOOD_STICK.get(), TAItems.STICKY_SPIKER.get(), TAItems.CRYSTALLINE_SPRITE.get(), TAItems.CRYSTAL.get(),
                TAItems.AURORIAN_CHAIN.get(), TAItems.AURORIAN_BERRY.get(), TAItems.EQUINOX_MUSHROOM.get(),
                TAItems.WORLD_SCROLL_FRAGMENT.get(), TAItems.WORLD_SCROLL.get(), TAItems.WEBBING.get(),
                TAItems.LIVING_DIVINING_ROD.get(), TAItems.MOON_WATER_BUCKET.get(), TAItems.MOON_FISH_BUCKET.get());
        this.tag(TAItemTags.IS_EPIC).add(TAItems.AURORIAN_STEEL.get());
        this.tag(TAItemTags.IS_LEGENDARY).add(TAItems.TROPHY_KEEPER.get(), TAItems.TROPHY_SPIDER_MOTHER.get(),
                TAItems.TROPHY_MOON_QUEEN.get(), TAItems.DEVELOPER_GIFT.get(), TAItems.AURORIAN_CRYSTAL.get(),
                TAItems.RUNE_KNOWLEDGE_FRAGMENT.get());
        this.tag(TAItemTags.IS_MYTHICAL).add(TAItems.SLEEPING_BLACK_TEA.get());
        for (Item item : TACommonUtils.getKnownItems()) {
            if (!(item instanceof IDeveloperItem)) {
                if (item.canBeDepleted()) {
                    this.tag(TAItemTags.IS_EPIC).add(item);
                } else if (item.isEdible()) {
                    this.tag(TAItemTags.IS_RARE).add(item);
                } else if (item instanceof BlockItem) {
                    this.tag(TAItemTags.BUILDING_BLOCK).add(item);
                }
            }
        }
    }

}