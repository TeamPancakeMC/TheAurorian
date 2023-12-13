package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.registry.TABlocks;
import cn.teampancake.theaurorian.registry.TAItems;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

@SuppressWarnings({"deprecation", "SpellCheckingInspection"})
public enum TAToolTiers implements Tier {

    SILENT_WOOD(0, 59, 3.0F, 0.0F, 20, () -> Ingredient.of(TABlocks.SILENT_TREE_PLANKS.get())),
    AURORIAN_STONE(0, 131, 4.5F, 1.5F, 14, () -> Ingredient.of(TABlocks.AURORIAN_STONE.get())),
    MOONSTONE(2, 250, 7.0F, 2.5F, 14, () -> Ingredient.of(TABlocks.MOONSTONE_BLOCK.get())),
    AURORIANITE(3, 1000, 8.0F, 3.0F, 20, () -> Ingredient.of(TAItems.AURORIANITE_INGOT.get())),
    UMBRA(3, 1000, 8.0F, 3.0F, 20, () -> Ingredient.of(TAItems.UMBRA_INGOT.get())),
    CRYSTALLINE(3, 1000, 8.0F, 3.0F, 20, () -> Ingredient.of(TAItems.CRYSTALLINE_INGOT.get())),
    AURORIAN_STEEL(3, 1500, 8.5F, 3.5F, 10, () -> Ingredient.of(TAItems.AURORIAN_STEEL.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    TAToolTiers(int level, int uses, float speed, float damage,
                int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    @Nonnull
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

}
