package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.client.inventory.AlchemyTableMenu;
import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipe;
import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipeInput;
import cn.teampancake.theaurorian.common.registry.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AlchemyTableBlockEntity extends SimpleContainerBlockEntity {

    private int alchemyTime;
    private int maxAlchemyTime;
    private boolean canMixPotion;
    private boolean canEffectFusion;
    private final ContainerData containerData = new Data();
    private final RecipeManager.CachedCheck<AlchemyTableRecipeInput, ? extends AlchemyTableRecipe> quickCheck;

    public AlchemyTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.ALCHEMY_TABLE.get(), pos, blockState);
        this.quickCheck = RecipeManager.createCheck(TARecipes.ALCHEMY_TABLE_RECIPE.get());
        this.handler = new Handler(5);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AlchemyTableBlockEntity blockEntity) {
        if (!level.isClientSide()) {
            blockEntity.mixPotion(pos, state);
            blockEntity.foodEffectFusion(pos, state);
            if (!blockEntity.canMixPotion && !blockEntity.canEffectFusion) {
                AlchemyTableRecipe recipe = blockEntity.checkBrewRecipe();
                if (recipe != null) {
                    blockEntity.baseBrew(recipe, pos, state);
                } else {
                    blockEntity.maxAlchemyTime = 0;
                    blockEntity.alchemyTime = 0;
                    setChanged(level, pos, state);
                }
            }
        }
    }

    private void baseBrew(AlchemyTableRecipe recipe, BlockPos pos, BlockState state) {
        AlchemyTableRecipeInput recipeInput = this.getRecipeInput();
        if (this.level != null && recipe.matches(recipeInput, this.level)) {
            RegistryAccess access = this.level.registryAccess();
            ItemStack result = recipe.assemble(recipeInput, access);
            ItemStack existing = this.getItem(4);
            if (this.canWork(existing, result)) {
                this.maxAlchemyTime = recipe.alchemyTime();
                this.alchemyTime++;
                if (this.alchemyTime > recipe.alchemyTime()) {
                    if (existing.isEmpty()) {
                        this.setItem(4, result);
                    } else {
                        existing.grow(1);
                    }

                    for (int i = 0; i < 4; i++) {
                        ItemStack inputItem = recipeInput.getItem(i);
                        if (inputItem.hasCraftingRemainingItem()) {
                            this.setItem(i, inputItem.getCraftingRemainingItem());
                        }

                        if (!inputItem.isEmpty()) {
                            inputItem.shrink(1);
                        }
                    }

                    this.maxAlchemyTime = 0;
                    this.alchemyTime = 0;
                    setChanged(this.level, pos, state);
                }
            }
        }
    }

    private void mixPotion(BlockPos pos, BlockState state) {
        DataComponentType<PotionContents> potionContents = DataComponents.POTION_CONTENTS;
        ItemStack material = this.getItem(3);
        boolean hasA = false, hasB = false, hasCustom = false;
        int cIndex = -1, mIndex = -1, potionIndex = -1;
        for (int i = 0; i < 3; i++) {
            ItemStack stack = this.getItem(i);
            if (!hasA && stack.is(TAItems.CERULEAN_NUGGET)) {
                hasA = true;
                cIndex = i;
            } else if (!hasB && stack.is(TAItems.MOONSTONE_NUGGET)) {
                hasB = true;
                mIndex = i;
            } else if (!hasCustom && stack.has(potionContents)) {
                hasCustom = true;
                potionIndex = i;
            }
        }

        this.canMixPotion = hasA && hasB && hasCustom;
        if (potionIndex < 0 || this.level == null) return;
        ItemStack potionStack = this.getItem(potionIndex);
        if (!potionStack.isEmpty() && !material.isEmpty() && this.getItem(4).isEmpty() && this.canMixPotion) {
            PotionContents inputContents = potionStack.get(potionContents);
            PotionContents materialContents = material.get(potionContents);
            if (inputContents != null && materialContents != null && inputContents.hasEffects()) {
                this.maxAlchemyTime = 140;
                this.alchemyTime++;
                if (this.alchemyTime > 140) {
                    List<MobEffectInstance> inputList = new ArrayList<>();
                    inputContents.getAllEffects().forEach(inputList::add);
                    Optional<Holder<Potion>> potion = Optional.of(TAPotions.OMNI);
                    List<MobEffectInstance> customEffects = inputList;
                    if (materialContents.hasEffects()) {
                        List<MobEffectInstance> materialList = new ArrayList<>();
                        materialContents.getAllEffects().forEach(materialList::add);
                        customEffects = this.mergeCustomEffectList(materialList, inputList);
                    }

                    customEffects.forEach(instance -> instance.duration += 200);
                    Optional<Integer> customColor = Optional.of(PotionContents.getColor(customEffects));
                    PotionContents newResultContents = new PotionContents(potion, customColor, customEffects);
                    material.set(potionContents, newResultContents);
                    this.setItem(4, material.copy());
                    this.setItem(3, ItemStack.EMPTY);
                    this.setItem(potionIndex, new ItemStack(Items.GLASS_BOTTLE));
                    this.getItem(cIndex).shrink(1);
                    this.getItem(mIndex).shrink(1);
                    this.maxAlchemyTime = 0;
                    this.alchemyTime = 0;
                    this.canMixPotion = false;
                    setChanged(this.level, pos, state);
                }
            }
        }
    }

    private void foodEffectFusion(BlockPos pos, BlockState state) {
        DataComponentType<PotionContents> potionContents = DataComponents.POTION_CONTENTS;
        DataComponentType<FoodProperties> food = DataComponents.FOOD;
        boolean hasFood = false;
        int foodIndex = -1;
        for (int i = 0; i < 3; i++) {
            if (this.getItem(i).has(food)) {
                hasFood = true;
                foodIndex = i;
                break;
            }
        }

        this.canEffectFusion = hasFood;
        if (foodIndex < 0) return;
        ItemStack foodStack = this.getItem(foodIndex);
        ItemStack potionStack = this.getItem(3);
        if (!foodStack.isEmpty() && !potionStack.isEmpty() && this.canEffectFusion) {
            FoodProperties oldFoodProperties = foodStack.get(food);
            PotionContents materialContents = potionStack.get(potionContents);
            boolean flag = materialContents != null && materialContents.hasEffects();
            if (this.level != null && oldFoodProperties != null && flag) {
                List<MobEffectInstance> potionEffects = new ArrayList<>();
                materialContents.getAllEffects().forEach(potionEffects::add);
                List<FoodProperties.PossibleEffect> possibleEffects = oldFoodProperties.effects();
                ImmutableList.Builder<FoodProperties.PossibleEffect> effects = ImmutableList.builder();
                if (!possibleEffects.isEmpty()) {
                    List<MobEffectInstance> foodEffects = new ArrayList<>();
                    possibleEffects.forEach(effect -> foodEffects.add(effect.effect()));
                    List<MobEffectInstance> customEffects = this.mergeCustomEffectList(foodEffects, potionEffects);
                    customEffects.forEach(instance -> effects.add(this.newPossibleEffect(instance)));
                } else {
                    potionEffects.forEach(instance -> effects.add(this.newPossibleEffect(instance)));
                }

                FoodProperties newFoodProperties = new FoodProperties(
                        oldFoodProperties.nutrition(),
                        oldFoodProperties.saturation(),
                        oldFoodProperties.canAlwaysEat(),
                        oldFoodProperties.eatSeconds(),
                        oldFoodProperties.usingConvertsTo(),
                        effects.build());
                ItemStack resultStack = foodStack.copyWithCount(1);
                resultStack.set(food, newFoodProperties);
                ItemStack existing = this.getItem(4);
                if (this.canWork(existing, resultStack)) {
                    this.maxAlchemyTime = 140;
                    this.alchemyTime++;
                    if (this.alchemyTime > 140) {
                        potionStack.set(potionContents, new PotionContents(Potions.WATER));
                        resultStack.set(TADataComponents.INFUSED_POTION, true);
                        foodStack.shrink(1);
                        if (existing.isEmpty()) {
                            this.setItem(4, resultStack);
                        } else {
                            existing.grow(1);
                        }

                        this.maxAlchemyTime = 0;
                        this.alchemyTime = 0;
                        this.canEffectFusion = false;
                        setChanged(this.level, pos, state);
                    }
                }
            }
        }
    }

    private List<MobEffectInstance> mergeCustomEffectList(List<MobEffectInstance> inputList, List<MobEffectInstance> resultList) {
        Map<Holder<MobEffect>, MobEffectInstance> resultMap = new HashMap<>();
        for (MobEffectInstance newInstance : inputList) {
            Holder<MobEffect> effect = newInstance.getEffect();
            if (resultMap.containsKey(effect)) {
                MobEffectInstance existInstance = resultMap.get(effect);
                existInstance.duration += newInstance.duration;
                existInstance.amplifier += newInstance.amplifier;
            } else {
                resultMap.put(effect, newInstance);
            }
        }

        for (MobEffectInstance newInstance : resultList) {
            Holder<MobEffect> effect = newInstance.getEffect();
            if (resultMap.containsKey(effect)) {
                MobEffectInstance existInstance = resultMap.get(effect);
                existInstance.duration += newInstance.duration;
                existInstance.amplifier += newInstance.amplifier;
            } else {
                resultMap.put(effect, newInstance);
            }
        }

        return new ArrayList<>(resultMap.values());
    }
    
    private boolean canWork(ItemStack existing, ItemStack result) {
        if (existing.isEmpty()) {
            return true;
        } else if (!ItemStack.isSameItemSameComponents(existing, result)) {
            return false;
        } else {
            int totalCount = existing.getCount() + result.getCount();
            return totalCount <= this.getMaxStackSize()
                    && totalCount <= existing.getMaxStackSize()
                    || totalCount <= result.getMaxStackSize();
        }
    }

    private FoodProperties.PossibleEffect newPossibleEffect(MobEffectInstance instance) {
        return new FoodProperties.PossibleEffect(() -> instance, 1.0F);
    }

    @Nullable
    private AlchemyTableRecipe checkBrewRecipe() {
        if (this.level != null) {
            RecipeHolder<? extends AlchemyTableRecipe> holder = this.quickCheck.getRecipeFor(this.getRecipeInput(), this.level).orElse(null);
            return holder != null ? holder.value() : null;
        }

        return null;
    }

    private AlchemyTableRecipeInput getRecipeInput() {
        ItemStack input1 = this.getItem(0);
        ItemStack input2 = this.getItem(1);
        ItemStack input3 = this.getItem(2);
        List<ItemStack> ingredients = Arrays.asList(input1, input2, input3);
        return new AlchemyTableRecipeInput(ingredients, this.getItem(3));
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.alchemyTime = tag.getInt("AlchemyTime");
        this.maxAlchemyTime = tag.getInt("MaxAlchemyTime");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("AlchemyTime", this.alchemyTime);
        tag.putInt("MaxAlchemyTime", this.maxAlchemyTime);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        ContainerLevelAccess access = ContainerLevelAccess.create(Objects.requireNonNull(this.level), this.worldPosition);
        return new AlchemyTableMenu(containerId, inventory, access, this.handler, this.containerData);
    }

    private class Data implements ContainerData {

        @Override
        public int get(int index) {
            if (index == 0) {
                return alchemyTime;
            } else if (index == 1) {
                return maxAlchemyTime;
            } else {
                return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                alchemyTime = value;
            } else if (index == 1) {
                maxAlchemyTime = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

    }

}