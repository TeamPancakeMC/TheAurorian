package cn.teampancake.theaurorian.common.data.datagen.provider.tag;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/** @noinspection deprecation*/
public class TAItemTagsProvider extends IntrinsicHolderTagsProvider<Item> {

    private final CompletableFuture<TagsProvider.TagLookup<Block>> blockTags;
    private final Map<TagKey<Block>, TagKey<Item>> tagsToCopy = new HashMap<>();

    public TAItemTagsProvider(
            PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
            CompletableFuture<TagsProvider.TagLookup<Block>> blockTags,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, lookupProvider, item -> item.builtInRegistryHolder().key(), TheAurorian.MOD_ID, existingFileHelper);
        this.blockTags = blockTags;
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(CuriosTags.NECKLACE).add(TAItems.CRIMSON_PACT_PENDANT.get());
        for (Item item : TACommonUtils.getKnownItems()) {
            if (item instanceof ArmorItem armor) {
                switch (armor.getEquipmentSlot()) {
                    case HEAD -> this.tag(ItemTags.HEAD_ARMOR).add(armor);
                    case CHEST -> this.tag(ItemTags.CHEST_ARMOR).add(armor);
                    case LEGS -> this.tag(ItemTags.LEG_ARMOR).add(armor);
                    case FEET -> this.tag(ItemTags.FOOT_ARMOR).add(armor);
                }
            }
        }
    }

    protected void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
        this.tagsToCopy.put(blockTag, itemTag);
    }

    @Override
    protected CompletableFuture<HolderLookup.Provider> createContentsProvider() {
        return super.createContentsProvider().thenCombineAsync(this.blockTags, (provider, lookup) -> {
            this.tagsToCopy.forEach((tagKey, builder) -> {
                TagBuilder tagbuilder = this.getOrCreateRawBuilder(builder);
                Optional<TagBuilder> optional = lookup.apply(tagKey);
                optional.ifPresent(tagBuilder -> tagBuilder.build().forEach(tagbuilder::add));
            });
            return provider;
        });
    }

}