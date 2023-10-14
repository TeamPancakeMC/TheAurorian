package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModItems;
import cn.teampancake.theaurorian.utils.ModCommonUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@SuppressWarnings({"ConstantConditions", "unused"})
public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.simpleItem(ModItems.SILK_BERRY.get());
        this.simpleItem(ModItems.LAVENDER_SEEDS.get());
        this.simpleBlockItem(ModBlocks.GEODE_ORE.get());
        this.simpleBlockItem(ModBlocks.CERULEAN_ORE.get());
        this.simpleBlockItem(ModBlocks.MOONSTONE_ORE.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_DIRT.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_STONE.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_COAL_ORE.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_FARM_TILE.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_STONE_BRICKS.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_COBBLESTONE.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_GRASS_BLOCK.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_GRASS_LIGHT_BLOCK.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_PORTAL_FRAME_BRICKS.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_FURNACE.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_PERIDOTITE.get());
        this.simpleBlockItem(ModBlocks.MOON_SAND.get());
        this.simpleBlockItem(ModBlocks.RUNE_STONE.get());
        this.simpleBlockItem(ModBlocks.MOON_TEMPLE_BRICKS.get());
        this.simpleBlockItem(ModBlocks.DARK_STONE_BRICKS.get());
        this.simpleBlockItem(ModBlocks.DARK_STONE_FANCY.get());
        this.simpleBlockItem(ModBlocks.DARK_STONE_LAYERS.get());
        this.simpleBlockItem(ModBlocks.SMOOTH_RUNE_STONE.get());
        this.simpleBlockItem(ModBlocks.SMOOTH_MOON_TEMPLE_BRICKS.get());
        this.simpleBlockItem(ModBlocks.SMOOTH_AURORIAN_PERIDOTITE.get());
        this.simpleBlockItem(ModBlocks.RUNE_STONE_LAMP.get());
        this.simpleBlockItem(ModBlocks.DARK_STONE_LAMP.get());
        this.simpleBlockItem(ModBlocks.MOON_TEMPLE_LAMP.get());
        this.simpleBlockItem(ModBlocks.CERULEAN_BLOCK.get());
        this.simpleBlockItem(ModBlocks.MOONSTONE_BLOCK.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_COAL_BLOCK.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_STEEL_BLOCK.get());
        this.simpleBlockItem(ModBlocks.RUNE_STONE_GATE.get());
        this.simpleBlockItem(ModBlocks.DARK_STONE_GATE.get());
        this.simpleBlockItem(ModBlocks.MOON_TEMPLE_GATE.get());
        this.simpleBlockItem(ModBlocks.RUNE_STONE_LOOT_GATE.get());
        this.simpleBlockItem(ModBlocks.MOON_TEMPLE_CELL_GATE.get());
        this.simpleBlockItem(ModBlocks.RUNE_STONE_GATE_KEYHOLE.get());
        this.simpleBlockItem(ModBlocks.DARK_STONE_GATE_KEYHOLE.get());
        this.simpleBlockItem(ModBlocks.MOON_TEMPLE_GATE_KEYHOLE.get());
        this.simpleBlockItem(ModBlocks.RUNE_STONE_LOOT_GATE_KEYHOLE.get());
        this.simpleBlockItem(ModBlocks.MOON_TEMPLE_CELL_GATE_KEYHOLE.get());
        this.simpleBlockItem(ModBlocks.SCRAPPER.get());
        this.simpleBlockItem(ModBlocks.UMBRA_STONE.get());
        this.simpleBlockItem(ModBlocks.UMBRA_STONE_CRACKED.get());
        this.simpleBlockItem(ModBlocks.UMBRA_STONE_ROOF_TILES.get());
        this.simpleBlockItem(ModBlocks.INDIGO_MUSHROOM.get());
        this.simpleBlockItem(ModBlocks.INDIGO_MUSHROOM_BLOCK.get());
        this.simpleBlockItem(ModBlocks.INDIGO_MUSHROOM_STEM.get());
        this.simpleBlockItem(ModBlocks.INDIGO_MUSHROOM_CRYSTAL.get());
        this.simpleBlockItem(ModBlocks.SILENT_TREE_LOG.get());
        this.simpleBlockItem(ModBlocks.SILENT_TREE_WOOD.get());
        this.simpleBlockItem(ModBlocks.SILENT_TREE_PLANKS.get());
        this.simpleBlockItem(ModBlocks.WEEPING_WILLOW_LOG.get());
        this.simpleBlockItem(ModBlocks.WEEPING_WILLOW_WOOD.get());
        this.simpleBlockItem(ModBlocks.WEEPING_WILLOW_PLANKS.get());
        this.simpleBlockItem(ModBlocks.MOON_GLASS.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_GLASS.get());
        this.simpleBlockItem(ModBlocks.SILENT_TREE_LEAVES.get());
        this.simpleBlockItem(ModBlocks.WEEPING_WILLOW_LEAVES.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_STONE_STAIRS.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_STONE_BRICK_STAIRS.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_COBBLESTONE_STAIRS.get());
        this.simpleBlockItem(ModBlocks.SILENT_WOOD_STAIRS.get());
        this.simpleBlockItem(ModBlocks.RUNE_STONE_STAIRS.get());
        this.simpleBlockItem(ModBlocks.MOON_TEMPLE_STAIRS.get());
        this.simpleBlockItem(ModBlocks.DARK_STONE_STAIRS.get());
        this.simpleBlockItem(ModBlocks.UMBRA_STONE_STAIRS.get());
        this.simpleBlockItem(ModBlocks.UMBRA_STONE_CRACKED_STAIRS.get());
        this.simpleBlockItem(ModBlocks.UMBRA_STONE_ROOF_STAIRS.get());
        this.simpleBlockItem(ModBlocks.WEEPING_WILLOW_STAIRS.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_PERIDOTITE_STAIRS.get());
        this.simpleBlockItem(ModBlocks.SMOOTH_AURORIAN_PERIDOTITE_STAIRS.get());
        this.simpleBlockItem(ModBlocks.SILENT_WOOD_CRAFTING_TABLE.get());
        this.simpleBlockItemWithParent(ModBlocks.AURORIAN_GRASS.get());
        this.simpleBlockItemWithParent(ModBlocks.AURORIAN_GRASS_LIGHT.get());
        this.simpleBlockItemWithParent(ModBlocks.LAVENDER_PLANT.get());
        this.simpleBlockItemWithParent(ModBlocks.PETUNIA_PLANT.get());
        this.simpleBlockItemWithParent(ModBlocks.SILENT_WOOD_LADDER.get());
        this.simpleBlockItemWithParent(ModBlocks.SILENT_WOOD_TORCH.get());
        this.simpleBlockItemWithParent(ModBlocks.MOON_TORCH.get());
        this.simpleBlockItemWithParent(ModBlocks.RUNE_STONE_BARS.get());
        this.simpleBlockItemWithParent(ModBlocks.MOON_TEMPLE_BARS.get());
        this.withExistingParent(this.name(ModBlocks.AURORIAN_GLASS_PANE.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(ModBlocks.AURORIAN_GLASS.get())));
        this.withExistingParent(this.name(ModBlocks.MOON_GLASS_PANE.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(ModBlocks.MOON_GLASS.get())));
        this.withExistingParent(this.name(ModBlocks.SILENT_TREE_SAPLING.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(ModBlocks.SILENT_TREE_SAPLING.get())));
        for (Item item : ModCommonUtils.getKnownItems()) {
            ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
            if (item instanceof ForgeSpawnEggItem) {
                this.withExistingParent(key.getPath(), this.mcLoc("item/template_spawn_egg"));
            } else if (item instanceof TieredItem) {
                this.withExistingParent(key.getPath(), this.mcLoc("item/handheld"))
                        .texture("layer0", this.modLoc("item/" + key.getPath()));
            } else {
                if (!(item instanceof BlockItem) && item != ModItems.SLEEPING_BLACK_TEA.get()) {
                    this.basicItem(item);
                }
            }
        }
    }

    private void simpleItem(Item item) {
        String path = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
        withExistingParent(path, new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(AurorianMod.MOD_ID, "item/" + path));
    }

    private void simpleBlockItem(Block block) {
        String path = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
        this.withExistingParent(path, this.modLoc("block/" + path));
    }

    private void simpleBlockItemWithParent(Block block) {
        this.withExistingParent(this.name(block), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.name(block)));
    }

    private String name(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

}