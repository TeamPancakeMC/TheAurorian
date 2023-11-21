package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModItems;
import cn.teampancake.theaurorian.utils.ModCommonUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.Objects;

@SuppressWarnings({"ConstantConditions", "unused"})
public class ModItemModelProvider extends ItemModelProvider {

    private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();

    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

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
        this.simpleBlockItem(ModBlocks.SMOOTH_RUNE_STONE.get());
        this.simpleBlockItem(ModBlocks.CHISELED_RUNE_STONE.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(ModBlocks.AURORIAN_STEEL_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(ModBlocks.CERULEAN_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(ModBlocks.CRYSTALLINE_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(ModBlocks.MOON_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(ModBlocks.TRANSPARENT_RUNE_STONE.get());
        this.simpleBlockItem(ModBlocks.UMBRA_CASTLE_RUNE_STONE.get());
        this.simpleBlockItem(ModBlocks.RUNE_STONE_PILLAR.get());
        this.simpleBlockItem(ModBlocks.MOON_TEMPLE_BRICKS.get());
        this.simpleBlockItem(ModBlocks.DARK_STONE_BRICKS.get());
        this.simpleBlockItem(ModBlocks.DARK_STONE_FANCY.get());
        this.simpleBlockItem(ModBlocks.DARK_STONE_LAYERS.get());
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

        for (Block block : ModCommonUtils.getKnownBlocks()) {
            if (block instanceof StairBlock || block instanceof SlabBlock) {
                this.simpleBlockItem(block);
            }
        }

        for (Item item : ModCommonUtils.getKnownItems()) {
            ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
            if (item instanceof ForgeSpawnEggItem) {
                this.withExistingParent(key.getPath(), this.mcLoc("item/template_spawn_egg"));
            } else if (item instanceof TieredItem) {
                this.withExistingParent(key.getPath(), this.mcLoc("item/handheld"))
                        .texture("layer0", this.modLoc("item/" + key.getPath()));
            } else if (item instanceof ShieldItem) {
                this.shieldItem(item);
            } else if (!(item instanceof BlockItem) && item != ModItems.SLEEPING_BLACK_TEA.get()) {
                this.basicItem(item);
            }
        }

        trimmedArmorItem(ModItems.AURORIAN_STEEL_HELMET);
        trimmedArmorItem(ModItems.AURORIAN_STEEL_CHESTPLATE);
        trimmedArmorItem(ModItems.AURORIAN_STEEL_LEGGINGS);
        trimmedArmorItem(ModItems.AURORIAN_STEEL_BOOTS);
        trimmedArmorItem(ModItems.CERULEAN_HELMET);
        trimmedArmorItem(ModItems.CERULEAN_CHESTPLATE);
        trimmedArmorItem(ModItems.CERULEAN_LEGGINGS);
        trimmedArmorItem(ModItems.KNIGHT_HELMET);
        trimmedArmorItem(ModItems.CERULEAN_BOOTS);
        trimmedArmorItem(ModItems.KNIGHT_CHESTPLATE);
        trimmedArmorItem(ModItems.KNIGHT_LEGGINGS);
        trimmedArmorItem(ModItems.KNIGHT_BOOTS);
        trimmedArmorItem(ModItems.SPECTRAL_HELMET);
        trimmedArmorItem(ModItems.SPECTRAL_CHESTPLATE);
        trimmedArmorItem(ModItems.SPECTRAL_LEGGINGS);
        trimmedArmorItem(ModItems.SPECTRAL_BOOTS);
        trimmedArmorItem(ModItems.AURORIAN_SLIME_BOOTS);
        trimmedArmorItem(ModItems.SPIKED_CHESTPLATE);
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

    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = AurorianMod.MOD_ID; // Change this to your mod id

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {

                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
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