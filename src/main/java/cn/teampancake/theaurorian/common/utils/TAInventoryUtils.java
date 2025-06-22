package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.common.components.AlchemyProduct;
import cn.teampancake.theaurorian.common.datamaps.AlchemyTableMaterial;
import cn.teampancake.theaurorian.common.level.alchemy.PotionDecaySystem;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TADataMaps;
import com.google.common.collect.Lists;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TAInventoryUtils {

    public static List<ItemStack> getInventoryItems(Inventory inventory, Function<ItemStack, Boolean> function) {
        ArrayList<ItemStack> stacks = Lists.newArrayList();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (function.apply(stack)) {
                stacks.add(stack);
            }
        }

        return stacks;
    }

    public static void refreshIngredientApplier(ContainerData containerData, List<ItemStack> stacks, boolean updateLiquidData) {
        DataComponentType<Boolean> component = TADataComponents.INGREDIENT_APPLIER.get();
        int liquidLevel = containerData.get(2);
        int liquidData = containerData.get(3);
        stacks.forEach(stack -> {
            if (liquidLevel > 0 && stack.is(Items.NETHER_WART)) {
                int i = TAPotionUtils.applyNetherWart(liquidData);
                stack.set(component, i != liquidData);
                if (updateLiquidData) {
                    containerData.set(3, i);
                }
            }

            AlchemyTableMaterial data = stack.getItemHolder().getData(TADataMaps.ALCHEMY_TABLE_INGREDIENTS);
            if (liquidLevel > 0 && data != null) {
                int i = TAPotionUtils.applyIngredient(liquidData, data.formula());
                stack.set(component, i != liquidData);
                if (updateLiquidData) {
                    containerData.set(3, i);
                }
            }
        });
    }

    public static void applyPotionDecay(NonNullList<ItemStack> items, @Nullable Player player, Level level) {
        if (!items.isEmpty() && level.getGameTime() % 20 == 0) {
            for (ItemStack stack : items) {
                AlchemyProduct alchemyProduct = stack.get(TADataComponents.ALCHEMY_PRODUCT);
                PotionContents potionContents = stack.get(DataComponents.POTION_CONTENTS);
                FoodProperties foodProperties = stack.get(DataComponents.FOOD);
                if (alchemyProduct != null) {
                    if (potionContents != null && potionContents.hasEffects()) {
                        PotionDecaySystem.applyDecay(alchemyProduct, potionContents.customEffects(), player, level);
                    }

                    if (foodProperties != null && !foodProperties.effects().isEmpty()) {
                        List<MobEffectInstance> effectInstances = new ArrayList<>();
                        foodProperties.effects().forEach(possibleEffect -> effectInstances.add(possibleEffect.effect()));
                        PotionDecaySystem.applyDecay(alchemyProduct, effectInstances, player, level);
                    }
                }
            }
        }
    }

}