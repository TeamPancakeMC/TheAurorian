package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

import java.util.Optional;

public class CrystallinePickaxe extends PickaxeItem {

    public CrystallinePickaxe() {
        super(TAToolTiers.CRYSTALLINE, TAItemProperties.get().rarity(Rarity.EPIC).attributes(createAttributes(TAToolTiers.CRYSTALLINE, (1), (-2.8F))).addItemTag(ItemTags.PICKAXES, TAItemTags.IS_EPIC).hasTooltips());
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0D) {
            if (state.is(Tags.Blocks.ORES) && Math.random() * 100 < 30) {
                SingleRecipeInput singleRecipeInput = new SingleRecipeInput(new ItemStack(state.getBlock()));
                Optional<RecipeHolder<SmeltingRecipe>> recipe = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, singleRecipeInput, level);
                ItemStack ingot = ItemStack.EMPTY;
                if(recipe.isPresent()) {
                    ingot = recipe.get().value().getResultItem(level.registryAccess());
                    ingot.setCount(1);
                }

                if (ingot != ItemStack.EMPTY) {
                    ingot.setCount(level.random.nextIntBetweenInclusive(1,3) + 1);
                    level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), ingot));
                    stack.hurtAndBreak(2, entityLiving, EquipmentSlot.MAINHAND);
                    return true;
                }
            }

            stack.hurtAndBreak(1, entityLiving, EquipmentSlot.MAINHAND);
        }

        return true;
    }

}