package cn.teampancake.theaurorian.common.data.datagen.loot;

import cn.teampancake.theaurorian.common.blocks.AlchemyTable;
import cn.teampancake.theaurorian.common.blocks.BlueberryBush;
import cn.teampancake.theaurorian.common.blocks.RelicTable;
import cn.teampancake.theaurorian.common.blocks.TACropBlock;
import cn.teampancake.theaurorian.common.blocks.base.DoorBlockWithBase;
import cn.teampancake.theaurorian.common.blocks.base.IHasBaseBlock;
import cn.teampancake.theaurorian.common.blocks.state.properties.AlchemyTablePart;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
public class TABlockLoot extends VanillaBlockLoot {

    @Override
    protected void generate() {
        this.dropSelf(TABlocks.AURORIAN_DIRT.get());
        this.dropSelf(TABlocks.AURORIAN_STONE_BRICKS.get());
        this.dropSelf(TABlocks.AURORIAN_COBBLESTONE.get());
        this.dropSelf(TABlocks.AURORIAN_FURNACE.get());
        this.dropSelf(TABlocks.AURORIAN_FURNACE_CHIMNEY.get());
        this.dropSelf(TABlocks.AURORIAN_PORTAL_FRAME_BRICKS.get());
        this.dropSelf(TABlocks.AURORIAN_PERIDOTITE.get());
        this.dropSelf(TABlocks.SMOOTH_AURORIAN_PERIDOTITE.get());
        this.dropSelf(TABlocks.MYSTERIUM_WOOL.get());
        this.dropSelf(TABlocks.NEBULA_BLOSSOM_CLUSTER.get());
        this.dropSelf(TABlocks.MOON_FROST_FLOWER.get());
        this.dropSelf(TABlocks.VOID_CANDLE_FLOWER.get());
        this.dropSelf(TABlocks.EQUINOX_FLOWER.get());
        this.dropSelf(TABlocks.CERULEAN_BLOCK.get());
        this.dropSelf(TABlocks.MOONSTONE_BLOCK.get());
        this.dropSelf(TABlocks.INDIGO_MUSHROOM.get());
        this.dropSelf(TABlocks.INDIGO_MUSHROOM_CRYSTAL.get());
        this.dropSelf(TABlocks.MOONLIGHT_FORGE.get());
        this.dropSelf(TABlocks.MOON_GEM.get());
        this.dropSelf(TABlocks.MOON_SAND.get());
        this.dropSelf(TABlocks.MOON_SAND_RIVER.get());
        this.dropSelf(TABlocks.MOON_TORCH.get());
        this.dropSelf(TABlocks.UMBRA_STONE.get());
        this.dropSelf(TABlocks.UMBRA_STONE_CRACKED.get());
        this.dropSelf(TABlocks.UMBRA_STONE_ROOF_TILES.get());
        this.dropSelf(TABlocks.SILENT_TREE_LOG.get());
        this.dropSelf(TABlocks.SILENT_TREE_PLANKS.get());
        this.dropSelf(TABlocks.SILENT_TREE_WOOD.get());
        this.dropSelf(TABlocks.SILENT_TREE_SAPLING.get());
        this.dropSelf(TABlocks.SILENT_WOOD_TORCH.get());
        this.dropSelf(TABlocks.SILENT_WOOD_CHEST.get());
        this.dropSelf(TABlocks.SILENT_WOOD_LADDER.get());
        this.dropSelf(TABlocks.ASTROLOGER.get());
        this.dropSelf(TABlocks.AURORIAN_CRAFTING_TABLE.get());
        this.dropSelf(TABlocks.WEEPING_WILLOW_LOG.get());
        this.dropSelf(TABlocks.WEEPING_WILLOW_PLANKS.get());
        this.dropSelf(TABlocks.WEEPING_WILLOW_WOOD.get());
        this.dropSelf(TABlocks.CURTAIN_TREE_LOG.get());
        this.dropSelf(TABlocks.CURTAIN_TREE_PLANKS.get());
        this.dropSelf(TABlocks.CURTAIN_TREE_WOOD.get());
        this.dropSelf(TABlocks.CURTAIN_TREE_SAPLING.get());
        this.dropSelf(TABlocks.CURSED_FROST_TREE_LOG.get());
        this.dropSelf(TABlocks.CURSED_FROST_TREE_PLANKS.get());
        this.dropSelf(TABlocks.CURSED_FROST_TREE_WOOD.get());
        this.dropSelf(TABlocks.CURSED_FROST_TREE_SAPLING.get());
        this.dropWhenSilkTouch(TABlocks.SILENT_CAMPFIRE.get());
        this.dropWhenSilkTouch(TABlocks.FILTHY_ICE.get());
        this.dropWhenSilkTouch(TABlocks.MOON_GLASS.get());
        this.dropWhenSilkTouch(TABlocks.AURORIAN_GLASS.get());
        this.dropWhenSilkTouch(TABlocks.DARK_STONE_GLASS.get());
        this.dropWhenSilkTouch(TABlocks.MOON_GLASS_PANE.get());
        this.dropWhenSilkTouch(TABlocks.AURORIAN_GLASS_PANE.get());
        this.dropWhenSilkTouch(TABlocks.DARK_STONE_GLASS_PANE.get());
        this.dropWhenSilkTouch(TABlocks.INDIGO_MUSHROOM_STEM.get());
        this.dropPottedContents(TABlocks.POTTED_AURORIAN_FLOWER_1.get());
        this.dropPottedContents(TABlocks.POTTED_AURORIAN_FLOWER_2.get());
        this.dropPottedContents(TABlocks.POTTED_AURORIAN_FLOWER_3.get());
        this.dropPottedContents(TABlocks.POTTED_EQUINOX_FLOWER.get());
        this.dropPottedContents(TABlocks.POTTED_WICK_GRASS.get());
        this.dropPottedContents(TABlocks.POTTED_AURORIAN_GRASS.get());
        this.dropPottedContents(TABlocks.POTTED_LAVENDER_PLANT.get());
        this.dropPottedContents(TABlocks.POTTED_PETUNIA_PLANT.get());
        this.dropPottedContents(TABlocks.POTTED_SILENT_TREE_SAPLING.get());
        this.dropPottedContents(TABlocks.POTTED_AURORIAN_GRASS_LIGHT.get());
        this.dropPottedContents(TABlocks.POTTED_CURTAIN_TREE_SAPLING.get());
        this.dropPottedContents(TABlocks.POTTED_CURSED_FROST_TREE_SAPLING.get());
        this.dropOther(TABlocks.MOON_WALL_TORCH.get(), TABlocks.MOON_TORCH.get());
        this.dropOther(TABlocks.SILENT_WOOD_WALL_TORCH.get(), TABlocks.SILENT_WOOD_TORCH.get());
        this.dropNuggets(TABlocks.CERULEAN_CLUSTER.get(), TAItems.CERULEAN_NUGGET.get(), 7.0F, 9.0F);
        this.dropNuggets(TABlocks.LARGE_CERULEAN_BUD.get(), TAItems.CERULEAN_NUGGET.get(), 5.0F, 6.0F);
        this.dropNuggets(TABlocks.MEDIUM_CERULEAN_BUD.get(), TAItems.CERULEAN_NUGGET.get(), 3.0F, 4.0F);
        this.dropNuggets(TABlocks.SMALL_CERULEAN_BUD.get(), TAItems.CERULEAN_NUGGET.get(), 0.0F, 2.0F);
        this.dropNuggets(TABlocks.MOONSTONE_CLUSTER.get(), TAItems.MOONSTONE_NUGGET.get(), 7.0F, 9.0F);
        this.dropNuggets(TABlocks.LARGE_MOONSTONE_BUD.get(), TAItems.MOONSTONE_NUGGET.get(), 5.0F, 6.0F);
        this.dropNuggets(TABlocks.MEDIUM_MOONSTONE_BUD.get(), TAItems.MOONSTONE_NUGGET.get(), 3.0F, 4.0F);
        this.dropNuggets(TABlocks.SMALL_MOONSTONE_BUD.get(), TAItems.MOONSTONE_NUGGET.get(), 0.0F, 2.0F);
        this.add(TABlocks.SILENT_WOOD_SIGN.get(), block -> this.createSingleItemTable(TAItems.SILENT_WOOD_SIGN.get()));
        this.add(TABlocks.SILENT_WOOD_WALL_SIGN.get(), block -> this.createSingleItemTable(TAItems.SILENT_WOOD_SIGN.get()));
        this.add(TABlocks.SILENT_WOOD_HANGING_SIGN.get(), block -> this.createSingleItemTable(TAItems.SILENT_WOOD_HANGING_SIGN.get()));
        this.add(TABlocks.SILENT_WOOD_WALL_HANGING_SIGN.get(), block -> this.createSingleItemTable(TAItems.SILENT_WOOD_HANGING_SIGN.get()));
        this.add(TABlocks.WEEPING_WILLOW_WOOD_SIGN.get(), block -> this.createSingleItemTable(TAItems.WEEPING_WILLOW_WOOD_SIGN.get()));
        this.add(TABlocks.WEEPING_WILLOW_WOOD_WALL_SIGN.get(), block -> this.createSingleItemTable(TAItems.WEEPING_WILLOW_WOOD_SIGN.get()));
        this.add(TABlocks.WEEPING_WILLOW_WOOD_HANGING_SIGN.get(), block -> this.createSingleItemTable(TAItems.WEEPING_WILLOW_WOOD_HANGING_SIGN.get()));
        this.add(TABlocks.WEEPING_WILLOW_WOOD_WALL_HANGING_SIGN.get(), block -> this.createSingleItemTable(TAItems.WEEPING_WILLOW_WOOD_HANGING_SIGN.get()));
        this.add(TABlocks.CURTAIN_WOOD_SIGN.get(), block -> this.createSingleItemTable(TAItems.CURTAIN_WOOD_SIGN.get()));
        this.add(TABlocks.CURTAIN_WOOD_WALL_SIGN.get(), block -> this.createSingleItemTable(TAItems.CURTAIN_WOOD_SIGN.get()));
        this.add(TABlocks.CURTAIN_WOOD_HANGING_SIGN.get(), block -> this.createSingleItemTable(TAItems.CURTAIN_WOOD_HANGING_SIGN.get()));
        this.add(TABlocks.CURTAIN_WOOD_WALL_HANGING_SIGN.get(), block -> this.createSingleItemTable(TAItems.CURTAIN_WOOD_HANGING_SIGN.get()));
        this.add(TABlocks.CURSED_FROST_WOOD_SIGN.get(), block -> this.createSingleItemTable(TAItems.CURSED_FROST_WOOD_SIGN.get()));
        this.add(TABlocks.CURSED_FROST_WOOD_WALL_SIGN.get(), block -> this.createSingleItemTable(TAItems.CURSED_FROST_WOOD_SIGN.get()));
        this.add(TABlocks.CURSED_FROST_WOOD_HANGING_SIGN.get(), block -> this.createSingleItemTable(TAItems.CURSED_FROST_WOOD_HANGING_SIGN.get()));
        this.add(TABlocks.CURSED_FROST_WOOD_WALL_HANGING_SIGN.get(), block -> this.createSingleItemTable(TAItems.CURSED_FROST_WOOD_HANGING_SIGN.get()));
        this.add(TABlocks.ALCHEMY_TABLE.get(), block -> this.createSinglePropConditionTable(block, AlchemyTable.PART, AlchemyTablePart.RIGHT));
        this.add(TABlocks.RELIC_TABLE.get(), block -> this.createSinglePropConditionTable(block, RelicTable.HALF, DoubleBlockHalf.LOWER));
        this.add(TABlocks.MYSTERIUM_WOOL_BED.get(), block -> this.createSinglePropConditionTable(block, BedBlock.PART, BedPart.HEAD));
        this.add(TABlocks.MOONSTONE_ORE.get(), block -> this.createOreDrop(block, TAItems.RAW_MOONSTONE.get()));
        this.add(TABlocks.EROSIVE_MOONSTONE_ORE.get(), block -> this.createOreDrop(block, TAItems.RAW_MOONSTONE.get()));
        this.add(TABlocks.CERULEAN_ORE.get(), block -> this.createOreDrop(block, TAItems.RAW_CERULEAN.get()));
        this.add(TABlocks.EROSIVE_CERULEAN_ORE.get(), block -> this.createOreDrop(block, TAItems.RAW_CERULEAN.get()));
        this.add(TABlocks.GEODE_ORE.get(), block -> this.createOreDrop(block, TAItems.CRYSTAL.get()));
        this.add(TABlocks.EROSIVE_GEODE_ORE.get(), block -> this.createOreDrop(block, TAItems.CRYSTAL.get()));
        this.add(TABlocks.AURORIAN_EMERALD_ORE.get(),block -> this.createOreDrop(block,Items.EMERALD));
        this.add(TABlocks.AURORIAN_IRON_ORE.get(),block -> this.createOreDrop(block,Items.RAW_IRON));
        this.add(TABlocks.AURORIAN_GOLD_ORE.get(),block -> this.createOreDrop(block,Items.RAW_GOLD));
        this.add(TABlocks.AURORIAN_COPPER_ORE.get(),block -> this.createOreDrop(block,Items.RAW_COPPER));
        this.add(TABlocks.AURORIAN_REDSTONE_ORE.get(),block -> this.createOreDrop(block,Items.REDSTONE));
        this.add(TABlocks.AURORIAN_DIAMOND_ORE.get(),block -> this.createOreDrop(block,Items.DIAMOND));
        this.add(TABlocks.AURORIAN_LAPIS_ORE.get(),block -> this.createOreDrop(block,Items.LAPIS_LAZULI));
        this.add(TABlocks.EROSIVE_AURORIAN_EMERALD_ORE.get(), block -> this.createOreDrop(block,Items.EMERALD));
        this.add(TABlocks.EROSIVE_AURORIAN_IRON_ORE.get(), block -> this.createOreDrop(block,Items.RAW_IRON));
        this.add(TABlocks.EROSIVE_AURORIAN_GOLD_ORE.get(), block -> this.createOreDrop(block,Items.RAW_GOLD));
        this.add(TABlocks.EROSIVE_AURORIAN_COPPER_ORE.get(), block -> this.createOreDrop(block,Items.RAW_COPPER));
        this.add(TABlocks.EROSIVE_AURORIAN_REDSTONE_ORE.get(), block -> this.createOreDrop(block,Items.REDSTONE));
        this.add(TABlocks.EROSIVE_AURORIAN_DIAMOND_ORE.get(), block -> this.createOreDrop(block,Items.DIAMOND));
        this.add(TABlocks.EROSIVE_AURORIAN_LAPIS_ORE.get(), block -> this.createOreDrop(block,Items.LAPIS_LAZULI));
        this.add(TABlocks.AURORIAN_COAL_ORE.get(), block -> this.createOreDrop(block, TAItems.AURORIAN_COAL.get()));
        this.add(TABlocks.INDIGO_MUSHROOM_BLOCK.get(), block -> this.createMushroomBlockDrop(block, TABlocks.INDIGO_MUSHROOM.get()));
        this.add(TABlocks.AURORIAN_STONE.get(), block -> this.createSingleItemTableWithSilkTouch(block, TABlocks.AURORIAN_COBBLESTONE.get()));
        this.add(TABlocks.AURORIAN_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, TABlocks.AURORIAN_DIRT.get()));
        this.add(TABlocks.LIGHT_AURORIAN_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, TABlocks.AURORIAN_DIRT.get()));
        this.add(TABlocks.SNOW_AURORIAN_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, TABlocks.AURORIAN_DIRT.get()));
        this.add(TABlocks.RED_AURORIAN_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, TABlocks.AURORIAN_DIRT.get()));
        this.add(TABlocks.AURORIAN_FARM_TILE.get(), block -> this.createSingleItemTableWithSilkTouch(block, TABlocks.AURORIAN_DIRT.get()));
        this.add(TABlocks.AURORIAN_GRASS.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.AURORIAN_GRASS.get()));
        this.add(TABlocks.TALL_AURORIAN_GRASS.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.AURORIAN_GRASS.get()));
        this.add(TABlocks.AURORIAN_WATER_GRASS.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.AURORIAN_WATER_GRASS.get()));
        this.add(TABlocks.TALL_AURORIAN_WATER_GRASS.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.AURORIAN_WATER_GRASS.get()));
        this.add(TABlocks.AURORIAN_GRASS_LIGHT.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.AURORIAN_GRASS_LIGHT.get()));
        this.add(TABlocks.TALL_AURORIAN_GRASS_LIGHT.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.AURORIAN_GRASS_LIGHT.get()));
        this.add(TABlocks.WICK_GRASS.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.WICK_GRASS.get()));
        this.add(TABlocks.TALL_WICK_GRASS.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.WICK_GRASS.get()));
        this.add(TABlocks.LAVENDER_PLANT.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.LAVENDER_PLANT.get()));
        this.add(TABlocks.TALL_LAVENDER_PLANT.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.LAVENDER_PLANT.get()));
        this.add(TABlocks.PETUNIA_PLANT.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.PETUNIA_PLANT.get()));
        this.add(TABlocks.ICE_CALENDULA.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.ICE_CALENDULA.get()));
        this.add(TABlocks.AURORIAN_WINTER_ROOT.get(), block -> this.createSilkTouchOrSicklesDispatchTable(TABlocks.AURORIAN_WINTER_ROOT.get()));
        this.add(TABlocks.LAVENDER_CROP.get(), this.createCropDrops(TABlocks.LAVENDER_CROP.get(), TAItems.LAVENDER.get(), TAItems.LAVENDER_SEEDS.get(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(TABlocks.LAVENDER_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TACropBlock.AGE, 3))));
        this.add(TABlocks.SILK_BERRY_CROP.get(), block -> this.applyExplosionDecay(block, LootTable.lootTable()
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(TABlocks.SILK_BERRY_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(TACropBlock.AGE, 3)))
                        .add(LootItem.lootTableItem(TAItems.SILK_BERRY.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(TABlocks.SILK_BERRY_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(TACropBlock.AGE, 2)))
                        .add(LootItem.lootTableItem(TAItems.SILK_BERRY.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));
        this.add(TABlocks.BLUEBERRY_BUSH.get(), block -> this.applyExplosionDecay(block, LootTable.lootTable()
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(TABlocks.BLUEBERRY_BUSH.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(BlueberryBush.AGE, 3)))
                        .add(LootItem.lootTableItem(TAItems.BLUEBERRY.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(TABlocks.BLUEBERRY_BUSH.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(BlueberryBush.AGE, 2)))
                        .add(LootItem.lootTableItem(TAItems.BLUEBERRY.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));
        this.add(TABlocks.URN.get(), block -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
                .add(LootItem.lootTableItem(TAItems.RUNE_STONE_KEY.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))).setWeight(5))
                .add(LootItem.lootTableItem(TAItems.CERULEAN_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).setWeight(10))
                .add(LootItem.lootTableItem(TAItems.MOONSTONE_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).setWeight(10))
                .add(LootItem.lootTableItem(TAItems.SILK_BERRY_JAM.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))).setWeight(25))
                .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomApplicableEnchantment()).setWeight(15))));
        for (Block block : TACommonUtils.getKnownBlocks()) {
            float f = Blocks.BEDROCK.getExplosionResistance();
            boolean isStair = block instanceof StairBlock;
            boolean isWall = block instanceof WallBlock;
            boolean hardLikeBedrock = block.getExplosionResistance() == f;
            boolean notHardLikeBedrock = block.getExplosionResistance() != f;
            if ((hardLikeBedrock || ((isStair || isWall || block instanceof IHasBaseBlock) && notHardLikeBedrock))) {
                this.dropSelf(block);
            } else if (block instanceof DoorBlockWithBase doorBlock) {
                this.add(doorBlock, block1 -> this.createDoorTable(doorBlock));
            }
        }
    }

    private void dropNuggets(Block clusterBlock, Item nuggetItem, float min, float max) {
        this.add(clusterBlock, block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(nuggetItem)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES)))
                .otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(nuggetItem)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
    }

    private LootTable.Builder createSilkTouchOrSicklesDispatchTable(ItemLike itemLike) {
        final LootItemCondition.Builder hasSickles = MatchTool.toolMatches(ItemPredicate.Builder.item()
                .of(TAItems.AURORIAN_STONE_SICKLE.get(), TAItems.SILENT_WOOD_SICKLE.get(), TAItems.MOONSTONE_SICKLE.get()));
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(itemLike).when(HAS_SHEARS.or(HAS_SILK_TOUCH)))
                .add(LootItem.lootTableItem(TAItems.PLANT_FIBER.get()).when(hasSickles)));
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        this.generate();
        Set<ResourceLocation> set = new HashSet<>();
        for (Block block : TACommonUtils.getKnownBlocks()) {
            if (block.isEnabled(this.enabledFeatures)) {
                ResourceLocation lootTable = block.getLootTable();
                if (lootTable != BuiltInLootTables.EMPTY && set.add(lootTable)) {
                    LootTable.Builder lootTable$builder = this.map.remove(lootTable);
                    if (lootTable$builder != null) {
                        biConsumer.accept(lootTable, lootTable$builder);
                    }
                }
            }
        }
    }

}