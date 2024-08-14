package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.base.DoorBlockWithBase;
import cn.teampancake.theaurorian.common.blocks.base.ISimpleBlockItem;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

@SuppressWarnings("ConstantConditions")
public class TAItemModelProvider extends ItemModelProvider {

    public TAItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheAurorian.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.simpleItem(TAItems.SILK_BERRY.get());
        this.simpleItem(TAItems.BLUEBERRY.get());
        this.simpleItem(TAItems.LAVENDER_SEEDS.get());
        this.simpleItem(TAItems.SILENT_WOOD_SIGN.get());
        this.simpleItem(TAItems.WEEPING_WILLOW_WOOD_SIGN.get());
        this.simpleItem(TAItems.CURTAIN_WOOD_SIGN.get());
        this.simpleItem(TAItems.CURSED_FROST_WOOD_SIGN.get());
        this.simpleItem(TAItems.SILENT_WOOD_HANGING_SIGN.get());
        this.simpleItem(TAItems.WEEPING_WILLOW_WOOD_HANGING_SIGN.get());
        this.simpleItem(TAItems.CURTAIN_WOOD_HANGING_SIGN.get());
        this.simpleItem(TAItems.CURSED_FROST_WOOD_HANGING_SIGN.get());
        this.simpleItem(TABlocks.MYSTERIUM_WOOL_BED.get().asItem());
        this.simpleItem(TABlocks.SILENT_CAMPFIRE.get().asItem());
        this.simpleBlockItem(TABlocks.GEODE_ORE.get());
        this.simpleBlockItem(TABlocks.EROSIVE_GEODE_ORE.get());
        this.simpleBlockItem(TABlocks.CERULEAN_ORE.get());
        this.simpleBlockItem(TABlocks.EROSIVE_CERULEAN_ORE.get());
        this.simpleBlockItem(TABlocks.MOONSTONE_ORE.get());
        this.simpleBlockItem(TABlocks.EROSIVE_MOONSTONE_ORE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_EMERALD_ORE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_IRON_ORE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_GOLD_ORE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_REDSTONE_ORE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_LAPIS_ORE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_COPPER_ORE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_DIAMOND_ORE.get());
        this.simpleBlockItem(TABlocks.EROSIVE_AURORIAN_EMERALD_ORE.get());
        this.simpleBlockItem(TABlocks.EROSIVE_AURORIAN_IRON_ORE.get());
        this.simpleBlockItem(TABlocks.EROSIVE_AURORIAN_GOLD_ORE.get());
        this.simpleBlockItem(TABlocks.EROSIVE_AURORIAN_REDSTONE_ORE.get());
        this.simpleBlockItem(TABlocks.EROSIVE_AURORIAN_LAPIS_ORE.get());
        this.simpleBlockItem(TABlocks.EROSIVE_AURORIAN_COPPER_ORE.get());
        this.simpleBlockItem(TABlocks.EROSIVE_AURORIAN_DIAMOND_ORE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_DIRT.get());
        this.simpleBlockItem(TABlocks.AURORIAN_STONE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_EROSIVE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_COAL_ORE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_STONE_BRICKS.get());
        this.simpleBlockItem(TABlocks.AURORIAN_COBBLESTONE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_GRANITE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_DIORITE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_ANDESITE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_BARRIER_STONE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_GRASS_BLOCK.get());
        this.simpleBlockItem(TABlocks.LIGHT_AURORIAN_GRASS_BLOCK.get());
        this.simpleBlockItem(TABlocks.SNOW_AURORIAN_GRASS_BLOCK.get());
        this.simpleBlockItem(TABlocks.RED_AURORIAN_GRASS_BLOCK.get());
        this.simpleBlockItem(TABlocks.AURORIAN_PORTAL_FRAME_BRICKS.get());
        this.simpleBlockItem(TABlocks.AURORIAN_PERIDOTITE.get());
        this.simpleBlockItem(TABlocks.MYSTERIUM_WOOL.get());
        this.simpleBlockItem(TABlocks.MOON_SAND.get());
        this.simpleBlockItem(TABlocks.MOON_SAND_RIVER.get());
        this.simpleBlockItem(TABlocks.MOON_SANDSTONE.get());
        this.simpleBlockItem(TABlocks.CUT_MOON_SANDSTONE.get());
        this.simpleBlockItem(TABlocks.SMOOTH_MOON_SANDSTONE.get());
        this.simpleBlockItem(TABlocks.BRIGHT_MOON_SAND.get());
        this.simpleBlockItem(TABlocks.BRIGHT_MOON_SANDSTONE.get());
        this.simpleBlockItem(TABlocks.RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.SMOOTH_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.CHISELED_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.CERULEAN_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.CRYSTALLINE_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.MOON_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.TRANSPARENT_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.UMBRA_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.LUMINOUS_AURORIAN_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.LUMINOUS_AURORIAN_STEEL_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.LUMINOUS_CERULEAN_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.LUMINOUS_CRYSTALLINE_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.LUMINOUS_MOON_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(TABlocks.RUNE_STONE_PILLAR.get());
        this.simpleBlockItem(TABlocks.DARK_STONE_PILLAR.get());
        this.simpleBlockItem(TABlocks.MOON_TEMPLE_PILLAR.get());
        this.simpleBlockItem(TABlocks.MOON_TEMPLE_BRICKS.get());
        this.simpleBlockItem(TABlocks.DARK_STONE_BRICKS.get());
        this.simpleBlockItem(TABlocks.DARK_STONE_FANCY.get());
        this.simpleBlockItem(TABlocks.DARK_STONE_LAYERS.get());
        this.simpleBlockItem(TABlocks.SMOOTH_DARK_STONE_BRICKS.get());
        this.simpleBlockItem(TABlocks.CHISELED_DARK_STONE_BRICKS.get());
        this.simpleBlockItem(TABlocks.SMOOTH_MOON_TEMPLE_BRICKS.get());
        this.simpleBlockItem(TABlocks.CHISELED_MOON_TEMPLE_BRICKS.get());
        this.simpleBlockItem(TABlocks.SMOOTH_AURORIAN_PERIDOTITE.get());
        this.simpleBlockItem(TABlocks.RUNE_STONE_LAMP.get());
        this.simpleBlockItem(TABlocks.DARK_STONE_LAMP.get());
        this.simpleBlockItem(TABlocks.MOON_TEMPLE_LAMP.get());
        this.simpleBlockItem(TABlocks.VOID_STONE.get());
        this.simpleBlockItem(TABlocks.RUNE_CRYSTAL.get());
        this.simpleBlockItem(TABlocks.CERULEAN_BLOCK.get());
        this.simpleBlockItem(TABlocks.MOONSTONE_BLOCK.get());
        this.simpleBlockItem(TABlocks.AURORIAN_COAL_BLOCK.get());
        this.simpleBlockItem(TABlocks.AURORIAN_STEEL_BLOCK.get());
        this.simpleBlockItem(TABlocks.RUNE_STONE_GATE.get());
        this.simpleBlockItem(TABlocks.DARK_STONE_GATE.get());
        this.simpleBlockItem(TABlocks.MOON_TEMPLE_GATE.get());
        this.simpleBlockItem(TABlocks.RUNE_STONE_LOOT_GATE.get());
        this.simpleBlockItem(TABlocks.MOON_TEMPLE_CELL_GATE.get());
        this.simpleBlockItem(TABlocks.RUNE_STONE_GATE_KEYHOLE.get());
        this.simpleBlockItem(TABlocks.DARK_STONE_GATE_KEYHOLE.get());
        this.simpleBlockItem(TABlocks.MOON_TEMPLE_GATE_KEYHOLE.get());
        this.simpleBlockItem(TABlocks.RUNE_STONE_LOOT_GATE_KEYHOLE.get());
        this.simpleBlockItem(TABlocks.MOON_TEMPLE_CELL_GATE_KEYHOLE.get());
        this.simpleBlockItem(TABlocks.SCRAPPER.get());
        this.simpleBlockItem(TABlocks.UMBRA_STONE.get());
        this.simpleBlockItem(TABlocks.UMBRA_STONE_CRACKED.get());
        this.simpleBlockItem(TABlocks.UMBRA_STONE_ROOF_TILES.get());
        this.simpleBlockItem(TABlocks.INDIGO_MUSHROOM_CRYSTAL.get());
        this.simpleBlockItem(TABlocks.STRIPPED_SILENT_TREE_LOG.get());
        this.simpleBlockItem(TABlocks.STRIPPED_SILENT_TREE_WOOD.get());
        this.simpleBlockItem(TABlocks.SILENT_TREE_LOG.get());
        this.simpleBlockItem(TABlocks.SILENT_TREE_WOOD.get());
        this.simpleBlockItem(TABlocks.SILENT_TREE_PLANKS.get());
        this.simpleBlockItem(TABlocks.STRIPPED_WEEPING_WILLOW_LOG.get());
        this.simpleBlockItem(TABlocks.STRIPPED_WEEPING_WILLOW_WOOD.get());
        this.simpleBlockItem(TABlocks.WEEPING_WILLOW_LOG.get());
        this.simpleBlockItem(TABlocks.WEEPING_WILLOW_WOOD.get());
        this.simpleBlockItem(TABlocks.WEEPING_WILLOW_PLANKS.get());
        this.simpleBlockItem(TABlocks.STRIPPED_CURTAIN_TREE_LOG.get());
        this.simpleBlockItem(TABlocks.STRIPPED_CURTAIN_TREE_WOOD.get());
        this.simpleBlockItem(TABlocks.CURTAIN_TREE_LOG.get());
        this.simpleBlockItem(TABlocks.CURTAIN_TREE_WOOD.get());
        this.simpleBlockItem(TABlocks.CURTAIN_TREE_PLANKS.get());
        this.simpleBlockItem(TABlocks.STRIPPED_CURSED_FROST_TREE_LOG.get());
        this.simpleBlockItem(TABlocks.STRIPPED_CURSED_FROST_TREE_WOOD.get());
        this.simpleBlockItem(TABlocks.CURSED_FROST_TREE_LOG.get());
        this.simpleBlockItem(TABlocks.CURSED_FROST_TREE_WOOD.get());
        this.simpleBlockItem(TABlocks.CURSED_FROST_TREE_PLANKS.get());
        this.simpleBlockItem(TABlocks.FILTHY_ICE.get());
        this.simpleBlockItem(TABlocks.MOON_GLASS.get());
        this.simpleBlockItem(TABlocks.AURORIAN_GLASS.get());
        this.simpleBlockItem(TABlocks.DARK_STONE_GLASS.get());
        this.simpleBlockItem(TABlocks.SILENT_TREE_LEAVES.get());
        this.simpleBlockItem(TABlocks.WEEPING_WILLOW_LEAVES.get());
        this.simpleBlockItem(TABlocks.CURTAIN_TREE_LEAVES.get());
        this.simpleBlockItem(TABlocks.CURSED_FROST_TREE_LEAVES.get());
        this.simpleBlockItem(TABlocks.AURORIAN_CRAFTING_TABLE.get());
        this.simpleBlockItemWithParent(TABlocks.AURORIAN_GRASS.get());
        this.simpleBlockItemWithParent(TABlocks.AURORIAN_GRASS_LIGHT.get());
        this.simpleBlockItemWithParent(TABlocks.AURORIAN_WATER_GRASS.get());
        this.simpleBlockItemWithParent(TABlocks.WINTER_ROOT.get());
        this.simpleBlockItemWithParent(TABlocks.NEBULA_BLOSSOM_CLUSTER.get());
        this.simpleBlockItemWithParent(TABlocks.MOON_FROST_FLOWER.get());
        this.simpleBlockItemWithParent(TABlocks.VOID_CANDLE_FLOWER.get());
        this.simpleBlockItemWithParent(TABlocks.EQUINOX_FLOWER.get());
        this.simpleBlockItemWithParent(TABlocks.LAVENDER_PLANT.get());
        this.simpleBlockItemWithParent(TABlocks.PETUNIA_PLANT.get());
        this.simpleBlockItemWithParent(TABlocks.CRISPED_MALLOW.get());
        this.simpleBlockItemWithParent(TABlocks.FROST_SNOW_GRASS.get());
        this.simpleBlockItemWithParent(TABlocks.ICE_CALENDULA.get());
        this.simpleBlockItemWithParent(TABlocks.WICK_GRASS.get());
        this.simpleBlockItemWithParent(TABlocks.SILENT_WOOD_LADDER.get());
        this.simpleBlockItemWithParent(TABlocks.SILENT_WOOD_TORCH.get());
        this.simpleBlockItemWithParent(TABlocks.MOON_TORCH.get());
        this.simpleBlockItemWithParent(TABlocks.RUNE_STONE_BARS.get());
        this.simpleBlockItemWithParent(TABlocks.DARK_STONE_BARS.get());
        this.simpleBlockItemWithParent(TABlocks.MOON_TEMPLE_BARS.get());
        this.withExistingParent(this.name(TABlocks.SILENT_WOOD_CHEST.get()), this.mcLoc("item/chest"))
                .texture("particle", this.modLoc("block/" + this.name(TABlocks.SILENT_TREE_PLANKS.get())));
        this.withExistingParent(this.name(TABlocks.MOON_GLASS_PANE.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.MOON_GLASS.get())));
        this.withExistingParent(this.name(TABlocks.AURORIAN_GLASS_PANE.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.AURORIAN_GLASS.get())));
        this.withExistingParent(this.name(TABlocks.DARK_STONE_GLASS_PANE.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.DARK_STONE_GLASS.get())));
        this.withExistingParent(this.name(TABlocks.SILENT_TREE_SAPLING.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.SILENT_TREE_SAPLING.get())));
        this.withExistingParent(this.name(TABlocks.CURTAIN_TREE_SAPLING.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.CURTAIN_TREE_SAPLING.get())));
        this.withExistingParent(this.name(TABlocks.CURSED_FROST_TREE_SAPLING.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.CURSED_FROST_TREE_SAPLING.get())));
        this.withExistingParent(this.name(TABlocks.INDIGO_MUSHROOM.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.INDIGO_MUSHROOM.get())));
        this.withExistingParent(this.name(TABlocks.SMALL_FILTHY_ICE_SPIKE.get()), this.modLoc("item/ta_small_bud"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.SMALL_FILTHY_ICE_SPIKE.get())));
        this.withExistingParent(this.name(TABlocks.MEDIUM_FILTHY_ICE_SPIKE.get()), this.modLoc("item/ta_medium_bud"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.MEDIUM_FILTHY_ICE_SPIKE.get())));
        this.withExistingParent(this.name(TABlocks.LARGE_FILTHY_ICE_SPIKE.get()), this.modLoc("item/ta_large_bud"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.LARGE_FILTHY_ICE_SPIKE.get())));
        this.withExistingParent(this.name(TABlocks.CERULEAN_CLUSTER.get()), this.modLoc("item/ta_cluster"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.CERULEAN_CLUSTER.get())));
        this.withExistingParent(this.name(TABlocks.LARGE_CERULEAN_BUD.get()), this.modLoc("item/ta_large_bud"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.LARGE_CERULEAN_BUD.get())));
        this.withExistingParent(this.name(TABlocks.MEDIUM_CERULEAN_BUD.get()), this.modLoc("item/ta_medium_bud"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.MEDIUM_CERULEAN_BUD.get())));
        this.withExistingParent(this.name(TABlocks.SMALL_CERULEAN_BUD.get()), this.modLoc("item/ta_small_bud"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.SMALL_CERULEAN_BUD.get())));
        this.withExistingParent(this.name(TABlocks.MOONSTONE_CLUSTER.get()), this.modLoc("item/ta_cluster"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.MOONSTONE_CLUSTER.get())));
        this.withExistingParent(this.name(TABlocks.LARGE_MOONSTONE_BUD.get()), this.modLoc("item/ta_large_bud"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.LARGE_MOONSTONE_BUD.get())));
        this.withExistingParent(this.name(TABlocks.MEDIUM_MOONSTONE_BUD.get()), this.modLoc("item/ta_medium_bud"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.MEDIUM_MOONSTONE_BUD.get())));
        this.withExistingParent(this.name(TABlocks.SMALL_MOONSTONE_BUD.get()), this.modLoc("item/ta_small_bud"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.SMALL_MOONSTONE_BUD.get())));
        this.withExistingParent(this.name(TABlocks.TALL_WICK_GRASS.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.TALL_WICK_GRASS.get()) + "_upper"));
        this.withExistingParent(this.name(TABlocks.TALL_AURORIAN_GRASS.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.TALL_AURORIAN_GRASS.get()) + "_upper"));
        this.withExistingParent(this.name(TABlocks.TALL_LAVENDER_PLANT.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.TALL_LAVENDER_PLANT.get()) + "_upper"));
        this.withExistingParent(this.name(TABlocks.TALL_AURORIAN_WATER_GRASS.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.TALL_AURORIAN_WATER_GRASS.get()) + "_upper"));
        this.withExistingParent(this.name(TABlocks.TALL_AURORIAN_GRASS_LIGHT.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.TALL_AURORIAN_GRASS_LIGHT.get()) + "_upper"));
        for (Block block : TACommonUtils.getKnownBlocks()) {
            if (block instanceof StairBlock || block instanceof ISimpleBlockItem) {
                this.simpleBlockItem(block);
            } else if (block instanceof DoorBlockWithBase doorBlock) {
                this.basicItem(doorBlock.asItem());
            }
        }

        for (Item item : TACommonUtils.getKnownItems()) {
            ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
            if (item.equals(TAItems.CRYSTALLINE_SWORD.get())) continue;
            if (item instanceof DeferredSpawnEggItem) {
                this.withExistingParent(key.getPath(), this.mcLoc("item/template_spawn_egg"));
            } else if (item instanceof TieredItem) {
                this.withExistingParent(key.getPath(), this.mcLoc("item/handheld"))
                        .texture("layer0", this.modLoc("item/" + key.getPath()));
            } else if (item instanceof BowItem) {
                this.bowItem(item);
            } else if (item instanceof ShieldItem) {
                this.shieldItem(item);
            } else if (!(item instanceof BlockItem)) {
                this.basicItem(item);
            }
        }
    }

    private void bowItem(Item item) {
        String name = BuiltInRegistries.ITEM.getKey(item).getPath();
        ModelFile.UncheckedModelFile bowModel = new ModelFile.UncheckedModelFile(this.modLoc("item/" + name));
        this.withExistingParent(name, this.modLoc("item/ta_bow"))
                .texture("layer0", this.modLoc("item/" + name))
                .override().predicate(this.modLoc("pulling"), 1)
                .model(new ModelFile.UncheckedModelFile(this.modLoc("item/" + name + "_pulling_0"))).end()
                .override().predicate(this.modLoc("pulling"), 1).predicate(this.modLoc("pull"), 0.65F)
                .model(new ModelFile.UncheckedModelFile(this.modLoc("item/" + name + "_pulling_1"))).end()
                .override().predicate(this.modLoc("pulling"), 1).predicate(this.modLoc("pull"), 0.9F)
                .model(new ModelFile.UncheckedModelFile(this.modLoc("item/" + name + "_pulling_2"))).end();
        for (int i = 0; i < 3; i++) {
            String path = name + "_pulling_" + i;
            this.getBuilder(path).parent(bowModel).texture("layer0", this.modLoc("item/" + path));
        }
    }

    private void shieldItem(Item item) {
        String name = BuiltInRegistries.ITEM.getKey(item).getPath();
        this.withExistingParent(name + "_blocking", this.modLoc("item/ta_shield_blocking"))
                .texture("layer0", this.modLoc("item/" + name));
        this.withExistingParent(name, this.modLoc("item/ta_shield"))
                .texture("layer0", this.modLoc("item/" + name))
                .override().predicate(this.modLoc("blocking"), 1.0F)
                .model(this.getExistingFile(this.modLoc(name + "_blocking")));
    }

    private void simpleItem(Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        this.withExistingParent(path, ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", TheAurorian.prefix("item/" + path));
    }

    private void simpleBlockItem(Block block) {
        this.withExistingParent(this.name(block), this.modLoc("block/" + this.name(block)));
    }

    private void simpleBlockItemWithParent(Block block) {
        this.withExistingParent(this.name(block), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(block)));
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

}