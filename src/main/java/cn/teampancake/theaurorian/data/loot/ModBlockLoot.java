package cn.teampancake.theaurorian.data.loot;

import cn.teampancake.theaurorian.common.blocks.AbstractCropBlock;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModItems;
import cn.teampancake.theaurorian.utils.ModCommonUtils;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
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
public class ModBlockLoot extends VanillaBlockLoot {

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.AURORIAN_DIRT.get());
        this.dropSelf(ModBlocks.AURORIAN_STONE_BRICKS.get());
        this.dropSelf(ModBlocks.AURORIAN_COBBLESTONE.get());
        this.dropSelf(ModBlocks.AURORIAN_FURNACE.get());
        this.dropSelf(ModBlocks.AURORIAN_FURNACE_CHIMNEY.get());
        this.dropSelf(ModBlocks.AURORIAN_PORTAL_FRAME_BRICKS.get());
        this.dropSelf(ModBlocks.AURORIAN_PERIDOTITE.get());
        this.dropSelf(ModBlocks.SMOOTH_AURORIAN_PERIDOTITE.get());
        this.dropSelf(ModBlocks.MOONSTONE_ORE.get());
        this.dropSelf(ModBlocks.CERULEAN_ORE.get());
        this.dropSelf(ModBlocks.INDIGO_MUSHROOM.get());
        this.dropSelf(ModBlocks.INDIGO_MUSHROOM_CRYSTAL.get());
        this.dropSelf(ModBlocks.MOONLIGHT_FORGE.get());
        this.dropSelf(ModBlocks.MOON_GEM.get());
        this.dropSelf(ModBlocks.MOON_SAND.get());
        this.dropSelf(ModBlocks.MOON_TORCH.get());
        this.dropSelf(ModBlocks.UMBRA_STONE.get());
        this.dropSelf(ModBlocks.UMBRA_STONE_CRACKED.get());
        this.dropSelf(ModBlocks.UMBRA_STONE_ROOF_TILES.get());
        this.dropSelf(ModBlocks.SILENT_TREE_LOG.get());
        this.dropSelf(ModBlocks.SILENT_TREE_PLANKS.get());
        this.dropSelf(ModBlocks.SILENT_TREE_WOOD.get());
        this.dropSelf(ModBlocks.SILENT_WOOD_TORCH.get());
        this.dropSelf(ModBlocks.SILENT_WOOD_LADDER.get());
        this.dropSelf(ModBlocks.WEEPING_WILLOW_LOG.get());
        this.dropSelf(ModBlocks.WEEPING_WILLOW_PLANKS.get());
        this.dropSelf(ModBlocks.WEEPING_WILLOW_WOOD.get());
        this.dropWhenSilkTouch(ModBlocks.AURORIAN_GLASS.get());
        this.dropWhenSilkTouch(ModBlocks.MOON_GLASS.get());
        this.dropWhenSilkTouch(ModBlocks.AURORIAN_GLASS_PANE.get());
        this.dropWhenSilkTouch(ModBlocks.MOON_GLASS_PANE.get());
        this.dropWhenSilkTouch(ModBlocks.PETUNIA_PLANT.get());
        this.dropWhenSilkTouch(ModBlocks.INDIGO_MUSHROOM_STEM.get());
        this.dropOther(ModBlocks.MOON_WALL_TORCH.get(), ModBlocks.MOON_TORCH.get());
        this.dropOther(ModBlocks.SILENT_WOOD_WALL_TORCH.get(), ModBlocks.SILENT_WOOD_TORCH.get());
        this.add(ModBlocks.GEODE_ORE.get(), block -> this.createOreDrop(block, ModItems.CRYSTAL.get()));
        this.add(ModBlocks.AURORIAN_COAL_ORE.get(), block -> this.createOreDrop(block, ModItems.AURORIAN_COAL.get()));
        this.add(ModBlocks.INDIGO_MUSHROOM_BLOCK.get(), block -> createMushroomBlockDrop(block, ModBlocks.INDIGO_MUSHROOM.get()));
        this.add(ModBlocks.AURORIAN_STONE.get(), block -> this.createSingleItemTableWithSilkTouch(block, ModBlocks.AURORIAN_COBBLESTONE.get()));
        this.add(ModBlocks.AURORIAN_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, ModBlocks.AURORIAN_DIRT.get()));
        this.add(ModBlocks.AURORIAN_GRASS_LIGHT_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, ModBlocks.AURORIAN_DIRT.get()));
        this.add(ModBlocks.AURORIAN_FARM_TILE.get(), block -> this.createSingleItemTableWithSilkTouch(block, ModBlocks.AURORIAN_DIRT.get()));
        this.add(ModBlocks.AURORIAN_GRASS.get(), block -> this.createSilkTouchOrSicklesDispatchTable(block, ModBlocks.AURORIAN_GRASS.get()));
        this.add(ModBlocks.AURORIAN_GRASS_LIGHT.get(), block -> this.createSilkTouchOrSicklesDispatchTable(block, ModBlocks.AURORIAN_GRASS_LIGHT.get()));
        this.add(ModBlocks.LAVENDER_CROP.get(), this.createCropDrops(ModBlocks.LAVENDER_CROP.get(), ModItems.LAVENDER.get(), ModItems.LAVENDER_SEEDS.get(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.LAVENDER_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractCropBlock.AGE, 3))));
        this.add(ModBlocks.SILK_BERRY_CROP.get(), block -> this.applyExplosionDecay(block, LootTable.lootTable()
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(ModBlocks.SILK_BERRY_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(AbstractCropBlock.AGE, 3)))
                        .add(LootItem.lootTableItem(ModItems.SILK_BERRY.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(ModBlocks.SILK_BERRY_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(AbstractCropBlock.AGE, 2)))
                        .add(LootItem.lootTableItem(ModItems.SILK_BERRY.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));
        for (Block block : ModCommonUtils.getKnownBlocks()) {
            float f = Blocks.BEDROCK.getExplosionResistance();
            boolean flag1 = block instanceof StairBlock stairBlock
                    && stairBlock.getExplosionResistance() != f;
            boolean flag2 = block.getExplosionResistance() == f;
            if ((flag2 || flag1)) {
                this.dropSelf(block);
            }
        }
    }

    private LootTable.Builder createSilkTouchOrSicklesDispatchTable(Block block, ItemLike itemLike) {
        final LootItemCondition.Builder hasSickles = MatchTool.toolMatches(ItemPredicate.Builder.item()
                .of(ModItems.AURORIAN_STONE_SICKLE.get(), ModItems.AURORIAN_STEEL_SICKLE.get(), ModItems.MOONSTONE_SICKLE.get()));
        return createSelfDropDispatchTable(block, hasSickles, this.applyExplosionDecay(block, LootItem.lootTableItem(itemLike)));
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        this.generate();
        Set<ResourceLocation> set = new HashSet<>();
        for (Block block : ModCommonUtils.getKnownBlocks()) {
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