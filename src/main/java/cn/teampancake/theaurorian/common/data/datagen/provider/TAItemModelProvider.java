package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.items.VagrantNotePage;
import cn.teampancake.theaurorian.common.items.curio.runestone.Runestone;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class TAItemModelProvider extends ItemModelProvider {

    public TAItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheAurorian.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        TACommonUtils.getKnownItemStream().filter(item -> item instanceof VagrantNotePage).forEach(item ->
                this.withExistingParent(BuiltInRegistries.ITEM.getKey(item).getPath(), this.mcLoc("item/generated"))
                        .texture("layer0", this.modLoc("item/vagrant_note_page")));
        TACommonUtils.getKnownItemStream().filter(item -> item instanceof Runestone).forEach(item -> {
            String path = BuiltInRegistries.ITEM.getKey(item).getPath();
            ResourceLocation texture = this.modLoc("item/" + path.substring(0, path.lastIndexOf('_')));
            if (this.existingFileHelper.exists(texture, ModelProvider.TEXTURE)) {
                this.withExistingParent(path, this.mcLoc("item/generated")).texture("layer0", texture);
            }
        });
    }

    public static void separateTransforms(String name, ItemModelProvider provider, ExistingFileHelper helper) {
        ItemModelBuilder builder = new ItemModelBuilder(provider.modLoc(name), helper);
        ModelFile.UncheckedModelFile handheld = new ModelFile.UncheckedModelFile("item/handheld");
        ModelFile.UncheckedModelFile geoHandheld = new ModelFile.UncheckedModelFile(provider.modLoc("item/geo_handheld"));
        NonNullFunction<String, ItemModelBuilder> function = s -> new ItemModelBuilder(provider.modLoc("item/" + name + s), helper);
        provider.getBuilder(name).parent(handheld).customLoader(SeparateTransformsModelBuilder::begin)
                .base(new ItemModelBuilder(provider.modLoc(name), helper).parent(function.apply("_3d")))
                .perspective(ItemDisplayContext.GUI, builder.parent(function.apply("_2d")))
                .perspective(ItemDisplayContext.GROUND, builder.parent(function.apply("_2d")))
                .perspective(ItemDisplayContext.FIXED, builder.parent(function.apply("_2d"))).end();
        provider.getBuilder(name + "_3d").parent(geoHandheld);
        provider.handheldItem(provider.modLoc(name + "_2d"));
    }
    
    public static void crossingBlockItem(Block block, Block original, ItemModelProvider provider, String suffix) {
        provider.withExistingParent(blockName(block), provider.mcLoc("item/generated"))
                .texture("layer0", provider.modLoc("block/" + blockName(original) + suffix));
    }

    public static void crossingBlockItem(Block block, Block original, ItemModelProvider provider) {
        crossingBlockItem(block, original, provider, "");
    }

    public static void crossingBlockItem(Block block, ItemModelProvider provider, String suffix) {
        crossingBlockItem(block, block, provider, suffix);
    }

    public static void crossingBlockItem(Block block, ItemModelProvider provider) {
        crossingBlockItem(block, block, provider, "");
    }

    public static void clusterBlockItem(Block block, ItemModelProvider provider, String name) {
        provider.withExistingParent(blockName(block), provider.modLoc("item/" + name))
                .texture("layer0", provider.modLoc("block/" + blockName(block)));
    }

    public static void bowItem(Item item, ItemModelProvider provider) {
        String name = BuiltInRegistries.ITEM.getKey(item).getPath();
        ModelFile.UncheckedModelFile bowModel = new ModelFile.UncheckedModelFile(provider.modLoc("item/" + name));
        provider.withExistingParent(name, provider.modLoc("item/ta_bow"))
                .texture("layer0", provider.modLoc("item/" + name))
                .override().predicate(provider.modLoc("pulling"), 1)
                .model(new ModelFile.UncheckedModelFile(provider.modLoc("item/" + name + "_pulling_0"))).end()
                .override().predicate(provider.modLoc("pulling"), 1).predicate(provider.modLoc("pull"), 0.65F)
                .model(new ModelFile.UncheckedModelFile(provider.modLoc("item/" + name + "_pulling_1"))).end()
                .override().predicate(provider.modLoc("pulling"), 1).predicate(provider.modLoc("pull"), 0.9F)
                .model(new ModelFile.UncheckedModelFile(provider.modLoc("item/" + name + "_pulling_2"))).end();
        for (int i = 0; i < 3; i++) {
            String path = name + "_pulling_" + i;
            provider.getBuilder(path).parent(bowModel).texture("layer0", provider.modLoc("item/" + path));
        }
    }

    public static void shieldItem(Item item, ItemModelProvider provider) {
        String name = BuiltInRegistries.ITEM.getKey(item).getPath();
        provider.withExistingParent(name + "_blocking", provider.modLoc("item/ta_shield_blocking"))
                .texture("layer0", provider.modLoc("item/" + name));
        provider.withExistingParent(name, provider.modLoc("item/ta_shield"))
                .texture("layer0", provider.modLoc("item/" + name))
                .override().predicate(provider.modLoc("blocking"), 1.0F)
                .model(provider.getExistingFile(provider.modLoc(name + "_blocking")));
    }

    public static String blockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

}