package cn.teampancake.theaurorian.common.items.crafting;

import cn.teampancake.theaurorian.common.items.VagrantNotePage;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class VagrantNoteRecipe extends CustomRecipe {

    public VagrantNoteRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Nullable
    private Pair<ItemStack, ItemStack> getInputItems(CraftingInput input) {
        ItemStack vagrantNoteStack = null;
        ItemStack vagrantNotePageStack = null;
        int count = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack inputStack = input.getItem(i);
            if (!inputStack.isEmpty()) {
                count++;
                if (inputStack.is(TAItems.VAGRANT_NOTE)) {
                    vagrantNoteStack = inputStack;
                } else if (inputStack.getItem() instanceof VagrantNotePage) {
                    vagrantNotePageStack = inputStack;
                }
            }
        }

        if (count == 2 && vagrantNoteStack != null && vagrantNotePageStack != null) {
            List<Integer> chapter = vagrantNoteStack.get(TADataComponents.CHAPTERS);
            Integer noteChapter = vagrantNotePageStack.get(TADataComponents.NOTE_CHAPTER);
            if (chapter != null && noteChapter != null && noteChapter - chapter.size() == 1) {
                return Pair.of(vagrantNoteStack, vagrantNotePageStack);
            }
        }

        return null;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return this.getInputItems(input) != null;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        Pair<ItemStack, ItemStack> inputItems = this.getInputItems(input);
        DataComponentType<List<Integer>> component = TADataComponents.CHAPTERS.get();
        if (inputItems != null) {
            ItemStack vagrantNoteStack = inputItems.getFirst();
            List<Integer> chapters = vagrantNoteStack.get(component);
            Integer noteChapter = inputItems.getSecond().get(TADataComponents.NOTE_CHAPTER);
            if (chapters != null && noteChapter != null && !chapters.contains(noteChapter)) {
                List<Integer> newChapters = new ArrayList<>(List.copyOf(chapters));
                newChapters.add(noteChapter);
                ItemStack newVagrantNoteStack = vagrantNoteStack.copy();
                newVagrantNoteStack.set(component, newChapters);
                return newVagrantNoteStack;
            }
        }

        return ItemStack.EMPTY;
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