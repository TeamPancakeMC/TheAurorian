package cn.teampancake.theaurorian.common.items.crafting;

import cn.teampancake.theaurorian.common.components.ChapterContent;
import cn.teampancake.theaurorian.common.items.VagrantNote;
import cn.teampancake.theaurorian.common.items.VagrantNotePage;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.*;

public class VagrantNoteRecipe extends CustomRecipe {

    public VagrantNoteRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Nullable
    private Pair<ItemStack, List<ItemStack>> getInputItems(CraftingInput input) {
        ItemStack noteStack = ItemStack.EMPTY;
        List<ItemStack> notePageStacks = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            ItemStack inputStack = input.getItem(i);
            if (inputStack.isEmpty()) continue;
            Item inputItem = inputStack.getItem();
            if (inputItem instanceof VagrantNote) {
                if (!noteStack.isEmpty()) return null;
                noteStack = inputStack;
            } else if (inputItem instanceof VagrantNotePage) {
                notePageStacks.add(inputStack);
            } else return null;
        }

        if (noteStack.isEmpty() || notePageStacks.isEmpty()) return null;
        List<ChapterContent> chapters = noteStack.get(TADataComponents.CHAPTERS);
        if (chapters == null) return null;
        int chapterSize = chapters.size();
        Set<Integer> indices = new HashSet<>();
        for (ItemStack stack : notePageStacks) {
            Integer index = stack.get(TADataComponents.NOTE_CHAPTER);
            if (index == null || index <= 0 || !indices.add(index)) return null;
            if (chapters.stream().anyMatch(c -> c.index() == index)) return null;
            if (index != chapterSize + indices.size()) return null;
        }

        return Pair.of(noteStack, notePageStacks);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return this.getInputItems(input) != null;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        Pair<ItemStack, List<ItemStack>> inputItems = this.getInputItems(input);
        DataComponentType<List<ChapterContent>> component = TADataComponents.CHAPTERS.get();
        if (inputItems == null) return ItemStack.EMPTY;
        ItemStack firstStack = inputItems.getFirst();
        List<ChapterContent> chapters = firstStack.get(component);
        if (chapters == null) return ItemStack.EMPTY;
        List<ChapterContent> newChapters = new ArrayList<>(chapters);
        for (ItemStack stack : inputItems.getSecond()) {
            Integer noteChapter = stack.get(TADataComponents.NOTE_CHAPTER);
            if (noteChapter == null) return ItemStack.EMPTY;
            newChapters.add(new ChapterContent(noteChapter, stack.getDisplayName()));
        }

        ItemStack outputStack = firstStack.copy();
        outputStack.set(component, newChapters);
        return outputStack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TARecipes.VAGRANT_NOTE_SERIALIZER.get();
    }

}