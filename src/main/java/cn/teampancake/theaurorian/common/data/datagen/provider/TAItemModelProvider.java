package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
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
        this.simpleBlockItem(TABlocks.SILENT_TREE_LEAVES.get());
        this.simpleBlockItem(TABlocks.WEEPING_WILLOW_LEAVES.get());
        this.simpleBlockItem(TABlocks.CURTAIN_TREE_LEAVES.get());
        this.simpleBlockItem(TABlocks.CURSED_FROST_TREE_LEAVES.get());
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
        this.withExistingParent(this.blockName(TABlocks.SILENT_WOOD_CHEST.get()), this.mcLoc("item/chest"))
                .texture("particle", this.modLoc("block/" + this.blockName(TABlocks.SILENT_TREE_PLANKS.get())));
        this.withExistingParent(this.blockName(TABlocks.MOON_GLASS_PANE.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.MOON_GLASS.get())));
        this.withExistingParent(this.blockName(TABlocks.AURORIAN_GLASS_PANE.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.AURORIAN_GLASS.get())));
        this.withExistingParent(this.blockName(TABlocks.DARK_STONE_GLASS_PANE.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.DARK_STONE_GLASS.get())));
        this.withExistingParent(this.blockName(TABlocks.SILENT_TREE_SAPLING.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.SILENT_TREE_SAPLING.get())));
        this.withExistingParent(this.blockName(TABlocks.CURTAIN_TREE_SAPLING.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.CURTAIN_TREE_SAPLING.get())));
        this.withExistingParent(this.blockName(TABlocks.CURSED_FROST_TREE_SAPLING.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.CURSED_FROST_TREE_SAPLING.get())));
        this.withExistingParent(this.blockName(TABlocks.INDIGO_MUSHROOM.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.INDIGO_MUSHROOM.get())));
        this.withExistingParent(this.blockName(TABlocks.SMALL_FILTHY_ICE_SPIKE.get()), this.modLoc("item/ta_small_bud"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.SMALL_FILTHY_ICE_SPIKE.get())));
        this.withExistingParent(this.blockName(TABlocks.MEDIUM_FILTHY_ICE_SPIKE.get()), this.modLoc("item/ta_medium_bud"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.MEDIUM_FILTHY_ICE_SPIKE.get())));
        this.withExistingParent(this.blockName(TABlocks.LARGE_FILTHY_ICE_SPIKE.get()), this.modLoc("item/ta_large_bud"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.LARGE_FILTHY_ICE_SPIKE.get())));
        this.withExistingParent(this.blockName(TABlocks.CERULEAN_CLUSTER.get()), this.modLoc("item/ta_cluster"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.CERULEAN_CLUSTER.get())));
        this.withExistingParent(this.blockName(TABlocks.LARGE_CERULEAN_BUD.get()), this.modLoc("item/ta_large_bud"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.LARGE_CERULEAN_BUD.get())));
        this.withExistingParent(this.blockName(TABlocks.MEDIUM_CERULEAN_BUD.get()), this.modLoc("item/ta_medium_bud"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.MEDIUM_CERULEAN_BUD.get())));
        this.withExistingParent(this.blockName(TABlocks.SMALL_CERULEAN_BUD.get()), this.modLoc("item/ta_small_bud"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.SMALL_CERULEAN_BUD.get())));
        this.withExistingParent(this.blockName(TABlocks.MOONSTONE_CLUSTER.get()), this.modLoc("item/ta_cluster"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.MOONSTONE_CLUSTER.get())));
        this.withExistingParent(this.blockName(TABlocks.LARGE_MOONSTONE_BUD.get()), this.modLoc("item/ta_large_bud"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.LARGE_MOONSTONE_BUD.get())));
        this.withExistingParent(this.blockName(TABlocks.MEDIUM_MOONSTONE_BUD.get()), this.modLoc("item/ta_medium_bud"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.MEDIUM_MOONSTONE_BUD.get())));
        this.withExistingParent(this.blockName(TABlocks.SMALL_MOONSTONE_BUD.get()), this.modLoc("item/ta_small_bud"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.SMALL_MOONSTONE_BUD.get())));
        this.withExistingParent(this.blockName(TABlocks.TALL_WICK_GRASS.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.TALL_WICK_GRASS.get()) + "_upper"));
        this.withExistingParent(this.blockName(TABlocks.TALL_AURORIAN_GRASS.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.TALL_AURORIAN_GRASS.get()) + "_upper"));
        this.withExistingParent(this.blockName(TABlocks.TALL_LAVENDER_PLANT.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.TALL_LAVENDER_PLANT.get()) + "_upper"));
        this.withExistingParent(this.blockName(TABlocks.TALL_AURORIAN_WATER_GRASS.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.TALL_AURORIAN_WATER_GRASS.get()) + "_upper"));
        this.withExistingParent(this.blockName(TABlocks.TALL_AURORIAN_GRASS_LIGHT.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(TABlocks.TALL_AURORIAN_GRASS_LIGHT.get()) + "_upper"));
        TACommonUtils.getKnownBlockStream().filter(block -> block.properties() instanceof TABlockProperties properties && properties.useSimpleBlockItem).forEach(this::simpleBlockItem);
        TACommonUtils.getKnownBlockStream().filter(block -> block instanceof DoorBlock).forEach(block -> this.basicItem(block.asItem()));
        TACommonUtils.getKnownItemStream().filter(item -> item instanceof TieredItem && !item.equals(TAItems.CRYSTALLINE_SWORD.get())).forEach(item ->
                this.withExistingParent(this.itemName(item), this.mcLoc("item/handheld")).texture("layer0", this.modLoc("item/" + this.itemName(item))));
        TACommonUtils.getKnownItemStream().filter(item -> item instanceof DeferredSpawnEggItem).forEach(item ->
                this.withExistingParent(this.itemName(item), this.mcLoc("item/template_spawn_egg")));
        TACommonUtils.getKnownItemStream().filter(item -> item instanceof BowItem).forEach(this::bowItem);
        TACommonUtils.getKnownItemStream().filter(item -> item instanceof ShieldItem).forEach(this::shieldItem);
        TACommonUtils.getKnownItemStream().filter(this::isSimpleModelItem).forEach(this::basicItem);
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
        this.withExistingParent(path, this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + path));
    }

    private void simpleBlockItem(Block block) {
        this.withExistingParent(this.blockName(block), this.modLoc("block/" + this.blockName(block)));
    }

    private void simpleBlockItemWithParent(Block block) {
        this.withExistingParent(this.blockName(block), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(block)));
    }

    private boolean isSimpleModelItem(Item item) {
        return TACommonUtils.getItemProperties(item).isSimpleModelItem && !(item instanceof BlockItem);
    }

    private String itemName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    private String blockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

}