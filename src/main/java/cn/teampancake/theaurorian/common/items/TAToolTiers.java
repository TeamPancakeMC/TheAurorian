package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.api.ISpecialty;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import javax.annotation.Nonnull;;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings({"deprecation", "SpellCheckingInspection"})
public enum TAToolTiers implements Tier, ISpecialty {

    SILENT_WOOD(0, 59, 3.0F, 0.0F, 20, () -> Ingredient.of(TABlocks.SILENT_TREE_PLANKS.get())){
        @Override
        public void doSpecialty(ItemStack stack) {

        }
    },
    AURORIAN_STONE(0, 131, 4.5F, 1.5F, 14, () -> Ingredient.of(TABlocks.AURORIAN_STONE.get())){
        @Override
        public void doSpecialty(ItemStack stack) {

        }
    },
    MOONSTONE(2, 250, 7.0F, 2.5F, 14, () -> Ingredient.of(TABlocks.MOONSTONE_BLOCK.get())){
        @Override
        public void doSpecialty(ItemStack stack) {

        }
    },
    AURORIANITE(3, 1000, 8.0F, 3.0F, 20, () -> Ingredient.of(TAItems.AURORIANITE_INGOT.get())){
        @Override
        public void doSpecialty(ItemStack stack) {
            CompoundTag orCreateTag = stack.getOrCreateTag();
            boolean aurorianiteSpecialty = orCreateTag.getBoolean("aurorianite_specialty");
            if (aurorianiteSpecialty){
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
                Enchantment enchantment = enchantments.keySet().stream()
                        .filter(enchantment1 -> {
                            int maxLevel = enchantment1.getMaxLevel();
                            return enchantments.get(enchantment1) < maxLevel;
                        })
                        .skip((int) (enchantments.size() * Math.random()))
                        .findFirst()
                        .orElse(null);

                if (enchantment != null) {
                    int level = enchantments.get(enchantment);
                    enchantments.put(enchantment, level + 1);
                    EnchantmentHelper.setEnchantments(enchantments, stack);
                }
            }
        }
    },
    UMBRA(3, 1000, 8.0F, 3.0F, 20, () -> Ingredient.of(TAItems.UMBRA_INGOT.get())){
        @Override
        public void doSpecialty(ItemStack stack) {

        }
    },
    CRYSTALLINE(3, 1000, 8.0F, 3.0F, 20, () -> Ingredient.of(TAItems.CRYSTALLINE_INGOT.get())){
        @Override
        public void doSpecialty(ItemStack stack) {

        }
    },
    AURORIAN_STEEL(3, 1500, 8.5F, 3.5F, 10, () -> Ingredient.of(TAItems.AURORIAN_STEEL.get())){
        @Override
        public void doSpecialty(ItemStack stack) {

        }
    },
    TSLAT(3, 2000, 1.9F, 7.0F, 15, Ingredient::of){
        @Override
        public void doSpecialty(ItemStack stack) {

        }
    };

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
