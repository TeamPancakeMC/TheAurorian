package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

import java.util.Optional;

public class CrystallinePickaxe extends PickaxeItem implements ITooltipsItem {

    public CrystallinePickaxe() {
        super(TAToolTiers.CRYSTALLINE, 1, -2.8F, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return !(EnchantmentHelper.getEnchantments(stack).containsKey(Enchantments.BLOCK_FORTUNE));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0D) {
            if (state.is(Tags.Blocks.ORES) && Math.random()*100<30) {
                Optional<SmeltingRecipe> recipe= level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(new ItemStack(state.getBlock())), level);
                ItemStack ingot = ItemStack.EMPTY;
                if(recipe.isPresent()) {
                    ingot = recipe.get().getResultItem(level.registryAccess());
                    ingot.setCount(1);
                }

                if (ingot != ItemStack.EMPTY) {
                    ingot.setCount(level.random.nextIntBetweenInclusive(1,3) + 1);
                    level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), ingot));
                    stack.hurtAndBreak(2, entityLiving, (player) -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    return true;
                }
            }
            stack.hurtAndBreak(1, entityLiving,(player) -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

}