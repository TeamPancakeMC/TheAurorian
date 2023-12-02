package cn.teampancake.theaurorian.data.loot;

import cn.teampancake.theaurorian.common.blocks.TACropBlock;
import cn.teampancake.theaurorian.registry.TABlocks;
import cn.teampancake.theaurorian.registry.TAItems;
import cn.teampancake.theaurorian.utils.TACommonUtils;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
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
        this.dropSelf(TABlocks.MOONSTONE_ORE.get());
        this.dropSelf(TABlocks.CERULEAN_ORE.get());
        this.dropSelf(TABlocks.INDIGO_MUSHROOM.get());
        this.dropSelf(TABlocks.INDIGO_MUSHROOM_CRYSTAL.get());
        this.dropSelf(TABlocks.MOONLIGHT_FORGE.get());
        this.dropSelf(TABlocks.MOON_GEM.get());
        this.dropSelf(TABlocks.MOON_SAND.get());
        this.dropSelf(TABlocks.MOON_TORCH.get());
        this.dropSelf(TABlocks.UMBRA_STONE.get());
        this.dropSelf(TABlocks.UMBRA_STONE_CRACKED.get());
        this.dropSelf(TABlocks.UMBRA_STONE_ROOF_TILES.get());
        this.dropSelf(TABlocks.SILENT_TREE_LOG.get());
        this.dropSelf(TABlocks.SILENT_TREE_PLANKS.get());
        this.dropSelf(TABlocks.SILENT_TREE_WOOD.get());
        this.dropSelf(TABlocks.SILENT_TREE_SAPLING.get());
        this.dropSelf(TABlocks.SILENT_WOOD_TORCH.get());
        this.dropSelf(TABlocks.SILENT_WOOD_LADDER.get());
        this.dropSelf(TABlocks.WEEPING_WILLOW_LOG.get());
        this.dropSelf(TABlocks.WEEPING_WILLOW_PLANKS.get());
        this.dropSelf(TABlocks.WEEPING_WILLOW_WOOD.get());
        this.dropWhenSilkTouch(TABlocks.AURORIAN_GLASS.get());
        this.dropWhenSilkTouch(TABlocks.MOON_GLASS.get());
        this.dropWhenSilkTouch(TABlocks.AURORIAN_GLASS_PANE.get());
        this.dropWhenSilkTouch(TABlocks.MOON_GLASS_PANE.get());
        this.dropWhenSilkTouch(TABlocks.LAVENDER_PLANT.get());
        this.dropWhenSilkTouch(TABlocks.PETUNIA_PLANT.get());
        this.dropWhenSilkTouch(TABlocks.INDIGO_MUSHROOM_STEM.get());
        this.dropPottedContents(TABlocks.POTTED_AURORIAN_FLOWER_1.get());
        this.dropPottedContents(TABlocks.POTTED_AURORIAN_FLOWER_2.get());
        this.dropPottedContents(TABlocks.POTTED_AURORIAN_FLOWER_3.get());
        this.dropPottedContents(TABlocks.POTTED_EQUINOX_FLOWER.get());
        this.dropPottedContents(TABlocks.POTTED_AURORIAN_GRASS.get());
        this.dropPottedContents(TABlocks.POTTED_LAVENDER_PLANT.get());
        this.dropPottedContents(TABlocks.POTTED_PETUNIA_PLANT.get());
        this.dropPottedContents(TABlocks.POTTED_SILENT_TREE_SAPLING.get());
        this.dropPottedContents(TABlocks.POTTED_AURORIAN_GRASS_LIGHT.get());
        this.dropOther(TABlocks.MOON_WALL_TORCH.get(), TABlocks.MOON_TORCH.get());
        this.dropOther(TABlocks.SILENT_WOOD_WALL_TORCH.get(), TABlocks.SILENT_WOOD_TORCH.get());
        this.add(TABlocks.GEODE_ORE.get(), block -> this.createOreDrop(block, TAItems.CRYSTAL.get()));
        this.add(TABlocks.AURORIAN_COAL_ORE.get(), block -> this.createOreDrop(block, TAItems.AURORIAN_COAL.get()));
        this.add(TABlocks.INDIGO_MUSHROOM_BLOCK.get(), block -> createMushroomBlockDrop(block, TABlocks.INDIGO_MUSHROOM.get()));
        this.add(TABlocks.AURORIAN_STONE.get(), block -> this.createSingleItemTableWithSilkTouch(block, TABlocks.AURORIAN_COBBLESTONE.get()));
        this.add(TABlocks.AURORIAN_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, TABlocks.AURORIAN_DIRT.get()));
        this.add(TABlocks.LIGHT_AURORIAN_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, TABlocks.AURORIAN_DIRT.get()));
        this.add(TABlocks.RED_AURORIAN_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, TABlocks.AURORIAN_DIRT.get()));
        this.add(TABlocks.AURORIAN_FARM_TILE.get(), block -> this.createSingleItemTableWithSilkTouch(block, TABlocks.AURORIAN_DIRT.get()));
        this.add(TABlocks.AURORIAN_GRASS.get(), block -> this.createSilkTouchOrSicklesDispatchTable(block, TABlocks.AURORIAN_GRASS.get()));
        this.add(TABlocks.AURORIAN_GRASS_LIGHT.get(), block -> this.createSilkTouchOrSicklesDispatchTable(block, TABlocks.AURORIAN_GRASS_LIGHT.get()));
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
        for (Block block : TACommonUtils.getKnownBlocks()) {
            float f = Blocks.BEDROCK.getExplosionResistance();
            boolean isStair = block instanceof StairBlock;
            boolean isSlab = block instanceof SlabBlock;
            boolean isWall = block instanceof WallBlock;
            boolean hardLikeBedrock = block.getExplosionResistance() == f;
            boolean notHardLikeBedrock = block.getExplosionResistance() != f;
            if ((hardLikeBedrock || ((isStair || isSlab || isWall) && notHardLikeBedrock))) {
                this.dropSelf(block);
            }
        }
    }

    private LootTable.Builder createSilkTouchOrSicklesDispatchTable(Block block, ItemLike itemLike) {
        final LootItemCondition.Builder hasSickles = MatchTool.toolMatches(ItemPredicate.Builder.item()
                .of(TAItems.AURORIAN_STONE_SICKLE.get(), TAItems.AURORIAN_STEEL_SICKLE.get(), TAItems.MOONSTONE_SICKLE.get()));
        return createSelfDropDispatchTable(block, hasSickles, this.applyExplosionDecay(block, LootItem.lootTableItem(itemLike)));
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