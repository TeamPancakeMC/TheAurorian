package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.TABlocks;
import cn.teampancake.theaurorian.registry.TAItems;
import cn.teampancake.theaurorian.utils.TACommonUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@SuppressWarnings({"ConstantConditions", "unused"})
public class TAItemModelProvider extends ItemModelProvider {

    public TAItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.simpleItem(TAItems.SILK_BERRY.get());
        this.simpleItem(TAItems.LAVENDER_SEEDS.get());
        this.simpleBlockItem(TABlocks.GEODE_ORE.get());
        this.simpleBlockItem(TABlocks.CERULEAN_ORE.get());
        this.simpleBlockItem(TABlocks.MOONSTONE_ORE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_DIRT.get());
        this.simpleBlockItem(TABlocks.AURORIAN_STONE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_COAL_ORE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_FARM_TILE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_STONE_BRICKS.get());
        this.simpleBlockItem(TABlocks.AURORIAN_COBBLESTONE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_GRASS_BLOCK.get());
        this.simpleBlockItem(TABlocks.AURORIAN_GRASS_LIGHT_BLOCK.get());
        this.simpleBlockItem(TABlocks.AURORIAN_PORTAL_FRAME_BRICKS.get());
        this.simpleBlockItem(TABlocks.AURORIAN_FURNACE.get());
        this.simpleBlockItem(TABlocks.AURORIAN_PERIDOTITE.get());
        this.simpleBlockItem(TABlocks.MOON_SAND.get());
        this.simpleBlockItem(TABlocks.MOON_SAND_RIVER.get());
        this.simpleBlockItem(TABlocks.MOON_SAND_STONE_1.get());
        this.simpleBlockItem(TABlocks.MOON_SAND_STONE_2.get());
        this.simpleBlockItem(TABlocks.MOON_SAND_STONE_3.get());
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
        this.simpleBlockItem(TABlocks.RUNE_STONE_PILLAR.get());
        this.simpleBlockItem(TABlocks.MOON_TEMPLE_BRICKS.get());
        this.simpleBlockItem(TABlocks.DARK_STONE_BRICKS.get());
        this.simpleBlockItem(TABlocks.DARK_STONE_FANCY.get());
        this.simpleBlockItem(TABlocks.DARK_STONE_LAYERS.get());
        this.simpleBlockItem(TABlocks.SMOOTH_MOON_TEMPLE_BRICKS.get());
        this.simpleBlockItem(TABlocks.SMOOTH_AURORIAN_PERIDOTITE.get());
        this.simpleBlockItem(TABlocks.RUNE_STONE_LAMP.get());
        this.simpleBlockItem(TABlocks.DARK_STONE_LAMP.get());
        this.simpleBlockItem(TABlocks.MOON_TEMPLE_LAMP.get());
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
        this.simpleBlockItem(TABlocks.INDIGO_MUSHROOM.get());
        this.simpleBlockItem(TABlocks.INDIGO_MUSHROOM_BLOCK.get());
        this.simpleBlockItem(TABlocks.INDIGO_MUSHROOM_STEM.get());
        this.simpleBlockItem(TABlocks.INDIGO_MUSHROOM_CRYSTAL.get());
        this.simpleBlockItem(TABlocks.SILENT_TREE_LOG.get());
        this.simpleBlockItem(TABlocks.SILENT_TREE_WOOD.get());
        this.simpleBlockItem(TABlocks.SILENT_TREE_PLANKS.get());
        this.simpleBlockItem(TABlocks.WEEPING_WILLOW_LOG.get());
        this.simpleBlockItem(TABlocks.WEEPING_WILLOW_WOOD.get());
        this.simpleBlockItem(TABlocks.WEEPING_WILLOW_PLANKS.get());
        this.simpleBlockItem(TABlocks.MOON_GLASS.get());
        this.simpleBlockItem(TABlocks.AURORIAN_GLASS.get());
        this.simpleBlockItem(TABlocks.SILENT_TREE_LEAVES.get());
        this.simpleBlockItem(TABlocks.WEEPING_WILLOW_LEAVES.get());
        this.simpleBlockItem(TABlocks.SILENT_WOOD_CRAFTING_TABLE.get());
        this.simpleBlockItemWithParent(TABlocks.AURORIAN_GRASS.get());
        this.simpleBlockItemWithParent(TABlocks.AURORIAN_GRASS_LIGHT.get());
        this.simpleBlockItemWithParent(TABlocks.AURORIAN_FLOWER_1.get());
        this.simpleBlockItemWithParent(TABlocks.AURORIAN_FLOWER_2.get());
        this.simpleBlockItemWithParent(TABlocks.AURORIAN_FLOWER_3.get());
        this.simpleBlockItemWithParent(TABlocks.AURORIAN_FLOWER_4.get());
        this.simpleBlockItemWithParent(TABlocks.LAVENDER_PLANT.get());
        this.simpleBlockItemWithParent(TABlocks.PETUNIA_PLANT.get());
        this.simpleBlockItemWithParent(TABlocks.SILENT_WOOD_LADDER.get());
        this.simpleBlockItemWithParent(TABlocks.SILENT_WOOD_TORCH.get());
        this.simpleBlockItemWithParent(TABlocks.MOON_TORCH.get());
        this.simpleBlockItemWithParent(TABlocks.RUNE_STONE_BARS.get());
        this.simpleBlockItemWithParent(TABlocks.MOON_TEMPLE_BARS.get());
        this.withExistingParent(this.name(TABlocks.AURORIAN_GLASS_PANE.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.AURORIAN_GLASS.get())));
        this.withExistingParent(this.name(TABlocks.MOON_GLASS_PANE.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.MOON_GLASS.get())));
        this.withExistingParent(this.name(TABlocks.SILENT_TREE_SAPLING.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(TABlocks.SILENT_TREE_SAPLING.get())));

        for (Block block : TACommonUtils.getKnownBlocks()) {
            if (block instanceof StairBlock || block instanceof SlabBlock) {
                this.simpleBlockItem(block);
            }
        }

        for (Item item : TACommonUtils.getKnownItems()) {
            ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
            if (item instanceof ForgeSpawnEggItem) {
                this.withExistingParent(key.getPath(), this.mcLoc("item/template_spawn_egg"));
            } else if (item instanceof TieredItem) {
                this.withExistingParent(key.getPath(), this.mcLoc("item/handheld"))
                        .texture("layer0", this.modLoc("item/" + key.getPath()));
            } else if (item instanceof ShieldItem) {
                this.shieldItem(item);
            } else if (!(item instanceof BlockItem) && item != TAItems.SLEEPING_BLACK_TEA.get()) {
                this.basicItem(item);
            }
        }

    }

    private void shieldItem(Item item) {
        String path = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
        this.withExistingParent(path + "_blocking", this.modLoc("item/ta_shield_blocking"))
                .texture("layer0", this.modLoc("item/" + path));
        this.withExistingParent(path, this.modLoc("item/ta_shield"))
                .texture("layer0", this.modLoc("item/" + path))
                .override().predicate(this.mcLoc("block"), 1.0F)
                .model(this.getExistingFile(this.modLoc(path + "_blocking")));
    }

    private void simpleItem(Item item) {
        String path = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
        withExistingParent(path, new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(AurorianMod.MOD_ID, "item/" + path));
    }

    private void simpleBlockItem(Block block) {
        this.withExistingParent(this.name(block), this.modLoc("block/" + this.name(block)));
    }

    private void simpleBlockItemWithParent(Block block) {
        this.withExistingParent(this.name(block), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(block)));
    }

    private String name(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

}